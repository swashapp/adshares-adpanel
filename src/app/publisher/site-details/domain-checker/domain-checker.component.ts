import { Component, Input, OnInit } from '@angular/core';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons';
import { HttpClient } from '@angular/common/http';
import { HandleSubscription } from 'common/handle-subscription';
import { PublisherService } from 'publisher/publisher.service';
import { timer } from 'rxjs/observable/timer';

enum PageRankInfo {
  OK = 'ok',
  UNKNOWN = 'unknown',
  HIGH_IVR = 'high-ivr',
  HIGH_CTR = 'high-ctr',
  LOW_CTR = 'low-ctr',
  POOR_TRAFFIC = 'poor-traffic',
  POOR_CONTENT = 'poor-content',
  SUSPICIOUS_DOMAIN = 'suspicious-domain',
  NOT_WORKING = 'not-working',
}

@Component({
  selector: 'app-domain-checker',
  templateUrl: './domain-checker.component.html',
  styleUrls: ['./domain-checker.component.scss'],
})
export class DomainCheckerComponent extends HandleSubscription implements OnInit {
  private static readonly UPDATE_INTERVAL = 300000;//5 * 60 * 1000 s minutes
  @Input() domain: string;
  faQuestionCircle = faQuestionCircle;

  pageRank: number = 0;
  pageRankInfo: string = '';
  inVerification: boolean = true;
  isBanned: boolean = false;

  constructor(private publisherService: PublisherService, private http: HttpClient) {
    super();
  }

  ngOnInit(): void {
    const domainCheckSubscription = timer(0, DomainCheckerComponent.UPDATE_INTERVAL)
      .switchMap(() => this.http.get<any>('https://gitoku.com/api/v1/domain/' + encodeURIComponent(this.domain)))
      .subscribe(
        (response: any) => {
          this.updateMessage(response.rank || 0, response.info || PageRankInfo.UNKNOWN);
        },
        () => this.updateMessage(0, PageRankInfo.UNKNOWN)
      );

    this.subscriptions.push(domainCheckSubscription);
  }

  updateMessage(pageRank: number, pageRankInfo: string): void {
    this.inVerification = 0 === pageRank && PageRankInfo.UNKNOWN === pageRankInfo;
    this.isBanned = !this.inVerification && pageRank <= 0;
    this.pageRank = Math.round(pageRank * 10);
    this.pageRankInfo = this.tooltipPageRankInfo(pageRankInfo);
  }

  rankQuality(): string {
    let quality = '';
    if (this.pageRank >= 7) {
      quality = 'great';
    } else if (this.pageRank >= 3) {
      quality = 'good';
    } else if (this.pageRank > 0) {
      quality = 'medium';
    } else {
      quality = 'bad';
    }

    return quality;
  }

  cpaQuality(): string {
    return this.inVerification || this.pageRank != 0 ? 'great' : 'bad';
  }

  cpmQuality(): string {
    if (this.inVerification) {
      return  'medium';
    }

    let quality = '';
    if (this.pageRank >= 7) {
      quality = 'great';
    } else if (this.pageRank >= 3) {
      quality = 'good';
    } else if (this.pageRank > 0) {
      quality = 'medium';
    } else {
      quality = 'bad';
    }

    return quality;
  }

  cpaMessage(): string {
    return this.inVerification || this.pageRank != 0 ? 'approved' : 'banned';
  }

  cpmMessage(): string {
    const rank = this.pageRank < 10 ? ` (${this.pageRank}/10)` : '';
    return this.inVerification ? 'in verification' : (this.isBanned ? 'banned' : `approved${rank}`);
  }

  cpaTooltip(): string {
    return this.inVerification || this.pageRank != 0 ?
      'Your site has been approved for CPA campaigns.' :
      'CPA campaigns have been excluded from your site. ' + this.pageRankInfo;

  }

  cpmTooltip(): string {
    if (this.inVerification) {
      return 'The domain is waiting for verification. CPM campaigns will not display until then.';
    }

    if (this.isBanned) {
      return 'CPM campaigns have been excluded from your site. ' + this.pageRankInfo;
    }

    let info = '';
    if (this.pageRank < 10) {
      info = 'Your site has been conditionally approved for CPM campaigns. ';
      if (this.pageRankInfo.length > 0) {
        info += `Your rating is ${this.pageRank}/10; to increase the rating, follow the instructions. `;
        info += this.pageRankInfo;
      }
    } else {
      info = 'Your site has been approved for CPM campaigns.';
    }

    return info;
  }

  tooltipPageRankInfo(pageRankInfo: string): string {

    let info = '';

    switch (pageRankInfo) {
      case PageRankInfo.HIGH_IVR:
        info = 'The invalid view rate is too high. Please check your anti-bot protection.';
        break;
      case PageRankInfo.HIGH_CTR:
        info = 'The click-through rate is too high. Please check your anti-bot protection.';
        break;
      case PageRankInfo.LOW_CTR:
        info = 'The click-through rate is too low. Please try placing ad units in a more visible places. You can also check if you are using the most popular ad unit sizes.';
        break;
      case PageRankInfo.POOR_TRAFFIC:
        info = 'Poor traffic. Please check your anti-bot protection.';
        break;
      case PageRankInfo.POOR_CONTENT:
        info = 'Please make sure to have quality content on your site. We don’t allow sites that have no other content than ads.';
        break;
      case PageRankInfo.SUSPICIOUS_DOMAIN:
        info = 'We don’t allow newly created domains and domains that are not present in public indexes.';
        break;
      case PageRankInfo.NOT_WORKING:
        info = 'The site is not responding or reports an error.';
        break;
    }

    return info;
  }
}

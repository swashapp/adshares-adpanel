import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { environment } from 'environments/environment';
import { AdUnitSize, Site, SiteLanguage, SitesTotals } from 'models/site.model';
import { TargetingOption } from 'models/targeting-option.model';
import { parseTargetingForBackend } from 'common/components/targeting/targeting.helpers';
import { TimespanFilter } from 'models/chart/chart-filter-settings.model';

@Injectable()
export class PublisherService {

  constructor(private http: HttpClient) {
  }

  getSites(timespan: TimespanFilter): Observable<Site[]> {
    return this.http.get<Site[]>(`${environment.apiUrl}/sites`);
  }

  getLanguagesList(): Observable<SiteLanguage[]> {
    return this.http.get<SiteLanguage[]>(`${environment.apiUrl}/options/sites/languages`);
  }

  getSitesTotals(timespan: TimespanFilter): Observable<SitesTotals> {
    return this.http.get<SitesTotals>(`${environment.apiUrl}/sites/count`);
  }

  getSite(id: number): Observable<Site> {
    return this.http.get<Site>(`${environment.apiUrl}/sites/${id}`);
  }

  saveSite(site: Site): Observable<Site> {
    if (site.filtering) {
      const targetingObject = parseTargetingForBackend(site.filtering);
      Object.assign(site, {filtering: targetingObject});
    }
    return this.http.post<Site>(`${environment.apiUrl}/sites`, {site});
  }

  deleteSite(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${environment.apiUrl}/sites/${id}`);
  }

  updateSiteData(id: number, site: Site): Observable<Site> {
    return this.http.patch<Site>(`${environment.apiUrl}/sites/${id}`, {site});
  }

  updateSiteFiltering(id: number, site: Site): Observable<Site> {
    if (site.filtering) {
      const targetingObject = parseTargetingForBackend(site.filtering);

      Object.assign(site, {filtering: targetingObject});
    }
    return this.http.patch<Site>(`${environment.apiUrl}/sites/${id}`, {site});
  }

  getFilteringCriteria(): Observable<TargetingOption[]> {
    return this.http.get<TargetingOption[]>(`${environment.apiUrl}/options/sites/filtering`);
  }

  getAdUnitSizes(): Observable<AdUnitSize[]> {
    return this.http.get<AdUnitSize[]>(`${environment.apiUrl}/options/sites/zones`);
  }
}

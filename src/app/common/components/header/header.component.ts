import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Router } from '@angular/router';

import { MatDialog } from '@angular/material';

import { HandleSubscription } from '../../handle-subscription';
import { AppState } from '../../../models/app-state.model';
import { User } from '../../../models/user.model';
import { SetYourEarningsDialogComponent } from '../../../admin/dialogs/set-your-earnings-dialog/set-your-earnings-dialog.component';
import { userRolesEnum } from '../../../models/enum/user.enum';

import * as commonActions from '../../../store/common/common.actions';
import * as advertiserActions from '../../../store/advertiser/advertiser.actions';
import * as publisherActions from '../../../store/publisher/publisher.actions';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent extends HandleSubscription implements OnInit {
  currentBalanceAdst = 128.20;
  currentBalanceUSD = 1240.02;
  notificationsCount = 8;
  userDataState: Store<User>;
  activeUserType: number;

  userRolesEnum = userRolesEnum;
  notificationsBarEnabled = false;

  constructor(
    private store: Store<AppState>,
    private router: Router,
    private dialog: MatDialog
  ) {
    super(null);
  }

  ngOnInit() {
    console.log('userRolesEnum', this.userRolesEnum);
    console.log('activeUserType', this.activeUserType);
    const activeUserTypeSubscription = this.store.select('state', 'common', 'activeUserType')
      .subscribe(activeUserType => {
        this.activeUserType = activeUserType;
      });

    this.subscriptions.push(activeUserTypeSubscription);
    this.userDataState = this.store.select('state', 'user', 'data');
  }

  toggleNotificationsBar(status: boolean) {
    this.notificationsBarEnabled = status;
  }

  navigateToCreateNewAsset() {
    const moduleDir =  `/${userRolesEnum[this.activeUserType].toLowerCase()}`;
    const isUserAdvertiser = this.activeUserType === userRolesEnum.ADVERTISER;
    const assetDir = isUserAdvertiser ? 'create-campaign' : 'create-site';

    if (isUserAdvertiser) {
      this.store.dispatch(new advertiserActions.ClearLastEditedCampaign(''));
    } else {
      this.store.dispatch(new publisherActions.ClearLastEditedSite(''));
    }

    this.router.navigate(
      [ moduleDir, assetDir, 'basic-information'],
      { queryParams: { step: 1 } }
    );
  }

  openSetEarningsDialog() {
    this.dialog.open(SetYourEarningsDialogComponent);
  }

  setActiveUserType(userType) {
    this.store.dispatch(new commonActions.SetActiveUserType(userType));
  }
}

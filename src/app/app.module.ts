import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { StoreModule } from '@ngrx/store/';

import { AppRoutingModule } from './app-routing.module';
import { AppCommonModule } from './common/common.module';
import { AuthModule } from './auth/auth.module';
import { AdvertiserModule } from './advertiser/advertiser.module';
import { PublisherModule } from './publisher/publisher.module';
import { SettingsModule } from './settings/settings.module'
import { AppComponent } from './app.component';
import { environment } from '../environments/environment';

import { reducers } from './store/index';

const appModules = [
  AppCommonModule,
  AuthModule,
  AdvertiserModule,
  PublisherModule,
  SettingsModule
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    StoreModule.forRoot({ state: reducers }),
    !environment.production ? StoreDevtoolsModule.instrument() : [],
    ...appModules
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

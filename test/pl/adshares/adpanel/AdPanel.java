package pl.adshares.adpanel;

import pl.adshares.adpanel.enums.Properties;
import pl.adshares.adpanel.pages.*;
import pl.adshares.adpanel.pages.RegisterPage;
import pl.adshares.adpanel.pages.advertiser.AdvertiserMainPage;
import pl.adshares.adpanel.pages.publisher.*;
import pl.adshares.adpanel.setup.BrowserTestCase;
import pl.adshares.adpanel.tools.Maps;
import pl.adshares.adpanel.tools.Structure;
import pl.adshares.adpanel.tools.Xml;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Random;

public class AdPanel extends BrowserTestCase {

  private String url_panel = "http://localhost:8102/";
  private String url_mailhog = "http://localhost:8025/";
  private String url_mailcatcher = "http://mailcatcher.ads/";
  private String url_target = "http://localhost:8101/test-publisher/index.html";
  private String user = "user@e11.click";
  private String admin = "admin@e11.click";
  private String password = "12345678";

  private RegisterPage registerPage;
  private LoginPage loginPage;
  private DashboardPopup dashboardPopup;
  private HeaderBarPage headerBarPage;
  private Mailcatcher mailcatcher;
  private AdvertiserMainPage advertiserMainPage;
  private PublisherMainPage publisherMainPage;
  private PublisherNewSite publisherNewSite;
  private SiteAdditionalTargeting siteAdditionalTargeting;
  private SiteCreateAds siteCreateAds;
  private SiteSummary siteSummary;
  private PublisherEditSite publisherEditSite;

  private String loginAdService;
  private String passwordAdService;
  private String randomsEmail;

  public AdPanel() {
  }

  @BeforeTest
  public void setUp() {
    Maps.url();
    Maps.url_panel("url_panel", url_panel);
    Maps.url_mailcatcher("url_mailcatcher", url_mailcatcher);
    Maps.url_mailhog("url_mailhog", url_mailhog);
    Maps.url_target("url_target", url_target);

    Random random = new Random();
    int number = random.nextInt(1000);
    String name = "Campaign "+number;
    Maps.createCampaign();
    Maps.campaign_name("campaign_name",name);

    loginAdService = Xml.getValue(Structure.CONFIG_PROPERTIES, Properties.PROPERTY, Properties.EMAIL);
    passwordAdService = Xml.getValue(Structure.CONFIG_PROPERTIES, Properties.PROPERTY, Properties.PASSWORD);
  }
  @Test
  public void sleep() {
    try {
      Thread.sleep(10000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  @Test
  public void logInPopUpFirstPublisher() {
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpFirstPublisher();
  }
  @Test
  public void logInPopUpFirstAdvertiser(){
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpFirstAdvertiser();
  }
  @Test
  public void logInPopUpFirstAdvertiserPublisher(){
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpFirstAdvertiserPublisher();
  }
  @Test
  public void logInPopUpPublisher(){
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpPublisher();
  }
    @Test
  public void loginPopUpAdvertiser(){
      dashboardPopup = new DashboardPopup(driver);
      dashboardPopup.popUpAdvertiser();
  }
  @Test
  public void firstLogInPopUp() {
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.chooseAccountTypeAllTypes();
  }
  @Test
  private void logInRandomEmail() {
    loginPage = new LoginPage(driver);
    loginPage.RandomEmail(password);
    loginPage.logIn();
  }
//  private void loginUserEmail() throws InterruptedException {
//    loginPage = new LoginPage(driver);
//    loginPage.UserEmail(password);
//    loginPage.logIn();
//  }



  @Test
  public void logInRandomEmailRememberMe() {
    loginPage = new LoginPage(driver);
    loginPage.RandomEmail(password);
    loginPage.logInRememberMe();
  }
  @Test
  public void logInUserEmail() {
    loginPage = new LoginPage(driver);
    loginPage.UserEmail(user,password);
    loginPage.logIn();
  }
  @Test
  public void logInUserEmailRememberMe() {
    loginPage = new LoginPage(driver);
    loginPage.UserEmail(user,password);
    loginPage.logInRememberMe();
  }

  @Test
  public void logInChangingTheDashboardBack() {
    loginPage = new LoginPage(driver);
    loginPage.goToLoginChangingTheDashboardBack();
  }

  @Test
  public void logInChangeEmailNegative()  {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmailNegative();
  }

  @Test
  public void logInChangePasswordNegative() {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangePasswordNegative();
  }

  @Test
  public void logInFAQ() {
    loginPage = new LoginPage(driver);
    loginPage.gotologinFAQ();
  }

  @Test
  public void logOut() {
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
    loginPage = new LoginPage(driver);
    loginPage.pageLayoutValidation();
  }


  @Test
  public void Registration_RandomEmail() {                      System.out.println("---------- TS_1 - TC_1 ----------");
    logInRandomEmail();
  }
  @Test
  public void Registration_Error() {                            System.out.println("---------- TS_1 - TC_2 ----------");
    loginPage = new LoginPage(driver);
    loginPage.goToLoginRegistration();
  }
  @Test
  public void Registration_ForgotPassword() {                   System.out.println("---------- TS_1 - TC_3 ----------");
    registerPage = new RegisterPage(driver);
    registerPage.registerForgotPassword(Maps.getEmail("email"),"ADS11ads");
  }
  @Test
  public void logInPageObjectValidation() {                     System.out.println("---------- TS_2 - TC_1 ----------");
    loginPage = new LoginPage(driver);
    loginPage.loginRequiredEmailValidation();
    loginPage.loginInvalidEmailValidation();
    loginPage.loginPasswordValidation();
  }
  @Test
  public void logInPageCrossAccessValidation() {                System.out.println("---------- TS_2 - TC_2 ----------");
    loginPage = new LoginPage(driver);
    loginPage.wrongEmailCorrectPassword(passwordAdService);
    driver.navigate().refresh();
    loginPage.wrongPasswordCorrectEmail(loginAdService);
  }
  @Test
  public void logInWithRolePublisher() {                        System.out.println("---------- TS_2 - TC_3 ----------");
  }
  @Test
  public void logInWithRoleAdvertiser() {                       System.out.println("---------- TS_2 - TC_4 ----------");
  }
  @Test
  public void logInWithRoleAdvertiserPublisher() {              System.out.println("---------- TS_2 - TC_5 ----------");
  }
  @Test
  public void logInChangeEmail_1() {                          System.out.println("---------- TS_2 - TC_6.1 ----------");
    loginPage = new LoginPage(driver);
    Random random = new Random();
    int number = random.nextInt(1000000);
    String s = String.format("%06d", number)+"e";
//    Thread.sleep(1000);
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal@"+s+".click", "test@"+s+".click");
    System.out.println("1/7. OK");
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
    System.out.println("2/7 - logOut");
    mailcatcher = new Mailcatcher(driver);
    mailcatcher.mailcatcherEmail();
    System.out.println("3/7 - Mailcatcher");
    // TODO: 05.10.18 TC_6.1 Error - notifications, count, sites ?XDEBUG_SESSION_START=PHPSTORM
    // TODO: 09.10.18 TC_6.1 Error -  Login require, Last request required logged-in user.
//    Robot robo = new Robot();
//    robo.keyPress(KeyEvent.VK_CONTROL);
//    robo.keyPress(KeyEvent.VK_SHIFT);
//    robo.keyPress(KeyEvent.VK_I);
//    robo.keyRelease(KeyEvent.VK_CONTROL);
//    robo.keyRelease(KeyEvent.VK_SHIFT);
//    robo.keyRelease(KeyEvent.VK_I);


    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    loginPage = new LoginPage(driver);
    loginPage.adshDialogClose();

    System.out.println("4z8 - logOut");
    headerBarPage.logOut();
    System.out.println("5z7 - logOut");
    mailcatcher.mailcatcherEmail();
    System.out.println("6z7 - Mailcatcher");
//    loginPage.logIn();
    loginPage.loginSignIn("michal@"+s+".click", "12345678");
    System.out.println("7z7 - loginSignIn");
    headerBarPage.logOut();
    System.out.println("8z7 - logOut");
  }
  @Test
  public void logInChangeEmail_2() {                          System.out.println("---------- TS_2 - TC_6.2 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal.michal@e11.click", "test@e11.click");
  }
  @Test
  public void loginChangeEmail_3() {                          System.out.println("---------- TS_2 - TC_6.3 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal@e11.click.pl", "test@e11.click");
  }
  @Test
  public void logInChangeEmail_4() {                          System.out.println("---------- TS_2 - TC_6.4 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("MiChAl@e11.click", "test@e11.click");
  }
  @Test
  public void logInChangeEmail_5() {                          System.out.println("---------- TS_2 - TC_6.5 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal123@e11.click", "test@e11.click");
  }
  @Test
  public void logInChangeEmail_6() {                          System.out.println("---------- TS_2 - TC_6.6 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal_123@e11.click", "test@e11.click");
  }
  @Test
  public void logInChangeEmail_7() {                          System.out.println("---------- TS_2 - TC_6.7 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("111michal@e11.click", "test@e11.click");
  }
  @Test
  public void logInChangePassword() {                           System.out.println("---------- TS_2 - TC_7 ----------");
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangePassword();
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
    loginPage.pageLayoutValidation();
    loginPage.loginSignIn(Maps.getEmail("email"), Maps.getPassword("password"));
    System.out.println("LoginEmail:  "+ Maps.getEmail("email"));
    System.out.println("NewPassword: "+ Maps.getPassword("password"));
  }
  @Test
  public void logOutTest() {                                    System.out.println("---------- TS_2 - TC_8 ----------");
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
  }
  @Test
  public void logInSecondTab() {                                System.out.println("---------- TS_2 - TC_9 ----------");
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab();
  }
  @Test
  public void logInSecondTab2() {                                System.out.println("---------- TS_2 TC_10 ----------");
    // TODO: 27.09.18 TC_10 Error - remember me do not work
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab();
    loginPage.loginSecondTab2();
  }
  @Test
  public void logInSessionHoles() {                            System.out.println("---------- TS_2 - TC_11 ----------");
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab3();
  }
  @Test
  public void logInSecondTab4() {                              System.out.println("---------- TS_2 - TC_12 ----------");
    // TODO: 27.09.18 TC_12 Error - session expiration not work - setting in file
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab4();
  }
  @Test
  // TODO: 27.09.18 TC_13 Error - admin account in the database
  public void logInAdminEmail() {                              System.out.println("---------- TS_2 - TC_13 ----------");
    loginPage = new LoginPage(driver);
    loginPage.AdminEmail(admin,password);
  }
  @Test
  public void logInFail() {                                    System.out.println("---------- TS_2 - TC_14 ----------");
    loginPage = new LoginPage(driver);
    loginPage.logInFail("fail@e11.click", "failfail");
  }
  @Test
  public void logInAdvertiser() {                              System.out.println("---------- TS_2 - TC_15 ----------");
  }
  @Test
  public void logInOutAdvertiser() {                           System.out.println("---------- TS_2 - TC_16 ----------");
  }

  @Test
  public void basicInformationSaveContinue() {                  System.out.println("---------- TS_3 - TC_1 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    advertiserMainPage.createNewCampaign();
    advertiserMainPage.basicInformation();
    advertiserMainPage.basicInformationSaveContinue();
  }
  @Test
  public void basicInformationBackToDashboard() {               System.out.println("---------- TS_3 - TC_2 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    advertiserMainPage.createNewCampaign();
    advertiserMainPage.basicInformation();
    advertiserMainPage.basicInformationBackToDashboard();
  }
  @Test
  public void additionalTargetingsaveSaveContinue() {           System.out.println("---------- TS_3 - TC_3 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    basicInformationSaveContinue();
    advertiserMainPage.additionalTargeting();
    advertiserMainPage.additionalTargetingsaveSaveContinue();
  }
  @Test
  public void additionalTargetingsaveSaveDraft() {             System.out.println("---------- TS_3 - TC_4 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    basicInformationSaveContinue();
    advertiserMainPage.additionalTargeting();
    advertiserMainPage.additionalTargetingsaveSaveDraft();
  }
  @Test
  public void additionalTargetingsaveBack() {                  System.out.println("---------- TS_3 - TC_5 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    basicInformationSaveContinue();
    advertiserMainPage.additionalTargeting();
    advertiserMainPage.additionalTargetingsaveBack();
  }
  @Test
  public void createAdsSaveContinue() {                        System.out.println("---------- TS_3 - TC_6 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    additionalTargetingsaveSaveContinue();
    advertiserMainPage.createAds();
    advertiserMainPage.createAdsSaveContinue();
    // TODO: 11.10.18 error - add jpg
  }
  @Test
  public void createAdsSaveDraft() {                           System.out.println("---------- TS_3 - TC_7 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    additionalTargetingsaveSaveContinue();
    advertiserMainPage.createAds();
    advertiserMainPage.createAdsSaveAsDraft();
  }
  @Test
  public void createAdsBack() {                                System.out.println("---------- TS_3 - TC_8 ----------");
    additionalTargetingsaveSaveContinue();
    advertiserMainPage.createAds();
    advertiserMainPage.createAdsBack();
  }
  @Test
  public void summarySaveContinue() {                          System.out.println("---------- TS_3 - TC_9 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    createAdsSaveContinue();
    advertiserMainPage.summary();
    advertiserMainPage.summarySaveContinue();
  }
  @Test
  public void summarySaveDraft() {                             System.out.println("---------- TS_3 - TC_10 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    createAdsSaveContinue();
    advertiserMainPage.summary();
    advertiserMainPage.summarySaveAsDraft();
  }
  @Test
  public void summaryBack() {                                  System.out.println("---------- TS_3 - TC_11 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    createAdsSaveContinue();
    advertiserMainPage.summary();
    advertiserMainPage.summaryBack();
  }
  @Test
  public void viewTheCampaign() {                              System.out.println("---------- TS_3 - TC_12 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    summarySaveContinue();
    advertiserMainPage.viewTheCampaign();
    // TODO: 11.10.18 error - no position - F5 + sleep
  }
  @Test
  public void editBasicInformation() {                         System.out.println("---------- TS_3 - TC_13 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    viewTheCampaign();
    advertiserMainPage.editTheCampaign();
    advertiserMainPage.editBasicInformation();
    // TODO: 12.10.18  
  }
  @Test
  public void editAdditionalTargeting() {                      System.out.println("---------- TS_3 - TC_14 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    viewTheCampaign();
    advertiserMainPage.editTheCampaign();
    advertiserMainPage.editAdditionalTargeting();
    // TODO: 12.10.18  
  }
  @Test
  public void editAds() {                                      System.out.println("---------- TS_3 - TC_15 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    viewTheCampaign();
    advertiserMainPage.editTheCampaign();
    advertiserMainPage.editAds();
    // TODO: 12.10.18  
  }



  @Test
  public void publisherBasicInformationSaveContinue() {         System.out.println("---------- TS_4 - TC_1 ----------");
    publisherMainPage = new PublisherMainPage(driver);
    publisherMainPage.AddNewSite();
    publisherNewSite = new PublisherNewSite(driver);
    publisherNewSite.basicInformation(Maps.get_url_target("url_target"));
    publisherNewSite.basicInformationSaveContinue();
  }
  @Test
  public void publisherBasicInformationBackToDashboard() {      System.out.println("---------- TS_4 - TC_2 ----------");
    publisherMainPage = new PublisherMainPage(driver);
    publisherMainPage.AddNewSite();
    publisherNewSite = new PublisherNewSite(driver);
    publisherNewSite.basicInformation(Maps.get_url_target("url_target"));
    publisherNewSite.basicInformationBackToDashboard();
  }
  @Test
  public void publisherAdditionalTargetingsaveSaveContinue() {  System.out.println("---------- TS_4 - TC_3 ----------");
    publisherMainPage = new PublisherMainPage(driver);
    publisherBasicInformationSaveContinue();
    siteAdditionalTargeting = new SiteAdditionalTargeting(driver);
    siteAdditionalTargeting.publisherRequiresCreativeType();
    siteAdditionalTargeting.publisherRequiresLanguage();
    siteAdditionalTargeting.publisherRequiresScreen();
    siteAdditionalTargeting.publisherRequiresJsSupport();
    siteAdditionalTargeting.additionalTargetingsaveSaveContinue();
  }
  @Test
  public void publisherAdditionalTargetingsaveSaveDraft() {     System.out.println("---------- TS_4 - TC_4 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    publisherBasicInformationSaveContinue();
    siteAdditionalTargeting = new SiteAdditionalTargeting(driver);
    siteAdditionalTargeting.publisherRequiresCreativeType();
    siteAdditionalTargeting.publisherRequiresLanguage();
    siteAdditionalTargeting.publisherRequiresScreen();
    siteAdditionalTargeting.publisherRequiresJsSupport();
    siteAdditionalTargeting.additionalTargetingsaveSaveDraft();
  }
  @Test
  public void publisherAdditionalTargetingsaveBack() {           System.out.println("---------- TS_4 - TC_5 ----------");
    advertiserMainPage = new AdvertiserMainPage(driver);
    publisherBasicInformationSaveContinue();
    siteAdditionalTargeting = new SiteAdditionalTargeting(driver);
    siteAdditionalTargeting.publisherRequiresCreativeType();
    siteAdditionalTargeting.publisherRequiresLanguage();
    siteAdditionalTargeting.publisherRequiresScreen();
    siteAdditionalTargeting.publisherRequiresJsSupport();
    siteAdditionalTargeting.additionalTargetingsaveBack();
  }
  // TODO: 16.10.18  SaveContinue SaveDraft
  @Test
  public void publisherCreateAdUnitsSaveContinue()          {  System.out.println("---------- TS_4 - TC_6 ----------");
    siteAdditionalTargeting = new SiteAdditionalTargeting(driver);
    publisherAdditionalTargetingsaveSaveContinue();
    siteCreateAds = new SiteCreateAds(driver);
    siteCreateAds.createNewAdUnit();
    siteCreateAds.adUnitHtml();
    siteCreateAds.createAdUnitsSaveContinue();
  }
  @Test
  public void publisherCreateAdUnitsSaveDraft() {              System.out.println("---------- TS_4 - TC_7 ----------");
    siteAdditionalTargeting = new SiteAdditionalTargeting(driver);
    publisherAdditionalTargetingsaveSaveContinue();
    siteCreateAds = new SiteCreateAds(driver);
    siteCreateAds.createNewAdUnit();
    siteCreateAds.adUnitImage();
    siteCreateAds.createAdUnitsSaveAsDraft();
  }
  @Test
  public void publisherCreateAdUnitsBack() {                   System.out.println("---------- TS_4 - TC_8 ----------");
    siteAdditionalTargeting = new SiteAdditionalTargeting(driver);
    publisherAdditionalTargetingsaveSaveContinue();
    siteCreateAds = new SiteCreateAds(driver);
    siteCreateAds.createNewAdUnit();
    siteCreateAds.adUnitImage();
    siteCreateAds.createAdUnitsBack();
  }
  @Test
  public void publisherSummaryPublishSite() {                  System.out.println("---------- TS_4 - TC_9 ----------");
    siteCreateAds = new SiteCreateAds(driver);
    publisherCreateAdUnitsSaveContinue();
    siteSummary = new SiteSummary(driver);
    siteSummary.summaryPublishSite();
  }
  @Test
  public void publisherSummarySaveDraft() {                    System.out.println("---------- TS_4 - TC_10 ----------");
    siteCreateAds = new SiteCreateAds(driver);
    publisherCreateAdUnitsSaveContinue();
    siteSummary = new SiteSummary(driver);
    siteSummary.summarySaveDraft();
  }
  @Test
  public void publisherSummaryBack() {                         System.out.println("---------- TS_4 - TC_11 ----------");
    siteCreateAds = new SiteCreateAds(driver);
    publisherCreateAdUnitsSaveContinue();
    siteSummary = new SiteSummary(driver);
    siteSummary.summaryBack();
  }
  @Test
  public void publisherViewSite() {                            System.out.println("---------- TS_4 - TC_12 ----------");
    siteSummary = new SiteSummary(driver);
    publisherSummaryPublishSite();
    publisherEditSite = new PublisherEditSite(driver);
    publisherEditSite.viewSite();
  }
  @Test
  public void publisherEditBasicInformation() {                System.out.println("---------- TS_4 - TC_13 ----------");
    siteSummary = new SiteSummary(driver);
    publisherSummaryPublishSite();
    publisherEditSite = new PublisherEditSite(driver);
    publisherEditSite.viewSite();
    publisherEditSite.editBasicInformation();
  }
  @Test
  public void publisherEditAdditionalTargeting() {             System.out.println("---------- TS_4 - TC_14 ----------");
    siteSummary = new SiteSummary(driver);
    publisherSummaryPublishSite();
    publisherEditSite = new PublisherEditSite(driver);
    publisherEditSite.viewSite();
    publisherEditSite.editAdditionalTargeting();
  }
  @Test
  public void publisherEditAds() {                             System.out.println("---------- TS_3 - TC_15 ----------");
    siteSummary = new SiteSummary(driver);
    publisherSummaryPublishSite();
    publisherEditSite = new PublisherEditSite(driver);
    publisherEditSite.viewSite();
    publisherEditSite.editAds();
  }
  @Test
  public void publisherBasicInformationError() {               System.out.println("---------- TS_4 - TC_16 ----------");
    publisherMainPage = new PublisherMainPage(driver);
    publisherMainPage.AddNewSite();
    publisherNewSite = new PublisherNewSite(driver);
    publisherNewSite.basicInformationError("");
  }
}

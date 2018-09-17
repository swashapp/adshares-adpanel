package pl.adshares.adpanel;

import pl.adshares.adpanel.enums.Properties;
import pl.adshares.adpanel.pages.*;
import pl.adshares.adpanel.setup.BrowserTestCase;
import pl.adshares.adpanel.tools.RandomPage;
import pl.adshares.adpanel.tools.Structure;
import pl.adshares.adpanel.tools.Xml;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

public class LoginTestCase extends BrowserTestCase {

  private LoginPage loginPage;
  private PageOpera pageOpera;
  private Mailcatcher mailcatcher;
  private DashboardPopup dashboardPopup;
  private HeaderBarPage headerBarPage;
  private String loginAdService;
  private String passwordAdService;
  private String randomsEmail;

  @BeforeTest
  public void setUp() {

    loginAdService = Xml.getValue(Structure.CONFIG_PROPERTIES, Properties.PROPERTY, Properties.EMAIL);
    passwordAdService = Xml.getValue(Structure.CONFIG_PROPERTIES, Properties.PROPERTY, Properties.PASSWORD);
  }

  @Test
  public void loginTest() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.pageLayoutValidation();
    loginPage.loginSignIn(loginAdService, passwordAdService);
    System.out.println("1. loginTest");
  }
  @Test
  public void loginAsdf() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.pageLayoutValidation();
    loginPage.loginSignIn("asdf", "asdf");
    loginPage.loginSignInError();
  }
  @Test
  public void loginAdmin() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.pageLayoutValidation();
    loginPage.loginSignIn("admin@e11.click", "adminadmin");
    System.out.println("1. loginAdmin");
  }

  @Test
  public void loginPopUpFirstPublisher() {
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpFirstPublisher();
  }
  @Test
  public void loginPopUpFirstAdvertiser(){
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpFirstAdvertiser();
  }
  @Test
  public void loginPopUpFirstAdvertiserPublisher(){
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpFirstAdvertiserPublisher();
  }

  @Test
  public void loginPopUpPublisher(){
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.popUpPublisher();
  }
    @Test
  public void loginPopUpAdvertiser(){
      dashboardPopup = new DashboardPopup(driver);
      dashboardPopup.popUpAdvertiser();
  }

  @Test
  public void firstLoginPopUp() {
    dashboardPopup = new DashboardPopup(driver);
    dashboardPopup.chooseAccountTypeAllTypes();
  }

  @Test
  public void loginPageObjectValidation() {
    loginPage = new LoginPage(driver);
    loginPage.loginRequiredEmailValidation();
    loginPage.loginInvalidEmailValidation();
    loginPage.loginPasswordValidation();
    System.out.println("<-- Login Page Validation passed -->");
  }

  @Test
  public void loginPageCrossAccessValidation() {
    loginPage = new LoginPage(driver);
    loginPage.wrongEmailCorrectPassword(passwordAdService);
    System.out.println("<-- Login Page: wrong Email & Correct Password scenerio passed -->");
    driver.navigate().refresh();
    loginPage.wrongPasswordCorrectEmail(loginAdService);
    System.out.println("<-- Login Page: Correct Email & wrong Password scenerio passed -->");
  }

  @Test
  public void loginRegisterError() {
    loginPage = new LoginPage(driver);
    loginPage.goToLoginRegistration();
  }

  @Test
  public void loginRegisterRandom() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.goToLoginRegistrRandom("12345678");
    loginPage .logIn();
  }

  @Test
  public void loginRegisterRememberMe() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.goToLoginRegistrRandom("12345678");
    loginPage .logInRememberMe();
  }

  @Test
  public void logOutTest() {
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
    loginPage = new LoginPage(driver);
    loginPage.pageLayoutValidation();

  }

  @Test
  public void loginChangingTheDashboardBack() {
    loginPage = new LoginPage(driver);
    loginPage.goToLoginChangingTheDashboardBack();
  }

  @Test
    public void loginChangeEmail_1() throws InterruptedException {
    loginPage = new LoginPage(driver);
    Random random = new Random();
    int number = random.nextInt(1000000);
    String s = String.format("%06d", number)+"e";
    loginPage.gotologinChangeEmail("michal@"+s+".click", "test@"+s+".click");
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
    loginPage.pageLayoutValidation();
    loginPage.loginSignIn("michal@"+s+".click", "12345678");
  }

  @Test
    public void loginChangeEmail_2() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal.michal@e11.click", "test@e11.click");
  }

  @Test
  public void loginChangeEmail_3() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal@e11.click.pl", "test@e11.click");
  }

  @Test
  public void loginChangeEmail_4() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("MiChAl@e11.click", "test@e11.click");
  }

  @Test
  public void loginChangeEmail_5() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal123@e11.click", "test@e11.click");
  }

  @Test
  public void loginChangeEmail_6() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("michal_123@e11.click", "test@e11.click");
  }

  @Test
  public void loginChangeEmail_7() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmail("111michal@e11.click", "test@e11.click");
  }

  @Test
  public void loginChangeEmailNegative()  {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangeEmailNegative();
  }

  @Test
  public void loginChangePassword() throws InterruptedException {
    loginPage = new LoginPage(driver);
    Random random = new Random();
    int number = random.nextInt(100000000);
    String NewPassword = String.format("%08d", number);
    loginPage.gotologinChangePassword(NewPassword);
    headerBarPage = new HeaderBarPage(driver);
    headerBarPage.logOut();
    loginPage.pageLayoutValidation();
    loginPage.loginSignIn((String) RandomPage.getFromStore("user_email"), NewPassword);
    System.out.println("LoginEmail:  "+RandomPage.getFromStore("user_email"));
    System.out.println("NewPassword: "+NewPassword);
    Thread.sleep(10000);
  }

  @Test
  public void loginChangePasswordNegative() {
    loginPage = new LoginPage(driver);
    loginPage.gotologinChangePasswordNegative();
  }

  @Test
  public void loginFAQ() throws InterruptedException {
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
  public void loginSecondTab() {
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab();
  }

  @Test
  public void loginSecondTab2() {
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab();
    loginPage.loginSecondTab2();
  }

  @Test
  public void loginSecondTab3() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab3();
  }

  @Test
  public void loginSecondTab4() throws InterruptedException {
    loginPage = new LoginPage(driver);
    loginPage.loginSecondTab4();
  }


}

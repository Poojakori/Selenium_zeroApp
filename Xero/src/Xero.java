import com.company.ReusableMethods;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Parameter;

public class Xero extends ReusableMethods {
    @BeforeClass
    void Report(){
        InitializeReport();

    }
    @BeforeMethod
    @Parameters("driverName")
    void initialize(String driverName) throws InterruptedException {
        InitializeDriver("firefox");
        Thread.sleep(10000);

    }

    void login() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC1_Login.xls", "Sheet1");
        //driver.get("https://www.xero.com/us/signup/");
        driver.get(data[0][1]);
        WebElement uName = driver.findElement(By.xpath("//input[@id='email']"));
        enterText(uName, data[1][1], "UserName");
        WebElement pWord = driver.findElement(By.xpath("//input[@id='password']"));
        enterText(pWord, data[2][1], "Password");
        WebElement loginBtn = driver.findElement(By.xpath("//button[@id='submitButton']"));
        clickElement(loginBtn, " Login Button");
        Thread.sleep(5000);
    }
    @Test
    void TC1_XeroLogin() throws IOException, InterruptedException {
        logger = reports.startTest("TC1_Login");
        login();
        Assert.assertEquals("Xero | Dashboard | dfg",driver.getTitle());
        logger.log(LogStatus.PASS, "Login is successful.");
    }

    @Test
    void TC2_loginError() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC2_loginError.xls", "Sheet1");
        logger = reports.startTest("TC2_loginError");
        driver.get(data[0][1]);
        WebElement uName = driver.findElement(By.xpath("//input[@id='email']"));
        enterText(uName, data[1][1], "UserName");
        WebElement pWord = driver.findElement(By.xpath("//input[@id='password']"));
        enterText(pWord, data[2][1], "Password");
        WebElement loginBtn = driver.findElement(By.xpath("//button[@id='submitButton']"));
        clickElement(loginBtn, " Login Button");
        Thread.sleep(5000);
        Boolean check=driver.findElement(By.xpath("//p[contains(text(),'Your email or password is incorrect')]")).isDisplayed();
        checkPresence(check,"Error message Your email or password is incorrect ");
    }

    @Test
    void TC3_ForgotPwd() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC3_ForgotPwd.xls", "Sheet1");
        logger = reports.startTest("TC3_ForgotPwd");
        driver.get(data[0][1]);
        WebElement forgotPwd = driver.findElement(By.xpath("//a[@class='forgot-password-advert']"));
        clickElement(forgotPwd, " Forgot your password Button");
        Thread.sleep(5000);
        WebElement uName = driver.findElement(By.xpath("//input[@id='UserName']"));
        enterText(uName, data[1][1], "UserName");
        WebElement send = driver.findElement(By.xpath("//span[@class='text']"));
        clickElement(send, " Send Link your password Button");
        Thread.sleep(5000);
        Boolean check=driver.findElement(By.xpath("//strong[contains(text(),'ekpoojakori@gmail.com')]")).isDisplayed();
        checkPresence(check,"email sent message ");
    }

    @Test
    void TC4_signUp() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC4_signUp.xls", "Sheet1");
        logger = reports.startTest("TC4_signUp");
        driver.get(data[0][1]);
        Thread.sleep(5000);
        WebElement freeTrial = driver.findElement(By.xpath("//a[@class='btn btn-primary global-ceiling-bar-btn']"));
        clickElement(freeTrial, " Free Trial Button ");
        WebElement firstName = driver.findElement(By.xpath("//input[@name='FirstName']"));
        enterText(firstName, data[1][1], "First Name");
        WebElement lastName = driver.findElement(By.xpath("//input[@name='LastName']"));
        enterText(lastName, data[2][1], "last Name");
        WebElement email = driver.findElement(By.xpath("//input[@name='EmailAddress']"));
        enterText(email, data[3][1], "Email");
        WebElement phoneNum = driver.findElement(By.xpath("//input[@name='PhoneNumber']"));
        enterText(phoneNum, data[4][1], "Phone Number");
        Thread.sleep(5000);
        Select country = new Select(driver.findElement(By.xpath("//select[@name='LocationCode']")));
        country.selectByVisibleText(data[5][1]);
        Thread.sleep(5000);
        logger.log(LogStatus.INFO, "Country Tuvalu is Selected");
        WebElement iframe= driver.findElement(By.xpath("//iframe[@role='presentation']"));
        switchFrame(iframe,"iframe");
        Thread.sleep(5000);
        //body[@class='xero is-live-mode']
        WebElement notRobot = driver.findElement(By.xpath("//div[@class='recaptcha-checkbox-border']"));
        checkBox(notRobot, " I'm not robot check box ");
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        Thread.sleep(20000);
        WebElement terms = driver.findElement(By.name("TermsAccepted"));
        checkBox(terms, " Terms check box ");
        Thread.sleep(20000);
        WebElement getStarted = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
        clickElement(getStarted, " Get Started Button ");
        Boolean check=driver.findElement(By.xpath("//a[@class='btn btn-primary']")).isDisplayed();
        checkPresence(check,"email sent message ");
    }
    @Test
    void TC5_signUpError() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC4_signUp.xls", "Sheet1");
        logger = reports.startTest("TC5_signUpError");
        driver.get(data[0][1]);
        Thread.sleep(5000);
        WebElement freeTrial = driver.findElement(By.xpath("//a[@class='btn btn-primary global-ceiling-bar-btn']"));
        clickElement(freeTrial, " Free Trial Button ");
        Thread.sleep(3000);
        WebElement getStarted = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
        clickElement(getStarted, " Get Started Button ");
        Thread.sleep(3000);
        Boolean check=driver.findElement(By.xpath("//span[@id='signup-form-error-message-1']")).isDisplayed();
        checkPresence(check,"First name,Last name,Email address, Phone number can't be empty error message ");
        Thread.sleep(3000);

        driver.navigate().refresh();
        WebElement firstName = driver.findElement(By.xpath("//input[@name='FirstName']"));
        enterText(firstName, data[1][1], "First Name");
        WebElement lastName = driver.findElement(By.xpath("//input[@name='LastName']"));
        enterText(lastName, data[2][1], "last Name");
        WebElement email = driver.findElement(By.xpath("//input[@name='EmailAddress']"));
        enterText(email, "lkjhgf", "Email");
        WebElement phoneNum = driver.findElement(By.xpath("//input[@name='PhoneNumber']"));
        enterText(phoneNum, data[4][1], "Phone Number");
        Thread.sleep(5000);
        Select country = new Select(driver.findElement(By.xpath("//select[@name='LocationCode']")));
        country.selectByVisibleText(data[5][1]);
        Thread.sleep(5000);
        logger.log(LogStatus.INFO, "Country Tuvalu is Selected");
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        WebElement terms = driver.findElement(By.name("TermsAccepted"));
        checkBox(terms, " Terms check box ");
        Thread.sleep(20000);
        clickElement(getStarted, " Get Started Button ");
        Boolean check1=driver.findElement(By.xpath("//span[@id='signup-form-error-message-3']")).isDisplayed();
        checkPresence(check1,"email is invalid message ");

        driver.navigate().refresh();
        enterText(firstName, data[1][1], "First Name");
        enterText(lastName, data[2][1], "last Name");
        enterText(email, data[3][1], "Email");
        enterText(phoneNum, data[4][1], "Phone Number");
        Thread.sleep(5000);
        country.selectByVisibleText(data[5][1]);
        Thread.sleep(5000);
        logger.log(LogStatus.INFO, "Country Tuvalu is Selected");
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        clickElement(getStarted, " Get Started Button ");
        Thread.sleep(5000);
        Boolean check3=driver.findElement(By.xpath("//label[@class='form-label text']")).isDisplayed();
        checkPresence(check1,"Terms and policy text box highlight ");
    }
    @Test
    void TC6_clickLinks() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC6_clickLinks.xls", "Sheet1");
        logger = reports.startTest("TC6_clickLinks");
        driver.get(data[0][1]);
        Thread.sleep(5000);
        WebElement freeTrial = driver.findElement(By.xpath("//a[@class='btn btn-primary global-ceiling-bar-btn']"));
        clickElement(freeTrial, " Free Trial Button ");
        WebElement terms = driver.findElement(By.xpath("//a[contains(text(),'terms')]"));
        clickElement(terms, " terms link ");
        String winHandleBefore = driver.getWindowHandle();
        switchWindaow();
            Boolean check=driver.findElement(By.xpath("//a[@class='btn btn-tertiary-alt global-ceiling-bar-btn']")).isDisplayed();
        checkPresence(check,"Terms page ");
        driver.close();
        driver.switchTo().window(winHandleBefore);
        Thread.sleep(5000);
        WebElement privacy = driver.findElement(By.xpath("//a[contains(text(),'privacy')]"));
        clickElement(privacy, " privacy ");
        String winHandleBefore1 = driver.getWindowHandle();
        switchWindaow();
        Boolean check1=driver.findElement(By.xpath("//a[@class='btn btn-tertiary-alt global-ceiling-bar-btn']")).isDisplayed();
        checkPresence(check1,"Privacy page ");
        driver.close();
        driver.switchTo().window(winHandleBefore1);
    }
    @Test
    void TC7_clickAccLink() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC7_clickAccLink.xls", "Sheet1");
        logger = reports.startTest("TC7_clickAccLink");
        driver.get(data[0][1]);
        Thread.sleep(5000);
        WebElement freeTrial = driver.findElement(By.xpath("//a[@class='btn btn-primary global-ceiling-bar-btn']"));
        clickElement(freeTrial, " Free Trial Button ");
        WebElement acc = driver.findElement(By.xpath("//a[contains(text(),'accountant or bookkeeper')]"));
        clickElement(acc, " Accountant or bookkeeper link ");
        String winHandleBefore = driver.getWindowHandle();
        switchWindaow();
        Boolean check=driver.findElement(By.xpath("//h2[contains(text(),'Xero partner program signup form')]")).isDisplayed();
        checkPresence(check,"Terms page ");
    }

    @Test
    void TC8_TestAllTabs() throws IOException, InterruptedException {
        logger = reports.startTest("TC8_TestAllTabs");
        login();
        WebElement dashboard = driver.findElement(By.xpath("//a[@class='xrh-focusable xrh-tab--body xrh-tab--body-is-selected']"));
        clickElement(dashboard, " Dashboard Button ");
        Thread.sleep(5000);
        Boolean check=driver.findElement(By.xpath("//div[@class='xdash-welcome__welcome--header___3B5Tm']")).isDisplayed();
        checkPresence(check,"Dashboard page ");

        WebElement Accounting = driver.findElement(By.xpath("//div[@class='xdash-welcome__welcome--header___3B5Tm']"));
        clickElement(Accounting, " Accounting Button ");
        Thread.sleep(5000);
        Boolean check1=driver.findElement(By.xpath("//a[contains(text(),'Bank accounts')]")).isDisplayed();
        checkPresence(check1,"Accounting drop down page ");

        WebElement contacts = driver.findElement(By.xpath("//button[contains(text(),'Contacts')]"));
        clickElement(contacts, " Contacts Button ");
        Thread.sleep(5000);
        Boolean check2=driver.findElement(By.xpath("//a[contains(text(),'All contacts')]")).isDisplayed();
        checkPresence(check2,"Contacts drop down page ");

          WebElement dfg = driver.findElement(By.xpath("//div[@class='xrh-appbutton--body']"));
        clickElement(dfg, " dfg Button ");
        Thread.sleep(5000);
        WebElement settings = driver.findElement(By.xpath("//a[contains(text(),'Settings')]"));
        clickElement(settings, " Settings Button ");
        Thread.sleep(5000);
        Boolean check3=driver.findElement(By.xpath("//span[contains(text(),'Organisation settings')]")).isDisplayed();
        checkPresence(check3,"Settings page ");

        WebElement add = driver.findElement(By.xpath("//li[1]//button[1]//div[1]"));
        clickElement(add, " Add Button ");
        Thread.sleep(5000);
        Boolean check4=driver.findElement(By.xpath("//div[@class='xrh-dropdown-layout xrh-addon--dropdown xrh-dropdown-is-open xrh-dropdown-is-opening xrh-dropdown-positionright']//a[@class='xrh-verticalmenuitem--body'][contains(text(),'Bill')]")).isDisplayed();
        checkPresence(check4,"Add page ");
        clickElement(dfg, " dfg Button ");
        Thread.sleep(5000);
        WebElement files = driver.findElement(By.xpath("//a[contains(text(),'Files')]"));
        clickElement(files, " Files Button ");
        Thread.sleep(5000);
        Boolean check5=driver.findElement(By.xpath("//span[@id='filelistpanel-1022_header_hd-textEl']")).isDisplayed();
        checkPresence(check5,"Files page ");
        WebElement notification = driver.findElement(By.xpath("//body[@id='ext-gen1018']/div[@id='header']/header[@class='xrh-header xrh-header-business xrh-header-wide']/div[@class='xrh-header--main']/ol[@class='xrh-addons xrh-header-background-color']/li[3]/button[1]/div[1]/*[1]"));
        clickElement(notification, " Notification Button ");
        Thread.sleep(5000);
        Boolean check6=driver.findElement(By.id("post_office_frame")).isDisplayed();
        checkPresence(check6,"Notifications page ");
        WebElement search = driver.findElement(By.xpath("//li[@class='xrh-addon xrh-addon-alwaysvisible']//*[@class='xrh-icon xrh-icon-svg']"));
        clickElement(search, " Search Button ");
        Thread.sleep(5000);
        Boolean check7=driver.findElement(By.id("GlobalSearchApp")).isDisplayed();
        checkPresence(check7,"Files page ");
        Thread.sleep(5000);
        WebElement help = driver.findElement(By.xpath("//body[@class='center xeroV2 bridge ext-chrome x-sandbox']/div[@id='header']/header[@class='xrh-header xrh-header-business xrh-header-wide']/div[@class='xrh-header--main']/ol[@class='xrh-addons xrh-header-background-color']/li[4]/button[1]/div[1]/*[1]"));
        clickElement(help, " Help Button ");
        Thread.sleep(5000);
        Boolean check8=driver.findElement(By.xpath("//input[@id='menu_help']")).isDisplayed();
        checkPresence(check8,"help page ");
    }

    @Test
    void TC9_logOut() throws IOException, InterruptedException {
        logger = reports.startTest("TC9_logOut");
        login();
        logout();
        logger.log(LogStatus.PASS, " successfully logged out. ");
    }

    private void logout() throws IOException, InterruptedException {
        WebElement profile = driver.findElement(By.xpath("//abbr[@class='xrh-avatar xrh-avatar-color-9']"));
        clickElement(profile, " Profile Button ");
        WebElement logout = driver.findElement(By.xpath("//div[@class='xrh-dropdown-layout xrh-addon--dropdown xrh-dropdown-is-open xrh-dropdown-is-opening xrh-dropdown-positionright']//a[@class='xrh-verticalmenuitem--body'][contains(text(),'Log out')]"));
        clickElement(logout, " Logout Button ");
        Thread.sleep(5000);
    }

    @Test
    void TC10_uploadImage() throws IOException, InterruptedException {
//        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC10_uploadImage.xls", "Sheet1");
        logger = reports.startTest("TC10_uploadImage");
        login();
        WebElement profile = driver.findElement(By.xpath("//abbr[@class='xrh-avatar xrh-avatar-color-9']"));
        clickElement(profile, " Profile Button ");
        WebElement editProfile = driver.findElement(By.xpath("//span[@class='xrh-verticalmenuitem--subheading']"));
        clickElement(editProfile, " Edit Profile Button ");
        Thread.sleep(10000);
        WebElement uploadImage = driver.findElement(By.xpath("//span[@id='button-1041-btnInnerEl']"));
        clickElement(uploadImage, " Upload Profile Button ");
        WebElement browse = driver.findElement(By.xpath("//input[@id='filefield-1174-button-fileInputEl']"));
        browse.sendKeys("C:\\Users\\ekpoo\\Desktop\\TekArch\\IMG_20190223_104136_0.jpg");
        WebElement upload = driver.findElement(By.xpath("//span[@id='button-1178-btnInnerEl']"));
        clickElement(upload, " Upload Button ");
        Thread.sleep(10000);
        logger.log(LogStatus.PASS,"Image uploaded successfully.");
    }

    @Test
    void TC11_addOrganisationTrail() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC11_addOrganisationTrail.xls", "Sheet1");
        logger = reports.startTest("TC11_addOrganisationTrail");
        login();
        Thread.sleep(5000);
        WebElement dfg = driver.findElement(By.xpath("//div[@class='xrh-appbutton--body']"));
        clickElement(dfg, " dfg Button ");
        Thread.sleep(5000);
        WebElement myXero = driver.findElement(By.xpath("//a[contains(text(),'My Xero')]"));
        clickElement(myXero, " MyXero Button ");
        Thread.sleep(5000);
        String winHandleBefore = driver.getWindowHandle();
        switchWindaow();
        Thread.sleep(5000);
        WebElement add = driver.findElement(By.xpath("//a[@id='ext-gen1042']"));
        clickElement(add, " ADD Organization Button ");
        Thread.sleep(3000);
        WebElement orgName = driver.findElement(By.xpath("//input[@class='xui-textinput--input xui-textinput--input-medium']"));
        enterText(orgName, data[0][1], "Organization Name");
        Thread.sleep(5000);
        WebElement place = driver.findElement(By.xpath("//button[@data-automationid='country-autocompleter--open-dropdown-button']"));
        clickElement(place,"Organization Place");
        WebElement place1 = driver.findElement(By.xpath("//li[@id='CNTRY/US']//span[@class='xui-pickitem--text']"));
        clickElement(place1,"Organization Place");

        Thread.sleep(10000);
//        WebElement time = driver.findElement(By.xpath("//input[@data-automationid='timezone-autocompleter--input']"));
//        enterText(time, data[2][1], "Organization Place");
//        Thread.sleep(5000);
//        WebElement currency = driver.findElement(By.xpath("//input[@data-automationid='currency-autocompleter--input']"));
//        enterText(currency, data[3][1], "Currency");
//        Thread.sleep(5000);
        WebElement what = driver.findElement(By.xpath("//input[@data-automationid='industry-autocompleter--input']"));
        enterText(what, data[4][1], "Industry");
        Thread.sleep(5000);
//        WebElement date = driver.findElement(By.xpath("//input[@data-automationid='financial-yearend-day--input']"));
//        enterText(date, data[5][1], "Date");
//        Thread.sleep(5000);
//        WebElement month = driver.findElement(By.xpath("//input[@data-automationid='financial-yearend-month--input']"));
//        enterText(month, data[6][1], "Month");
        Thread.sleep(5000);
        WebElement software = driver.findElement(By.xpath("//span[@class='xui-select--content xui-select--content-truncated']"));
        clickElement(software,"Accounting Software ");
        Thread.sleep(5000);
        WebElement software2 = driver.findElement(By.xpath("//span[contains(text(),'Wave')]"));
        clickElement(software2,"Accounting Software ");
        Thread.sleep(5000);
        WebElement startTrail = driver.findElement(By.xpath("//button[@class='xui-button xui-actions--primary xui-actions--button-spacing xui-button-main xui-button-medium']"));
        clickElement(startTrail,"Start trial button ");
        Thread.sleep(10000);
        logger.log(LogStatus.PASS,"Organization created successfully.");
    }

    @Test
    void TC12_addOrganisationPaid() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC11_addOrganisationTrail.xls", "Sheet1");
        logger = reports.startTest("TC11_addOrganisationTrail");
        login();
        Thread.sleep(5000);
        WebElement dfg = driver.findElement(By.xpath("//div[@class='xrh-appbutton--body']"));
        clickElement(dfg, " dfg Button ");
        Thread.sleep(5000);
        WebElement myXero = driver.findElement(By.xpath("//a[contains(text(),'My Xero')]"));
        clickElement(myXero, " MyXero Button ");
        Thread.sleep(5000);
        String winHandleBefore = driver.getWindowHandle();
        switchWindaow();
        Thread.sleep(5000);
        WebElement add = driver.findElement(By.xpath("//a[@id='ext-gen1042']"));
        clickElement(add, " ADD Organization Button ");
        Thread.sleep(30000);
        WebElement orgName = driver.findElement(By.xpath("//input[@class='xui-textinput--input xui-textinput--input-medium']"));
        enterText(orgName, data[0][1], "Organization Name");
        Thread.sleep(5000);
        WebElement place = driver.findElement(By.xpath("//button[@data-automationid='country-autocompleter--open-dropdown-button']"));
        clickElement(place,"Organization Place");
        WebElement place1 = driver.findElement(By.xpath("//li[@id='CNTRY/US']//span[@class='xui-pickitem--text']"));
        clickElement(place1,"Organization Place");
        Thread.sleep(5000);
        WebElement what = driver.findElement(By.xpath("//input[@data-automationid='industry-autocompleter--input']"));
        enterText(what, data[4][1], "Industry");
        Thread.sleep(5000);
        WebElement software = driver.findElement(By.xpath("//span[@class='xui-select--content xui-select--content-truncated']"));
        clickElement(software,"Accounting Software ");
        Thread.sleep(5000);
        WebElement software2 = driver.findElement(By.xpath("//span[contains(text(),'Wave')]"));
        clickElement(software2,"Accounting Software ");
        Thread.sleep(5000);
        WebElement paid = driver.findElement(By.xpath("//button[@class='xui-button xui-actions--secondary xui-button-standard xui-button-medium']"));
        clickElement(paid,"Pay now button ");
        Thread.sleep(10000);
        Boolean check8=driver.findElement(By.xpath("//img[@class='xrh-avatar']")).isDisplayed();
        checkPresence(check8,"help page ");
    }
    @Test
    void TC13_addOrganisationPaidStarter() throws IOException, InterruptedException {
        String[][] data = readXlData("C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Inputfiles\\Xero\\TC13_addOrganisationPaidStarter.xls", "Sheet1");
        logger = reports.startTest("TC13_addOrganisationPaidStarter");
        login();
        Thread.sleep(5000);
        WebElement dfg = driver.findElement(By.xpath("//div[@class='xrh-appbutton--body']"));
        clickElement(dfg, " dfg Button ");
        Thread.sleep(5000);
        WebElement myXero = driver.findElement(By.xpath("//a[contains(text(),'My Xero')]"));
        clickElement(myXero, " MyXero Button ");
        Thread.sleep(5000);
        String winHandleBefore = driver.getWindowHandle();
        switchWindaow();
        Thread.sleep(5000);
        WebElement add = driver.findElement(By.xpath("//a[@id='ext-gen1042']"));
        clickElement(add, " ADD Organization Button ");
        Thread.sleep(30000);
        WebElement orgName = driver.findElement(By.xpath("//input[@class='xui-textinput--input xui-textinput--input-medium']"));
        enterText(orgName, data[0][1], "Organization Name");
        Thread.sleep(5000);
        WebElement place = driver.findElement(By.xpath("//button[@data-automationid='country-autocompleter--open-dropdown-button']"));
        clickElement(place,"Organization Place");
        WebElement place1 = driver.findElement(By.xpath("//li[@id='CNTRY/US']//span[@class='xui-pickitem--text']"));
        clickElement(place1,"Organization Place");
        Thread.sleep(5000);
        WebElement what = driver.findElement(By.xpath("//input[@data-automationid='industry-autocompleter--input']"));
        enterText(what, data[4][1], "Industry");
        Thread.sleep(5000);
        WebElement software = driver.findElement(By.xpath("//span[@class='xui-select--content xui-select--content-truncated']"));
        clickElement(software,"Accounting Software ");
        Thread.sleep(5000);
        WebElement software2 = driver.findElement(By.xpath("//span[contains(text(),'Wave')]"));
        clickElement(software2,"Accounting Software ");
        Thread.sleep(5000);
        WebElement paid = driver.findElement(By.xpath("//button[@class='xui-button xui-actions--secondary xui-button-standard xui-button-medium']"));
        clickElement(paid,"Pay now button ");
        Thread.sleep(5000);

        switchWindaow();
        WebElement starter = driver.findElement(By.cssSelector("body.xui-body:nth-child(2) div.xui-page-width-large div.xui-u-flex.xui-u-flex-justify-center.buyflow-card-panel section.xui-panel.xui-panel-layout.xui-u-flex.xui-u-flex-vertical.buyflow-card.plancard-deselected-border:nth-child(1) div.xui-padding-top.xui-padding-left label.xui-styledcheckboxradio > div.xui-styledcheckboxradio--radio"));
        clickElement(starter,"Starter radio button ");
//        WebElement continueBill = driver.findElement(By.xpath("//button[@id='continueButton']"));
//        clickElement(continueBill,"Continue button ");
        Thread.sleep(5000);
        switchWindaow();
        WebElement addressBtn = driver.findElement(By.xpath("//button[@id='togglePostalAddress']"));
        clickElement(addressBtn,"Add Address button ");
        Thread.sleep(5000);
        WebElement street = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[1]/form[1]/div[5]/div[2]/div[1]/div[1]/textarea[1]"));
        enterText(street,data[8][1],"Street ");
        Thread.sleep(5000);
        WebElement town = driver.findElement(By.xpath("//input[@id='contactCity']"));
        enterText(town,data[9][1],"Town ");
        Thread.sleep(5000);
        WebElement state = driver.findElement(By.xpath("//input[@id='contactRegion']"));
        enterText(state,data[10][1],"State ");
        Thread.sleep(5000);
        WebElement postalCode = driver.findElement(By.xpath("//input[@id='contactPostalCode']"));
        enterText(postalCode,data[11][1],"Postal Code ");
        Thread.sleep(5000);
        WebElement pay = driver.findElement(By.xpath("//button[@id='stepAccountCreate']"));
        clickElement(pay,"Pay button ");
        Thread.sleep(5000);
        WebElement cardNum = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/section[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]"));
        enterText(cardNum,data[12][1],"Card Number ");
        Thread.sleep(5000);
        WebElement expDate = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/section[1]/section[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]"));
        enterText(expDate,"03/23","Expiry Date ");
        Thread.sleep(5000);
        WebElement cvc = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/section[1]/section[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]"));
        enterText(cvc,data[14][1],"CVc ");
        WebElement name = driver.findElement(By.xpath("//input[@id='stripe-cardholder-name']"));
        enterText(name,data[15][1],"Name ");
        Thread.sleep(5000);
        WebElement confirm = driver.findElement(By.xpath("//button[@id='continueButton']"));
        clickElement(confirm,"Confirm ");
        driver.close();
        Thread.sleep(5000);
        driver.switchTo().window(winHandleBefore);
    }

    @Test
    void TC18_userLookout() throws IOException, InterruptedException {
        logger = reports.startTest("TC18_userLookout");
        login();
        Thread.sleep(5000);
        WebElement Accounting = driver.findElement(By.xpath("//div[@class='xdash-welcome__welcome--header___3B5Tm']"));
        clickElement(Accounting, " Accounting Button ");
        Thread.sleep(5000);

    }


    @AfterMethod
    void close(){
        reports.endTest(logger);
        driver.quit();
    }
    @AfterClass
    void FlushReport(){
        reports.flush();
    }
}

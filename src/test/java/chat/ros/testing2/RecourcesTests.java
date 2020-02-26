package chat.ros.testing2;

import chat.ros.testing2.pages.LoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URI;

import static chat.ros.testing2.helpers.ScreenshotTests.AScreenshot;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecourcesTests implements BeforeAllCallback, AfterEachCallback {

    private LoginPage loginPage = new LoginPage();
    private String classTest = "";

    @Override
    public void beforeAll(ExtensionContext context){

        classTest = String.valueOf(context.getTestClass());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setVersion("72.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        WebDriver driver = null;
        try {
            driver = WebDriverPool.DEFAULT.getDriver(URI.create("http://10.10.199.45:4444/wd/hub").toURL(), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().setPosition(new Point(2,2));
        WebDriverRunner.setWebDriver(driver);

        Configuration.baseUrl = "https://testing2.ros.chat:1110";
        Configuration.screenshots = false;

        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains("https://testing2.ros.chat:1110")) open("/");



        if( ! loginPage.isLoginMS()) loginPage.loginOnServer("admin", "123456");

        assertTrue(loginPage.isLoginMS(), "Не удалось авторизоваться");

        if(classTest.contains("ServerPage")) open("/settings/web-server");
        else if (classTest.contains("TelefonyPage")) open("/settings/telephony");
    }

    @Override
    public void afterEach(ExtensionContext context){
        String filename = String.valueOf(context.getTestMethod());
        AScreenshot(filename);
    }
}

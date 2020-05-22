package chat.ros.testing2;

import com.codeborne.selenide.WebDriverRunner;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public interface TestSuiteBase {

    TestsBase testBase = new TestsBase();

    @BeforeSuite
    default void setUp(){
        testBase.init();
    }

    @AfterMethod(alwaysRun = true)
    default void afterTestMethod(Method m, ITestResult testResult){
        testBase.afterTestMethod(m, testResult);
    }

    @AfterSuite
    default void tearDown(){
        WebDriverRunner.closeWebDriver();
    }

}

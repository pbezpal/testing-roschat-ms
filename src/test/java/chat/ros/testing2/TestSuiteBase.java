package chat.ros.testing2;

import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public interface TestSuiteBase {

    @BeforeSuite
    default void setUp(){
        TestsBase.getInstance().init();
    }

    @AfterMethod(alwaysRun = true)
    default void afterTestMethod(Method m, ITestResult testResult){
        TestsBase.getInstance().afterTestMethod(m, testResult);
    }

    @AfterSuite
    default void tearDown(){
        TestsBase.getInstance().dismissWebDriver();
    }

}

package chat.ros.testing2;

import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public interface TestSuiteBase {

    TestsBase testsBase = new TestsBase();

    @BeforeSuite
    default void setUp(){
        getInstanceTestBase().init();
    }

    @AfterMethod(alwaysRun = true)
    default void afterTestMethod(Method m, ITestResult testResult){
        getInstanceTestBase().afterTestMethod(m, testResult);
    }

    @AfterSuite
    default void tearDown(){
        getInstanceTestBase().dismissWebDriver();
    }

    default TestsBase getInstanceTestBase(){
        return testsBase;
    }

}

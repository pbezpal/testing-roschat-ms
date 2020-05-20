package chat.ros.testing2;

import org.testng.ISuite;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class TestBaseSuite {

    ITestContext context;

    @AfterClass
    public void afterClass(){
        String className = this.getClass().getName();
        System.out.println(className);
    }

    @AfterSuite
    public void tearDown(ITestContext c){
        this.context = c;
        System.out.println(context.getCurrentXmlTest().getName());
    }
}

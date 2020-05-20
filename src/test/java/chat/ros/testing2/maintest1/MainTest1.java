package chat.ros.testing2.maintest1;

import chat.ros.testing2.TestBaseSuite;
import org.testng.annotations.Test;

public class MainTest1 extends TestBaseSuite {

    @Test(dependsOnMethods = {"chat.ros.testing2.maintest.MainTest.test"})
    public void test1(){
        System.out.println("test");
    }
}

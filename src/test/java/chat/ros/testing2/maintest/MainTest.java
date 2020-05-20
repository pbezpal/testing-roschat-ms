package chat.ros.testing2.maintest;

import chat.ros.testing2.TestBaseSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import static org.testng.Assert.assertEquals;

public class MainTest extends TestBaseSuite {

    @Test
    public void test(){
        assertEquals("test", "notest");
    }
}

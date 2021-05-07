package chat.ros.testing2;

import java.util.HashMap;
import java.util.Map;

public abstract class TestStatusResult {

    private static Map<String, Boolean> testResult = new HashMap<>();
    private static boolean status = false;

    public static void setTestResult(String method, boolean result){
        TestStatusResult.testResult.put(method, result);
    }

    public static void setTestResult(boolean status){
        TestStatusResult.status = status;
    }

    public static Map<String, Boolean> getTestResult(){
        return testResult;
    }

    public static boolean getStatusTest(){
        return status;
    }

}

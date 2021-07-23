package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ResourcesIVRPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {

    /**
     * <p>assume the previous test on which the current test depends</p>
     * <p>this method only checks parametrization tests</p>
     * @param tests the first value is the current parameter full name
     *              the second value is the first part of the parameter name of the previous test
     */
    private void verifyParamTest(String... tests){
        String parameterName = tests[0].split("=")[1];
        //the value is the parameter full name of the previous test
        String beforeParameterNameTest = tests[1] + parameterName;
        if(TestStatusResult.getTestResult().get(beforeParameterNameTest) != null) {
            assumeTrue(TestStatusResult.getTestResult().get(beforeParameterNameTest),
                    "Skipping test because test " + beforeParameterNameTest + " failed");
        }else
            assumeTrue(false, "Skipping test because test " + beforeParameterNameTest + " failed");
    }

    /**
     * <p>assume the test before the current test starts</p>
     * <p>if the result of the previous test equal null, then the test fails</p>
     * @param tests the first value is the name of the test
     *              the second value is the error text
     */
    private void beforeVerifyTests(String... tests){
        if( TestStatusResult.getTestResult().get(tests[0]) != null)
            assumeTrue(TestStatusResult.getTestResult().get(tests[0]), tests[1]);
        else
            assumeTrue(false, tests[1]);

        if(tests.length > 2) {
            if (TestStatusResult.getTestResult().get(tests[2]) != null)
                assumeTrue(TestStatusResult.getTestResult().get(tests[2]), tests[3]);
            else
                assumeTrue(false, tests[3]);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String testMethod = context.getRequiredTestMethod().getName();
        String testClass = context.getTestClass().toString();
        String testParams = context.getDisplayName();
        TestStatusResult.setTestResult(false);
        if(testClass.contains("TestSoundPage")){
            switch (testMethod){
                case "test_Delete_Sound_File_After_Edit":
                case "test_Download_Sound_File":
                case "test_Button_Play_After_Edit_Sound_File":
                case "test_AudioPlayer_When_Edit_Sound_File":
                    beforeVerifyTests("test_Audio_Player_When_Uploading_File", "The sound file don't add. Skip the test!");
            }
        }else if(testClass.contains("TestMenuPage")) {
            switch (testMethod){
                case "test_Add_Menu":
                case "test_Delete_Sound_File":
                    assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File"), "The sound file don't add. Skip the test!");
                    break;
                case "test_Add_Rules_With_Week_Days":
                case "test_Add_Rules_With_Calendar_Date":
                case "test_Edit_Schedule":
                case "test_Check_Title_Text_Modal_Window_When_Edit_Schedule":
                case "test_Check_Title_Text_Modal_Window_When_Add_Rules":
                case "test_Delete_Schedule":
                    verifyParamTest(testParams, "Add schedule=");
                    break;
                case "test_Add_Entry_Point_WIth_Simple_Menu":
                    verifyParamTest(testParams, "Add menu=");
                    verifyParamTest(testParams, "Add schedule=");
                    break;
                case "test_Look_Simple_Menu":
                case "test_Add_Go_To_Menu":
                case "test_Check_Title_Text_Modal_Window_When_Edit_Menu":
                case "test_Delete_Simple_Menu":
                    verifyParamTest(testParams, "Add menu=");
                    break;
                case "test_Add_Entry_Point_With_Go_To_Menu":
                    verifyParamTest(testParams, "Add schedule=");
                    verifyParamTest(testParams, "Add go to menu=");
                    break;
                case "test_Look_Go_To_Menu":
                case "test_Delete_Go_To_Menu":
                    verifyParamTest(testParams, "Add go to menu=");
                    break;
                case "test_Edit_Simple_Menu":
                    assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File_2"),
                            "The sound file without description don't add. Skip the test!");
                    verifyParamTest(testParams, "Add menu=");
                    break;
                case "test_Check_Title_Text_Modal_Window_When_Edit_Rules":
                case "test_Edit_Rules_With_Week_Days":
                    verifyParamTest(testParams, "Edit schedule=");
                    verifyParamTest(testParams, "Add rule with week days=");
                    break;
                case "test_Edit_Rules_With_Calendar_Date":
                    verifyParamTest(testParams, "Edit schedule=");
                    verifyParamTest(testParams, "Add rule with calendar date=");
                    break;
                case "test_Edit_Entry_Point_With_Simple_Menu":
                    verifyParamTest(testParams, "Edit schedule=");
                    verifyParamTest(testParams, "Edit menu=");
                    verifyParamTest(testParams, "Add entry point with simple menu=");
                    break;
                case "test_Look_Simple_Menu_After_Edit":
                    verifyParamTest(testParams, "Edit menu=");
                    break;
                case "test_Edit_Go_To_Menu":
                    assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File_2"),
                            "The sound file without description don't add. Skip the test!");
                    verifyParamTest(testParams, "Add go to menu=");
                    verifyParamTest(testParams, "Edit menu=");
                    break;
                case "test_Look_Go_to_Menu_After_Edit":
                    verifyParamTest(testParams, "Edit go to menu=");
                    break;
                case "test_Edit_Entry_Point_With_Go_To_Menu":
                    verifyParamTest(testParams, "Edit schedule=");
                    verifyParamTest(testParams, "Edit go to menu=");
                    verifyParamTest(testParams, "Add entry point with go to menu=");
                    break;
                case "test_Check_Title_Text_Modal_Window_When_Edit_Entry_Point":
                case "test_Delete_Entry_Point_With_Simple_Menu":
                    verifyParamTest(testParams, "Add entry point with simple menu=");
                    break;
                case "test_Delete_Entry_Point_With_Go_To_Menu":
                    verifyParamTest(testParams, "Add entry point with go to menu=");
                    break;
                case "test_No_Links_Go_To_Menu":
                    verifyParamTest(testParams, "Add go to menu=");
                    verifyParamTest(testParams, "Delete menu=");
                    break;
                case "test_Delete_Rules_With_Week_Days":
                    verifyParamTest(testParams, "Add rule with week days=");
                    break;
                case "test_Delete_Rules_With_Calendar_Date":
                    verifyParamTest(testParams, "Add rule with calendar date=");
            }
        }

        getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Сервисы","Голосовое меню");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String testMethod = context.getRequiredTestMethod().getName();
        String testClass = context.getTestClass().toString();
        String testParams = context.getDisplayName();
        if(testClass.contains("TestSoundPage")) {
            switch (testMethod){
                case "test_Audio_Player_When_Uploading_File":
                case "test_AudioPlayer_When_Edit_Sound_File":
                    TestStatusResult.setTestResult(testMethod, TestStatusResult.getStatusResult());
            }
        }else if(testClass.contains("TestMenuPage")){
            switch (testMethod){
                case "test_Add_Simple_Menu":
                case "test_Add_Entry_Point_With_Simple_Menu":
                case "test_Add_Go_To_Menu":
                case "test_Add_Entry_Point_With_Go_To_Menu":
                case "test_Edit_Simple_Menu":
                case "test_Edit_Entry_Point_With_Simple_Menu":
                case "test_Edit_Go_To_Menu":
                case "test_Edit_Entry_Point_With_Go_To_Menu":
                case "test_Delete_Simple_Menu":
                case "test_Add_Schedule":
                case "test_Add_Rules":
                case "test_Add_Rules_With_Week_Days":
                case "test_Add_Rules_With_Calendar_Date":
                case "test_Edit_Schedule":
                case "test_Edit_Rules_With_Week_Days":
                case "test_Edit_Rules_With_Calendar_Date":
                    TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusResult());
                    break;
                default:
                    TestStatusResult.setTestResult(testMethod, TestStatusResult.getStatusResult());
            }
        }
    }
}

package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ResourcesIVRPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {

    private void verifyParamTest(String... tests){
        String typeMenu = tests[0].split("=")[1];
        String beforeTest = tests[1] + typeMenu;
        if(TestStatusResult.getTestResult().get(beforeTest) != null)
            assumeTrue(TestStatusResult.getTestResult().get(beforeTest),
                    "Skipping test because test " + beforeTest + " failed");
        else
            assumeTrue(false, "Skipping test because test " + beforeTest + " failed");
    }

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
            if(testMethod.equals("test_Delete_Sound_File_After_Edit")
                    || testMethod.equals("test_Download_Sound_File")
                    || testMethod.equals("test_Button_Play_After_Edit_Sound_File")
                    || testMethod.equals("test_AudioPlayer_When_Edit_Sound_File")
            )
                beforeVerifyTests("test_Audio_Player_When_Uploading_File", "The sound file don't add. Skip the test!");
        }else if(testClass.contains("TestMenuPage")){
            if(testMethod.equals("test_Add_Menu"))
                assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File"), "The sound file don't add. Skip the test!");
            else if(testMethod.equals("test_Add_Entry_Point_WIth_Simple_Menu")) verifyParamTest(testParams, "Add menu=");
            else if(testMethod.equals("test_Look_Simple_Menu")) verifyParamTest(testParams, "Add menu=");
            else if(testMethod.equals("test_Add_Go_To_Menu")) verifyParamTest(testParams, "Add menu=");
            else if(testMethod.equals("test_Add_Entry_Point_With_Go_To_Menu")) verifyParamTest(testParams, "Add go to menu=");
            else if(testMethod.equals("test_Look_Go_To_Menu")) verifyParamTest(testParams, "Add go to menu=");
            else if(testMethod.equals("test_Edit_Simple_Menu")) {
                assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File_2"),
                        "The sound file without description don't add. Skip the test!");
                verifyParamTest(testParams, "Add menu=");
            }else if(testMethod.equals("test_Edit_Entry_Point_With_Simple_Menu"))
                verifyParamTest(testParams, "Edit menu=");
            else if(testMethod.equals("test_Look_Simple_Menu_After_Edit")) verifyParamTest(testParams, "Edit menu=");
            else if(testMethod.equals("test_Edit_Go_To_Menu")){
                assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File_2"),
                        "The sound file without description don't add. Skip the test!");
                verifyParamTest(testParams, "Add go to menu=");
                verifyParamTest(testParams, "Edit menu=");
            }
            else if(testMethod.equals("test_Look_Go_to_Menu_After_Edit")) verifyParamTest(testParams, "Edit go to menu=");
            else if(testMethod.equals("test_Edit_Entry_Point_With_Go_To_Menu")) verifyParamTest(testParams, "Edit go to menu=");
            else if(testMethod.equals("test_Delete_Sound_File"))
                assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File"),
                        "The sound file don't add. Skip the test!");
            else if(testMethod.equals("test_Delete_Entry_Point_With_Simple_Menu")) {
                verifyParamTest(testParams, "Add entry point with simple menu=");
                verifyParamTest(testParams, "Edit entry point=");
            }else if(testMethod.equals("test_Delete_Entry_Point_With_Go_To_Menu")) {
                verifyParamTest(testParams, "Add entry point with go to menu=");
            }else if(testMethod.equals("test_Delete_Simple_Menu")) {
                verifyParamTest(testParams, "Add menu=");
            }else if(testMethod.equals("test_No_Links_Go_To_Menu")) {
                verifyParamTest(testParams, "Add go to menu=");
                verifyParamTest(testParams, "Delete menu=");
            }else if(testMethod.equals("test_Delete_Go_To_Menu")) verifyParamTest(testParams, "Add go to menu=");
        }

        if(testClass.contains("TestSoundPage") || testClass.contains("TestMenuPage"))
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Сервисы","Голосовое меню");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String testMethod = context.getRequiredTestMethod().getName();
        String testClass = context.getTestClass().toString();
        String testParams = context.getDisplayName();
        if(testClass.contains("TestSoundPage")) {
            if (testMethod.equals("test_Audio_Player_When_Uploading_File"))
                TestStatusResult.setTestResult(testMethod, TestStatusResult.getStatusTest());
            else if (testMethod.equals("test_AudioPlayer_When_Edit_Sound_File"))
                TestStatusResult.setTestResult(testMethod, TestStatusResult.getStatusTest());
        }else if(testClass.contains("TestMenuPage")){
            if(testMethod.equals("test_Upload_Sound_File") || testMethod.equals("test_Upload_Sound_File_2"))
                TestStatusResult.setTestResult(testMethod, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Add_Simple_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Add_Entry_Point_With_Simple_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Add_Go_To_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Add_Entry_Point_With_Go_To_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Edit_Simple_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Edit_Entry_Point_With_Simple_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Edit_Go_To_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Edit_Entry_Point_With_Go_To_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Delete_Simple_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
        }
    }
}

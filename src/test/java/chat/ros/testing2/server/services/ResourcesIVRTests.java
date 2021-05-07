package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesIVRTests extends StartWebDriver implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        String testMethod = context.getRequiredTestMethod().getName();
        String testClass = context.getTestClass().toString();
        String testParams = context.getDisplayName();
        TestStatusResult.setTestResult(false);
        if(testClass.contains("TestSoundPage")){
            if(testMethod.equals("test_Delete_Sound_File_After_Edit")){
                if( TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File") == null || ! TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File")){
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Audio_Player_When_Uploading_File"), "The sound file don't add. Skip the test!");
                }
            }if(testMethod.equals("test_Download_Sound_File")){
                if( TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File") == null || ! TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File")){
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Audio_Player_When_Uploading_File"), "The sound file don't add. Skip the test!");
                }
            }else if(testMethod.equals("test_Button_Play_After_Edit_Sound_File"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File"), "The sound file don't edit. Skip the test!");
            else if( ! testMethod.equals("test_Audio_Player_When_Uploading_File"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Audio_Player_When_Uploading_File"), "The sound file don't add. Skip the test!");
        }else if(testClass.contains("TestMenuPage")){
            if(testMethod.equals("test_Add_Menu"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File"), "The sound file don't add. Skip the test!");
            else if(testMethod.equals("test_Add_Entry_Point_WIth_Simple_Menu")) {
                String typeMenu = testParams.replace("Add entry point with simple menu=", "");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add menu=" + typeMenu),
                        "Skipping test because menu adding test Add menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Add_Go_To_Menu")){
                String typeMenu = testParams.replace("Add go to menu=", "");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add menu=" + typeMenu),
                        "Skipping test because menu adding test Add menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Add_Entry_Point_With_Go_To_Menu")){
                String typeMenu = testParams.replace("Add entry point with go to menu=", "");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add go to menu=" + typeMenu),
                        "Skipping test because menu adding test Add go to menu=" + typeMenu + " failed");
            }
            else if(testMethod.equals("test_Edit_Simple_Menu")){
                String typeMenu = testParams.replace("Edit menu=", "");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File_Without_Description"),
                        "The sound file without description don't add. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add menu=" + typeMenu),
                        "Skipping test because menu adding test Add menu=" + typeMenu + " failed");
            }
            else if(testMethod.equals("test_Edit_Entry_Point_With_Simple_Menu")){
                String typeMenu = testParams.replace("Edit entry point=", "");
                if(TestStatusResult.getTestResult().get("Edit menu=" + typeMenu) == null || ! TestStatusResult.getTestResult().get("Edit menu=" + typeMenu))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add menu=" + typeMenu),
                            "Skipping test because menu adding test Add menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Edit_Go_To_Menu")){
                String typeMenu = testParams.replace("Edit go to menu=", "");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File_Without_Description"),
                        "The sound file without description don't add. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add go to menu=" + typeMenu),
                        "Skipping test because menu adding test Add go to menu=" + typeMenu + " failed");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Edit menu=" + typeMenu),
                        "Skipping test because menu adding test Edit menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Edit_Entry_Point_With_Go_To_Menu")){
                String typeMenu = testParams.replace("Edit entry point with go to menu=", "");
                if(TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu) == null && ! TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add go to menu=" + typeMenu),
                            "Skipping test because menu adding test Add go to menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Delete_Sound_File"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Upload_Sound_File"), "The sound file don't add. Skip the test!");
            else if(testMethod.equals("test_Delete_Entry_Point_With_Simple_Menu")) {
                String typeMenu = testParams.replace("Delete entry point with simple menu=", "");
                if (TestStatusResult.getTestResult().get("Edit entry point=" + typeMenu) == null || ! TestStatusResult.getTestResult().get("Edit entry point=" + typeMenu))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add entry point with simple menu=" + typeMenu),
                            "Skipping test because menu adding test Add entry point with simple menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Delete_Entry_Point_With_Go_To_Menu")) {
                String typeMenu = testParams.replace("Delete entry point with go to menu=", "");
                if (TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + typeMenu) == null || !TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + typeMenu))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add entry point with go to menu=" + typeMenu),
                            "Skipping test because menu adding test Add entry point with go to menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Delete_Simple_Menu")) {
                String typeMenu = testParams.replace("Delete menu=", "");
                if (TestStatusResult.getTestResult().get("Edit menu=" + typeMenu) == null || !TestStatusResult.getTestResult().get("Edit menu=" + typeMenu))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add menu=" + typeMenu),
                            "Skipping test because menu adding test Add menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_No_Links_Go_To_Menu")) {
                String typeMenu = testParams.replace("Check links go to menu=", "");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu),
                        "Skipping test because menu adding test Edit go to menu=" + typeMenu + " failed");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Delete menu=" + typeMenu),
                        "Skipping test because menu adding test Delete menu=" + typeMenu + " failed");
            }else if(testMethod.equals("test_Delete_Go_To_Menu")) {
                String typeMenu = testParams.replace("Delete go to menu=", "");
                if (TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu) == null || !TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("Add go to menu=" + typeMenu),
                            "Skipping test because menu adding test Add go to menu=" + typeMenu + " failed");
            }
        }

        if(testClass.contains("TestSoundPage") || testClass.contains("TestMenuPage"))
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Голосовое меню");
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
            if(testMethod.equals("test_Upload_Sound_File") || testMethod.equals("test_Upload_Sound_File_Without_Description"))
                TestStatusResult.setTestResult(testMethod, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Add_Menu"))
                TestStatusResult.setTestResult(testParams, TestStatusResult.getStatusTest());
            else if(testMethod.equals("test_Add_Entry_Point_WIth_Simple_Menu"))
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

    @Override
    public void afterAll(ExtensionContext context) {
        Selenide.closeWebDriver();
    }
}

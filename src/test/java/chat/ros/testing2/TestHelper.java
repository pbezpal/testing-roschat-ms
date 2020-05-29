package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.sleep;

public class TestHelper {

    private static boolean statusWebServer;

    @Step(value = "Проверяем в течение минуты, запущен ли Web-сервер")
    public static boolean isWebServerStatus(){
        for(int i = 0; i < 60; i++) {
            statusWebServer =  SSHManager.isCheckQuerySSH("systemctl status wlan | grep active | grep -v inactive");
            if(statusWebServer) break;
            sleep(1000);
        }

        return statusWebServer;
    }
}

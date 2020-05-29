package chat.ros.testing2.server.settings;

import chat.ros.testing2.server.LoginPage;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;

public class UserPage extends LoginPage implements SettingsPage {

    private SelenideElement buttonUserAvatar = $("div.user-avatar button");
    private SelenideElement divLogoutMS = $("div.menuable__content__active div.v-list__tile__title.item-title");
    private String locatorDeleteUser = "//table//td[contains(text(),'%1$s')]//ancestor::tr//button//i[text()='delete']";

    public UserPage() {}

    @Step(value = "Проверяем, что мы авторизовались под пользователем {user}")
    public boolean isLoginNewUser(String user){
        return buttonUserAvatar.find("span").text().equals(user);
    }

    @Step(value = "Выходим из системы управления")
    public void logoutMS(){
        buttonUserAvatar.click();
        divLogoutMS.click();
        clickButtonConfirmAction(USER_BUTTON_CONTINUE);
    }

    @Step(value = "Нажимаем кнопку удалить у пользователя {user}")
    public UserPage clickDeleteUser(String user){
        $x(String.format(locatorDeleteUser,user)).click();
        return this;
    }

    public boolean addUser(Map<String, String> mapInputValueUser, String user){
        clickButtonAdd();
        sendLabelInputsForm(mapInputValueUser);
        clickButtonSave();
        return isExistsTableText(user, true);
    }

    public boolean isDeleteUser(String user){
        clickDeleteUser(user);
        clickButtonConfirmAction(USER_BUTTON_CONTINUE);
        return isNotExistsTableText(user);
    }
}

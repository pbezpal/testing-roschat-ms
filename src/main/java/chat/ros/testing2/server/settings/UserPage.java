package chat.ros.testing2.server.settings;

import chat.ros.testing2.server.LoginPage;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class UserPage extends LoginPage implements SettingsPage {

    private SelenideElement buttonUserAvatar = $("div.user-avatar button");
    private SelenideElement divLogoutMS = $("div.menuable__content__active div.v-list__tile__title.item-title");
    private String locatorDeleteUser = "//table//td[contains(text(),'%1$s')]//ancestor::tr//button//i[text()='%2$s']";
    private SelenideElement divRoles = $("div[role='combobox']");

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

    @Step(value = "Нажимаем кнопку {button} у пользователя {user}")
    public UserPage clickButtonActionUser(String user, String button){
        $$("table td").findBy(text(user)).parent().$$("button i").findBy(text(button)).click();
        return this;
    }

    @Step(value = "Выбираем тип пользователя {role}")
    public UserPage selectRoleUser(int role){
        divRoles.click();
        listItems.get(role).click();
        return this;
    }

    public UserPage addUser(Map<String, String> mapInputValueUser){
        clickButtonAdd();
        sendLabelInputsForm(mapInputValueUser);
        return this;
    }

    public boolean changeUser(Map<String, String> mapInputValueUser, String user){
        clickButtonActionUser(user,"edit");
        sendLabelInputsForm(mapInputValueUser);
        clickButtonSave();
        return isExistsTableText(user, true);
    }

    public boolean isDeleteUser(String user){
        clickButtonActionUser(user,"delete");
        clickButtonConfirmAction(USER_BUTTON_CONTINUE);
        return isNotExistsTableText(user);
    }
}

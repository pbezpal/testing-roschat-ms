package chat.ros.testing2.server.settings;

import chat.ros.testing2.data.MSGeneralElements;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;

public class UserPage extends SettingsPage implements MSGeneralElements {

    private SelenideElement buttonAddUser = $("button.primary");
    private ElementsCollection tdUserList = $$("table.v-datatable td");
    private SelenideElement buttonUserAvatar = $("div.user-avatar button");
    private SelenideElement divLogoutMS = $("div.menuable__content__active div.v-list__tile__title.item-title");

    public UserPage() {}

    @Step(value = "Нажимаем кнопку Добавить")
    private UserPage clickAddUser(){
        buttonAddUser.click();
        return this;
    }

    @Step(value = "Заполняем поля пользователя")
    @Override
    public void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()) {
            String input = entry.getKey();
            String value = entry.getValue();
            $("input[aria-label='" + input + "']").click();
            $("input[aria-label='" + input + "']").sendKeys(value);
        }
    }

    @Step(value = "Проверяем, что пользователь {user} добавлен")
    private boolean isExistsUser(String user){
        try{
            tdUserList.findBy(Condition.text(user)).shouldBe(Condition.visible);
        }catch (ElementNotFound elementNotFound){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, что пользователь {user} был успешно удалён")
    private boolean isNotExistsUser(String user){
        try{
            tdUserList.findBy(Condition.text(user)).shouldBe(Condition.not(Condition.visible));
        }catch (ElementShould elementShould){
            return false;
        }

        return true;
    }

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
        $x("//table//td[contains(text(),'" + user +"')]//ancestor::tr//button//i[text()='delete']").click();
        return this;
    }

    public boolean addUser(Map<String, String> mapInputValueUser, String user){
        clickAddUser();
        sendInputsForm(mapInputValueUser);
        clickButtonSave();
        return isExistsUser(user);
    }

    public boolean isDeleteUser(String user){
        clickDeleteUser(user);
        clickButtonConfirmAction(USER_BUTTON_CONTINUE);
        return isNotExistsUser(user);
    }
}

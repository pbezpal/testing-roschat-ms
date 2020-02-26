package chat.ros.testing2.pages;

import chat.ros.testing2.data.MSGeneralElements;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class UserPage implements MSGeneralElements {

    //Элементы раздела пользователь
    private SelenideElement divWrapperUser = $("div.main-wrapper.user");
    private SelenideElement activeItemMenu = $("div.main-wrapper.user a.v-tabs__item.v-tabs__item--active");
    private ElementsCollection inactiveItemMenu = $$("div.main-wrapper.user a.v-tabs__item");
    private SelenideElement buttonNewAccount = $("div#new-account button");
    private ElementsCollection inputsPassword = $$("form.v-form input[type='password']");
    private SelenideElement divProgressBar = $("div.v-progress-circular__info");
    private ElementsCollection spanValueAccount = $$("div.v-window__container span");

    //Данные раздела пользователь
    private String inputUsername = "Имя пользователя";
    private String inputNumberSIP = "Номер SIP";

    public UserPage() {}

    //Добавляем учётную запись пользователю
    public UserPage addUserAccount(String number, String password, String itemMenu) {
        String username = number + "@ros.chat";
        //Проверяем, что находимся на странице пользователя
        assertTrue(isDivWrapperUser(), "Не удалось перейти на страницу пользователя");
        //Переходим в раздел учётная запись
        clickMenuItem(itemMenu);
        //Нажимаем кнопку создать аккаунт
        clickButtonAddAccount();
        //Проверяем, появилась ли форма для редактирования
        assertTrue(isFormChange(), "Форма для добавления учётной записи не появилась");
        //Вводим имя пользователя
        sendInputForm(inputUsername, username);
        //Вводим пароль
        sendInputsPassword(password);
        //Вводим номер SIP
        sendInputForm(inputNumberSIP, number);
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки аккаунта, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();
        //Ждем, когда пропадёт прогрессбар и проверяем добавлен ли аккаунт
        assertTrue(isWaitInvisibleProgressbar(), "Прогрессбар завис");
        //Проверяем, появилась ли учётная запись пользователя
        assertTrue(isExistsAccount(username), "Учётная запись не добавлена");

        return this;
    }

    @Step(value = "Проверяем, что мы на странице Пользователь")
    public boolean isDivWrapperUser(){
        try{
            divWrapperUser.shouldBe(visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Переходим в раздел {itemMenu}")
    public UserPage clickMenuItem(String itemMenu){
        if( ! activeItemMenu.text().contains(itemMenu)) {
            inactiveItemMenu.findBy(text(itemMenu)).click();
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку Создать аккаунт")
    public UserPage clickButtonAddAccount(){
        buttonNewAccount.click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт прогрессбар при добавлении аккаунта")
    public boolean isWaitInvisibleProgressbar(){
        try{
            divProgressBar.waitUntil(not(visible), 30000);
        }catch (ElementShould elementShould){
            return false;
        }

        return true;
    }

    @Step(value = "Вводим пароль и подтверждение пароля")
    public UserPage sendInputsPassword(String password){
        inputsPassword.first().sendKeys(password);
        inputsPassword.last().sendKeys(password);
        return this;
    }

    @Step(value = "Проверяем, добавлена лм учётная запись")
    public boolean isExistsAccount(String account){
        try{
            spanValueAccount.findBy(text(account)).shouldBe(visible);
        }catch (ElementShould elementShould){
            return false;
        }
        return true;
    }

}

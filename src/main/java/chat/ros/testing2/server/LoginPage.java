package chat.ros.testing2.server;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage implements BasePage {

    private static SelenideElement buttonLogin = formChange.find("button");
    private SelenideElement navSuccessLogin = $("nav.v-toolbar.theme--light");
    private SelenideElement checkboxRememberSystem = $(".v-input--selection-controls__ripple");
    private SelenideElement inputCheckBoxRememberSystem = $$("input").findBy(Condition.type("checkbox"));

    public LoginPage () {}

    @Step(value = "Выбираем оставаться {remember} ли в системе")
    public LoginPage selectCheckboxRememberSystem(boolean remember){
        if(remember){
            if( ! inputCheckBoxRememberSystem.isSelected())
                checkboxRememberSystem.click();
            inputCheckBoxRememberSystem.shouldBe(Condition.selected);
        }else{
            if(inputCheckBoxRememberSystem.isSelected())
                checkboxRememberSystem.click();
            inputCheckBoxRememberSystem.shouldNotBe(Condition.selected);
        }
        return this;
    }

    @Step(value = "Авторизуемся на сервере с пользователем {login} и паролем {password}")
    public void loginOnServer(String login, String password){
        inputLogin.sendKeys(login);
        inputPassword.sendKeys(password);
        buttonLogin.click();
    }

    @Step(value = "Авторизуемся на сервере с пользователем {login} и паролем {password} остаёмся в системе {remember}")
    public void loginOnServer(String login, String password, boolean remember){
        inputLogin.sendKeys(login);
        inputPassword.sendKeys(password);
        selectCheckboxRememberSystem(remember);
        buttonLogin.click();
    }

    @Step(value = "Проверяем, авторизованы ли мы в СУ")
    public boolean isLoginMS(){
        try {
            navSuccessLogin.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }
}

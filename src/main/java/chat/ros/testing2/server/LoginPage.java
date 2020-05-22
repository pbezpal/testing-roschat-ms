package chat.ros.testing2.server;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage implements BasePage {

    private static SelenideElement buttonLogin = formChange.find("button");
    private SelenideElement navSuccessLogin = $("nav.v-toolbar.theme--light");

    public LoginPage () {}

    @Step(value = "Авторизуемся на сервере с пользователем {login} и паролем {password}")
    public void loginOnServer(String login, String password){
        inputLogin.sendKeys(login);
        inputPassword.sendKeys(password);
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

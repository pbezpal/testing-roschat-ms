package chat.ros.testing2.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private static SelenideElement formLogin = $("form.v-form");
    private static SelenideElement inputLogin = formLogin.find("input[type='text']");
    private static SelenideElement inputPassword = formLogin.find("input[type='password']");
    private static SelenideElement buttonLogin = formLogin.find("button");
    private SelenideElement navSuccessLogin = $("nav.v-toolbar.theme--light");

    public static LoginPage loginPage = new LoginPage();
    public static LoginPage getInstance() { return loginPage; }

    @Step(value = "Проверяем, что есть форма авторизации")
    public static SelenideElement getLoginForm(){ return formLogin;}

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

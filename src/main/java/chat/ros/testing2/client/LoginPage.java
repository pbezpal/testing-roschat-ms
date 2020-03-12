package chat.ros.testing2.client;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class LoginPage {

    private SelenideElement divLogin = $("div#login");
    private SelenideElement buttonPencil = $("i.fal.fa-pencil");
    private SelenideElement inputEmail = $("input.input.non-border-input[type='text']");
    private SelenideElement inputPassword = $("input.input.non-border-input[type='password']");
    private SelenideElement buttonLogin = $("button#login-btn");

    public LoginPage() {}

    @Step(value = "Проверяем, появилось ли окно авторизации")
    private boolean isLoginWindow(){
        try{
            divLogin.waitUntil(Condition.visible, 30000);
        }catch (ElementNotFound elementNotFound){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку 'Ввести логин и пароль'")
    private LoginPage clickButtonPencil(){
        if(isLoginWindow()) buttonPencil.click();
        return this;
    }

    @Step(value = "Вводим адрес электронной почты {email}")
    private LoginPage sendInputEmail(String email){
        inputEmail.sendKeys(Keys.CONTROL + "a");
        inputEmail.sendKeys(Keys.BACK_SPACE);
        inputEmail.sendKeys(email);
        return this;
    }

    @Step(value = "Вводим пароль {password}")
    private LoginPage sendInputPassword(String password){
        inputPassword.sendKeys(Keys.CONTROL + "a");
        inputPassword.sendKeys(Keys.BACK_SPACE);
        inputPassword.sendKeys(password);
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Войти'")
    private LoginPage clickButtonLogin(){
        buttonLogin.click();
        return this;
    }

    public ClientPage sendDataLogin(String email, String password){
        assertTrue(isLoginWindow(), "Невозможно авторизоваться на клиенте");
        clickButtonPencil();
        sendInputEmail(email);
        sendInputPassword(password);
        clickButtonLogin();

        return new ClientPage();
    }

}

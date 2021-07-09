package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.FAX_USERS_TITLE;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class FaxPage extends ServicesPage {

    private String hostServer = System.getProperty("hostserver");

    private SelenideElement logoutActionForm = $(".v-dialog--active");
    private SelenideElement iconLogoutActionForm = logoutActionForm.find("i");
    private SelenideElement titleLogoutActionForm = logoutActionForm.find("h3");
    private SelenideElement textLogoutActionForm = logoutActionForm.find("p");
    private ElementsCollection buttonsLogoutActionForm = logoutActionForm.findAll("span");

    public FaxPage sendNumberFaxes(String number, String description, String button){
        sendInputModalWindow("Номер факса", number)
                .sendInputModalWindow("Описание", description)
                .clickActionButtonOfModalWindow(button);
        return this;
    }

    public SelenideElement getElementLinkToFax(){
        return getServiceSection(FAX_USERS_TITLE)
                .find(".fax-link")
                .shouldHave(text("Для входа в систему пользователю необходимо перейти по ссылке: "))
                .find("a[href=\"http://" + hostServer + ":5000\"]");
    }

    @Step(value = "Проверяем, текст ссылки")
    public FaxPage isLinkText(){
        getElementLinkToFax()
                .shouldBe(visible, text("http://" + hostServer + ":5000"));
        return this;
    }

    @Step(value = "Нажимаем на ссылку для перехода на страницу управления факсами")
    public ServicesPage clickLinkToGoMSFax(){
        getElementLinkToFax().click();
        switchTo().window(1);
        return new ServicesPage();
    }

    @Step(value = "Проверяем отображаются ли все элементы формы подтверждения выхода и нажимаем кнопку {button}")
    public ServicesPage clickButtonConfirmAction(String button){
        iconLogoutActionForm.shouldBe(visible);
        titleLogoutActionForm.shouldBe(text("Подтвердите свои действия"));
        textLogoutActionForm.shouldHave(text("Вы уверены, что хотите выйти из приложения?"));
        buttonsLogoutActionForm.findBy(text(button)).click();
        return this;
    }
}

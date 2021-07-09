package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.ALERT_TITLE_SECTION_USERS;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class AlertPage extends ServicesPage{

    private String hostServer = System.getProperty("hostserver");

    private SelenideElement logoutActionForm = $(".v-dialog--active");
    private SelenideElement iconLogoutActionForm = logoutActionForm.find("i");
    private SelenideElement titleLogoutActionForm = logoutActionForm.find("h4");
    private SelenideElement textLogoutActionForm = logoutActionForm.find("p");
    private ElementsCollection buttonsLogoutActionForm = logoutActionForm.findAll("span");

    public SelenideElement getElementLinkToAlert(){
        return getServiceSection(ALERT_TITLE_SECTION_USERS)
                .find(".alert-link")
                .shouldHave(text("Для входа в систему пользователю необходимо перейти по ссылке: "))
                .find("a[href=\"http://" + hostServer + ":3010\"]");
    }

    @Step(value = "Проверяем текст ссылки для перехода на страницу управления системой оповещения")
    public AlertPage isTextLinkToGoMSAlertPage(){
        getElementLinkToAlert()
                .shouldBe(visible, text("http://" + hostServer + ":3010"));
        return this;
    }

    @Step(value = "Нажимаем на ссылку для перехода на страницу управления системой оповещения")
    public ServicesPage clickLinkToGoMSAlertPage(){
        getElementLinkToAlert().click();
        switchTo().window(1);
        return new ServicesPage();
    }

    @Step(value = "Проверяем инфомационный текст {text} в разделе {section}")
    public AlertPage isInfoText(String section, String text){
        getServiceSection(section)
                .find(".d-flex p")
                .shouldBe(visible, text(text));
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section}")
    public AlertPage clickButtonAction(String section, String button){
        getServiceSection(section)
                .findAll(".flex.buttons button div")
                .findBy(text(button))
                .click();
        return this;
    }

    @Step(value = "Проверяем текст {text} после внесения изменений в поле {field} в разделе {section}")
    public AlertPage isTextAfterAction(String section, String field, String text){
        getServiceSection(section)
                .findAll(".selected-support-info")
                .findBy(text(field))
                .parent()
                .find("span")
                .shouldBe(visible, text(text));
        return this;
    }

    @Step(value = "Проверяем отображаются ли все элементы формы подтверждения выхода и нажимаем кнопку {button}")
    public ServicesPage clickButtonConfirmAction(String title, String button){
        iconLogoutActionForm.shouldBe(visible);
        titleLogoutActionForm.shouldBe(text("Подтверждение выхода"));
        textLogoutActionForm.shouldHave(text("Вы уверены, что хотите выйти из приложения?"));
        buttonsLogoutActionForm.findBy(text(button)).click();
        return this;
    }
}

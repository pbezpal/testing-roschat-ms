package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.ALERT_TITLE_SECTION_USERS;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class AlertPage extends ServicesPage{

    private String hostServer = System.getProperty("hostserver");

    public SelenideElement getElementLinkToAlert(){
        return getServiceSection(ALERT_TITLE_SECTION_USERS)
                .find(".alert-link")
                .shouldHave(text("Для входа в систему пользователю необходимо перейти по ссылке: "))
                .find("a[href=\"http://" + hostServer + ":3010]\"]");
    }

    @Step(value = "Проверяем текст ссылки для перехода в систему")
    public AlertPage isLinkText(){
        getElementLinkToAlert()
                .shouldBe(visible, text("http://" + hostServer + ":3010"));
        return this;
    }

    @Step(value = "Проверяем инфомационный текст {text} в разделе {section}")
    public AlertPage isInfoText(String section, String text){
        getServiceSection(section)
                .find(".d-flex p")
                .shouldBe(visible, text(text));
        return this;
    }
}

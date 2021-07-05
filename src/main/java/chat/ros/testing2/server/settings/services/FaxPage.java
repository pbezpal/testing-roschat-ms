package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.FAX_USERS_TITLE;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class FaxPage extends ServicesPage {

    private String hostServer = System.getProperty("hostserver");

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
}

package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$;

public class FaxPage extends ServicesPage {

    private ElementsCollection itemTableContactName = $$(".contacts-box .contact-name");

    public FaxPage sendNumberFaxes(String number, String description, String button){
        sendInputModalWindow("Номер факса", number)
                .sendInputModalWindow("Описание", description)
                .clickActionButtonOfModalWindow(button);
        return this;
    }

    @Step(value = "Получаем элемент контакта {contact}")
    public SelenideElement getContactName(String contact){
        if(itemTableContactName.size() == 0) return null;
        else return itemTableContactName.findBy(Condition.text(contact));
    }

    @Step(value = "Проверяем, что найден {search} контакт {contact}")
    public FaxPage isContactName(String contact, boolean search) {
        if (search) getContactName(contact).shouldBe(Condition.visible);
        else getContactName(contact).shouldNotBe(Condition.visible);
        return this;
    }
}

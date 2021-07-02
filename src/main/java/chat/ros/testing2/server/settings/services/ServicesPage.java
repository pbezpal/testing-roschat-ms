package chat.ros.testing2.server.settings.services;

import chat.ros.testing2.server.settings.SettingsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static chat.ros.testing2.data.SettingsData.IVR_MENU_TITLE;
import static chat.ros.testing2.data.SettingsData.IVR_SOUND_FILES_TITLE;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ServicesPage implements SettingsPage {

    private SelenideElement contentWrapper = $(".v-content__wrap");
    private SelenideElement modalWindow = $(".modal-window");
    private SelenideElement titleModalWindow = modalWindow.find("h2");
    private SelenideElement contentMenu = modalWindow.find(".modal-window__content");
    private ElementsCollection buttonActionOfModalWindow = modalWindow.$$(".modal-window__actions button div");
    private ElementsCollection itemTableContactName = $$(".contacts-box .contact-name");

    protected SelenideElement getContentWrapper(){
        return contentWrapper;
    }

    protected SelenideElement getServiceSection(String title){
        return $$("h2").findBy(text(title)).parent();
    }

    public SelenideElement getModalWindow() {
        return modalWindow;
    }

    @Step(value = "Проверяем, отображается {show} ли моадльное окно")
    public ServicesPage isModalWindow(boolean show){
        if(show) modalWindow.shouldBe(visible, Duration.ofSeconds(10));
        else modalWindow.shouldNotBe(visible, Duration.ofSeconds(10));
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в модальном окне")
    public ServicesPage clickActionButtonOfModalWindow(String button){
        buttonActionOfModalWindow.findBy(text(button)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Добавить в разделе {title}")
    public ServicesPage clickButtonAdd(String title){
        getServiceSection(title).scrollIntoView(false);
        getServiceSection(title).find(".action-bar button").click();
        return this;
    }

    @Step(value = "Проверяем, отображается {show} ли запись {item] в разделе {section}")
    public ServicesPage isItemTable(String section, String item, boolean show){
        getServiceSection(section).scrollTo();
        getServiceSection(section).$("table").scrollIntoView(false);
        SelenideElement itemTable = getServiceSection(section).$("table").$(byText(item));
        if(show) itemTable.shouldBe(visible);
        else itemTable.shouldNotBe(visible);
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section} у записи {item}")
    public ServicesPage clickButtonTable(String section, String item, String button){
        if(section.equals(IVR_MENU_TITLE) || section.equals(IVR_SOUND_FILES_TITLE)) contentWrapper.scrollIntoView(false);
        getServiceSection(section).scrollIntoView(false);
        getServiceSection(section)
                .$("table")
                .find(byText(item))
                .shouldHave(text(item))
                .closest("tr")
                .findAll(".layout i")
                .findBy(text(button))
                .click();
        return this;
    }

    @Step(value = "Нажимаем кнопку удаления в разделе {section} у записи {item}")
    public ServicesPage clickButtonTable(String section, String item){
        getServiceSection(section).scrollIntoView(false);
        getServiceSection(section)
                .$("table")
                .find(byText(item))
                .shouldHave(text(item))
                .closest("tr")
                .find("button")
                .click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли заголовок модального окна")
    public String getTextTitleModalWindow(){
        return titleModalWindow.getText();
    }

    @Step(value = "Вводим {value} в поле {field}")
    public ServicesPage sendInputModalWindow(String field, String ...value){
        if(value.length > 0) {
            SelenideElement input = $("input[aria-label='" + field + "']");
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(value);
        }
        return this;
    }

    @Step(value = "Получаем элемент контакта {contact}")
    public SelenideElement getContactName(String contact){
        if(itemTableContactName.size() == 0) return null;
        else return itemTableContactName.findBy(Condition.text(contact));
    }

    @Step(value = "Проверяем, что найден {search} контакт {contact}")
    public ServicesPage isContactName(String contact, boolean search) {
        if (search) getContactName(contact).shouldBe(Condition.visible);
        else getContactName(contact).shouldNotBe(Condition.visible);
        return this;
    }

}

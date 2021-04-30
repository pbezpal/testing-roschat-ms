package chat.ros.testing2.server.settings.services;

import chat.ros.testing2.server.settings.SettingsPage;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ServicesPage implements SettingsPage {

    private SelenideElement modalWindow = $(".modal-window");
    private SelenideElement titleModalWindow = modalWindow.find("h2");

    private SelenideElement getServiceSection(String title){
        return $x("//h2[text()='" + title + "']//parent::*[@class='block-wrapper']");
    }

    public SelenideElement getModalWindow() {
        return modalWindow;
    }

    @Step(value = "Нажимаем кнопку Добавить в разделе {title}")
    public ServicesPage clickButtonAdd(String title){
        getServiceSection(title).scrollIntoView(false);
        getServiceSection(title).find(".action-bar button").click();
        return this;
    }

    @Step(value = "Проверяем, отображается {show} ли запись {item] в разделе {section}")
    public boolean isItemTable(String section, String item, boolean show){
        getServiceSection(section).scrollTo();
        getServiceSection(section).$("table").scrollIntoView(false);
        if(show){
            try{
                getServiceSection(section)
                        .$("table")
                        .$(byText(item))
                        .shouldBe(visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try{
                getServiceSection(section).
                        $("table")
                        .$(byText(item))
                        .shouldBe(not(visible));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section} у записи {item}")
    public ServicesPage clickButtonTable(String section, String item, String button){
        getServiceSection(section).scrollIntoView(false);
        getServiceSection(section)
                .$("table")
                .$(byText(item))
                .parent()
                .$$(".layout i")
                .findBy(text(button))
                .click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли заголовок {title} модального окна")
    public String isVisibleTitleModalWrapper(){
        try {
            titleModalWindow.shouldBe(visible);
        }catch (ElementNotFound e){
            return null;
        }

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

}

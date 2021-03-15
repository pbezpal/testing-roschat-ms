package chat.ros.testing2.server.settings;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.IVR_TITLE_ENTRY_POINTS;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class IVRPage implements SettingsPage {

    private SelenideElement inputUploadSoundFile = $("#upload");
    private SelenideElement modalWindow = $(".modal-window");
    private SelenideElement titleModalWindow = modalWindow.find("h2");
    private SelenideElement buttonSave = modalWindow.find(".primary");
    private SelenideElement buttonClose = modalWindow.find(".v-btn--flat");

    private SelenideElement getIVRSection(String title){
        return mainWrapper.$$("h2").findBy(text(title)).parent();
    }

    @Step(value = "Нажимаем кнопку Добавить в разделе {title}")
    private IVRPage clickButtonAdd(String title){
        getIVRSection(title).find(".action-bar button").click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли заголовок {title} модального окна")
    private String isVisibleTitleModalWrapper(){
        try {
            titleModalWindow.shouldBe(visible);
        }catch (ElementNotFound e){
            return null;
        }

        return titleModalWindow.getText();
    }

    @Step(value = "Загружаем звуковой файл")
    private IVRPage uploadSoundFile(String file){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].style.display = 'block';", inputUploadSoundFile);
        inputUploadSoundFile.sendKeys(file);
        return this;
    }

    @Step(value = "Нажимаем кнопку Сохранить")
    private IVRPage clickButtonSaveOfModalWindow(){
        buttonSave.click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Отменить")
    private IVRPage clickButtonCloseOfModalWindow(){
        buttonClose.click();
        return this;
    }

    @Step(value = "Проверяем, отображается {show} ли запись в таблице")
    public boolean isItemTable(String section, String item, boolean show){
        if(show){
            try{
                getIVRSection(section).$$("table td")
                        .findBy(text(item))
                        .shouldBe(visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try{
                getIVRSection(section).$$("table td")
                        .findBy(text(item))
                        .shouldBe(not(visible));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    /**
     * This method uploads sound file
     * @param file path of sound file
     * @param description
     * @return
     */
    public IVRPage uploadSoundFile(String file, String description){
        uploadSoundFile(file);
        SelenideElement input = $("input[aria-label]");
        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(description);
        return clickButtonSaveOfModalWindow();
    }

    public IVRPage setInputsEntryPoint(Map<String, String> values, String item){
        sendInputsForm(values);
        selectItemComboBox(item);
        return  clickButtonSaveOfModalWindow();
    }

    public IVRPage createEntryPoint(Map<String, String> values, String item){
        clickButtonAdd(IVR_TITLE_ENTRY_POINTS);
        return setInputsEntryPoint(values, item);
    }

}

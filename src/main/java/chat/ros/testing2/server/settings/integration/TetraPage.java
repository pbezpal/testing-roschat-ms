package chat.ros.testing2.server.settings.integration;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;

public class TetraPage implements IntegrationPage {

    SelenideElement buttonSaveTetra = $("div.modal-wrapper button.v-btn.primary--text");
    private String locatorInput = "//div[@class='naming' and contains(text(), '%1$s')]" +
            "//ancestor::div[@class='modal-fields']//input";

    public TetraPage() {}

    @Step(value = "Заполняем поля для добавления сервера")
    @Override
    public void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()) {
            String input = entry.getKey();
            String value = entry.getValue();
            $x(String.format(locatorInput,input)).sendKeys(Keys.CONTROL + "a");
            $x(String.format(locatorInput,input)).sendKeys(Keys.BACK_SPACE);
            $x(String.format(locatorInput,input)).sendKeys(value);
        }
    }

    @Step(value = "Нажимаем нопку Сохранить")
    @Override
    public void clickButtonSave(){
        buttonSaveTetra.click();
    }


    public boolean addTetraServer(Map<String, String> mapInputValueTetra){
        clickButtonAdd();
        sendInputsForm(mapInputValueTetra);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        isNotShowLoaderSettings();
        return isExistsTableText(INTEGRATION_SERVICE_TETRA_NAME);
    }
}

package chat.ros.testing2.server.settings.integration;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;

public class TetraPage implements IntegrationPage {

    SelenideElement buttonSaveTetra = $("div.modal-wrapper button.v-btn.primary--text");

    public TetraPage() {}

    @Step(value = "Заполняем поля для добавления сервера")
    @Override
    public void sendH4InputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()) {
            String input = entry.getKey();
            String value = entry.getValue();
            $x("//div[@class='naming' and contains(text(), '" + input + "')]" +
                    "//ancestor::div[@class='modal-fields']//input").sendKeys(Keys.CONTROL + "a");
            $x("//div[@class='naming' and contains(text(), '" + input + "')]" +
                    "//ancestor::div[@class='modal-fields']//input").sendKeys(Keys.BACK_SPACE);
            $x("//div[@class='naming' and contains(text(), '" + input + "')]" +
                    "//ancestor::div[@class='modal-fields']//input").sendKeys(value);
        }
    }

    @Step(value = "Нажимаем нопку Сохранить")
    @Override
    public void clickButtonSave(){
        buttonSaveTetra.click();
    }


    public boolean addTetraServer(Map<String, String> mapInputValueTetra){
        clickButtonAdd();
        sendH4InputsForm(mapInputValueTetra);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        isNotShowLoaderSettings();
        return isExistsTableText(INTEGRATION_SERVICE_TETRA_NAME);
    }
}

package chat.ros.testing2.server.settings.integration;

import chat.ros.testing2.data.MSGeneralElements;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public class TetraPage extends IntegrationPage implements MSGeneralElements {

    SelenideElement buttonSaveTetra = $("div.modal-wrapper button.v-btn.primary--text");

    private Map<String, String> mapInputValueTetra = new HashMap() {{
        put("Название", INTEGRATION_SERVICE_TETRA_NAME);
        put("Описание", INTEGRATION_SERVICE_TETRA_DESCRIPTION);
    }};

    public TetraPage() {}

    @Step(value = "Заполняем поля для добавления сервера")
    @Override
    public void sendInputsForm(Map<String, String> mapInputValue){
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


    public IntegrationPage addTetraServer(){
        clickButtonAddService();
        sendInputsForm(mapInputValueTetra);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertTrue(isNotShowLoaderSettings(), "Элемент загрузки не пропал");
        assertTrue(isService(INTEGRATION_SERVICE_TETRA_NAME), "Сервис тетра не был добавлен");
        return new IntegrationPage();
    }
}

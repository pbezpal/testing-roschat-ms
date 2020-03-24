package chat.ros.testing2.server.settings.integration;

import chat.ros.testing2.data.MSGeneralElements;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TetraPage implements MSGeneralElements {

    SelenideElement buttonSaveTetra = $("div.modal-wrapper button.v-btn.primary--text");
    ElementsCollection listServersTetra = $$("table td");

    public TetraPage() {}

    @Step(value = "Вводим в поле {field} значение {value}")
    @Override
    public void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()) {
            String input = entry.getKey();
            String value = entry.getValue();
            $(By.xpath("//div[@class='naming' and contains(text(), '" + input + "')]" +
                    "//ancestor::div[@class='modal-fields']//input")).sendKeys(Keys.CONTROL + "a");
            $(By.xpath("//div[@class='naming' and contains(text(), '" + input + "')]" +
                    "//ancestor::div[@class='modal-fields']//input")).sendKeys(Keys.BACK_SPACE);
            $(By.xpath("//div[@class='naming' and contains(text(), '" + input + "')]" +
                    "//ancestor::div[@class='modal-fields']//input")).sendKeys(value);
        }
    }

    @Step(value = "Нажимаем нопку Сохранить")
    @Override
    public void clickButtonSave(){
        buttonSaveTetra.click();
    }

    @Step(value = "Проверяем, что сервер Tetra {server} был добавлен")
    public boolean isServerTetra(String server){
        try{
            listServersTetra.findBy(Condition.text(server)).shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }
}

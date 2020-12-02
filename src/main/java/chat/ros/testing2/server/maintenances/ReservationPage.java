package chat.ros.testing2.server.maintenances;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Selenide.$$;

public class ReservationPage implements MaintenancesPage {

    private ElementsCollection buttons = $$(".block-content.complex button .v-btn__content");

    public ReservationPage uploadConfigs(String file){
        SelenideElement uploadFile = buttons.find(Condition.text("Восстановить")).find("input[type='file']");
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript(
                "arguments[0].style.display = 'block';",
                uploadFile);
        uploadFile.sendKeys(file);

        return this;
    }

}

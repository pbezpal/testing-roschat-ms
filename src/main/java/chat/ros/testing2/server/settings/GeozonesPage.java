package chat.ros.testing2.server.settings;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class GeozonesPage implements SettingsPage {

    private String tableGeozona = "//table//td[contains(text(),'%1$s')]//ancestor::tr//button//i[text()='open_in_new']";
    private SelenideElement buttonAddBeacon = $x("//h2[text()='Beacons']//ancestor::" +
            "div[@class='block-wrapper']//button[@class='v-btn theme--light primary']");

    public GeozonesPage() {}

    @Step(value = "Нажимаем кнопку Открыть у геозоны {geozona}")
    protected GeozonesPage clickOpenGeoZone(String geozona){
        $x(String.format(tableGeozona,geozona)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку добавить в секции Beacons")
    public void clickButtonAddBeacons(){
        buttonAddBeacon.click();
    }

    public boolean addGeozone(Map<String, String> mapInputValueGeozone, String geozone){
        clickButtonAdd();
        sendLabelInputsForm(mapInputValueGeozone);
        clickButtonSave();
        return isExistsTableText(geozone,true);
    }

    public boolean addBeacon(String geozone, Map<String, String> mapInputValueBeacon, String beacon){
        clickOpenGeoZone(geozone);
        clickButtonAddBeacons();
        sendLabelInputsForm(mapInputValueBeacon);
        clickButtonSave();
        return isExistsTableText(beacon, true);
    }
}

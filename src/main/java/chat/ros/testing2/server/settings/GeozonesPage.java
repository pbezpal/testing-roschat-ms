package chat.ros.testing2.server.settings;

import io.qameta.allure.Step;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class GeozonesPage implements SettingsPage {

    public GeozonesPage() {}

    @Step(value = "Нажимаем кнопку Открыть у геозоны {geozona}")
    protected GeozonesPage clickOpenGeoZone(String geozona){
        $x("//table//td[contains(text(),'" + geozona + "')]//ancestor::tr//button//i[text()='open_in_new']").click();
        return this;
    }

    public boolean addGeozone(Map<String, String> mapInputValueGeozone, String geozone){
        clickButtonAdd();
        sendLabelInputsForm(mapInputValueGeozone);
        clickButtonSave();
        return isExistsTableText(geozone);
    }

    public boolean addBeacon(String geozone, Map<String, String> mapInputValueBeacon, String beacon){
        clickOpenGeoZone(geozone);
        clickButtonAdd();
        sendLabelInputsForm(mapInputValueBeacon);
        clickButtonSave();
        return isExistsTableText(beacon);
    }
}

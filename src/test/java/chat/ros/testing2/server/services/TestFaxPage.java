package chat.ros.testing2.server.services;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.services.FaxPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic(value = "Сервисы")
@Feature(value = "Факс")
@ExtendWith(ResourcesFaxPage.class)
@ExtendWith(WatcherTests.class)
public class TestFaxPage extends FaxPage {

    @Story(value = "Добавление номер факса с описанием")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Добавляем номер факса с описанием\n" +
            "3. Проверяем, что номер факса с описанием успешно добавлен")
    @Test
    void test_Add_Number_Fax_With_Description(){
        assertAll("Добаялем номер факса с описанием и проверяем, что номер с описанием был добавлен",
                () -> assertEquals(clickButtonAdd(FAX_NUMBERS_TITLE).isVisibleTitleModalWrapper(),
                        "Добавление номера факса",
                        "Не найден заголовок модального окна при добавлении номера факса"),
                () -> {sendNumberFaxes(FAX_NUMBER_WITH_DESCRIPTION, FAX_DESCRIPTION_FAX, "Сохранить");},
                () -> assertTrue(isItemTable(FAX_NUMBERS_TITLE, FAX_NUMBER_WITH_DESCRIPTION, true),
                        "Не найдена запись " + FAX_NUMBER_WITH_DESCRIPTION + " в таблице " + FAX_NUMBERS_TITLE),
                () -> assertTrue(isItemTable(FAX_NUMBERS_TITLE, FAX_DESCRIPTION_FAX, true),
                        "Не найдена запись " + FAX_DESCRIPTION_FAX + " в таблице " + FAX_NUMBERS_TITLE)
                );
    }

}

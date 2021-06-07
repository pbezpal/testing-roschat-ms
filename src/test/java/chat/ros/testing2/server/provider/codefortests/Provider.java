package chat.ros.testing2.server.provider.codefortests;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

public class Provider extends Routes implements IProvider {

    public Provider() {}

    private String titleProvider = null;
    private String descriptionProvider = null;
    private String addressProvider = null;
    private String aonProvider = null;
    private String usernameProvider = null;
    private String intervalProvider = null;

    private void verifyBeforeAddedProvider(){
        clickButtonSettings(TELEPHONY_PROVIDER_TITLE_FORM, "Добавить провайдера");
        if( ! getTitleOfModalWindow().equals("Провайдеры"))
            Allure.step("Не найден заголовок модального окна Провайдеры", Status.FAILED);
        if( ! isSubtitleModalWindow("Общее"))
            Allure.step("Не найден подзаголовок модального окна Общее", Status.FAILED);
        if( ! isSubtitleModalWindow("Регистрация настроек провайдера"))
            Allure.step("Не найден подзаголовок модального окна Регистрация настроек провайдера", Status.FAILED);
    }

    private void verifyAfterAddedProvider(Map<String, String> dataProvider){

        titleProvider = null;
        descriptionProvider = null;
        addressProvider = null;

        for(Map.Entry data: dataProvider.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_INPUT_NAME))
                titleProvider = data.getValue().toString();
            else if(data.getKey().equals(TELEPHONY_PROVIDER_INPUT_DESCRIPTION))
                descriptionProvider = data.getValue().toString();
            else if(data.getKey().equals(TELEPHONY_PROVIDER_INPUT_ADDRESS))
                addressProvider = data.getValue().toString();
        }

        assertAll("Проверяем, что провайдер добавлен в таблицу провайдеров",
                () -> assertTrue(isExistsTableText(titleProvider, true),
                        "Не отображается название " + titleProvider + " в таблице провайдеров"),
                () -> assertTrue(isExistsTableText(descriptionProvider, true),
                        "Не отображается описание " + descriptionProvider + " в таблице провайдеров"),
                () -> assertTrue(isExistsTableText(addressProvider, true),
                        "Не отображается адрес " + addressProvider + " в таблице провайдеров")
        );
    }


    @Override
    public void addProvider(Map<String, String> dataGeneralProvider, Map<String, String> dataRegistrationProvider) {
        verifyBeforeAddedProvider();
        setProvider(dataGeneralProvider, dataRegistrationProvider, true);
        verifyAfterAddedProvider(dataGeneralProvider);
    }

    @Override
    public void addProvider(Map<String, String> dataProvider) {
        verifyBeforeAddedProvider();
        setProvider(dataProvider);
        verifyAfterAddedProvider(dataProvider);
    }

    @Override
    public void verifyShowSettingsProvider(Map<String, String> dataProvider, boolean registration) {
        titleProvider = null;
        descriptionProvider = null;
        addressProvider = null;
        aonProvider = null;
        usernameProvider = null;
        intervalProvider = null;

        for (Map.Entry data : dataProvider.entrySet()) {
            if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_NAME))
                titleProvider = data.getValue().toString();
            else if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_DESCRIPTION))
                descriptionProvider = data.getValue().toString();
            else if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_ADDRESS))
                addressProvider = data.getValue().toString();
            else if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_AON))
                aonProvider = data.getValue().toString();
            if(registration) {
                if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_USERNAME))
                    usernameProvider = data.getValue().toString();
                else if(data.getKey().equals(TELEPHONY_PROVIDER_INPUT_INTERVAL))
                    intervalProvider = data.getValue().toString();
            }
        }

        clickButtonTableProvider(titleProvider, "Изменить");

        assertAll("1. Нажимаем кнопку изманить\n" +
                        "2. Проверяем, что в разделе Провайдер отображаются общие настройки провайдера " + titleProvider,
                () -> assertTrue(isSubtitleProviderForm("Общее", true),
                        "Не отображается подзаголовок Общее в настройках Провайдера " + titleProvider),
                () -> assertTrue(isContentSettingProvider(titleProvider, true),
                        "Не отображается название " + titleProvider + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(descriptionProvider, true),
                        "Не отображается описание " + descriptionProvider + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(addressProvider, true),
                        "Не отображается адрес провайдера " + addressProvider + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(aonProvider, true),
                        "Не отображается АОН " + aonProvider + " в настройках провадера"),
                () -> {
                    if(registration){
                        assertAll("Проверяем, что в разделе Провайдер отображаются отображаются настройки регистрации",
                                () -> assertTrue(isSubtitleProviderForm("Регистрация", true),
                                        "Не отображается подзаголовок Регистрация в настройках Провайдера " + titleProvider),
                                () -> assertTrue(isContentSettingProvider(usernameProvider, true),
                                        "Не отображается Имя пользователя " + usernameProvider + " в настройках" +
                                                " провадера после редактирования"),
                                () -> assertTrue(isContentSettingProvider(intervalProvider, true),
                                        "Не отображается Интервал регистрации " + intervalProvider + " в " +
                                                "настройках провадера после редактирования")
                        );
                    }else{
                        assertTrue(isSubtitleProviderForm("Регистрация", false),
                                "Отображается подзаголовок Регистрация в настройках Провайдера " + titleProvider);
                    }
                }

        );

    }
}

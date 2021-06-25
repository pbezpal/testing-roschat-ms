package chat.ros.testing2.server.provider.codefortests;

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

    /**
     * this method verifies title and subtitle on modal window
     */
    private void checkHeaderAndSubheadersModalWindow(){
        assertEquals(getTitleOfModalWindow(), TELEPHONY_PROVIDER_TITLE_FORM, "Не найден заголовок модального окна " + TELEPHONY_PROVIDER_TITLE_FORM);
        isSubtitleModalWindow("Общее").isSubtitleModalWindow("Регистрация настроек провайдера");
    }

    /**
     * this method verifies the provider data on table
     * @param dataProvider the provider data
     */
    private void verifyProviderDataOnTable(Map<String, String> dataProvider, boolean show){

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


        isExistsTableText(titleProvider, show)
                .isExistsTableText(addressProvider, show);
        if (descriptionProvider != null && descriptionProvider.length() > 0)
            isExistsTableText(descriptionProvider, show);
    }

    @Override
    public void checkHeaderAndSubtitlesWindowModalProvider(String... provider) {
        if(provider.length > 0){
            clickButtonTableProvider(provider[0], provider[1]);
            if(provider[1].equals("Изменить"))
                clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_PROVIDER, "Настроить");
        }
        else clickButtonSettings(TELEPHONY_PROVIDER_TITLE_FORM, "Добавить провайдера");
        isModalWindow(true);
        checkHeaderAndSubheadersModalWindow();
        clickButtonClose().isModalWindow(false);
    }

    @Override
    public void addProvider(Map<String, String> dataGeneralProvider, Map<String, String> dataRegistrationProvider) {
        clickButtonSettings(TELEPHONY_PROVIDER_TITLE_FORM, "Добавить провайдера");
        isModalWindow(true);
        setProvider(dataGeneralProvider, dataRegistrationProvider, true);
        verifyProviderDataOnTable(dataGeneralProvider, true);
    }

    @Override
    public void addProvider(Map<String, String> dataProvider) {
        clickButtonSettings(TELEPHONY_PROVIDER_TITLE_FORM, "Добавить провайдера");
        isModalWindow(true);
        setProvider(dataProvider);
        verifyProviderDataOnTable(dataProvider, true);
    }

    @Override
    public void checkHeadersAndTitlesViewProviderSettings(String provider, boolean registration) {
        clickButtonTableProvider(provider, "Изменить");
        isSubtitleProviderForm("Общее", true)
                .isContentSettingProvider("Название", true)
                .isContentSettingProvider("Описание", true)
                .isContentSettingProvider("Aдрес провайдера (с портом)", true)
                .isContentSettingProvider("AOH", true);
        if(registration){
            isSubtitleProviderForm("Регистрация", true)
                    .isContentSettingProvider("Имя пользователя", true)
                    .isContentSettingProvider("Интервал регистрации", true);
        }else isSubtitleProviderForm("Регистрация", false);
    }

    @Override
    public void editProvider(String provider, Map<String, String> dataProvider, boolean registration, String buttonEdit) {
        clickButtonTableProvider(provider, buttonEdit);
        if(buttonEdit.equals("Изменить"))
            clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_PROVIDER, "Настроить");
        isModalWindow(true);
        selectCheckboxProvider(registration).setProvider(dataProvider);
        if(buttonEdit.equals("Изменить"))
            checkViewProviderSettings(dataProvider, registration);
        else
            verifyProviderDataOnTable(dataProvider, true);
    }

    @Override
    public void checkViewProviderSettings(Map<String, String> dataProvider, boolean registration) {
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
            if (registration) {
                if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_USERNAME))
                    usernameProvider = data.getValue().toString();
                else if (data.getKey().equals(TELEPHONY_PROVIDER_INPUT_INTERVAL))
                    intervalProvider = data.getValue().toString();
            }
        }

        isContentSettingProvider(titleProvider, true)
                .isContentSettingProvider(addressProvider, true);

        if (descriptionProvider != null && descriptionProvider.length() > 0)
            isContentSettingProvider(descriptionProvider, true);

        if (aonProvider != null && aonProvider.length() > 0)
            isContentSettingProvider(aonProvider, true);

        if (registration) {
            isContentSettingProvider(usernameProvider, true)
                    .isContentSettingProvider(intervalProvider, true);
        }
    }


    @Override
    public void checkExistProviderInTableProviders(Map<String, String> dataProvider, boolean show) {
        verifyProviderDataOnTable(dataProvider, show);
    }

    @Override
    public void deleteProvider(String provider, Map<String, String> dataProvider, boolean tableDelete) {
        if(tableDelete)
            clickButtonTableProvider(provider, "Удалить");
        else {
            clickButtonTableProvider(provider, "Изменить");
            clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_PROVIDER, "Удалить");
        }
        clickButtonConfirmAction("Продолжить");
        verifyProviderDataOnTable(dataProvider, false);
    }
}

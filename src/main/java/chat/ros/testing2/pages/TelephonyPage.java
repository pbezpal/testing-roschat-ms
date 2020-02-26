package chat.ros.testing2.pages;

import static org.junit.gen5.api.Assertions.assertTrue;

public class TelephonyPage extends SettingsPage {

    //Общие переменные
    private String telephoneSection = "Телефония";

    //Переменные для настроек сети
    private String titleFormNetwork = "Сеть";
    private String inputPublicAddress = "Публичный адрес";
    private String valuePublicAddress = "188.170.5.169";
    private String inputFrondEnd = "Внешний интерфейс";
    private String valueFrontEnd = "10.10.199.47";
    private String inputInsideInterface = "Внутренний интерфейс";
    private String valueInsideInterface = "10.10.199.47";
    private String textCheckTelephone = "Настройки телефонии корректны.";

    //Параметры для настроек SIP-сервера
    private String titleFormSipServer = "SIP-сервер";
    private String nameSpeechPorts = "Речевые порты";
    private String inputMinimapPort = "Минимальный порт";
    private String valueMinimalPort = "49000";
    private String inputMaximumPort = "Максимальный порт";
    private String valueMaximumPort = "49150";

    //Параметры для настройки Turnserver
    private String titleFormTurnserver = "TURN/STUN";
    private String inputSecret = "Секрет";
    private String valueSecret = "Secret";

    public TelephonyPage () {}


    public TelephonyPage setNetwork(){
        //Проверяем, настрена ли сеть
        if(isNotValueInField(inputPublicAddress, valuePublicAddress)
                || isNotValueInField(inputFrondEnd, valueFrontEnd)
                || isNotValueInField(inputInsideInterface, valueInsideInterface)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(titleFormNetwork, getButtonSetting());
            //Проверяем, появилась ли форма редактирования
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим публичный адрес
            sendInputForm(inputPublicAddress, valuePublicAddress);
            //Вводим внешний интерфейс
            sendInputForm(inputFrondEnd, valueFrontEnd);
            //Вводим внутренний интерфес
            sendInputForm(inputInsideInterface, valueInsideInterface);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(getButtonRestartServices());
        }

        //Нажимаем кнопку Проверить
        clickButtonSettings(titleFormNetwork, getButtonCheck());
        //Проверяем, появилась ли форма проверки настроек
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        //Проверяем, что настройки сервера корректны
        assertTrue(isCheckSettings(textCheckTelephone), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();

        return this;

    }

    public TelephonyPage setSipServer(){
        //Проверяем настроен ли SIP сервер
        if(isNotValueInField(nameSpeechPorts, valueMinimalPort) && isNotValueInField(nameSpeechPorts, valueMinimalPort)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(titleFormSipServer, getButtonSetting());
            //Проверяем, появилась ли форма редактирования
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим минимальный порт
            sendInputForm(inputMinimapPort, valueMinimalPort);
            //Вводим максимальный порт
            sendInputForm(inputMaximumPort, valueMaximumPort);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(getButtonRestartServices());
        }

        return this;
    }

    public TelephonyPage setTurnserver(){
        if(isNotValueInField(inputSecret, valueSecret)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(titleFormTurnserver, getButtonSetting());
            //Проверяем, появилась ли форма редактирования
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим значение в поле Секрет
            sendInputForm(inputSecret, valueSecret);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(getButtonRestartServices());
        }

        return this;
    }

}

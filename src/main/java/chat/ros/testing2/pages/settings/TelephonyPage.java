package chat.ros.testing2.pages.settings;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public class TelephonyPage extends SettingsPage {

    //Общие переменные
    private String telephoneSection = "Телефония";

    public TelephonyPage () {}


    public TelephonyPage setNetwork(){
        //Проверяем, настрена ли сеть
        if(isNotValueInField(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS)
                || isNotValueInField(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP)
                || isNotValueInField(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            //Проверяем, появилась ли форма редактирования
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим публичный адрес
            sendInputForm(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS);
            //Вводим внешний интерфейс
            sendInputForm(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP);
            //Вводим внутренний интерфес
            sendInputForm(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(SETTINGS_BUTTON_RESTART);
        }

        //Нажимаем кнопку Проверить
        clickButtonSettings(TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_CHECK);
        //Проверяем, появилась ли форма проверки настроек
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        //Проверяем, что настройки сервера корректны
        assertTrue(isCheckSettings(TELEPHONY_NETWORK_TEXT_CHECK), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();

        return this;

    }

    public TelephonyPage setSipServer(){
        //Проверяем настроен ли SIP сервер
        if(isNotValueInField(TELEPHONY_SIP_INPUT_SPEECH_PORTS, TELEPHONY_SIP_MIN_PORT) && isNotValueInField(TELEPHONY_SIP_INPUT_SPEECH_PORTS, TELEPHONY_SIP_MAX_PORT)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            //Проверяем, появилась ли форма редактирования
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим минимальный порт
            sendInputForm(TELEPHONY_SIP_INPUT_MIN_PORT, TELEPHONY_SIP_MIN_PORT);
            //Вводим максимальный порт
            sendInputForm(TELEPHONY_SIP_INPUT_MAX_PORT, TELEPHONY_SIP_MAX_PORT);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(SETTINGS_BUTTON_RESTART);
        }

        return this;
    }

    public TelephonyPage setTurnserver(){
        if(isNotValueInField(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            //Проверяем, появилась ли форма редактирования
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим значение в поле Секрет
            sendInputForm(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(SETTINGS_BUTTON_RESTART);
        }

        return this;
    }

}

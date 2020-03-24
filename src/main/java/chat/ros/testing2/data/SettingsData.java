package chat.ros.testing2.data;

import static chat.ros.testing2.data.GetDataTests.getProperty;

public interface SettingsData {

    String config = "SettingsServer.properties";

    /****** Общие параметры ******/
    String SETTINGS_HEAD_SECTION = getProperty("SETTINGS_HEAD_SECTION", config);
    String SETTINGS_BUTTON_SETTING = getProperty("SETTINGS_BUTTON_SETTING", config);
    String SETTINGS_BUTTON_CHECK = getProperty("SETTINGS_BUTTON_CHECK", config);
    String SETTINGS_BUTTON_RESTART = getProperty("SETTINGS_BUTTON_RESTART", config);


    /*********************************************** Раздел Сервер ***************************************************/

    /****** Параметры для настройки раздела Подключение ******/
    String SERVER_CONNECT_TITLE_FORM = getProperty("SERVER_CONNECT_TITLE_FORM", config);
    String SERVER_CONNECT_INPUT_PUBLIC_NETWORK = getProperty("SERVER_CONNECT_INPUT_PUBLIC_NETWORK", config);
    String SERVER_CONNECT_TEXT_CHECK_SERVER = getProperty("SERVER_CONNECT_TEXT_CHECK_SERVER", config);

    /****** Параметры для настройки Push сервера ******/
    String SERVER_PUSH_TITLE_FORM = getProperty("SERVER_PUSH_TITLE_FORM", config);
    String SERVER_PUSH_INPUT_HOST = getProperty("SERVER_PUSH_INPUT_HOST", config);
    String SERVER_PUSH_HOST_SERVER = getProperty("SERVER_PUSH_HOST_SERVER", config);
    String SERVER_PUSH_INPUT_LOGIN = getProperty("SERVER_PUSH_INPUT_LOGIN", config);
    String SERVER_PUSH_LOGIN_SERVER = getProperty("SERVER_PUSH_LOGIN_SERVER", config);
    String SERVER_PUSH_INPUT_PORT = getProperty("SERVER_PUSH_INPUT_PORT", config);
    String SERVER_PUSH_PORT_SERVER = getProperty("SERVER_PUSH_PORT_SERVER", config);
    String SERVER_PUSH_INPUT_PASSWORD = getProperty("SERVER_PUSH_INPUT_PASSWORD", config);
    String SERVER_PUSH_PASSWORD_SERVER = getProperty("SERVER_PUSH_PASSWORD_SERVER", config);
    String SERVER_PUSH_BUTTON_UPDATE_LICENSE = getProperty("SERVER_PUSH_BUTTON_UPDATE_LICENSE", config);
    String SERVER_PUSH_TEXT_CHECK_LICENSE = getProperty("SERVER_PUSH_TEXT_CHECK_LICENSE", config);


    /********************************************** Раздел Телефония *************************************************/

    /****** Параметры для настройки Сети ******/
    String TELEPHONY_NETWORK_TITLE_FORM = getProperty("TELEPHONY_NETWORK_TITLE_FORM", config);
    String TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS = getProperty("TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS", config);
    String TELEPHONY_NETWORK_PUBLIC_ADDRESS = getProperty("TELEPHONY_NETWORK_PUBLIC_ADDRESS", config);
    String TELEPHONY_NETWORK_INPUT_FRONT_DEV = getProperty("TELEPHONY_NETWORK_INPUT_FRONT_DEV", config);
    String TELEPHONY_NETWORK_FRONT_IP = getProperty("TELEPHONY_NETWORK_FRONT_IP", config);
    String TELEPHONY_NETWORK_INPUT_INSIDE_DEV = getProperty("TELEPHONY_NETWORK_INPUT_INSIDE_DEV", config);
    String TELEPHONY_NETWORK_INSIDE_IP = getProperty("TELEPHONY_NETWORK_INSIDE_IP", config);
    String TELEPHONY_NETWORK_TEXT_CHECK = getProperty("TELEPHONY_NETWORK_TEXT_CHECK", config);

    /****** Параметры для настройки SIP-сервера ******/
    String TELEPHONY_SIP_TITLE_FORM = getProperty("TELEPHONY_SIP_TITLE_FORM", config);
    String TELEPHONY_SIP_INPUT_SPEECH_PORTS = getProperty("TELEPHONY_SIP_INPUT_SPEECH_PORTS", config);
    String TELEPHONY_SIP_INPUT_MIN_PORT = getProperty("TELEPHONY_SIP_INPUT_MIN_PORT", config);
    String TELEPHONY_SIP_MIN_PORT = getProperty("TELEPHONY_SIP_MIN_PORT", config);
    String TELEPHONY_SIP_INPUT_MAX_PORT = getProperty("TELEPHONY_SIP_INPUT_MAX_PORT", config);
    String TELEPHONY_SIP_MAX_PORT = getProperty("TELEPHONY_SIP_MAX_PORT", config);

    /****** Параметры для настройки TURN сервера ******/
    String TELEPHONY_TURN_TITLE_FORM = getProperty("TELEPHONY_TURN_TITLE_FORM", config);
    String TELEPHONY_TURN_INPUT_SECRET = getProperty("TELEPHONY_TURN_INPUT_SECRET", config);
    String TELEPHONY_TURN_SECRET = getProperty("TELEPHONY_TURN_SECRET", config);


    /************************************************ Раздел Почта ***************************************************/

    /****** Параметры для настройки раздела Подключение ******/
    String MAIL_CONNECT_INPUT_EMAIL_SERVER = getProperty("MAIL_CONNECT_INPUT_EMAIL_SERVER", config);
    String MAIL_CONNECT_INPUT_USERNAME = getProperty("MAIL_CONNECT_INPUT_USERNAME", config);
    String MAIL_CONNECT_INPUT_PASSWORD = getProperty("MAIL_CONNECT_INPUT_PASSWORD", config);
    String MAIL_CONNECT_INPUT_EMAIL_PORT = getProperty("MAIL_CONNECT_INPUT_EMAIL_PORT", config);
    String MAIL_CONNECT_TEXT_CHECK = getProperty("MAIL_CONNECT_TEXT_CHECK", config);

    /****** Параметры для настройки раздела Контактная информация ******/
    String MAIL_CONTACT_TITLE_FORM = getProperty("MAIL_CONTACT_TITLE_FORM", config);
    String MAIL_CONTACT_INPUT_FROM_USER = getProperty("MAIL_CONTACT_INPUT_FROM_USER", config);
    String MAIL_CONTACT_INPUT_FROM_MAIL = getProperty("MAIL_CONTACT_INPUT_FROM_MAIL", config);

    /****** Порты почтового сервера ******/
    String MAIL_PORT_NO_SECURITY = getProperty("MAIL_PORT_NO_SECURITY", config);
    String MAIL_PORT_SSL = getProperty("MAIL_PORT_SSL", config);
    String MAIL_PORT_STARTTLS = getProperty("MAIL_PORT_STARTTLS", config);

    /****** Методы защиты соединения ******/
    String MAIL_TYPE_SECURITY_NO = getProperty("MAIL_TYPE_SECURITY_NO", config);
    String MAIL_TYPE_SECURITY_SSL = getProperty("MAIL_TYPE_SECURITY_SSL", config);
    String MAIL_TYPE_SECURITY_STARTTLS = getProperty("MAIL_TYPE_SECURITY_STARTTLS", config);

    /****** Параметры настройки постового сервера Infotek ******/
    String MAIL_INFOTEK_SERVER = getProperty("MAIL_INFOTEK_SERVER", config);
    String MAIL_INFOTEK_USERNAME = getProperty("MAIL_INFOTEK_USERNAME", config);
    String MAIL_INFOTEK_PASSWORD = getProperty("MAIL_INFOTEK_PASSWORD", config);
    String MAIL_INFOTEK_FROM_USER = getProperty("MAIL_INFOTEK_FROM_USER", config);
    String MAIL_INFOTEK_FROM_MAIL = getProperty("MAIL_INFOTEK_FROM_MAIL", config);

    /****** Параметры настройки постового сервера Google ******/
    String MAIL_GOOGLE_SERVER = getProperty("MAIL_GOOGLE_SERVER", config);
    String MAIL_GOOGLE_USERNAME = getProperty("MAIL_GOOGLE_USERNAME", config);
    String MAIL_GOOGLE_PASSWORD = getProperty("MAIL_GOOGLE_PASSWORD", config);
    String MAIL_GOOGLE_FROM_USER = getProperty("MAIL_GOOGLE_FROM_USER", config);
    String MAIL_GOOGLE_FROM_MAIL = getProperty("MAIL_GOOGLE_FROM_MAIL", config);

    /********************************************* Раздел Интеграция *************************************************/

    /****** Параметры настройки сервера Тетра ******/
    String INTEGRATION_SERVICE_TETRA_TYPE = getProperty("INTEGRATION_SERVICE_TETRA_TYPE", config);
    String INTEGRATION_SERVICE_TETRA_NAME = getProperty("INTEGRATION_SERVICE_TETRA_NAME", config);
    String INTEGRATION_SERVICE_TETRA_DESCRIPTION = getProperty("INTEGRATION_SERVICE_TETRA_DESCRIPTION", config);
}

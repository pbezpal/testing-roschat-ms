package chat.ros.testing2.data;

public interface SettingsData {

    /****** Общие параметры ******/
    String SETTINGS_HEAD_SECTION = "Настройки";
    String SETTINGS_BUTTON_SETTING = "Настроить";
    String SETTINGS_BUTTON_CHECK = "Проверить";
    String SETTINGS_BUTTON_RESTART = "Перезапустить";


    /*********************************************** Раздел Сервер ***************************************************/

    /****** Параметры для настройки раздела Подключение ******/
    String SERVER_CONNECT_TITLE_FORM = "Подключение";
    String SERVER_CONNECT_INPUT_PUBLIC_NETWORK = "Внешний адрес сервера";
    String SERVER_CONNECT_TEXT_CHECK_SERVER = "Настройки сервера корректны.";

    /****** Параметры для настройки Push сервера ******/
    String SERVER_PUSH_TITLE_FORM = "Лицензирование и обсуживание";
    String SERVER_PUSH_INPUT_HOST = "IP адрес";
    String SERVER_PUSH_HOST_SERVER = "firelink.me";
    String SERVER_PUSH_INPUT_LOGIN = "Логин";
    String SERVER_PUSH_LOGIN_SERVER = "testing2";
    String SERVER_PUSH_INPUT_PORT = "Порт";
    String SERVER_PUSH_PORT_SERVER = "8088";
    String SERVER_PUSH_INPUT_PASSWORD = "Пароль";
    String SERVER_PUSH_PASSWORD_SERVER = "lJddfDnwycX0ag7o";
    String SERVER_PUSH_BUTTON_UPDATE_LICENSE = "Обновить лицензию";
    String SERVER_PUSH_TEXT_CHECK_LICENSE = "Лицензия успешно обновлена";


    /********************************************** Раздел Телефония *************************************************/

    /****** Параметры для настройки Сети ******/
    String TELEPHONY_NETWORK_TITLE_FORM = "Сеть";
    String TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS = "Публичный адрес";
    String TELEPHONY_NETWORK_PUBLIC_ADDRESS = "188.170.5.169";
    String TELEPHONY_NETWORK_INPUT_FRONT_DEV = "Внешний интерфейс";
    String TELEPHONY_NETWORK_FRONT_IP = "10.10.199.47";
    String TELEPHONY_NETWORK_INPUT_INSIDE_DEV = "Внутренний интерфейс";
    String TELEPHONY_NETWORK_INSIDE_IP = "10.10.199.47";
    String TELEPHONY_NETWORK_TEXT_CHECK = "Настройки телефонии корректны.";

    /****** Параметры для настройки SIP-сервера ******/
    String TELEPHONY_SIP_TITLE_FORM = "SIP-сервер";
    String TELEPHONY_SIP_INPUT_SPEECH_PORTS = "Речевые порты";
    String TELEPHONY_SIP_INPUT_MIN_PORT = "Минимальный порт";
    String TELEPHONY_SIP_MIN_PORT = "49000";
    String TELEPHONY_SIP_INPUT_MAX_PORT = "Максимальный порт";
    String TELEPHONY_SIP_MAX_PORT = "49150";

    /****** Параметры для настройки TURN сервера ******/
    String TELEPHONY_TURN_TITLE_FORM = "TURN/STUN";
    String TELEPHONY_TURN_INPUT_SECRET = "Секрет";
    String TELEPHONY_TURN_SECRET = "Secret";


    /************************************************ Раздел Почта ***************************************************/

    /****** Параметры для настройки раздела Подключение ******/
    String MAIL_CONNECT_INPUT_EMAIL_SERVER = "Адрес почтового сервера";
    String MAIL_CONNECT_INPUT_USERNAME = "Имя пользователя";
    String MAIL_CONNECT_INPUT_PASSWORD = "Пароль пользователя";
    String MAIL_CONNECT_INPUT_EMAIL_PORT = "Порт почтового сервера";
    String MAIL_CONNECT_TEXT_CHECK= "Настройки почты корректны.";

    /****** Параметры для настройки раздела Контактная информация ******/
    String MAIL_CONTACT_TITLE_FORM = "Контактная информация";
    String MAIL_CONTACT_INPUT_FROM_USER = "Имя отправителя";
    String MAIL_CONTACT_INPUT_FROM_MAIL = "Почтовый адрес";

    /****** Порты почтового сервера ******/
    String MAIL_PORT_NO_SECURITY = "25";
    String MAIL_PORT_SSL = "465";
    String MAIL_PORT_STARTTLS = "587";

    /****** Методы защиты соединения ******/
    String MAIL_TYPE_SECURITY_NO = "no";
    String MAIL_TYPE_SECURITY_SSL = "SSL/TLS";
    String MAIL_TYPE_SECURITY_STARTTLS = "STARTTLS";

    /****** Параметры настройки постового сервера Infotek ******/
    String MAIL_INFOTEK_SERVER = "mail.infotek.ru";
    String MAIL_INFOTEK_USERNAME = "Noreply.roschat";
    String MAIL_INFOTEK_PASSWORD = "SmGRz6bc";
    String MAIL_INFOTEK_FROM_USER = "noreply.roschat";
    String MAIL_INFOTEK_FROM_MAIL = "Noreply.roschat@infotek.ru";

    /****** Параметры настройки постового сервера Google ******/
    String MAIL_GOOGLE_SERVER = "smtp.gmail.com";
    String MAIL_GOOGLE_USERNAME = "testroschat";
    String MAIL_GOOGLE_PASSWORD = "Nimd@123";
    String MAIL_GOOGLE_FROM_USER = "testroschat";
    String MAIL_GOOGLE_FROM_MAIL= "testroschat@gmail.com";

    /********************************************* Раздел Интеграция *************************************************/

    /****** Параметры настройки сервера Тетра ******/
    String INTEGRATION_SERVICE_TETRA_TYPE = "МиниКом TETRA";
    String INTEGRATION_SERVICE_TETRA_NAME = "Тестовый сервер ТЕТРА";
    String INTEGRATION_SERVICE_TETRA_DESCRIPTION = "Это тестовый сервер ТЕТРА для РОСЧАТА";

    /****** Параметры настройки Офис-Монитора ******/
    String INTEGRATION_SERVICE_OM_TYPE = "Офис-Монитор";
    String INTEGRATION_SERVICE_OM_IP_ADDRESS = "192.168.254.3";
    String INTEGRATION_SERVICE_OM_PORT_BD = "3306";
    String INTEGRATION_SERVICE_OM_LOGIN_DB = "root";

    /****** Параметры настройки Active Directory ******/
    String INTEGRATION_SERVICE_AD_TYPE = "Active Directory";
    String INTEGRATION_SERVICE_AD_SERVER = "192.168.254.254";
    String INTEGRATION_SERVICE_AD_PORT = "3268";
    String INTEGRATION_SERVICE_AD_BASE_DN = "DC=infotek,DC=lan";
    String INTEGRATION_SERVICE_AD_USERNAME = "infotek\\scan";
    String INTEGRATION_SERVICE_AD_PASSWORD = "123456";

    /************************************************ Раздел SNMP ****************************************************/

    String SNMP_TITLE_FORM = "SNMP-мониторинг";
    String SNMP_ADDRESS_SERVER = "10.10.199.249";
    String SNMP_PORT_SERVER = "10051";

    /******************************************** Раздел Настройка СУ ************************************************/

    String USER_FIRST_NAME = "Тестовый";
    String USER_LAST_NAME = "Тест";
    String USER_PATRON_NAME = "Тестович";
    String USER_LOGIN = "Testuser";
    String USER_PASSWORD = "12345678";
    String USER_BUTTON_CONTINUE = "Продолжить";
}

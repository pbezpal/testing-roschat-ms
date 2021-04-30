package chat.ros.testing2.data;

public interface SettingsData {

    /****** Общие параметры ******/
    String SETTINGS_HEAD_SECTION = "Настройки";
    String SETTINGS_BUTTON_SETTING = "Настроить";
    String SETTINGS_BUTTON_CHECK = "Проверить";
    String SETTINGS_BUTTON_RESTART = "Перезапустить";
    String SETTINGS_BUTTON_DELETE = "Удалить";
    String SETTINGS_BUTTON_CONTINUE = "Продолжить";


    /*********************************************** Раздел Сервер ***************************************************/

    /****** Параметры для настройки раздела Подключение ******/
    String SERVER_CONNECT_TITLE_FORM = "Подключение";
    String SERVER_CONNECT_INPUT_PUBLIC_NETWORK = "Внешний адрес сервера";
    String SERVER_CONNECT_INPUT_HTTP_PORT = "HTTP порт";
    String SERVER_CONNECT_HTTP_PORT = "80";
    String SERVER_CONNECT_HTTP_OTHER_PORT = "88";
    String SERVER_CONNECT_INPUT_HTTPS_PORT = "HTTPS порт";
    String SERVER_CONNECT_HTTPS_PORT = "443";
    String SERVER_CONNECT_HTTPS_OTHER_PORT = "446";
    String SERVER_CONNECT_INPUT_WEBSOCKET_PORT = "WebSocket порт";
    String SERVER_CONNECT_WEBSOCKET_PORT = "8080";
    String SERVER_CONNECT_WEBSOCKET_OTHER_PORT = "8088";
    String SERVER_CONNECT_TEXT_CHECK_SERVER = "Настройки сервера корректны.";
    String SERVER_CONNECT_FIELD_PORTS = "Порты (http, https, websocket):";

    /****** Параметры для настройки Push сервера ******/
    String SERVER_PUSH_TITLE_FORM = "Лицензирование и обслуживание";
    String SERVER_PUSH_INPUT_HOST = "Адрес сервера";
    String SERVER_PUSH_HOST_SERVER = "firelink.me";
    String SERVER_PUSH_INPUT_LOGIN = "Логин";
    String SERVER_PUSH_LOGIN_SERVER = "ormp2";
    String SERVER_PUSH_INPUT_PORT = "Порт";
    String SERVER_PUSH_PORT_SERVER = "8088";
    String SERVER_PUSH_INPUT_PASSWORD = "Пароль";
    String SERVER_PUSH_PASSWORD_SERVER = "ZiAHFaJaGx";
    String SERVER_PUSH_BUTTON_UPDATE_LICENSE = "Обновить лицензию";
    String SERVER_PUSH_TEXT_CHECK_LICENSE = "Лицензия успешно обновлена";


    /********************************************** Раздел Телефония *************************************************/

    String TELEPHONY_INPUT_SPEECH_PORTS = "Речевые порты";

    /****** Параметры для настройки Сети ******/
    String TELEPHONY_NETWORK_TITLE_FORM = "Сеть";
    String TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS = "Публичный адрес";
    String TELEPHONY_NETWORK_PUBLIC_ADDRESS = "10.10.11.183";
    String TELEPHONY_NETWORK_INPUT_FRONT_DEV = "Внешний интерфейс";
    String TELEPHONY_NETWORK_FRONT_IP = "10.10.11.183";
    String TELEPHONY_NETWORK_INPUT_INSIDE_DEV = "Внутренний интерфейс";
    String TELEPHONY_NETWORK_INSIDE_IP = "10.10.11.183";
    String TELEPHONY_NETWORK_TEXT_CHECK = "Настройки телефонии корректны.";

    /****** Параметры для настройки SIP-сервера ******/
    String TELEPHONY_SIP_TITLE_FORM = "SIP-сервер";
    String TELEPHONY_SIP_INPUT_MIN_PORT = "Минимальный порт";
    String TELEPHONY_SIP_MIN_PORT = "49000";
    String TELEPHONY_SIP_MIN_PORT_MORE_MAX_PORT = "50000";
    String TELEPHONY_SIP_INPUT_MAX_PORT = "Максимальный порт";
    String TELEPHONY_SIP_MAX_PORT = "49150";

    /****** Параметры для настройки TURN сервера ******/
    String TELEPHONY_TURN_TITLE_FORM = "TURN/STUN";
    String TELEPHONY_TURN_INPUT_MIN_PORT = "Минимальный порт";
    String TELEPHONY_TURN_MIN_PORT = "49153";
    String TELEPHONY_TURN_MIN_PORT_MORE_MAX_PORT = "50000";
    String TELEPHONY_TURN_INPUT_MAX_PORT = "Максимальный порт";
    String TELEPHONY_TURN_MAX_PORT = "49183";
    String TELEPHONY_TURN_INPUT_REALM = "Realm";
    String TELEPHONY_TURN_REALM = "realm";
    String TELEPHONY_TURN_INPUT_SECRET = "Секрет";
    String TELEPHONY_TURN_SECRET = "Secret";


    /************************************************ Раздел Почта ***************************************************/

    /****** Параметры для настройки раздела Подключение ******/
    String MAIL_CONNECT_INPUT_EMAIL_SERVER = "Адрес почтового сервера";
    String MAIL_CONNECT_INPUT_USERNAME = "Имя пользователя";
    String MAIL_CONNECT_INPUT_PASSWORD = "Пароль пользователя";
    String MAIL_CONNECT_INPUT_EMAIL_PORT = "Порт почтового сервера";
    String MAIL_CONTACT_INPUT_FROM_USER = "Имя отправителя";
    String MAIL_CONTACT_INPUT_FROM_MAIL = "Почтовый адрес";
    String MAIL_CONNECT_TEXT_CHECK= "Настройки почты корректны.";


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

    /*********************************************** Раздел Геозоны **************************************************/
    String GEOZONES_NAME_ZONA = "test";
    String GEOZONES_WIDTH_ZONA = "67";
    String GEOZONES_LENGHT_ZONA = "34";
    String GEOZONES_RADIUS_ZONA = "543";

    /***** Параметры добавления бекона ******/
    String GEOZONES_BEACONE_INDICATOR = "b9407f30-f5f8-466e-aff9-25556b57fe6d";
    String GEOZONES_BEACONE_MINOR = "7686";
    String GEOZONES_BEACONE_MAJOR = "1239";

    /********************************************* Раздел Интеграция *************************************************/

    /****** Параметры настройки сервера Тетра ******/
    String INTEGRATION_SERVICE_TETRA_TYPE = "МиниКом TETRA";
    String INTEGRATION_SERVICE_TETRA_NAME = "Тестовый сервер ТЕТРА";
    String INTEGRATION_SERVICE_TETRA_DESCRIPTION = "Это тестовый сервер ТЕТРА для РОСЧАТА";

    /****** Параметры настройки СКУД Офис-Монитора ******/
    String INTEGRATION_SERVICE_OM_TYPE = "Офис-Монитор";
    String INTEGRATION_SERVICE_OM_IP_ADDRESS = "192.168.254.3";
    String INTEGRATION_SERVICE_OM_WRONG_IP_ADDRESS = "192.168.254.100";
    String INTEGRATION_SERVICE_OM_PORT_BD = "3306";
    String INTEGRATION_SERVICE_OM_LOGIN_DB = "root";

    /****** Параметры настройки СКУД Орион ******/
    String INTEGRATION_SERVICE_ORION_TYPE = "Орион";
    String INTEGRATION_SERVICE_ORION_IP_ADDRESS = "192.168.249.173";
    String INTEGRATION_SERVICE_ORION_WRONG_IP_ADDRESS = "192.168.249.100";
    String INTEGRATION_SERVICE_ORION_PORT = "8090";
    String INTEGRATION_SERVICE_ORION_OUTGOING_PORT = "Дверь 1";

    /****** Параметры настройки СКУД PERCo ******/
    String INTEGRATION_SERVICE_PERCO_TYPE = "PERCo";
    String INTEGRATION_SERVICE_PERCO_IP_MODULE = "10.10.199.142";
    String INTEGRATION_SERVICE_PERCO_WRONG_IP_MODULE = "192.168.249.100";
    String INTEGRATION_SERVICE_PERCO_PORT_MODULE = "8080";
    String INTEGRATION_SERVICE_PERCO_IP_SERVER = "127.0.0.1";
    String INTEGRATION_SERVICE_PERCO_PORT_SERVER = "211";
    String INTEGRATION_SERVICE_PERCO_USERNAME = "admin";
    String INTEGRATION_SERVICE_PERCO_KEY = "SECRET";

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

    /******************************************* Раздел Голосовое меню ***********************************************/

    //General elements
    String IVR_BUTTON_EDIT = "edit";
    String IVR_BUTTON_DELETE = "delete";

    String IVR_ENTRY_POINTS_TITLE = "Точки входа";

    String IVR_MENU_TITLE = "Меню";
    String[] IVR_MENU_ITEMS = {"Звонок", "Вызвать абонента", "Принять факс", "Положить трубку",
            "Вернуться в родительское меню", "Вернуться в корневое меню", "Повторить меню"};
    //String[] IVR_MENU_ITEMS = {"Звонок"};
    String IVR_MENU_DESCRIPTION = "Тестирование меню"; // + добавить одно из названий
    String IVR_MENU_BUTTON_LOOK_MENU = "remove_red_eye";

    //Раздел Звуковые файлы
    String IVR_SOUND_FILES_TITLE = "Звуковые файлы";
    String IVR_SOUND_FILES_FIELD_DESCRIPTION = "Описание";
    String IVR_SOUND_FILES_DESCRIPTION_WAV_1 = "Тестовое описание первого звукового файла";
    String IVR_SOUND_FILES_DESCRIPTION_WAV_2 = "Тестовое описание второго звукового файла";

    /******************************************* Раздел Факс ***********************************************/

    String CONTACT_FOR_FAX = "7100";

    /******************************************* Раздел Пользователи СУ **********************************************/

    /**
     * Администратор безопасности
     */
    String USER_FIRST_NAME_AS = "Администратор";
    String USER_LAST_NAME_AS = "Безопасности";
    String USER_PATRON_NAME_AS = "Безопаснович";
    String USER_LOGIN_AS = "AdminSecurity";
    String USER_PASSWORD_AS = "12345678";
    String USER_ROLE_AS = "Администратор безопасности";

    /**
     * Администратор для тестирования СУ
     */
    String USER_FIRST_NAME_ADMIN = "Администратор";
    String USER_LAST_NAME_ADMIN = "Администраторов";
    String USER_PATRON_NAME_ADMIN = "Администратович";
    String USER_LOGIN_ADMIN = "AdminMS";
    String USER_PASSWORD_ADMIN = "87654321";
    String USER_ROLE_ADMIN = "Администратор";

    /**
     * Администратор для проверки раздела пользователи
     */
    String USER_FIRST_NAME_ADMIN_TEST = "Администратор";
    String USER_LAST_NAME_ADMIN_TEST = "Администраторов";
    String USER_PATRON_NAME_ADMIN_TEST = "Администратович";
    String USER_LOGIN_ADMIN_TEST = "Admintest";
    String USER_PASSWORD_ADMIN_TEST = "qwerty123456";

    /**
     * Администратор
     */
    String USER_FIRST_NAME_OPER = "Оператор";
    String USER_LAST_NAME_OPER = "Операторов";
    String USER_PATRON_NAME_OPER = "Операторович";
    String USER_LOGIN_OPER = "Oper";
    String USER_PASSWORD_OPER = "123456";
    String USER_ROLE_OPER = "Оператор";

    String USER_BUTTON_CONTINUE = "Продолжить";
}

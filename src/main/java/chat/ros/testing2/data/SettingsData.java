package chat.ros.testing2.data;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

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
    //String SERVER_PUSH_LOGIN_SERVER = "testing2";
    String SERVER_PUSH_LOGIN_SERVER = "ormp2";
    String SERVER_PUSH_INPUT_PORT = "Порт";
    String SERVER_PUSH_PORT_SERVER = "8088";
    String SERVER_PUSH_INPUT_PASSWORD = "Пароль";
    String SERVER_PUSH_PASSWORD_SERVER = "ZiAHFaJaGx";
    //String SERVER_PUSH_PASSWORD_SERVER = "lJddfDnwycX0ag7o";
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

    /****** Параметры для настройки провайдера ******/
    //The data for form of provider
    String TELEPHONY_PROVIDER_TITLE_FORM = "Провайдеры";
    String TELEPHONY_PROVIDER_INPUT_NAME = "Название";
    String TELEPHONY_PROVIDER_INPUT_DESCRIPTION = "Описание";
    String TELEPHONY_PROVIDER_INPUT_ADDRESS = "Aдрес провайдера (с портом)";
    String TELEPHONY_PROVIDER_INPUT_AON = "AOH";
    String TELEPHONY_PROVIDER_INPUT_USERNAME = "Имя пользователя";
    String TELEPHONY_PROVIDER_INPUT_PASSWORD = "Пароль";
    String TELEPHONY_PROVIDER_INPUT_INTERVAL = "Интервал регистрации";

    String TELEPHONE_PROVIDER_EDIT_TITLE_PROVIDER = "Провайдер";

    //The data for creating the first provider without registration
    String TELEPHONY_PROVIDER_TITLE_WITHOUT_REG = "Asterisk";
    String TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG = "Это тестовое описание для провайдера Asterisk";
    String TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG = "127.0.0.1:5062";
    String TELEPHONY_PROVIDER_AON_WITHOUT_REG = "Ksiretsa";

    //The data for create provider with registration
    String TELEPHONY_PROVIDER_TITLE_WITH_REG = "MX-1000";
    String TELEPHONY_PROVIDER_DESCRIPTION_WITH_REG = "Это тестовое описание для провайдера MX-1000";
    String TELEPHONY_PROVIDER_ADDRESS_WITH_REG = "10.10.1.100";
    String TELEPHONY_PROVIDER_AON_WITH_REG = "XM001";
    String TELEPHONY_PROVIDER_USERNAME_WITH_REG = "MX1000";
    String TELEPHONY_PROVIDER_PASSWORD_WITH_REG = "123456";
    String TELEPHONY_PROVIDER_INTERVAL_WITH_REG = "299";

    //Data for creating a provider for testing the route in simple mode
    String TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE = "Provider - route in simple mode";
    String TELEPHONY_PROVIDER_ADDRESS_ROUTE_SIMPLE_MODE = "127.0.0.1";

    //Data for creating a provider for testing the route in expert mode
    String TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE = "Provider - route in expert mode";
    String TELEPHONY_PROVIDER_ADDRESS_ROUTE_EXPERT_MODE = "127.0.0.1";

    //The data for edit registration provider
    String TELEPHONY_PROVIDER_EDIT_USERNAME_WITH_REG = "admin";
    String TELEPHONY_PROVIDER_EDIT_PASSWORD_WITH_REG = "654321";
    String TELEPHONY_PROVIDER_EDIT_INTERVAL_WITH_REG = "31";

    String TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE = "Маршруты";

    //Button for add route
    String TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE = "Создать маршрут";
    String TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE = "Новый маршрут";


    //Type route
    String TELEPHONY_PROVIDER_INCOMING_ROUTE = "Входящий";
    String TELEPHONY_PROVIDER_OUTGOING_ROUTE = "Исходящий";

    //The fields for route with simple mode
    String TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER = "Шаблон номера";
    String TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE = "Шаблон замены";

    String[] TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_FIELDS = {TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER,
            TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE};

    //The fields for route with normal mode
    String TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER = "Рег. выр. номера";
    String TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE = "Рег. выр. замены";
    String TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE = "Группы замены";

    //Data for incoming route with simple mode
    String TELEPHONY_PROVIDER_INCOMING_ROUTE_SIMPLE_MODE_NUMBER = "1000";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_NUMBER = "2000";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER = "^(2005)$";
    String TELEPHONY_PROVIDER_INCOMING_ROUTE_SIMPLE_MODE_REPLACE = "1+1|XXX";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_REPLACE = "1+2|XXX";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE = "^(2[0-9]{3})$";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_GROUP_REPLACE = "2\\1";

    //Data for outgoing route with simple mode
    String TELEPHONY_PROVIDER_OUTGOING_ROUTE_SIMPLE_MODE_NUMBER = "3000";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_MODE_NUMBER = "4000";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER = "^(4005)$";
    String TELEPHONY_PROVIDER_OUTGOING_ROUTE_SIMPLE_MODE_REPLACE = "1+3|XXX";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_MODE_REPLACE = "1+4|XXX";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE = "^(4[0-9]{3})$";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_GROUP_REPLACE = "4\\1";

    //Data for route incoming with expert mode
    String TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_NUMBER = "^(5000)$";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_NUMBER = "^(6000)$";
    String TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_REPLACE = "^(5[0-9]{3})$";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_REPLACE = "^(6[0-9]{3})$";
    String TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE = "5\\1";
    String TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE = "6\\2";

    //Data for outgoing route with expert mode
    String TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_NUMBER = "^(7000)$";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_NUMBER = "^(8000)$";
    String TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_REPLACE = "^(7[0-9]{3})$";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_REPLACE = "^(8[0-9]{3})$";
    String TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_GROUP_REPLACE = "7\\1";
    String TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_GROUP_REPLACE = "8\\2";

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
    String MAIL_GOOGLE_SMTP_SERVER = "smtp.gmail.com";
    String MAIL_GOOGLE_IMAP_SERVER = "impa.gmail.com";
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

    /***************************************** Раздел Система оповещения *********************************************/

    String CONTACT_FOR_ALERT = "7200";

    String ALERT_TITLE_SECTION_USERS = "Пользователи системы оповещения";

    String ALERT_TITLE_SECTION_SIP_ALERT = "Росчат оповещение, SIP-оповещение";
    String ALERT_BUTTON_SELECT_CONTACT_SIP = "Выбрать контакт";

    String ALERT_TITLE_SECTION_SMS_ALERT = "SMS-оповещение";

    String ALERT_TITLE_SECTION_EMAIL_ALERT = "EMAIL-оповещение";

    String ALERT_TITLE_ITEM_LEFT_MENU_TASKS = "Задания";
    String ALERT_TITLE_ITEM_LEFT_MENU_JOURNALS = "Журналы";

    String[] ALERT_TITLE_LEST_MENU_ITEMS = {
            ALERT_TITLE_ITEM_LEFT_MENU_TASKS
            , ALERT_TITLE_ITEM_LEFT_MENU_JOURNALS
    };

    String ALERT_TITLE_ITEM_TOP_MENU_TASKS = "Список заданий";
    String ALERT_TITLE_ITEM_TOP_MENU_LIST_ALERT = "Списки оповещения";
    String ALERT_TITLE_ITEM_TOP_MENU_MESSAGES = "Сообщения";
    String ALERT_TITLE_ITEM_TOP_MENU_SOUND_FILES = "Звуковые файлы";

    String[] ALERT_TITLE_TOP_MENU_ITEMS = {ALERT_TITLE_ITEM_TOP_MENU_TASKS
            , ALERT_TITLE_ITEM_TOP_MENU_LIST_ALERT
            , ALERT_TITLE_ITEM_TOP_MENU_MESSAGES
            , ALERT_TITLE_ITEM_TOP_MENU_SOUND_FILES
    };

    String ALERT_INPUT_NAME = "Название";
    String ALERT_INPUT_DESCRIPTION = "Описание";
    String ALERT_INPUT_MESSAGE = "Текст сообщения";
    String ALERT_INPUT_TELEPHONE_CODE = "Телефонный код доступа";

    String ALERT_VALUE_NAME_FOR_MESSAGE = "Тестовое название";
    String ALERT_VALUE_DESCRIPTION_FOR_MESSAGE = "Тестовое описание";
    String ALERT_VALUE_TEXT_FOR_MESSAGE = "Тестовое сообщение для системы оповещения";

    String ALERT_VALUE_NAME_FOR_ALERT = "Тестовое название оповещание";
    String ALERT_VALUE_DESCRIPTION_FOR_ALERT = "Тестовое описание оповещения";

    String[] ALERT_LIST_ICONS_ALERT = {"mdi-phone", "mdi-email-outline", "mdi-message-processing", "mdi-cellphone-message"};

    String ALERT_VALUE_NAME_FOR_TASK = "Тестовое задание";
    String ALERT_VALUE_DESCRIPTION_FOR_TASK = "Тестовое описание задания";

    /******************************************* Раздел Голосовое меню ***********************************************/

    //General elements
    String IVR_BUTTON_EDIT = "edit";
    String IVR_BUTTON_PLAY_AUDIO = "play_arrow";
    String IVR_BUTTON_DELETE = "delete";

    String IVR_ENTRY_POINTS_TITLE = "Точки входа";

    String IVR_MENU_TITLE = "Меню";
    String[] IVR_MENU_ITEMS = {"Положить трубку", "Вернуться в родительское меню",
            "Вернуться в корневое меню", "Повторить меню"};
    //String[] IVR_MENU_ITEMS = {"Положить трубку"};
    String[] IVR_SCHEDULE_ITEMS_FOR_SIMPLE_MENU = {"Расписание 1", "Расписание 2", "Расписание 3", "Расписание 4"};
    //String[] IVR_SCHEDULE_ITEMS_FOR_SIMPLE_MENU = {"Расписание 1"};
    String[] IVR_SCHEDULE_ITEMS_FOR_GO_TO_MENU = {"Расписание 5", "Расписание 6", "Расписание 7", "Расписание 8"};
    //String[] IVR_SCHEDULE_ITEMS_FOR_GO_TO_MENU = {"Расписание 5"};
    String[] IVR_SCHEDULE_ITEMS = ArrayUtils.addAll(IVR_SCHEDULE_ITEMS_FOR_SIMPLE_MENU, IVR_SCHEDULE_ITEMS_FOR_GO_TO_MENU);
    String IVR_MENU_DESCRIPTION = "Тестирование "; // + добавить одно из названий
    String IVR_MENU_BUTTON_LOOK_MENU = "remove_red_eye";

    //Раздел Звуковые файлы
    String IVR_SOUND_FILES_TITLE = "Звуковые файлы";
    String IVR_SOUND_FILES_FIELD_NAME = "Название";

    //Section schedule
    String IVR_SCHEDULE_TITLE = "Расписание";
    String IVR_SCHEDULE_RULES_TITLE = "Правила";
    String IVR_SCHEDULE_RULE_TYPE_WEEK_DAY = "День недели";
    String IVR_SCHEDULE_RULE_TYPE_CALENDAR_DATE = "Календарные дни";

    /******************************************* Раздел Факс ***********************************************/

    String CONTACT_FOR_FAX = "7100";

    String FAX_NUMBERS_TITLE = "Номера факсов";
    String FAX_NUMBER_WITH_DESCRIPTION = "7100";
    String FAX_NUMBER_WITHOUT_DESCRIPTION = "7101";
    String FAX_DESCRIPTION_FAX = "Это тестовый номер для факса";

    String FAX_USERS_TITLE = "Пользователи факсов";

    String FAX_TITLE_LEFT_MENU_ITEM_INCOMING = "Полученные";
    String FAX_TITLE_LEFT_MENU_ITEM_OUTGOING = "Отправленные";
    String FAX_TITLE_LEFT_MENU_ITEM_SEND_FAX = "Отправить факс";

    String[] FAX_TITLE_LEFT_MENU_ITEMS = {
            FAX_TITLE_LEFT_MENU_ITEM_INCOMING
            , FAX_TITLE_LEFT_MENU_ITEM_OUTGOING
            , FAX_TITLE_LEFT_MENU_ITEM_SEND_FAX
    };

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
     * Оператор
     */
    String USER_FIRST_NAME_OPER = "Оператор";
    String USER_LAST_NAME_OPER = "Операторов";
    String USER_PATRON_NAME_OPER = "Операторович";
    String USER_LOGIN_OPER = "Oper";
    String USER_PASSWORD_OPER = "123456";
    String USER_ROLE_OPER = "Оператор";

    String USER_BUTTON_CONTINUE = "Продолжить";
}

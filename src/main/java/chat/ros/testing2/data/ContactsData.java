package chat.ros.testing2.data;

import static chat.ros.testing2.data.GetDataTests.getProperty;

public interface ContactsData {

    String config = "Contacts.properties";

    /***** Общие параметры контактов ******/
    String CONTACT_INPUT_LASTNAME = getProperty("CONTACT_INPUT_LASTNAME", config);
    String CONTACT_INPUT_PHONE_JOB = getProperty("CONTACT_INPUT_PHONE_JOB", config);

    /***** Параметры контактов ******/
    String CONTACT_NUMBER_7010 = getProperty("CONTACT_NUMBER_7010", config);
    String CONTACT_NUMBER_7011 = getProperty("CONTACT_NUMBER_7011", config);
    String CONTACT_NUMBER_7012 = getProperty("CONTACT_NUMBER_7012", config); //Контакт для проверки каналов
    String CONTACT_NUMBER_7013 = getProperty("CONTACT_NUMBER_7013", config); //Контакт для проверки каналов

    /******************************************** Раздел Польхователь ************************************************/

    /****** Параметры учётных записей ******/
    String USER_ACOUNT_ITEM_MENU = getProperty("USER_ACOUNT_ITEM_MENU", config);
    String USER_ACCOUNT_INPUT_USERNAME = getProperty("USER_ACCOUNT_INPUT_USERNAME", config);
    String USER_ACCOUNT_PASSWORD = getProperty("USER_ACCOUNT_PASSWORD", config);

    /****** Параметры сервисов ******/
    String USER_SERVICES_ITEM_MENU = getProperty("USER_SERVICES_ITEM_MENU", config);
    String USER_SERVICES_BUTTON_ADD = getProperty("USER_SERVICES_BUTTON_ADD", config);
    String USER_SERVICES_TYPE_RADIO = getProperty("USER_SERVICES_TYPE_RADIO", config);
    String USER_SERVICES_TYPE_DX500 = getProperty("USER_SERVICES_TYPE_DX500", config);
    String USER_SERVICES_TYPE_TETRA = getProperty("USER_SERVICES_TYPE_TETRA", config);
    String USER_SERVICES_INPUT_TETRA_SSI = getProperty("USER_SERVICES_INPUT_TETRA_SSI", config);
    String USER_SERVICES_TYPE_SIP = getProperty("USER_SERVICES_TYPE_SIP", config);
    String USER_SERVICE_INPUT_SIP = getProperty("USER_SERVICE_INPUT_SIP", config);

}

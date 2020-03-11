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

    /******************************************** Раздел Польхователь ************************************************/

    /****** Параметры учётных записей ******/
    String USER_ACOUNT_ITEM_MENU = getProperty("USER_ACOUNT_ITEM_MENU", config);
    String USER_ACCOUNT_INPUT_USERNAME = getProperty("USER_ACCOUNT_INPUT_USERNAME", config);
    String USER_ACCOUNT_PASSWORD = getProperty("USER_ACCOUNT_PASSWORD", config);

    /****** Параметры сервисов ******/
    String USER_SERVICES_ITEM_MENU = getProperty("USER_SERVICES_ITEM_MENU", config);
}

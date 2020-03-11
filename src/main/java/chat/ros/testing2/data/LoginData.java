package chat.ros.testing2.data;

import static chat.ros.testing2.data.GetDataTests.*;

public interface LoginData {

    String config = "Login.properties";

    /****** Параметры сервера ******/
    String HOST_HUB = getProperty("HOST_HUB", config);
    String HOST_SERVER = getProperty("HOST_SERVER", config);
    String PORT_SERVER = getProperty("PORT_SERVER", config);

    /****** Параметры для авторизации в СУ ******/
    String LOGIN_ADMIN_MS = getProperty("LOGIN_ADMIN_MS", config);
    String PASSWORD_ADMIN_MS = getProperty("PASSWORD_ADMIN_MS", config);

}

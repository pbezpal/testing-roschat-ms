package chat.ros.testing2.data;

public interface LoginData {

    /****** Параметры сервера ******/
    String HOST_HUB = "10.10.199.45";
    String HOST_SERVER = System.getProperty("server");
    String PORT_SERVER = System.getProperty("port");

    /****** Параметры для авторизации в СУ ******/
    String LOGIN_AS_MS = "admin";
    String PASSWORD_AS_MS = "123456";

    /****** Параметры для авторизации ssh ******/
    String LOGIN_ADMIN_SSH = "root";
    String PASSWORD_ADMIN_SSH = "Art7Tykx78Dp";
    int PORT_SSH = 2222;

}

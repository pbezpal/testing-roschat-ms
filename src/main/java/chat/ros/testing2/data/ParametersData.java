package chat.ros.testing2.data;

public interface ParametersData {

    String WRONG_SYMBOLS_SERVER = " !\"/#$%&'()*+,:;<=>?[\\]^_`{|}~‘’“”–ё№»" +
            "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
    String[] WRONG_VALUE_HOST = {"t",".chat","testing2roschat", "testing2.r", "r.chat",
            "testing2testing2testing2testing2testing2testing2testing2testing2.me", "-testing2.ros.chat",
            "testing2.ros..chat","testing2.ros.chat-",".testing2.ros.chat","testing2.ros.chat.",
            getRandomSymbol(WRONG_SYMBOLS_SERVER) + "testing2.ros.chat",
            "test"  + getRandomSymbol(WRONG_SYMBOLS_SERVER) +  "ing2.ros.chat",
            "testing2" + getRandomSymbol(WRONG_SYMBOLS_SERVER) +  ".ros.chat",
            "testing2." + getRandomSymbol(WRONG_SYMBOLS_SERVER) +  ".chat",
            "testing2.ros." + getRandomSymbol(WRONG_SYMBOLS_SERVER) +  "chat",
            "testing2.ros.c" + getRandomSymbol(WRONG_SYMBOLS_SERVER),
            "testing2.ros.chat" + getRandomSymbol(WRONG_SYMBOLS_SERVER)};
    String WRONG_SYMBOLS_IP_ADDRESS = WRONG_SYMBOLS_SERVER + "-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String[] WRONG_VALUE_IP_ADDRESS = {"127.0.0.", "127.0..1", "127..0.1", "256.0.0.1", "127.256.0.1", "127.0.256.1",
            "127.0.0.256","00.0.0.1","127.00.0.1","127.0.00.1","127.0.0.00",
            "12" + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS) + ".0.0.1",
            "127." + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS) + ".0.1",
            "127.0.0" + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS) + ".1",
            "127.0.0.1" + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS),
            "127" + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS) + "0.0.1",
            "127.0" + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS) + "0.1",
            "127.0.0" + getRandomSymbol(WRONG_SYMBOLS_IP_ADDRESS) + "1"};
    String WRONG_SYMBOLS_PORT = WRONG_SYMBOLS_SERVER + "-.ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String VALID_SYMBOLS_PORT = "1234567890";
    String MORE_MAX_VALUE_PORT = "65536";
    String WRONG_SYMBOLS_EMAIL = " \"(),:;<>[\\]‘’“”–ё№»АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
    String[] WRONG_VALUE_EMAIL = {"testinfotek.ru", "test@infotekru",".test@infotek.ru","test@infotek.ru.",
            "test@infotek.r",getRandomSymbol(WRONG_SYMBOLS_EMAIL) + "test@infotek.ru",
            "test" + getRandomSymbol(WRONG_SYMBOLS_EMAIL) + "@infotek.ru",
            "t" + getRandomSymbol(WRONG_SYMBOLS_EMAIL) + "st@infotek.ru",
            "test" + getRandomSymbol(WRONG_SYMBOLS_EMAIL) + "infotek.ru",
            "test@" + getRandomSymbol(WRONG_SYMBOLS_EMAIL) + "infotek.ru",
            "test@inf" + getRandomSymbol(WRONG_SYMBOLS_EMAIL) + "tek.ru",
            "test@infotek.r" + getRandomSymbol(WRONG_SYMBOLS_EMAIL),
            "test@infotek.ru" + getRandomSymbol(WRONG_SYMBOLS_EMAIL),};

    static String getRandomSymbol(String value){
        int max = value.length() - 1;
        return String.valueOf(value.charAt((int)(Math.random() * ++max)));
    }

}

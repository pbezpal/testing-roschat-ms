package chat.ros.testing2.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;

public interface GetDataTests {

    Properties prop = new Properties();

    static HashMap<String, String> getLoadConfig(String config){
        InputStream input = GetDataTests.class.getClassLoader().getResourceAsStream("data/" + config);
        try{
            prop.load(input);
        }catch (IOException e){
            return null;
        }

        return new HashMap(prop);
    }

    static String getProperty(String prop, String config){
        try {
            return new String(getLoadConfig(config).get(prop).getBytes("ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}

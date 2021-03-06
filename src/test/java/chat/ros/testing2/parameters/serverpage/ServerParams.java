package chat.ros.testing2.parameters.serverpage;

import chat.ros.testing2.server.settings.ServerPage;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.SERVER_CONNECT_FIELD_PORTS;
import static org.junit.jupiter.api.Assertions.*;

public abstract class ServerParams extends ServerPage {

    private static String hostServer = System.getProperty("hostserver");

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getWrongHosts(){
        ArrayList<String> data = new ArrayList<>();

        for(String host: WRONG_VALUE_HOST){
            data.add(host);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    protected static Iterable<Character> getWrongValueConnectPort() {
        ArrayList<Character> data = new ArrayList<>();

        for(char c: WRONG_SYMBOLS_PORT.toCharArray()) {
            data.add(c);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    protected static Iterable<Object[]> getValidValueConnectPorts() {
        ArrayList<Object[]> data = new ArrayList<>();

        for (char c : VALID_SYMBOLS_PORT.toCharArray()) {
            data.add(new Object[]{SERVER_CONNECT_INPUT_HTTP_PORT, c});
            data.add(new Object[]{SERVER_CONNECT_INPUT_HTTPS_PORT, c});
            data.add(new Object[]{SERVER_CONNECT_INPUT_WEBSOCKET_PORT, c});
        }

        return data;
    }

    protected void wrong_symbols_ports(String field, String value){
        String ports = null;
        Map<String, String> mapInputValueConnect = null;

        if (field.contains(SERVER_CONNECT_INPUT_HTTP_PORT)){
            ports = value + ", " + SERVER_CONNECT_HTTPS_PORT + ", " + SERVER_CONNECT_WEBSOCKET_PORT;
            mapInputValueConnect = new HashMap() {{
                put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, hostServer);
                put(SERVER_CONNECT_INPUT_HTTP_PORT, value);
                put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
                put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
            }};
        }
        else if (field.contains(SERVER_CONNECT_INPUT_HTTPS_PORT)){
            ports = SERVER_CONNECT_HTTP_PORT + ", " + value + ", " + SERVER_CONNECT_WEBSOCKET_PORT;
            mapInputValueConnect = new HashMap() {{
                put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, hostServer);
                put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
                put(SERVER_CONNECT_INPUT_HTTPS_PORT, value);
                put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
            }};
        }
        else if (field.contains(SERVER_CONNECT_INPUT_WEBSOCKET_PORT)){
            ports = SERVER_CONNECT_HTTP_PORT +  ", " + SERVER_CONNECT_HTTPS_PORT + ", " + value;
            mapInputValueConnect = new HashMap() {{
                put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, hostServer);
                put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
                put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
                put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, value);
            }};
        }

        serverPage.clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        serverPage.sendInputsForm(mapInputValueConnect);
        assertEquals(isShowTextWrongValue(field),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                ports,
                false),
                "Значение " + ports + " отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
    }

    protected void valid_symbols_port(String field, String value){
        String ports = null;
        Map<String, String> mapInputValueConnect = null;

        if (field.contains(SERVER_CONNECT_INPUT_HTTP_PORT)){
            ports = value + ", " + SERVER_CONNECT_HTTPS_PORT + ", " + SERVER_CONNECT_WEBSOCKET_PORT;
            mapInputValueConnect = new HashMap() {{
                put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, hostServer);
                put(SERVER_CONNECT_INPUT_HTTP_PORT, value);
                put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
                put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
            }};
        }
        else if (field.contains(SERVER_CONNECT_INPUT_HTTPS_PORT)){
            ports = SERVER_CONNECT_HTTP_PORT + ", " + value + ", " + SERVER_CONNECT_WEBSOCKET_PORT;
            mapInputValueConnect = new HashMap() {{
                put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, hostServer);
                put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
                put(SERVER_CONNECT_INPUT_HTTPS_PORT, value);
                put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
            }};
        }
        else if (field.contains(SERVER_CONNECT_INPUT_WEBSOCKET_PORT)){
            ports = SERVER_CONNECT_HTTP_PORT +  ", " + SERVER_CONNECT_HTTPS_PORT + ", " + value;
            mapInputValueConnect = new HashMap() {{
                put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, hostServer);
                put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
                put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
                put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, value);
            }};
        }

        serverPage.clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        serverPage.sendInputsForm(mapInputValueConnect);
        clickButtonSave();
        isFormConfirmActions(true)
                .clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertTrue(isShowFieldAndValue(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                ports,
                true),
                "Значение " + ports + " не отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
    }
}

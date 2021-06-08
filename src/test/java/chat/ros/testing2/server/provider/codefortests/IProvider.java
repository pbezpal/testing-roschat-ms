package chat.ros.testing2.server.provider.codefortests;

import java.util.Map;

public interface IProvider {

    void addProvider(Map<String, String> dataGeneralProvider, Map<String, String> dataRegistrationProvider);

    void addProvider(Map<String, String> dataProvider);

    void editProvider(String provider, Map<String, String> dataProvider, boolean registration, String buttonEdit);

    void verifyShowSettingsProvider(Map<String, String> dataProvider, boolean registration);

    void verifyTableProvider(Map<String, String> dataProvider, boolean show);

    void deleteProvider(String provider, Map<String, String> dataProvider, boolean tableDelete);
}

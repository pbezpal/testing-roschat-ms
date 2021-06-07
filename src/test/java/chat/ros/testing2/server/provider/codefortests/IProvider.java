package chat.ros.testing2.server.provider.codefortests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IProvider {

    void addProvider(Map<String, String> dataGeneralProvider, Map<String, String> dataRegistrationProvider);

    void addProvider(Map<String, String> dataProvider);

    void verifyShowSettingsProvider(Map<String, String> dataProvider, boolean registration);
}

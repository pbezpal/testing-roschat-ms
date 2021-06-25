package chat.ros.testing2.server.provider.codefortests;

import java.util.Map;

public interface IProvider {

    /**
     * verify title and subtitle window modal for provider
     * @param provider if exist then add provider
     *            if not exist then edit provider
     */
    void checkHeaderAndSubtitlesWindowModalProvider(String... provider);

    /**
     * add provide with registration
     * @param dataGeneralProvider
     * @param dataRegistrationProvider
     */
    void addProvider(Map<String, String> dataGeneralProvider, Map<String, String> dataRegistrationProvider);

    /**
     * add provider without registration
     * @param dataProvider
     */
    void addProvider(Map<String, String> dataProvider);

    /**
     * check headers and label to view provider settings
     * @param provider
     * @param registration if true then check view headers and labels to view registration settings
     *                     if false then don't check view headers and labels to view registration settings
     */
    void checkHeadersAndTitlesViewProviderSettings(String provider, boolean registration);

    void editProvider(String provider, Map<String, String> dataProvider, boolean registration, String buttonEdit);

    void checkViewProviderSettings(Map<String, String> dataProvider, boolean registration);

    void checkExistProviderInTableProviders(Map<String, String> dataProvider, boolean show);

    void deleteProvider(String provider, Map<String, String> dataProvider, boolean tableDelete);
}

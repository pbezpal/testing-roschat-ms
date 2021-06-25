package chat.ros.testing2.server.provider.codefortests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IRoutes {

    List<String> patternNumbersIncomingRoute = new ArrayList<String>();
    List<String> patternsReplaceIncomingRouteSimpleMode = new ArrayList<String>();
    List<String> replacesIncomingRouteExpertMode = new ArrayList<String>();
    List<String> groupReplacesIncomingRouteExpertMode = new ArrayList<String>();
    List<String> patternIncomingRouteExpertMode = new ArrayList<String>();

    List<String> patternNumbersOutgoingRoute = new ArrayList<String>();
    List<String> patternsReplaceOutgoingRouteSimpleMode = new ArrayList<String>();
    List<String> replacesOutgoingRouteExpertMode = new ArrayList<String>();
    List<String> groupReplacesOutgoingRouteExpertMode = new ArrayList<String>();
    List<String> patternOutgoingRouteExpertMode = new ArrayList<String>();

    void closeModalWindowForAddRoute(String provider, String direction, Map<String, String> dataRoute, String button, boolean simpleMod);

    void closeModalWindowForEditRoute(String provider, String route, Map<String, String> dataRoute, boolean... simpleMode);

    void checkHeaderModalWindowWhenAddRoute(String provider, String button);

    void addRoute(String provider, String direction, Map<String, String> dataRoute, String addButton, boolean simpleMode);

    void checkHeaderModalWindowWhenEditRoute(String provider, String route);

    void editRouteSimpleMode(String provider, String direction, Map<String, String> dataRoute);

    void editRouteExpertMode(String provider, String direction, Map<String, String> dataRoute, boolean... expertMode);

    void deleteRoute(String provider, String direction);

}

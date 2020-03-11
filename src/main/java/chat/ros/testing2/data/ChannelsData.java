package chat.ros.testing2.data;

import static chat.ros.testing2.data.GetDataTests.getProperty;

public interface ChannelsData {

    String config = "Channels.properties";

    /***** Параметры для создания канала на клиенте ******/
    String CLIENT_ITEM_NEW_CHANNEL = getProperty("CLIENT_ITEM_NEW_CHANNEL", config);
    String CLIENT_NAME_CHANNEL = getProperty("CLIENT_NAME_CHANNEL", config);
}

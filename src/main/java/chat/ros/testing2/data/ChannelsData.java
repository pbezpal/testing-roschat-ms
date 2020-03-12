package chat.ros.testing2.data;

import static chat.ros.testing2.data.GetDataTests.getProperty;

public interface ChannelsData {

    String config = "Channels.properties";

    /***** Параметры для создания канала на клиенте ******/
    String CLIENT_ITEM_NEW_CHANNEL = getProperty("CLIENT_ITEM_NEW_CHANNEL", config);
    String CLIENT_NAME_CHANNEL_PUBLIC = getProperty("CLIENT_NAME_CHANNEL_PUBLIC", config);
    String CLIENT_TYPE_CHANNEL_PUBLIC = getProperty("CLIENT_TYPE_CHANNEL_PUBLIC", config);
    String CLIENT_NAME_CHANNEL_CLOSED = getProperty("CLIENT_NAME_CHANNEL_CLOSED", config);
    String CLIENT_TYPE_CHANNEL_CLOSED = getProperty("CLIENT_TYPE_CHANNEL_CLOSED", config);
}

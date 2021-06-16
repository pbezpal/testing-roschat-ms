package chat.ros.testing2;

import chat.ros.testing2.helpers.AttachToReport;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class WatcherTests extends AttachToReport implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        AttachScreen();
        /*ABrowserLogNetwork();
        ABrowserLogConsole();*/
    }
}

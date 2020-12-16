package chat.ros.testing2.helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.List;

public class AttachToReport {

    public AttachToReport() {}

    @Attachment(type = "image/png")
    public byte[] AttachScreen() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Browser network log", type = "text/plain")
    public static String ABrowserLogNetwork() {
        LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get("performance");
        String logsBrowser = "";

        for (LogEntry le : logs) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(le.getMessage());

            //String prettyJsonString = gson.toJson(je);
            if (gson.toJson(je).contains("webSocketFrame"))  logsBrowser = logsBrowser + gson.toJson(je);


        }

        return logsBrowser;
    }

    @Attachment(value = "Browser console log", type = "text/plain")
    public static String ABrowserLogConsole() {

        List logList = Selenide.getWebDriverLogs(LogType.BROWSER);
        StringBuilder sb = new StringBuilder();

        for(Object line : logList) {
            sb.append(line);
            sb.append("\n");
        }

        return sb.toString();
    }

}

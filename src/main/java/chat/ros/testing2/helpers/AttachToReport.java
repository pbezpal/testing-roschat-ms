package chat.ros.testing2.helpers;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.qameta.allure.Attachment;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class AttachToReport {

    public AttachToReport() {}

    @Attachment(type = "image/png")
    public byte[] AttachScreen() {
        File screenshot = Screenshots.getLastScreenshot();
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //return ((TakesScreenshot) Selenide.).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Browser network log", type = "text/plain")
    public static String ABrowserLogNetwork() {
        LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get(LogType.PERFORMANCE);
        String logsBrowser = "";

        for (LogEntry log : logs) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(log.getMessage());

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

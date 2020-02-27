package chat.ros.testing2.helpers;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.qameta.allure.Attachment;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class AttachToReport {

    @Attachment(type = "image/png")
    public static byte[] AttachScreen(File screenshot) {
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static AShot aShot = new AShot();
    private static String pathScreenshots = "./build/classes/screenshots/";

    public static void AScreenshot(String filename){
        Screenshot screenshot = aShot.takeScreenshot(WebDriverRunner.getWebDriver());

        if( ! java.nio.file.Files.exists(Paths.get(pathScreenshots))) {
            try {
                java.nio.file.Files.createDirectories(Paths.get(pathScreenshots));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File currentScreenshot = new File(pathScreenshots + filename + ".png");

        try {
            ImageIO.write(screenshot.getImage(), "png", currentScreenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttachScreen(currentScreenshot);
    }

    public static void AScreenshot(String filename, SelenideElement element){
        Screenshot screenshot = aShot.takeScreenshot(WebDriverRunner.getWebDriver(), element);

        if( ! java.nio.file.Files.exists(Paths.get(pathScreenshots))) {
            try {
                java.nio.file.Files.createDirectories(Paths.get(pathScreenshots));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File currentScreenshot = new File(pathScreenshots + filename + ".png");

        try {
            ImageIO.write(screenshot.getImage(), "png", currentScreenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttachScreen(currentScreenshot);
    }

    @Attachment(value = "Browser network log", type = "text/plain")
    public static String ABrowserLogToReport() {
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

}

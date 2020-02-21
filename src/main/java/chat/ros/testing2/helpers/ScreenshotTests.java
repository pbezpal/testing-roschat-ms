package chat.ros.testing2.helpers;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ScreenshotTests {

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

}

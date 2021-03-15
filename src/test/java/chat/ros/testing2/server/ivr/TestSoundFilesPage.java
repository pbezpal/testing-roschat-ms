package chat.ros.testing2.server.ivr;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.IVRPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Голосовое меню")
@Feature(value = "Звуковые файлы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
public class TestSoundFilesPage extends IVRPage {

    private String wavFile = "test_sound.wav";
    private String pathWAVFile = getClass().
            getClassLoader().
            getResource("sound/" + wavFile).
            getFile();

    private String mp3File = "test_sound.mp3";
    private String pathMP3File = getClass().
            getClassLoader().
            getResource("sound/" + mp3File).
            getFile();

    @Story(value = "Загружаем звуковой wav файл")
    @Description(value = "Переходим в раздел Голосовое меню \n" +
            "загружаем звуковой файл wav\n" +
            "проверяем, что звуковой файл загрузился")
    @Test
    @Order(1)
    void test_Upload_Sound_File_WAV(){
        uploadSoundFile(pathWAVFile, IVR_TITLE_DESCRIPTION_SOUND_FILE_WAV);
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertTrue(isItemTable(IVR_TITLE_SOUND_FILES, wavFile, true),
                        "Название файла не найдено в таблице звуковых файлов"),
                () -> assertTrue(isItemTable(IVR_TITLE_SOUND_FILES, IVR_TITLE_DESCRIPTION_SOUND_FILE_WAV, true))
        );

    }

    @Story(value = "Загружаем звуковой mp3 файл")
    @Description(value = "Переходим в раздел Голосовое меню \n" +
            "загружаем звуковой файл mp3\n" +
            "проверяем, что звуковой файл загрузился")
    @Test
    void test_Upload_Sound_File_MP3(){
        uploadSoundFile(pathMP3File, IVR_TITLE_DESCRIPTION_SOUND_FILE_MP3);
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertTrue(isItemTable(IVR_TITLE_SOUND_FILES, mp3File, true),
                        "Название файла не найдено в таблице звуковых файлов"),
                () -> assertTrue(isItemTable(IVR_TITLE_SOUND_FILES, IVR_TITLE_DESCRIPTION_SOUND_FILE_MP3, true))
        );

    }
}

package chat.ros.testing2.server.services;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.services.IVRPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Голосовое меню")
@Feature(value = "Звуковые файлы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
public class TestAudioPlayerPage extends IVRPage {

    private static boolean status_add_sound_file;
    private String wavFile1 = "conf-usermenu.wav";
    private String pathWAVFile1 = getClass().
            getClassLoader().
            getResource("sound/" + wavFile1).
            getFile();

    private String wavFile2 = "vm-options.wav";
    private String pathWAVFile2 = getClass().
            getClassLoader().
            getResource("sound/" + wavFile2).
            getFile();

    @BeforeAll
    static void setUp(){
        status_add_sound_file = false;
    }

    @Story(value = "Проверяем работу аудиплеера при добавлении аудиофайла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл wav и описание к нему\n" +
            "3. Проверяем работу аудиплеера " +
            "4. Проверяем, что звуковой файл wav и описание к нему добавлены в таблицу звуковых файлов")
    @Test
    @Order(1)
    void test_Audio_Player_When_Uploading_File(){
        assertEquals(uploadSoundFile(pathWAVFile1, IVR_SOUND_FILES_DESCRIPTION_WAV_1)
                        .isVisibleTitleModalWrapper(),
                "Новый звуковой файл",
                "Не найден заголовок модального окна при добавлении звукового файла");
        assertTrue(isAudioPlayer(),
                "Отсутствует аудиоплеер в модальном окне при добавление аудиофайла");
        assertAll("Проверяем функции аудиоплеера",
                () -> assertTrue(clickPlayAudio().isPlayAudioPlayer() > 0, "Проигрывание аудио работает некорректно"),
                () -> assertTrue(isPauseAudioPlayer(), "Функция паузы аудио работает некорректно"),
                () -> assertEquals(isDurationAudio(),
                        "19.121625",
                        "Продолжительность файла не соотвествует продолжительности загруженного файла"),
                () -> assertEquals(isVolumeAudioPlayer(),
                        "0.5",
                        "Некорректно работает настройки звука"),
                () -> assertTrue(isMutedAudioPlayer(),
                        "Некорректно работает выключение звука"),
                () -> assertFalse(isOutMutedAudioPlayer(),
                        "Некорректно работает включение звука")
        );
        clickActionButtonOfModalWindow("Сохранить");
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile1, true),
                        "Название файла " + wavFile1 + " не найдено в таблице звуковых файлов"),
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, IVR_SOUND_FILES_DESCRIPTION_WAV_1, true),
                        "Описание " + IVR_SOUND_FILES_DESCRIPTION_WAV_1 + " звукового файла " + wavFile1 + " " +
                                "не найдено в таблице звуковых файлов")
        );

        status_add_sound_file = true;
    }

    @Story(value = "Проверяем работу аудиоплеера после нажатия кнопки Редактировать")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Редактировать в таблице звуков\n" +
            "3. Проверяем работу аудиплеера")
    @Test
    @Order(2)
    void test_Audio_Player_After_Click_Buttin_Edit(){
        assertTrue(status_add_sound_file,
                "Тест с добавлением звукового файла " + wavFile1 + " провалился. Невозможно продолжать тестирование.");
        assertEquals(clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile1, IVR_BUTTON_EDIT)
                        .isVisibleTitleModalWrapper(),
                "Редактирование звукового файла",
                "Не найден заголовок модального окна при воспроизведение");
        assertTrue(isAudioPlayer(),
                "Отсутствует аудиоплеер в модальном окне при добавление аудиофайла");
        assertAll("Проверяем функции аудиоплеера",
                () -> assertTrue(clickPlayAudio().isPlayAudioPlayer() > 0, "Проигрывание аудио работает некорректно"),
                () -> assertTrue(isPauseAudioPlayer(), "Функция паузы аудио работает некорректно"),
                () -> assertEquals(isDurationAudio(),
                        "19.121625",
                        "Продолжительность файла не соотвествует продолжительности загруженного файла"),
                () -> assertEquals(isVolumeAudioPlayer(),
                        "0.5",
                        "Некорректно работает настройки звука"),
                () -> assertTrue(isMutedAudioPlayer(),
                        "Некорректно работает выключение звука"),
                () -> assertFalse(isOutMutedAudioPlayer(),
                        "Некорректно работает включение звука")
        );
        clickActionButtonOfModalWindow("Отменить");
    }

    @Story(value = "Проверяем работу аудиоплеера после нажатия кнопки Воспроизвести")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести в таблице звуков\n" +
            "3. Проверяем работу аудиплеера")
    @Test
    @Order(3)
    void test_Audio_Player_After_Click_Button_Play(){
        assertTrue(status_add_sound_file,
                "Тест с добавлением звукового файла " + wavFile1 + " провалился. Невозможно продолжать тестирование.");
        assertEquals(clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile1, IVR_BUTTON_PLAY_AUDIO)
                .isVisibleTitleModalWrapper(),
                "Редактирование звукового файлa",
                "Не найден заголовок модального окна при воспроизведение");
        assertTrue(isAudioPlayer(),
                "Отсутствует аудиоплеер в модальном окне при добавление аудиофайла");
        assertAll("Проверяем функции аудиоплеера",
                () -> assertTrue(isPlayAudioPlayer() > 0, "Проигрывание аудио работает некорректно"),
                () -> assertTrue(isPauseAudioPlayer(), "Функция паузы аудио работает некорректно"),
                () -> assertEquals(isDurationAudio(),
                        "19.121625",
                        "Продолжительность файла не соотвествует продолжительности загруженного файла"),
                () -> assertEquals(isVolumeAudioPlayer(),
                        "0.5",
                        "Некорректно работает настройки звука"),
                () -> assertTrue(isMutedAudioPlayer(),
                        "Некорректно работает выключение звука"),
                () -> assertFalse(isOutMutedAudioPlayer(),
                        "Некорректно работает включение звука")
        );
        clickActionButtonOfModalWindow("Отменить");
    }
}

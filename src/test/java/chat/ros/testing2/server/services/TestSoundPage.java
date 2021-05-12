package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
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
@ExtendWith(ResourcesIVRPage.class)
@ExtendWith(WatcherTests.class)
public class TestSoundPage extends IVRPage {

    private final String wavFile1 = "conf-usermenu.wav";
    private final String pathWAVFile1 = getClass().
            getClassLoader().
            getResource("sound/" + wavFile1).
            getFile();

    private final String wavFile2 = "vm-options.wav";
    private final String pathWAVFile2 = getClass().
            getClassLoader().
            getResource("sound/" + wavFile2).
            getFile();


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

        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем работу аудиоплеера после нажатия кнопки Воспроизвести")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести в таблице звуков\n" +
            "3. Проверяем работу аудиплеера")
    @Test
    @Order(2)
    void test_Button_Play_After_Add_Sound_File(){
        clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile1, IVR_BUTTON_PLAY_AUDIO);
        assertAll("Проверяем налицие закоголовка и работу аудиоплеера в модальном окне",
                () -> assertEquals(isVisibleTitleModalWrapper(),
                        "Редактирование звукового файла",
                        "Не найден заголовок модального окна при воспроизведение"),
                () -> {
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
                }
        );
        clickActionButtonOfModalWindow("Отменить");
    }

    @Story(value = "Редактируем звуковой фал")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем редактировать звуковой файл добавленный в первом тесте \n" +
            "3. Выбираем новый звуковой файл \n" +
            "4. Проверяем, что новый файл и описание добавлены в таблицу звуковых файлов")
    @Test
    @Order(3)
    void test_AudioPlayer_When_Edit_Sound_File() {
        clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile1, IVR_BUTTON_EDIT);
        uploadSoundFileByModalWindow(pathWAVFile2, IVR_SOUND_FILES_DESCRIPTION_WAV_2);
        assertAll("Проверяем, появился ли заголовок и аудиоплеер в модальном окне редактирования звукового файла",
                () -> assertEquals(isVisibleTitleModalWrapper(),
                        "Редактирование звукового файла",
                        "Не найден заголовок модального окна при добавлении звукового файла"),
                () -> {
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
                }
        );
        clickActionButtonOfModalWindow("Сохранить");
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile2, true),
                        "Название файла " + wavFile2 + " не найдено в таблице звуковых файлов"),
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, IVR_SOUND_FILES_DESCRIPTION_WAV_2, true),
                        "Описание " + IVR_SOUND_FILES_DESCRIPTION_WAV_2 + " звукового файла " + wavFile2 + " " +
                                "не найдено в таблице звуковых файлов")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем работу аудиоплеера после нажатия кнопки Воспроизвести плосле редактирования файла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести в таблице звуков\n" +
            "3. Проверяем работу аудиплеера")
    @Test
    @Order(4)
    void test_Button_Play_After_Edit_Sound_File(){
        clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile2, IVR_BUTTON_PLAY_AUDIO);
        assertAll("Проверяем налицие закоголовка и работу аудиоплеера в модальном окне",
                () -> assertEquals(isVisibleTitleModalWrapper(),
                        "Редактирование звукового файла",
                        "Не найден заголовок модального окна при воспроизведение"),
                () -> {
                    assertTrue(isAudioPlayer(),
                            "Отсутствует аудиоплеер в модальном окне при добавление аудиофайла");
                    assertAll("Проверяем функции аудиоплеера",
                            () -> assertTrue(isPlayAudioPlayer() > 0, "Проигрывание аудио работает некорректно"),
                            () -> assertTrue(isPauseAudioPlayer(), "Функция паузы аудио работает некорректно"),
                            () -> assertEquals(isDurationAudio(),
                                    "20.349375",
                                    "Продолжительность файла не соотвествует продолжительности загруженного файла"),
                            () -> assertEquals(isVolumeAudioPlayer(),
                                    "0.5",
                                    "Некорректно работает настройки звука"),
                            () -> assertTrue(isMutedAudioPlayer(),
                                    "Некорректно работает выключение звука"),
                            () -> assertFalse(isOutMutedAudioPlayer(),
                                    "Некорректно работает включение звука")
                    );
                }
        );
        clickActionButtonOfModalWindow("Отменить");
    }

    @Story(value = "Проверяем функцию скачивания файла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Редактировать в таблице звуков\n" +
            "3. Скачиваем звуковой файл \n" +
            "4. Проверяем, скачался ли звуковой файл"
    )
    @Test
    @Order(5)
    void test_Download_Sound_File() {
        String soundFile = "";
        if(TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File") == null || ! TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File")) soundFile = wavFile1;
        else soundFile = wavFile2;
        assertEquals(clickButtonTable(IVR_SOUND_FILES_TITLE, soundFile, IVR_BUTTON_EDIT)
                        .isVisibleTitleModalWrapper(),
                "Редактирование звукового файла",
                "Не найден заголовок модального окна при воспроизведение");
        String finalSoundFile = soundFile;
        assertAll("Проверяем скачивание файла " + soundFile,
                () -> assertEquals(downloadSoundFile().getName(), finalSoundFile,
                        "Файл " + finalSoundFile + " не удалось скачать"),
                () -> {clickActionButtonOfModalWindow("Отменить");}
        );
    }

    @Story(value = "Удаляем звуковой файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем звуковой файл \n" +
            "3. Проверяем, что в таблице звуковых файлов отсуствтует удленный звуковой файл")
    @Test
    @Order(6)
    void test_Delete_Sound_File(){
        String soundFile = "";
        if(TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File") == null || ! TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File")) soundFile = wavFile1;
        else soundFile = wavFile2;
        clickButtonTable(IVR_SOUND_FILES_TITLE, soundFile, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления звукового файла");
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, soundFile, false),
                "Название файла " + soundFile + " найдено в таблице звуковых файлов после удаления файла");
    }
}

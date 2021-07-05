package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.services.codefortests.TIVRPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.*;

@Epic(value = "Сервисы")
@Feature(value = "Голосовое меню")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesIVRPage.class)
@ExtendWith(WatcherTests.class)
public class TestSoundPage extends TIVRPage {

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

    private String soundFile = null;

    @Story(value = "Проверяем заголовок модального окна при добавление аудиофайла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Добавить файл\n" +
            "3. Выбираем аудиофайл\n" +
            "4. Проверяем текст в заголовке модального окна\n" +
            "5. Нажимаем кнопку Отменить\n" +
            "6. Проверяем, что аудиофайл не отобраджается в таблице звуковых файлов")
    @Test
    @Order(1)
    void test_Check_Title_Text_Modal_Window_When_Uploading_File(){
        checkTitleTextModalWindowWhenUploadFile(pathWAVFile1, wavFile1, "Новый звуковой файл");
    }


    @Story(value = "Проверяем работу аудиплеера при добавлении аудиофайла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл wav и название к нему\n" +
            "3. Проверяем работу аудиплеера\n" +
            "4. Проверяем, что звуковой файл wav и название к нему сохраняются в таблицу звуковых файлов")
    @Test
    @Order(2)
    void test_Audio_Player_When_Uploading_File(){
        uploadMusicFile(pathWAVFile1, wavFile1, "19.121625", "0.5");
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем текст модального окна, после нажатия кнопки Воспроихвести")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести у аудиофайла\n" +
            "3. Проверяем текст в заголовке модального окна\n" +
            "4. Нажимаем кнопку Отменить")
    @Test
    @Order(3)
    void test_Check_Title_Text_Modal_Window_After_Click_Button_Play(){
        checkTitleTextWhenEditItem(IVR_SOUND_FILES_TITLE, wavFile1, "Редактирование звукового файла", IVR_BUTTON_PLAY_AUDIO);
    }

    @Story(value = "Проверяем работу аудиоплеера после нажатия кнопки Воспроизвести")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести в таблице звуков\n" +
            "3. Проверяем работу аудиплеера")
    @Test
    @Order(4)
    void test_Button_Play_After_Add_Sound_File(){
        verifyButtonAudioPlayer(wavFile1, "19.121625", "0.5");
    }

    @Story(value = "Проверяем текст модального окна, после нажатия кнопки Изменить")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести у аудиофайла\n" +
            "3. Проверяем текст в заголовке модального окна\n" +
            "4. Нажимаем кнопку Отменить")
    @Test
    @Order(5)
    void test_Check_Title_Text_Modal_Window_After_Click_Button_Edit(){
        checkTitleTextWhenEditItem(IVR_SOUND_FILES_TITLE, wavFile1, "Редактирование звукового файла", IVR_BUTTON_EDIT);
    }

    @Story(value = "Редактируем звуковой фал")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем редактировать звуковой файл добавленный в первом тесте \n" +
            "3. Выбираем новый звуковой файл \n" +
            "4. Проверяем, что новый файл и описание добавлены в таблицу звуковых файлов")
    @Test
    @Order(6)
    void test_AudioPlayer_When_Edit_Sound_File() {
        editMusicFile(wavFile1, wavFile2, pathWAVFile2, "19.121625", "0.5");
        TestStatusResult.setTestResult(true);
    }



    @Story(value = "Проверяем работу аудиоплеера после нажатия кнопки Воспроизвести плосле редактирования файла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Воспроизвести в таблице звуков\n" +
            "3. Проверяем работу аудиплеера")
    @Test
    @Order(7)
    void test_Button_Play_After_Edit_Sound_File(){
        verifyButtonAudioPlayer(wavFile2, "20.349375", "0.5");
    }

    @Story(value = "Проверяем функцию скачивания файла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Редактировать в таблице звуков\n" +
            "3. Скачиваем звуковой файл \n" +
            "4. Проверяем, скачался ли звуковой файл"
    )
    @Test
    @Order(8)
    void test_Download_Sound_File() {
        soundFile = null;
        if(TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File") == null || ! TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File")) soundFile = wavFile1;
        else soundFile = wavFile2;
        downloadMusicFile(soundFile);
    }

    @Story(value = "Удаляем звуковой файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем звуковой файл \n" +
            "3. Проверяем, что в таблице звуковых файлов отсуствтует удленный звуковой файл")
    @Test
    @Order(9)
    void test_Delete_Sound_File(){
        soundFile = null;
        if(TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File") == null || ! TestStatusResult.getTestResult().get("test_AudioPlayer_When_Edit_Sound_File")) soundFile = wavFile1;
        else soundFile = wavFile2;
        deleteMusicFile(soundFile);
    }
}

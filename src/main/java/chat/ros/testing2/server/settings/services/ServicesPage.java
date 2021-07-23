package chat.ros.testing2.server.settings.services;

import chat.ros.testing2.server.settings.SettingsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.io.File;
import java.time.Duration;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ServicesPage implements SettingsPage, IServicePage {

    private SelenideElement contentWrapper = $(".v-content__wrap");
    private SelenideElement modalWindow = $(".modal-window");
    private SelenideElement titleModalWindow = modalWindow.find("h2");
    private SelenideElement contentMenu = modalWindow.find(".modal-window__content");
    private ElementsCollection buttonActionOfModalWindow = modalWindow.$$(".modal-window__actions button div");
    private ElementsCollection itemTableContactName = $$(".contacts-box .contact-name");

    //Upload sound file
    private SelenideElement inputUploadSoundFile = $("#upload");
    private SelenideElement audioPlayer = $(".modal-window__content audio");


    //Login
    private SelenideElement loginForm = $(".login-wrapper");
    private SelenideElement imgLogo = $("img[src='/img/logo.4ec202cf.png']");
    private SelenideElement loginTitle = loginForm.find("h1");
    private SelenideElement mainRight = $(".main-right");
    private SelenideElement inputLogin = $("input[type='text']");
    private SelenideElement inputPassword = $("input[type='password']");
    private SelenideElement buttonNotSeePassword = $(".mdi-eye-off");
    private SelenideElement buttonSeePassword = $(".mdi-eye");
    private SelenideElement buttonLogin = $("button .v-btn__content");
    private SelenideElement inputChecBoxStaySystem = $("input[type='checkbox']");
    private SelenideElement selectCheckBoxStaySystem = $(".v-input--selection-controls__ripple");

    //Failed login
    private SelenideElement modalSheet = $(".v-sheet");
    private SelenideElement iconFailedLogin = modalSheet.find("i.error--text");
    private SelenideElement titleFailedLogin = modalSheet.find("h3");
    private SelenideElement msgFailedLogin = modalSheet.find(".msg-body p");

    //Logout
    private SelenideElement logoutButton = $(".logout-wrapper button");
    private SelenideElement logoutConfirm = $(".logout-text");

    //MS Service
    //List items left menu
    private ElementsCollection listTitleMenuLeft = $$(".v-list--dense .list-item__content-title");

    protected SelenideElement getContentWrapper(){
        return contentWrapper;
    }

    protected SelenideElement getServiceSection(String title){
        return $$("h2").findBy(text(title)).parent();
    }

    public SelenideElement getModalWindow() {
        return modalWindow;
    }

    public SelenideElement getModalSheet() { return modalSheet; }

    @Step(value = "Добавляем звуковой файл {file}")
    @Override
    public ServicesPage uploadSoundFile(String file){
        inputUploadSoundFile.uploadFile(new File(file));
        return this;
    }

    @Step(value = "Проверяем, отображается ли плеер")
    public ServicesPage isAudioPlayer(){
        audioPlayer.shouldBe(visible);
        return this;
    }

    @Step(value = "Нажимаем кнопку проигрывания звукового файла")
    public ServicesPage clickPlayAudio(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").play();");
        return this;
    }

    @Step(value = "Проверяем функцию проигрывания аудио")
    public Double isPlayAudioPlayer(){
        sleep(5000);
        return (Double) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").currentTime;");
    }

    @Step(value = "Проверяем функцию паузы в аудиоплеере")
    public boolean isPauseAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").pause();");
        return (boolean) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").paused;");
    }

    @Step(value = "Проверяем длину звукового файла")
    public String isDurationAudio(){
        return Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").duration").toString();
    }

    @Step(value = "Проверяем функцию изменения уровня звука в аудиоплеере")
    public String isVolumeAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").volume = 0.5;");
        return Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").volume;").toString();
    }

    @Step(value = "Проверяем функцию выключения звука")
    public boolean isMutedAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").muted = true;");
        return (boolean) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").muted;");
    }

    @Step(value = "Проверяем функцию включения звука")
    public boolean isOutMutedAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").muted = false;");
        return (boolean) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").muted;");
    }

    @Step(value = "Проверяем логотип Росчат в окне авторизации")
    public ServicesPage isImgLogo(){
        imgLogo.shouldBe(visible);
        return this;
    }

    @Step(value = "Проверяем текст загоовка {title} в форме авторизации")
    public ServicesPage isTextTitle(String title){
        loginTitle.shouldHave(text(title));
        return this;
    }

    @Step(value = "Проверяем наличие кнопки показать пароль и её функционал")
    public ServicesPage isButtonSeePassword(){
        SelenideElement inputPassword = $$("label").findBy(text("Пароль")).parent();
        inputPassword.find("input[type='password']").shouldBe(visible);
        buttonNotSeePassword.click();
        buttonSeePassword.shouldBe(visible);
        inputPassword.find("input[type='password']").shouldNotBe(visible);
        inputPassword.find("input[type='text']").shouldBe(visible);
        buttonSeePassword.click();
        buttonNotSeePassword.shouldBe(visible);
        inputPassword.find("input[type='text']").shouldNotBe(visible);
        inputPassword.find("input[type='password']").shouldBe(visible);
        return this;
    }

    @Step(value = "Вводим логин {login} и пароль {password}, выбираем остаться ли в системе {stay} и нажимаем кнопку Войти")
    public ServicesPage loginService(String login, String password, boolean stay){
        if(getModalSheet().isDisplayed() && iconFailedLogin.isDisplayed()){
            getModalSheet().sendKeys(Keys.ESCAPE);
        }
        inputLogin.click();
        inputLogin.sendKeys(login);
        inputPassword.click();
        inputPassword.sendKeys(password);
        if(stay){
            if( ! inputChecBoxStaySystem.isSelected())
                selectCheckBoxStaySystem.click();
            inputChecBoxStaySystem.shouldBe(selected);
        }else{
            if(inputChecBoxStaySystem.isSelected())
                selectCheckBoxStaySystem.click();
            inputChecBoxStaySystem.shouldNotBe(selected);
        }
        buttonLogin.click();
        return this;
    }

    @Step(value = "Вводим логин {login} и пароль {password} и нажимаем кнопку Войти")
    public ServicesPage loginService(String login, String password){
        inputLogin.click();
        inputLogin.sendKeys(login);
        inputPassword.click();
        inputPassword.sendKeys(password);
        buttonLogin.click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли модальное окно с заголовком {title} и текстом {text}")
    public ServicesPage isInfoModalFormWhenLoginFailed(String title, String text){
        iconFailedLogin.shouldBe(visible);
        //titleFailedLogin.shouldHave(text("Неудачная попытка авторизации"));
        titleFailedLogin.shouldHave(text(title));
        msgFailedLogin.shouldHave(text(text));
        return this;
    }

    @Step(value = "Проверяем, авторизованы ли мы в сервисе")
    public boolean isLoginService(){
        if(mainRight.isDisplayed())
            return true;
        return false;
    }

    @Step(value = "Проверяем, прошла ли авторизация в сервис")
    public ServicesPage isLoginService(boolean auth, String... textLoginFailed){
        if(auth) mainRight.shouldBe(visible);
        else {
            if(textLoginFailed.length > 0)
                isInfoModalFormWhenLoginFailed(textLoginFailed[0], textLoginFailed[1]);
            mainRight.shouldNotBe(visible);
        }
        return this;
    }

    @Step(value = "Выходим из управления ссистемой оповещения/факсами")
    public ServicesPage logoutService(){
        logoutButton.click();
        logoutConfirm.shouldBe(visible).click();
        return this;
    }

    @Step(value = "Проверяем, отображается {show} ли модальное окно")
    public ServicesPage isModalWindow(boolean show){
        if(show) modalWindow.shouldBe(visible, Duration.ofSeconds(10));
        else modalWindow.shouldNotBe(visible, Duration.ofSeconds(10));
        return this;
    }

    @Step(value = "Получаем заголовок моадльного окна")
    public ServicesPage isTitleTextModalWindow(String title){
        modalWindow.find("h2").shouldHave(text(title));
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в модальном окне")
    public ServicesPage clickActionButtonOfModalWindow(String button){
        buttonActionOfModalWindow.findBy(text(button)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Добавить в разделе {title}")
    public ServicesPage clickButtonAdd(String title){
        getServiceSection(title).scrollIntoView(false);
        getServiceSection(title).find(".action-bar button").click();
        return this;
    }

    public boolean isItemTable(String section, String item){
        getServiceSection(section).scrollTo();
        getServiceSection(section).$("table").scrollIntoView(false);
        return getServiceSection(section).$("table").$(byText(item)).isDisplayed();
    }

    @Step(value = "Проверяем, отображается {show} ли запись {item] в разделе {section}")
    public ServicesPage isItemTable(String section, String item, boolean show){
        getServiceSection(section).scrollTo();
        getServiceSection(section).$("table").scrollIntoView(false);
        SelenideElement itemTable = getServiceSection(section).$("table").$(byText(item));
        if(show) itemTable.shouldBe(visible);
        else itemTable.shouldNotBe(visible);
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section} у записи {item}")
    public ServicesPage clickButtonTable(String section, String item, String button){
        if(section.equals(IVR_MENU_TITLE) || section.equals(IVR_SOUND_FILES_TITLE)) contentWrapper.scrollIntoView(false);
        getServiceSection(section).scrollIntoView(false);
        getServiceSection(section)
                .$("table")
                .find(byText(item))
                .shouldHave(text(item))
                .closest("tr")
                .findAll(".layout i")
                .findBy(text(button))
                .click();
        return this;
    }

    @Step(value = "Нажимаем кнопку удаления в разделе {section} у записи {item}")
    public ServicesPage clickButtonTable(String section, String item){
        getServiceSection(section).scrollIntoView(false);
        getServiceSection(section)
                .$("table")
                .find(byText(item))
                .shouldHave(text(item))
                .closest("tr")
                .find("button")
                .click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли заголовок модального окна")
    public String getTextTitleModalWindow(){
        return titleModalWindow.getText();
    }

    @Step(value = "Вводим {value} в поле {field}")
    public ServicesPage sendInputModalWindow(String field, String ...value){
        if(value.length > 0) {
            SelenideElement input = $("input[aria-label='" + field + "']");
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(value);
        }
        return this;
    }

    @Step(value = "Получаем элемент контакта {contact}")
    public SelenideElement getContactName(String contact){
        if(itemTableContactName.size() == 0) return null;
        else return itemTableContactName.findBy(Condition.text(contact));
    }

    @Step(value = "Проверяем, что найден {search} контакт {contact}")
    public ServicesPage isContactName(String contact, boolean search) {
        if (search) getContactName(contact).shouldBe(Condition.visible);
        else getContactName(contact).shouldNotBe(Condition.visible);
        return this;
    }

    @Step(value = "Проверяем текст {text} с ссылкой {link} в разделе {section}")
    public ServicesPage isInfoTextWithLink(String section, String text){
        String hostServer = System.getProperty("hostserver");
        if(section.equals(FAX_USERS_TITLE))
            getServiceSection(section)
                    .find(".fax-link")
                    .shouldHave(text(text))
                    .find("a[href=\"http://" + hostServer + ":5000\"]")
                    .shouldBe(text("http://" + hostServer + ":5000"));
        else
            getServiceSection(section)
                    .find(".alert-link")
                    .shouldHave(text(text))
                    .find("a[href=\"http://" + hostServer + ":3010]\"]")
                    .shouldHave(text("http://" + hostServer + ":3010"));
        return this;
    }

    @Step(value = "Переходим в раздел {item}")
    @Override
    public ServicesPage clickItemLeftMenu(String item, boolean checkMenu) {
        SelenideElement itemMenu = listTitleMenuLeft.findBy(text(item));
        itemMenu.click();
        if(checkMenu) {
            itemMenu
                    .closest(".v-list-item--link")
                    .shouldHave(cssClass("active"));
            $(".align-center h1").shouldHave(text(item));
        }
        return this;
    }

    @Step(value = "Проверяем, что у меню {item} есть иконка")
    public ServicesPage isIconLeftMenu(String item, String className) {
        SelenideElement itemMenu = listTitleMenuLeft.findBy(text(item));
        itemMenu
                .closest(".v-list-item--link")
                .find("." + className)
                .shouldBe(visible);
        return this;
    }
}

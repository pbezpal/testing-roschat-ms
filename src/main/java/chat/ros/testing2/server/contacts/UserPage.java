package chat.ros.testing2.server.contacts;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import java.time.Duration;

import static chat.ros.testing2.data.ContactsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public class UserPage implements BasePage {

    //Элементы раздела пользователь
    private SelenideElement divWrapperUser = $("div.main-wrapper.user");
    private SelenideElement activeItemMenu = $("div.main-wrapper.user a.v-tabs__item.v-tabs__item--active");
    private ElementsCollection inactiveItemMenu = $$("div.main-wrapper.user a.v-tabs__item");
    private SelenideElement buttonNewAccount = $("#new-account button");
    private ElementsCollection inputsPassword = $$("form.v-form input[type='password']");
    private SelenideElement divProgressBar = $("div.v-progress-circular__info");
    private ElementsCollection spanValueAccount = $$("div.v-window__container span");
    private SelenideElement buttonActiveItem = $(".v-window-item:not([style='display: none;'])").find("button.primary");
    private SelenideElement formServices = $("div.v-dialog--active");
    private SelenideElement buttonsAddService = formServices.find("button.primary");
    private ElementsCollection listServices = formServices.$$("div.dialog-item h4");
    private ElementsCollection servicesMenuContent = $$("div.menuable__content__active a:not([disabled]) div.v-list__tile__title");
    private ElementsCollection listTitleServices = $$("div.service-info %1$s");

    private SelenideElement inputSelectServerTetra = $("div.v-select__selections");
    private ElementsCollection divListServerTetra = $$("div.v-select-list.v-card.theme--light div.v-list__tile__title");

    public UserPage() {}

    @Step(value = "Проверяем, что мы на странице Пользователь")
    protected boolean isDivWrapperUser(){
        try{
            divWrapperUser.shouldBe(visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Переходим в раздел {itemMenu}")
    protected UserPage clickMenuItem(String itemMenu){
        if( ! activeItemMenu.text().contains(itemMenu)) {
            inactiveItemMenu.findBy(text(itemMenu)).click();
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку Создать аккаунт")
    protected UserPage clickButtonAddAccount(){
        buttonNewAccount.shouldBe(visible);
        buttonNewAccount.click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт прогрессбар при добавлении аккаунта")
    protected boolean isWaitInvisibleProgressbar(){
        try{
            divProgressBar.shouldBe(not(visible), Duration.ofMillis(10000));
        }catch (ElementShould elementShould){
            return false;
        }

        return true;
    }

    @Step(value = "Вводим пароль и подтверждение пароля")
    protected UserPage sendInputsPassword(String password){
        inputsPassword.first().sendKeys(password);
        inputsPassword.last().sendKeys(password);
        return this;
    }

    @Step(value = "Проверяем, добавлена ли учётная запись")
    protected boolean isExistsAccount(String account){
        try{
            spanValueAccount.findBy(text(account)).shouldBe(visible);
        }catch (ElementShould elementShould){
            return false;
        }
        return true;
    }

    //Добавляем учётную запись пользователю
    public UserPage addUserAccount(String number, String password, String itemMenu) {
        String username = number + "@ros.chat";
        //Проверяем, что находимся на странице пользователя
        assertTrue(isDivWrapperUser(), "Не удалось перейти на страницу пользователя");
        //Переходим в раздел учётная запись
        clickMenuItem(itemMenu);
        //Нажимаем кнопку создать аккаунт
        clickButtonAddAccount();
        //Проверяем, появилась ли форма для редактирования
        isFormChange()
            .sendInputForm(USER_ACCOUNT_INPUT_USERNAME, username);
        //Вводим пароль
        sendInputsPassword(password);
        //Вводим номер SIP
        //sendInputForm(inputNumberSIP, number);
        //Проверяем, что кнопка Сохранить активна
        isActiveButtonSave()
                .clickButtonSave();
        //Ждем, когда пропадёт прогрессбар и проверяем добавлен ли аккаунт
        assertTrue(isWaitInvisibleProgressbar(), "Прогрессбар завис");
        //Проверяем, появилась ли учётная запись пользователя
        assertTrue(isExistsAccount(username), "Учётная запись не добавлена");

        return this;
    }

    /*@Step(value = "Нажимаем кнопку добавить в разделе Сервисы")
    private UserPage clickButtonService(){
        buttonsAddService.click();
        return this;
    }*/

    @Step(value = "Проверяем, что сервис {service} доступен для добавления")
    private boolean isNotDisabledService(String service){
        try{
            servicesMenuContent.findBy(text(service)).shouldBe(enabled);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Нажимаем кнопку Добавить в разделе {item}")
    private UserPage clickButtonAddOfService(String item){
        $(".service")
                .shouldBe(visible, Duration.ofSeconds(2))
                .parent()
                .find("button.primary")
                .click();
        return this;
    }

    @Step(value = "Выбираем тип сервиса {service}")
    private UserPage clickAddTypeService(String service){
        listServices.findBy(text(service)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Добавить")
    private UserPage clickButtonAddService(){
        buttonsAddService.shouldBe(visible, Duration.ofSeconds(3)).click();
        return this;
    }

    @Step(value = "Настраиваем сервис SIP. Прописываем абоненту SIP номер {number}")
    private UserPage sendSipNumber(String number){
        sendInputForm(USER_SERVICE_INPUT_SIP, number);
        return this;
    }

    @Step(value = "Выбираем сервер Тетра {server}")
    private UserPage selectServerTetra(String server){
        inputSelectServerTetra.click();
        divListServerTetra.findBy(text(server)).click();
        return this;
    }

    @Step(value = "Вводим номер SSI {number}")
    private UserPage sendInputNumberSSI(String number){
        sendInputForm(USER_SERVICES_INPUT_TETRA_SSI, number);
        return this;
    }

    @Step(value = "Проверяем, отображается ли {show} значение {text} в списке сервисов")
    public UserPage isShowService(String element, String text, boolean show){
        if(show) $$("div.service-info " + element).findBy(text(text)).shouldBe(visible);
        else $$("div.service-info " + element).findBy(text(text)).shouldNotBe(visible);
        return this;
    }

    /**
     * This method add service roschat to client
     * @param itemMenu name section of services roschat
     * @param type what service of roschat to be added
     * @return instance UserPage
     */
    public UserPage addServices(String itemMenu, String type){
        clickMenuItem(itemMenu)
                .clickButtonAddOfService(itemMenu)
                .clickAddTypeService(type)
                .clickButtonAddService();
        return this;
    }

    //Добавляем сервис Тетра
    public UserPage addServices(String itemMenu, String type, String server, String ssi){
        //Переходим в раздел Сервисы
        clickMenuItem(itemMenu)
                .clickButtonAddOfService(itemMenu)
                .clickAddTypeService(type)
                .clickButtonAddService()
                .selectServerTetra(server)
                .sendInputNumberSSI(ssi)
                .clickButtonSave();
        return this;
    }

    /**
     * add sip service fo contact
     * @param itemMenu menu for add sip service
     * @param type select sip service
     * @param number sip number
     * @return
     */
    public UserPage addServices(String itemMenu, String type, String number){
        //Переходим в раздел Сервисы
        clickMenuItem(itemMenu)
                .clickButtonAddOfService(itemMenu)
                .clickAddTypeService(type)
                .clickButtonAddService()
                .sendSipNumber(number)
                .clickButtonSave();
        return this;
    }
}

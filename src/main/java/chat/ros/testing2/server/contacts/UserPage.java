package chat.ros.testing2.server.contacts;

import chat.ros.testing2.server.MSGeneralElements;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.ContactsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public class UserPage implements MSGeneralElements {

    //Элементы раздела пользователь
    private SelenideElement divWrapperUser = $("div.main-wrapper.user");
    private SelenideElement activeItemMenu = $("div.main-wrapper.user a.v-tabs__item.v-tabs__item--active");
    private ElementsCollection inactiveItemMenu = $$("div.main-wrapper.user a.v-tabs__item");
    private SelenideElement buttonNewAccount = $("div#new-account button");
    private ElementsCollection inputsPassword = $$("form.v-form input[type='password']");
    private SelenideElement divProgressBar = $("div.v-progress-circular__info");
    private ElementsCollection spanValueAccount = $$("div.v-window__container span");
    private SelenideElement buttonsAddService = $x("//div[@class='service']//ancestor::div[@class='main-block']//button");
    private ElementsCollection servicesMenuContent = $$("div.menuable__content__active a:not([disabled]) div.v-list__tile__title");
    private ElementsCollection listServices = $$("div.service-info h4.service-name");
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
            divProgressBar.waitUntil(not(visible), 10000);
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
        assertTrue(isFormChange(), "Форма для добавления учётной записи не появилась");
        //Вводим имя пользователя
        sendInputForm(USER_ACCOUNT_INPUT_USERNAME, username);
        //Вводим пароль
        sendInputsPassword(password);
        //Вводим номер SIP
        //sendInputForm(inputNumberSIP, number);
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки аккаунта, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();
        //Ждем, когда пропадёт прогрессбар и проверяем добавлен ли аккаунт
        assertTrue(isWaitInvisibleProgressbar(), "Прогрессбар завис");
        //Проверяем, появилась ли учётная запись пользователя
        assertTrue(isExistsAccount(username), "Учётная запись не добавлена");

        return this;
    }

    @Step(value = "Нажимаем кнопку добавить в разделе Сервисы")
    private UserPage clickButtonService(){
        buttonsAddService.click();
        return this;
    }

    @Step(value = "Проверяем, что сервис {service} доступен для добавления")
    private boolean isNotDisabledService(String service){
        try{
            servicesMenuContent.findBy(text(service)).shouldBe(enabled);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем тип сервиса {service}")
    private UserPage clickAddTypeService(String service){
        servicesMenuContent.findBy(text(service)).click();
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

    @Step(value = "Проверяем, появился сервир в списке сервисов")
    public boolean isShowService(String service){
        try{
            listServices.findBy(text(service)).shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    //Добавляем сервисы
    public UserPage addServices(String itemMenu, String type){
        //Переходим в раздел Сервисы
        clickMenuItem(itemMenu);
        clickButtonService();
        clickAddTypeService(type);
        return this;
    }

    //Добавляем сервис Тетра
    public UserPage addServices(String itemMenu, String type, String server, String ssi){
        //Переходим в раздел Сервисы
        clickMenuItem(itemMenu);
        clickButtonService();
        clickAddTypeService(type);
        selectServerTetra(server);
        sendInputNumberSSI(ssi);
        clickButtonSave();
        return this;
    }

    //Добавляем сервис SIP
    public UserPage addServices(String itemMenu, String type, String number){
        //Переходим в раздел Сервисы
        clickMenuItem(itemMenu);
        clickButtonService();
        clickAddTypeService(type);
        sendSipNumber(number);
        clickButtonSave();
        return this;
    }
}

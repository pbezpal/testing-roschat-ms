package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.File;
import java.time.Duration;

import static chat.ros.testing2.data.SettingsData.ALERT_TITLE_SECTION_USERS;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class AlertPage extends ServicesPage {

    private String hostServer = System.getProperty("hostserver");

    private SelenideElement logoutActionForm = $(".v-dialog--active");
    private SelenideElement iconLogoutActionForm = logoutActionForm.find("i");
    private SelenideElement titleLogoutActionForm = logoutActionForm.find("h4");
    private SelenideElement textLogoutActionForm = logoutActionForm.find("p");
    private ElementsCollection buttonsLogoutActionForm = logoutActionForm.findAll("span");

    //List of top menu items
    private ElementsCollection listItemsTopMenu = $$(".v-tabs-bar__content .v-tab");
    //List alert tools
    private SelenideElement listAlertTools = $("#alert-tools");
    private ElementsCollection listInfoTitleAlertTools = listAlertTools.findAll(".info-title");

    //Upload sound file
    private ElementsCollection listButtonOfModalWindow = $$(".modal-window__actions button span");

    //Write fileds of modal window

    public SelenideElement getElementLinkToAlert() {
        return getServiceSection(ALERT_TITLE_SECTION_USERS)
                .find(".alert-link")
                .shouldHave(text("Для входа в систему пользователю необходимо перейти по ссылке: "))
                .find("a[href=\"http://" + hostServer + ":3010\"]");
    }

    @Step(value = "Проверяем текст ссылки для перехода на страницу управления системой оповещения")
    public AlertPage isTextLinkToGoMSAlertPage() {
        getElementLinkToAlert()
                .shouldBe(visible, text("http://" + hostServer + ":3010"));
        return this;
    }

    @Step(value = "Нажимаем на ссылку для перехода на страницу управления системой оповещения")
    public ServicesPage clickLinkToGoMSAlertPage() {
        getElementLinkToAlert().click();
        switchTo().window(1);
        return new ServicesPage();
    }

    @Step(value = "Проверяем инфомационный текст {text} в разделе {section}")
    public AlertPage isInfoText(String section, String text) {
        getServiceSection(section)
                .find(".d-flex p")
                .shouldBe(visible, text(text));
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section}")
    public AlertPage clickButtonAction(String section, String button) {
        getServiceSection(section)
                .findAll(".flex.buttons button div")
                .findBy(text(button))
                .click();
        return this;
    }

    @Step(value = "Проверяем текст {text} после внесения изменений в поле {field} в разделе {section}")
    public AlertPage isTextAfterAction(String section, String field, String text) {
        getServiceSection(section).scrollIntoView(false);
        getServiceSection(section)
                .findAll(".selected-support-info")
                .findBy(text(field))
                .parent()
                .find("span")
                .shouldBe(visible, text(text));
        return this;
    }

    @Step(value = "Проверяем отображаются ли все элементы формы подтверждения выхода и нажимаем кнопку {button}")
    public ServicesPage clickButtonConfirmAction(String title, String text, String button) {
        iconLogoutActionForm.shouldBe(visible);
        //titleLogoutActionForm.shouldBe(text("Подтверждение выхода"));
        titleLogoutActionForm.shouldBe(text(title));
        //textLogoutActionForm.shouldHave(text("Вы уверены, что хотите выйти из приложения?"));
        textLogoutActionForm.shouldHave(text(text));
        buttonsLogoutActionForm.findBy(text(button)).click();
        return this;
    }

    @Step(value = "Переходим в рездел {item}")
    public AlertPage clickItemTopMenu(String item, boolean check) {
        String titleTopText = null;
        SelenideElement itemMenu = listItemsTopMenu.findBy(text(item));
        itemMenu.click();
        if (check) {
            itemMenu.shouldHave(cssClass("active-tab"));
            itemMenu.shouldHave(cssClass("v-tab--active"));
            if (item.equals("Список заданий"))
                titleTopText = "Задания";
            else
                titleTopText = item;
            $(".align-center h1").shouldHave(text(titleTopText));
        }
        return this;
    }

    @Step(value = "Проверяем, что отбражается статус {status} для оповещения {alert}")
    public AlertPage isStatusAlert(String alert, String classNameIcon, String... dataStatusError) {
        //SelenideElement alertTool = $x("//*[@id='alert-tools']//*[text()='" + alert +"']//ancestor::*[@class='alert-tool']");
        SelenideElement alertTool = $("#alert-tools ." + classNameIcon).closest(".alert-tool");
        SelenideElement infoStatusAlert = alertTool.find(".alert-tool__info");
        alertTool
                .hover();
        String status = dataStatusError[0];
        infoStatusAlert
                .find(".info-title")
                .shouldHave(text(alert))
                .shouldBe(visible);
        if (status.equals("ок")) {
            alertTool
                    .find(".success.success--text ." + classNameIcon)
                    .shouldBe(visible);
            infoStatusAlert
                    .shouldHave(cssClass("active"))
                    .shouldBe(visible);
            infoStatusAlert
                    .findAll("p")
                    .findBy(text("Статус"))
                    .shouldHave(text("Статус: " + status))
                    .shouldBe(visible);
        }else {
            alertTool
                    .find(".error.error--text ." + classNameIcon)
                    .shouldBe(visible);
            infoStatusAlert
                    .shouldHave(cssClass("inactive"))
                    .shouldBe(visible);
            infoStatusAlert
                    .findAll("p")
                    .findBy(text("Статус"))
                    .shouldHave(text("Статус: " + status))
                    .shouldBe(visible);
            if( ! alert.equals("Сообщение")) {
                String error = dataStatusError[1];
                infoStatusAlert
                        .findAll("p")
                        .findBy(text("Ошибка"))
                        .shouldHave(text("Ошибка: " + error))
                        .shouldBe(visible);
            }
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку Добавить")
    public AlertPage clickButtonAddService(){
        $(".table button").click();
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в модальном окне")
    public AlertPage clickButtonOfModalWindow(String button){
        $$(".modal-window__actions button span")
                .findBy(text(button))
                .click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли {show} значение {value} в таблице")
    public AlertPage isVisibleValueOfTable(String value, boolean show){
        SelenideElement valueTable = $("table").findAll("td").findBy(text(value));
        if(show)
            valueTable.shouldBe(visible);
        else
            valueTable.shouldNotBe(visible);
        return this;
    }

    @Step(value = "Нажимаем кнопку действия {button} в таблице у записи {value}")
    public AlertPage clickButtonActionOfTable(String value, String button){
        $("table")
                .findAll("td")
                .findBy(text(value))
                .parent()
                .find(byTitle(button))
                .click();
        return this;
    }

    @Step(value = "Вводим в поле {field} значение {value}")
    public AlertPage sendFieldModalWindow(String field, String value){
        SelenideElement fieldText;
        SelenideElement parnetFieldText = getModalWindow()
                .findAll("label")
                .findBy(text(field))
                .parent();
        if(parnetFieldText.find("input").exists())
                fieldText = parnetFieldText.find("input");
        else fieldText = parnetFieldText.find("textarea");
        fieldText.sendKeys(Keys.CONTROL + "a");
        fieldText.sendKeys(Keys.BACK_SPACE);
        fieldText.sendKeys(value);
        return this;
    }

    @Step(value = "Вызываем список в поле {field}")
    public AlertPage clickComboBox(String field){
        getModalWindow()
                .findAll("label")
                .findBy(text(field))
                .parent()
                .find("i")
                .click();
        return this;
    }

    @Step(value = "Выбираем значение {value} из списка")
    public AlertPage selectValueFromList(String value){
        activeContextMenu
                .findAll(".v-list-item__title")
                .findBy(text(value))
                .click();
        return this;
    }

    @Step(value = "Проверяем наличие иконки {classNameIcon} у контакта {contact} в модально окне списка оповещения")
    public AlertPage isIconHeartsAlert(String contact, String classNameIcon){
        getModalWindow()
                .find("table")
                .findAll("td")
                .findBy(text(contact))
                .parent()
                .find("." + classNameIcon)
                .shouldBe(visible);
        return this;
    }

    @Step(value = "Вызываем список сервисов для контакта")
    public AlertPage clickListCheckBoxServicesOfContact(String contact){
        getModalWindow()
                .find("table")
                .findAll("td")
                .findBy(text(contact))
                .parent()
                .find(".mdi-dots-vertical")
                .click();
        return this;
    }

    @Step(value = "Выбираем сервис {service} для контакта {contact}")
    public AlertPage selectServiceOfContact(String contact, String service, String email){
        SelenideElement serviceName;
        if(service.equals("email"))
            serviceName = $$(".menuable__content__active .v-list-item__title")
                    .findBy(text(service + ": " + email));
        else
            serviceName = $$(".menuable__content__active .v-list-item__title")
                    .findBy(text(service + ": " + contact));
        serviceName
                .closest(".alert-tool-item")
                .find(".mdi-checkbox-marked")
                .click();
        return this;
    }

    @Step(value = "Проверяем выбран ли {select} сервис {service} в средствах оповещения для контакта {contact}")
    public AlertPage isSelectServiceAlertOfContact(String contact, String service, boolean select, String... email){
        SelenideElement serviceName;
        if(email.length > 0)
            serviceName = $$(".menuable__content__active .v-list-item__title")
                    .findBy(text(service + ": " + email[0]));
        else
            serviceName = $$(".menuable__content__active .v-list-item__title")
                    .findBy(text(service + ": " + contact));
        if(select)
            serviceName
                    .closest(".alert-tool-item")
                    .find("input")
                    .shouldBe(selected);
        else
            serviceName
                    .closest(".alert-tool-item")
                    .find("input")
                    .shouldNotBe(selected);
        return this;
    }

    @Step(value = "Включаем/выключаем сервис оповещения {alert}")
    public AlertPage clickOnOffAlert(String alert){
        SelenideElement alertName = $$(".v-list-group .v-list-item__title")
                .findBy(text(alert));
        if (alertName.closest(".v-list-group--disabled").exists()) {
            alertName
                    .closest(".v-list-group__header")
                    .find(".v-input--selection-controls__ripple")
                    .click();
            alertName.closest(".v-list-group--active").shouldBe(visible);
        }else if (alertName.closest(".v-list-group--active").exists()){
            alertName
                    .closest(".v-list-group__header")
                    .find(".v-input--selection-controls__ripple")
                    .click();
            alertName.closest(".v-list-group--disabled").shouldBe(visible);
        }
        return this;
    }

    @Step(value = "Проверяем форму выполнения задания")
    public AlertPage isVisibleTaskProgress(boolean show){
        if(show) $("table .runner-progress").shouldBe(visible);
        else $("table .runner-progress").shouldNotBe(visible, Duration.ofMinutes(1));
        return this;
    }

    @Step(value = "Проверяем, появилась надписи {text}")
    public AlertPage isTextTaskProgress(String text){
        $("table .v-progress-linear__content strong").shouldHave(text(text), Duration.ofMinutes(1));
        return this;
    }
}

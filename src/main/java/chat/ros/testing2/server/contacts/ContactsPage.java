package chat.ros.testing2.server.contacts;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ContactsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ContactsPage implements BasePage {

    //Элементы раздела Справочник
    private SelenideElement formNewContact = $("div#new-contact");
    private SelenideElement buttonSaveContact = formNewContact.find("button.v-btn.theme--light.primary");
    private SelenideElement inputSearchContact = $("input[aria-label='Найти']");
    private ElementsCollection tdSearchContact = $$("table.v-datatable.v-table.theme--light td");
    private SelenideElement selectShowCountContacts = $("div.v-input__append-inner");
    private ElementsCollection listCountShowContacts = $$("div.v-select-list.v-card div.v-list__tile__title");
    private ElementsCollection trCountContacts = $$("table.v-datatable.v-table.theme--light tr");
    private String locatorInputContactForm = "input[aria-label='%1$s']";

    public ContactsPage () {}

    @Step(value = "Проверяем, появилась ли форма для добавления нового контакта")
    protected ContactsPage isFormNewContact(){
        formNewContact.shouldBe(Condition.visible);
        return this;
    }

    protected Map<String, String> getMapInputsValueContact(String... values){
        switch (values.length) {
            case 1:
                return new HashMap() {{
                    put(CONTACT_INPUT_LASTNAME, values[0]);
                    put(CONTACT_INPUT_PHONE_JOB, values[0]);
                }};
            case 2:
                return new HashMap() {{
                    put(CONTACT_INPUT_LASTNAME, values[0]);
                    put(CONTACT_INPUT_PHONE_JOB, values[0]);
                    put(CONTACT_INPUT_EMAIL, values[1]);
                }};
        }
        return null;
    }

    @Step(value = "Вводим в поле {field} значение {value}")
    protected ContactsPage sendInputsContact(Map<String, String> mapInputValueContact){
        for(Map.Entry<String, String> entry : mapInputValueContact.entrySet()){
            String input = String.format(locatorInputContactForm,entry.getKey());
            formNewContact.find(input).sendKeys(Keys.CONTROL + "a");
            formNewContact.find(input).sendKeys(Keys.BACK_SPACE);
            formNewContact.find(input).sendKeys(entry.getValue());
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку Сохранить")
    protected ContactsPage clickButtonSaveContact(){
        buttonSaveContact.click();
        return this;
    }

    @Step(value = "Вводим в поле поиска значение {value}")
    public ContactsPage sendInputSearchContact(String value){
        inputSearchContact.sendKeys(Keys.CONTROL + "a");
        inputSearchContact.sendKeys(Keys.BACK_SPACE);
        inputSearchContact.sendKeys(value);
        return this;
    }

    @Step(value = "Переходим в раздел Пользователь контакта {contact}")
    public UserPage clickContact(String contact){
        tdSearchContact.findBy(text(contact)).click();
        return new UserPage();
    }

    @Step(value = "Проверяем количество количество пользователей")
    public int countContacts(){
        selectShowCountContacts.scrollIntoView(false);
        selectShowCountContacts.click();
        listCountShowContacts.findBy(text("Все")).click();
        inputSearchContact.shouldBe(visible, Duration.ofSeconds(10));
        return trCountContacts.size();
    }

    /**
     * add contact
     * @param contact the contact name and number telephone for contact
     * @return
     */
    public UserPage actionsContact(String... contact){
        //Вводим фамилию в поле поиска
        sendInputSearchContact(contact[0]);
        //Проверяем, что контакта нет в таблице
        if(!tdSearchContact.findBy(text(contact[0])).isDisplayed()) {
            //Нажимаем кнопку добавить
            clickButtonAdd();
            //Проверяем, появилась ли форма для добавления контакта
            isFormNewContact();
            //Вводим данные контакта
            sendInputsContact(getMapInputsValueContact(contact));
            clickButtonSaveContact();
            //Вводим фамилию в поле поиска
            sendInputSearchContact(contact[0]);
        }
        //Проверяем, добавился ли контакт в БД контактов
        isExistsTableText(contact[0], true);
        //Переходим к настройкам учётной записи контакта
        return clickContact(contact[0]);
    }

}

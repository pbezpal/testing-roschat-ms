package chat.ros.testing2.server.contacts;

import chat.ros.testing2.data.MSGeneralElements;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ContactsData.CONTACT_INPUT_LASTNAME;
import static chat.ros.testing2.data.ContactsData.CONTACT_INPUT_PHONE_JOB;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ContactsPage implements MSGeneralElements {

    //Элементы раздела Справочник
    private SelenideElement buttonAddContact = $("div.action-bar button.v-btn.theme--light.primary");
    private SelenideElement formNewContact = $("div#new-contact");
    private SelenideElement buttonSaveContact = formNewContact.find("button.v-btn.theme--light.primary");
    private SelenideElement inputSearchContact = $("input[aria-label='Найти']");
    private ElementsCollection tdSearchContact = $$("table.v-datatable.v-table.theme--light td");
    private SelenideElement selectShowCountContacts = $("div.v-input__append-inner");
    private ElementsCollection listCountShowContacts = $$("div.v-select-list.v-card div.v-list__tile__title");
    private ElementsCollection trCountContacts = $$("table.v-datatable.v-table.theme--light tr");

    public ContactsPage () {}

    @Step(value = "Проверяем, что контакта {contact} нет в таблице")
    public boolean isNotSearchContact(String contact){
        try{
            tdSearchContact.findBy(text(contact)).shouldNotBe(Condition.visible);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    @Step(value = "Нажимаем кнопку Добавить")
    protected ContactsPage clickButtonAddContact(){
        buttonAddContact.click();
        return this;
    }

    @Step(value = "Проверяем, появилась ли форма для добавления нового контакта")
    protected boolean isFormNewContact(){
        try{
            formNewContact.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    protected Map<String, String> getMapInputsValueContact(String lastName, String phoneJob){
        Map<String, String> mapInputValueContact = new HashMap() {{
            put(CONTACT_INPUT_LASTNAME, lastName);
            put(CONTACT_INPUT_PHONE_JOB, phoneJob);
        }};
        return mapInputValueContact;
    }

    @Step(value = "Вводим в поле {field} значение {value}")
    protected ContactsPage sendInputsContact(Map<String, String> mapInputValueContact){
        for(Map.Entry<String, String> entry : mapInputValueContact.entrySet()){
            formNewContact.find("input[aria-label='" + entry.getKey() + "']").sendKeys(Keys.CONTROL + "a");
            formNewContact.find("input[aria-label='" + entry.getKey() + "']").sendKeys(Keys.BACK_SPACE);
            formNewContact.find("input[aria-label='" + entry.getKey() + "']").sendKeys(entry.getValue());
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

    @Step(value = "Проверяем, появился ли контакт {contact} в таблице")
    protected boolean isSearchContact(String contact){
        try{
            tdSearchContact.findBy(text(contact)).shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
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
        return trCountContacts.size();
    }

    //Добавляем контакт
    public UserPage addContact(String contact){
        //Вводим фамилию в поле поиска
        sendInputSearchContact(contact);
        //Проверяем, что контакта нет в таблице
        if(isNotSearchContact(contact)) {
            //Нажимаем кнопку добавить
            clickButtonAddContact();
            //Проверяем, появилась ли форма для добавления контакта
            assertTrue(isFormNewContact(), "Форма для добаления контакта не появилась");
            //Вводим данные контакта
            sendInputsContact(getMapInputsValueContact(contact, contact));
            clickButtonSaveContact();
            //Вводим фамилию в поле поиска
            sendInputSearchContact(contact);
        }
        //Проверяем, добавился ли контакт в БД контактов
        assertTrue(isSearchContact(contact), "Контакт " + contact + " не добавлен в БД контактов");
        //Переходим к настройкам учётной записи контакта
        return clickContact(contact);
    }

}

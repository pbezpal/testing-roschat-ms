package chat.ros.testing2.pages.contacts;

import chat.ros.testing2.data.MSGeneralElements;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

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

    @Step(value = "Вводим в поле {field} значение {value}")
    protected ContactsPage sendInputContact(String field, String value){
        formNewContact.find("input[aria-label='" + field + "']").sendKeys(value);
        return this;
    }

    @Step(value = "Нажимаем кнопку Сохранить")
    protected ContactsPage clickButtonSaveContact(){
        buttonSaveContact.click();
        return this;
    }

    @Step(value = "Вводим в поле поиска значение {value}")
    protected ContactsPage sendInputSearchContact(String value){
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
            //Запилняет поле фамилия
            sendInputContact(CONTACT_INPUT_LASTNAME, contact);
            //Заполняем номер рабочего телефона
            sendInputContact(CONTACT_INPUT_PHONE_JOB, contact);
            //Нажимаем кнопку Сохранить
            clickButtonSaveContact();
            //Вводим фамилию в поле поиска
            sendInputSearchContact(contact);
        }
        //Проверяем, добавился ли контакт в БД контактов
        assertTrue(isSearchContact(contact), "Контакт " + contact + " не добавлен в БД контактов");
        //Переходим к настройкам учётной записи контакта
        tdSearchContact.findBy(text(contact)).click();

        return new UserPage();
    }

}

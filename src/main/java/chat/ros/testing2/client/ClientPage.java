package chat.ros.testing2.client;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementNotVisibleException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ClientPage {

    private SelenideElement divSide = $("div#side");
    private SelenideElement itemComments = $("i.fa.fa-comments");
    private ElementsCollection itemsToolbar = $$("div.toolbar-wrapper span");
    private SelenideElement contextMenu = $("div.chat-list.content svg");
    private ElementsCollection itemsContextMenu = $$("div#context-menu li");
    private SelenideElement inputNameChannel = $("div.channel-create input[type='text']");
    private SelenideElement buttonCreateChannel = $("div.footer button.btn.btn-primary");
    private SelenideElement itemListChat = $("div.chat-list-item div.name");
    private SelenideElement divMainHeader = $("div.main-header");

    public ClientPage() {}

    @Step(value = "Проверяем, что мы успешно авторизовались на клиенте")
    private boolean isLoginClient(){
        try{
            divSide.shouldBe(Condition.visible);
        }catch (ElementNotVisibleException element){
            return false;
        }

        return true;
    }

    @Step(value = "Переходим в раздел Беседы")
    private ClientPage clickItemComments(){
        itemComments.click();
        return this;
    }

    @Step(value = "Вызываем контекстное меню")
    private ClientPage clickContextMenu(){
        contextMenu.click();
        return this;
    }

    @Step(value = "Выбираем элемент контекстного меню {item}")
    private ClientPage clickItemContextMenu(String item){
        itemsContextMenu.findBy(Condition.text(item)).click();
        return this;
    }

    @Step(value = "Вводим имя нового канала {name}")
    private ClientPage sendInputNameChannel(String name){
        inputNameChannel.sendKeys(name);
        return this;
    }

    @Step(value = "Нажимаем кнопку Создать")
    private ClientPage clickCreateNewChannel(){
        buttonCreateChannel.click();
        return this;
    }

    @Step(value = "Проверяем, что канал создался")
    private boolean isCreateChannel(String name){
        return itemListChat.find("span").text().contains(name);
    }

    @Step(value = "Проверяем, что в разделе Беседы у канала появился статус проверенного")
    public boolean isStatusTestedChannelListChat() {
        try{
            itemListChat.find("i.fa-check").shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, что в заголовке канала появился статус проверенного")
    public boolean isStatusTestedChannelMainHeader() {
        itemListChat.click();
        try{
            divMainHeader.find("i.fa-check").shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    public ClientPage createNewChannel(String name, String item){
        assertTrue(isLoginClient(), "Клиент не авторизовался");
        clickItemComments();
        clickContextMenu();
        clickItemContextMenu(item);
        sendInputNameChannel(name);
        clickCreateNewChannel();
        assertTrue(isCreateChannel(name), "Новый кнал не найден");
        return this;
    }

    public ClientPage checkStatusTestedChannel(String item){
        assertTrue(isLoginClient(), "Клиент не авторизовался");
        clickItemComments();
        assertTrue(isStatusTestedChannelListChat(), "Отсутствует статус Проверенный у канала в разделе Беседы");
        assertTrue(isStatusTestedChannelMainHeader(), "Отсутствует статус Проверенный в заголовке канала");
        return this;
    }
}

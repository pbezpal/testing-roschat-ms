package chat.ros.testing2.client;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;

import static chat.ros.testing2.data.ChannelsData.CLIENT_NAME_CHANNEL_CLOSED;
import static chat.ros.testing2.data.ChannelsData.CLIENT_NAME_CHANNEL_PUBLIC;
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
    private SelenideElement inputSearch = $("div.search-wrapper input");
    private SelenideElement spanItemChannel = $("div.filter.channels span");
    private ElementsCollection radioTypeChannel = $$("form.custom-radio label");

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

    @Step(value = "Вводим имя нового канала {channel}")
    private ClientPage sendInputNameChannel(String channel){
        inputNameChannel.sendKeys(channel);
        return this;
    }

    @Step(value = "Нажимаем кнопку Создать")
    private ClientPage clickCreateNewChannel(){
        buttonCreateChannel.click();
        return this;
    }

    @Step(value = "Проверяем, что канал создался")
    private boolean isCreateChannel(String channel){
        return itemListChat.find("span").text().contains(channel);
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
    private boolean isStatusTestedChannelMainHeader() {
        itemListChat.click();
        try{
            divMainHeader.find("i.fa-check").shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, что канал не найден")
    public boolean isNotSearchChannel() {
        try{
            divMainHeader.shouldNotBe(Condition.visible);
        }catch (ElementShould e){
            return false;
        }
        return true;
    }

    @Step(value = "Вводим в поле поиска {text}")
    private ClientPage sendInputSearch(String text){
        inputSearch.sendKeys(Keys.CONTROL + "a");
        inputSearch.sendKeys(Keys.BACK_SPACE);
        inputSearch.sendKeys(text);
        return this;
    }

    @Step(value = "Переходим в раздел Каналы")
    private ClientPage clickItemChannels(){
        inputSearch.click();
        spanItemChannel.click();
        return this;
    }

    private boolean isNotActiveRadioTypeChannel(String type){
        try{
            radioTypeChannel.findBy(Condition.text(type)).find("input[value='false']").isEnabled();
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем тип канала {type}")
    private ClientPage clickTypeChannel(String type){
        if(isNotActiveRadioTypeChannel(type)) radioTypeChannel.findBy(Condition.text(type)).find("i").click();
        return this;
    }

    //Создаём канал
    public ClientPage createNewChannel(String name, String item, String type){
        assertTrue(isLoginClient(), "Клиент не авторизовался");
        clickItemComments();
        clickContextMenu();
        clickItemContextMenu(item);
        sendInputNameChannel(name);
        clickTypeChannel(type);
        clickCreateNewChannel();
        assertTrue(isCreateChannel(name), "Новый кнал не найден");
        return this;
    }

    //Проверяем, что каналы отобража.тся у администратора канала после авторизации
    public ClientPage checkStatusTestedChannel(String item){
        assertTrue(isLoginClient(), "Клиент не авторизовался");
        clickItemComments();
        assertTrue(isStatusTestedChannelListChat(), "Отсутствует статус Проверенный у канала в разделе Беседы");
        assertTrue(isStatusTestedChannelMainHeader(), "Отсутствует статус Проверенный в заголовке канала");
        return this;
    }

    //Ищем канал
    public ClientPage searchChannel(String text, String type){
        assertTrue(isLoginClient(), "Клиент не авторизовался");
        clickItemChannels();
        sendInputSearch(text);
        if(type.equals(CLIENT_NAME_CHANNEL_PUBLIC)) {
            assertTrue(isStatusTestedChannelListChat(), "Отсутствует статус Проверенный у канала в разделе Беседы");
            assertTrue(isStatusTestedChannelMainHeader(), "Отсутствует статус Проверенный в заголовке канала");
        }else if(type.equals(CLIENT_NAME_CHANNEL_CLOSED)) {
            assertTrue(isNotSearchChannel(), "Закрытый канал отображается в списке каналов при поиске");
        }

        return this;
    }
}

package chat.ros.testing2.webclient.comments;

import chat.ros.testing2.webclient.ClientPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.ChannelsData.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ClientChannelsPage implements ClientCommentsPage {

    private SelenideElement inputNameChannel = $("div.channel-create input[type='text']");
    private SelenideElement buttonCreateChannel = $("div.footer button.btn.btn-primary");
    private SelenideElement spanItemChannel = $("div.filter.channels span");
    private ElementsCollection radioTypeChannel = $$("form.custom-radio label");
    private String statusTestedChannel = "i.fa-check";

    public ClientChannelsPage(){}

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

    @Step(value = "Переходим в раздел Каналы")
    private ClientPage clickItemChannels(){
        inputSearch.click();
        spanItemChannel.click();
        return this;
    }

    @Step(value = "Проверяем, что в заголовке канала появился статус проверенного")
    public boolean isStatusTestedChannelMainHeader() {
        itemListChat.click();
        try{
            divMainHeader.find(statusTestedChannel).shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    public boolean isNotActiveRadioTypeChannel(String type){
        try{
            radioTypeChannel.findBy(Condition.text(type)).find("input[value='false']").isEnabled();
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем тип канала {type}")
    public ClientPage clickTypeChannel(String type){
        if(isNotActiveRadioTypeChannel(type)) radioTypeChannel.findBy(Condition.text(type)).find("i").click();
        return this;
    }

    //Ищем канал
    public boolean searchChannel(String text, String type){
        clickItemChannels();
        sendInputSearch(text);
        if(type.equals(CLIENT_TYPE_CHANNEL_PUBLIC)) {
            return isStatusTestedChannelListChat() && isStatusTestedChannelMainHeader();
        }else if(type.equals(CLIENT_TYPE_CHANNEL_CLOSED)) {
            return isExistComments(text, "No");
        }
        return false;
    }

    @Step(value = "Проверяем, что в разделе Беседы у канала появился статус проверенного")
    public boolean isStatusTestedChannelListChat() {
        try{
            itemListChat.find(statusTestedChannel).shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    //Создаём канал
    public ClientChannelsPage createNewChannel(String name, String item, String type){
        clickItemComments();
        clickContextMenu();
        clickItemContextMenu(item);
        sendInputNameChannel(name);
        clickTypeChannel(type);
        clickCreateNewChannel();
        return this;
    }
}

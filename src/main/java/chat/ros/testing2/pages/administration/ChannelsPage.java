package chat.ros.testing2.pages.administration;

import chat.ros.testing2.client.ClientPage;
import chat.ros.testing2.client.LoginPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ChannelsPage extends LoginPage {

    private SelenideElement spanNameChannel = $("table.v-datatable.v-table.theme--light tbody td.name-td span");
    private SelenideElement buttonTestedChannel = $("table.v-datatable.v-table.theme--light tbody button");
    private SelenideElement buttonContinueAction = $("div.actions-wrapper button.primary--text");

    public ChannelsPage () {}

    //Создаём новый канал
    public ClientPage createNewChannel(String login, String password, String name, String item){
        return sendDataLogin(login, password).createNewChannel(name, item);
    }

    public ClientPage checkStatusTestedChannel(String login, String password, String item){
        return sendDataLogin(login, password).checkStatusTestedChannel(item);
    }

    @Step(value = "Проверяем, что новый канал отображается в СУ")
    private boolean isShowNewChannel(String name){
        try{
            spanNameChannel.shouldBe(Condition.text(name));
        }catch (ElementNotFound elementNotFound){
            return false;
        }

        return true;
    }

    @Step(value = "Делаем канал проверенным")
    private ChannelsPage clickButtonTestedChannel(){
        buttonTestedChannel.click();
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Продолжить'")
    private ChannelsPage clicckButtonContinueAction(){
        buttonContinueAction.click();
        return this;
    }

    public ChannelsPage doTestedChannel(String name){
        assertTrue(isShowNewChannel(name), "Канал не отображается в СУ");
        clickButtonTestedChannel();
        clicckButtonContinueAction();
        return this;
    }
}

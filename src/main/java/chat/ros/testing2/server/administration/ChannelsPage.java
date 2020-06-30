package chat.ros.testing2.server.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class ChannelsPage extends client.comments.ChannelsPage {

    private ElementsCollection spanNameChannel = $$("table.v-datatable.v-table.theme--light tbody td.name-td span");
    private SelenideElement buttonContinueAction = $("div.actions-wrapper button.primary--text");

    public ChannelsPage () {}

    @Step(value = "Возвращаем количество каналов")
    public int getCountChannels(){
        return spanNameChannel.size();
    }

    @Step(value = "Проверяем, отображается ли канал {channel}")
    public boolean isShowChannel(String channel, boolean show){
        if(show){
            try{
                spanNameChannel.findBy(Condition.text(channel)).shouldBe(Condition.visible);
            }catch (ElementNotFound e){
                return false;
            }
        } else {
            try{
                spanNameChannel.findBy(Condition.text(channel)).shouldBe(Condition.not(Condition.visible));
            }catch (ElementShould e){
                return false;
            }
        }
        return true;
    }

    @Step(value = "Делаем канал проверенным")
    private ChannelsPage clickButtonTestedChannel(String channel){
        $$("table span").findBy(Condition.text(channel)).closest("tr").find("button").click();
        //$x("//table//span[contains(text(),'" + channel + "')]//ancestor::tr//button").click();
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Продолжить'")
    private ChannelsPage clickButtonContinueAction(){
        buttonContinueAction.click();
        return this;
    }

    public ChannelsPage doTestedChannel(String name){
        spanNameChannel.findBy(Condition.text(name)).shouldBe(Condition.visible);
        clickButtonTestedChannel(name);
        clickButtonContinueAction();
        return this;
    }
}

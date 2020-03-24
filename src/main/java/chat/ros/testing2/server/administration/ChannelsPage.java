package chat.ros.testing2.server.administration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class ChannelsPage{

    private SelenideElement spanNameChannel = $("table.v-datatable.v-table.theme--light tbody td.name-td span");
    private SelenideElement buttonTestedChannel = $("table.v-datatable.v-table.theme--light tbody button");
    private SelenideElement buttonContinueAction = $("div.actions-wrapper button.primary--text");

    public ChannelsPage () {}

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
        spanNameChannel.shouldBe(Condition.text(name));
        clickButtonTestedChannel();
        clicckButtonContinueAction();
        return this;
    }
}

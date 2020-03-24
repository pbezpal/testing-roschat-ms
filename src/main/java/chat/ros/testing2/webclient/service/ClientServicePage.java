package chat.ros.testing2.webclient.service;

import chat.ros.testing2.webclient.ClientPage;
import com.codeborne.selenide.Condition;

public interface ClientServicePage extends ClientPage {

    @Override
    default Object getInstanceClient(String login, String password, String type) {
        getLoginClient(login, password).shouldBe(Condition.visible);
        switch(type){
            case "Рация":
                return new ClientRadioPage();
        }
        return null;
    }
}

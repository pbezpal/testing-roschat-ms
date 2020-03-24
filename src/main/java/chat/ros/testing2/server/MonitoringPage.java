package chat.ros.testing2.server;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class MonitoringPage extends LoginPage{

    private SelenideElement headingPage = $("div.v-toolbar__title.headline.layout.align-center h1");

    public MonitoringPage () {}

    @Step(value = "Проверяем, что находимся в разделе {heading}")
    public boolean isSectionPage(String heading){
        if(headingPage.text().equals(heading) ) return true;
        return false;
    }

    @Step(value = "Переходим в раздел {itemMenu}")
    public void clickItemMenu(String itemMenu){
            $(By.xpath("//div[@class='v-list__tile__title' and contains(text(),'" + itemMenu + "')]")).click();
    }
}
package chat.ros.testing2.monitoring;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public interface MonitoringPage extends BasePage {

    SelenideElement headingPage = $("div.v-toolbar__title.headline.layout.align-center h1");
    String locatorStatusService = "//*[@class='services']//span[text()='%1$s']//ancestor::li[@class='service']" +
            "//div[@class='%2$s']";
    SelenideElement sectionServices = $(".services");
    @Step(value = "Проверяем, что находимся в разделе {heading}")
    default boolean isSectionPage(String heading){
        if(headingPage.text().equals(heading) ) return true;
        return false;
    }

    @Step(value = "Получаем статус сервиса {service} в разделе Сервисы")
    static boolean isStatusService(String service, String classStatus){
        sectionServices.waitUntil(Condition.visible,30000).scrollIntoView(false);
        try{
            $x(String.format(locatorStatusService, service, classStatus)).waitUntil(Condition.visible, 60000);
        }catch (ElementNotFound e){
            return false;
        }

        return true;
    }
}

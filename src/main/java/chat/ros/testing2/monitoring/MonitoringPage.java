package chat.ros.testing2.monitoring;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public interface MonitoringPage extends BasePage {

    SelenideElement headingPage = $("div.v-toolbar__title.headline.layout.align-center h1");
    SelenideElement sectionServices = $(".services");

    @Step(value = "Проверяем, что находимся в разделе {heading}")
    default boolean isSectionPage(String heading){
        if(headingPage.text().equals(heading) ) return true;
        return false;
    }

    @Step(value = "Получаем статус сервиса {service} в разделе Сервисы")
    static boolean isStatusService(String service, String classStatus){
        sectionServices.shouldBe(Condition.visible, Duration.ofSeconds(30)).scrollIntoView(false);
        try{
            $$(".services span").findBy(Condition.text(service)).parent().find(classStatus)
                    .shouldBe(Condition.visible, Duration.ofMinutes(1));
        }catch (ElementNotFound e){
            return false;
        }

        return true;
    }
}

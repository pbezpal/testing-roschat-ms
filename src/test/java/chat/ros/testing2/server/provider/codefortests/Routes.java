package chat.ros.testing2.server.provider.codefortests;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.server.settings.TelephonyPage;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_PROVIDER_INCOMING_ROUTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Routes extends TelephonyPage implements IRoutes {

    public Routes() {}

    @Override
    public void closeModalWindowForAddRoute(String provider, String addButton, boolean simpleMode) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, addButton);
        selectCheckboxProvider(simpleMode);
        assertTrue(clickButtonClose().isShowElement(modalWindow, false),
                "Модальное окно для редактирования маршрута не заркылось после нажатия кнопки Закрыть");
    }

    @Override
    public void addRouteVerifyTitleModalWindow(String provider, String direction, Map<String, String> dataRoute, String addButton, boolean simpleMode) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, addButton);
        createRoute(direction, simpleMode, dataRoute);
        String titleModalWindow = getTitleOfModalWindow();
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertEquals(titleModalWindow,
                "Добавление маршрута",
                "Не найден заголовок Добавление маршрута в модальном окне при добавление маршрута");
        assertTrue(isExistsTableText(direction, true),
                "Не отображается значение " + direction +
                        " в столбце Направление в таблице маршрутов");
    }

    @Override
    public void addRoute(String provider, String direction, Map<String, String> dataRoute, String addButton, boolean simpleMode) {
        String patternNumber = null;
        String patternReplace = null;
        String groupReplace = null;
        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
                patternNumber = data.getValue().toString();
            else
            if(simpleMode)
                patternReplace = data.getValue().toString();
            else{
                if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE))
                    patternReplace = data.getValue().toString();
                else
                    groupReplace = data.getValue().toString();
            }
        }

        final String finalPatternNumber = patternNumber;
        String finalPatternReplace  = null;
        if(simpleMode)
            finalPatternReplace = patternReplace;
        else
            finalPatternReplace = patternReplace + groupReplace;

        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, addButton);
        createRoute(direction, simpleMode, dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertAll("\"1. Проверяем, правильно ли отображается заголовок модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что в столбце Шаблон номера отображается значение " + finalPatternNumber + "\n" +
                        "4. Проверяем, что в столбце Шаблон замены отображается значение " + patternReplace,
                () -> assertTrue(isExistsTableText(direction, true),
                        "Не отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> {
                    assertTrue(isExistsTableText(finalPatternNumber, true),
                            "Не отображается значение " + finalPatternNumber +
                                    " в столбце Шаблон номера в таблице маршрутов");
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.add(finalPatternNumber);
                    else
                        patternNumbersOutgoingRoute.add(finalPatternNumber);
                }
        );
        TestStatusResult.setTestResult(true);
        assertTrue(isExistsTableText(finalPatternReplace, true),
                "Не отображается Шаблон замены " + finalPatternReplace +
                        " в столбце Шаблон номера в таблице маршрутов");
        if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
            patternsReplaceIncomingRouteSimpleMode.add(finalPatternReplace);
        else
            patternsReplaceOutgoingRouteSimpleMode.add(finalPatternReplace);
    }

    @Override
    public void editRouteVerifyTitleModalWindow(String provider, String direction, boolean... simpleMode) {
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(simpleMode.length > 0)
            selectCheckboxProvider(simpleMode[0]);
        assertTrue(clickButtonClose().isShowElement(modalWindow, false),
                "Модальное окно для редактирования маршрута не заркылось после нажатия кнопки Закрыть");
    }

    @Override
    public void editRouteSimpleMode(String provider, String direction, Map<String, String> dataRoute) {
        String patternNumber = null;
        String patternReplace = null;
        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
                patternNumber = data.getValue().toString();
            else
                patternReplace = data.getValue().toString();
        }

        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(! getTitleOfModalWindow().equals("Редактирование маршрута"))
            Allure.step("Не найден заголовок Редактирование маршрута модального окна при редактирование маршрута",
                    Status.FAILED);
        final String finalPatternNumber = patternNumber;
        editRoute(dataRoute, true);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertAll("1. Проверяем, правильно ли отображается заголовок модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что в столбце Шаблон номера отображается значение " + finalPatternNumber + "\n" +
                        "4. Проверяем, что в столбце Шаблон замены отображается значение " + patternReplace,
                () -> assertTrue(isExistsTableText(direction, true),
                        "Не отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> {
                    assertTrue(isExistsTableText(finalPatternNumber, true),
                            "Не отображается значение " + finalPatternNumber +
                                    " в столбце Шаблон номера в таблице маршрутов");
                    if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.add(finalPatternNumber);
                    else
                        patternNumbersOutgoingRoute.add(finalPatternNumber);
                }
        );
        TestStatusResult.setTestResult(true);

        if(patternReplace.equals("")){
            patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                    "Отображается Шаблон замены " + replace +
                            " в столбце Шаблон номера в таблице маршрутов"));
        }else {
            assertTrue(isExistsTableText(patternReplace, true),
                    "Не отображается Шаблон замены " + patternReplace +
                            " в столбце Шаблон номера в таблице маршрутов");
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                patternsReplaceIncomingRouteSimpleMode.add(patternReplace);
            else
                patternsReplaceOutgoingRouteSimpleMode.add(patternReplace);
        }
    }

    @Override
    public void editRouteExpertMode(String provider, String direction, Map<String, String> dataRoute, boolean... expertMode) {
        String number = null;
        String replace = null;
        String groupReplace = null;

        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER))
                number = data.getValue().toString();
            else if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE))
                replace = data.getValue().toString();
            else
                groupReplace = data.getValue().toString();
        }

        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(! getTitleOfModalWindow().equals("Редактирование маршрута"))
            Allure.step("Не найден заголовок Редактирование маршрута модального окна при редактирование маршрута",
                    Status.FAILED);
        String patternReplace = replace + groupReplace;
        String finalNumber = number;
        if(expertMode.length > 0 && expertMode[0])
            editRoute(dataRoute, false);
        else
            editRoute(dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertAll("Проверяем, что корректно отображаютс данные в таблице маршрутов:\n" +
                        "1. В столбце Шаблон номера отображается значение " + finalNumber + "\n" +
                        "2. В столбце Шаблон замены отображается значение " + patternReplace,
                () -> assertTrue(isExistsTableText(direction, true),
                        "Не отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> {
                    assertTrue(isExistsTableText(finalNumber, true),
                            "Не отображается значение " + finalNumber +
                                    " в столбце Шаблон номера в таблице маршрутов");
                    if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.add(finalNumber);
                    else
                        patternNumbersOutgoingRoute.add(finalNumber);
                }
        );
        TestStatusResult.setTestResult(true);
        if(groupReplace.equals("") && ! replace.equals("")) {
            assertTrue(isExistsTableText(patternReplace, true),
                    "Не отображается значение " + patternReplace + " в столбце Шаблон замены в таблице маршрутов");
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) replacesIncomingRouteExpertMode.add(replace);
            else replacesOutgoingRouteExpertMode.add(replace);
        }else if(groupReplace.equals("") && replace.equals("")){
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                replacesIncomingRouteExpertMode.forEach((rep) -> assertTrue(isExistsTableText(rep, false),
                        "Отображается значение " + rep + " в столбце Шаблон замены в таблице маршрутов"));
                groupReplacesIncomingRouteExpertMode.forEach((gRep) -> assertTrue(isExistsTableText(gRep, false),
                        "Отображается значение " + gRep + " в столбце Шаблон замены в таблице маршрутов"));
                patternIncomingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                        "Отображается значение " + pattern + " в столбце Шаблон замены в таблице маршрутов"));
            }
            else {
                replacesOutgoingRouteExpertMode.forEach((rep) -> assertTrue(isExistsTableText(rep, false),
                        "Отображается значение " + rep + " в столбце Шаблон замены в таблице маршрутов"));
                groupReplacesOutgoingRouteExpertMode.forEach((gRep) -> assertTrue(isExistsTableText(gRep, false),
                        "Отображается значение " + gRep + " в столбце Шаблон замены в таблице маршрутов"));
                patternOutgoingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                        "Отображается значение " + pattern + " в столбце Шаблон замены в таблице маршрутов"));
            }
        }else{
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                replacesIncomingRouteExpertMode.add(replace);
                groupReplacesIncomingRouteExpertMode.add(groupReplace);
                patternIncomingRouteExpertMode.add(patternReplace);
            }else{
                replacesOutgoingRouteExpertMode.add(replace);
                groupReplacesOutgoingRouteExpertMode.add(groupReplace);
                patternOutgoingRouteExpertMode.add(patternReplace);
            }
        }
    }

    @Override
    public void deleteRoute(String provider, String direction) {
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "delete");
        clickButtonConfirmAction("Продолжить");
        assertAll("Проверяем, что после удаления входящего маршрута:\n" +
                        "1. Не отображается в таблице маршрутов в столбце Направление значение Входящий\n" +
                        "2. Не отображается в таблице маршрутов Шаблон номера\n" +
                        "3. Не отображается в таблице маршрутов Шаблон замены",
                () -> assertTrue(isExistsTableText(direction, false),
                        "Отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов после удаления маршрута"),
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.forEach((number) -> assertTrue(isExistsTableText(number, false),
                                "Отображается значение " + number + " с столбце Шаблон номера в таблице " +
                                        "маршрутов после удаления маршрута"));
                    else
                        patternNumbersOutgoingRoute.forEach((number) -> assertTrue(isExistsTableText(number, false),
                                "Отображается значение " + number + " с столбце Шаблон номера в таблице " +
                                        "маршрутов после удаления маршрута"));
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                        if (!patternsReplaceIncomingRouteSimpleMode.isEmpty())
                            patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                    "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }else {
                        if (!patternsReplaceOutgoingRouteSimpleMode.isEmpty())
                            patternsReplaceOutgoingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                    "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }
                },
                () -> {
                    if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                        if (!replacesIncomingRouteExpertMode.isEmpty())
                            replacesIncomingRouteExpertMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                    "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }else {
                        replacesOutgoingRouteExpertMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                    }
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                        if(!groupReplacesIncomingRouteExpertMode.isEmpty())
                            groupReplacesIncomingRouteExpertMode.forEach((gReplace) -> assertTrue(isExistsTableText(gReplace, false),
                                    "Отображается значение " + gReplace + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }else {
                        if(!groupReplacesOutgoingRouteExpertMode.isEmpty())
                            groupReplacesOutgoingRouteExpertMode.forEach((gReplace) -> assertTrue(isExistsTableText(gReplace, false),
                                    "Отображается значение " + gReplace + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                        if(!patternIncomingRouteExpertMode.isEmpty())
                            patternIncomingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                                    "Отображается значение " + pattern + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }else {
                        if(!patternOutgoingRouteExpertMode.isEmpty())
                            patternOutgoingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                                    "Отображается значение " + pattern + " с столбце Шаблон замены в таблице " +
                                            "маршрутов после удаления маршрута"));
                    }
                }
        );
    }
}

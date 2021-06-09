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

    private String patternNumber = null;
    private String patternReplace = null;
    private String groupReplace = null;
    private String startMessageError = null;

    private String verifyAddRoute(String direction, Map<String, String> dataRoute, boolean simpleMode, boolean showTable){
        patternNumber = null;
        patternReplace = null;
        groupReplace = null;
        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER)
                    || data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER))
                patternNumber = data.getValue().toString();
            else
                if(simpleMode)
                    patternReplace = data.getValue().toString();
                else{
                    if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE))
                        patternReplace = data.getValue().toString();
                    else
                        groupReplace = data.getValue().toString();
                }
        }

        String finalPatternReplace = null;

        if(showTable) startMessageError = "Не отображается значение ";
        else startMessageError = "Отображается значение ";

        if(simpleMode)
            finalPatternReplace = patternReplace;
        else
            finalPatternReplace = patternReplace + groupReplace;

        assertAll("1. Проверяем, правильно ли отображается заголовок модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что в столбце Шаблон номера отображается значение " + patternNumber + "\n" +
                        "4. Проверяем, что в столбце Шаблон замены отображается значение " + finalPatternReplace,
                () -> assertTrue(isExistsTableText(direction, showTable),
                        startMessageError + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> {
                    if(patternNumber != null)
                        assertTrue(isExistsTableText(patternNumber, showTable),
                                startMessageError + patternNumber +
                                        " в столбце Шаблон номера в таблице маршрутов");
                }
        );

        return finalPatternReplace;
    }

    private String verifyEditRoute(String direction, boolean verifyDirection, Map<String, String> dataRoute, boolean simpleMode, boolean showTable){
        patternNumber = null;
        patternReplace = null;
        groupReplace = null;
        String finalPatternReplace = null;

        if(simpleMode){
            for(Map.Entry data: dataRoute.entrySet()){
                if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
                    patternNumber = data.getValue().toString();
                else
                    patternReplace = data.getValue().toString();
            }

            finalPatternReplace = patternReplace;

            if(showTable) startMessageError = "Не отображается значение ";
            else startMessageError = "Отображается значение ";

            assertAll("1. Проверяем, правильно ли отображается заголовок модального окна\n" +
                            "2. Заполняем поля модального окна и сохраняем настройки\n" +
                            "3. Проверяем, что в столбце Шаблон номера отображается значение " + patternNumber + "\n" +
                            "4. Проверяем, что в столбце Шаблон замены отображается значение " + patternReplace,
                    () ->{
                        if(verifyDirection)
                            assertTrue(isExistsTableText(direction, showTable),
                                    startMessageError + direction +
                                            " в столбце Направление в таблице маршрутов");
                    },
                    () -> {
                        if (patternNumber != null) {
                            assertTrue(isExistsTableText(patternNumber, showTable),
                                    startMessageError + patternNumber +
                                            " в столбце Шаблон номера в таблице маршрутов");
                            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                                patternNumbersIncomingRoute.add(patternNumber);
                            else
                                patternNumbersOutgoingRoute.add(patternNumber);
                        }
                    }
            );
        }else{
            for(Map.Entry data: dataRoute.entrySet()){
                if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER))
                    patternNumber = data.getValue().toString();
                else if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE))
                    patternReplace = data.getValue().toString();
                else
                    groupReplace = data.getValue().toString();
            }

            finalPatternReplace = patternReplace + groupReplace;

            assertAll("Проверяем, что корректно отображаютс данные в таблице маршрутов:\n" +
                            "1. В столбце Шаблон номера отображается значение " + patternNumber + "\n" +
                            "2. В столбце Шаблон замены отображается значение " + patternReplace,
                    () -> {
                        if(showTable)
                            assertTrue(isExistsTableText(direction, showTable),
                                    startMessageError + direction +
                                            " в столбце Направление в таблице маршрутов");
                    },
                    () -> {
                        if(patternNumber != null) {
                            assertTrue(isExistsTableText(patternNumber, showTable),
                                    startMessageError + patternNumber +
                                            " в столбце Шаблон номера в таблице маршрутов");
                            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                                patternNumbersIncomingRoute.add(patternNumber);
                            else
                                patternNumbersOutgoingRoute.add(patternNumber);
                        }
                    }
            );
        }

        return finalPatternReplace;
    }

    @Override
    public void closeModalWindowForAddRoute(String provider, String direction, Map<String, String> dataRoute, String button, boolean simpleMode) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, button);
        createRoute(direction, simpleMode, dataRoute);
        assertTrue(clickButtonClose().isShowElement(modalWindow, false),
                "Модальное окно для добавления маршрута не заркылось после нажатия кнопки Закрыть");
        String patternReplace = verifyAddRoute(direction, dataRoute, simpleMode, false);
        if(patternReplace != null)
            assertTrue(isExistsTableText(patternReplace, false),
                    "Отображается Шаблон замены " + patternReplace +
                            " в столбце Шаблон замены в таблице маршрутов");
    }

    @Override
    public void closeModalWindowForEditRoute(String provider, String direction, Map<String, String> dataRoute, boolean... simpleMode) {
        String patternReplace = null;
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(simpleMode.length > 0) {
            editRoute(dataRoute, simpleMode[0]);
            assertTrue(clickButtonClose().isShowElement(modalWindow, false),
                    "Модальное окно для редактирования маршрута не заркылось после нажатия кнопки Закрыть");
            if (simpleMode[0])
                patternReplace = verifyEditRoute(direction, false, dataRoute, true, false);
            else
                patternReplace = verifyEditRoute(direction, false, dataRoute, false, false);
        }else {
            editRoute(dataRoute);
            assertTrue(clickButtonClose().isShowElement(modalWindow, false),
                    "Модальное окно для редактирования маршрута не заркылось после нажатия кнопки Закрыть");
            patternReplace = verifyEditRoute(direction, false, dataRoute, false, false);
        }
        if(patternReplace != null)
            assertTrue(isExistsTableText(patternReplace, false),
                    "Отображается Шаблон замены " + patternReplace +
                            " в столбце Шаблон замены в таблице маршрутов");
    }

    @Override
    public void addRoute(String provider, String direction, Map<String, String> dataRoute, String addButton, boolean simpleMode) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, addButton);
        if( ! getTitleOfModalWindow().equals("Добавление маршрута"))
            Allure.step("Не найден заголовок модального окна Добавление маршрута", Status.FAILED);
        createRoute(direction, simpleMode, dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        String patternReplace = verifyAddRoute(direction, dataRoute, simpleMode, true);
        if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
            patternNumbersIncomingRoute.add(patternNumber);
        else
            patternNumbersOutgoingRoute.add(patternNumber);
        TestStatusResult.setTestResult(true);
        if(patternReplace != null) {
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
    public void editRouteSimpleMode(String provider, String direction, Map<String, String> dataRoute) {
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(! getTitleOfModalWindow().equals("Редактирование маршрута"))
            Allure.step("Не найден заголовок Редактирование маршрута модального окна при редактирование маршрута",
                    Status.FAILED);
        editRoute(dataRoute, true);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        String patternReplace = verifyEditRoute(direction, true, dataRoute, true, true);
        TestStatusResult.setTestResult(true);

        if(patternReplace != null) {
            if (patternReplace.equals("")) {
                patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                        "Отображается Шаблон замены " + replace +
                                " в столбце Шаблон замены в таблице маршрутов"));
            } else {
                assertTrue(isExistsTableText(patternReplace, true),
                        "Не отображается Шаблон замены " + patternReplace +
                                " в столбце Шаблон замены в таблице маршрутов");
                if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                    patternsReplaceIncomingRouteSimpleMode.add(patternReplace);
                else
                    patternsReplaceOutgoingRouteSimpleMode.add(patternReplace);
            }
        }
    }

    @Override
    public void editRouteExpertMode(String provider, String direction, Map<String, String> dataRoute, boolean... expertMode) {
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(! getTitleOfModalWindow().equals("Редактирование маршрута"))
            Allure.step("Не найден заголовок Редактирование маршрута модального окна при редактирование маршрута",
                    Status.FAILED);
        if(expertMode.length > 0 && expertMode[0])
            editRoute(dataRoute, false);
        else
            editRoute(dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        String groupPatternReplace = verifyEditRoute(direction, true, dataRoute, false, true);
        TestStatusResult.setTestResult(true);
        if(groupReplace.equals("") && ! patternReplace.equals("")) {
            assertTrue(isExistsTableText(groupPatternReplace, true),
                    "Не отображается значение " + groupPatternReplace + " в столбце Шаблон замены в таблице маршрутов");
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) replacesIncomingRouteExpertMode.add(patternReplace);
            else replacesOutgoingRouteExpertMode.add(patternReplace);
        }else if(groupReplace.equals("") && patternReplace.equals("")){
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
                replacesIncomingRouteExpertMode.add(patternReplace);
                groupReplacesIncomingRouteExpertMode.add(groupReplace);
                patternIncomingRouteExpertMode.add(groupPatternReplace);
            }else{
                replacesOutgoingRouteExpertMode.add(patternReplace);
                groupReplacesOutgoingRouteExpertMode.add(groupReplace);
                patternOutgoingRouteExpertMode.add(groupPatternReplace);
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

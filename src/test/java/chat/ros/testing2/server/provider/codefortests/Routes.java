package chat.ros.testing2.server.provider.codefortests;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.server.settings.TelephonyPage;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_PROVIDER_INCOMING_ROUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Routes extends TelephonyPage implements IRoutes {

    public Routes() {}

    private String patternNumber = null;
    private String patternReplace = null;
    private String groupReplace = null;
    private String startMessageError = null;

    private String checkAddRoute(String direction, Map<String, String> dataRoute, boolean simpleMode, boolean showTable){
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

        isExistsTableText(direction, showTable);
        if(patternNumber != null)
           isExistsTableText(patternNumber, showTable);

        return finalPatternReplace;
    }

    private String checkEditRoute(String direction, boolean verifyDirection, Map<String, String> dataRoute, boolean simpleMode, boolean showTable){
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

            if(verifyDirection)
                isExistsTableText(direction, showTable);

            if (patternNumber != null) {
                isExistsTableText(patternNumber, showTable);
                if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                    patternNumbersIncomingRoute.add(patternNumber);
                else
                    patternNumbersOutgoingRoute.add(patternNumber);
            }
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
        }

        return finalPatternReplace;
    }

    @Override
    public void closeModalWindowForAddRoute(String provider, String direction, Map<String, String> dataRoute, String button, boolean simpleMode) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, button);
        createRoute(direction, simpleMode, dataRoute);
        clickButtonClose()
                .isShowElement(modalWindow, false);
        String patternReplace = checkAddRoute(direction, dataRoute, simpleMode, false);
        if(patternReplace != null)
            isExistsTableText(patternReplace, false);
    }

    @Override
    public void closeModalWindowForEditRoute(String provider, String direction, Map<String, String> dataRoute, boolean... simpleMode) {
        String patternReplace = null;
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        if(simpleMode.length > 0) {
            editRoute(dataRoute, simpleMode[0]);
            clickButtonClose()
                    .isShowElement(modalWindow, false);
            if (simpleMode[0])
                patternReplace = checkEditRoute(direction, false, dataRoute, true, false);
            else
                patternReplace = checkEditRoute(direction, false, dataRoute, false, false);
        }else {
            editRoute(dataRoute);
            clickButtonClose()
                    .isShowElement(modalWindow, false);
            patternReplace = checkEditRoute(direction, false, dataRoute, false, false);
        }
        if(patternReplace != null)
            isExistsTableText(patternReplace, false);
    }

    @Override
    public void checkHeaderModalWindowWhenAddRoute(String provider, String button) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, button);
        isModalWindow(true);
        assertEquals(getTitleOfModalWindow(),
                "Добавление маршрута",
                "Не найден заголовок модального окна Добавление маршрута");
        clickButtonClose().isModalWindow(false);
    }

    @Override
    public void addRoute(String provider, String direction, Map<String, String> dataRoute, String addButton, boolean simpleMode) {
        clickButtonTableProvider(provider, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, addButton);
        createRoute(direction, simpleMode, dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        String patternReplace = checkAddRoute(direction, dataRoute, simpleMode, true);
        if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
            patternNumbersIncomingRoute.add(patternNumber);
        else
            patternNumbersOutgoingRoute.add(patternNumber);
        TestStatusResult.setTestResult(true);
        if(patternReplace != null) {
            isExistsTableText(patternReplace, true);
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                patternsReplaceIncomingRouteSimpleMode.add(patternReplace);
            else
                patternsReplaceOutgoingRouteSimpleMode.add(patternReplace);
        }
    }

    @Override
    public void checkHeaderModalWindowWhenEditRoute(String provider, String route) {
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(route, "edit");
        assertEquals(getTitleOfModalWindow()
                , "Редактирование маршрута"
                , "Не найден заголовок Редактирование маршрута модального окна при редактирование маршрута");
    }

    @Override
    public void editRouteSimpleMode(String provider, String direction, Map<String, String> dataRoute) {
        clickButtonTableProvider(provider, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        editRoute(dataRoute, true);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        String patternReplace = checkEditRoute(direction, true, dataRoute, true, true);
        TestStatusResult.setTestResult(true);

        if(patternReplace != null) {
            if (patternReplace.equals("")) {
                patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> isExistsTableText(replace, false));
            } else {
                isExistsTableText(patternReplace, true);
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
        if(expertMode.length > 0 && expertMode[0])
            editRoute(dataRoute, false);
        else
            editRoute(dataRoute);
        clickButtonSave();
        String groupPatternReplace = checkEditRoute(direction, true, dataRoute, false, false);
        System.out.println("Pattern replace: " + patternReplace);
        System.out.println("Group replace: " + groupReplace);
        if(groupPatternReplace.equals("")){
            assertEquals(isShowTextWrongValue(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE)
                    , "Значение неверно",
                    "Не появилась надпись Значение неверно при вводе пустого значения в поле " + TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE);
            assertEquals(isShowTextWrongValue(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE)
                    , "Значение неверно",
                    "Не появилась надпись Значение неверно при вводе пустого значения в поле " + TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE);
            clickButtonClose().isModalWindow(false);
            TestStatusResult.setTestResult(true);
        }else if(groupReplace.equals("")){
            assertEquals(isShowTextWrongValue(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE)
                    , "Значение неверно",
                    "Не появилась надпись Значение неверно при вводе пустого значения в поле " + TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE);
            clickButtonClose().isModalWindow(false);
            TestStatusResult.setTestResult(true);
        }else if(patternReplace.equals("")){
            assertEquals(isShowTextWrongValue(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE)
                    , "Значение неверно",
                    "Не появилась надпись Значение неверно при вводе пустого значения в поле " + TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE);
            clickButtonClose().isModalWindow(false);
            TestStatusResult.setTestResult(true);
        }else {
            isModalWindow(true);
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
            isExistsTableText(direction, true)
                    .isExistsTableText(patternNumber, true);
            TestStatusResult.setTestResult(true);
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                patternNumbersIncomingRoute.add(patternNumber);
                replacesIncomingRouteExpertMode.add(patternReplace);
                groupReplacesIncomingRouteExpertMode.add(groupReplace);
                patternIncomingRouteExpertMode.add(groupPatternReplace);
            } else {
                patternNumbersOutgoingRoute.add(patternNumber);
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
        isExistsTableText(direction, false);
        if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
            patternNumbersIncomingRoute.forEach((number) -> isExistsTableText(number, false));
            if (!patternsReplaceIncomingRouteSimpleMode.isEmpty())
                patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> isExistsTableText(replace, false));
            if(!groupReplacesIncomingRouteExpertMode.isEmpty())
                groupReplacesIncomingRouteExpertMode.forEach((gReplace) -> isExistsTableText(gReplace, false));
            if(!patternIncomingRouteExpertMode.isEmpty())
                patternIncomingRouteExpertMode.forEach((pattern) -> isExistsTableText(pattern, false));
        }else {
            patternNumbersOutgoingRoute.forEach((number) -> isExistsTableText(number, false));
            if (!patternsReplaceOutgoingRouteSimpleMode.isEmpty())
                patternsReplaceOutgoingRouteSimpleMode.forEach((replace) -> isExistsTableText(replace, false));
            if(!groupReplacesOutgoingRouteExpertMode.isEmpty())
                groupReplacesOutgoingRouteExpertMode.forEach((gReplace) -> isExistsTableText(gReplace, false));
            if(!patternOutgoingRouteExpertMode.isEmpty())
                patternOutgoingRouteExpertMode.forEach((pattern) -> isExistsTableText(pattern, false));
        }
    }
}

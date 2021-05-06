package com.ustudents.farmland.scene.menus;

import com.ustudents.engine.Game;
import com.ustudents.engine.GameConfig;
import com.ustudents.engine.core.Resources;
import com.ustudents.engine.core.cli.print.Out;
import com.ustudents.engine.core.event.EventListener;
import com.ustudents.engine.graphic.Anchor;
import com.ustudents.engine.graphic.Color;
import com.ustudents.engine.graphic.Origin;
import com.ustudents.engine.gui.GuiBuilder;
import com.ustudents.engine.input.Action;
import com.ustudents.engine.input.Input;
import com.ustudents.engine.input.Key;
import com.ustudents.farmland.Farmland;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.CallbackI;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class CommandsMenu extends MenuScene{
    int bindError;

    public CommandsMenu(){
        bindError = -1;
    }

    public CommandsMenu(int errorBind){
        this.bindError = errorBind;
    }

    @Override
    public void initialize() {
        String[] buttonNames = new String[0];
        String[] buttonIds = new String[0];
        EventListener[] eventListeners = new EventListener[buttonNames.length];
        GuiBuilder guiBuilder = new GuiBuilder();


        initializeTitle(guiBuilder);
        showTextAndButton(guiBuilder, "goUp");
        showTextAndButton(guiBuilder, "goDown");
        showTextAndButton(guiBuilder, "goLeft");
        showTextAndButton(guiBuilder, "goRight");
        showTextAndButton(guiBuilder, "showTerritory");
        showTextAndButton(guiBuilder, "putItem");
        showTextAndButton(guiBuilder, "getItem");
        showTextAndButton(guiBuilder, "debugMenu");
        showTextAndButton(guiBuilder, "showPerfomance");
        if(bindError != -1)
            displayBindError(guiBuilder);
        canGoBackButton(guiBuilder);
        reloadBindButton(guiBuilder);

        initializeMenu(buttonNames, buttonIds, eventListeners, false, false, false, false);

        super.initialize();
    }

    private void initializeTitle(GuiBuilder guiBuilder){
        GuiBuilder.TextData commandsTitle = new GuiBuilder.TextData("Commands : ");
        commandsTitle.id = "commandsTitle";
        commandsTitle.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
        commandsTitle.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
        commandsTitle.position = new Vector2f(0, 15);
        commandsTitle.color = Color.BLACK;
        guiBuilder.addText(commandsTitle);

        GuiBuilder.TextData moveOptions = new GuiBuilder.TextData("Déplacements : ");
        moveOptions.id = "moveSection";
        moveOptions.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
        moveOptions.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
        moveOptions.position = new Vector2f(70, 120);
        moveOptions.color = Color.BLACK;
        guiBuilder.addText(moveOptions);

        GuiBuilder.TextData gameplayOptions = new GuiBuilder.TextData("Gameplay : ");
        gameplayOptions.id = "gameplayOptions";
        gameplayOptions.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
        gameplayOptions.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
        gameplayOptions.position = new Vector2f(0, 115);
        gameplayOptions.color = Color.BLACK;
        guiBuilder.addText(gameplayOptions);

        GuiBuilder.TextData OtherOptions = new GuiBuilder.TextData("Divers : ");
        OtherOptions.id = "moveSection";
        OtherOptions.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Right);
        OtherOptions.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Right);
        OtherOptions.position = new Vector2f(-150, 115);
        OtherOptions.color = Color.BLACK;
        guiBuilder.addText(OtherOptions);


    }

    private void showTextAndButton(GuiBuilder guiBuilder, String action){
        addTextNearButtons(guiBuilder, action);
        addButtonNearTheText(guiBuilder, action);
    }

    private void addTextNearButtons(GuiBuilder guiBuilder, String action){
        if(action.equals("goUp")){
            GuiBuilder.TextData goUp = new GuiBuilder.TextData(Resources.getLocalizedText("goUp"));
            goUp.id = action;
            goUp.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goUp.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goUp.position = new Vector2f(90, 225); // y = 75 , y = 150
            goUp.color = Color.BLACK;
            guiBuilder.addText(goUp);
        }

        if(action.equals("goDown")){
            GuiBuilder.TextData goDown = new GuiBuilder.TextData(Resources.getLocalizedText("goDown"));
            goDown.id = action;
            goDown.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goDown.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goDown.position = new Vector2f(100, 300); // y = 150 , y = 225
            goDown.color = Color.BLACK;
            guiBuilder.addText(goDown);
        }

        if(action.equals("goLeft")){
            GuiBuilder.TextData goLeft = new GuiBuilder.TextData(Resources.getLocalizedText("goLeft"));
            goLeft.id = action;
            goLeft.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goLeft.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goLeft.position = new Vector2f(10, 365); // y = 300
            goLeft.color = Color.BLACK;
            guiBuilder.addText(goLeft);
        }

        if(action.equals("goRight")){
            GuiBuilder.TextData goRight = new GuiBuilder.TextData(Resources.getLocalizedText("goRight"));
            goRight.id = action;
            goRight.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goRight.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goRight.position = new Vector2f(10, 430); // y = 400
            goRight.color = Color.BLACK;
            guiBuilder.addText(goRight);
        }

        if(action.equals("showTerritory")){
            GuiBuilder.TextData showTerritory = new GuiBuilder.TextData(Resources.getLocalizedText("showTerritory"));
            showTerritory.id = action;
            showTerritory.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
            showTerritory.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
            showTerritory.position = new Vector2f(-150, 225);
            showTerritory.color = Color.BLACK;
            guiBuilder.addText(showTerritory);
        }

        if(action.equals("putItem")){
            GuiBuilder.TextData putItem = new GuiBuilder.TextData(Resources.getLocalizedText("putItem"));
            putItem.id = action;
            putItem.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
            putItem.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
            putItem.position = new Vector2f(-150, 300);
            putItem.color = Color.BLACK;
            guiBuilder.addText(putItem);
        }

        if(action.equals("getItem")){
            GuiBuilder.TextData getItem = new GuiBuilder.TextData(Resources.getLocalizedText("getItem"));
            getItem.id = action;
            getItem.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
            getItem.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
            getItem.position = new Vector2f(-150, 365);
            getItem.color = Color.BLACK;
            guiBuilder.addText(getItem);
        }

        if(action.equals("debugMenu")){
            GuiBuilder.TextData debugMenu = new GuiBuilder.TextData(Resources.getLocalizedText("debugMenu"));
            debugMenu.id = action;
            debugMenu.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Right);
            debugMenu.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Right);
            debugMenu.position = new Vector2f(-220, 225); // y = 400
            debugMenu.color = Color.BLACK;
            guiBuilder.addText(debugMenu);
        }

        if(action.equals("showPerfomance")){
            GuiBuilder.TextData showPerfomance = new GuiBuilder.TextData(Resources.getLocalizedText("showPerformance"));
            showPerfomance.id = action;
            showPerfomance.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Right);
            showPerfomance.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Right);
            showPerfomance.position = new Vector2f(-210, 300);
            showPerfomance.color = Color.BLACK;
            guiBuilder.addText(showPerfomance);
        }
    }

    private void addButtonNearTheText(GuiBuilder guiBuilder, String action){
        int keyBind = Resources.getConfig().commands.get(action).getFirstBindInMapping();
        String typeOfBind = Resources.getConfig().commands.get(action).getFirstTypeOfBindInMapping();
        String key;
        if(typeOfBind.equals("keyboard")){
            key = displayGoodKeyBind(keyBind);
            if(keyBind <= 90 && keyBind > 0 && key != null)
                key = key.toUpperCase(Locale.ROOT);
        }else{
            key = displayGoodMouseButtonBind(keyBind);
        }
        if(action.equals("goUp")){
            GuiBuilder.ButtonData goUp = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            goUp.id = action;
            goUp.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goUp.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goUp.position = new Vector2f(250, 210); //y = 65, y = 130
            guiBuilder.addButton(goUp);
        }

        if(action.equals("goDown")){
            GuiBuilder.ButtonData goDown = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            goDown.id = action;
            goDown.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goDown.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goDown.position = new Vector2f(250, 285); // y = 130 , y = 195
            guiBuilder.addButton(goDown);
        }

        if(action.equals("goLeft")){
            GuiBuilder.ButtonData goLeft = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            goLeft.id = action;
            goLeft.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goLeft.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goLeft.position = new Vector2f(250, 350); // y = 260
            guiBuilder.addButton(goLeft);
        }

        if(action.equals("goRight")){
            GuiBuilder.ButtonData goRight = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            goRight.id = action;
            goRight.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Left);
            goRight.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Left);
            goRight.position = new Vector2f(250, 425); // y = 425
            guiBuilder.addButton(goRight);
        }

        if(action.equals("showTerritory")){
            GuiBuilder.ButtonData showTerritory = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            showTerritory.id = action;
            showTerritory.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
            showTerritory.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
            showTerritory.position = new Vector2f(100, 210); // y = 425
            guiBuilder.addButton(showTerritory);
        }

        if(action.equals("putItem")){
            GuiBuilder.ButtonData putItem = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            putItem.id = action;
            putItem.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
            putItem.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
            putItem.position = new Vector2f(100, 285); // y = 425
            guiBuilder.addButton(putItem);
        }

        if(action.equals("getItem")){
            GuiBuilder.ButtonData getItem = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            getItem.id = action;
            getItem.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Center);
            getItem.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Center);
            getItem.position = new Vector2f(100, 350); // y = 425
            guiBuilder.addButton(getItem);
        }

        if(action.equals("debugMenu")){
            GuiBuilder.ButtonData debugMenu = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            debugMenu.id = action;
            debugMenu.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Right);
            debugMenu.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Right);
            debugMenu.position = new Vector2f(-90, 210); // y = 210
            guiBuilder.addButton(debugMenu);
        }

        if(action.equals("showPerfomance")){
            GuiBuilder.ButtonData showPerfomance = new GuiBuilder.ButtonData(key, (dataType, data) -> {
                removeBind(action);
            });
            showPerfomance.id = action;
            showPerfomance.origin = new Origin(Origin.Vertical.Top, Origin.Horizontal.Right);
            showPerfomance.anchor = new Anchor(Anchor.Vertical.Top, Anchor.Horizontal.Right);
            showPerfomance.position = new Vector2f(-90, 285); // y = 210
            guiBuilder.addButton(showPerfomance);
        }
    }

    private String displayGoodKeyBind(int key){
        if(key >= 290 && key <= 302){
            return "F" + ((key%10) + 1);
        }else if(key >= 32 && key < 96 || key >= 320 && key <= 329) {
            return GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
        }else if(key == 341 || key == 345) {
            if(key == 341)
                return Resources.getLocalizedText("LCtrl");
            else
                return Resources.getLocalizedText("RCtrl");
        }else if(key == 342 || key == 346) {
            if (key == 342)
                return Resources.getLocalizedText("LAlt");
            else
                return Resources.getLocalizedText("RAlt");
        }else if(key == 96){
            return Resources.getLocalizedText("GraveAccent");
        }else{
            return "[...]";
        }
    }

    private String displayGoodMouseButtonBind(int mouseButton){
        if(mouseButton == 0)
            return Resources.getLocalizedText("LMB");
        else if (mouseButton == 1)
            return Resources.getLocalizedText("RMB");
        else if (mouseButton > 1 && mouseButton <= 7)
            return Resources.getLocalizedText("Button") + (mouseButton + 1);
        else
            return "[...]";
    }

    private void displayBindError(GuiBuilder guiBuilder){
        GuiBuilder.TextData bindError = new GuiBuilder.TextData(Resources.getLocalizedText("bindError" + this.bindError));
        bindError.id = "bindError";
        bindError.origin = new Origin(Origin.Vertical.Bottom, Origin.Horizontal.Center);
        bindError.anchor = new Anchor(Anchor.Vertical.Bottom, Anchor.Horizontal.Center);
        bindError.position = new Vector2f(0, -200);
        bindError.color = Color.BLACK;
        guiBuilder.addText(bindError);
    }

    private void canGoBackButton(GuiBuilder guiBuilder){
        GuiBuilder.ButtonData goBack = new GuiBuilder.ButtonData("Retour", (dataType, data) -> {
            Farmland.get().reloadCustomizableCommands();
            changeScene(new SettingsMenu());
        });
        goBack.id = "goBack";
        goBack.origin = new Origin(Origin.Vertical.Bottom, Origin.Horizontal.Center);
        goBack.anchor = new Anchor(Anchor.Vertical.Bottom, Anchor.Horizontal.Center);
        goBack.position = new Vector2f(-150, -75); // x = 0
        guiBuilder.addButton(goBack);
    }

    private void reloadBindButton(GuiBuilder guiBuilder){
        GuiBuilder.ButtonData reloadBind = new GuiBuilder.ButtonData("Réinitialiser les touches", (dataType, data) -> {
            Resources.getConfig().commands.clear();
            Farmland.get().initializeCommands(Resources.getConfig());
            changeScene(new CommandsMenu());
        });
        reloadBind.id = "reloadBind";
        reloadBind.origin = new Origin(Origin.Vertical.Bottom, Origin.Horizontal.Center);
        reloadBind.anchor = new Anchor(Anchor.Vertical.Bottom, Anchor.Horizontal.Center);
        reloadBind.position = new Vector2f(150, -75);
        guiBuilder.addButton(reloadBind);
    }

    private void removeBind(String action){
        if(searchAction() == null)
            Resources.getConfig().commands.get(action).removeFirstBindInMapping();
        changeScene(new CommandsMenu());
    }

    public void selectNewBind(boolean isKey, int selectedBind){
        String actionName = searchAction();
        if(actionName == null || isKey != isKeyAction(actionName)) return;
        String typeOfBind = typeOfBind(actionName);
        if (typeOfBind == null) return;
        Action currentAction = Resources.getConfig().commands.get(actionName);
        Out.println(selectedBind);
        if(isKey){
            if(selectedBind <= 0 || !bindNotAlreadyDefine(selectedBind, true) || avoidKey(selectedBind)) {
                changeScene(new CommandsMenu(2));
                return;
            }
        }else{
            if(selectedBind < 0 || !bindNotAlreadyDefine(selectedBind, false)) {
                changeScene(new CommandsMenu());
                return;
            }
        }
        Out.println(selectedBind);
        currentAction.removeFirstBindInMapping();
        currentAction.addFirstBindInMapping(selectedBind, typeOfBind);
        changeScene(new CommandsMenu());
    }

    public boolean bindNotAlreadyDefine(int key, boolean isKey){
        Map<String, Action> commands = Resources.getConfig().commands;
        for(String actionName: commands.keySet()){
            if(isKey == isKeyAction(actionName) && !commands.get(actionName).bindNotAlreadyDefine(key)){
                return false;
            }
        }
        return true;
    }

    public String searchAction(){
        Map<String, Action> commands = Resources.getConfig().commands;
        for(String actionName: commands.keySet()){
            if(commands.get(actionName).firstBindInMappingIsRemoved()){
                return actionName;
            }
        }
        return null;
    }

    private String typeOfBind(String actionName){
        if(actionName.equals("goUp") || actionName.equals("goDown") |
        actionName.equals("goLeft") || actionName.equals("goRight") ||
        actionName.equals("showTerritory") || actionName.equals("putItem") ||
        actionName.equals("getItem"))
            return "down";
        else if(actionName.equals("debugMenu") || actionName.equals("showPerfomance")){
            return "pressed";
        }
        return null;
    }

    private boolean isKeyAction(String action){
        return !(action.equals("putItem") || action.equals("getItem"));
    }

    public boolean avoidKey(int key){
        return displayGoodKeyBind(key).equals("[...]");
    }
}

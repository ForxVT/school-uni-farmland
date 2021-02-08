package com.ustudents.farmland.game.scene;

import com.ustudents.farmland.Farmland;
import com.ustudents.farmland.core.Scene;
import com.ustudents.farmland.graphics.tools.ImGuiUtils;
import imgui.ImGui;
import imgui.flag.ImGuiCond;

public class MultiPlayerMenu extends Scene {
    @Override
    public void initialize() {

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render() {
        ImGuiUtils.setNextWindowWithSizeCentered(300, 300, ImGuiCond.Appearing);
        ImGui.begin("Multiplayer Menu");

        if (ImGui.button("Main Menu")) {
            Farmland.get().getSceneManager().changeScene(MainMenu.class);
        }
        if (ImGui.button("Waiting Room")) {
            Farmland.get().getSceneManager().changeScene(WaitingRoom.class);
        }

        ImGui.end();
    }

    @Override
    public void destroy() {

    }
}

package com.ustudents.farmland.graphics.tools;

import com.ustudents.farmland.Farmland;
import com.ustudents.farmland.cli.option.Runnable;
import com.ustudents.farmland.cli.option.annotation.Option;
import com.ustudents.farmland.core.SceneManager;
import com.ustudents.farmland.ecs.Component;
import com.ustudents.farmland.ecs.Entity;
import com.ustudents.farmland.graphics.tools.annotation.Editable;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.*;
import org.joml.Vector2f;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Debugger {
    private final ImBoolean showDemoWindow = new ImBoolean(false);
    private final ImBoolean showSceneHierarchyWindow = new ImBoolean(true);
    private final ImBoolean showInspectorWindow = new ImBoolean(true);
    private final ImBoolean showSettingsWindow = new ImBoolean(false);
    private final ImBoolean showPerformanceCounter = new ImBoolean(false);
    private static boolean vsyncCurrentState;
    private static ImBoolean useVsync;
    private int selectedInspectorEntity = -1;
    SceneManager sceneManager;

    public void initialize(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        vsyncCurrentState = Farmland.get().getVsync();
        useVsync = new ImBoolean(Farmland.get().getVsync());
    }

    public void update(double dt) {
        if (vsyncCurrentState != useVsync.get()) {
            vsyncCurrentState = useVsync.get();
            Farmland.get().setVsync(vsyncCurrentState);
        }
    }

    public void renderImGui() {
        drawMainMenu();
        if (showSceneHierarchyWindow.get()) {
            drawSceneHierarchy();
        }
        if (showInspectorWindow.get()) {
            drawInspector();
        }
        if (showSettingsWindow.get()) {
            drawSettings();
        }
        if (showDemoWindow.get()) {
            drawDemo();
        }
        if (showPerformanceCounter.get()) {
            drawPerformanceCounter();
        }
    }

    private void drawMainMenu() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                if (ImGui.menuItem("Quit")) {
                    Farmland.get().close();
                }

                ImGui.endMenu();
            }

            if(ImGui.beginMenu("View")) {
                ImGui.menuItem("Show scene hierarchy", "", showSceneHierarchyWindow);
                ImGui.menuItem("Show inspector", "", showInspectorWindow);
                ImGui.separator();
                ImGui.menuItem("Show settings", "", showSettingsWindow);
                ImGui.menuItem("Show performance counter", "", showPerformanceCounter);

                ImGui.endMenu();
            }

            if (ImGui.beginMenu("Help")) {
                ImGui.menuItem("Show ImGui demo", "", showDemoWindow);

                ImGui.endMenu();
            }

            ImGui.endMainMenuBar();
        }
    }

    private void drawSceneHierarchy() {
        ImGui.setNextWindowSize(250, 600, ImGuiCond.Appearing);
        ImGuiUtils.setNextWindowPos(0, 0, ImGuiCond.Appearing);

        ImGui.begin("Scene hierarchy", showSceneHierarchyWindow);

        ImGui.text(sceneManager.getScene().getClass().getSimpleName());
        ImGui.separator();

        drawEntitiesTree(sceneManager.getScene().getRegistry().getEntitiesAtRoot(), true);

        ImGui.end();
    }

    private void drawEntitiesTree(List<Entity> entities, boolean root) {
        if (!root || entities.size() > 0) {
            for (Entity child : entities) {
                int flags = 0;
                if (child.getChildren().size() == 0) {
                    flags |= ImGuiTreeNodeFlags.Leaf;
                }
                if (selectedInspectorEntity == child.getId()) {
                    flags |= ImGuiTreeNodeFlags.Selected;
                }

                if (ImGui.treeNodeEx(child.getId(), flags, child.getNameOrIdentifier())) {
                    if (ImGui.isItemClicked()) {
                        selectedInspectorEntity = child.getId();
                    }

                    drawEntitiesTree(child.getChildren(), false);

                    ImGui.treePop();
                }
            }
        } else {
            ImGui.text("This scene has no entity");
        }
    }

    private void drawInspector() {
        ImGui.setNextWindowSize(350, 600, ImGuiCond.Appearing);
        ImGuiUtils.setNextWindowPosFromEnd(-350, 0, ImGuiCond.Appearing);

        ImGui.begin("Inspector", showInspectorWindow);

        if (selectedInspectorEntity != -1) {
            Entity entity = sceneManager.getScene().getRegistry().getEntityById(selectedInspectorEntity);
            int entityId = entity.getId();
            List<Component> components = new ArrayList<>(entity.getComponents());
            components.sort(Comparator.comparingInt(Component::getId));

            ImGui.text(entity.getNameOrIdentifier());
            ImGui.separator();

            if (components.size() > 0) {
                if (!values.containsKey(entityId)) {
                    values.put(entityId, new HashMap<>());
                }

                for (Component component : components) {
                    if (ImGui.treeNode(component.getClass().getSimpleName())) {
                        drawEditableFields(entityId, component);
                        ImGui.treePop();
                    }

                    ImGui.separator();
                }
            } else {
                ImGui.text("This entity has no components");
            }
        } else {
            ImGui.text("No entity selected");
        }

        ImGui.end();
    }

    private final Map<Integer, Map<Integer, Map<String, Object>>> values = new HashMap<>();

    private void drawEditableFields(int entityId, Component component) {
        int componentId = component.getId();
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(Runnable.class.getDeclaredFields()));
        fields.addAll(Arrays.asList(component.getClass().getDeclaredFields()));
        fields.removeIf(field -> !field.isAnnotationPresent(Editable.class));

        if (!values.get(entityId).containsKey(componentId)) {
            values.get(entityId).put(componentId, new HashMap<>());

            for (Field field : fields) {
                try {
                    Class<?> type = field.getType();
                    String name = field.getName();
                    Object originalValue = field.get(component);
                    Map<String, Object> container = values.get(entityId).get(componentId);

                    if (type == Vector2f.class) {
                        Vector2f value = (Vector2f)originalValue;
                        float[] floatArray = {value.x, value.y};
                        container.put(name, floatArray);
                    } else if (type == Float.class) {
                        Float value = (Float)originalValue;
                        ImFloat imFloatValue = new ImFloat(value);
                        container.put(name, imFloatValue);
                    } else if (type == Integer.class) {
                        Integer value = (Integer)originalValue;
                        ImInt imIntValue = new ImInt(value);
                        container.put(name, imIntValue);
                    } else if (type == String.class) {
                        String value = (String)originalValue;
                        ImString imStringValue = new ImString(value);
                        container.put(name, imStringValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (Field field : fields) {
            Class<?> type = field.getType();
            String name = field.getName();
            Object value = values.get(entityId).get(componentId).get(field.getName());

            if (type == Vector2f.class) {
                ImGui.inputFloat2(name, (float[])value);
            } else if (type == Float.class) {
                ImGui.inputFloat(name, (ImFloat)value);
            } else if (type == Integer.class) {
                ImGui.inputInt(name, (ImInt)value);
            } else if (type == String.class) {
                ImGui.inputText(name, (ImString)value);
            } else {
                ImGui.text("Unknown value type");
            }
        }

        component.renderImGui();
    }

    private void drawSettings() {
        ImGuiUtils.setNextWindowWithSizeCentered(300, 300, ImGuiCond.Appearing);

        ImGui.begin("Settings", showSettingsWindow);

        ImGui.checkbox("Enable V-sync", useVsync);

        ImGui.end();
    }

    private void drawPerformanceCounter() {
        ImGuiUtils.setNextWindowWithSizeCentered(200, 100, ImGuiCond.Appearing);

        ImGui.begin("Performance counter", showPerformanceCounter);

        int fps = Farmland.get().getTimer().getFPS();
        double ms = BigDecimal.valueOf(Farmland.get().getTimer().getFrameDuration())
                .setScale(3, RoundingMode.HALF_UP).doubleValue();

        ImGui.text("FPS: " + fps);
        ImGui.text("Framerate: " + ms + "/ms");

        ImGui.end();
    }

    private void drawDemo() {
        ImGui.showDemoWindow(showDemoWindow);
    }
}
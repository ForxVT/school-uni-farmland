package com.ustudents.farmland.entities.resources;

import com.ustudents.engine.core.Resources;
import com.ustudents.engine.ecs.Registry;
import com.ustudents.engine.ecs.component.SpriteComponent;
import com.ustudents.farmland.entities.Resource;
import org.joml.Vector4f;

public class WaterMelon extends Resource {
    /**
     * Class constructor.
     *
     * @param id       The ID.
     * @param registry The registry.
     */
    public WaterMelon(Integer id, Registry registry) {
        super(id, registry);
    }

    @Override
    public void init() {
        addComponent(SpriteComponent.class, Resources.loadTexture("Resource/crops.png"),new Vector4f(23 * 32, 6 * 32, 32, 64),11);
    }
}

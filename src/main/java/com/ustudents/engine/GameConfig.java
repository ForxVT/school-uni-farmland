package com.ustudents.engine;

import com.ustudents.engine.core.json.annotation.JsonSerializable;
import org.joml.Vector2i;

import java.util.*;

@JsonSerializable
public class GameConfig {
    @JsonSerializable(necessary = false)
    public Vector2i windowSize = new Vector2i(1280, 720);

    @JsonSerializable(necessary = false)
    public boolean useVsync = true;

    @JsonSerializable(necessary = false)
    public String language = "fr";

    @JsonSerializable(necessary = false)
    public Map<String, Object> game = new LinkedHashMap<>();
}
package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 * Lädt das interaktive 3D-Modell (glTF/GLB) über den AssetManager.
 */
public class ModelLoader {

    private static final String MODEL_PATH = "models/model.glb";
    private static final float DEFAULT_SPAWN_HEIGHT = 4f;

    /**
     * Lädt das konfigurierte Modell und positioniert es oberhalb des Bodens,
     * damit es initial fallen kann.
     *
     * @param assetManager jME-AssetManager
     * @return geladenes Spatial, bereit zur Physik-Anbindung
     */
    public Spatial loadInteractiveModel(AssetManager assetManager) {
        Spatial model = assetManager.loadModel(MODEL_PATH);
        model.setLocalTranslation(0f, DEFAULT_SPAWN_HEIGHT, 0f);
        return model;
    }
}

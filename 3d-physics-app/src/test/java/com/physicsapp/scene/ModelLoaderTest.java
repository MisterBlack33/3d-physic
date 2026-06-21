package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.scene.Spatial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModelLoaderTest {

    private ModelLoader modelLoader;
    private AssetManager assetManager;

    @BeforeEach
    void setUp() throws URISyntaxException {
        modelLoader = new ModelLoader();
        assetManager = new DesktopAssetManager(true);

        Path resourcesDir = resolveTestResourcesDir();
        assetManager.registerLocator(resourcesDir.toString(),
                com.jme3.asset.plugins.FileLocator.class);
    }

    private Path resolveTestResourcesDir() throws URISyntaxException {
        Path classesDir = Paths.get(
                getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        return classesDir.resolveSibling("classes");
    }

    @Test
    @DisplayName("Modell wird geladen und oberhalb des Bodens positioniert")
    void loadInteractiveModel_positionsAboveFloor() {
        // Act
        Spatial model = modelLoader.loadInteractiveModel(assetManager);

        // Assert
        assertNotNull(model);
        assertEquals(4f, model.getLocalTranslation().y, 0.0001f);
    }
}

package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.scene.Geometry;
import com.physicsapp.config.SceneObjectsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicObjectSpawnerTest {

    private DynamicObjectSpawner spawner;
    private AssetManager assetManager;

    @BeforeEach
    void setUp() {
        spawner = new DynamicObjectSpawner();
        assetManager = new DesktopAssetManager(true);
    }

    @Test
    @DisplayName("Spawnt konfigurierte Anzahl an Würfeln")
    void spawnCubes_createsConfiguredCount() {
        // Act
        Geometry[] cubes = spawner.spawnCubes(assetManager);

        // Assert
        assertEquals(SceneObjectsConfig.FALLING_CUBE_COUNT, cubes.length);
    }

    @Test
    @DisplayName("Würfel werden in steigender Höhe gestaffelt")
    void spawnCubes_staggersHeight() {
        // Act
        Geometry[] cubes = spawner.spawnCubes(assetManager);

        // Assert
        for (int i = 1; i < cubes.length; i++) {
            assertTrue(cubes[i].getLocalTranslation().y > cubes[i - 1].getLocalTranslation().y);
        }
    }

    @Test
    @DisplayName("Spawnt konfigurierte Anzahl an Kugeln")
    void spawnSpheres_createsConfiguredCount() {
        // Act
        Geometry[] spheres = spawner.spawnSpheres(assetManager);

        // Assert
        assertEquals(SceneObjectsConfig.FALLING_SPHERE_COUNT, spheres.length);
    }

    @Test
    @DisplayName("Kugeln werden in steigender Höhe gestaffelt")
    void spawnSpheres_staggersHeight() {
        // Act
        Geometry[] spheres = spawner.spawnSpheres(assetManager);

        // Assert
        for (int i = 1; i < spheres.length; i++) {
            assertTrue(spheres[i].getLocalTranslation().y > spheres[i - 1].getLocalTranslation().y);
        }
    }
}

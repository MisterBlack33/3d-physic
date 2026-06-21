package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.physicsapp.config.SceneObjectsConfig;

/**
 * Erzeugt die beweglichen Sandbox-Objekte (Würfel und Kugeln),
 * die zusätzlich zum Hauptmodell mit der Physik interagieren.
 */
public class DynamicObjectSpawner {

    /**
     * Erzeugt die konfigurierte Anzahl an Würfeln, gestaffelt in der Höhe.
     *
     * @param assetManager jME-AssetManager
     * @return Array der erzeugten Würfel-Geometrien
     */
    public Geometry[] spawnCubes(AssetManager assetManager) {
        Geometry[] cubes = new Geometry[SceneObjectsConfig.FALLING_CUBE_COUNT];
        Box shape = new Box(
                SceneObjectsConfig.CUBE_SIZE_HALF_EXTENT,
                SceneObjectsConfig.CUBE_SIZE_HALF_EXTENT,
                SceneObjectsConfig.CUBE_SIZE_HALF_EXTENT);

        for (int i = 0; i < cubes.length; i++) {
            Geometry cube = new Geometry("Cube" + i, shape);
            cube.setMaterial(createMaterial(assetManager, ColorRGBA.Orange));
            float height = SceneObjectsConfig.CUBE_SPAWN_HEIGHT_START + i * SceneObjectsConfig.CUBE_SPAWN_HEIGHT_STEP;
            cube.setLocalTranslation(SceneObjectsConfig.CUBE_SPAWN_X_OFFSET, height, 0f);
            cubes[i] = cube;
        }
        return cubes;
    }

    /**
     * Erzeugt die konfigurierte Anzahl an Kugeln, gestaffelt in der Höhe.
     *
     * @param assetManager jME-AssetManager
     * @return Array der erzeugten Kugel-Geometrien
     */
    public Geometry[] spawnSpheres(AssetManager assetManager) {
        Geometry[] spheres = new Geometry[SceneObjectsConfig.FALLING_SPHERE_COUNT];
        Sphere shape = new Sphere(16, 16, SceneObjectsConfig.SPHERE_RADIUS);

        for (int i = 0; i < spheres.length; i++) {
            Geometry sphere = new Geometry("Sphere" + i, shape);
            sphere.setMaterial(createMaterial(assetManager, ColorRGBA.Blue));
            float height = SceneObjectsConfig.SPHERE_SPAWN_HEIGHT_START
                    + i * SceneObjectsConfig.SPHERE_SPAWN_HEIGHT_STEP;
            sphere.setLocalTranslation(SceneObjectsConfig.SPHERE_SPAWN_X_OFFSET, height, 0f);
            spheres[i] = sphere;
        }
        return spheres;
    }

    private Material createMaterial(AssetManager assetManager, ColorRGBA color) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        return material;
    }
}

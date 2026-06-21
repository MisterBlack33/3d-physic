package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.physicsapp.config.PhysicsConfig;

/**
 * Erzeugt die statische Umgebung: Boden und vier Begrenzungswände.
 */
public class EnvironmentBuilder {

    private static final ColorRGBA FLOOR_COLOR = ColorRGBA.LightGray;
    private static final ColorRGBA WALL_COLOR = ColorRGBA.DarkGray;

    /**
     * Erzeugt die Bodenebene als Box-Geometrie.
     *
     * @param assetManager jME-AssetManager
     * @return Boden-Geometrie mit Material, ohne Physik-Control
     */
    public Geometry buildFloor(AssetManager assetManager) {
        Box floorShape = new Box(
                PhysicsConfig.FLOOR_SIZE_HALF_EXTENT, 0.25f, PhysicsConfig.FLOOR_SIZE_HALF_EXTENT);
        Geometry floor = new Geometry("Floor", floorShape);
        floor.setMaterial(createUnshadedMaterial(assetManager, FLOOR_COLOR));
        floor.setLocalTranslation(0f, -0.25f, 0f);
        return floor;
    }

    /**
     * Erzeugt vier Begrenzungswände um den Boden herum, damit Objekte
     * nicht aus der Szene fallen können.
     *
     * @param assetManager jME-AssetManager
     * @return Array mit vier Wand-Geometrien
     */
    public Geometry[] buildBoundaryWalls(AssetManager assetManager) {
        float half = PhysicsConfig.FLOOR_SIZE_HALF_EXTENT;
        float wallY = PhysicsConfig.WALL_HEIGHT / 2f;
        float thickness = PhysicsConfig.WALL_THICKNESS;

        Box northSouthShape = new Box(half, wallY, thickness);
        Box eastWestShape = new Box(thickness, wallY, half);

        Geometry north = createWall(assetManager, "WallNorth", northSouthShape, 0f, wallY, half);
        Geometry south = createWall(assetManager, "WallSouth", northSouthShape, 0f, wallY, -half);
        Geometry east = createWall(assetManager, "WallEast", eastWestShape, half, wallY, 0f);
        Geometry west = createWall(assetManager, "WallWest", eastWestShape, -half, wallY, 0f);

        return new Geometry[]{north, south, east, west};
    }

    private Geometry createWall(AssetManager assetManager, String name, Box shape,
                                 float x, float y, float z) {
        Geometry wall = new Geometry(name, shape);
        wall.setMaterial(createUnshadedMaterial(assetManager, WALL_COLOR));
        wall.setLocalTranslation(x, y, z);
        return wall;
    }

    private Material createUnshadedMaterial(AssetManager assetManager, ColorRGBA color) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        return material;
    }
}

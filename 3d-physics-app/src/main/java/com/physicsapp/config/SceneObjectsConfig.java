package com.physicsapp.config;

/**
 * Konfiguration der zusätzlichen, beweglichen Physik-Objekte in der Szene.
 */
public final class SceneObjectsConfig {

    public static final int FALLING_CUBE_COUNT = 5;
    public static final float CUBE_SIZE_HALF_EXTENT = 0.5f;
    public static final float CUBE_MASS_KG = 0.8f;
    public static final float CUBE_SPAWN_HEIGHT_START = 6f;
    public static final float CUBE_SPAWN_HEIGHT_STEP = 1.5f;
    public static final float CUBE_SPAWN_X_OFFSET = 2.5f;

    public static final int FALLING_SPHERE_COUNT = 4;
    public static final float SPHERE_RADIUS = 0.4f;
    public static final float SPHERE_MASS_KG = 0.5f;
    public static final float SPHERE_SPAWN_HEIGHT_START = 8f;
    public static final float SPHERE_SPAWN_HEIGHT_STEP = 1.2f;
    public static final float SPHERE_SPAWN_X_OFFSET = -2.5f;

    private SceneObjectsConfig() {
        // Reine Konstantenklasse ohne Verhalten.
    }
}

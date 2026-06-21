package com.physicsapp.config;

/**
 * Zentrale, unveränderliche Physik-Konfiguration.
 * Vermeidet Magic Numbers im restlichen Code (siehe Clean-Code-Guide).
 */
public final class PhysicsConfig {

    public static final float GRAVITY_Y = -9.81f;
    public static final float MODEL_MASS_KG = 1.0f;
    public static final float FLOOR_MASS_KG = 0f; // 0 = statisch
    public static final float WALL_MASS_KG = 0f;

    public static final float MODEL_RESTITUTION = 0.3f;
    public static final float MODEL_FRICTION = 0.6f;
    public static final float FLOOR_FRICTION = 0.8f;

    public static final float DRAG_FORCE_MULTIPLIER = 25f;
    public static final float THROW_IMPULSE_MULTIPLIER = 15f;

    public static final float FLOOR_SIZE_HALF_EXTENT = 20f;
    public static final float WALL_HEIGHT = 8f;
    public static final float WALL_THICKNESS = 0.5f;

    private PhysicsConfig() {
        // Utility-Konstantenklasse, keine Instanziierung gewünscht.
        // (Bewusste Ausnahme von "Utils"-Namensverbot: reine Konstanten,
        // kein Verhalten/Logik enthalten.)
    }
}

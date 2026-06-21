package com.physicsapp.physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.physicsapp.config.PhysicsConfig;

/**
 * Erstellt und konfiguriert die zentrale Bullet-Physikwelt (Gravitation etc.).
 */
public class PhysicsWorldFactory {

    /**
     * Erzeugt einen {@link BulletAppState} mit konfigurierter Gravitation.
     *
     * @return einsatzbereiter Bullet-Physik-State
     */
    public BulletAppState createConfiguredState() {
        BulletAppState bulletAppState = new BulletAppState();
        PhysicsSpace space = bulletAppState.getPhysicsSpace();
        space.setGravity(new com.jme3.math.Vector3f(0f, PhysicsConfig.GRAVITY_Y, 0f));
        return bulletAppState;
    }
}

package com.physicsapp.physics;

import com.jme3.bullet.BulletAppState;
import com.physicsapp.config.PhysicsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PhysicsWorldFactoryTest {

    @Test
    @DisplayName("Erstellter Bullet-State hat die konfigurierte Gravitation")
    void createConfiguredState_setsConfiguredGravity() {
        // Arrange
        PhysicsWorldFactory factory = new PhysicsWorldFactory();

        // Act
        BulletAppState state = factory.createConfiguredState();

        // Assert
        assertNotNull(state);
        assertEquals(PhysicsConfig.GRAVITY_Y, state.getPhysicsSpace().getGravity(null).y, 0.0001f);
    }
}

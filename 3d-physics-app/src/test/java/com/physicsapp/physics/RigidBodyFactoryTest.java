package com.physicsapp.physics;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RigidBodyFactoryTest {

    private RigidBodyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new RigidBodyFactory();
    }

    @Test
    @DisplayName("Box-Body erhält die übergebene Masse")
    void createBoxBody_setsMass() {
        // Arrange
        Geometry geometry = new Geometry("box", new Box(1f, 1f, 1f));

        // Act
        RigidBodyControl body = factory.createBoxBody(geometry, 2.5f);

        // Assert
        assertEquals(2.5f, body.getMass());
    }

    @Test
    @DisplayName("Box-Body mit Masse 0 ist statisch")
    void createBoxBody_zeroMass_isStatic() {
        // Arrange
        Geometry geometry = new Geometry("floor", new Box(10f, 0.5f, 10f));

        // Act
        RigidBodyControl body = factory.createBoxBody(geometry, 0f);

        // Assert
        assertFalse(body.isDynamic());
    }

    @Test
    @DisplayName("Sphere-Body erhält die übergebene Masse")
    void createSphereBody_setsMass() {
        // Arrange
        Geometry geometry = new Geometry("sphere", new Sphere(8, 8, 1f));

        // Act
        RigidBodyControl body = factory.createSphereBody(geometry, 1.2f, 1f);

        // Assert
        assertEquals(1.2f, body.getMass());
        assertTrue(body.isDynamic());
    }

    @Test
    @DisplayName("Hull-Body wird für ein Geometry-Mesh erfolgreich erstellt")
    void createHullBody_createsNonNullControl() {
        // Arrange
        Geometry geometry = new Geometry("mesh", new Box(1f, 1f, 1f));

        // Act
        RigidBodyControl body = factory.createHullBody(geometry, 1f);

        // Assert
        assertNotNull(body);
        assertEquals(1f, body.getMass());
    }
}

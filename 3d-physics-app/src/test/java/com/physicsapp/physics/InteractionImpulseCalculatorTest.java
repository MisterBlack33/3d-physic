package com.physicsapp.physics;

import com.jme3.math.Vector3f;
import com.physicsapp.config.PhysicsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InteractionImpulseCalculatorTest {

    private static final float DELTA = 0.0001f;

    private InteractionImpulseCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new InteractionImpulseCalculator();
    }

    @Test
    @DisplayName("Wurf-Impuls wird entlang normalisierter Richtung skaliert")
    void calculateThrowImpulse_scalesNormalizedDirection() {
        // Arrange
        Vector3f direction = new Vector3f(0f, 0f, 1f);

        // Act
        Vector3f impulse = calculator.calculateThrowImpulse(direction);

        // Assert
        assertEquals(0f, impulse.x, DELTA);
        assertEquals(0f, impulse.y, DELTA);
        assertEquals(PhysicsConfig.THROW_IMPULSE_MULTIPLIER, impulse.z, DELTA);
    }

    @Test
    @DisplayName("Wurf-Impuls normalisiert auch unnormalisierte Eingaben")
    void calculateThrowImpulse_normalizesNonUnitVector() {
        // Arrange
        Vector3f direction = new Vector3f(0f, 0f, 5f);

        // Act
        Vector3f impulse = calculator.calculateThrowImpulse(direction);

        // Assert
        assertEquals(PhysicsConfig.THROW_IMPULSE_MULTIPLIER, impulse.z, DELTA);
    }

    @Test
    @DisplayName("Wurf-Impuls wirft Exception bei null-Richtung")
    void calculateThrowImpulse_nullDirection_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateThrowImpulse(null));
    }

    @Test
    @DisplayName("Zugkraft zeigt vom aktuellen Punkt zum Zielpunkt")
    void calculateDragForce_pointsTowardsTarget() {
        // Arrange
        Vector3f current = new Vector3f(0f, 0f, 0f);
        Vector3f target = new Vector3f(2f, 0f, 0f);

        // Act
        Vector3f force = calculator.calculateDragForce(current, target);

        // Assert
        assertEquals(2f * PhysicsConfig.DRAG_FORCE_MULTIPLIER, force.x, DELTA);
        assertEquals(0f, force.y, DELTA);
        assertEquals(0f, force.z, DELTA);
    }

    @Test
    @DisplayName("Zugkraft ist Null-Vektor wenn Ziel = aktuelle Position")
    void calculateDragForce_samePosition_returnsZero() {
        // Arrange
        Vector3f position = new Vector3f(1f, 1f, 1f);

        // Act
        Vector3f force = calculator.calculateDragForce(position, position.clone());

        // Assert
        assertEquals(0f, force.length(), DELTA);
    }

    @Test
    @DisplayName("Zugkraft wirft Exception bei null currentPosition")
    void calculateDragForce_nullCurrent_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateDragForce(null, new Vector3f()));
    }

    @Test
    @DisplayName("Zugkraft wirft Exception bei null targetPosition")
    void calculateDragForce_nullTarget_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateDragForce(new Vector3f(), null));
    }
}

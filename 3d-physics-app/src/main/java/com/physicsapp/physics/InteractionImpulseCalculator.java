package com.physicsapp.physics;

import com.jme3.math.Vector3f;
import com.physicsapp.config.PhysicsConfig;

/**
 * Berechnet physikalische Impulse für Wurf- und Zieh-Interaktionen.
 * Reine Berechnungslogik, unabhängig von Eingabegeräten (gut testbar).
 */
public class InteractionImpulseCalculator {

    /**
     * Berechnet den Impuls-Vektor für einen Wurf in eine Richtung.
     *
     * @param direction normalisierte Wurfrichtung
     * @return skalierter Impulsvektor
     */
    public Vector3f calculateThrowImpulse(Vector3f direction) {
        requireNonNullDirection(direction);
        return direction.normalize().mult(PhysicsConfig.THROW_IMPULSE_MULTIPLIER);
    }

    /**
     * Berechnet die Zugkraft, um ein Objekt zu einem Zielpunkt zu ziehen.
     *
     * @param currentPosition aktuelle Objektposition
     * @param targetPosition  gewünschte Zielposition (z. B. Mausstrahl-Treffpunkt)
     * @return Kraftvektor in Richtung Ziel
     */
    public Vector3f calculateDragForce(Vector3f currentPosition, Vector3f targetPosition) {
        requireNonNullDirection(currentPosition);
        requireNonNullDirection(targetPosition);

        Vector3f delta = targetPosition.subtract(currentPosition);
        return delta.mult(PhysicsConfig.DRAG_FORCE_MULTIPLIER);
    }

    private void requireNonNullDirection(Vector3f vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector darf nicht null sein.");
        }
    }
}

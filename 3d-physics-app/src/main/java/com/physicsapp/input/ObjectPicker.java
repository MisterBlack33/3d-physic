package com.physicsapp.input;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Optional;

/**
 * Wandelt 2D-Mauskoordinaten in 3D-Raycasts um und findet das
 * getroffene physikalische Objekt in der Szene.
 */
public class ObjectPicker {

    /**
     * Führt einen Raycast von der Kamera durch die Mausposition aus.
     *
     * @param camera       aktive Kamera
     * @param mousePos     Mausposition in Bildschirmkoordinaten
     * @param sceneRoot    durchsuchbarer Szenen-Root
     * @return getroffenes Spatial mit RigidBodyControl, falls vorhanden
     */
    public Optional<Spatial> pickRigidBody(Camera camera, Vector2f mousePos, Node sceneRoot) {
        Ray ray = createRayFromMouse(camera, mousePos);
        CollisionResults results = new CollisionResults();
        sceneRoot.collideWith(ray, results);

        return findFirstWithRigidBody(results);
    }

    private Ray createRayFromMouse(Camera camera, Vector2f mousePos) {
        Vector3f origin = camera.getWorldCoordinates(mousePos, 0f);
        Vector3f direction = camera.getWorldCoordinates(mousePos, 1f).subtractLocal(origin).normalizeLocal();
        return new Ray(origin, direction);
    }

    private Optional<Spatial> findFirstWithRigidBody(CollisionResults results) {
        for (CollisionResult result : results) {
            Spatial hit = result.getGeometry();
            if (hit.getControl(RigidBodyControl.class) != null) {
                return Optional.of(hit);
            }
        }
        return Optional.empty();
    }
}

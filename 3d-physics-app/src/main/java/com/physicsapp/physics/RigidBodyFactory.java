package com.physicsapp.physics;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;

/**
 * Erstellt {@link RigidBodyControl}-Instanzen für Spatials.
 * Kapselt die Wahl der Collision-Shape-Strategie an einer Stelle.
 */
public class RigidBodyFactory {

    /**
     * Erstellt einen dynamischen RigidBody mit einer Hull-Collision-Shape,
     * geeignet für bewegliche, komplexe Meshes (z. B. importierte Modelle).
     *
     * @param spatial das zu umhüllende Objekt
     * @param mass    Masse in kg (0 = statisch)
     * @return konfiguriertes RigidBodyControl
     */
    public RigidBodyControl createHullBody(Spatial spatial, float mass) {
        CollisionShape shape = CollisionShapeFactory.createDynamicMeshShape(spatial);
        return new RigidBodyControl(shape, mass);
    }

    /**
     * Erstellt einen RigidBody mit einer einfachen Box-Collision-Shape,
     * geeignet für Würfel, Wände und Böden (performant).
     *
     * @param spatial das zu umhüllende Objekt
     * @param mass    Masse in kg (0 = statisch)
     * @return konfiguriertes RigidBodyControl
     */
    public RigidBodyControl createBoxBody(Spatial spatial, float mass) {
        CollisionShape shape = CollisionShapeFactory.createBoxShape(spatial);
        return new RigidBodyControl(shape, mass);
    }

    /**
     * Erstellt einen RigidBody mit einer Sphere-Collision-Shape.
     *
     * @param spatial das zu umhüllende Objekt
     * @param mass    Masse in kg (0 = statisch)
     * @param radius  Kugelradius
     * @return konfiguriertes RigidBodyControl
     */
    public RigidBodyControl createSphereBody(Spatial spatial, float mass, float radius) {
        com.jme3.bullet.collision.shapes.SphereCollisionShape shape =
                new com.jme3.bullet.collision.shapes.SphereCollisionShape(radius);
        return new RigidBodyControl(shape, mass);
    }
}

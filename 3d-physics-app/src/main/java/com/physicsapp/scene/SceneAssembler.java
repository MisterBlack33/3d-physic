package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.physicsapp.config.PhysicsConfig;
import com.physicsapp.config.SceneObjectsConfig;
import com.physicsapp.physics.RigidBodyFactory;

/**
 * Baut die vollständige Szene auf: Umgebung, Hauptmodell und
 * bewegliche Sandbox-Objekte, jeweils mit Physik-Anbindung.
 */
public class SceneAssembler {

    private final ModelLoader modelLoader;
    private final EnvironmentBuilder environmentBuilder;
    private final DynamicObjectSpawner objectSpawner;
    private final RigidBodyFactory rigidBodyFactory;

    public SceneAssembler(ModelLoader modelLoader,
                           EnvironmentBuilder environmentBuilder,
                           DynamicObjectSpawner objectSpawner,
                           RigidBodyFactory rigidBodyFactory) {
        this.modelLoader = modelLoader;
        this.environmentBuilder = environmentBuilder;
        this.objectSpawner = objectSpawner;
        this.rigidBodyFactory = rigidBodyFactory;
    }

    /**
     * Baut die komplette Szene unter dem übergebenen Root-Node auf
     * und registriert alle Physik-Körper im übergebenen Bullet-State.
     *
     * @param rootNode     Root-Node der jME-Szene
     * @param assetManager jME-AssetManager
     * @param bulletState  aktiver Bullet-Physik-State
     * @return das geladene Hauptmodell (für spätere Interaktion, z. B. Picking)
     */
    public Spatial assembleScene(Node rootNode, AssetManager assetManager, BulletAppState bulletState) {
        attachEnvironment(rootNode, assetManager, bulletState);
        attachDynamicObjects(rootNode, assetManager, bulletState);
        return attachMainModel(rootNode, assetManager, bulletState);
    }

    private void attachEnvironment(Node rootNode, AssetManager assetManager, BulletAppState bulletState) {
        Geometry floor = environmentBuilder.buildFloor(assetManager);
        attachStaticBody(rootNode, bulletState, floor);

        for (Geometry wall : environmentBuilder.buildBoundaryWalls(assetManager)) {
            attachStaticBody(rootNode, bulletState, wall);
        }
    }

    private void attachDynamicObjects(Node rootNode, AssetManager assetManager, BulletAppState bulletState) {
        for (Geometry cube : objectSpawner.spawnCubes(assetManager)) {
            attachBoxBody(rootNode, bulletState, cube, SceneObjectsConfig.CUBE_MASS_KG);
        }
        for (Geometry sphere : objectSpawner.spawnSpheres(assetManager)) {
            attachSphereBody(rootNode, bulletState, sphere);
        }
    }

    private Spatial attachMainModel(Node rootNode, AssetManager assetManager, BulletAppState bulletState) {
        Spatial model = modelLoader.loadInteractiveModel(assetManager);
        RigidBodyControl body = rigidBodyFactory.createHullBody(model, PhysicsConfig.MODEL_MASS_KG);
        body.setRestitution(PhysicsConfig.MODEL_RESTITUTION);
        body.setFriction(PhysicsConfig.MODEL_FRICTION);

        model.addControl(body);
        rootNode.attachChild(model);
        bulletState.getPhysicsSpace().add(body);
        return model;
    }

    private void attachStaticBody(Node rootNode, BulletAppState bulletState, Geometry geometry) {
        RigidBodyControl body = rigidBodyFactory.createBoxBody(geometry, PhysicsConfig.FLOOR_MASS_KG);
        geometry.addControl(body);
        rootNode.attachChild(geometry);
        bulletState.getPhysicsSpace().add(body);
    }

    private void attachBoxBody(Node rootNode, BulletAppState bulletState, Geometry geometry, float mass) {
        RigidBodyControl body = rigidBodyFactory.createBoxBody(geometry, mass);
        geometry.addControl(body);
        rootNode.attachChild(geometry);
        bulletState.getPhysicsSpace().add(body);
    }

    private void attachSphereBody(Node rootNode, BulletAppState bulletState, Geometry geometry) {
        RigidBodyControl body = rigidBodyFactory.createSphereBody(
                geometry, SceneObjectsConfig.SPHERE_MASS_KG, SceneObjectsConfig.SPHERE_RADIUS);
        geometry.addControl(body);
        rootNode.attachChild(geometry);
        bulletState.getPhysicsSpace().add(body);
    }
}

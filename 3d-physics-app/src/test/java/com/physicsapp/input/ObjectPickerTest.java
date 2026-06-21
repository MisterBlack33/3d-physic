package com.physicsapp.input;

import com.jme3.asset.DesktopAssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectPickerTest {

    private ObjectPicker picker;
    private Camera camera;
    private Node sceneRoot;
    private DesktopAssetManager assetManager;

    @BeforeEach
    void setUp() {
        picker = new ObjectPicker();
        camera = new Camera(800, 600);
        camera.setLocation(new com.jme3.math.Vector3f(0f, 0f, 10f));
        camera.lookAt(new com.jme3.math.Vector3f(0f, 0f, 0f), com.jme3.math.Vector3f.UNIT_Y);
        camera.setFrustumPerspective(45f, 800f / 600f, 1f, 1000f);
        sceneRoot = new Node("root");
        assetManager = new DesktopAssetManager(true);
    }

    @Test
    @DisplayName("Findet Objekt mit RigidBodyControl im Sichtzentrum")
    void pickRigidBody_findsObjectWithRigidBody() {
        // Arrange
        Geometry box = createBoxAt(0f, 0f, 0f);
        box.addControl(new RigidBodyControl(new BoxCollisionShape(1f, 1f, 1f), 1f));
        sceneRoot.attachChild(box);
        Vector2f screenCenter = new Vector2f(400f, 300f);

        // Act
        Optional<Spatial> result = picker.pickRigidBody(camera, screenCenter, sceneRoot);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Liefert leeres Optional wenn kein Objekt getroffen wird")
    void pickRigidBody_emptyScene_returnsEmpty() {
        // Arrange
        Vector2f screenCenter = new Vector2f(400f, 300f);

        // Act
        Optional<Spatial> result = picker.pickRigidBody(camera, screenCenter, sceneRoot);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Ignoriert getroffene Objekte ohne RigidBodyControl")
    void pickRigidBody_objectWithoutRigidBody_returnsEmpty() {
        // Arrange
        Geometry box = createBoxAt(0f, 0f, 0f);
        sceneRoot.attachChild(box);
        Vector2f screenCenter = new Vector2f(400f, 300f);

        // Act
        Optional<Spatial> result = picker.pickRigidBody(camera, screenCenter, sceneRoot);

        // Assert
        assertFalse(result.isPresent());
    }

    private Geometry createBoxAt(float x, float y, float z) {
        Geometry geometry = new Geometry("box", new Box(1f, 1f, 1f));
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.White);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(x, y, z);
        return geometry;
    }
}

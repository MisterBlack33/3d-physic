package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.physicsapp.physics.PhysicsWorldFactory;
import com.physicsapp.physics.RigidBodyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SceneAssemblerTest {

    @Mock
    private ModelLoader modelLoader;

    private EnvironmentBuilder environmentBuilder;
    private DynamicObjectSpawner objectSpawner;
    private RigidBodyFactory rigidBodyFactory;

    private SceneAssembler assembler;
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletState;

    @BeforeEach
    void setUp() {
        environmentBuilder = new EnvironmentBuilder();
        objectSpawner = new DynamicObjectSpawner();
        rigidBodyFactory = new RigidBodyFactory();

        assembler = new SceneAssembler(modelLoader, environmentBuilder, objectSpawner, rigidBodyFactory);
        rootNode = new Node("root");
        assetManager = new DesktopAssetManager(true);
        bulletState = new PhysicsWorldFactory().createConfiguredState();

        Geometry fakeModel = new Geometry("fakeModel", new Box(1f, 1f, 1f));
        when(modelLoader.loadInteractiveModel(assetManager)).thenReturn(fakeModel);
    }

    @Test
    @DisplayName("Komplette Szene wird unter Root-Node angehängt")
    void assembleScene_attachesAllObjectsToRoot() {
        // Act
        Spatial mainModel = assembler.assembleScene(rootNode, assetManager, bulletState);

        // Assert
        assertNotNull(mainModel);
        assertTrue(rootNode.getChildren().contains(mainModel));
        // Boden(1) + Wände(4) + Würfel(5) + Kugeln(4) + Hauptmodell(1) = 15
        assertEquals(15, rootNode.getChildren().size());
    }

    @Test
    @DisplayName("Alle erzeugten Körper werden im PhysicsSpace registriert")
    void assembleScene_registersAllBodiesInPhysicsSpace() {
        // Act
        assembler.assembleScene(rootNode, assetManager, bulletState);

        // Assert
        assertEquals(15, bulletState.getPhysicsSpace().getRigidBodyList().size());
    }
}

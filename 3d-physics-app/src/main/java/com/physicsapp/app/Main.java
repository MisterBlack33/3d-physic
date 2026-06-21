package com.physicsapp.app;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.physicsapp.input.MouseInteractionController;
import com.physicsapp.input.ObjectPicker;
import com.physicsapp.physics.InteractionImpulseCalculator;
import com.physicsapp.physics.PhysicsWorldFactory;
import com.physicsapp.physics.RigidBodyFactory;
import com.physicsapp.scene.DynamicObjectSpawner;
import com.physicsapp.scene.EnvironmentBuilder;
import com.physicsapp.scene.ModelLoader;
import com.physicsapp.scene.SceneAssembler;

/**
 * Einstiegspunkt der Anwendung. Verkabelt Physik, Szene und Eingabe
 * gemäß Dependency-Injection-Prinzip (manuelle Konstruktor-Injektion).
 */
public class Main extends SimpleApplication {

    private MouseInteractionController interactionController;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        BulletAppState bulletState = initPhysics();
        setupLighting();
        setupCamera();

        SceneAssembler assembler = createSceneAssembler();
        assembler.assembleScene(rootNode, assetManager, bulletState);

        setupInteraction(bulletState);
    }

    @Override
    public void simpleUpdate(float tpf) {
        interactionController.updateDrag(inputManager.getCursorPosition());
    }

    private BulletAppState initPhysics() {
        BulletAppState bulletState = new PhysicsWorldFactory().createConfiguredState();
        stateManager.attach(bulletState);
        return bulletState;
    }

    private void setupLighting() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.4f));
        rootNode.addLight(ambient);
    }

    private void setupCamera() {
        cam.setLocation(new Vector3f(0f, 8f, 18f));
        cam.lookAt(new Vector3f(0f, 2f, 0f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(10f);
    }

    private SceneAssembler createSceneAssembler() {
        return new SceneAssembler(
                new ModelLoader(),
                new EnvironmentBuilder(),
                new DynamicObjectSpawner(),
                new RigidBodyFactory());
    }

    private void setupInteraction(BulletAppState bulletState) {
        interactionController = new MouseInteractionController(
                inputManager, cam, rootNode,
                new ObjectPicker(),
                new InteractionImpulseCalculator());
        interactionController.registerInputMappings();
    }
}

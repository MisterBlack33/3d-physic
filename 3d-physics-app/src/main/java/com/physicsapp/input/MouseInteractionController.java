package com.physicsapp.input;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.physicsapp.physics.InteractionImpulseCalculator;

import java.util.Optional;

/**
 * Registriert Maus-Inputs und übersetzt sie in Wurf-Interaktionen
 * auf physikalische Objekte. Ziehen erfolgt über kontinuierliches
 * Auslesen der Mausposition während gedrückter Taste.
 */
public class MouseInteractionController implements ActionListener {

    private static final String MAPPING_GRAB = "GrabOrThrow";

    private final InputManager inputManager;
    private final Camera camera;
    private final Node sceneRoot;
    private final ObjectPicker objectPicker;
    private final InteractionImpulseCalculator impulseCalculator;

    private Spatial grabbedObject;

    public MouseInteractionController(InputManager inputManager, Camera camera, Node sceneRoot,
                                       ObjectPicker objectPicker,
                                       InteractionImpulseCalculator impulseCalculator) {
        this.inputManager = inputManager;
        this.camera = camera;
        this.sceneRoot = sceneRoot;
        this.objectPicker = objectPicker;
        this.impulseCalculator = impulseCalculator;
    }

    /** Registriert die Maus-Trigger beim InputManager. */
    public void registerInputMappings() {
        inputManager.addMapping(MAPPING_GRAB, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, MAPPING_GRAB);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!MAPPING_GRAB.equals(name)) {
            return;
        }
        if (isPressed) {
            handleGrabStart();
            return;
        }
        handleThrowRelease();
    }

    /**
     * Wird pro Frame aufgerufen, solange ein Objekt gegriffen ist,
     * um es zur aktuellen Mausposition zu ziehen.
     *
     * @param mousePos aktuelle Mausposition
     */
    public void updateDrag(Vector2f mousePos) {
        if (grabbedObject == null) {
            return;
        }
        applyDragForce(mousePos);
    }

    private void handleGrabStart() {
        Vector2f mousePos = inputManager.getCursorPosition();
        Optional<Spatial> picked = objectPicker.pickRigidBody(camera, mousePos, sceneRoot);
        picked.ifPresent(this::setGrabbedObject);
    }

    private void setGrabbedObject(Spatial spatial) {
        this.grabbedObject = spatial;
    }

    private void handleThrowRelease() {
        if (grabbedObject == null) {
            return;
        }
        applyThrowImpulse();
        grabbedObject = null;
    }

    private void applyDragForce(Vector2f mousePos) {
        RigidBodyControl body = grabbedObject.getControl(RigidBodyControl.class);
        Vector3f targetPoint = camera.getWorldCoordinates(mousePos, 0.5f);
        Vector3f force = impulseCalculator.calculateDragForce(body.getPhysicsLocation(), targetPoint);
        body.activate();
        body.applyCentralForce(force);
    }

    private void applyThrowImpulse() {
        RigidBodyControl body = grabbedObject.getControl(RigidBodyControl.class);
        Vector3f impulse = impulseCalculator.calculateThrowImpulse(camera.getDirection());
        body.activate();
        body.applyImpulse(impulse, Vector3f.ZERO);
    }
}

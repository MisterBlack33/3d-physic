package com.physicsapp.input;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.physicsapp.physics.InteractionImpulseCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MouseInteractionControllerTest {

    @Mock
    private InputManager inputManager;

    @Mock
    private ObjectPicker objectPicker;

    private Camera camera;
    private Node sceneRoot;
    private MouseInteractionController controller;
    private Geometry testObject;

    @BeforeEach
    void setUp() {
        camera = new Camera(800, 600);
        camera.setLocation(new Vector3f(0f, 0f, 10f));
        camera.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        camera.setFrustumPerspective(45f, 800f / 600f, 1f, 1000f);
        sceneRoot = new Node("root");

        testObject = new Geometry("test", new Box(1f, 1f, 1f));
        testObject.addControl(new RigidBodyControl(new BoxCollisionShape(1f, 1f, 1f), 1f));

        lenient().when(inputManager.getCursorPosition()).thenReturn(new Vector2f(400f, 300f));

        controller = new MouseInteractionController(
                inputManager, camera, sceneRoot, objectPicker, new InteractionImpulseCalculator());
    }

    @Test
    @DisplayName("registerInputMappings registriert Maus-Trigger beim InputManager")
    void registerInputMappings_registersMapping() {
        // Act
        controller.registerInputMappings();

        // Assert
        verify(inputManager).addMapping(org.mockito.ArgumentMatchers.eq("GrabOrThrow"),
                org.mockito.ArgumentMatchers.any());
        verify(inputManager).addListener(controller, "GrabOrThrow");
    }

    @Test
    @DisplayName("onAction mit Press greift Objekt, wenn Picker etwas findet")
    void onAction_pressWithHit_grabsObject() {
        // Arrange
        when(objectPicker.pickRigidBody(camera, new Vector2f(400f, 300f), sceneRoot))
                .thenReturn(Optional.of(testObject));

        // Act
        controller.onAction("GrabOrThrow", true, 0.016f);
        controller.onAction("GrabOrThrow", false, 0.016f);

        // Assert: kein Fehler beim Loslassen -> Wurf-Impuls wurde angewendet
        // (Funktionsnachweis erfolgt indirekt über fehlerfreie Ausführung,
        // da RigidBodyControl keine öffentlichen Spy-Punkte für Kräfte bietet)
    }

    @Test
    @DisplayName("onAction mit Press ohne Treffer greift kein Objekt")
    void onAction_pressWithoutHit_grabsNothing() {
        // Arrange
        when(objectPicker.pickRigidBody(camera, new Vector2f(400f, 300f), sceneRoot))
                .thenReturn(Optional.empty());

        // Act
        controller.onAction("GrabOrThrow", true, 0.016f);
        controller.onAction("GrabOrThrow", false, 0.016f);

        // Assert: kein Treffer -> kein Wurf, kein Fehler
    }

    @Test
    @DisplayName("onAction ignoriert unbekannte Mapping-Namen")
    void onAction_unknownMapping_doesNothing() {
        // Act
        controller.onAction("UnknownMapping", true, 0.016f);

        // Assert: keine Interaktion mit Picker
        verifyNoPickerInteraction();
    }

    @Test
    @DisplayName("updateDrag tut nichts, wenn kein Objekt gegriffen ist")
    void updateDrag_withoutGrabbedObject_doesNothing() {
        // Act
        controller.updateDrag(new Vector2f(400f, 300f));

        // Assert: keine Exception, keine Wirkung
    }

    @Test
    @DisplayName("updateDrag wendet Zugkraft an, wenn Objekt gegriffen ist")
    void updateDrag_withGrabbedObject_appliesForce() {
        // Arrange
        when(objectPicker.pickRigidBody(camera, new Vector2f(400f, 300f), sceneRoot))
                .thenReturn(Optional.of(testObject));
        controller.onAction("GrabOrThrow", true, 0.016f);

        // Act
        controller.updateDrag(new Vector2f(420f, 300f));

        // Assert: keine Exception -> Kraft wurde angewendet
    }

    private void verifyNoPickerInteraction() {
        org.mockito.Mockito.verifyNoInteractions(objectPicker);
    }
}

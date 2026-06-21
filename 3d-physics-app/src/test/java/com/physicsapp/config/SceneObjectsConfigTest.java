package com.physicsapp.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SceneObjectsConfigTest {

    @Test
    @DisplayName("Konstruktor ist privat (Konstantenklasse nicht instanziierbar)")
    void constructor_isPrivate() throws NoSuchMethodException {
        // Arrange
        Constructor<SceneObjectsConfig> constructor = SceneObjectsConfig.class.getDeclaredConstructor();

        // Assert
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    @DisplayName("Konstruktor ist über Reflection aufrufbar ohne Fachfehler")
    void constructor_canBeInvokedViaReflection()
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        // Arrange
        Constructor<SceneObjectsConfig> constructor = SceneObjectsConfig.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Act
        SceneObjectsConfig instance = constructor.newInstance();

        // Assert
        assertTrue(instance != null);
    }
}

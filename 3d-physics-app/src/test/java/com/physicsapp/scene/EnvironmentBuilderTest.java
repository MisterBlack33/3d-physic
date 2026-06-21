package com.physicsapp.scene;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.scene.Geometry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnvironmentBuilderTest {

    private EnvironmentBuilder builder;
    private AssetManager assetManager;

    @BeforeEach
    void setUp() {
        builder = new EnvironmentBuilder();
        assetManager = new DesktopAssetManager(true);
    }

    @Test
    @DisplayName("Boden wird mit Material und unterhalb von Y=0 erzeugt")
    void buildFloor_createsPositionedGeometry() {
        // Act
        Geometry floor = builder.buildFloor(assetManager);

        // Assert
        assertNotNull(floor.getMaterial());
        assertEquals(-0.25f, floor.getLocalTranslation().y, 0.0001f);
    }

    @Test
    @DisplayName("Es werden genau vier Begrenzungswände erzeugt")
    void buildBoundaryWalls_createsFourWalls() {
        // Act
        Geometry[] walls = builder.buildBoundaryWalls(assetManager);

        // Assert
        assertEquals(4, walls.length);
    }

    @Test
    @DisplayName("Wände haben eindeutige Namen")
    void buildBoundaryWalls_haveUniqueNames() {
        // Act
        Geometry[] walls = builder.buildBoundaryWalls(assetManager);

        // Assert
        long distinctNames = java.util.Arrays.stream(walls)
                .map(Geometry::getName)
                .distinct()
                .count();
        assertEquals(4, distinctNames);
    }
}

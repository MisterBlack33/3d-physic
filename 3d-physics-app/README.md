# 3D Physics App

Interaktive 3D-Szene mit jMonkeyEngine 3 + Bullet-Physik (Minie).
Das mitgelieferte glTF-Modell (`source/model.glb`) fällt per Gravitation
auf einen Boden, kollidiert mit Wänden/anderen Objekten und kann per
Maus gegriffen, gezogen und geworfen werden.

## Build & Start

```bash
mvn clean package
java -jar target/3d-physics-app.jar
```

## Steuerung

- **Linke Maustaste gedrückt halten** auf einem Objekt: greifen & ziehen
- **Maustaste loslassen**: wirft das Objekt in Blickrichtung der Kamera
- **WASD**: Kamera bewegen (FlyCam, Standard jME-Steuerung)

## Architektur (Schichten)

```
app/      Main.java – Einstiegspunkt, Verkabelung (DI)
config/   PhysicsConfig, SceneObjectsConfig – alle Konstanten zentral
physics/  PhysicsWorldFactory, RigidBodyFactory, InteractionImpulseCalculator
scene/    ModelLoader, EnvironmentBuilder, DynamicObjectSpawner, SceneAssembler
input/    ObjectPicker, MouseInteractionController
```

Jede Klasse hat genau eine Verantwortung (Single Responsibility),
Abhängigkeiten werden injiziert (Konstruktor-Injektion), keine
globalen/statischen Zustände, keine Magic Numbers.

## Tests

```bash
mvn test
```

JUnit 5 + Mockito, Ziel-Testabdeckung >90% (JaCoCo, `mvn verify`
bricht den Build ab, falls die Schwelle unterschritten wird).

## Wichtiger Hinweis zur Build-Umgebung

Dieses Projekt wurde in einer Sandbox ohne Zugriff auf Maven Central
(`repo.maven.apache.org`) erstellt. Der Code konnte daher **nicht
kompiliert oder getestet** werden. Bitte beim ersten lokalen Build
genau auf Fehlermeldungen achten – insbesondere bei:

- Minie-Artefakt-Koordinaten/Version (`com.github.stephengold:Minie`)
- jME3-Versionskompatibilität (3.7.0-stable)
- glTF-Loader-Registrierung (jme3-plugins sollte das automatisch über
  SPI erledigen; falls nicht, ggf. manuell `GltfModelKey` nutzen)

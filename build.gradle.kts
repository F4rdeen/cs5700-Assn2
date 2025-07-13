plugins {
    kotlin("jvm") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjfx:javafx-base:21:win")
    implementation("org.openjfx:javafx-controls:21:win")
    implementation("org.openjfx:javafx-fxml:21:win")
    implementation("org.openjfx:javafx-graphics:21:win")
    implementation("org.openjfx:javafx-media:21:win")
    implementation("org.openjfx:javafx-swing:21:win")
    implementation("org.openjfx:javafx-web:21:win")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}

tasks.withType<JavaExec> {
    val modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics",
        "javafx.media",
        "javafx.swing",
        "javafx.web"
    )
    val javafxLibPath = configurations.runtimeClasspath.get().filter { it.name.contains("javafx") }.joinToString(":")
    jvmArgs = listOf(
        "--module-path", javafxLibPath,
        "--add-modules", modules.joinToString(",")
    )
}

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Run the main class with JavaFX modules."
    mainClass.set("AppKt") // Use the correct main class (App.kt)
    classpath = sourceSets["main"].runtimeClasspath
    val modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics",
        "javafx.media",
        "javafx.swing",
        "javafx.web"
    )
    val javafxLibPath = configurations.runtimeClasspath.get().filter { it.name.contains("javafx") }.joinToString(":")
    jvmArgs = listOf(
        "--module-path", javafxLibPath,
        "--add-modules", modules.joinToString(",")
    )
}
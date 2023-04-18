import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "dev.jombi"
version = "1.0-DEV"
val lwjglVersion = "3.3.1"
val natives = arrayOf("natives-windows", "natives-macos-arm64")

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")

    natives.forEach {
        runtimeOnly("org.lwjgl", "lwjgl", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = it)
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    archiveFileName.set("${project.name.toLowerCaseAsciiOnly()}.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = archiveVersion.get()
        attributes["Main-Class"] = "dev.jombi.tetris.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("dev.jombi.tetris.MainKt")
}
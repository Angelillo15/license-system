import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    implementation(project(":server"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.javalin:javalin:5.5.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.projectlombok:lombok:1.18.28")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}



application {
    mainClass.set("es.angelillo15.license.LicenseServer")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("license-server.jar")
    archiveClassifier.set("")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
        compileOnly("com.google.guava:guava:31.1-jre")
        compileOnly("io.javalin:javalin:5.5.0")
        compileOnly("com.fasterxml.jackson.core:jackson-databind:2.15.1")
        compileOnly("org.slf4j:slf4j-simple:2.0.7")
        compileOnly("org.projectlombok:lombok:1.18.28")
        annotationProcessor("org.projectlombok:lombok:1.18.28")

    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}

tasks.register("buildFrontend") {
    doLast {
        project.exec {
            commandLine("pwd")
            commandLine("yarn", "--cwd", "./frontend", "build")
        }
    }
}

tasks.register("devFrontServer") {
    doLast {
        project.exec {
            commandLine("pwd")
            commandLine("yarn", "--cwd", "./frontend", "dev")
        }
    }
}

tasks.register("buildJar") {
    dependsOn("buildFrontend", "shadowJar")
}
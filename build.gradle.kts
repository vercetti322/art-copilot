plugins {
    id("java")
    id("application")
}

group = "io.jatinjindal"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.squareup.okhttp3:okhttp:5.5.0")

    implementation("tools.jackson.core:jackson-databind:3.2.2")
    implementation("net.java.dev.jna:jna:5.19.1")
    implementation("net.java.dev.jna:jna-platform:5.19.1")
}

tasks.test {
    useJUnitPlatform()
}
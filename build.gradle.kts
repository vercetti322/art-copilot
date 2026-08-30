plugins {
    id("java")
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
}

tasks.test {
    useJUnitPlatform()
}
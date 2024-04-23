plugins {
    id("com.open-elements.benchscape.jmh")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:1.37")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}
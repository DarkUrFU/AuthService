plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
}



group = "com.darkurfu"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.1")


    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    //implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.3")
    implementation("org.springframework.security:spring-security-oauth2-resource-server:6.1.5")
    //implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    //implementation("io.swagger.core.v3:swagger-annotations:2.2.8")
    //implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testImplementation("org.junit.platform:junit-platform-suite:1.8.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

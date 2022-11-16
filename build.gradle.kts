plugins {
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.splunk.example.SpanAttributesMain")
    applicationDefaultJvmArgs = listOf(
        "-javaagent:splunk-otel-javaagent-1.17.0.jar",
        "-Dotel.javaagent.debug=true",
        "-Dotel.service.name=SpanAttrExample"
    )
}

dependencies {
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.19.2-alpha")
}

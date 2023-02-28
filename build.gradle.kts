plugins {
    application
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    val compilerArgs = options.compilerArgs
    //NOTE: This is required for custom @SpanAttribute names
    compilerArgs.addAll(listOf("-parameters"))
}
application {
    mainClass.set("com.splunk.example.SpanAttributesMain")
    applicationDefaultJvmArgs = listOf(
        "-javaagent:splunk-otel-javaagent-1.18.0.jar",
        "-Dotel.javaagent.debug=true",
        "-Dotel.service.name=SpanAttrExample",
        "-Dotel.instrumentation.methods.include=com.splunk.example.SpanAttributesMain[superDuperBonusMethod,highScore]"
    )
}

dependencies {
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.23.0")
}

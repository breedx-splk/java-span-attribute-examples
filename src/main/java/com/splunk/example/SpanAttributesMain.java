package com.splunk.example;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.util.concurrent.TimeUnit;

public class SpanAttributesMain {

    public static void main(String[] args) throws Exception {
        new SpanAttributesMain().run();

    }

    private void run() throws Exception {
        int counter = 0;
        while(true){
            counter++;
            System.out.println("Performing action...");
            parentMethod(counter);
            System.out.println("Action complete.");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * This is the root span in the trace. This demonstrates how @WithSpan
     * will automatically pull the name of the method to use as the span
     * name, and will also demonstrate that the method argument is
     * ignored (not included as a span attribute).
     */
    @WithSpan
    private void parentMethod(int counter) {
        int bucket = counter % 5; // Quantized to 5 buckets
        method2(bucket);
    }

    /**
     * Method 2 demonstrates both custom span naming in @WithSpan
     * and also the use of @SpanAttribute to add an attribute
     * to the span. In this case, the attribute name will be taken
     * from the argument name "bucket". IMPORTANT NOTE: This only
     * works if javac is invoked with -parameters. See method3()
     * below for an example of manually specifying the attribute
     * name.
     */
    @WithSpan("Method Two")
    private void method2(@SpanAttribute int bucket) {
        method3(bucket);
        method4(bucket);
        method5(bucket);
        superDuperBonusMethod(bucket);
    }

    /**
     * Method 3 demonstrates how to set a custom span name as well
     * as customizing the name of the parameter attribute.
     */
    @WithSpan("Method Three")
    private void method3(@SpanAttribute("custom.example.bucket.value") int bucket) {
        //no-op zzz
    }

    /**
     * Method 4 demonstrates how to manually add an attribute to the
     * currently-scoped span.
     */
    @WithSpan
    private void method4(int bucket) {
        Span.current().setAttribute("special.bucket.value", bucket);
    }

    /**
     * This method demonstrates how to do full manual creation of a new child
     * span and to manually add an attribute. Note that this method is NOT
     * using the @WithSpan convenience annotation, but instead reaches out
     * to GlobalOpenTelemetry to acquire a tracer. This approach offers much
     * greater control, but also requires more code.
     */
    private void method5(int bucket) {
        Tracer tracer = GlobalOpenTelemetry.get().getTracer("SpanAttributesMain");
        SpanBuilder spanBuilder = tracer.spanBuilder("method 5");
        spanBuilder.setAttribute("method5.bucket.value", bucket);
        Span span = spanBuilder.startSpan();
        try(Scope scope = span.makeCurrent()){
            //Normally there would be "real" work done in this span context, but
            //for this example we just sleep.
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // If errors are relevant, they may be set on the
            span.setStatus(StatusCode.ERROR);
        } finally {
            span.end();
        };
    }

    private void superDuperBonusMethod(int bucket) {
        highScore(bucket);
    }

    private void highScore(int bucket) {
        System.out.println("You got the top score! " + bucket);
        Span.current().setAttribute("bucket", bucket);
    }


}

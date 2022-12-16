# java-span-attribute-examples

Example code showing how to add attributes to OpenTelemetry spans.

[Please see the code for details](https://github.com/breedx-splk/java-span-attribute-examples/blob/main/src/main/java/com/splunk/example/SpanAttributesMain.java).

## Running

```
./gradlew run
```

Note: Java instrumentation debug logging is enabled, so it's fairly verbose.

## Output

At the top level, we get a nice trace consisting of 5 spans:

<img width="876" alt="image" src="https://user-images.githubusercontent.com/75337021/202268401-4bd88e67-494c-44b4-883a-67ed822853a0.png">

Let's dive a little deeper into each one:

### Parent Method (root span)

We see that the root span attained its name from the simple use of the `@WithSpan` attribute. 
The operation name is `SpanAttributesMain.parentMethod` which is automatically derived from the 
name of the class and the name of the method being instrumented.

It is worth pointing out that, by default, method parameters are NOT included as attributes. 
In this example, you may note the absence of `counter` anywhere on the span.

In the list of tags (OpenTelemtry attributes) you will notice `code.function` and `code.namespace`,
which are standard [OpenTelmetry semantic conventions](https://github.com/open-telemetry/opentelemetry-specification/blob/main/specification/trace/semantic_conventions/span-general.md#source-code-attributes)
for source code. This will be true of all other spans that used `@WithSpan` below.

<img width="750" alt="image" src="https://user-images.githubusercontent.com/75337021/202268737-6206d1ca-c6bb-4b60-a480-44167e5220b5.png">

### Method 2

Method 2 looks a lot like the root span, with a few exceptions. First, the name has been customized so that
instead of the raw class and method name, we see `Method Two`. This is customized by passing a string value
to the `@WithSpan` annotation.

Additionally, there is now a new custom attribute on the span: `bucket`. The `@SpanAttribute` parameter annotation
is used to automatically append a parameter as a tag to a span. **NOTE**: The automatic detection of parameter names
is only available through java reflection when the `-parameters` argument is passed to the compiler (see the `build.gradle.kts`
to see how this was accomplished). Without `-parameters` passed to javac, your `@SpanAttribute` argument will NOT be
on the span unless you give it a name (see method 3 below).

<img width="729" alt="image" src="https://user-images.githubusercontent.com/75337021/202268906-795073f2-4d62-4eef-bd7b-5c516af3ef14.png">

### Method 3

Method 3 is similar to `Method Two` but it customizes the attribute name. Instead of `bucket`, it has specified `custom.example.bucket.value`
by passing an argument to the `@SpanAttribute` annotation.

<img width="727" alt="image" src="https://user-images.githubusercontent.com/75337021/202268983-3b70bf49-d906-4f5d-abc2-0948e8bbe6cf.png">

### Method 4

Method 4 falls back to the generic `@WithSpan` naming but has manually added an attribute to the currently scoped span *with code*.

The code to do this looks like this:
```
Span.current().setAttribute("special.bucket.value", bucket);
```

<img width="724" alt="image" src="https://user-images.githubusercontent.com/75337021/202269057-9ba0bef5-73ed-4672-88ae-dc883c543181.png">

### Method 5

Span 5 is created entirely with manual instrumentation. The span name (`method 5`) and custom attribute name (`method5.bucket.value`) are
both generated with java code manually written into the method. 

This approach is the most flexible, but requires more effort. It's worth noting that the instrumentation tags like `code.function`,
`code.namespace`, `otel.library.name`, and `otel.library.version` are not added automatically here.

<img width="730" alt="image" src="https://user-images.githubusercontent.com/75337021/202269123-bdb69cb4-5d26-4ffa-9ef9-8034c7e9f2de.png">

### Bonus method!

Because we're showing several approaches to manual instrumentation, why not include
one more? This approach uses a built-in feature of the java agent and doesn't actually 
require making any code changes.

If you peek in the [`build.gradle.kts`](build.gradle.kts) file, you'll see that we have added
a system property: `otel.instrumentation.methods.include`:

```
-Dotel.instrumentation.methods.include=com.splunk.example.SpanAttributesMain[superDuperBonusMethod,highScore]
```

[This property](https://opentelemetry.io/docs/instrumentation/java/automatic/annotations/#creating-spans-around-methods-with-otelinstrumentationmethodsinclude)
allows us to tell the java instrumentation agent to automatically create spans for certain methods
on certain classes. In our case, we have asked the agent to create spans for both the `superDuperBonusMethod` and 
`highScore` methods on the `SpanAttributesMain` class.

In the implementation of `highScore()` we have manually obtained the active span in order ot set the 
bucket attribute. In the tracing view, it ends up looking like this:

<img width="774" alt="image" src="https://user-images.githubusercontent.com/75337021/208167962-c2c34c84-835e-4b25-8ddd-d16f029372d4.png">


## Metricizing Span Attributes

From Tag Spotlight, you can click "New MetricSet" to define a new metric set for our custom attribute:

<img width="627" alt="image" src="https://user-images.githubusercontent.com/75337021/202287440-d07e0573-3d01-4dab-a99b-9f58b215f9de.png">

Now that we've created some span data with our custom bucket attribute, we can create metrics for it.

<img width="919" alt="image" src="https://user-images.githubusercontent.com/75337021/202287024-3946dab0-ec50-4d67-9e76-6ef4e63afa0e.png">

As you can see, we can compare times for each bucket (which in our contrived example is not exciting) or graph them to do additional analysis.

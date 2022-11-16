# java-span-attribute-examples

Example code showing how to add attributes to OpenTelemetry spans.

[Please see the code for details](https://github.com/breedx-splk/java-span-attribute-examples/blob/main/src/main/java/com/splunk/example/SpanAttributesMain.java).

## Running

```
./gradlew run
```

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

<img width="727" alt="image" src="https://user-images.githubusercontent.com/75337021/202268983-3b70bf49-d906-4f5d-abc2-0948e8bbe6cf.png">

### Method 4

<img width="724" alt="image" src="https://user-images.githubusercontent.com/75337021/202269057-9ba0bef5-73ed-4672-88ae-dc883c543181.png">

### Method 5

<img width="730" alt="image" src="https://user-images.githubusercontent.com/75337021/202269123-bdb69cb4-5d26-4ffa-9ef9-8034c7e9f2de.png">

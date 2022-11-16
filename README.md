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

### Parent Method (topmost span)

<img width="750" alt="image" src="https://user-images.githubusercontent.com/75337021/202268737-6206d1ca-c6bb-4b60-a480-44167e5220b5.png">

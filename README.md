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

### Method 2

<img width="729" alt="image" src="https://user-images.githubusercontent.com/75337021/202268906-795073f2-4d62-4eef-bd7b-5c516af3ef14.png">

### Method 3

<img width="727" alt="image" src="https://user-images.githubusercontent.com/75337021/202268983-3b70bf49-d906-4f5d-abc2-0948e8bbe6cf.png">

### Method 4

<img width="724" alt="image" src="https://user-images.githubusercontent.com/75337021/202269057-9ba0bef5-73ed-4672-88ae-dc883c543181.png">

### Method 5

<img width="730" alt="image" src="https://user-images.githubusercontent.com/75337021/202269123-bdb69cb4-5d26-4ffa-9ef9-8034c7e9f2de.png">

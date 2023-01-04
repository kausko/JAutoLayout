# JAutoLayout

[![](https://jitpack.io/v/kausko/JAutoLayout.svg)](https://jitpack.io/#kausko/JAutoLayout)

A constraint-based layout manager that allows you to lay out components in (Java) Swing using [Apple's Visual Format Language (VFL)](https://developer.apple.com/library/archive/documentation/UserExperience/Conceptual/AutolayoutPG/VisualFormatLanguage.html).

## Installation

### Maven
In `pom.xml`, add the following at the end of the `<repositories>` section:

    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>

Then, add the following to the `<dependencies>` section:

    <dependency>
        <groupId>com.github.kausko</groupId>
        <artifactId>Jautolayout</artifactId>
        <version>main-SNAPSHOT</version>
    </dependency>

### Gradle
In `build.gradle`, add the following at the end of the `repositories` section:

    maven { url 'https://jitpack.io' }

Then, add the following to the `dependencies` section:

    implementation 'com.github.kausko.JAutoLayout:main-SNAPSHOT'

**Notes:**
- Only works with Java 17 and above
- The `main-SNAPSHOT` version is the latest commit on the main branch. It can be replaced with a specific commit hash or tag.
- For Kotlin DSL, use `url = uri("https://jitpack.io")` and `implementation(...)`.

## Usage
    
```java
import org.JAutoLayout.JAutoLayout;

public class Example {
    public static void main(String[] args) {
        // ...
        var button1 = new JButton("button1");
        var button2 = new JButton("button2");

        var viewNames = new HashMap<String, Component>();

        viewNames.put("button1", button1);
        viewNames.put("button2", button2);

        // VFL for equal width buttons
        var constraints = """
                    [button1(==button2)]
                    |-[button1]-[button2]-|
                    V:[button1(==button2)]
                """;
        var layout = new JAutoLayout(constraints, viewNames);
        var panel = new JPanel(layout);
        panel.add(button1);
        panel.add(button2);
        // ...
    }
}
```

*An interactive playground is available in the demo directory.*
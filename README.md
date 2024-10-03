# File Format Wrapper for Java (FFW4J)

A small wrapper around a few popular libraries and some internal utilities that make file handling a little bit easier.

This project has 3 goals:

* Allow all files to be compatible with one another, allowing for conversion and compatibility between nearly any file type;
* Allow developers to forget about file parsing and the costs that come with it, and choose whats right for them;
* And complete both goals using a modular system of dependencies, allowing developers to pick and choose what they want from FFW4J

## Contents

- [Starting Guide](#starting-guide)
- [Installing](#installing)
- [What can FFW4J do?](#what-can-ffw4j-do)
  - [Benchmarks](#benchmarks)
  - [How does it work?](#how-does-it-work)
- [Legal](#legal)

## Starting Guide

TODO: The following code is very outdated

```java
import java.io.*;

import net.onedsix.ffw4j.core.FileEntry;
import net.onedsix.ffw4j.core.ex.FFWException;
import net.onedsix.ffw4j.core.FFWServiceLoader;
import net.onedsix.ffw4j.services.container.AbstractFileFormat;

class FFW4JTest {
    public static void main(String[] args) {
        // Make sure FFW4J's service loaders are ran ASAP
        // Formats don't need to be loaded
        FFWServiceLoader.loadReaderServices();
        FFWServiceLoader.loadConversionServices();
        // As a shorthand, you can also just construct the service loader
        new FFWServiceLoader();
        
        // Start with passing a file into FFW4J
        // You can pass in: File, Path, String, ZipFile, or FileHandle (if using LibGDX)
        // It will return an IOException if something goes wrong
        // The return type is <? extends PrimitiveQueryableFile>
        try (PrimitiveQueryableFile file = (PrimitiveQueryableFile) FileEntry.read("./data.json")) {
            // Grab some data from it
            int messages = file.getInt("messages");
            // Convert the Json to a Toml
            // convert(AbstractFileFormat, Class<? extends AbstractFileFormat>, Class<? extends AbstractFileFormat>)
            TomlFileFormat toml = convert(file, file.getFormat(), TomlFileFormat.class);
        
        
        } catch (IOException ioe) {
            log.error("You should probably handle errors more gracefully that this", ioe);
        } catch (ClassCastException cce) {
            log.error("Invalid file type!", cce);
        }
    }
}
```

## Installing

We are currently using Jitpack for distribution.

```kotlin
repositories {
    // You probably have these already
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Import FFW4J Core
    implementaion("com.github.OneDSix:ffw4j:core:$ffw4jVersion")

    // Then import the formats and systems you want from it
    implementation("com.github.OneDSix:ffw4j:jackson:toml:$ffw4jVersion")
    implementation("com.github.OneDSix:ffw4j:jackson:json:$ffw4jVersion")
    implementation("com.github.OneDSix:ffw4j:gson:$ffw4jVersion")
}
```

Note that the only format built into `ffw4j:core` is the `.txt` format.\
You need to import the rest of the formats you require.

## What can FFW4J do?

There are over 20 file types planned, with 4 already covered:

| File Extension                                                     | Converts to...                                | Gets with...                                         | Lib Source                          |
|--------------------------------------------------------------------|-----------------------------------------------|------------------------------------------------------|-------------------------------------|
| `.json`                                                            |                                               | With                                                 | Gson; `ffw4j:gson`                  |
| `.json`                                                            | :heavy_check_mark:, Prop, TOML, XML           | With Separate Methods                                | Jackson; `ffw4j:jackson:json`       |
| `.json5`                                                           |                                               |                                                      | Jackson; `ffw4j:jackson:json-five`  |
| `.toml`                                                            | :heavy_check_mark:, JSON                      | With Separate Methods                                | Jackson; `ffw4j:jackson:toml`       |
| `.xml`                                                             | :heavy_check_mark:, JSON & Prop               | With a Single Method, returns `Object`               | Jackson; `ffw4j:jackson:xml`        |
| `.yaml`                                                            | :x:                                           | With either Specified Class or `Map<Object, Object>` | Jackson; `ffw4j:jackson:yaml`       |
| `.yml`                                                             |                                               |                                                      | Jackson; `ffw4j:jackson:yml`        |
| `.csv`                                                             | :large_orange_diamond:, JSON, Prop, TOML, XML | With a Single Method, returns `String`               | Jackson; `ffw4j:jackson:csv`        |
| `.properties`                                                      | :heavy_check_mark:, JSON & XML                | With a Single Method, returns `String`               | Jackson; `ffw4j:jackson:properties` |
| `.txt`                                                             | :x:                                           | N/A                                                  | FFW4J Core; `ffw4j`                 |
| `.plist`                                                           | :large_orange_diamond:, XML                   | With a Single Method, returns `Object`               | FFW4J PList; `ffw4j:plist`          |
| `.hocon`                                                           |                                               |                                                      | Lightbend Config; `ffw4j:hocon`     |
| `.bson`                                                            |                                               |                                                      | TODO; `ffw4j:bson`                  |
| [`.phpsf`](https://en.wikipedia.org/wiki/PHP_serialization_format) |                                               |                                                      | TODO; `ffw4j:php`                   |

### Benchmarks

TODO: How do I benchmark this? JMH + JUnit obviously, but im not sure how to set it up.

### How does it work?

FFW4J is split into 3 portions:

* `FormatReaderService`s
* `FormatContainer`s
* and `FormatConversionService`s

#### `FormatIOService`

As the name implies this is one of the services in `FFWServiceLoader`. It handles everything to do with creating and
destroying `FormatContainer`s, acting as the read-write interface for interacting with the raw files.\
Only contains the `read(File)` and `write(File, FormatContainer)` methods,
with an optional `castRead(File, Class<?>)` method for direct File > Object conversion.

This does nothing with data in the file, instead `FormatContainer` handles that.

#### `FormatContainer`

This is not a service itself, and is referred to from both `FormatReader` and `FormatConversion`.\
This class does not contain any usable methods for getting data from it, instead its meant to extend and implement specific methods.

Here are some of the existing extensions of this class:

* `AbstractKeyedContainer`
    * Only Properties extends this as of now. Key-Value data types.
    * These can usually do a one-way conversion to a `AbstractNestedContainer`.
* `AbstractNestedContainer`
    * TOML, YAML, YML, JSON, XML, etc. Data types that are still Key-Value but allow for nested data.
    * These can be easily converted between each-other.
* `AbstractPlainContainer`
    * Only extend by TXT and BIN files.

## Why FFW4J?

FFW4J is a fork of 1D6's original file format mini-library, but it far outgrew 1D6 itself. Instead it was pushed into
its own library and maintained separately due to its usefulness outside of a niche game engine.
The library was originally created for 1D6's translation system, allowing for any file type to be converted to a
`TriMap<Locale, String, String>` (`TriMap` being another custom mini-library, now part of Ratils) and used as translation files.
For example, you could use a `.toml` file instead of a `.properties` or `.json` file for 1D6 translation files if you so choose.\
This comes with the downside that writing anything that takes in files may not work correctly due to each external
library's different way of handling data. Fortunately, FFW4J mostly fixes this issue, and reading any file is like
you're reading a heavily nested array or map.\
For example, GSON and TOML return using separate methods for grabbing data, so a method like `ReadableFile#getFloat()` has a direct parallel.
On the other hand, XML and Properties need to be casted or parsed into a different type to be returned by the same method,
maybe even throwing an error if something goes wrong.\
Say you intend for a `.json` input, but get in a `.plist` input. With FFW4J it will probably work, but consider setting
debugging it up as a test, you should probably be doing that anyways.\
The current goal is to make specific formats an afterthought, and make the global interface the new format.

## Legal

FFW4J is under **Apache 2.0**.

For those who want the dependencies as well:

- [Gson](https://github.com/google/gson) - Apache-2.0
- [Jackson](https://github.com/FasterXML/jackson) - Apache-2.0
- [LibGDX](https://github.com/libgdx/libgdx) - Apache-2.0
- [lombok](https://github.com/projectlombok/lombok) - MIT
- [slf4j](https://github.com/qos-ch/slf4j) - MIT
- [logback](https://github.com/qos-ch/logback) - GPL 2.1
- [Ratils](https://github.com/OneDSix/ratils) - MIT

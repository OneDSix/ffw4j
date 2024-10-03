# File Format Wrapper for Java (FFW4J)
A small wrapper around a few popular libraries and some internal methods that make file handling a little bit easier.

This project has 3 goals:

* Allow all files to be compatible with one another, allowing for conversion and compatibility between nearly any file type;
* Allow developers to forget about file parsing and the costs that come with it, and choose whats right for them;
* And complete both goals using a modular system of dependencies, allowing developers to pick and choose what they want from FFW4J

## What can FFW4J do?

FFW4J extends Jackson, Gson, Apache Commons, a heavy amount of imports from the `java.util` package, and a few smaller
libraries, to read, cast, and modify nearly any file format out there.\
All of these libraries are either GPL or Apache licensed, meaning FFW4J and all its dependencies are FOSS. FFW4J itself
is under **GPL-3.0**.

The end goal is for this library to take in nearly any file format in existence, parse it, and allow you to use it in
any JVM-based project you like.

Below is a table of the most common files you might be working with, and FFW4J can handle.

- :link: means it has to be converted to another type to be casted to an object.
- :large_orange_diamond: means that once converted, the data cannot be easily converted back.
- All files have plain text and plain value (JsonObject, Toml, Yaml, etc.) methods in case they are needed.
- Plain Text can return a `String` or `byte[]` instance, and can be modified using `FileOutputStream`, it just cant be parsed or modified as easily and doesnt implement the `ReadableFile` or `ModifyableFile` interfaces.

| File Type  | Read Content?      | Modify & Save Content?                                                                             | Casts via...               | Converts to...                                | Gets with...                                         | Lib Source              |
|------------|--------------------|----------------------------------------------------------------------------------------------------|----------------------------|-----------------------------------------------|------------------------------------------------------|-------------------------|
| JSON       | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :heavy_check_mark:         | :heavy_check_mark:, Prop, TOML, XML           | With Separate Methods                                | `com.google.gson`       |
| TOML       | :heavy_check_mark: | :x: ([workaround](https://github.com/mwanji/toml4j?tab=readme-ov-file#converting-objects-to-toml)) | :heavy_check_mark:         | :heavy_check_mark:, JSON                      | With Separate Methods                                | `com.moandjiezana.toml` |
| XML        | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, JSON > Class       | :heavy_check_mark:, JSON & Prop               | With a Single Method, returns `Object`               | `org.json`              |
| YAML       | :x:                | :x:                                                                                                | :heavy_check_mark:         | :x:                                           | With either Specified Class or `Map<Object, Object>` | `org.yaml.snakeyaml`    |
| CSV        | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, JSON > Class       | :large_orange_diamond:, JSON, Prop, TOML, XML | With a Single Method, returns `String`               | Internal Library        |
| Properties | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, JSON > Class       | :heavy_check_mark:, JSON & XML                | With a Single Method, returns `String`               | `java.util`             |
| Plain Text | :x:                | :x:                                                                                                | :x:                        | :x:                                           | N/A                                                  | `java.util`             |
| PList      | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, XML > JSON > Class | :large_orange_diamond:, XML                   | With a Single Method, returns `Object`               | `com.dd.plist`          |

Contained in FFW4J is a few extra file utilities, like bulk file moving, bulk zipping, a `getResourcesAsStream()` that isn't a one-liner mess, and more.
All of these are used internally by FFW4J itself, so

FFW4J makes heavy use of Java's SPI, meaning its very extendable, allowing for anyone to easily add their own custom file type.\
It also uses 1D6's ratils package, allowing for more concise internal returns.

## How can I use it?

First, add it to your project:

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

Next, here is some example code to get you started

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

For more complex operations, check the wiki [here](../../wiki)

## How does it work?

FFW4J is split into 3 portions:

* `FormatReaderService`s
* `FormatContainer`s
* and `FormatConversionService`s

### `FormatIOService`

As the name implies this is one of the services in `FFWServiceLoader`. It handles everything to do with creating and
destroying `FormatContainer`s, acting as the read-write interface for interacting with the raw files.\
Only contains the `read(File)` and `write(File, FormatContainer)` methods,
with an optional `castRead(File, Class<?>)` method for direct File > Object conversion.

This does nothing with data in the file, instead `FormatContainer` handles that.

### `FormatContainer`

This is not a service itself, and is referred to from both `FormatReader` and `FormatConversion`.\
This class does not contain any usable methods for getting data from it, instead its meant to extend and implement specific methods.

Here are some of the existing extensions of this class:

* AbstractKeyedContainer
  * Properties, CSV, and TXT extend. Only Key-Value data types.
* AbstractNestedContainer
  * TOML, YAML, YML, JSON, etc. Data types that are still Key-Value but allow for nested data.
* AbstractPlainContainer
  * Only extend by TXT and BIN files.

## Why FFW4J?







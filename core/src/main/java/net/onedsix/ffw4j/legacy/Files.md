# Files Explained

These are all of the files that 1D6's internal file handling library supports. This package allows any file type to be used in nearly anywhere,
be it for configs, game data (although using `com.esoerticsoftware.kyro.Kyro` or `net.onedsix.storage.database.GameDatabaseAccessor` is preferred for saving game data),
converting between files types, and even casting files to objects.

For example, you could use a `.toml` file instead of a `.properties` or `.json` file for translation files if you so choose.\
This comes with the downside that writing anything that takes in files may not work correctly due to each external library's different way of handling data.
For example, GSON and TOML return using separate methods for grabbing data, so a method like `ReadableFile#getFloat()` has a direct parallel.
On the other hand, XML and Properties need to be casted or parsed into a different type to be returned by the same method, maybe even throwing an error if something goes wrong.\
Say you intend for a `.json` input, but get in a `.plist` input. It will probably work, but get ready to debug every compatible file format.
Consider setting it up as a test, you should probably be doing that anyways.

All of these classes are available in the `net.onedsix.storage.files` package.

- :link: means it has to be converted to another type to be casted to an object.
- :large_orange_diamond: means that once converted, the data cannot be easily converted back.
- All files have plain text and plain value (JsonObject, Toml, Yaml, etc.) methods in case they are needed.
- Plain Text can return a `String` or `byte[]` instance, and can be modified using `FileOutputStream`, it just cant be parsed or modified as easily and doesnt implement the `ReadableFile` or `ModifyableFile` interfaces.
- Do not confuse `org.google.gson.JsonObject` (used for JSON files) and `org.json.JSONObject` (used for XML files), they are from completely different libraries.

| File Type  | Read Content?      | Modify & Save Content?                                                                             | Casts via...               | Converts to...                                | Gets with...                                         | Lib Source              |
|------------|--------------------|----------------------------------------------------------------------------------------------------|----------------------------|-----------------------------------------------|------------------------------------------------------|-------------------------|
| CSV        | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, JSON > Class       | :large_orange_diamond:, JSON, Prop, TOML, XML | With a Single Method, returns `String`               | Internal Library        |
| JSON       | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :heavy_check_mark:         | :heavy_check_mark:, Prop, TOML, XML           | With Separate Methods                                | `com.google.gson`       |
| PList      | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, XML > JSON > Class | :large_orange_diamond:, XML                   | With a Single Method, returns `Object`               | `com.dd.plist`          |
| Plain Text | :x:                | :x:                                                                                                | :x:                        | :x:                                           | N/A                                                  | `java.util`             |
| Properties | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, JSON > Class       | :heavy_check_mark:, JSON & XML                | With a Single Method, returns `String`               | `java.util`             |
| TOML       | :heavy_check_mark: | :x: ([workaround](https://github.com/mwanji/toml4j?tab=readme-ov-file#converting-objects-to-toml)) | :heavy_check_mark:         | :heavy_check_mark:, JSON                      | With Separate Methods                                | `com.moandjiezana.toml` |
| XML        | :heavy_check_mark: | :heavy_check_mark:                                                                                 | :link:, JSON > Class       | :heavy_check_mark:, JSON & Prop               | With a Single Method, returns `Object`               | `org.json`              |
| YAML       | :x:                | :x:                                                                                                | :heavy_check_mark:         | :x:                                           | With either Specified Class or `Map<Object, Object>` | `org.yaml.snakeyaml`    |

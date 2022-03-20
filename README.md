This tiny project can be added to your Spigot plugins to tell noob admins that they have to put the .jar file
into their plugins folder.

If they decide to run your .jar file directly, they will be shown this screen:

![](https://static.jeff-media.com/img/standalonepluginscreen.png)

## Usage
Shade the StandalonePluginScreen class into your plugin:

```xml
<repositories>
    <repository>
        <id>jeff-media-public</id>
        <url>https://hub.jeff-media.com/nexus/repository/jeff-media-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.jeff_media</groupId>
        <artifactId>StandalonePluginScreen</artifactId>
        <version>1.0.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

Now, create a file called `META-INF/MANIFEST.MF` in your resources directory with the following content:

```manifest
Main-Class: com.jeff_media.standalonepluginscreen.StandalonePluginScreen

```

**Important:** Your MANIFEST.MF file **must** end with a new line or carriage return.
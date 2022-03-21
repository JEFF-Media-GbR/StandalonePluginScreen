This tiny project can be added to your Spigot plugins to tell noob admins that they have to put the .jar file
into their plugins folder.

If they decide to run your .jar file directly, they will be shown this screen:

![](https://static.jeff-media.com/img/standalonepluginscreen.png)

Your plugin name and version will be read automatically from your plugin.yml file.

## Todo

1. Make message configurable using a resource file

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

Now you just have to declare the StandalonePluginScreen class to be the main class in your MANIFEST.MF file:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <archive>
            <manifest>
                <mainClass>com.jeff_media.standalonepluginscreen.StandalonePluginScreen</mainClass>
            </manifest>
        </archive>
    </configuration>
</plugin>
```

If you're using the shade plugin's `minimizeJar` option, you also have to include the artifact in a filter:

```xml
<filter>
    <artifact>com.jeff_media:StandalonePluginScreen</artifact>
    <includes>
        <include>**</include>
    </includes>
</filter>
```

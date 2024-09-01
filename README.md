# BunnyAPI

Die BunnyAPI unterstützt Entwickler bei der Entwicklung indem Sie die verwaltung von Daten vereinfacht. Die API bietet eine unkomplizierte Schnittstelle für Aufgaben im Bereich Datenmanagement.

## Installation
### Gradle
````
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.Balanceakt:DBCenter:Tag'
}
````
### Maven
````
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Balanceakt</groupId>
    <artifactId>BunnyAPI</artifactId>
    <version>NEUSTE VERSION</version>
</dependency>
````
## Java Beispielcode:

### BunnyAPI Instance
Statische Methode um die Singleton-Instanz der Klasse zu erhalten:
````java
BunnyAPI.Companion.getInstance()
````
# BunnyAPI

Die BunnyAPI unterstützt Entwickler bei der Entwicklung indem Sie die verwaltung von Daten vereinfacht. Die API bietet
eine unkomplizierte Schnittstelle für Aufgaben im Bereich Datenmanagement.

## Installation

### gradle

````
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.Balanceakt:BunnyAPI:Tag'
	}
````

### maven

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
	    <version>Tag</version>
	</dependency>
````

### sbt

````
    resolvers += "jitpack" at "https://jitpack.io"

	libraryDependencies += "com.github.Balanceakt" % "BunnyAPI" % "Tag"	
````

### leiningen

````
    :repositories [["jitpack" "https://jitpack.io"]]

	:dependencies [[com.github.Balanceakt/BunnyAPI "Tag"]]	
````

## Java Beispielcode:

### BunnyAPI Instance

Statische Methode um die Singleton-Instanz der Klasse zu erhalten:

````java
BunnyAPI.Companion.getInstance()
````
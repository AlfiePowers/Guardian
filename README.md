# Guardian Bot
Guardian is a Discord Music bot based off of JDA 3.0 API with support for most audio formats through Lavaplayer. Furthermore, 
Guardian has the ability to fetch any post from Reddit and to format it correctly. 

# Compiling the Source-code:
To compile the source code, you will need to have a IDE that can download a Libary via Maven, or have the original binary files. If
you wish to use Maven, the .pom file included will supply you with the required downloads for the Source code to be compiled. However,
if this is not included for any reason, you can download the API via these repositories / dependancies:
```
<dependencies>
  <dependency>
    <groupId>net.dv8tion</groupId>
    <artifactId>JDA</artifactId>
    <version>2.2.1_353</version>
  </dependency>
  <dependency>
    <groupId>ca.pjer</groupId>
    <artifactId>chatter-bot-api</artifactId>
    <version>1.4.2</version>
  </dependency>
</dependencies>
```

And, in the repository section, add:
```
<repositories>
  <repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>
```

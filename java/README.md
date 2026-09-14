# bloomy-yearbook-lambda

Create a simple Maven project on your computer
Create a folder called bloomy-yearbook-lambda and inside it create this structure:
textbloomy-yearbook-lambda/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── Application.java
pom.xml (copy exactly):
XML<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.bloomy</groupId>
    <artifactId>yearbook-lambda</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-lambda-java-core</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-lambda-java-events</artifactId>
            <version>3.11.4</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <createDependencyReducedPom>false</createDependencyReducedPom>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
Application.java → paste the full class I gave you earlier (the one with the complete HTML).
2. Build the JAR
Open a terminal in the project folder and run:
Bashmvn clean package
You will get a file here:

target/yearbook-lambda-1.0.0.jar
This is the file you will upload to Lambda.

3. Create the Lambda function in AWS Console

Go to AWS Lambda → Create function
Choose Author from scratch
Function name: bloomy-yearbook
Runtime: Java 17 or Java 21
Architecture: x86_64
Click Create function

4. Upload the code

In the Lambda function page → Code tab
Click Upload from → .zip or .jar file
Select the yearbook-lambda-1.0.0.jar you built
Click Save

5. Set the Handler

Go to Runtime settings → Edit
Handler:textApplication::handleRequest
Click Save

6. Create the public URL (API Gateway)

In the Lambda function page, go to the Configuration tab → Triggers
Click Add trigger
Select API Gateway
Choose:
Create a new API
API type: HTTP API
Security: Open (for easy testing)

Click Add

After a few seconds you will see an API endpoint URL (something like https://xxxxxx.execute-api.region.amazonaws.com).

7. Test it
Open the API endpoint URL in your browser.

You should see the full Bloomy Technologies Class of 2026 Yearbook page.

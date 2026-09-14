Here’s a clean, well-formatted `README.md`:

```markdown
# Bloomy Yearbook Lambda project

A simple AWS Lambda function that serves the **Bloomy Technologies – Class of 2026 Yearbook** page.

---

## 1. Create the Project

Create a folder called `bloomy-yearbook-lambda` with the following structure:

```text
bloomy-yearbook-lambda/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── Application.java
```

### `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
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
```

### `Application.java`

Paste the full Lambda-ready Java class (the one containing the complete HTML yearbook page) into:

```
src/main/java/Application.java
```

---

## 2. Build the JAR

Open a terminal in the project folder and run:

```bash
mvn clean package
```

After a successful build you will find the deployment artifact at:

```
target/yearbook-lambda-1.0.0.jar
```

This is the file you will upload to AWS Lambda.

---

## 3. Create the Lambda Function (AWS Console)

1. Go to **AWS Lambda** → **Create function**
2. Choose **Author from scratch**
3. Configure:
   - **Function name**: `bloomy-yearbook`
   - **Runtime**: Java 17 or Java 21
   - **Architecture**: `x86_64`
4. Click **Create function**

---

## 4. Upload the Code

1. In the Lambda function page, open the **Code** tab
2. Click **Upload from** → **.zip or .jar file**
3. Select `yearbook-lambda-1.0.0.jar`
4. Click **Save**

---

## 5. Set the Handler

1. Go to **Runtime settings** → **Edit**
2. Set the **Handler** to:

   ```
   Application::handleRequest
   ```

3. Click **Save**

---

## 6. Create a Public URL (API Gateway)

1. In the Lambda function page, go to the **Configuration** tab → **Triggers**
2. Click **Add trigger**
3. Select **API Gateway**
4. Configure:
   - Create a new API
   - API type: **HTTP API**
   - Security: **Open** (for easy testing)
5. Click **Add**

After a few seconds you will see an **API endpoint** URL  
(example: `https://xxxxxx.execute-api.region.amazonaws.com`)

---

## 7. Test It

Open the API endpoint URL in your browser.  
You should see the full **Bloomy Technologies Class of 2026 Yearbook** page.
```

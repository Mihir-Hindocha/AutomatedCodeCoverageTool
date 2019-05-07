# AutomatedCodeCoverageTool
SE6367 Software Testing, Validation and Verfication Course Project.

Instructions to run the project:-

Download the repository and go to project folder for the one you want to run: 
<br>-> Statement coverage (Complete). 
<br>-> Statement Coverage and Invariant.

When you are in the folder with pom.xml open cmd and run the following commands:
1. mvn clean
2. mvn install

To check the statement coverage for commons-dbutils download the current version of the project from https://github.com/apache/commons-dbutils.

Replace the surefire < plugin > from the < build >< plugins > in pom.xml of commons-dbutils with the following:
<br>**(include absolute path to the statement-coverage-1.0-SNAPSHOT.jar created in target folder of the project after mvn install command)**
<br> Example: " C:\user\Desktop\Statement Coverage\target\statement-coverage-1.0-SNAPSHOT.jar "

``` 
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
      <properties>
        <property>
          <name>listener</name>
          <value>edu.utdallas.JUnitListener</value>
        </property>
      </properties>
      <argLine>-javaagent:"{absolute path to the jar file in target folder}\statement-coverage-1.0-SNAPSHOT.jar"</argLine>
      <forkCount>2</forkCount>
    </configuration>
</plugin>
```      

After adding the plugin to the pom.xml of commons-dbutils,
**Run mvn test** on the project to calculate the code coverage automatically.

A file named "stmt-cov.txt" will be created in the root directory of the project folder providing a detailed report of the statement coverage. 

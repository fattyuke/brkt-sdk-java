The **Bracket Java SDK** is a Java library for interacting with the Bracket
REST API.

To install the Java SDK, clone the git repo and run the Maven build:

    ~/test$ git clone git@github.com:brkt/brkt-sdk-java.git
    Cloning into 'brkt-sdk-java'...
    ...

    ~/test$ cd brkt-sdk-java/

    ~/test/brkt-sdk-java (master)$ mvn install
    ...
    [INFO] Building brkt-sdk-java 1.0-SNAPSHOT
    ...
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 5.924 s
    [INFO] Finished at: 2015-06-18T17:27:46-07:00
    [INFO] Final Memory: 30M/310M
    [INFO] ------------------------------------------------------------------------

The build places the JAR files in the `target` directory.  For more details and
sample code, take a look at the [Javadoc](http://brkt.github.io/brkt-sdk-java/).

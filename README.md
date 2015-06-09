The **Bracket Java SDK** is a Java library for interacting with the Bracket
REST API.

To install the Java SDK, clone the git repo and run the Maven build. The
build places the JAR and Javadoc in the `target` directory.  **Note that
the git URL will change once we move the SDK to a public repo.**

    ~/test$ git clone git@github.int.brkt.com:boris/brkt-java-sdk.git
    Cloning into 'brkt-java-sdk'...
    remote: Counting objects: 917, done.
    remote: Compressing objects: 100% (428/428), done.
    remote: Total 917 (delta 532), reused 717 (delta 333)
    Receiving objects: 100% (917/917), 300.17 KiB | 0 bytes/s, done.
    Resolving deltas: 100% (532/532), done.
    Checking connectivity... done.

    ~/test$ cd brkt-java-sdk/

    ~/test/brkt-java-sdk (master)$ mvn install
    ...
    [INFO] Building brkt-java-sdk 1.0-SNAPSHOT
    ...
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 7.048 s
    [INFO] Finished at: 2015-06-09T11:33:58-07:00
    [INFO] Final Memory: 17M/81M
    [INFO] ------------------------------------------------------------------------

For more details and sample code, take a look at the [Javadoc](https://github.int.brkt.com/pages/boris/brkt-java-sdk/).

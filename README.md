Description
===========

Fraud detection project for credit card authorization using GigaSpaces XAP. This application demonstrates how to build a scalable credit card fraud detection application on top of GigaSpaces XAP, using XAP's in-memory event processing and data access capabilities. More details can be found in [this slide deck](http://www.slideshare.net/uri1803/gigaspaces-xap-dont-call-me-cache-java-version-sept-10). 

The project consists of few modules:
* `common` - contains POJOs which are common to the other modules.
* `feeder` - creates payment transactions.
* `user` - contains functionality for payment validation.
* `vendor` - contains functionality for vendor validation.

Requirements
============

JDK 1.6 or higher, Maven, XAP

How to run
==========
* Download and install XAP. [This page](http://wiki.gigaspaces.com/wiki/display/XAP96/Installing+GigaSpaces) will get you started. 
* Clone this repository to your local drive. 
* Start XAP agent using
```
<XAP HOME>/bin/gs-agent.sh
```
* Package the project: `mvn package`
* Open a command line termianl and cd to the directory into which you cloned the repo. 
* Assuming you have the [XAP Maven plugin](http://www.gigaspaces.com/wiki/display/XAP96/Maven+Plugin) installed, you can deploy the project to XAP using the following maven command: 
```
mvn os:deploy -Dlocators=localhost:4174
```
Otherwise you can deploy to XAP using [one of the other deployment options](http://wiki.gigaspaces.com/wiki/display/XAP96/Deploying+and+Running+the+Processing+Unit). 


### Some more details on building, packaging and deploying

Quick list:

* `mvn compile`: Compiles the project.
* `mvn os:run`: Runs the project.
* `mvn test`: Runs the tests in the project.
* `mvn package`: Compiles and packages the project.
* `mvn os:run-standalone`: Runs a packaged application (from the jars).
* `mvn os:deploy`: Deploys the project onto the Service Grid.
* `mvn os:undeploy`: Removes the project from the Service Grid.

In order to build the example, a simple `mvn compile` executed from the root of the project will compile all the different modules.

Packaging the application can be done using `mvn package` (note, by default, it also runs the tests, in order to disable it, use `-DskipTests`). The packaging process jars up the common module. The feeder, vendor and user modules packaging process creates a "processing unit structure" directory within the target directory. It also creates a jar from the mentioned directory. 

In order to simply run both the processor and the feeder (after compiling), `mvn os:run` can be used. This will run a single instance of each processing unit within the same JVM using the compilation level classpath (no need for packaging). A specific module can also be executed by itself, which in this case, executing more than one instance of the processing unit can be done. For example, running the vendor module with a cluster topology of 2 partitions, each with one backup, the following command can be used:
```
mvn os:run -Dmodule=vendor -Dcluster="total_members=2,1".
```

In order to run a packaged processing unit, `mvn package os:run-standalone` can be used (if `mvn package` was already executed, it can be omitted). This operation will run the processing units using the packaged jar files. Running a specific module with a cluster topology can be executed using:
```
mvn package os:run-standalone -Dmodule=processor -Dcluster="total_members=2,1".
```

Deploying the application requires starting up a GSM and at least 2 GSCs (this can be done by simply launching the `bin/gs-agent.sh(bat)` script. Once started, running `mvn package os:deploy` will deploy all the processing units. When deploying, the SLA elements within each processing unit descriptor 
(`pu.xml`) are taken into account. This means that by default when deploying the application, 2 partitions, each with one backup will be created for the processor, and a single instance of the feeder will be created. 

A special note regarding lookup groups and deployment: If the GSM and GSCs were started under a specific group, `-Dgroups=[group-name]` will need to be used in the deploy command.

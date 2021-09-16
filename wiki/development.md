# Development guide

## Requirements
- maven
```Setup maven settings
...
          <proxy>
             <id>bosch</id>
             <active>true</active>
             <protocol>http</protocol>
             <host>127.0.0.1</host>
             <port>3128</port><!--use cntlm-->
             <nonProxyHosts>localhost|127.0.0.1|rb-artifactory.bosch.com</nonProxyHosts>
          </proxy>
...
```

## Application Setup
The application can act as a consumer or as a producer.
The springboot profiles consumer and producer are available for this purpose.
```Example
    java -jar  -Dspring.profiles.active=dev,consumer ./target/solace-amqp-sample-0.0.1-SNAPSHOT.jar
```
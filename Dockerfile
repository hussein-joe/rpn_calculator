FROM openjdk:8-jdk-alpine
ADD target/rpn-calculator.jar rpn-calculator.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rpn-calculator.jar"]
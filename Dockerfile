FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar vacation_pay_app.jar
ENTRYPOINT ["java","-jar","/vacation_pay_app.jar"]
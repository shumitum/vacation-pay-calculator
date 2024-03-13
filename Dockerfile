FROM amazoncorretto:17-alpine-jdk AS builder
WORKDIR /opt/vacation_pay_app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM amazoncorretto:17-alpine-jdk
WORKDIR /opt/app
COPY --from=builder /opt/vacation_pay_app/target/*.jar /opt/vacation_pay_app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/vacation_pay_app/*.jar"]
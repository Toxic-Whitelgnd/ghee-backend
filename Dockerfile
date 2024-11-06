#Stage 1 build
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#Stage 2 run dynamically
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ghee-0.0.1-SNAPSHOT.jar ghee.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","ghee.jar"]
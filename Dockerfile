
# AS <NAME> to name this stage as maven
FROM maven:latest AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package -DskipTests

# For Java 17, 
FROM amazoncorretto:17

ARG JAR_FILE=subzeroqr_backend.jar

WORKDIR /opt/app

# Copy the subzeroqr_backend.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","subzeroqr_backend.jar"]
# Use the official OpenJDK image from the Docker Hub as the base image
# https://hub.docker.com/layers/library/openjdk/24/images/sha256-b35661af838fd678573ed7a908915ece18eba919fd9cdfe66ef6521a68a46133?context=explore
FROM openjdk:24

# Set the working directory inside the container to /app
WORKDIR /app

# Copy the JAR file from the target directory of your local machine to the working directory inside the container
# ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar is the path on your local machine
# . (dot) is the path inside the container, which refers to the current working directory (/app)
COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

# Expose port 8001 to allow external access to the application running inside the container
EXPOSE 8001

# Set the entry point to run the JAR file using the java command
ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

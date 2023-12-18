FROM eclipse-temurin:17-jdk-alpine
ARG STAGE="development"
LABEL org.opencontainers.image.title="BenchScape Frontend"
LABEL org.opencontainers.image.description="Frontend image for BenchScape"
LABEL org.opencontainers.image.url="https://app.integration.benchscape.cloud"
LABEL org.opencontainers.image.source="https://github.com/OpenElements/BenchScape"
LABEL org.opencontainers.image.authors="Open Elements"
LABEL stage=${STAGE}
VOLUME /tmp
ARG JAR_FILE=target/benchscape-server.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
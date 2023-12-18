FROM eclipse-temurin:17-jdk-alpine
ARG STAGE="development"
LABEL org.opencontainers.image.title="BenchScape Frontend"
LABEL org.opencontainers.image.description="Frontend image for BenchScape"
LABEL org.opencontainers.image.url="https://app.integration.benchscape.cloud"
LABEL org.opencontainers.image.source="https://github.com/OpenElements/BenchScape"
LABEL org.opencontainers.image.authors="Open Elements"
LABEL stage=${STAGE}
VOLUME /tmp
COPY target/benchscape-server.jar app.jar
ENV SPRING_PROFILES_ACTIVE=${STAGE}
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
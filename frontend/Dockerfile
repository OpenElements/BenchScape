FROM nginx:1.25.3
ARG STAGE="development"
LABEL org.opencontainers.image.title="BenchScape Frontend"
LABEL org.opencontainers.image.description="Frontend image for BenchScape"
LABEL org.opencontainers.image.url="https://app.integration.benchscape.cloud"
LABEL org.opencontainers.image.source="https://github.com/OpenElements/BenchScape"
LABEL org.opencontainers.image.authors="Open Elements"
LABEL stage=${STAGE}
COPY build /usr/share/nginx/html/
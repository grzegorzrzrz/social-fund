# syntax=docker/dockerfile:1.4

FROM openjdk:8 AS app-build
EXPOSE 8000
WORKDIR /app
COPY . /app
CMD javac app/App.java


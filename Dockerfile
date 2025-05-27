FROM openjdk:21
LABEL authors="Ingo"
EXPOSE 8080
COPY backend/target/webshop-app.jar /webshop-app.jar
ENTRYPOINT ["java","-jar","/webshop-app.jar"]
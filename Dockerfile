FROM eclipse-temurin:17-jdk-alpine
COPY build/libs/*.jar app.jar
ENTRYPOINT exec java $JAVA_OPTS -Dserver.port=$PORT -Ddb.url=$DB_URL -Ddb.username=$DB_USER -Ddb.password=$DB_PAS -jar /app.jar
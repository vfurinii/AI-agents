# Dockerfile para Camarim POA Assistant

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia código fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Cria usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia JAR do stage de build
COPY --from=build /app/target/camarim-agent-1.0.0-SNAPSHOT.jar app.jar

# Cria diretório para banco de dados
RUN mkdir -p /app/data

# Expõe porta
EXPOSE 8082

# Variáveis de ambiente
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV OPENAI_API_KEY=""
ENV OPENAI_MODEL="gpt-4o-mini"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8082/api/chat/health || exit 1

# Executa aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


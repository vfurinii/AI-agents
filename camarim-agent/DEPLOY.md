# 🚀 Guia de Deploy - Camarim POA Assistant

## Opções de Deploy

### 1. Deploy Local (Desenvolvimento)

#### Pré-requisitos
- Java 17+
- Maven 3.6+
- OpenAI API Key

#### Passos

```bash
# 1. Clone ou navegue até o projeto
cd camarim-agent

# 2. Configure a API Key
export OPENAI_API_KEY="sk-your-key-here"

# 3. Execute
./run.sh
```

Acesse: http://localhost:8082/index.html

---

### 2. Deploy com Docker

#### Build e Run Manual

```bash
# 1. Build da imagem
docker build -t camarim-agent .

# 2. Run do container
docker run -d \
  --name camarim-assistant \
  -p 8082:8082 \
  -e OPENAI_API_KEY="sk-your-key-here" \
  -v $(pwd)/data:/app/data \
  camarim-agent
```

#### Com Docker Compose (Recomendado)

```bash
# 1. Criar arquivo .env
cp .env.example .env
# Edite .env e adicione sua OPENAI_API_KEY

# 2. Iniciar
docker-compose up -d

# 3. Ver logs
docker-compose logs -f

# 4. Parar
docker-compose down
```

---

### 3. Deploy em Cloud (AWS, GCP, Azure)

#### AWS Elastic Beanstalk

```bash
# 1. Instalar EB CLI
pip install awsebcli

# 2. Inicializar
eb init -p docker camarim-agent

# 3. Criar ambiente
eb create camarim-production

# 4. Configurar variáveis de ambiente
eb setenv OPENAI_API_KEY=sk-your-key-here

# 5. Deploy
eb deploy

# 6. Abrir aplicação
eb open
```

#### Google Cloud Run

```bash
# 1. Build e push da imagem
gcloud builds submit --tag gcr.io/[PROJECT-ID]/camarim-agent

# 2. Deploy
gcloud run deploy camarim-agent \
  --image gcr.io/[PROJECT-ID]/camarim-agent \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated \
  --set-env-vars OPENAI_API_KEY=sk-your-key-here
```

#### Azure Container Instances

```bash
# 1. Login
az login

# 2. Criar resource group
az group create --name camarim-rg --location eastus

# 3. Criar container registry
az acr create --resource-group camarim-rg \
  --name camarimregistry --sku Basic

# 4. Build e push
az acr build --registry camarimregistry \
  --image camarim-agent:latest .

# 5. Deploy
az container create \
  --resource-group camarim-rg \
  --name camarim-agent \
  --image camarimregistry.azurecr.io/camarim-agent:latest \
  --dns-name-label camarim-assistant \
  --ports 8082 \
  --environment-variables OPENAI_API_KEY=sk-your-key-here
```

---

### 4. Deploy em Heroku

```bash
# 1. Login
heroku login

# 2. Criar app
heroku create camarim-assistant

# 3. Configurar buildpack
heroku buildpacks:set heroku/java

# 4. Configurar variáveis
heroku config:set OPENAI_API_KEY=sk-your-key-here

# 5. Deploy
git push heroku main

# 6. Abrir
heroku open
```

Criar arquivo `Procfile`:
```
web: java -jar target/camarim-agent-1.0.0-SNAPSHOT.jar
```

---

### 5. Deploy em Railway

1. Acesse https://railway.app
2. Conecte seu repositório GitHub
3. Configure variáveis de ambiente:
   - `OPENAI_API_KEY`
4. Deploy automático!

---

### 6. Deploy em Render

1. Acesse https://render.com
2. New > Web Service
3. Conecte repositório
4. Configure:
   - Build Command: `mvn clean package`
   - Start Command: `java -jar target/camarim-agent-1.0.0-SNAPSHOT.jar`
5. Adicione variável `OPENAI_API_KEY`
6. Deploy!

---

## Configurações de Produção

### application-prod.yml

Crie `src/main/resources/application-prod.yml`:

```yaml
server:
  port: ${PORT:8082}
  compression:
    enabled: true
    mime-types: application/json,text/html,text/css,application/javascript

spring:
  datasource:
    url: jdbc:h2:file:/data/camarim_prod
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update

logging:
  level:
    root: INFO
    com.camarim.agent: INFO
  file:
    name: /var/log/camarim-agent.log

openai:
  api-key: ${OPENAI_API_KEY}
  model: ${OPENAI_MODEL:gpt-4o-mini}
```

Execute com:
```bash
java -jar app.jar --spring.profiles.active=prod
```

---

## Segurança

### 1. HTTPS (Obrigatório em Produção)

#### Com Nginx

```nginx
server {
    listen 443 ssl;
    server_name camarim.exemplo.com;
    
    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;
    
    location / {
        proxy_pass http://localhost:8082;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

#### Com Let's Encrypt

```bash
# Instalar certbot
sudo apt-get install certbot python3-certbot-nginx

# Obter certificado
sudo certbot --nginx -d camarim.exemplo.com
```

### 2. Autenticação API (Opcional)

Adicione ao `application.yml`:

```yaml
spring:
  security:
    user:
      name: admin
      password: ${API_PASSWORD}
```

### 3. Rate Limiting

Adicione dependência no `pom.xml`:

```xml
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.6.0</version>
</dependency>
```

---

## Monitoramento

### 1. Spring Boot Actuator

Adicione ao `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

No `application.yml`:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
```

Acesse: `http://localhost:8082/actuator/health`

### 2. Prometheus + Grafana

```yaml
# docker-compose.yml
services:
  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
      
  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
```

---

## Backup

### Backup do Banco de Dados

```bash
#!/bin/bash
# backup.sh

DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/backups"

# Criar backup
cp data/camarim.mv.db "$BACKUP_DIR/camarim_$DATE.mv.db"

# Manter últimos 7 dias
find $BACKUP_DIR -name "camarim_*.mv.db" -mtime +7 -delete
```

### Restaurar Backup

```bash
# Parar aplicação
docker-compose down

# Restaurar
cp /backups/camarim_YYYYMMDD.mv.db data/camarim.mv.db

# Reiniciar
docker-compose up -d
```

---

## Performance

### Otimizações JVM

```bash
java -Xmx512m -Xms256m \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+UseStringDeduplication \
  -jar app.jar
```

### Connection Pool

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
```

---

## Troubleshooting

### Logs

```bash
# Docker
docker logs -f camarim-assistant

# Local
tail -f logs/spring.log

# Heroku
heroku logs --tail
```

### Health Check

```bash
curl http://localhost:8082/api/chat/health
```

### Verificar Memória

```bash
docker stats camarim-assistant
```

---

## Checklist de Deploy

- [ ] Variável `OPENAI_API_KEY` configurada
- [ ] Banco de dados com backup configurado
- [ ] HTTPS habilitado
- [ ] Logs configurados
- [ ] Monitoramento ativo
- [ ] Rate limiting (se necessário)
- [ ] Variáveis de ambiente documentadas
- [ ] Health check funcionando
- [ ] Performance testada
- [ ] Documentação atualizada

---

## Contatos e Suporte

- **Desenvolvedor**: [seu-email]
- **Instagram**: @camarim.poa
- **Documentação**: [PROJETO.md](PROJETO.md)

---

**Última atualização**: Março 2026


# 🎯 Quick Reference - Camarim POA Assistant

## Comandos Rápidos

### Executar a Aplicação
```bash
cd camarim-agent
export OPENAI_API_KEY="sua-chave-aqui"
./run.sh
```

### URLs Importantes
- Interface Web: http://localhost:8082/index.html
- API: http://localhost:8082/api/chat
- Health Check: http://localhost:8082/api/chat/health
- H2 Console: http://localhost:8082/h2-console

### Testar a API
```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Olá!"}'
```

## Configuração Rápida

### application.yml
```yaml
openai:
  api-key: ${OPENAI_API_KEY}
  model: gpt-4o-mini

camarim:
  store:
    name: "Camarim POA"
    instagram: "@camarim.poa"
```

## Estrutura de Pastas

```
camarim-agent/
├── src/main/java/com/camarim/agent/
│   ├── controller/     # API REST endpoints
│   ├── service/        # Lógica de negócio
│   ├── model/          # Entidades JPA
│   └── repository/     # Acesso a dados
├── src/main/resources/
│   ├── application.yml # Configurações
│   └── static/         # Frontend
├── data/               # Banco de dados H2
└── pom.xml            # Dependências Maven
```

## Principais Classes

| Classe | Responsabilidade |
|--------|------------------|
| `CamarimAgentService` | Orquestra o fluxo de conversação |
| `OpenAiService` | Integração com OpenAI API |
| `MemoryService` | Gerencia histórico de conversas |
| `ChatController` | Endpoints REST da API |
| `Conversation` | Entidade JPA para persistência |

## API Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/chat` | Envia mensagem |
| DELETE | `/api/chat/session/{id}` | Limpa sessão |
| GET | `/api/chat/session/{id}/count` | Conta mensagens |
| GET | `/api/chat/health` | Health check |

## Troubleshooting

### Erro: "OPENAI_API_KEY não definida"
```bash
export OPENAI_API_KEY="sk-..."
```

### Erro: "Port 8082 already in use"
Edite `application.yml` e mude a porta:
```yaml
server:
  port: 8083
```

### Ver logs em tempo real
```bash
tail -f logs/spring.log
```

### Limpar banco de dados
```bash
rm -rf data/camarim.mv.db
```

## Customização do Prompt

Edite `OpenAiService.java`, método `getSystemPrompt()`:
```java
private String getSystemPrompt() {
    return """
        Você é a assistente virtual da Camarim POA...
        [seu prompt aqui]
    """;
}
```

## Variáveis de Ambiente

| Variável | Obrigatória | Padrão | Descrição |
|----------|-------------|--------|-----------|
| `OPENAI_API_KEY` | ✅ Sim | - | Chave API OpenAI |
| `OPENAI_MODEL` | ❌ Não | gpt-4o-mini | Modelo a usar |
| `SERVER_PORT` | ❌ Não | 8082 | Porta do servidor |

## Banco de Dados H2

### Acessar Console
1. http://localhost:8082/h2-console
2. JDBC URL: `jdbc:h2:file:./data/camarim`
3. User: `sa` | Password: (vazio)

### Queries Úteis
```sql
-- Ver todas as conversas
SELECT * FROM CONVERSATIONS ORDER BY TIMESTAMP DESC;

-- Contar mensagens por sessão
SELECT SESSION_ID, COUNT(*) as TOTAL 
FROM CONVERSATIONS 
GROUP BY SESSION_ID;

-- Últimas 10 conversas
SELECT * FROM CONVERSATIONS 
ORDER BY TIMESTAMP DESC 
LIMIT 10;
```

## Build & Deploy

### Compilar
```bash
mvn clean package
```

### Executar JAR
```bash
java -jar target/camarim-agent-1.0.0-SNAPSHOT.jar
```

### Docker
```bash
docker build -t camarim-agent .
docker run -p 8082:8082 -e OPENAI_API_KEY=sk-... camarim-agent
```

## Exemplos de Teste

### Teste Completo de Conversa
```bash
# 1. Primeira mensagem
RESPONSE=$(curl -s -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Olá!"}')

# 2. Extrair sessionId
SESSION_ID=$(echo $RESPONSE | jq -r '.sessionId')

# 3. Continuar conversa
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d "{\"sessionId\": \"$SESSION_ID\", \"message\": \"Quais produtos vocês têm?\"}"
```

## Performance

### Tempo de Resposta Esperado
- Primeira mensagem: 2-5 segundos
- Mensagens seguintes: 1-3 segundos

### Capacidade
- Sessões simultâneas: ~100
- Mensagens por minuto: ~500
- Banco de dados: Ilimitado (H2 file-based)

## Links Úteis

- [Documentação Completa](PROJETO.md)
- [Exemplos de API](API_EXAMPLES.md)
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [OpenAI API Docs](https://platform.openai.com/docs)


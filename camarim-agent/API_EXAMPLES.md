# Exemplos de Uso da API

## Conversação Básica

### 1. Primeira Mensagem (sem sessionId)

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Olá! Vocês têm vestidos?"
  }'
```

**Resposta:**
```json
{
  "response": "Olá! Sim, temos uma linda coleção de vestidos! 💕 Qual estilo você está procurando? Temos desde modelos mais casuais até peças para ocasiões especiais. Posso te ajudar a encontrar o vestido perfeito!",
  "sessionId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

### 2. Continuando a Conversa (com sessionId)

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "message": "Quais são os preços?"
  }'
```

### 3. Perguntando sobre Formas de Pagamento

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "message": "Quais formas de pagamento vocês aceitam?"
  }'
```

### 4. Perguntando sobre Envio

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "message": "Vocês fazem envio para outras cidades?"
  }'
```

### 5. Perguntando sobre Tamanhos

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "message": "Qual tamanho devo escolher? Uso 40"
  }'
```

## Gerenciamento de Sessão

### Verificar Número de Mensagens

```bash
curl http://localhost:8082/api/chat/session/a1b2c3d4-e5f6-7890-abcd-ef1234567890/count
```

**Resposta:**
```
10
```

### Limpar Histórico da Sessão

```bash
curl -X DELETE http://localhost:8082/api/chat/session/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

### Health Check

```bash
curl http://localhost:8082/api/chat/health
```

**Resposta:**
```
Camarim POA Assistant is running! 💕
```

## Testes com JavaScript (Browser)

```javascript
// Função para enviar mensagem
async function sendMessage(message, sessionId = null) {
  const response = await fetch('http://localhost:8082/api/chat', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      sessionId: sessionId,
      message: message
    })
  });
  
  return await response.json();
}

// Exemplo de uso
const result = await sendMessage("Olá! Vocês têm blusas?");
console.log(result.response);
console.log("Session ID:", result.sessionId);

// Continuar a conversa
const result2 = await sendMessage("Quais cores?", result.sessionId);
console.log(result2.response);
```

## Testes com Python

```python
import requests
import json

API_URL = "http://localhost:8082/api/chat"

# Primeira mensagem
response = requests.post(API_URL, json={
    "message": "Olá! Vocês têm calças jeans?"
})

data = response.json()
print(f"Assistente: {data['response']}")
session_id = data['sessionId']
print(f"Session ID: {session_id}")

# Continuar a conversa
response = requests.post(API_URL, json={
    "sessionId": session_id,
    "message": "Quanto custam?"
})

data = response.json()
print(f"Assistente: {data['response']}")

# Verificar número de mensagens
count_response = requests.get(f"{API_URL}/session/{session_id}/count")
print(f"Mensagens na sessão: {count_response.text}")

# Limpar sessão
requests.delete(f"{API_URL}/session/{session_id}")
print("Sessão limpa!")
```

## Cenários de Teste Recomendados

### 1. Consulta de Produtos
```json
{
  "message": "Vocês têm blusas de frio?"
}
```

### 2. Dúvida sobre Preços
```json
{
  "message": "Qual a faixa de preço dos vestidos?"
}
```

### 3. Informações sobre Loja Física
```json
{
  "message": "Onde fica a loja?"
}
```

### 4. Promoções
```json
{
  "message": "Vocês têm promoções?"
}
```

### 5. Disponibilidade
```json
{
  "message": "Esse modelo está disponível em outras cores?"
}
```

### 6. Troca e Devolução
```json
{
  "message": "Como funciona a troca?"
}
```

### 7. Informação Não Disponível
```json
{
  "message": "Vocês têm parceria com influenciadoras?"
}
```
*Neste caso, a assistente deve responder que irá verificar com a equipe*

## Monitoramento

### Verificar Logs
```bash
tail -f logs/camarim-agent.log
```

### Ver Banco de Dados
Acesse http://localhost:8082/h2-console

- JDBC URL: `jdbc:h2:file:./data/camarim`
- User: `sa`
- Password: (vazio)

Consulta SQL para ver conversas:
```sql
SELECT * FROM CONVERSATIONS ORDER BY TIMESTAMP DESC;
```

## Performance Testing

### Teste de Carga Simples (usando ab - Apache Bench)

```bash
# Instalar se necessário: brew install httpd (macOS)

# Criar arquivo com payload
echo '{"message": "Olá!"}' > payload.json

# Executar teste
ab -n 100 -c 10 -p payload.json -T application/json \
  http://localhost:8082/api/chat
```

### Teste com múltiplas sessões (script bash)

```bash
#!/bin/bash
for i in {1..10}
do
  curl -X POST http://localhost:8082/api/chat \
    -H "Content-Type: application/json" \
    -d "{\"message\": \"Teste $i\"}" &
done
wait
echo "Todos os testes concluídos!"
```


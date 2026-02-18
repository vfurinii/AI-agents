# 🧪 Testes da Integração WhatsApp

## 📋 Pré-requisitos

- [ ] Servidor Spring Boot rodando (`mvn spring-boot:run`)
- [ ] Variáveis de ambiente configuradas
- [ ] ngrok expondo o webhook (ou app em produção)
- [ ] Webhook configurado no Meta

## 🧪 Testes Locais

### 1. Teste de Verificação do Webhook

```bash
# Windows PowerShell
curl "http://localhost:8080/webhook/whatsapp?hub.mode=subscribe&hub.verify_token=seu-verify-token&hub.challenge=test123"

# Deve retornar: test123
```

### 2. Teste de Envio Manual

```bash
# Enviar mensagem de teste
curl -X POST "http://localhost:8080/webhook/whatsapp/send?to=5511999999999&message=Olá,%20teste!"
```

### 3. Simular Webhook do WhatsApp

```bash
# Windows PowerShell
curl -X POST http://localhost:8080/webhook/whatsapp `
  -H "Content-Type: application/json" `
  -d '{
    "object": "whatsapp_business_account",
    "entry": [{
      "id": "123456789",
      "changes": [{
        "value": {
          "messaging_product": "whatsapp",
          "metadata": {
            "display_phone_number": "15550000000",
            "phone_number_id": "123456789"
          },
          "contacts": [{
            "profile": { "name": "Teste" },
            "wa_id": "5511999999999"
          }],
          "messages": [{
            "from": "5511999999999",
            "id": "wamid.test123",
            "timestamp": "1234567890",
            "text": { "body": "Olá, chatbot!" },
            "type": "text"
          }]
        },
        "field": "messages"
      }]
    }]
  }'
```

## 🌐 Testes com ngrok

### 1. Iniciar ngrok

```bash
ngrok http 8080
```

Anote a URL: `https://abc123.ngrok.io`

### 2. Testar Verificação Remota

```bash
curl "https://abc123.ngrok.io/webhook/whatsapp?hub.mode=subscribe&hub.verify_token=seu-verify-token&hub.challenge=remote-test"

# Deve retornar: remote-test
```

### 3. Configurar no Meta

1. Acesse: https://developers.facebook.com/apps
2. Seu App → WhatsApp → Configuration
3. Edit Callback URL
4. Cole: `https://abc123.ngrok.io/webhook/whatsapp`
5. Verify Token: `seu-verify-token`
6. Verify and Save

## 📱 Teste Real no WhatsApp

### 1. Adicionar Número de Teste

No painel do Meta:
1. WhatsApp → API Setup
2. Seção "To"
3. Adicione seu número: `5511999999999`
4. Confirme no WhatsApp

### 2. Enviar Mensagem

1. Abra o WhatsApp no celular
2. Envie mensagem para o número Business
3. Aguarde resposta do chatbot

### Exemplos de Conversação:

```
Você: Olá!
Bot: Olá! Como posso ajudar você hoje?

Você: Qual é a capital do Brasil?
Bot: A capital do Brasil é Brasília.

Você: Me conte uma piada
Bot: [resposta criativa do GPT]
```

## 🔍 Verificar Logs

### Console do Spring Boot

```
INFO - Received message from 5511999999999: Olá!
INFO - Sent response to 5511999999999: Olá! Como posso ajudar?
```

### Verificar Database (Memória)

```bash
# Acesse H2 Console: http://localhost:8080/h2-console

# SQL Query:
SELECT * FROM messages ORDER BY timestamp DESC LIMIT 10;
```

## ❌ Troubleshooting

### Webhook não verifica

**Problema**: Retorna 403 Forbidden

**Solução**:
```bash
# Verifique se o token está correto
echo $env:WHATSAPP_VERIFY_TOKEN  # Windows
echo $WHATSAPP_VERIFY_TOKEN      # Linux/Mac

# Teste manualmente
curl "http://localhost:8080/webhook/whatsapp?hub.mode=subscribe&hub.verify_token=VALOR_CORRETO&hub.challenge=test"
```

### Mensagens não chegam

**Verificações**:
1. Logs do Spring Boot mostram webhook recebido?
2. Campo "messages" está ativo no Meta?
3. Número está na lista de testes?

```bash
# Simule webhook manualmente
curl -X POST http://localhost:8080/webhook/whatsapp -H "Content-Type: application/json" -d @webhook-sample.json
```

### Erro ao enviar resposta

**Problema**: 401 Unauthorized

**Solução**:
```bash
# Verifique access token
echo $env:WHATSAPP_ACCESS_TOKEN

# Gere novo token no Meta:
# App → WhatsApp → API Setup → Temporary access token
```

**Problema**: Número não autorizado

**Solução**: Adicione o número na lista de testes do Meta

### ngrok desconectou

**Problema**: Webhook parou de funcionar

**Solução**:
```bash
# 1. Reinicie ngrok
ngrok http 8080

# 2. Atualize URL no Meta
# Nova URL: https://xyz456.ngrok.io/webhook/whatsapp
```

## 📊 Monitoramento

### Logs do WhatsApp API

Acesse: https://developers.facebook.com/apps/YOUR_APP_ID/webhooks/

Veja:
- ✅ Webhooks entregues com sucesso
- ❌ Webhooks falhados
- 🔄 Tentativas de reenvio

### Logs do Servidor

```bash
# Acompanhar logs em tempo real
tail -f logs/spring-boot.log

# Ou no console do Spring Boot
mvn spring-boot:run
```

## ✅ Checklist de Testes

- [ ] Teste de verificação local passou
- [ ] Teste de envio manual funcionou
- [ ] ngrok está rodando e acessível
- [ ] Webhook verificado no Meta
- [ ] Campo "messages" ativado
- [ ] Número de teste adicionado
- [ ] Mensagem real enviada pelo WhatsApp
- [ ] Resposta recebida corretamente
- [ ] Logs mostram fluxo completo
- [ ] Memória/histórico funcionando

## 📝 Exemplo de Webhook Real

```json
{
  "object": "whatsapp_business_account",
  "entry": [
    {
      "id": "106157892476592",
      "changes": [
        {
          "value": {
            "messaging_product": "whatsapp",
            "metadata": {
              "display_phone_number": "15550000000",
              "phone_number_id": "106157892476593"
            },
            "contacts": [
              {
                "profile": {
                  "name": "João Silva"
                },
                "wa_id": "5511999999999"
              }
            ],
            "messages": [
              {
                "from": "5511999999999",
                "id": "wamid.HBgLNTU4NDAyNzU1OTkVAgARGBI5QTNDQTA5RkQ2MDYyMjlFRDUA",
                "timestamp": "1699999999",
                "text": {
                  "body": "Olá, preciso de ajuda!"
                },
                "type": "text"
              }
            ]
          },
          "field": "messages"
        }
      ]
    }
  ]
}
```

## 🎯 Comandos Úteis

```bash
# Ver logs do Spring Boot
mvn spring-boot:run

# Recompilar código
mvn clean install

# Reiniciar aplicação
Ctrl+C
mvn spring-boot:run

# Verificar porta 8080 em uso
netstat -ano | findstr :8080  # Windows
lsof -i :8080                  # Linux/Mac

# Matar processo na porta 8080
taskkill /PID <PID> /F         # Windows
kill -9 <PID>                  # Linux/Mac
```

---

**Documentação completa**: `WHATSAPP_INTEGRATION.md`  
**Quick Start**: `WHATSAPP_QUICKSTART.md`


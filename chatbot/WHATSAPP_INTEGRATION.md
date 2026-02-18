# 📱 Integração WhatsApp Business API

## 🎯 Visão Geral

Este chatbot está integrado com a **WhatsApp Business API** da Meta, permitindo que usuários conversem com o assistente AI diretamente pelo WhatsApp.

## 🏗️ Arquitetura

```
WhatsApp User → Meta WhatsApp API → Webhook → WhatsAppService → AgentService → OpenAI → Response → WhatsApp User
```

### Componentes Criados:

1. **WhatsAppWebhook.java** - DTO para receber webhooks do WhatsApp
2. **WhatsAppMessageRequest.java** - DTO para enviar mensagens
3. **WhatsAppService.java** - Lógica de processamento de mensagens
4. **WhatsAppWebhookController.java** - Endpoints REST para webhooks

## 🔧 Configuração

### 1. Criar App no Meta for Developers

1. Acesse: https://developers.facebook.com/apps
2. Clique em **"Create App"**
3. Selecione **"Business"** como tipo de app
4. Preencha os dados do app

### 2. Configurar WhatsApp Business

1. No painel do app, adicione o produto **"WhatsApp"**
2. Configure um número de telefone de teste (fornecido pela Meta)
3. Anote os seguintes valores:

   - **Phone Number ID**: ID do número de telefone
   - **Access Token**: Token de acesso temporário (depois gere um permanente)
   - **Verify Token**: Crie uma string aleatória (ex: `meu_token_secreto_123`)

### 3. Configurar Variáveis de Ambiente

Adicione as seguintes variáveis de ambiente:

#### Windows (PowerShell)
```powershell
$env:WHATSAPP_PHONE_NUMBER_ID="seu-phone-number-id"
$env:WHATSAPP_ACCESS_TOKEN="seu-access-token"
$env:WHATSAPP_VERIFY_TOKEN="seu-verify-token"
$env:OPENAI_API_KEY="sua-openai-key"
```

#### Linux/Mac
```bash
export WHATSAPP_PHONE_NUMBER_ID="seu-phone-number-id"
export WHATSAPP_ACCESS_TOKEN="seu-access-token"
export WHATSAPP_VERIFY_TOKEN="seu-verify-token"
export OPENAI_API_KEY="sua-openai-key"
```

#### Ou edite application.yml diretamente:
```yaml
whatsapp:
  phone-number-id: 123456789
  access-token: EAAxxxxxxxxxxxxx
  verify-token: meu_token_secreto_123
```

### 4. Expor Webhook Publicamente

O WhatsApp precisa acessar seu webhook via HTTPS. Use uma das opções:

#### Opção A: ngrok (Desenvolvimento)
```bash
# Instale ngrok: https://ngrok.com/download
ngrok http 8080
```

Você receberá uma URL como: `https://abc123.ngrok.io`

#### Opção B: Deploy em Cloud (Produção)
- **Heroku**: https://www.heroku.com
- **Railway**: https://railway.app
- **Google Cloud Run**: https://cloud.google.com/run
- **AWS EC2/ECS**: https://aws.amazon.com

### 5. Configurar Webhook no Meta

1. No painel do WhatsApp Business, vá em **"Configuration"**
2. Clique em **"Edit"** no campo Callback URL
3. Insira:
   - **Callback URL**: `https://sua-url.com/webhook/whatsapp`
   - **Verify Token**: O mesmo token que você configurou nas variáveis

4. Clique em **"Verify and Save"**

5. Em **"Webhook fields"**, ative a opção **"messages"**

## 🚀 Como Usar

### 1. Iniciar o Servidor

```bash
cd chatbot
mvn spring-boot:run
```

### 2. Testar Webhook (Opcional)

```bash
# Verificar webhook
curl "http://localhost:8080/webhook/whatsapp?hub.mode=subscribe&hub.verify_token=seu-verify-token&hub.challenge=test123"

# Enviar mensagem de teste
curl -X POST "http://localhost:8080/webhook/whatsapp/send?to=5511999999999&message=Olá%20teste"
```

### 3. Conversar pelo WhatsApp

1. No painel do Meta, adicione seu número de telefone na lista de **"Phone numbers"** de teste
2. Envie uma mensagem para o número do WhatsApp Business
3. O chatbot responderá automaticamente usando IA!

## 📋 Endpoints da API

### Webhook Verification (GET)
```
GET /webhook/whatsapp?hub.mode=subscribe&hub.verify_token=TOKEN&hub.challenge=CHALLENGE
```
- Usado pelo WhatsApp para verificar o webhook
- Retorna o challenge se o token estiver correto

### Receive Message (POST)
```
POST /webhook/whatsapp
Content-Type: application/json

{
  "object": "whatsapp_business_account",
  "entry": [...]
}
```
- Recebe mensagens do WhatsApp
- Processa automaticamente e responde

### Send Test Message (POST)
```
POST /webhook/whatsapp/send?to=5511999999999&message=Hello
```
- Endpoint manual para enviar mensagens de teste
- Útil para debug

## 🔍 Fluxo de Mensagens

### Mensagem Recebida:

1. **Usuário** envia mensagem pelo WhatsApp
2. **WhatsApp API** envia webhook para `/webhook/whatsapp`
3. **WhatsAppWebhookController** recebe e valida
4. **WhatsAppService** extrai texto da mensagem
5. **AgentService** processa com histórico de memória
6. **OpenAI API** gera resposta inteligente
7. **WhatsAppService** envia resposta via API
8. **Usuário** recebe resposta no WhatsApp

### Exemplo de Webhook Recebido:

```json
{
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
          "profile": { "name": "João" },
          "wa_id": "5511999999999"
        }],
        "messages": [{
          "from": "5511999999999",
          "id": "wamid.xxx",
          "timestamp": "1234567890",
          "text": { "body": "Olá!" },
          "type": "text"
        }]
      }
    }]
  }]
}
```

## 🛡️ Segurança

### Verificação de Webhook
- O endpoint GET valida o `verify_token` antes de confirmar o webhook
- Apenas requisições com token correto são aceitas

### HTTPS Obrigatório
- WhatsApp só aceita webhooks HTTPS em produção
- Use certificado SSL válido

### Access Token
- Mantenha o `access_token` secreto
- Use variáveis de ambiente, nunca hardcode
- Gere tokens permanentes (não temporários) para produção

## 📊 Logs e Monitoramento

O serviço registra logs detalhados:

```
INFO - Received message from 5511999999999: Olá!
INFO - Sent response to 5511999999999: Olá! Como posso ajudar?
ERROR - Error processing message from 5511999999999: Connection timeout
```

## 🐛 Troubleshooting

### Webhook não verifica
- ✅ Confirme que o `verify_token` está correto
- ✅ Verifique se a URL está acessível (ngrok/deploy)
- ✅ Teste manualmente: `curl https://sua-url/webhook/whatsapp?hub.mode=subscribe&hub.verify_token=TOKEN&hub.challenge=test`

### Mensagens não são recebidas
- ✅ Verifique logs do servidor
- ✅ Confirme que "messages" está ativo nos Webhook Fields
- ✅ Teste com número de telefone autorizado no Meta

### Não consegue enviar mensagens
- ✅ Verifique o `access_token` e `phone_number_id`
- ✅ Confirme que o número de destino está na lista de testes
- ✅ Verifique logs de erro para detalhes

### Erro 401 Unauthorized
- ✅ Token de acesso expirado - gere um novo token permanente
- ✅ Token incorreto nas variáveis de ambiente

## 🎨 Personalização

### Customizar Mensagens de Erro

Edite `WhatsAppService.java`:

```java
sendMessage(userPhone, "Sua mensagem de erro personalizada aqui!");
```

### Adicionar Comandos Especiais

```java
if (messageText.equalsIgnoreCase("/reset")) {
    memoryService.clear();
    sendMessage(userPhone, "Conversa resetada com sucesso!");
    return;
}
```

### Suporte Multi-usuário

Para manter memórias separadas por usuário, implemente:

```java
private final Map<String, MemoryService> userMemories = new HashMap<>();

private MemoryService getMemoryForUser(String phoneNumber) {
    return userMemories.computeIfAbsent(phoneNumber, k -> new MemoryService());
}
```

## 📚 Recursos Adicionais

- **WhatsApp Business API Docs**: https://developers.facebook.com/docs/whatsapp
- **Meta for Developers**: https://developers.facebook.com
- **Webhook Setup Guide**: https://developers.facebook.com/docs/whatsapp/cloud-api/guides/set-up-webhooks
- **Message Templates**: https://developers.facebook.com/docs/whatsapp/api/messages/message-templates

## ✅ Checklist de Configuração

- [ ] App criado no Meta for Developers
- [ ] WhatsApp Business adicionado ao app
- [ ] Phone Number ID obtido
- [ ] Access Token gerado
- [ ] Verify Token criado
- [ ] Variáveis de ambiente configuradas
- [ ] Servidor rodando em URL pública (HTTPS)
- [ ] Webhook configurado no Meta
- [ ] Webhook verificado com sucesso
- [ ] Campo "messages" ativado
- [ ] Número de teste adicionado
- [ ] Teste de mensagem realizado

## 🎉 Pronto!

Agora seu chatbot AI está integrado com o WhatsApp e pronto para conversar com usuários!

---

**Desenvolvido com ❤️ usando Spring Boot, OpenAI e WhatsApp Business API**


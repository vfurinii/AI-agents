# 🚀 WhatsApp Integration - Quick Start

## ⚡ Configuração Rápida (5 minutos)

### Passo 1: Obter Credenciais do WhatsApp

1. Acesse: https://developers.facebook.com/apps
2. Crie um novo app (tipo: Business)
3. Adicione o produto "WhatsApp"
4. Copie:
   - **Phone Number ID** (na página Configuration)
   - **Access Token** (botão "Generate Token")
   - Crie um **Verify Token** (qualquer string, ex: `meu_token_123`)

### Passo 2: Configurar Variáveis de Ambiente

**Windows PowerShell:**
```powershell
$env:WHATSAPP_PHONE_NUMBER_ID="cole-aqui"
$env:WHATSAPP_ACCESS_TOKEN="cole-aqui"
$env:WHATSAPP_VERIFY_TOKEN="meu_token_123"
$env:OPENAI_API_KEY="sua-key-openai"
```

**Linux/Mac:**
```bash
export WHATSAPP_PHONE_NUMBER_ID="cole-aqui"
export WHATSAPP_ACCESS_TOKEN="cole-aqui"
export WHATSAPP_VERIFY_TOKEN="meu_token_123"
export OPENAI_API_KEY="sua-key-openai"
```

### Passo 3: Expor Webhook com ngrok

```bash
# Baixe ngrok: https://ngrok.com/download
ngrok http 8080
```

Copie a URL HTTPS gerada (ex: `https://abc123.ngrok.io`)

### Passo 4: Iniciar Servidor

```bash
cd chatbot
mvn spring-boot:run
```

### Passo 5: Configurar Webhook no Meta

1. No painel WhatsApp → Configuration
2. Clique em "Edit" no Callback URL
3. Cole:
   - **URL**: `https://abc123.ngrok.io/webhook/whatsapp`
   - **Verify Token**: `meu_token_123`
4. Clique "Verify and Save"
5. Ative o campo **"messages"** nos Webhook Fields

### Passo 6: Adicionar Número de Teste

1. No painel WhatsApp → API Setup
2. Em "To", adicione seu número com código do país (ex: `5511999999999`)
3. Clique "Add phone number"

### Passo 7: Testar! 🎉

Envie uma mensagem para o número do WhatsApp Business e veja a mágica acontecer!

---

## 🧪 Teste Manual (Opcional)

### Verificar se webhook está funcionando:
```bash
curl "http://localhost:8080/webhook/whatsapp?hub.mode=subscribe&hub.verify_token=meu_token_123&hub.challenge=test"
```

Deve retornar: `test`

### Enviar mensagem de teste:
```bash
curl -X POST "http://localhost:8080/webhook/whatsapp/send?to=5511999999999&message=Hello"
```

---

## 📝 Arquivos Criados

- ✅ `WhatsAppWebhook.java` - DTO para webhooks
- ✅ `WhatsAppMessageRequest.java` - DTO para envio
- ✅ `WhatsAppService.java` - Lógica de negócio
- ✅ `WhatsAppWebhookController.java` - Endpoints REST
- ✅ `application.yml` - Configurações atualizadas

---

## ❓ Problemas Comuns

**Webhook não verifica?**
→ Confira se o verify_token está igual em ambos os lugares

**Mensagens não chegam?**
→ Verifique se o campo "messages" está ativo nos Webhook Fields

**Erro 401?**
→ Gere um novo Access Token no Meta

**Ngrok parou de funcionar?**
→ Reinicie ngrok e atualize a URL no Meta

---

## 📚 Documentação Completa

Veja o arquivo `WHATSAPP_INTEGRATION.md` para documentação detalhada.

---

**Status**: ✅ Integração completa e funcional!


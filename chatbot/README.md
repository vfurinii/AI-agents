# 🤖 AI Chatbot with WhatsApp Integration

Um chatbot inteligente integrado com **OpenAI GPT** e **WhatsApp Business API**, com memória de conversação persistente em banco de dados.

## ✨ Funcionalidades

- 🧠 **AI Inteligente**: Powered by OpenAI GPT-4o-mini
- 💬 **WhatsApp Integration**: Responde mensagens automaticamente no WhatsApp
- 🗄️ **Memória Persistente**: Histórico de conversação salvo em H2 Database
- 🌐 **REST API**: Endpoints para chat via HTTP
- 📱 **Multi-plataforma**: Web + WhatsApp
- 🔄 **Otimização de Tokens**: Sistema inteligente de gerenciamento de contexto

## 🚀 Quick Start

### 1. Pré-requisitos

- Java 17+
- Maven 3.6+
- Conta OpenAI (API Key)
- Conta Meta for Developers (para WhatsApp)

### 2. Clonar e Configurar

```bash
git clone <seu-repositorio>
cd chatbot
```

### 3. Configurar Variáveis de Ambiente

```bash
# Windows PowerShell
$env:OPENAI_API_KEY="sua-chave-openai"
$env:WHATSAPP_PHONE_NUMBER_ID="seu-phone-number-id"
$env:WHATSAPP_ACCESS_TOKEN="seu-access-token"
$env:WHATSAPP_VERIFY_TOKEN="seu-verify-token"
```

Ou copie `.env.example` para `.env` e preencha os valores.

### 4. Executar

```bash
mvn spring-boot:run
```

Acesse: http://localhost:8080

## 📱 Integração WhatsApp

### Setup Rápido (5 minutos)

Siga o guia: **[WHATSAPP_QUICKSTART.md](WHATSAPP_QUICKSTART.md)**

### Documentação Completa

- **[WHATSAPP_INTEGRATION.md](WHATSAPP_INTEGRATION.md)** - Guia completo de integração
- **[WHATSAPP_TESTING.md](WHATSAPP_TESTING.md)** - Testes e troubleshooting

### Fluxo WhatsApp

```
Usuário WhatsApp → Meta API → Webhook → ChatBot → OpenAI → Resposta → WhatsApp
```

## 🌐 API REST

### Chat Endpoint

```bash
POST http://localhost:8080/chat
Content-Type: text/plain

Olá, como você está?
```

### Limpar Histórico

```bash
DELETE http://localhost:8080/chat/history
```

### WhatsApp Webhook

```bash
# Verificação (GET)
GET /webhook/whatsapp?hub.mode=subscribe&hub.verify_token=TOKEN&hub.challenge=CHALLENGE

# Receber Mensagens (POST)
POST /webhook/whatsapp
```

## 🗄️ Banco de Dados

### H2 Console

Acesse: http://localhost:8080/h2-console

**Configurações:**
- JDBC URL: `jdbc:h2:file:./data/chatbot`
- Username: `sa`
- Password: (vazio)

### Consultas SQL

```sql
-- Ver todas as mensagens
SELECT * FROM messages ORDER BY timestamp;

-- Últimas 10 mensagens
SELECT * FROM messages ORDER BY timestamp DESC LIMIT 10;

-- Contar por tipo
SELECT role, COUNT(*) FROM messages GROUP BY role;
```

## 📂 Estrutura do Projeto

```
chatbot/
├── src/main/java/com/chatbot/demo/
│   ├── config/          # Configurações (OpenAI, etc)
│   ├── controller/      # REST Controllers
│   ├── domain/          # Entidades JPA
│   ├── llm/             # Cliente OpenAI
│   ├── service/         # Lógica de negócio
│   └── whatsapp/        # Integração WhatsApp
│       ├── controller/  # Webhook endpoints
│       ├── dto/         # DTOs WhatsApp
│       └── service/     # WhatsApp service
├── src/main/resources/
│   └── application.yml  # Configurações
├── data/                # H2 Database files
└── docs/
    ├── WHATSAPP_INTEGRATION.md
    ├── WHATSAPP_QUICKSTART.md
    └── WHATSAPP_TESTING.md
```

## 🔧 Configuração

### application.yml

```yaml
server:
  port: 8080

openai:
  api-key: ${OPENAI_API_KEY}
  model: gpt-4o-mini

whatsapp:
  phone-number-id: ${WHATSAPP_PHONE_NUMBER_ID}
  access-token: ${WHATSAPP_ACCESS_TOKEN}
  verify-token: ${WHATSAPP_VERIFY_TOKEN}

spring:
  datasource:
    url: jdbc:h2:file:./data/chatbot
  jpa:
    hibernate:
      ddl-auto: update
```

## 🧪 Testes

### Teste Local (HTTP)

```bash
# Chat
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: text/plain" \
  -d "Olá!"

# Limpar histórico
curl -X DELETE http://localhost:8080/chat/history
```

### Teste WhatsApp

```bash
# 1. Inicie ngrok
ngrok http 8080

# 2. Configure webhook no Meta
# URL: https://xxx.ngrok.io/webhook/whatsapp

# 3. Envie mensagem no WhatsApp
```

Veja testes completos em: **[WHATSAPP_TESTING.md](WHATSAPP_TESTING.md)**

## 📚 Documentação

| Documento | Descrição |
|-----------|-----------|
| [DATABASE.md](DATABASE.md) | Implementação e uso do banco de dados |
| [MEMORY_OPTIMIZATION.md](MEMORY_OPTIMIZATION.md) | Otimização de memória e tokens |
| [WHATSAPP_INTEGRATION.md](WHATSAPP_INTEGRATION.md) | Guia completo de integração WhatsApp |
| [WHATSAPP_QUICKSTART.md](WHATSAPP_QUICKSTART.md) | Setup rápido WhatsApp (5 min) |
| [WHATSAPP_TESTING.md](WHATSAPP_TESTING.md) | Testes e troubleshooting |

## 🛡️ Segurança

- ✅ Tokens em variáveis de ambiente
- ✅ Webhook com token de verificação
- ✅ HTTPS obrigatório em produção
- ✅ Validação de origem das mensagens

## 🚀 Deploy

### Opções de Deploy

- **Heroku**: Deploy rápido com Git
- **Railway**: Free tier com SSL
- **Google Cloud Run**: Serverless
- **AWS EC2/ECS**: Controle total
- **Azure App Service**: Integração com ecosystem Microsoft

### Exemplo Railway

```bash
# 1. Instale Railway CLI
npm i -g @railway/cli

# 2. Login
railway login

# 3. Deploy
railway up
```

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

## 🙏 Agradecimentos

- OpenAI pela API GPT
- Meta pela WhatsApp Business API
- Spring Boot Team
- Comunidade Open Source

---

**Desenvolvido com ❤️ usando Spring Boot, OpenAI e WhatsApp API**

Para dúvidas ou suporte, abra uma issue no GitHub.


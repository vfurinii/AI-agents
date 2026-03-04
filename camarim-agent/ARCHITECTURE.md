# 🏗️ Arquitetura - Camarim POA Assistant

## Visão Geral do Sistema

```
┌─────────────────────────────────────────────────────────────────┐
│                         USUÁRIO                                  │
│              (Instagram DM / Interface Web)                      │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ HTTP Request
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    FRONTEND (index.html)                         │
│  • Interface moderna e responsiva                                │
│  • Gerenciamento de sessões (localStorage)                       │
│  • Indicador de digitação                                        │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ POST /api/chat
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                   BACKEND (Spring Boot)                          │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │            ChatController                                 │  │
│  │  • Recebe requisições HTTP                               │  │
│  │  • Valida input                                          │  │
│  │  • Retorna respostas JSON                                │  │
│  └────────────────────┬─────────────────────────────────────┘  │
│                       │                                          │
│                       ▼                                          │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         CamarimAgentService                              │  │
│  │  • Orquestra fluxo de conversação                        │  │
│  │  • Coordena Memory e OpenAI                              │  │
│  └────────┬─────────────────────┬───────────────────────────┘  │
│           │                     │                               │
│           ▼                     ▼                               │
│  ┌─────────────────┐   ┌──────────────────────┐               │
│  │  MemoryService  │   │   OpenAiService      │               │
│  │                 │   │                      │               │
│  │ • Salva msgs    │   │ • Monta prompt       │               │
│  │ • Recupera      │   │ • Chama OpenAI       │               │
│  │   histórico     │   │ • Processa resposta  │               │
│  └────────┬────────┘   └───────────┬──────────┘               │
│           │                        │                           │
│           ▼                        ▼                           │
│  ┌──────────────────┐    ┌──────────────────────┐            │
│  │ ConversationRepo │    │   OpenAI API         │            │
│  │   (JPA)          │    │  (GPT-4o-mini)       │            │
│  └────────┬─────────┘    └──────────────────────┘            │
│           │                                                    │
└───────────┼────────────────────────────────────────────────────┘
            │
            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    H2 DATABASE (File)                            │
│                                                                  │
│  Table: CONVERSATIONS                                            │
│  ┌────────┬────────────┬──────┬─────────┬───────────┐          │
│  │   ID   │ SESSION_ID │ ROLE │ MESSAGE │ TIMESTAMP │          │
│  ├────────┼────────────┼──────┼─────────┼───────────┤          │
│  │   1    │  uuid-123  │ user │  "Oi!"  │ 2026-...  │          │
│  │   2    │  uuid-123  │ asst │  "..."  │ 2026-...  │          │
│  └────────┴────────────┴──────┴─────────┴───────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

## Fluxo de Dados Detalhado

### 1️⃣ Usuário Envia Mensagem

```
Usuário → Frontend
  {
    "sessionId": "uuid" | null,
    "message": "Olá! Vocês têm vestidos?"
  }
```

### 2️⃣ Frontend → Backend

```
POST /api/chat
Headers: Content-Type: application/json
Body: {
  "sessionId": "abc-123",
  "message": "Olá! Vocês têm vestidos?"
}
```

### 3️⃣ ChatController Processa

```java
ChatController.chat(request)
  ↓
  • Valida request
  • Gera sessionId se necessário
  • Chama CamarimAgentService
```

### 4️⃣ CamarimAgentService Orquestra

```java
CamarimAgentService.chat(sessionId, message)
  ↓
  1. MemoryService.saveMessage("user", message)
  2. MemoryService.getMessages(sessionId)
  3. OpenAiService.chat(messages)
  4. MemoryService.saveMessage("assistant", response)
  5. return response
```

### 5️⃣ MemoryService Persiste

```java
MemoryService
  ↓
  • Conversation entity = new Conversation()
  • ConversationRepository.save(entity)
  • H2 Database persiste em arquivo
```

### 6️⃣ OpenAiService Processa

```java
OpenAiService.chat(messages)
  ↓
  1. Adiciona System Prompt:
     "Você é a assistente da Camarim POA..."
  
  2. Monta request:
     {
       "model": "gpt-4o-mini",
       "messages": [
         {"role": "system", "content": "..."},
         {"role": "user", "content": "Oi!"},
         {"role": "assistant", "content": "..."},
         {"role": "user", "content": "Vestidos?"}
       ]
     }
  
  3. POST https://api.openai.com/v1/chat/completions
  
  4. Extrai resposta do JSON
```

### 7️⃣ Resposta Retorna

```
Backend → Frontend
{
  "response": "Olá! Sim, temos uma linda coleção...",
  "sessionId": "abc-123"
}

Frontend → Usuário
Exibe mensagem na interface
```

## Componentes e Responsabilidades

### 🎨 Frontend (index.html)
- **Responsabilidade**: Interface do usuário
- **Tecnologia**: HTML5, CSS3, Vanilla JavaScript
- **Features**:
  - Gerenciamento de sessão via localStorage
  - Animações e UX moderna
  - Comunicação assíncrona com backend

### 🎮 ChatController
- **Responsabilidade**: Camada de API REST
- **Tecnologia**: Spring Web @RestController
- **Endpoints**:
  - POST /api/chat - Chat principal
  - DELETE /api/chat/session/{id} - Limpar sessão
  - GET /api/chat/session/{id}/count - Contar msgs
  - GET /api/chat/health - Health check

### 🧠 CamarimAgentService
- **Responsabilidade**: Orquestração do fluxo
- **Tecnologia**: Spring @Service
- **Funções**:
  - Coordenar memória e IA
  - Gerenciar ciclo de vida da conversa
  - Logging e monitoramento

### 💾 MemoryService
- **Responsabilidade**: Persistência de conversas
- **Tecnologia**: Spring Data JPA
- **Funções**:
  - Salvar mensagens
  - Recuperar histórico
  - Limpar sessões

### 🤖 OpenAiService
- **Responsabilidade**: Integração com IA
- **Tecnologia**: Spring WebFlux (WebClient)
- **Funções**:
  - Montar prompt do sistema
  - Chamar API OpenAI
  - Processar respostas
  - Tratamento de erros

### 🗄️ ConversationRepository
- **Responsabilidade**: Acesso a dados
- **Tecnologia**: Spring Data JPA
- **Queries**:
  - findBySessionIdOrderByTimestampAsc
  - deleteBySessionId

### 💽 H2 Database
- **Responsabilidade**: Persistência
- **Tecnologia**: H2 file-based
- **Local**: ./data/camarim.mv.db

## Padrões de Design Utilizados

### 1. **Repository Pattern**
```
Controller → Service → Repository → Database
```

### 2. **Dependency Injection**
```java
@Service
public class CamarimAgentService {
    private final OpenAiService openAiService;
    private final MemoryService memoryService;
    // Injetados via construtor
}
```

### 3. **DTO Pattern**
```
ChatRequest, ChatResponse, ChatMessage
Separação entre entidades e objetos de transferência
```

### 4. **Builder Pattern**
```java
Conversation.builder()
    .sessionId(id)
    .role("user")
    .message(text)
    .build();
```

## Segurança

```
┌──────────────────────────────────────┐
│ Frontend                             │
│ • CORS habilitado                    │
│ • Validação client-side              │
└──────────────┬───────────────────────┘
               │
               ▼
┌──────────────────────────────────────┐
│ Backend                              │
│ • Validação server-side              │
│ • API Key em variável de ambiente    │
│ • Sem exposição de credenciais       │
└──────────────┬───────────────────────┘
               │
               ▼
┌──────────────────────────────────────┐
│ OpenAI                               │
│ • HTTPS                              │
│ • Authorization header               │
└──────────────────────────────────────┘
```

## Performance

### Otimizações Implementadas:

1. **Lazy Loading** - Entities JPA
2. **Connection Pooling** - HikariCP (padrão Spring)
3. **Reactive Client** - WebFlux para chamadas HTTP
4. **Caching** - Configurações carregadas uma vez
5. **Database Indexing** - session_id indexado

### Métricas Esperadas:

| Operação | Tempo Médio |
|----------|-------------|
| Salvar mensagem | < 10ms |
| Recuperar histórico | < 50ms |
| Chamada OpenAI | 1-3s |
| **Total por mensagem** | **1-3.5s** |

## Escalabilidade

### Vertical (Aumentar recursos)
```
Aumentar:
- Heap JVM (-Xmx)
- Connection pool
- Thread pool
```

### Horizontal (Mais instâncias)
```
┌─────────┐     ┌─────────┐     ┌─────────┐
│ App 1   │     │ App 2   │     │ App 3   │
└────┬────┘     └────┬────┘     └────┬────┘
     │               │               │
     └───────────────┴───────────────┘
                     │
              ┌──────▼──────┐
              │   Database  │
              │   Shared    │
              └─────────────┘
```

**Nota**: Para produção, considerar migrar H2 para PostgreSQL/MySQL.

## Monitoramento

```
Application
    │
    ├─> Logs → CloudWatch/Elasticsearch
    │
    ├─> Metrics → Prometheus → Grafana
    │
    └─> Health Check → Load Balancer
```

## Diagrama de Sequência

```
User    Frontend    Controller    Service    Memory    OpenAI    Database
 │          │            │           │         │         │          │
 │──msg────>│            │           │         │         │          │
 │          │──POST─────>│           │         │         │          │
 │          │            │──chat────>│         │         │          │
 │          │            │           │──save──>│         │          │
 │          │            │           │         │──INSERT──────────>│
 │          │            │           │<────ok──│         │          │
 │          │            │           │──getHistory────>│          │
 │          │            │           │<────msgs────────┤          │
 │          │            │           │─────chat──────────────────>│
 │          │            │           │<────response───────────────┤
 │          │            │           │──save──>│         │          │
 │          │            │           │         │──INSERT──────────>│
 │          │            │<──reply───│         │         │          │
 │          │<──JSON────┤            │         │         │          │
 │<─display─┤            │           │         │         │          │
```

---

**Desenvolvido com 💜 para Camarim POA**


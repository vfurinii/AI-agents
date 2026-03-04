# 🛍️ Camarim POA - Assistente Virtual Instagram

Assistente virtual inteligente para atendimento no Instagram da **Camarim POA**, loja de moda feminina em Porto Alegre.

## 📋 Descrição

Este projeto implementa uma assistente virtual completa utilizando Spring Boot e OpenAI, projetada especificamente para o atendimento da Camarim POA. A assistente possui personalidade própria, alinhada com o tom de voz da marca.

### 🎯 Características da Assistente

**Tom de voz:** Feminino, sofisticado e próximo  
**Estilo:** Simpático, ágil e elegante

**Funções principais:**
- ✅ Responder dúvidas sobre produtos, tamanhos, valores e disponibilidade
- ✅ Informar formas de pagamento e envio
- ✅ Direcionar clientes para compras via direct
- ✅ Divulgar novidades, lançamentos e promoções
- ✅ Incentivar visitas à loja física

**Regras de atendimento:**
- Clara e objetiva
- Linguagem moderna e acolhedora
- Sempre incentiva a finalização da compra
- Informa quando precisa verificar com a equipe

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.10**
- **Spring Data JPA** (persistência de conversas)
- **H2 Database** (banco de dados em arquivo)
- **Spring WebFlux** (cliente HTTP reativo)
- **OpenAI API** (GPT-4o-mini)
- **Lombok** (redução de boilerplate)

## 📁 Estrutura do Projeto

```
camarim-agent/
├── src/main/java/com/camarim/agent/
│   ├── CamarimAgentApplication.java      # Classe principal
│   ├── config/
│   │   └── CamarimProperties.java        # Configurações da loja
│   ├── controller/
│   │   └── ChatController.java           # API REST
│   ├── dto/
│   │   ├── ChatMessage.java
│   │   ├── ChatRequest.java
│   │   └── ChatResponse.java
│   ├── model/
│   │   └── Conversation.java             # Entidade JPA
│   ├── repository/
│   │   └── ConversationRepository.java   # Acesso a dados
│   └── service/
│       ├── CamarimAgentService.java      # Lógica principal
│       ├── MemoryService.java            # Gerenciamento de memória
│       └── OpenAiService.java            # Integração OpenAI
├── src/main/resources/
│   ├── application.yml                    # Configurações
│   └── static/
│       └── index.html                     # Interface web
├── data/                                  # Banco de dados H2
└── pom.xml
```

## ⚙️ Configuração

### 1. Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Chave de API da OpenAI

### 2. Configurar a API Key

Defina a variável de ambiente com sua chave OpenAI:

```bash
export OPENAI_API_KEY="sua-chave-aqui"
```

Ou edite o arquivo `src/main/resources/application.yml` e substitua o valor padrão.

### 3. Customizar a Assistente

Edite `src/main/resources/application.yml` para ajustar as configurações da loja:

```yaml
camarim:
  store:
    name: "Camarim POA"
    location: "Porto Alegre"
    instagram: "@camarim.poa"
  assistant:
    tone: "feminino, sofisticado e próximo"
    style: "simpático, ágil e elegante"
```

## 🏃‍♀️ Como Executar

### Opção 1: Usando Maven

```bash
cd camarim-agent
mvn clean install
mvn spring-boot:run
```

### Opção 2: Usando JAR

```bash
cd camarim-agent
mvn clean package
java -jar target/camarim-agent-1.0.0-SNAPSHOT.jar
```

### Opção 3: Usando o script (macOS/Linux)

```bash
cd camarim-agent
chmod +x run.sh
./run.sh
```

A aplicação estará disponível em:
- **API**: http://localhost:8082
- **Interface Web**: http://localhost:8082/index.html
- **H2 Console**: http://localhost:8082/h2-console

## 📡 API Endpoints

### POST /api/chat
Envia uma mensagem para a assistente

**Request:**
```json
{
  "sessionId": "uuid-opcional",
  "message": "Olá! Vocês têm vestidos?"
}
```

**Response:**
```json
{
  "response": "Olá! Sim, temos uma linda coleção de vestidos! 💕 ...",
  "sessionId": "uuid-da-sessao"
}
```

### DELETE /api/chat/session/{sessionId}
Limpa o histórico de uma sessão

### GET /api/chat/session/{sessionId}/count
Retorna o número de mensagens em uma sessão

### GET /api/chat/health
Health check do serviço

## 💾 Banco de Dados

O histórico de conversas é salvo em um banco H2 no arquivo `./data/camarim.mv.db`.

Para acessar o console H2:
1. Abra http://localhost:8082/h2-console
2. JDBC URL: `jdbc:h2:file:./data/camarim`
3. Username: `sa`
4. Password: (deixe em branco)

## 🎨 Interface Web

A aplicação inclui uma interface web moderna e responsiva para testes. Acesse em:
http://localhost:8082/index.html

Recursos da interface:
- Design moderno e responsivo
- Indicador de digitação
- Persistência de sessão
- Botão para limpar conversa
- Animações suaves

## 🔧 Desenvolvimento

### Adicionar Novas Funcionalidades

1. **Adicionar novos endpoints**: Edite `ChatController.java`
2. **Modificar o sistema de prompts**: Edite `OpenAiService.java` (método `getSystemPrompt()`)
3. **Adicionar validações**: Implemente em `CamarimAgentService.java`
4. **Customizar a UI**: Edite `static/index.html`

### Logs

Os logs são exibidos no console. Para ajustar o nível de log, edite `application.yml`:

```yaml
logging:
  level:
    com.camarim.agent: DEBUG
```

## 🚀 Deploy

### Variáveis de Ambiente Necessárias

- `OPENAI_API_KEY`: Chave da API OpenAI (obrigatória)
- `OPENAI_MODEL`: Modelo a usar (padrão: gpt-4o-mini)
- `SERVER_PORT`: Porta do servidor (padrão: 8082)

### Docker (Exemplo)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/camarim-agent-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📝 Prompt do Sistema

O prompt completo usado pela assistente está em `OpenAiService.java`. Ele incorpora:
- Identidade e personalidade da marca
- Funções e responsabilidades
- Regras de atendimento
- Tom de voz
- Informações da loja

## 🤝 Contribuindo

Para contribuir com melhorias:
1. Faça fork do projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto é privado e proprietário da Camarim POA.

## 📞 Suporte

Para dúvidas ou suporte:
- Instagram: @camarim.poa
- Email: (adicionar se disponível)

---

**Desenvolvido com 💜 para Camarim POA**


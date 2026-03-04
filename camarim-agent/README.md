# 🤖 Assistente Virtual Inteligente - Multi-Negócios

Assistente virtual inteligente para atendimento automático, adaptável para lojas e salões de beleza.

## 📋 Sobre o Projeto

Este projeto implementa uma assistente virtual completa utilizando **Spring Boot** e **OpenAI GPT-4**, projetada para diversos tipos de negócios:
- 💇 **Salões de Beleza** - Informar serviços, preços e agendar horários
- 🛍️ **Lojas de Moda** - Atender dúvidas sobre produtos e vendas

### 🎯 Configuração Flexível

O agente se adapta automaticamente ao seu negócio através do arquivo `application.yml`:

**Para Salão de Beleza:**
```yaml
camarim:
  store:
    name: "Seu Salão"
    location: "Sua Cidade"
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais"
```

**Para Loja:**
```yaml
camarim:
  store:
    name: "Sua Loja"
    location: "Sua Cidade"
    instagram: "@sua_loja"
```

📖 **[Ver guia completo de configuração de serviços](SERVICOS.md)**

### 🎯 Exemplo de Prompt (Salão de Beleza)

Você é a assistente virtual de um salão de beleza.
Seu atendimento deve ser simpático, ágil e atencioso.

**Suas funções:**
* Responder dúvidas sobre serviços, preços e disponibilidade
* Informar formas de pagamento
* Ajudar a agendar horários
* Divulgar promoções e pacotes especiais
* Incentivar o agendamento

**Regras:**
* Seja clara e objetiva
* Use linguagem moderna e acolhedora
* Sempre incentive o agendamento
* Use os preços EXATOS configurados no sistema
* Quando não souber uma informação, informe que irá verificar com a equipe

**Tom de voz:** profissional, acolhedor e confiante

## 🚀 Quick Start

```bash
# 1. Configure sua API Key da OpenAI
export OPENAI_API_KEY="sua-chave-aqui"

# 2. Execute o projeto
./run.sh
```

Acesse a interface web em: http://localhost:8082/index.html

## 📚 Documentação Completa

- **[SUMMARY.md](SUMMARY.md)** - ⭐ Resumo executivo do projeto
- **[PROJETO.md](PROJETO.md)** - Documentação técnica completa
- **[API_EXAMPLES.md](API_EXAMPLES.md)** - Exemplos de uso da API
- **[DEPLOY.md](DEPLOY.md)** - Guias de deploy para produção
- **[QUICKREF.md](QUICKREF.md)** - Referência rápida para desenvolvedores

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.5.10
- OpenAI API (GPT-4o-mini)
- H2 Database
- Spring Data JPA
- Lombok

## 📡 API Principal

**POST /api/chat**
```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Olá! Quanto custa mecha de cabelo?"}'
```

## 💻 Interface Web

A aplicação inclui uma interface web moderna para testes:
- Design responsivo e elegante
- Histórico de conversas persistente
- Indicador de digitação em tempo real

## 🧪 Testando

### Testes Automáticos
```bash
./test.sh
```

### Teste Manual
1. Acesse http://localhost:8082/index.html
2. Digite: "Olá! Quanto custa mecha de cabelo?"
3. Continue a conversa naturalmente

---

**Desenvolvido com 💜 - Assistente Virtual Inteligente**

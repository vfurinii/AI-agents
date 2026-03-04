# ✅ Configuração Completa - Serviço de Mecha de Cabelo

## 🎯 O que foi feito

Seu agente agora está configurado para responder perguntas sobre **serviços de cabelo**, especificamente:

- ✅ **Serviço**: Mecha de Cabelo
- ✅ **Preço**: R$ 100,00
- ✅ **Descrição**: Mechas tradicionais para realçar seu visual

## 📝 Arquivos Modificados

### 1. `application.yml` - Configuração Principal
```yaml
camarim:
  store:
    name: "Salão [Seu Nome]"           # ← Altere para o nome do seu salão
    location: "[Sua Cidade]"            # ← Altere para sua cidade
    instagram: "@[seu_instagram]"       # ← Altere para seu Instagram
  assistant:
    tone: "profissional, acolhedor e confiante"
    style: "simpático, ágil e atencioso"
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais para realçar seu visual"
```

### 2. `CamarimProperties.java` - Suporte a Serviços
Adicionada classe `Service` para armazenar:
- Nome do serviço
- Preço
- Descrição

### 3. `OpenAiService.java` - Prompt Inteligente
O system prompt agora inclui automaticamente:
- Lista de serviços
- Preços exatos
- Descrições

## 🚀 Como Usar

### 1️⃣ Personalize Seu Salão

Edite `src/main/resources/application.yml`:

```yaml
camarim:
  store:
    name: "Salão Beleza Total"      # Seu nome aqui
    location: "São Paulo, SP"        # Sua cidade aqui
    instagram: "@salao.beleza"       # Seu Instagram aqui
```

### 2️⃣ Adicione Mais Serviços

No mesmo arquivo, adicione quantos serviços quiser:

```yaml
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais"
    
    - name: "Corte Feminino"
      price: 80.00
      description: "Corte moderno e personalizado"
    
    - name: "Escova"
      price: 60.00
      description: "Escova modeladora profissional"
    
    - name: "Hidratação"
      price: 120.00
      description: "Tratamento profundo de hidratação"
```

### 3️⃣ Inicie o Servidor

```bash
# Configure a API Key (apenas uma vez)
export OPENAI_API_KEY="sua-chave-aqui"

# Inicie o servidor
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
./run.sh
```

### 4️⃣ Teste

Acesse: http://localhost:8082/index.html

**Perguntas de teste:**
- "Olá! Quanto custa mecha de cabelo?"
- "Quais serviços vocês fazem?"
- "Qual o preço da mecha?"
- "Quero agendar uma mecha"

## 📋 Exemplo de Conversa

**Cliente:** "Oi! Quanto custa fazer mecha?"

**Agente:** "Oi! 😊 O serviço de Mecha de Cabelo custa R$ 100,00. São mechas tradicionais para realçar seu visual. Quer agendar um horário?"

**Cliente:** "Sim, quero agendar"

**Agente:** "Que ótimo! Para agendar seu horário, entre em contato via direct no Instagram @[seu_instagram] ou ligue para nosso salão. Qual horário você prefere?"

## 🎨 Como Funciona

1. **Cliente pergunta** sobre serviços ou preços
2. **Agente busca** na lista configurada no `application.yml`
3. **GPT-4 responde** usando os valores EXATOS configurados
4. **Agente incentiva** o agendamento

## 📚 Documentação Adicional

- 📖 **[SERVICOS.md](SERVICOS.md)** - Guia completo de configuração de serviços
- 🚀 **[TESTE_RAPIDO.md](TESTE_RAPIDO.md)** - Guia rápido de teste
- 🛠️ **[README.md](README.md)** - Documentação completa do projeto
- 🚢 **[DEPLOY.md](DEPLOY.md)** - Como colocar em produção

## ⚙️ Customização Avançada

### Mudar o Tom de Voz

```yaml
assistant:
  tone: "descontraído e jovem"              # Mais informal
  style: "dinâmico e entusiasmado"          # Mais energético
```

ou

```yaml
assistant:
  tone: "formal e elegante"                 # Mais sofisticado
  style: "calmo e profissional"             # Mais sério
```

### Ajustar Temperatura da IA

Edite `OpenAiService.java` linha 56:

```java
requestBody.put("temperature", 0.7);  // 0.0 = mais preciso, 1.0 = mais criativo
```

## ✨ Próximos Passos

1. ✅ Configure nome, cidade e Instagram do seu salão
2. ✅ Adicione todos os seus serviços com preços
3. ✅ Teste todas as interações
4. ✅ Ajuste o tom de voz conforme necessário
5. 🌐 Veja [DEPLOY.md](DEPLOY.md) para produção

---

**Seu agente está pronto para atender! 💇‍♀️✨**


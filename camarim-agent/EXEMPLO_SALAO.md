# 💡 Exemplo Completo - Salão de Beleza

## Cenário Real

Você tem um salão de beleza em São Paulo e oferece diversos serviços. Veja como configurar:

## application.yml - Configuração Completa

```yaml
server:
  port: 8082

spring:
  application:
    name: camarim-agent
  datasource:
    url: jdbc:h2:file:./data/camarim
    driverClassName: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true

# OpenAI Configuration
openai:
  api-key: ${OPENAI_API_KEY:your-api-key-here}
  model: ${OPENAI_MODEL:gpt-4o-mini}
  base-url: https://api.openai.com/v1

# Camarim Configuration
camarim:
  store:
    name: "Salão Beleza Total"
    location: "São Paulo, SP"
    instagram: "@belezatotal"
  assistant:
    tone: "profissional, acolhedor e confiante"
    style: "simpático, ágil e atencioso"
  services:
    # Serviços de Cabelo
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais para realçar seu visual"
    
    - name: "Corte Feminino"
      price: 80.00
      description: "Corte moderno e personalizado com lavagem"
    
    - name: "Corte Masculino"
      price: 50.00
      description: "Corte clássico ou moderno"
    
    - name: "Escova"
      price: 60.00
      description: "Escova modeladora profissional"
    
    - name: "Hidratação"
      price: 120.00
      description: "Tratamento profundo de hidratação capilar"
    
    - name: "Progressiva"
      price: 300.00
      description: "Alisamento progressivo de longa duração"
    
    - name: "Luzes"
      price: 250.00
      description: "Luzes completas para iluminar seu cabelo"
    
    - name: "Coloração"
      price: 150.00
      description: "Coloração profissional com produtos de qualidade"
    
    - name: "Reflexo"
      price: 100.00
      description: "Reflexo para realçar a cor natural"
    
    - name: "Reconstrução Capilar"
      price: 150.00
      description: "Tratamento intensivo para cabelos danificados"
    
    # Serviços de Estética
    - name: "Manicure"
      price: 40.00
      description: "Esmaltação e cuidados com as unhas"
    
    - name: "Pedicure"
      price: 50.00
      description: "Tratamento completo para os pés"
    
    - name: "Design de Sobrancelhas"
      price: 35.00
      description: "Modelagem profissional de sobrancelhas"
    
    - name: "Depilação Facial"
      price: 30.00
      description: "Remoção de pelos faciais com cera"
    
    - name: "Limpeza de Pele"
      price: 120.00
      description: "Limpeza profunda e tratamento facial"
    
    # Pacotes Promocionais
    - name: "Pacote Noiva"
      price: 500.00
      description: "Cabelo, maquiagem e penteado completo"
    
    - name: "Day Spa"
      price: 400.00
      description: "Dia completo de beleza e relaxamento"
```

## 💬 Exemplos de Conversas

### Conversa 1: Cliente Perguntando Preço

**Cliente:** "Oi! Quanto custa fazer mecha?"

**Agente:** "Oi! 😊 O serviço de Mecha de Cabelo custa R$ 100,00. São mechas tradicionais para realçar seu visual. Quer agendar um horário?"

---

### Conversa 2: Cliente Comparando Serviços

**Cliente:** "Qual a diferença entre luzes e mecha?"

**Agente:** "Ótima pergunta! 💡

**Luzes** (R$ 250,00) - Luzes completas para iluminar todo o cabelo, resultado mais impactante

**Mecha de Cabelo** (R$ 100,00) - Mechas tradicionais para realçar o visual de forma mais sutil

As luzes dão um efeito mais completo e iluminado. Qual estilo você prefere? Posso te ajudar a escolher! 😊"

---

### Conversa 3: Cliente Querendo Vários Serviços

**Cliente:** "Quero fazer corte e escova. Quanto fica?"

**Agente:** "Perfeito! 💇‍♀️ Seria:

- Corte Feminino: R$ 80,00
- Escova: R$ 60,00

**Total: R$ 140,00**

Que dia você prefere agendar? Entre em contato via direct @belezatotal para garantir seu horário! 💕"

---

### Conversa 4: Cliente Perguntando Sobre Todos os Serviços

**Cliente:** "Quais serviços vocês fazem?"

**Agente:** "Oferecemos diversos serviços! 💇‍♀️✨

**Cabelo:**
- Corte Feminino (R$ 80,00)
- Corte Masculino (R$ 50,00)
- Mecha de Cabelo (R$ 100,00)
- Luzes (R$ 250,00)
- Coloração (R$ 150,00)
- Progressiva (R$ 300,00)
- Escova (R$ 60,00)
- Hidratação (R$ 120,00)

**Estética:**
- Manicure (R$ 40,00)
- Pedicure (R$ 50,00)
- Design de Sobrancelhas (R$ 35,00)
- Limpeza de Pele (R$ 120,00)

**Pacotes:**
- Pacote Noiva (R$ 500,00)
- Day Spa (R$ 400,00)

Qual te interessa mais? 😊"

---

### Conversa 5: Cliente Querendo Agendar

**Cliente:** "Quero agendar uma progressiva para sexta"

**Agente:** "Ótima escolha! A Progressiva (R$ 300,00) deixa o cabelo liso por meses! 😍

Para agendar seu horário de sexta-feira, entre em contato:
- 📱 Direct: @belezatotal
- 📍 Salão Beleza Total - São Paulo, SP

Vou adorar te ver por aqui! 💕"

---

### Conversa 6: Cliente com Dúvida Específica

**Cliente:** "Meu cabelo tá muito danificado, o que vocês recomendam?"

**Agente:** "Entendo sua preocupação! Para cabelos danificados, temos duas opções excelentes:

**Reconstrução Capilar** (R$ 150,00) - Tratamento intensivo ideal para cabelos muito danificados

**Hidratação** (R$ 120,00) - Tratamento profundo para restaurar a saúde dos fios

A reconstrução é mais completa e indicada para casos mais sérios. Quer agendar uma avaliação? Assim posso te indicar o melhor tratamento! 💕"

## 🎯 Benefícios da Configuração

✅ **Respostas Precisas** - Preços sempre corretos
✅ **Fácil Atualização** - Muda no YAML, não precisa alterar código
✅ **Inteligente** - GPT-4 entende e recomenda serviços
✅ **Profissional** - Atendimento consistente 24/7
✅ **Escalável** - Adicione quantos serviços quiser

## 🔄 Atualizando Preços

Quando os preços mudarem, basta editar o `application.yml`:

```yaml
- name: "Mecha de Cabelo"
  price: 120.00  # ← Mudou de 100 para 120
  description: "Mechas tradicionais"
```

Reinicie o servidor e pronto! ✅

---

**Seu salão agora tem uma assistente virtual profissional! 💇‍♀️✨**


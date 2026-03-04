# 🚀 Guia Rápido - Teste do Agente

## 1️⃣ Configurar a API Key da OpenAI

```bash
export OPENAI_API_KEY="sua-chave-aqui"
```

## 2️⃣ Iniciar o Servidor

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
./run.sh
```

Ou com Maven:
```bash
mvn spring-boot:run
```

## 3️⃣ Testar o Agente

### Opção 1: Interface Web
1. Abra seu navegador
2. Acesse: http://localhost:8082/index.html
3. Teste com perguntas como:
   - "Olá! Quanto custa mecha de cabelo?"
   - "Quais serviços vocês fazem?"
   - "Qual o preço da mecha?"

### Opção 2: Via API (curl)

```bash
curl -X POST http://localhost:8082/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Olá! Quanto custa mecha de cabelo?"}'
```

**Resposta esperada:**
O agente deve responder algo como:
> "Oi! 😊 O serviço de Mecha de Cabelo custa R$ 100,00. São mechas tradicionais para realçar seu visual. Quer agendar um horário?"

## 4️⃣ Personalizar para o Seu Salão

Edite o arquivo: `src/main/resources/application.yml`

```yaml
camarim:
  store:
    name: "Nome do Seu Salão"        # ← Altere aqui
    location: "Sua Cidade"            # ← Altere aqui
    instagram: "@seu_instagram"       # ← Altere aqui
  assistant:
    tone: "profissional, acolhedor e confiante"
    style: "simpático, ágil e atencioso"
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais para realçar seu visual"
    # ← Adicione mais serviços aqui
```

## 5️⃣ Adicionar Mais Serviços

No mesmo arquivo `application.yml`, adicione mais serviços:

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
      description: "Tratamento profundo"
```

## 6️⃣ Reiniciar Após Mudanças

Sempre que alterar o `application.yml`:

1. Pare o servidor (Ctrl+C)
2. Reinicie: `./run.sh` ou `mvn spring-boot:run`

## 📱 Testando Conversas Realistas

### Exemplo 1: Cliente perguntando preço
**Você:** "Olá! Quanto custa fazer mecha?"  
**Agente:** "Oi! 😊 O serviço de Mecha de Cabelo custa R$ 100,00..."

### Exemplo 2: Cliente querendo agendar
**Você:** "Quero agendar uma mecha para amanhã"  
**Agente:** "Que ótimo! Para agendar, entre em contato via direct no Instagram..."

### Exemplo 3: Cliente perguntando sobre vários serviços
**Você:** "Quais serviços vocês fazem?"  
**Agente:** "Oferecemos: Mecha de Cabelo (R$ 100,00), Corte Feminino (R$ 80,00)..."

## 🔍 Verificando Logs

Para ver o que está acontecendo nos bastidores:

```bash
tail -f logs/camarim-agent.log
```

Ou verifique o console onde o servidor está rodando.

## ❌ Problemas Comuns

### "Connection refused"
- Verifique se o servidor está rodando
- Porta 8082 deve estar livre

### "OpenAI API Error"
- Verifique se a API Key está configurada corretamente
- Confirme que tem créditos na conta OpenAI

### Agente não responde com preços corretos
- Verifique o arquivo `application.yml`
- Reinicie o servidor após alterações

## 🎯 Próximos Passos

1. ✅ Personalize nome, localização e Instagram
2. ✅ Adicione todos os seus serviços com preços corretos
3. ✅ Teste todas as interações possíveis
4. ✅ Ajuste o tom de voz se necessário
5. 📖 Veja [DEPLOY.md](DEPLOY.md) para colocar em produção

---

**Dúvidas?** Consulte [SERVICOS.md](SERVICOS.md) para mais exemplos!


# 💇 Configuração de Serviços e Preços

## Como adicionar serviços ao agente

Os serviços e preços são configurados no arquivo `src/main/resources/application.yml`.

### Exemplo Atual

```yaml
camarim:
  store:
    name: "Salão [Seu Nome]"
    location: "[Sua Cidade]"
    instagram: "@[seu_instagram]"
  assistant:
    tone: "profissional, acolhedor e confiante"
    style: "simpático, ágil e atencioso"
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais para realçar seu visual"
```

### Adicionando Mais Serviços

Para adicionar mais serviços, basta incluir novos itens na lista `services`:

```yaml
camarim:
  store:
    name: "Salão Beleza Total"
    location: "São Paulo"
    instagram: "@salao.beleza"
  assistant:
    tone: "profissional, acolhedor e confiante"
    style: "simpático, ágil e atencioso"
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais para realçar seu visual"
    
    - name: "Corte Feminino"
      price: 80.00
      description: "Corte moderno e personalizado"
    
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
```

## Como o Agente Usa os Serviços

O agente receberá automaticamente a lista de serviços e preços no **system prompt**, garantindo que:

1. ✅ Sempre informará preços corretos
2. ✅ Poderá recomendar serviços baseado na descrição
3. ✅ Manterá consistência nas informações
4. ✅ Poderá combinar serviços quando apropriado

## Personalizando o Salão

### Nome e Localização

```yaml
store:
  name: "Seu Salão Aqui"          # Nome do seu salão
  location: "Sua Cidade"            # Cidade onde está localizado
  instagram: "@seu_instagram"       # Seu perfil no Instagram
```

### Tom de Voz da Assistente

```yaml
assistant:
  tone: "profissional, acolhedor e confiante"
  style: "simpático, ágil e atencioso"
```

Você pode personalizar:
- **tone**: formal, informal, descontraído, elegante, etc.
- **style**: dinâmico, calmo, entusiasmado, etc.

## Exemplos de Interação

### Cliente perguntando sobre preço:
**Cliente:** "Quanto custa fazer mecha?"  
**Agente:** "Oi! 😊 O serviço de Mecha de Cabelo custa R$ 100,00. São mechas tradicionais para realçar seu visual. Quer agendar um horário?"

### Cliente perguntando sobre múltiplos serviços:
**Cliente:** "Quais serviços vocês fazem?"  
**Agente:** "Oferecemos vários serviços! 💇‍♀️

- Mecha de Cabelo - R$ 100,00
- Corte Feminino - R$ 80,00  
- Escova - R$ 60,00
- Hidratação - R$ 120,00

Qual te interessa mais?"

## Reiniciando o Servidor

Após modificar o `application.yml`, reinicie o servidor:

```bash
./run.sh
```

Ou com Maven:

```bash
mvn spring-boot:run
```

## Testando

1. Acesse: http://localhost:8082/index.html
2. Pergunte: "Quanto custa mecha de cabelo?"
3. O agente deve responder: "R$ 100,00"

---

**Dica:** Mantenha descrições curtas e objetivas para facilitar a recomendação do agente!


# 🚀 INÍCIO RÁPIDO - Serviço de Mecha R$ 100,00

## ✅ Seu agente está PRONTO!

---

## 🎯 O que já está configurado

```yaml
Serviço: Mecha de Cabelo
Preço: R$ 100,00
Descrição: Mechas tradicionais para realçar seu visual
```

---

## 🏃 Testar AGORA (3 comandos)

```bash
# 1. Configure a API Key da OpenAI
export OPENAI_API_KEY="sk-sua-chave-aqui"

# 2. Inicie o servidor
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent && ./run.sh

# 3. Acesse no navegador
# http://localhost:8082/index.html
```

### Digite no chat:
```
"Quanto custa mecha de cabelo?"
```

### Resposta esperada:
```
"Oi! 😊 O serviço de Mecha de Cabelo custa R$ 100,00. 
São mechas tradicionais para realçar seu visual. 
Quer agendar um horário?"
```

✅ **Funcionou?** Pronto para usar!

---

## ⚙️ Personalizar (1 minuto)

Edite: `src/main/resources/application.yml`

```yaml
camarim:
  store:
    name: "Salão da Maria"        # ← Coloque o nome do seu salão
    location: "São Paulo"          # ← Sua cidade
    instagram: "@salao_maria"      # ← Seu Instagram
```

Reinicie o servidor → Pronto! ✅

---

## ➕ Adicionar Mais Serviços (2 minutos)

No mesmo arquivo `application.yml`:

```yaml
  services:
    - name: "Mecha de Cabelo"
      price: 100.00
      description: "Mechas tradicionais"
    
    # Adicione aqui ↓
    - name: "Corte Feminino"
      price: 80.00
      description: "Corte moderno"
    
    - name: "Escova"
      price: 60.00
      description: "Escova profissional"
    
    - name: "Hidratação"
      price: 120.00
      description: "Tratamento profundo"
```

Reinicie → Todos os serviços disponíveis! ✅

---

## 📚 Quer saber mais?

| Precisa de... | Veja este arquivo |
|---------------|-------------------|
| ⚡ Guia completo de teste | **TESTE_RAPIDO.md** |
| 📝 Como configurar serviços | **SERVICOS.md** |
| 💡 Exemplo com 17 serviços | **EXEMPLO_SALAO.md** |
| 🎯 Visão técnica completa | **CONFIGURACAO_COMPLETA.md** |
| 📊 O que foi modificado | **RESUMO_ALTERACOES.md** |
| 📖 Toda a documentação | **INDEX.md** |

---

## 🆘 Problemas?

### "Connection refused"
→ Servidor não está rodando. Execute `./run.sh`

### "OpenAI API Error"
→ Verifique se a API Key está configurada: `echo $OPENAI_API_KEY`

### Preços errados
→ Confira o `application.yml` e reinicie o servidor

---

## 🎯 Resumo

**✅ Está pronto!**
- Mecha configurada (R$ 100,00)
- Sistema funcionando
- Fácil adicionar serviços
- Documentação completa

**🚀 Próximo passo:**
Teste agora com o comando acima!

---

**Seu salão com assistente virtual! 💇‍♀️✨**


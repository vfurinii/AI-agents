# 🔄 Como Reiniciar o Servidor com Cache Limpo

## Problema
O navegador está mostrando a interface antiga (cache).

## Solução Rápida

### Opção 1: Limpar Cache do Navegador (MAIS RÁPIDO)

1. **Abra o navegador em**: http://localhost:5173

2. **Limpe o cache forçadamente**:
   - **Chrome/Edge**: Pressione `Ctrl + Shift + R` ou `Ctrl + F5`
   - **Firefox**: Pressione `Ctrl + Shift + R` ou `Ctrl + F5`
   - Ou: Abra DevTools (F12) → Clique com botão direito no botão Reload → "Empty Cache and Hard Reload"

3. **Pronto!** A nova interface com logo Camarim deve aparecer.

---

### Opção 2: Reiniciar Servidor com Cache Limpo

1. **Pare o servidor atual** (se estiver rodando):
   - No terminal, pressione `Ctrl + C`
   
   Ou execute:
   ```powershell
   Get-Process | Where-Object {$_.ProcessName -like "*node*"} | Stop-Process -Force
   ```

2. **Limpe o cache do Vite**:
   ```powershell
   cd C:\Users\vitor\IdeaProjects\AI-agents\chatbot-ui
   Remove-Item -Recurse -Force node_modules\.vite -ErrorAction SilentlyContinue
   Remove-Item -Recurse -Force dist -ErrorAction SilentlyContinue
   ```

3. **Inicie o servidor novamente**:
   ```powershell
   npm run dev
   ```
   
   Ou use o arquivo batch:
   ```powershell
   .\start.bat
   ```

4. **Abra o navegador**: http://localhost:5173

5. **Limpe o cache do navegador**: `Ctrl + Shift + R`

---

## ✅ O Que Deve Aparecer

Após limpar o cache, você deve ver:

- ✨ **Logo Camarim** no canto superior esquerdo
- 👑 **"Camarim AI Assistant"** como título (não mais "AI Chatbot")
- 🎨 **Cores douradas** (não mais verde/roxo)
- 💬 **Interface elegante** com gradientes dourados
- 📜 **Texto de boas-vindas** atualizado

---

## 🐛 Se ainda não funcionar

### Verifique os arquivos:

```powershell
# Verifique se o App.jsx tem o logo importado
Get-Content C:\Users\vitor\IdeaProjects\AI-agents\chatbot-ui\src\App.jsx | Select-String "logoCamarim"

# Deve retornar:
# import logoCamarim from './logo-camarim.jpg'
# <img src={logoCamarim} ...
```

### Abra o modo privado/anônimo do navegador:
- Isso ignora completamente o cache
- Chrome: `Ctrl + Shift + N`
- Firefox: `Ctrl + Shift + P`
- Edge: `Ctrl + Shift + N`

### Inspecione o Console do Navegador:
1. Abra DevTools (F12)
2. Vá para a aba "Console"
3. Procure por erros em vermelho
4. Se houver erro 404 no logo, verifique se `src/logo-camarim.jpg` existe

---

## 📝 Comandos Úteis

### Ver processos Node rodando:
```powershell
Get-Process | Where-Object {$_.ProcessName -like "*node*"}
```

### Matar todos os processos Node:
```powershell
Get-Process | Where-Object {$_.ProcessName -like "*node*"} | Stop-Process -Force
```

### Limpar cache e reiniciar (tudo em um comando):
```powershell
cd C:\Users\vitor\IdeaProjects\AI-agents\chatbot-ui; `
Remove-Item -Recurse -Force node_modules\.vite -ErrorAction SilentlyContinue; `
npm run dev
```

---

## 🎯 Solução Recomendada

**A forma mais rápida é simplesmente fazer um Hard Reload no navegador:**

1. Abra: http://localhost:5173
2. Pressione: **`Ctrl + Shift + R`**
3. ✅ Pronto!

Isso força o navegador a ignorar o cache e baixar todos os arquivos novamente do servidor.


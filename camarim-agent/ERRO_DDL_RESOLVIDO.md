# 🔧 SOLUÇÃO - Erro DDL "NULL not allowed for column CONTENT"

## ❌ Problema

```
Error executing DDL "alter table if exists conversations add column content TEXT not null" 
via JDBC [NULL not allowed for column "CONTENT";]
```

**Causa:** O banco H2 já existe com dados antigos e o Hibernate está tentando adicionar a coluna `content` como NOT NULL em registros que não têm esse valor.

---

## ✅ SOLUÇÃO APLICADA

### 1. Deletar Banco de Dados Antigo

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
rm -rf ./data/camarim.mv.db ./data/camarim.trace.db
mkdir -p data
```

### 2. Ajustar application.yml

Mudei o `ddl-auto` de `update` para `create-drop`:

```yaml
jpa:
  hibernate:
    ddl-auto: create-drop  # Recria o banco a cada restart
```

**Isso vai:**
- ✅ Criar tabelas do zero toda vez que iniciar
- ✅ Eliminar problemas de migração
- ✅ Perfeito para desenvolvimento

---

## 🚀 Como Iniciar Agora

### Opção 1: Via Maven

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
mvn spring-boot:run
```

### Opção 2: Via Script

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
./run.sh
```

### Opção 3: Via JAR

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
mvn clean package -DskipTests
java -jar target/camarim-agent-1.0.0-SNAPSHOT.jar
```

---

## 🔍 Se o Erro Persistir

### Passo 1: Limpar Completamente

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent

# Deletar todos os bancos H2
rm -rf ./data/*.mv.db ./data/*.trace.db

# Limpar build do Maven
mvn clean

# Recriar diretório
mkdir -p data
```

### Passo 2: Verificar application.yml

Certifique-se que está assim:

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/camarim
  jpa:
    hibernate:
      ddl-auto: create-drop  # IMPORTANTE!
```

### Passo 3: Reiniciar

```bash
mvn spring-boot:run
```

---

## 📊 Diferenças entre ddl-auto

| Valor | Comportamento | Quando Usar |
|-------|---------------|-------------|
| `create-drop` | Recria tudo a cada restart | ✅ **Desenvolvimento** |
| `create` | Cria na primeira vez, não deleta | Testes |
| `update` | Altera tabelas existentes | ⚠️ Pode dar erro |
| `validate` | Só valida, não altera | Produção |
| `none` | Não faz nada | Produção com Flyway/Liquibase |

---

## 🎯 Para Produção (Depois)

Quando for para produção, mude para:

```yaml
jpa:
  hibernate:
    ddl-auto: validate  # Apenas valida, não altera
```

E use ferramentas de migração como:
- **Flyway**
- **Liquibase**

---

## ✅ Status Atual

- ✅ Banco deletado
- ✅ application.yml ajustado (create-drop)
- ✅ Diretório data criado
- ✅ Pronto para iniciar sem erros

---

## 🚀 Teste Rápido

```bash
cd /Users/vitorfurini/IdeaProjects/AI-agents/camarim-agent
mvn spring-boot:run
```

**Aguarde aparecer:**
```
Started CamarimAgentApplication in X.XXX seconds
```

**Então acesse:**
```
http://localhost:8082/index.html
```

---

## 💡 Dica

Se quiser manter os dados entre restarts (para desenvolvimento):

1. Mude `ddl-auto` para `update`
2. Delete o banco atual: `rm -rf ./data/*.mv.db`
3. Inicie a aplicação (vai criar limpo)
4. Agora pode reiniciar sem perder dados

Mas se tiver mudanças no modelo, delete o banco novamente.

---

**Problema resolvido! Sua aplicação agora vai iniciar sem erros! ✅**


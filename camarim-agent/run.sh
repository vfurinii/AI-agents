#!/bin/bash

echo "🛍️  Camarim POA - Assistente Virtual"
echo "====================================="
echo ""

# Verifica se OPENAI_API_KEY está definida
if [ -z "$OPENAI_API_KEY" ]; then
    echo "⚠️  ATENÇÃO: A variável OPENAI_API_KEY não está definida!"
    echo ""
    echo "Por favor, defina sua chave da OpenAI:"
    echo "export OPENAI_API_KEY='sua-chave-aqui'"
    echo ""
    read -p "Deseja continuar mesmo assim? (s/n) " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Ss]$ ]]; then
        exit 1
    fi
fi

# Verifica se Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale Java 17 ou superior."
    exit 1
fi

# Verifica versão do Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 ou superior é necessário. Versão atual: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java $JAVA_VERSION detectado"

# Verifica se Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale Maven 3.6+."
    exit 1
fi

echo "✅ Maven detectado"
echo ""

# Cria diretório de dados se não existir
mkdir -p data

# Compila o projeto
echo "📦 Compilando o projeto..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi

echo ""
echo "✅ Compilação concluída com sucesso!"
echo ""
echo "🚀 Iniciando a aplicação..."
echo ""
echo "   API: http://localhost:8082/api/chat"
echo "   Interface Web: http://localhost:8082/index.html"
echo "   H2 Console: http://localhost:8082/h2-console"
echo ""
echo "Pressione Ctrl+C para parar a aplicação"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# Executa a aplicação
java -jar target/camarim-agent-1.0.0-SNAPSHOT.jar


#!/bin/bash

# Test Script para Camarim POA Assistant
# Testa os principais endpoints da API

echo "🧪 Testando Camarim POA Assistant"
echo "=================================="
echo ""

API_URL="http://localhost:8082/api/chat"

# Cores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para testar endpoint
test_endpoint() {
    local name=$1
    local method=$2
    local url=$3
    local data=$4

    echo -n "Testing $name... "

    if [ "$method" = "POST" ]; then
        response=$(curl -s -w "\n%{http_code}" -X POST "$url" \
            -H "Content-Type: application/json" \
            -d "$data")
    elif [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$url")
    elif [ "$method" = "DELETE" ]; then
        response=$(curl -s -w "\n%{http_code}" -X DELETE "$url")
    fi

    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')

    if [ "$http_code" = "200" ]; then
        echo -e "${GREEN}✓ PASS${NC} (HTTP $http_code)"
        [ ! -z "$body" ] && echo "   Response: $(echo $body | head -c 100)..."
    else
        echo -e "${RED}✗ FAIL${NC} (HTTP $http_code)"
        [ ! -z "$body" ] && echo "   Error: $body"
    fi
    echo ""
}

# Verifica se a aplicação está rodando
echo "1. Verificando se a aplicação está rodando..."
if curl -s http://localhost:8082/api/chat/health > /dev/null; then
    echo -e "${GREEN}✓ Aplicação está rodando!${NC}"
else
    echo -e "${RED}✗ Aplicação não está rodando!${NC}"
    echo "Por favor, execute './run.sh' primeiro"
    exit 1
fi
echo ""

# Teste 1: Health Check
test_endpoint "Health Check" "GET" "$API_URL/health"

# Teste 2: Primeira mensagem (sem sessionId)
echo "2. Testando primeira mensagem..."
RESPONSE=$(curl -s -X POST "$API_URL" \
    -H "Content-Type: application/json" \
    -d '{"message": "Olá! Vocês têm vestidos?"}')

if echo "$RESPONSE" | grep -q "sessionId"; then
    SESSION_ID=$(echo "$RESPONSE" | grep -o '"sessionId":"[^"]*' | cut -d'"' -f4)
    echo -e "${GREEN}✓ Primeira mensagem enviada com sucesso!${NC}"
    echo "   SessionId: $SESSION_ID"
    echo "   Resposta: $(echo $RESPONSE | grep -o '"response":"[^"]*' | cut -d'"' -f4 | head -c 80)..."
else
    echo -e "${RED}✗ Falha ao enviar primeira mensagem${NC}"
    echo "   Response: $RESPONSE"
fi
echo ""

# Teste 3: Mensagem com sessionId
if [ ! -z "$SESSION_ID" ]; then
    echo "3. Testando mensagem com sessionId..."
    test_endpoint "Mensagem com sessão" "POST" "$API_URL" \
        "{\"sessionId\": \"$SESSION_ID\", \"message\": \"Quais os preços?\"}"

    # Teste 4: Contar mensagens
    echo "4. Testando contagem de mensagens..."
    test_endpoint "Contagem de mensagens" "GET" "$API_URL/session/$SESSION_ID/count"

    # Teste 5: Limpar sessão
    echo "5. Testando limpeza de sessão..."
    test_endpoint "Limpar sessão" "DELETE" "$API_URL/session/$SESSION_ID"
fi

echo ""
echo "=================================="
echo "🎉 Testes concluídos!"
echo ""

# Teste adicional: Múltiplas mensagens em sequência
echo "Teste Extra: Conversação completa"
echo "-----------------------------------"

# Criar nova sessão
RESPONSE=$(curl -s -X POST "$API_URL" \
    -H "Content-Type: application/json" \
    -d '{"message": "Oi!"}')
SESSION_ID=$(echo "$RESPONSE" | grep -o '"sessionId":"[^"]*' | cut -d'"' -f4)

if [ ! -z "$SESSION_ID" ]; then
    echo "Nova sessão criada: $SESSION_ID"
    echo ""

    # Série de perguntas
    questions=(
        "Vocês têm blusas?"
        "Quais cores?"
        "Qual o preço?"
        "Aceitam cartão?"
        "Obrigada!"
    )

    for question in "${questions[@]}"; do
        echo -e "${YELLOW}👤 Usuário:${NC} $question"
        RESPONSE=$(curl -s -X POST "$API_URL" \
            -H "Content-Type: application/json" \
            -d "{\"sessionId\": \"$SESSION_ID\", \"message\": \"$question\"}")

        REPLY=$(echo "$RESPONSE" | grep -o '"response":"[^"]*' | cut -d'"' -f4)
        echo -e "${GREEN}🤖 Assistente:${NC} $(echo $REPLY | head -c 100)..."
        echo ""
        sleep 1
    done

    # Limpar sessão de teste
    curl -s -X DELETE "$API_URL/session/$SESSION_ID" > /dev/null
    echo "Sessão de teste limpa."
fi

echo ""
echo "=================================="
echo "✨ Todos os testes concluídos!"
echo ""
echo "Para mais informações:"
echo "  - Documentação: README.md"
echo "  - Exemplos de API: API_EXAMPLES.md"
echo "  - Interface Web: http://localhost:8082/index.html"
echo ""


import { useState, useRef, useEffect } from 'react'
import './App.css'
import ChatMessage from './components/ChatMessage'
import ChatInput from './components/ChatInput'
import axios from 'axios'
import logoCamarim from './logo-camarim.jpg'

function App() {
  const [messages, setMessages] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const chatContainerRef = useRef(null)

  useEffect(() => {
    // Scroll to bottom when messages change
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight
    }
  }, [messages, isLoading])

  const sendMessage = async (messageText) => {
    if (!messageText.trim()) return

    // Add user message
    const userMessage = {
      id: Date.now(),
      text: messageText,
      isUser: true,
      timestamp: new Date()
    }

    setMessages(prev => [...prev, userMessage])
    setIsLoading(true)

    try {
      const response = await axios.post('/chat', messageText, {
        headers: {
          'Content-Type': 'text/plain'
        }
      })

      // Add assistant response
      const assistantMessage = {
        id: Date.now() + 1,
        text: response.data,
        isUser: false,
        timestamp: new Date()
      }

      setMessages(prev => [...prev, assistantMessage])
    } catch (error) {
      console.error('Error sending message:', error)

      // Add error message
      const errorMessage = {
        id: Date.now() + 1,
        text: 'Sorry, I encountered an error. Please try again.',
        isUser: false,
        isError: true,
        timestamp: new Date()
      }

      setMessages(prev => [...prev, errorMessage])
    } finally {
      setIsLoading(false)
    }
  }

  const clearChat = () => {
    setMessages([])
  }

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-brand">
          <img src={logoCamarim} alt="Camarim Logo" className="header-logo" />
          <h1>Camarim AI Assistant</h1>
        </div>
        {messages.length > 0 && (
          <button className="clear-btn" onClick={clearChat}>
            Limpar Chat
          </button>
        )}
      </header>

      <div className="chat-container" ref={chatContainerRef}>
        {messages.length === 0 ? (
          <div className="empty-state">
            <h2>Bem-vindo ao Camarim AI Assistant</h2>
            <p>Inicie uma conversa digitando sua mensagem abaixo. Estou aqui para ajudar com suas dúvidas!</p>
            <div className="suggestions">
              <button onClick={() => sendMessage("Como você pode me ajudar?")}>
                ❓ Como pode ajudar?
              </button>
              <button onClick={() => sendMessage("Quais serviços vocês oferecem?")}>
                💼 Serviços disponíveis
              </button>
              <button onClick={() => sendMessage("Preciso de orientação")}>
                🤝 Preciso de ajuda
              </button>
            </div>
          </div>
        ) : (
          <div className="messages">
            {messages.map(message => (
              <ChatMessage key={message.id} message={message} />
            ))}
            {isLoading && (
              <div className="loading-container">
                <div className="message-bubble assistant">
                  <div className="message-icon">AI</div>
                  <div className="loading">
                    <div className="loading-dot"></div>
                    <div className="loading-dot"></div>
                    <div className="loading-dot"></div>
                  </div>
                </div>
              </div>
            )}
          </div>
        )}
      </div>

      <ChatInput onSend={sendMessage} disabled={isLoading} />
    </div>
  )
}

export default App


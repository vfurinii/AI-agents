import { useState, useRef, useEffect } from 'react'
import './App.css'
import ChatMessage from './components/ChatMessage'
import ChatInput from './components/ChatInput'
import axios from 'axios'

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
        <h1>🤖 AI Chatbot</h1>
        {messages.length > 0 && (
          <button className="clear-btn" onClick={clearChat}>
            Clear Chat
          </button>
        )}
      </header>

      <div className="chat-container" ref={chatContainerRef}>
        {messages.length === 0 ? (
          <div className="empty-state">
            <h2>Welcome to AI Chatbot</h2>
            <p>Start a conversation by typing a message below. Ask questions, get weather information, or just chat!</p>
            <div className="suggestions">
              <button onClick={() => sendMessage("What's the weather in São Paulo?")}>
                🌤️ What's the weather?
              </button>
              <button onClick={() => sendMessage("Tell me a joke")}>
                😄 Tell me a joke
              </button>
              <button onClick={() => sendMessage("How can you help me?")}>
                ❓ How can you help?
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


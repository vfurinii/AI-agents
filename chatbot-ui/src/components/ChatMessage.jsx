import React from 'react'
import './ChatMessage.css'

const ChatMessage = ({ message }) => {
  return (
    <div className={`message-bubble ${message.isUser ? 'user' : 'assistant'} ${message.isError ? 'error' : ''}`}>
      <div className="message-icon">
        {message.isUser ? '👤' : '🤖'}
      </div>
      <div className="message-content">
        <div className="message-text">{message.text}</div>
        <div className="message-time">
          {message.timestamp.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
        </div>
      </div>
    </div>
  )
}

export default ChatMessage


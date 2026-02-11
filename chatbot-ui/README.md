# AI Chatbot - React UI

A modern, responsive chat interface for the AI Chatbot backend built with React and Vite.

## Features

✨ **Modern UI Design** - Beautiful ChatGPT-inspired interface with smooth animations
🎨 **Dark Theme** - Easy on the eyes with a sleek dark color scheme
💬 **Real-time Chat** - Seamless communication with the AI backend
📱 **Responsive** - Works great on desktop and mobile devices
⚡ **Fast** - Built with Vite for lightning-fast development and production builds
🎯 **Smart Suggestions** - Quick-start buttons to begin conversations
⌨️ **Keyboard Shortcuts** - Press Enter to send, Shift+Enter for new lines

## Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Java Spring Boot backend running on port 8080

## Installation

1. Install dependencies:
```bash
npm install
```

## Running the Application

1. Make sure your Spring Boot backend is running on `http://localhost:8080`

2. Start the development server:
```bash
npm run dev
```

3. Open your browser and navigate to `http://localhost:3000`

## Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## Preview Production Build

```bash
npm run preview
```

## Project Structure

```
chatbot-ui/
├── src/
│   ├── components/
│   │   ├── ChatMessage.jsx      # Message bubble component
│   │   ├── ChatMessage.css
│   │   ├── ChatInput.jsx        # Input field component
│   │   └── ChatInput.css
│   ├── App.jsx                  # Main application component
│   ├── App.css
│   ├── main.jsx                 # Application entry point
│   └── index.css
├── index.html
├── vite.config.js              # Vite configuration with proxy
└── package.json
```

## API Integration

The app connects to your Spring Boot backend at `http://localhost:8080/chat` via a proxy configuration in Vite. The proxy is set up in `vite.config.js`.

## Technologies Used

- **React 18** - UI framework
- **Vite** - Build tool and dev server
- **Axios** - HTTP client
- **CSS3** - Styling with animations

## Usage

1. Type your message in the input field at the bottom
2. Press **Enter** to send (or click the send button)
3. Use **Shift+Enter** to add a new line
4. Click suggested questions to quickly start a conversation
5. Use the **Clear Chat** button to reset the conversation

## Customization

### Colors
Edit the CSS files to change the color scheme:
- Primary: `#19C37D` (green)
- Secondary: `#5436DA` (purple)
- Background: `#343541` (dark gray)

### Backend URL
If your backend runs on a different port, update `vite.config.js`:
```javascript
proxy: {
  '/chat': {
    target: 'http://localhost:YOUR_PORT',
    changeOrigin: true,
  }
}
```

## License

MIT


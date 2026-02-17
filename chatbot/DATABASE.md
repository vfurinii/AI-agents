# Database Implementation

## Overview
The chatbot now uses **H2 Database** to persist conversation history. This means your conversations are saved even after restarting the application.

## Key Changes

### 1. Database Configuration
- **Database**: H2 (file-based)
- **Location**: `./data/chatbot.mv.db`
- **H2 Console**: Available at `http://localhost:8080/h2-console`

### 2. Message Entity
- Messages are now stored in a `messages` table
- Each message has:
  - `id` (auto-generated)
  - `role` (user/assistant)
  - `content` (the message text)
  - `timestamp` (when the message was created)
  - `session_id` (for future multi-user support)

### 3. Memory Service
The `MemoryService` now:
- Saves messages to the database instead of in-memory storage
- Automatically trims old messages (keeps last 10 conversation pairs = 20 messages)
- Retrieves conversation history from database

### 4. Benefits
✅ **Persistent Storage**: Conversations survive application restarts
✅ **Scalability**: Can be easily migrated to PostgreSQL/MySQL in production
✅ **Multi-session Support**: Ready to support multiple users
✅ **Query Capabilities**: Can analyze conversation history with SQL

## API Endpoints

### Chat
```bash
POST http://localhost:8080/chat
Content-Type: text/plain

Hello, how are you?
```

### Clear History
```bash
DELETE http://localhost:8080/chat/history
```

## Database Access

### H2 Console
Access the database console at: `http://localhost:8080/h2-console`

**Connection Settings:**
- JDBC URL: `jdbc:h2:file:./data/chatbot`
- Username: `sa`
- Password: (leave empty)

### SQL Queries
```sql
-- View all messages
SELECT * FROM messages ORDER BY timestamp;

-- Count messages by role
SELECT role, COUNT(*) FROM messages GROUP BY role;

-- Recent messages
SELECT * FROM messages ORDER BY timestamp DESC LIMIT 10;
```

## Migration to Production Database

To use PostgreSQL or MySQL in production, update `application.yml`:

### PostgreSQL Example
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/chatbot
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

And add the dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Troubleshooting

### Database locked error
If you get a "database is locked" error, make sure only one instance of the application is running.

### Reset database
To clear all data and reset the database:
1. Stop the application
2. Delete the `data` folder
3. Restart the application


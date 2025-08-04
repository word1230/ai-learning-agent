<template>
  <div class="learning-chat">
    <!-- 头部导航 -->
    <header class="chat-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
          <path d="M19 12H5M12 19L5 12L12 5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </button>
      <div class="header-info">
        <h1 class="chat-title">AI 学习助手</h1>
        <span class="chat-id">会话ID: {{ chatId }}</span>
      </div>
      <div class="status-indicator" :class="{ connected: isConnected }">
        {{ isConnected ? '已连接' : '未连接' }}
      </div>
    </header>

    <!-- 聊天消息区域 -->
    <div class="chat-messages" ref="messagesContainer">
      <div class="messages-container">
        <div class="welcome-message" v-if="messages.length === 0">
          <div class="welcome-icon">🤖</div>
          <h3>欢迎使用AI学习助手！</h3>
          <p>我是您的智能学习伙伴，有什么问题都可以问我哦～</p>
        </div>

        <div
          v-for="message in messages"
          :key="message.id"
          class="message-wrapper"
          :class="{ 'user-message': message.isUser, 'ai-message': !message.isUser }"
        >
          <div class="message-content">
            <div class="message-avatar">
              <div v-if="message.isUser" class="user-avatar">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                  <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </div>
              <div v-else class="ai-avatar">
                <div class="ai-avatar-core">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                    <path d="M12 2L13.09 8.26L22 9L13.09 9.74L12 16L10.91 9.74L2 9L10.91 8.26L12 2Z" fill="url(#avatarGradient)"/>
                    <defs>
                      <linearGradient id="avatarGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                        <stop offset="0%" style="stop-color:#00f5ff"/>
                        <stop offset="50%" style="stop-color:#ff00ff"/>
                        <stop offset="100%" style="stop-color:#ffff00"/>
                      </linearGradient>
                    </defs>
                  </svg>
                </div>
                <div class="ai-avatar-ring"></div>
              </div>
            </div>
            <div class="message-bubble">
              <div class="message-text" v-html="formatMessage(message.content)"></div>
              <div class="message-time">{{ formatTime(message.timestamp) }}</div>
            </div>
          </div>
        </div>

        <!-- AI正在输入指示器 -->
        <div v-if="isAiTyping" class="message-wrapper ai-message">
          <div class="message-content">
            <div class="message-avatar">
              <div class="ai-avatar">
                <div class="ai-avatar-core">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                    <path d="M12 2L13.09 8.26L22 9L13.09 9.74L12 16L10.91 9.74L2 9L10.91 8.26L12 2Z" fill="url(#typingGradient)"/>
                    <defs>
                      <linearGradient id="typingGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                        <stop offset="0%" style="stop-color:#00f5ff"/>
                        <stop offset="50%" style="stop-color:#ff00ff"/>
                        <stop offset="100%" style="stop-color:#ffff00"/>
                      </linearGradient>
                    </defs>
                  </svg>
                </div>
                <div class="ai-avatar-ring"></div>
              </div>
            </div>
            <div class="message-bubble typing">
              <div class="typing-indicator">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 输入区域 -->
    <div class="chat-input-area">
      <div class="input-wrapper">
        <div class="input-container">
          <textarea
            v-model="inputMessage"
            @keydown="handleKeyDown"
            placeholder="输入您的问题..."
            class="message-input"
            rows="1"
            ref="messageInput"
          ></textarea>
          <button
            @click="sendMessage"
            :disabled="!inputMessage.trim() || isLoading"
            class="send-btn"
          >
            <svg v-if="!isLoading" width="20" height="20" viewBox="0 0 24 24" fill="none">
              <path d="M22 2L11 13M22 2L15 22L11 13M22 2L2 9L11 13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <div v-else class="loading-spinner"></div>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, nextTick, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { generateChatId, formatMessageContent, formatTimestamp } from '../utils/chatUtils'
import { createSSEConnection } from '../services/chatService'

export default {
  name: 'LearningChat',
  setup() {
    const router = useRouter()
    const chatId = ref('')
    const messages = ref([])
    const inputMessage = ref('')
    const isLoading = ref(false)
    const isAiTyping = ref(false)
    const isConnected = ref(false)
    const messagesContainer = ref(null)
    const messageInput = ref(null)
    let currentEventSource = null

    // 生成聊天ID
    onMounted(() => {
      chatId.value = generateChatId()
      isConnected.value = true
    })

    // 清理资源
    onUnmounted(() => {
      if (currentEventSource) {
        currentEventSource.close()
      }
    })

    // 返回主页
    const goBack = () => {
      if (currentEventSource) {
        currentEventSource.close()
      }
      router.push('/')
    }

    // 滚动到底部
    const scrollToBottom = () => {
      nextTick(() => {
        if (messagesContainer.value) {
          messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
        }
      })
    }

    // 发送消息
    const sendMessage = async () => {
      if (!inputMessage.value.trim() || isLoading.value) return

      const userMessage = {
        id: Date.now(),
        content: inputMessage.value.trim(),
        isUser: true,
        timestamp: new Date()
      }

      messages.value.push(userMessage)
      const query = inputMessage.value.trim()
      inputMessage.value = ''
      
      scrollToBottom()
      
      // 开始AI响应
      isLoading.value = true
      isAiTyping.value = true
      
      try {
        await handleSSEChat(query)
      } catch (error) {
        console.error('发送消息失败:', error)
        // 添加错误消息
        messages.value.push({
          id: Date.now(),
          content: '抱歉，发送消息时出现错误，请稍后重试。',
          isUser: false,
          timestamp: new Date()
        })
      } finally {
        isLoading.value = false
        isAiTyping.value = false
        scrollToBottom()
      }
    }

    // 处理SSE聊天
    const handleSSEChat = (query) => {
      return new Promise((resolve, reject) => {
        // 关闭之前的连接
        if (currentEventSource) {
          currentEventSource.close()
        }

        const aiMessage = {
          id: Date.now() + 1,
          content: '',
          isUser: false,
          timestamp: new Date()
        }
        
        messages.value.push(aiMessage)
        isAiTyping.value = false
        
        currentEventSource = createSSEConnection(query, chatId.value, {
          onMessage: (data) => {
            // 累积AI响应内容
            aiMessage.content += data
            scrollToBottom()
          },
          onComplete: () => {
            resolve()
          },
          onError: (error) => {
            reject(error)
          }
        })
      })
    }

    // 键盘事件处理
    const handleKeyDown = (event) => {
      if (event.key === 'Enter' && !event.shiftKey) {
        event.preventDefault()
        sendMessage()
      }
    }

    // 格式化消息内容
    const formatMessage = (content) => {
      return formatMessageContent(content)
    }

    // 格式化时间
    const formatTime = (timestamp) => {
      return formatTimestamp(timestamp)
    }

    return {
      chatId,
      messages,
      inputMessage,
      isLoading,
      isAiTyping,
      isConnected,
      messagesContainer,
      messageInput,
      goBack,
      sendMessage,
      handleKeyDown,
      formatMessage,
      formatTime
    }
  }
}
</script>

<style scoped>
.learning-chat {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg-primary);
  position: relative;
}

/* 背景装饰 */
.learning-chat::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image:
    linear-gradient(rgba(0, 245, 255, 0.02) 1px, transparent 1px),
    linear-gradient(90deg, rgba(0, 245, 255, 0.02) 1px, transparent 1px);
  background-size: 30px 30px;
  pointer-events: none;
  z-index: 1;
}

/* 头部样式 */
.chat-header {
  background: var(--bg-secondary);
  backdrop-filter: blur(20px);
  padding: var(--spacing-md) var(--spacing-xl);
  border-bottom: 1px solid var(--border-secondary);
  display: flex;
  align-items: center;
  gap: var(--spacing-lg);
  box-shadow: var(--shadow-card);
  position: relative;
  z-index: 10;
  flex-shrink: 0; /* 防止头部被压缩 */
}

.chat-header::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: var(--primary-gradient);
  opacity: 0.5;
}

.back-btn {
  background: var(--bg-glass);
  border: 1px solid var(--border-secondary);
  cursor: pointer;
  padding: var(--spacing-sm);
  border-radius: var(--radius-md);
  color: var(--text-secondary);
  transition: all var(--transition-normal);
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
}

.back-btn:hover {
  background: var(--accent-cyan);
  color: var(--bg-primary);
  border-color: var(--accent-cyan);
  box-shadow: 0 0 15px var(--accent-cyan);
  transform: translateX(-2px);
}

.header-info {
  flex: 1;
}

.chat-title {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.chat-id {
  font-size: 0.8rem;
  color: var(--text-muted);
  font-family: 'Courier New', monospace;
  margin-top: var(--spacing-xs);
}

.status-indicator {
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-xl);
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  background: var(--bg-glass);
  border: 1px solid var(--border-secondary);
  color: var(--text-muted);
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
}

.status-indicator::before {
  content: '';
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--text-muted);
  animation: pulse 2s ease-in-out infinite;
}

.status-indicator.connected {
  background: rgba(0, 255, 65, 0.1);
  color: var(--accent-green);
  border-color: var(--accent-green);
}

.status-indicator.connected::before {
  background: var(--accent-green);
}

/* 消息区域样式 */
.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: var(--spacing-lg) var(--spacing-xl);
  position: relative;
  z-index: 2;
  display: flex;
  justify-content: center;
  min-height: 0; /* 确保flex子项可以收缩 */
}

.messages-container {
  width: 100%;
  max-width: 900px;
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
  margin: 0 auto;
  position: relative;
  padding: 0 var(--spacing-md);
  height: 100%; /* 占满父容器高度 */
}

.messages-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(to bottom, transparent, var(--accent-cyan), transparent);
  opacity: 0.1;
}

.messages-container::after {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(to bottom, transparent, var(--accent-magenta), transparent);
  opacity: 0.1;
}

.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: var(--bg-secondary);
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: var(--accent-cyan);
  border-radius: 3px;
}

.welcome-message {
  text-align: center;
  padding: var(--spacing-xl);
  color: var(--text-secondary);
  background: var(--bg-glass);
  border: 1px solid var(--border-secondary);
  border-radius: var(--radius-xl);
  backdrop-filter: blur(10px);
  animation: slideInUp 0.6s ease forwards;
  margin: var(--spacing-lg) 0;
}

.welcome-icon {
  font-size: 4rem;
  margin-bottom: var(--spacing-lg);
  animation: float 3s ease-in-out infinite;
}

.welcome-message h3 {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: var(--spacing-sm);
  color: var(--text-primary);
}

.welcome-message p {
  color: var(--text-secondary);
  line-height: 1.6;
}

.message-wrapper {
  display: flex;
  margin-bottom: var(--spacing-md);
  animation: slideInUp 0.3s ease forwards;
}

.user-message {
  justify-content: flex-end;
}

.ai-message {
  justify-content: flex-start;
}

.message-content {
  display: flex;
  align-items: flex-end;
  gap: var(--spacing-md);
  max-width: 80%;
}

.user-message .message-content {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  position: relative;
  border: 2px solid var(--border-secondary);
  background: var(--bg-secondary);
}

.user-avatar {
  background: var(--bg-glass);
  color: var(--accent-cyan);
  border-color: var(--accent-cyan);
}

.ai-avatar {
  background: var(--bg-primary);
  border-color: var(--accent-magenta);
  position: relative;
  overflow: hidden;
}

.ai-avatar-core {
  position: relative;
  z-index: 2;
  animation: float 3s ease-in-out infinite;
}

.ai-avatar-ring {
  position: absolute;
  top: -2px;
  left: -2px;
  right: -2px;
  bottom: -2px;
  border-radius: 50%;
  background: conic-gradient(from 0deg, var(--accent-cyan), var(--accent-magenta), var(--accent-yellow), var(--accent-cyan));
  animation: rotate 3s linear infinite;
  z-index: 1;
}

.ai-avatar::before {
  content: '';
  position: absolute;
  top: 2px;
  left: 2px;
  right: 2px;
  bottom: 2px;
  background: var(--bg-primary);
  border-radius: 50%;
  z-index: 1;
}

.message-bubble {
  background: var(--bg-card);
  backdrop-filter: blur(10px);
  border: 1px solid var(--border-secondary);
  border-radius: var(--radius-lg);
  padding: var(--spacing-md) var(--spacing-lg);
  position: relative;
  box-shadow: var(--shadow-card);
  min-width: 120px;
  max-width: 100%;
  word-wrap: break-word;
  overflow-wrap: break-word;
}

.user-message .message-bubble {
  background: var(--accent-cyan);
  color: var(--bg-primary);
  border-color: var(--accent-cyan);
}

.ai-message .message-bubble {
  background: var(--bg-glass);
  border-color: var(--border-primary);
}

.message-text {
  line-height: 1.6;
  word-wrap: break-word;
  text-align: left; /* 确保文字左对齐 */
}

.user-message .message-text {
  text-align: left; /* 用户消息也左对齐 */
  color: var(--bg-primary);
}

.ai-message .message-text {
  color: var(--text-primary);
}

.message-time {
  font-size: 0.7rem;
  opacity: 0.7;
  margin-top: var(--spacing-xs);
  font-family: 'Courier New', monospace;
  text-align: left; /* 时间也左对齐 */
}

.user-message .message-time {
  color: rgba(10, 10, 10, 0.7);
}

.ai-message .message-time {
  color: var(--text-muted);
}

/* 正在输入指示器 */
.typing {
  background: var(--bg-glass) !important;
  border-color: var(--accent-cyan) !important;
}

.typing-indicator {
  display: flex;
  gap: var(--spacing-xs);
  padding: var(--spacing-sm) 0;
}

.typing-indicator span {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--accent-cyan);
  animation: typing 1.4s infinite ease-in-out;
}

.typing-indicator span:nth-child(1) { animation-delay: -0.32s; }
.typing-indicator span:nth-child(2) { animation-delay: -0.16s; }

@keyframes typing {
  0%, 80%, 100% { transform: scale(0.8); opacity: 0.5; }
  40% { transform: scale(1); opacity: 1; }
}

/* 输入区域样式 */
.chat-input-area {
  background: var(--bg-secondary);
  backdrop-filter: blur(20px);
  border-top: 1px solid var(--border-secondary);
  padding: var(--spacing-md) var(--spacing-xl);
  position: relative;
  z-index: 10;
  display: flex;
  justify-content: center;
  flex-shrink: 0; /* 防止输入区域被压缩 */
}

.input-wrapper {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding: 0 var(--spacing-md);
}

.chat-input-area::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: var(--primary-gradient);
  opacity: 0.3;
}

.input-container {
  display: flex;
  align-items: flex-end;
  gap: var(--spacing-md);
  max-width: 100%;
  background: var(--bg-glass);
  border: 1px solid var(--border-secondary);
  border-radius: var(--radius-xl);
  padding: var(--spacing-sm);
  backdrop-filter: blur(10px);
}

.message-input {
  flex: 1;
  border: none;
  background: transparent;
  padding: var(--spacing-md) var(--spacing-lg);
  font-size: 1rem;
  line-height: 1.5;
  resize: none;
  min-height: 44px;
  max-height: 120px;
  font-family: inherit;
  color: var(--text-primary);
  transition: all var(--transition-normal);
}

.message-input::placeholder {
  color: var(--text-muted);
}

.message-input:focus {
  outline: none;
  color: var(--text-primary);
}

.send-btn {
  background: var(--accent-cyan);
  color: var(--bg-primary);
  border: none;
  border-radius: var(--radius-lg);
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all var(--transition-normal);
  flex-shrink: 0;
  position: relative;
  overflow: hidden;
}

.send-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left var(--transition-slow);
}

.send-btn:hover::before {
  left: 100%;
}

.send-btn:hover:not(:disabled) {
  background: var(--accent-magenta);
  transform: translateY(-2px) scale(1.05);
  box-shadow: 0 0 20px var(--accent-magenta);
}

.send-btn:disabled {
  background: var(--text-muted);
  cursor: not-allowed;
  transform: none;
  opacity: 0.5;
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid transparent;
  border-top: 2px solid currentColor;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .messages-container,
  .input-wrapper {
    max-width: 800px;
  }
}

@media (max-width: 1024px) {
  .messages-container,
  .input-wrapper {
    max-width: 700px;
  }

  .message-content {
    max-width: 85%;
  }
}

@media (max-width: 768px) {
  .messages-container,
  .input-wrapper {
    max-width: 100%;
  }

  .chat-header {
    padding: var(--spacing-sm) var(--spacing-lg);
  }

  .chat-messages {
    padding: var(--spacing-md) var(--spacing-lg);
  }

  .chat-input-area {
    padding: var(--spacing-sm) var(--spacing-lg);
  }

  .message-content {
    max-width: 90%;
  }

  .chat-title {
    font-size: 1.1rem;
  }

  .chat-id {
    font-size: 0.7rem;
  }

  .message-avatar {
    width: 36px;
    height: 36px;
  }

  .message-bubble {
    padding: var(--spacing-sm) var(--spacing-md);
  }

  .input-container {
    padding: var(--spacing-xs);
  }

  .message-input {
    padding: var(--spacing-sm) var(--spacing-md);
    font-size: 0.9rem;
  }

  .send-btn {
    width: 40px;
    height: 40px;
  }
}

@media (max-width: 480px) {
  .chat-header {
    padding: var(--spacing-sm) var(--spacing-md);
  }

  .chat-messages {
    padding: var(--spacing-md);
  }

  .chat-input-area {
    padding: var(--spacing-sm) var(--spacing-md);
  }

  .message-content {
    max-width: 95%;
  }

  .welcome-message {
    padding: var(--spacing-lg);
  }

  .welcome-icon {
    font-size: 3rem;
  }

  .welcome-message h3 {
    font-size: 1.25rem;
  }

  .status-indicator {
    padding: var(--spacing-xs) var(--spacing-sm);
    font-size: 0.7rem;
  }

  .back-btn {
    width: 36px;
    height: 36px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .message-bubble {
    border-width: 2px;
  }

  .user-message .message-bubble {
    background: var(--text-primary);
    color: var(--bg-primary);
  }

  .ai-message .message-bubble {
    background: var(--bg-primary);
    color: var(--text-primary);
    border-color: var(--text-primary);
  }
}

/* 减少动画模式 */
@media (prefers-reduced-motion: reduce) {
  .message-wrapper,
  .welcome-message,
  .ai-avatar-core,
  .ai-avatar-ring,
  .typing-indicator span {
    animation: none !important;
  }

  .send-btn:hover:not(:disabled) {
    transform: none;
  }

  .back-btn:hover {
    transform: none;
  }
}

/* 深色模式优化 */
@media (prefers-color-scheme: dark) {
  .message-input::placeholder {
    color: var(--text-muted);
  }
}
</style>

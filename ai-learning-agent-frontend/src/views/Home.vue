<template>
  <div class="home">
    <!-- 背景动画层 -->
    <div class="bg-animation">
      <div class="matrix-rain"></div>
      <div class="floating-particles"></div>
      <div class="grid-overlay"></div>
    </div>

    <!-- 主要内容 -->
    <div class="container">
      <!-- 英雄区域 -->
      <section class="hero-section">
        <div class="hero-content">
          <div class="hero-badge">
            <span class="badge-text">NEXT-GEN AI PLATFORM</span>
            <div class="badge-glow"></div>
          </div>

          <h1 class="hero-title">
            <span class="title-line">
              <span class="gradient-text">AI</span>
              <span class="glitch-text" data-text="LEARNING">LEARNING</span>
            </span>
            <span class="title-line">
              <span class="cyber-text">AGENT</span>
              <span class="version-tag">v3.0</span>
            </span>
          </h1>

          <p class="hero-subtitle">
            <span class="typing-text">{{ typingText }}</span>
            <span class="cursor">|</span>
          </p>

          <div class="hero-stats">
            <div class="stat-item">
              <div class="stat-number">{{ stats.users }}</div>
              <div class="stat-label">ACTIVE USERS</div>
            </div>
            <div class="stat-divider"></div>
            <div class="stat-item">
              <div class="stat-number">{{ stats.conversations }}</div>
              <div class="stat-label">CONVERSATIONS</div>
            </div>
            <div class="stat-divider"></div>
            <div class="stat-item">
              <div class="stat-number">{{ stats.uptime }}</div>
              <div class="stat-label">UPTIME</div>
            </div>
          </div>

          <div class="hero-actions">
            <button class="btn-primary cyber-btn" @click="goToLearningChat">
              <span class="btn-text">开始对话</span>
              <div class="btn-particles"></div>
            </button>
            <button class="btn-secondary cyber-btn" @click="scrollToApps">
              <span class="btn-text">更多应用</span>
            </button>
          </div>
        </div>

        <div class="hero-visual">
          <div class="ai-avatar">
            <div class="avatar-core">
              <div class="core-ring"></div>
              <div class="core-center">
                <svg width="80" height="80" viewBox="0 0 24 24" fill="none">
                  <path d="M12 2L13.09 8.26L22 9L13.09 9.74L12 16L10.91 9.74L2 9L10.91 8.26L12 2Z" fill="url(#heroGradient)"/>
                  <defs>
                    <linearGradient id="heroGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                      <stop offset="0%" style="stop-color:#00f5ff"/>
                      <stop offset="50%" style="stop-color:#ff00ff"/>
                      <stop offset="100%" style="stop-color:#ffff00"/>
                    </linearGradient>
                  </defs>
                </svg>
              </div>
            </div>
            <div class="avatar-rings">
              <div class="ring ring-1"></div>
              <div class="ring ring-2"></div>
              <div class="ring ring-3"></div>
            </div>
          </div>

          <div class="data-streams">
            <div class="stream stream-1"></div>
            <div class="stream stream-2"></div>
            <div class="stream stream-3"></div>
          </div>
        </div>
      </section>

      <!-- 应用网格 -->
      <section class="apps-section" ref="appsSection">
        <div class="section-header">
          <h2 class="section-title">
            <span class="title-bracket">[</span>
            <span class="gradient-text">APPLICATIONS</span>
            <span class="title-bracket">]</span>
          </h2>
          <div class="section-line"></div>
        </div>

        <div class="apps-grid">
          <div class="app-card cyber-card" @click="goToLearningChat">
            <div class="card-header">
              <div class="card-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none">
                  <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM13 17H11V15H13V17ZM13 13H11V7H13V13Z" fill="currentColor"/>
                </svg>
              </div>
              <div class="card-status">
                <span class="status-dot active"></span>
                <span class="status-text">ONLINE</span>
              </div>
            </div>

            <div class="card-content">
              <h3 class="card-title">AI LEARNING AGENT</h3>
              <p class="card-description">
                Advanced neural network for intelligent conversation and learning assistance
              </p>

              <div class="card-features">
                <div class="feature-item">
                  <span class="feature-icon">⚡</span>
                  <span>Real-time Processing</span>
                </div>
                <div class="feature-item">
                  <span class="feature-icon">🧠</span>
                  <span>Deep Learning</span>
                </div>
                <div class="feature-item">
                  <span class="feature-icon">🔮</span>
                  <span>Predictive Analysis</span>
                </div>
              </div>

              <div class="card-metrics">
                <div class="metric">
                  <span class="metric-value">99.9%</span>
                  <span class="metric-label">Accuracy</span>
                </div>
                <div class="metric">
                  <span class="metric-value">&lt;100ms</span>
                  <span class="metric-label">Response</span>
                </div>
              </div>
            </div>

            <div class="card-footer">
              <button class="card-btn">
                <span>初始化</span>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                  <path d="M5 12H19M12 5L19 12L12 19" stroke="currentColor" stroke-width="2"/>
                </svg>
              </button>
            </div>
          </div>

          <!-- 即将推出的应用 -->
          <div class="app-card cyber-card coming-soon">
            <div class="card-header">
              <div class="card-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none">
                  <path d="M12 2L13.09 8.26L22 9L13.09 9.74L12 16L10.91 9.74L2 9L10.91 8.26L12 2Z" fill="currentColor"/>
                </svg>
              </div>
              <div class="card-status">
                <span class="status-dot pending"></span>
                <span class="status-text">DEVELOPING</span>
              </div>
            </div>

            <div class="card-content">
              <h3 class="card-title">NEURAL NETWORK HUB</h3>
              <p class="card-description">
                Next-generation AI applications powered by quantum computing
              </p>

              <div class="coming-soon-info">
                <div class="progress-bar">
                  <div class="progress-fill" :style="{ width: developmentProgress + '%' }"></div>
                </div>
                <div class="progress-text">{{ developmentProgress }}% Complete</div>
              </div>
            </div>

            <div class="card-footer">
              <button class="card-btn disabled">
                <span>COMING SOON</span>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                  <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                  <polyline points="12,6 12,12 16,14" stroke="currentColor" stroke-width="2"/>
                </svg>
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- 技术规格 -->
      <section class="tech-specs">
        <div class="specs-header">
          <h3 class="specs-title">SYSTEM SPECIFICATIONS</h3>
        </div>
        <div class="specs-grid">
          <div class="spec-item">
            <div class="spec-label">FRAMEWORK</div>
            <div class="spec-value">Vue 3.3.4</div>
          </div>
          <div class="spec-item">
            <div class="spec-label">BUILD TOOL</div>
            <div class="spec-value">Vite 4.4.9</div>
          </div>
          <div class="spec-item">
            <div class="spec-label">ARCHITECTURE</div>
            <div class="spec-value">SPA + SSE</div>
          </div>
          <div class="spec-item">
            <div class="spec-label">PERFORMANCE</div>
            <div class="spec-value">Optimized</div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Home',
  setup() {
    const router = useRouter()
    const appsSection = ref(null)

    // 打字机效果文本
    const typingText = ref('')
    const fullText = 'ADVANCED AI SYSTEM FOR INTELLIGENT LEARNING AND CONVERSATION'
    let typingIndex = 0
    let typingTimer = null

    // 统计数据
    const stats = ref({
      users: '1,337',
      conversations: '42.7K',
      uptime: '99.9%'
    })

    // 开发进度
    const developmentProgress = ref(73)

    // 打字机效果
    const startTyping = () => {
      typingTimer = setInterval(() => {
        if (typingIndex < fullText.length) {
          typingText.value += fullText[typingIndex]
          typingIndex++
        } else {
          clearInterval(typingTimer)
          // 重新开始
          setTimeout(() => {
            typingText.value = ''
            typingIndex = 0
            startTyping()
          }, 3000)
        }
      }, 100)
    }

    // 导航到聊天页面
    const goToLearningChat = () => {
      router.push('/learning-chat')
    }

    // 滚动到应用区域
    const scrollToApps = () => {
      if (appsSection.value) {
        appsSection.value.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        })
      }
    }

    // 动态更新统计数据
    const updateStats = () => {
      setInterval(() => {
        const users = parseInt(stats.value.users.replace(',', ''))
        const conversations = parseFloat(stats.value.conversations.replace('K', ''))

        stats.value.users = (users + Math.floor(Math.random() * 3)).toLocaleString()
        stats.value.conversations = (conversations + Math.random() * 0.1).toFixed(1) + 'K'
      }, 5000)
    }

    // 创建矩阵雨效果
    const createMatrixRain = () => {
      const matrixContainer = document.querySelector('.matrix-rain')
      if (!matrixContainer) return

      const characters = '01アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン'

      for (let i = 0; i < 50; i++) {
        const column = document.createElement('div')
        column.className = 'matrix-column'
        column.style.left = Math.random() * 100 + '%'
        column.style.animationDelay = Math.random() * 5 + 's'
        column.style.animationDuration = (Math.random() * 3 + 2) + 's'

        for (let j = 0; j < 20; j++) {
          const char = document.createElement('span')
          char.textContent = characters[Math.floor(Math.random() * characters.length)]
          char.style.opacity = Math.random()
          column.appendChild(char)
        }

        matrixContainer.appendChild(column)
      }
    }

    // 创建浮动粒子
    const createFloatingParticles = () => {
      const particlesContainer = document.querySelector('.floating-particles')
      if (!particlesContainer) return

      for (let i = 0; i < 30; i++) {
        const particle = document.createElement('div')
        particle.className = 'particle'
        particle.style.left = Math.random() * 100 + '%'
        particle.style.top = Math.random() * 100 + '%'
        particle.style.animationDelay = Math.random() * 10 + 's'
        particle.style.animationDuration = (Math.random() * 20 + 10) + 's'
        particlesContainer.appendChild(particle)
      }
    }

    onMounted(() => {
      startTyping()
      updateStats()

      // 延迟创建动画效果，确保DOM已渲染
      setTimeout(() => {
        createMatrixRain()
        createFloatingParticles()
      }, 100)

      // 渐进式开发进度动画
      setTimeout(() => {
        const interval = setInterval(() => {
          if (developmentProgress.value < 73) {
            developmentProgress.value += 1
          } else {
            clearInterval(interval)
          }
        }, 50)
      }, 1000)
    })

    onUnmounted(() => {
      if (typingTimer) {
        clearInterval(typingTimer)
      }
    })

    return {
      appsSection,
      typingText,
      stats,
      developmentProgress,
      goToLearningChat,
      scrollToApps
    }
  }
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  background: var(--primary-dark);
  position: relative;
  overflow-x: hidden;
}

/* ===== 背景动画层 ===== */
.bg-animation {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1;
}

.matrix-rain {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  opacity: 0.1;
}

.matrix-column {
  position: absolute;
  top: -100%;
  width: 20px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  color: var(--accent-cyan);
  font-family: 'Courier New', monospace;
  font-size: 12px;
  animation: matrixFall linear infinite;
}

.matrix-column span {
  display: block;
  line-height: 1.2;
}

@keyframes matrixFall {
  to {
    transform: translateY(100vh);
  }
}

.floating-particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.particle {
  position: absolute;
  width: 2px;
  height: 2px;
  background: var(--accent-cyan);
  border-radius: 50%;
  animation: float linear infinite;
  opacity: 0.3;
}

.grid-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image:
    linear-gradient(rgba(0, 245, 255, 0.03) 1px, transparent 1px),
    linear-gradient(90deg, rgba(0, 245, 255, 0.03) 1px, transparent 1px);
  background-size: 50px 50px;
}

/* ===== 主要内容 ===== */
.container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 var(--spacing-xl);
  position: relative;
  z-index: 2;
}

/* ===== 英雄区域 ===== */
.hero-section {
  min-height: 100vh;
  display: flex;
  align-items: center;
  gap: var(--spacing-2xl);
  padding: var(--spacing-2xl) 0;
}

.hero-content {
  flex: 1;
  animation: slideInLeft 1s ease forwards;
}

.hero-badge {
  display: inline-block;
  position: relative;
  margin-bottom: var(--spacing-xl);
  padding: var(--spacing-sm) var(--spacing-lg);
  border: 1px solid var(--border-primary);
  border-radius: var(--radius-2xl);
  background: var(--bg-glass);
  backdrop-filter: blur(10px);
}

.badge-text {
  font-size: 0.8rem;
  font-weight: 600;
  letter-spacing: 2px;
  color: var(--accent-cyan);
  text-transform: uppercase;
}

.badge-glow {
  position: absolute;
  top: -1px;
  left: -1px;
  right: -1px;
  bottom: -1px;
  background: var(--primary-gradient);
  border-radius: var(--radius-2xl);
  z-index: -1;
  opacity: 0.3;
  animation: pulse 2s ease-in-out infinite;
}

.hero-title {
  font-size: clamp(3rem, 8vw, 6rem);
  font-weight: 900;
  line-height: 0.9;
  margin-bottom: var(--spacing-xl);
  text-transform: uppercase;
  letter-spacing: -2px;
}

.title-line {
  display: block;
  position: relative;
}

.glitch-text {
  position: relative;
  color: var(--text-primary);
  animation: glitch 2s infinite;
}

.glitch-text::before,
.glitch-text::after {
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.glitch-text::before {
  animation: glitch-1 2s infinite;
  color: var(--accent-cyan);
  z-index: -1;
}

.glitch-text::after {
  animation: glitch-2 2s infinite;
  color: var(--accent-magenta);
  z-index: -2;
}

@keyframes glitch {
  0%, 100% { transform: translate(0); }
  20% { transform: translate(-2px, 2px); }
  40% { transform: translate(-2px, -2px); }
  60% { transform: translate(2px, 2px); }
  80% { transform: translate(2px, -2px); }
}

@keyframes glitch-1 {
  0%, 100% { transform: translate(0); }
  20% { transform: translate(2px, -2px); }
  40% { transform: translate(-2px, 2px); }
  60% { transform: translate(-2px, -2px); }
  80% { transform: translate(2px, 2px); }
}

@keyframes glitch-2 {
  0%, 100% { transform: translate(0); }
  20% { transform: translate(-2px, -2px); }
  40% { transform: translate(2px, -2px); }
  60% { transform: translate(2px, 2px); }
  80% { transform: translate(-2px, 2px); }
}

.cyber-text {
  color: var(--text-primary);
  text-shadow: 0 0 10px var(--accent-cyan);
}

.version-tag {
  font-size: 1rem;
  color: var(--accent-yellow);
  margin-left: var(--spacing-md);
  opacity: 0.8;
}

.hero-subtitle {
  font-size: 1.2rem;
  color: var(--text-secondary);
  margin-bottom: var(--spacing-2xl);
  font-family: 'Courier New', monospace;
  min-height: 2em;
}

.typing-text {
  color: var(--accent-cyan);
}

.cursor {
  animation: blink 1s infinite;
  color: var(--accent-yellow);
}

@keyframes blink {
  0%, 50% { opacity: 1; }
  51%, 100% { opacity: 0; }
}

.hero-stats {
  display: flex;
  align-items: center;
  gap: var(--spacing-xl);
  margin-bottom: var(--spacing-2xl);
  padding: var(--spacing-lg);
  background: var(--bg-glass);
  border: 1px solid var(--border-secondary);
  border-radius: var(--radius-lg);
  backdrop-filter: blur(10px);
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--accent-cyan);
  font-family: 'Courier New', monospace;
}

.stat-label {
  font-size: 0.8rem;
  color: var(--text-muted);
  letter-spacing: 1px;
  text-transform: uppercase;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background: var(--border-secondary);
}

.hero-actions {
  display: flex;
  gap: var(--spacing-lg);
  flex-wrap: wrap;
}

.cyber-btn {
  position: relative;
  padding: var(--spacing-lg) var(--spacing-2xl);
  background: transparent;
  border: 2px solid var(--accent-cyan);
  color: var(--accent-cyan);
  font-family: inherit;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all var(--transition-normal);
  overflow: hidden;
  clip-path: polygon(0 0, calc(100% - 20px) 0, 100% 100%, 20px 100%);
}

.cyber-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(0, 245, 255, 0.2), transparent);
  transition: left var(--transition-slow);
}

.cyber-btn:hover::before {
  left: 100%;
}

.cyber-btn:hover {
  background: var(--accent-cyan);
  color: var(--bg-primary);
  box-shadow: 0 0 20px var(--accent-cyan);
  transform: translateY(-2px);
}

.btn-primary.cyber-btn {
  background: var(--accent-cyan);
  color: var(--bg-primary);
}

.btn-primary.cyber-btn:hover {
  background: var(--accent-magenta);
  border-color: var(--accent-magenta);
  box-shadow: 0 0 20px var(--accent-magenta);
}

.btn-particles {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

/* ===== 英雄视觉效果 ===== */
.hero-visual {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  animation: slideInRight 1s ease forwards;
}

.ai-avatar {
  position: relative;
  width: 300px;
  height: 300px;
}

.avatar-core {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 120px;
  height: 120px;
}

.core-ring {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border: 2px solid var(--accent-cyan);
  border-radius: 50%;
  animation: rotate 10s linear infinite;
}

.core-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: float 3s ease-in-out infinite;
}

.avatar-rings {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.ring {
  position: absolute;
  border: 1px solid;
  border-radius: 50%;
  animation: rotate linear infinite;
}

.ring-1 {
  width: 200px;
  height: 200px;
  top: -100px;
  left: -100px;
  border-color: var(--accent-cyan);
  animation-duration: 20s;
  opacity: 0.3;
}

.ring-2 {
  width: 250px;
  height: 250px;
  top: -125px;
  left: -125px;
  border-color: var(--accent-magenta);
  animation-duration: 30s;
  animation-direction: reverse;
  opacity: 0.2;
}

.ring-3 {
  width: 300px;
  height: 300px;
  top: -150px;
  left: -150px;
  border-color: var(--accent-yellow);
  animation-duration: 40s;
  opacity: 0.1;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.data-streams {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.stream {
  position: absolute;
  width: 2px;
  height: 100px;
  background: linear-gradient(to bottom, var(--accent-cyan), transparent);
  animation: streamFlow 3s ease-in-out infinite;
}

.stream-1 {
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.stream-2 {
  top: 60%;
  right: 15%;
  animation-delay: 1s;
}

.stream-3 {
  bottom: 20%;
  left: 70%;
  animation-delay: 2s;
}

@keyframes streamFlow {
  0%, 100% { opacity: 0; transform: translateY(0); }
  50% { opacity: 1; transform: translateY(-20px); }
}

/* ===== 应用区域 ===== */
.apps-section {
  padding: var(--spacing-2xl) 0;
  margin-top: var(--spacing-2xl);
}

.section-header {
  text-align: center;
  margin-bottom: var(--spacing-2xl);
  position: relative;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: var(--spacing-lg);
  text-transform: uppercase;
  letter-spacing: 2px;
}

.title-bracket {
  color: var(--accent-cyan);
  font-family: 'Courier New', monospace;
}

.section-line {
  width: 100px;
  height: 2px;
  background: var(--primary-gradient);
  margin: 0 auto;
  border-radius: 1px;
}

.apps-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: var(--spacing-2xl);
  max-width: 1000px;
  margin: 0 auto;
}

.cyber-card {
  background: var(--bg-card);
  border: 1px solid var(--border-secondary);
  border-radius: var(--radius-xl);
  padding: 0;
  overflow: hidden;
  transition: all var(--transition-normal);
  position: relative;
  cursor: pointer;
  backdrop-filter: blur(20px);
}

.cyber-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: var(--primary-gradient);
  opacity: 0;
  transition: opacity var(--transition-normal);
}

.cyber-card:hover {
  transform: translateY(-8px);
  border-color: var(--border-primary);
  box-shadow: var(--shadow-card);
}

.cyber-card:hover::before {
  opacity: 1;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: var(--spacing-xl);
  border-bottom: 1px solid var(--border-secondary);
}

.card-icon {
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-glass);
  border-radius: var(--radius-lg);
  color: var(--accent-cyan);
  border: 1px solid var(--border-primary);
}

.card-status {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  animation: pulse 2s ease-in-out infinite;
}

.status-dot.active {
  background: var(--accent-green);
  box-shadow: 0 0 10px var(--accent-green);
}

.status-dot.pending {
  background: var(--accent-yellow);
  box-shadow: 0 0 10px var(--accent-yellow);
}

.status-text {
  font-size: 0.8rem;
  font-weight: 600;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: var(--text-secondary);
}

.card-content {
  padding: var(--spacing-xl);
}

.card-title {
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: var(--spacing-md);
  color: var(--text-primary);
  text-transform: uppercase;
  letter-spacing: 1px;
}

.card-description {
  color: var(--text-secondary);
  line-height: 1.6;
  margin-bottom: var(--spacing-lg);
  font-size: 0.9rem;
}

.card-features {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-sm);
  margin-bottom: var(--spacing-lg);
}

.feature-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  font-size: 0.85rem;
  color: var(--text-secondary);
}

.feature-icon {
  font-size: 1rem;
}

.card-metrics {
  display: flex;
  gap: var(--spacing-lg);
}

.metric {
  text-align: center;
}

.metric-value {
  display: block;
  font-size: 1.1rem;
  font-weight: 700;
  color: var(--accent-cyan);
  font-family: 'Courier New', monospace;
}

.metric-label {
  font-size: 0.75rem;
  color: var(--text-muted);
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.card-footer {
  padding: var(--spacing-xl);
  border-top: 1px solid var(--border-secondary);
}

.card-btn {
  width: 100%;
  padding: var(--spacing-md) var(--spacing-lg);
  background: transparent;
  border: 1px solid var(--accent-cyan);
  color: var(--accent-cyan);
  border-radius: var(--radius-md);
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all var(--transition-normal);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: var(--spacing-sm);
}

.card-btn:hover:not(.disabled) {
  background: var(--accent-cyan);
  color: var(--bg-primary);
  box-shadow: 0 0 15px var(--accent-cyan);
}

.card-btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
  border-color: var(--text-muted);
  color: var(--text-muted);
}

.coming-soon-info {
  margin-bottom: var(--spacing-lg);
}

.progress-bar {
  width: 100%;
  height: 4px;
  background: var(--bg-secondary);
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: var(--spacing-sm);
}

.progress-fill {
  height: 100%;
  background: var(--primary-gradient);
  border-radius: 2px;
  transition: width var(--transition-slow);
}

.progress-text {
  font-size: 0.8rem;
  color: var(--text-secondary);
  text-align: center;
  font-family: 'Courier New', monospace;
}

/* ===== 技术规格 ===== */
.tech-specs {
  margin-top: var(--spacing-2xl);
  padding: var(--spacing-2xl);
  background: var(--bg-glass);
  border: 1px solid var(--border-secondary);
  border-radius: var(--radius-xl);
  backdrop-filter: blur(20px);
}

.specs-header {
  text-align: center;
  margin-bottom: var(--spacing-xl);
}

.specs-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--text-primary);
  text-transform: uppercase;
  letter-spacing: 2px;
}

.specs-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: var(--spacing-lg);
}

.spec-item {
  text-align: center;
  padding: var(--spacing-lg);
  background: var(--bg-secondary);
  border-radius: var(--radius-lg);
  border: 1px solid var(--border-secondary);
}

.spec-label {
  font-size: 0.8rem;
  color: var(--text-muted);
  text-transform: uppercase;
  letter-spacing: 1px;
  margin-bottom: var(--spacing-sm);
}

.spec-value {
  font-size: 1rem;
  font-weight: 600;
  color: var(--accent-cyan);
  font-family: 'Courier New', monospace;
}

/* ===== 响应式设计 ===== */
@media (max-width: 1200px) {
  .hero-section {
    flex-direction: column;
    text-align: center;
  }

  .hero-visual {
    margin-top: var(--spacing-2xl);
  }
}

@media (max-width: 768px) {
  .container {
    padding: 0 var(--spacing-lg);
  }

  .hero-title {
    font-size: clamp(2rem, 10vw, 4rem);
  }

  .hero-stats {
    flex-direction: column;
    gap: var(--spacing-lg);
  }

  .stat-divider {
    width: 100%;
    height: 1px;
  }

  .hero-actions {
    flex-direction: column;
  }

  .cyber-btn {
    width: 100%;
  }

  .apps-grid {
    grid-template-columns: 1fr;
    gap: var(--spacing-xl);
  }

  .specs-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .ai-avatar {
    width: 200px;
    height: 200px;
  }

  .avatar-core {
    width: 80px;
    height: 80px;
  }

  .ring-1 {
    width: 150px;
    height: 150px;
    top: -75px;
    left: -75px;
  }

  .ring-2 {
    width: 180px;
    height: 180px;
    top: -90px;
    left: -90px;
  }

  .ring-3 {
    width: 200px;
    height: 200px;
    top: -100px;
    left: -100px;
  }
}

@media (max-width: 480px) {
  .hero-section {
    padding: var(--spacing-xl) 0;
  }

  .section-title {
    font-size: 2rem;
  }

  .specs-grid {
    grid-template-columns: 1fr;
  }

  .card-header {
    padding: var(--spacing-lg);
  }

  .card-content {
    padding: var(--spacing-lg);
  }

  .card-footer {
    padding: var(--spacing-lg);
  }
}

/* ===== 性能优化 ===== */
@media (prefers-reduced-motion: reduce) {
  .matrix-column,
  .particle,
  .ring,
  .core-center,
  .stream,
  .glitch-text,
  .glitch-text::before,
  .glitch-text::after {
    animation: none !important;
  }
}
</style>

<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <div class="footer-logo">
          <div class="logo-icon">
            <svg width="32" height="32" viewBox="0 0 24 24" fill="none">
              <path d="M12 2L13.09 8.26L22 9L13.09 9.74L12 16L10.91 9.74L2 9L10.91 8.26L12 2Z" fill="url(#gradient)"/>
              <defs>
                <linearGradient id="gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" style="stop-color:#00f5ff"/>
                  <stop offset="50%" style="stop-color:#ff00ff"/>
                  <stop offset="100%" style="stop-color:#ffff00"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <span class="logo-text gradient-text">AI学习助手</span>
        </div>
        <p class="footer-description">
          基于Vue3的现代化AI学习平台<br>
          让学习变得更智能、更高效
        </p>
      </div>
      
      <div class="footer-section">
        <h4 class="footer-title">产品功能</h4>
        <ul class="footer-links">
          <li><a href="#" class="footer-link">智能问答</a></li>
          <li><a href="#" class="footer-link">实时对话</a></li>
          <li><a href="#" class="footer-link">学习指导</a></li>
          <li><a href="#" class="footer-link">知识管理</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h4 class="footer-title">技术栈</h4>
        <ul class="footer-links">
          <li><a href="https://vuejs.org" target="_blank" class="footer-link">Vue 3</a></li>
          <li><a href="https://router.vuejs.org" target="_blank" class="footer-link">Vue Router</a></li>
          <li><a href="https://vitejs.dev" target="_blank" class="footer-link">Vite</a></li>
          <li><a href="https://axios-http.com" target="_blank" class="footer-link">Axios</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h4 class="footer-title">联系我们</h4>
        <div class="contact-info">
          <div class="contact-item">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
              <path d="M4 4H20C21.1 4 22 4.9 22 6V18C22 19.1 21.1 20 20 20H4C2.9 20 2 19.1 2 18V6C2 4.9 2.9 4 4 4Z" stroke="currentColor" stroke-width="2"/>
              <polyline points="22,6 12,13 2,6" stroke="currentColor" stroke-width="2"/>
            </svg>
            <span>support@ai-learning.com</span>
          </div>
          <div class="contact-item">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
              <path d="M9 19C-5 19 -5 5 9 5C23 5 23 19 9 19Z" stroke="currentColor" stroke-width="2"/>
              <path d="M9 5C9 5 15 9 15 13C15 17 9 19 9 19" stroke="currentColor" stroke-width="2"/>
            </svg>
            <span>www.ai-learning.com</span>
          </div>
        </div>
      </div>
    </div>
    
    <div class="footer-bottom">
      <div class="footer-bottom-content">
        <div class="copyright">
          <p>&copy; {{ currentYear }} AI学习助手. 保留所有权利.</p>
          <p class="tech-info">Powered by Vue 3 + Vite | 极客风格设计</p>
        </div>
        <div class="footer-social">
          <a href="#" class="social-link" aria-label="GitHub">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
              <path d="M9 19C4 20.5 4 16.5 2 16M22 16V22L18 20L14 22V16" stroke="currentColor" stroke-width="2"/>
              <path d="M16 2V8L20 6L24 8V2" stroke="currentColor" stroke-width="2"/>
            </svg>
          </a>
          <a href="#" class="social-link" aria-label="Twitter">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
              <path d="M23 3A10.9 10.9 0 0 1 20.1 4.7A4.48 4.48 0 0 0 22.46 2A9 9 0 0 1 19.36 3.5A4.48 4.48 0 0 0 12 7.44V8.5A10.66 10.66 0 0 1 3 4S-1 13 8 17A11.64 11.64 0 0 1 0 19C9 24 20 19 20 7.5A4.5 4.5 0 0 0 23 3Z" stroke="currentColor" stroke-width="2"/>
            </svg>
          </a>
          <a href="#" class="social-link" aria-label="Discord">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
              <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
              <path d="M8 14S9.5 16 12 16S16 14 16 14" stroke="currentColor" stroke-width="2"/>
              <line x1="9" y1="9" x2="9.01" y2="9" stroke="currentColor" stroke-width="2"/>
              <line x1="15" y1="9" x2="15.01" y2="9" stroke="currentColor" stroke-width="2"/>
            </svg>
          </a>
        </div>
      </div>
    </div>
    
    <!-- 装饰性背景元素 -->
    <div class="footer-bg-decoration">
      <div class="decoration-line"></div>
      <div class="decoration-dots">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>
  </footer>
</template>

<script>
import { ref, computed } from 'vue'

export default {
  name: 'Footer',
  setup() {
    const currentYear = computed(() => new Date().getFullYear())
    
    return {
      currentYear
    }
  }
}
</script>

<style scoped>
.footer {
  background: var(--bg-secondary);
  border-top: 1px solid var(--border-secondary);
  position: relative;
  overflow: hidden;
  margin-top: auto;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-2xl) var(--spacing-xl);
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: var(--spacing-2xl);
  position: relative;
  z-index: 2;
}

.footer-section {
  animation: slideInUp 0.6s ease forwards;
}

.footer-section:nth-child(1) { animation-delay: 0.1s; }
.footer-section:nth-child(2) { animation-delay: 0.2s; }
.footer-section:nth-child(3) { animation-delay: 0.3s; }
.footer-section:nth-child(4) { animation-delay: 0.4s; }

.footer-logo {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-lg);
}

.logo-icon {
  width: 32px;
  height: 32px;
  animation: float 3s ease-in-out infinite;
}

.logo-text {
  font-size: 1.25rem;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.footer-description {
  color: var(--text-secondary);
  line-height: 1.6;
  margin-bottom: var(--spacing-lg);
}

.footer-title {
  color: var(--text-primary);
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: var(--spacing-lg);
  position: relative;
}

.footer-title::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 30px;
  height: 2px;
  background: var(--primary-gradient);
  border-radius: 1px;
}

.footer-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li {
  margin-bottom: var(--spacing-sm);
}

.footer-link {
  color: var(--text-secondary);
  text-decoration: none;
  transition: all var(--transition-normal);
  position: relative;
  display: inline-block;
}

.footer-link::before {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 1px;
  background: var(--accent-cyan);
  transition: width var(--transition-normal);
}

.footer-link:hover {
  color: var(--accent-cyan);
  transform: translateX(4px);
}

.footer-link:hover::before {
  width: 100%;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
}

.contact-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  color: var(--text-secondary);
  font-size: 0.9rem;
}

.contact-item svg {
  color: var(--accent-cyan);
  flex-shrink: 0;
}

.footer-bottom {
  background: var(--bg-primary);
  border-top: 1px solid var(--border-secondary);
  position: relative;
  z-index: 2;
}

.footer-bottom-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-xl);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: var(--spacing-lg);
}

.copyright {
  color: var(--text-muted);
  font-size: 0.875rem;
}

.tech-info {
  margin-top: var(--spacing-xs);
  font-size: 0.8rem;
  opacity: 0.7;
}

.footer-social {
  display: flex;
  gap: var(--spacing-md);
}

.social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: var(--bg-glass);
  color: var(--text-secondary);
  text-decoration: none;
  transition: all var(--transition-normal);
  border: 1px solid var(--border-secondary);
}

.social-link:hover {
  background: var(--accent-cyan);
  color: var(--bg-primary);
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow);
}

/* 装饰性背景元素 */
.footer-bg-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 1;
}

.decoration-line {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: var(--primary-gradient);
  opacity: 0.3;
}

.decoration-dots {
  position: absolute;
  top: 20px;
  right: 20px;
  display: flex;
  gap: 8px;
}

.decoration-dots span {
  width: 4px;
  height: 4px;
  border-radius: 50%;
  background: var(--accent-cyan);
  opacity: 0.3;
  animation: pulse 2s ease-in-out infinite;
}

.decoration-dots span:nth-child(2) { animation-delay: 0.5s; }
.decoration-dots span:nth-child(3) { animation-delay: 1s; }

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    grid-template-columns: 1fr;
    gap: var(--spacing-xl);
    padding: var(--spacing-xl) var(--spacing-lg);
  }
  
  .footer-bottom-content {
    flex-direction: column;
    text-align: center;
    padding: var(--spacing-lg);
  }
  
  .footer-social {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .footer-content {
    padding: var(--spacing-lg) var(--spacing-md);
  }
  
  .footer-logo {
    justify-content: center;
  }
  
  .footer-section {
    text-align: center;
  }
}
</style>

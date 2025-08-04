import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import Home from './views/Home.vue'
import LearningChat from './views/LearningChat.vue'
import './style.css'

const routes = [
  {
    path: '/',
    component: Home,
    name: 'Home',
    meta: {
      title: 'AI应用中心 - 智能学习助手平台',
      description: '专业的AI学习助手平台，提供智能问答、实时对话、学习指导等功能。体验最先进的人工智能技术，让学习更高效。',
      keywords: 'AI学习助手,人工智能,智能问答,在线学习,AI聊天,学习平台'
    }
  },
  {
    path: '/learning-chat',
    component: LearningChat,
    name: 'LearningChat',
    meta: {
      title: 'AI学习助手 - 智能对话学习',
      description: '与AI学习助手进行实时对话，获得个性化学习指导和问题解答。支持流式响应，体验流畅的AI交互。',
      keywords: 'AI对话,智能学习,实时聊天,学习助手,AI问答,在线教育'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

// 路由守卫 - 更新页面标题和meta信息
router.beforeEach((to, from, next) => {
  // 更新页面标题
  if (to.meta.title) {
    document.title = to.meta.title
  }

  // 更新meta描述
  if (to.meta.description) {
    let metaDescription = document.querySelector('meta[name="description"]')
    if (metaDescription) {
      metaDescription.content = to.meta.description
    }
  }

  // 更新meta关键词
  if (to.meta.keywords) {
    let metaKeywords = document.querySelector('meta[name="keywords"]')
    if (metaKeywords) {
      metaKeywords.content = to.meta.keywords
    }
  }

  next()
})

const app = createApp(App)
app.use(router)
app.mount('#app')

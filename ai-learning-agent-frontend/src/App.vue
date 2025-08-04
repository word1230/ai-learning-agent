<template>
  <div id="app" :class="appLayoutClass">
    <router-view />
    <Footer v-if="showFooter" />
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import Footer from './components/Footer.vue'

export default {
  name: 'App',
  components: {
    Footer
  },
  setup() {
    const route = useRoute()
    const showFooter = ref(true)
    const appLayoutClass = ref('home-layout')

    // 控制Footer显示和布局类 - 只在主页显示Footer
    const updateLayoutAndFooter = (routeName) => {
      const isHome = routeName === 'Home' || routeName === undefined
      showFooter.value = isHome
      appLayoutClass.value = isHome ? 'home-layout' : 'chat-layout'
    }

    // SEO优化 - 动态更新页面标题和meta信息
    const updateSEO = (routeName) => {
      const seoConfig = {
        'Home': {
          title: 'AI应用中心 - 智能学习助手平台',
          description: '专业的AI学习助手平台，提供智能问答、实时对话、学习指导等功能。体验最先进的人工智能技术，让学习更高效。',
          keywords: 'AI学习助手,人工智能,智能问答,在线学习,AI聊天,学习平台'
        },
        'LearningChat': {
          title: 'AI学习助手 - 智能对话学习',
          description: '与AI学习助手进行实时对话，获得个性化学习指导和问题解答。支持流式响应，体验流畅的AI交互。',
          keywords: 'AI对话,智能学习,实时聊天,学习助手,AI问答,在线教育'
        },
        'default': {
          title: 'AI学习助手 - 智能学习平台',
          description: '基于Vue3的现代化AI学习助手平台，提供智能对话、学习指导等功能。',
          keywords: 'AI,学习助手,Vue3,人工智能,在线学习'
        }
      }

      const config = seoConfig[routeName] || seoConfig.default

      // 更新页面标题
      document.title = config.title

      // 更新meta描述
      let metaDescription = document.querySelector('meta[name="description"]')
      if (!metaDescription) {
        metaDescription = document.createElement('meta')
        metaDescription.name = 'description'
        document.head.appendChild(metaDescription)
      }
      metaDescription.content = config.description

      // 更新meta关键词
      let metaKeywords = document.querySelector('meta[name="keywords"]')
      if (!metaKeywords) {
        metaKeywords = document.createElement('meta')
        metaKeywords.name = 'keywords'
        document.head.appendChild(metaKeywords)
      }
      metaKeywords.content = config.keywords

      // 更新Open Graph标签
      updateOpenGraph(config)
    }

    const updateOpenGraph = (config) => {
      const ogTags = [
        { property: 'og:title', content: config.title },
        { property: 'og:description', content: config.description },
        { property: 'og:type', content: 'website' },
        { property: 'og:url', content: window.location.href },
        { property: 'og:site_name', content: 'AI学习助手' },
        { name: 'twitter:card', content: 'summary_large_image' },
        { name: 'twitter:title', content: config.title },
        { name: 'twitter:description', content: config.description }
      ]

      ogTags.forEach(tag => {
        const selector = tag.property ? `meta[property="${tag.property}"]` : `meta[name="${tag.name}"]`
        let metaTag = document.querySelector(selector)
        if (!metaTag) {
          metaTag = document.createElement('meta')
          if (tag.property) metaTag.setAttribute('property', tag.property)
          if (tag.name) metaTag.setAttribute('name', tag.name)
          document.head.appendChild(metaTag)
        }
        metaTag.content = tag.content
      })
    }

    // 监听路由变化，更新SEO信息、Footer显示和布局
    watch(route, (newRoute) => {
      updateSEO(newRoute.name)
      updateLayoutAndFooter(newRoute.name)
    }, { immediate: true })

    onMounted(() => {
      // 添加结构化数据
      const structuredData = {
        "@context": "https://schema.org",
        "@type": "WebApplication",
        "name": "AI学习助手",
        "description": "专业的AI学习助手平台，提供智能问答、实时对话、学习指导等功能",
        "url": window.location.origin,
        "applicationCategory": "EducationalApplication",
        "operatingSystem": "Web Browser",
        "offers": {
          "@type": "Offer",
          "price": "0",
          "priceCurrency": "CNY"
        },
        "creator": {
          "@type": "Organization",
          "name": "AI学习助手团队"
        }
      }

      let scriptTag = document.querySelector('script[type="application/ld+json"]')
      if (!scriptTag) {
        scriptTag = document.createElement('script')
        scriptTag.type = 'application/ld+json'
        document.head.appendChild(scriptTag)
      }
      scriptTag.textContent = JSON.stringify(structuredData)
    })

    return {
      showFooter,
      appLayoutClass
    }
  }
}
</script>

<style>
#app {
  font-family: 'SF Pro Display', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 100vh;
  margin: 0;
  padding: 0;
  background: var(--bg-primary);
  color: var(--text-primary);
  display: flex;
  flex-direction: column;
}

/* 聊天页面的特殊布局 */
#app.chat-layout {
  height: 100vh;
  overflow: hidden;
}

#app.chat-layout > div:first-child {
  flex: 1;
  min-height: 0;
}

/* 主页布局 */
#app.home-layout > div:first-child {
  flex: 1;
}

/* 全局焦点样式 */
*:focus {
  outline: 2px solid var(--accent-cyan);
  outline-offset: 2px;
}

/* 无障碍访问 - 减少动画 */
@media (prefers-reduced-motion: reduce) {
  * {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  :root {
    --text-primary: #ffffff;
    --text-secondary: #ffffff;
    --bg-primary: #000000;
    --border-primary: #ffffff;
  }
}
</style>

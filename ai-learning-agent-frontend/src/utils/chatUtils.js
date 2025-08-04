/**
 * 生成唯一的聊天ID
 * @returns {string} 聊天ID
 */
export function generateChatId() {
  const timestamp = Date.now()
  const random = Math.random().toString(36).substring(2, 11)
  return `chat_${timestamp}_${random}`
}

/**
 * 格式化消息内容，支持简单的markdown渲染
 * @param {string} content 原始内容
 * @returns {string} 格式化后的HTML内容
 */
export function formatMessageContent(content) {
  if (!content) return ''
  
  // 转义HTML特殊字符
  let formatted = content
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;')
  
  // 处理换行
  formatted = formatted.replace(/\n/g, '<br>')
  
  // 处理粗体 **text**
  formatted = formatted.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
  
  // 处理斜体 *text*
  formatted = formatted.replace(/\*(.*?)\*/g, '<em>$1</em>')
  
  // 处理代码块 `code`
  formatted = formatted.replace(/`(.*?)`/g, '<code style="background: #f1f5f9; padding: 2px 4px; border-radius: 4px; font-family: monospace;">$1</code>')
  
  // 处理链接 [text](url)
  formatted = formatted.replace(/\[([^\]]+)\]\(([^)]+)\)/g, '<a href="$2" target="_blank" style="color: #667eea; text-decoration: underline;">$1</a>')
  
  return formatted
}

/**
 * 格式化时间戳
 * @param {Date} timestamp 时间戳
 * @returns {string} 格式化后的时间字符串
 */
export function formatTimestamp(timestamp) {
  if (!timestamp) return ''
  
  const now = new Date()
  const date = new Date(timestamp)
  const diffInMinutes = Math.floor((now - date) / (1000 * 60))
  
  if (diffInMinutes < 1) {
    return '刚刚'
  } else if (diffInMinutes < 60) {
    return `${diffInMinutes}分钟前`
  } else if (diffInMinutes < 1440) { // 24小时
    const hours = Math.floor(diffInMinutes / 60)
    return `${hours}小时前`
  } else {
    // 超过24小时显示具体时间
    const options = {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    }
    return date.toLocaleDateString('zh-CN', options)
  }
}

/**
 * 防抖函数
 * @param {Function} func 要防抖的函数
 * @param {number} wait 等待时间（毫秒）
 * @returns {Function} 防抖后的函数
 */
export function debounce(func, wait) {
  let timeout
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout)
      func(...args)
    }
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
  }
}

/**
 * 节流函数
 * @param {Function} func 要节流的函数
 * @param {number} limit 时间限制（毫秒）
 * @returns {Function} 节流后的函数
 */
export function throttle(func, limit) {
  let inThrottle
  return function(...args) {
    if (!inThrottle) {
      func.apply(this, args)
      inThrottle = true
      setTimeout(() => inThrottle = false, limit)
    }
  }
}

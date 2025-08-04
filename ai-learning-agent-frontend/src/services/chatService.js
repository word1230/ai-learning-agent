import axios from 'axios'

// 配置axios实例
const apiClient = axios.create({
  baseURL: 'http://localhost:8123',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
apiClient.interceptors.request.use(
  config => {
    console.log('发送请求:', config.url, config.params)
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
apiClient.interceptors.response.use(
  response => {
    return response
  },
  error => {
    console.error('响应错误:', error)
    if (error.code === 'ECONNREFUSED') {
      console.error('无法连接到后端服务，请确保后端服务已启动')
    }
    return Promise.reject(error)
  }
)

/**
 * 同步聊天接口
 * @param {string} query 用户查询
 * @param {string} chatId 聊天ID
 * @returns {Promise<string>} AI响应
 */
export async function chatSync(query, chatId) {
  try {
    const response = await apiClient.get('/ai/learning_app/chat/sync', {
      params: { query, chatId }
    })
    return response.data
  } catch (error) {
    console.error('同步聊天请求失败:', error)
    throw new Error('发送消息失败，请检查网络连接')
  }
}

/**
 * 创建SSE连接进行流式聊天
 * @param {string} query 用户查询
 * @param {string} chatId 聊天ID
 * @param {Object} callbacks 回调函数对象
 * @param {Function} callbacks.onMessage 接收到消息时的回调
 * @param {Function} callbacks.onComplete 完成时的回调
 * @param {Function} callbacks.onError 错误时的回调
 * @returns {EventSource} EventSource实例
 */
export function createSSEConnection(query, chatId, callbacks = {}) {
  const { onMessage, onComplete, onError } = callbacks
  
  // 构建SSE URL
  const params = new URLSearchParams({ query, chatId })
  const sseUrl = `http://localhost:8123/ai/learning_app/chat/sse?${params.toString()}`
  
  console.log('创建SSE连接:', sseUrl)
  
  // 创建EventSource实例
  const eventSource = new EventSource(sseUrl)
  
  // 监听消息事件
  eventSource.onmessage = (event) => {
    try {
      const data = event.data
      console.log('收到SSE消息:', data)
      
      // 检查是否是结束标志
      if (data === '[DONE]' || data === 'data: [DONE]') {
        console.log('SSE连接完成')
        eventSource.close()
        if (onComplete) onComplete()
        return
      }
      
      // 处理普通消息
      if (data && data.trim() && onMessage) {
        onMessage(data)
      }
    } catch (error) {
      console.error('处理SSE消息时出错:', error)
      if (onError) onError(error)
    }
  }
  
  // 监听连接打开事件
  eventSource.onopen = (event) => {
    console.log('SSE连接已打开:', event)
  }
  
  // 监听错误事件
  eventSource.onerror = (event) => {
    console.error('SSE连接错误:', event)
    eventSource.close()
    
    if (event.target.readyState === EventSource.CLOSED) {
      console.log('SSE连接已关闭')
      if (onComplete) onComplete()
    } else {
      const error = new Error('SSE连接出现错误')
      if (onError) onError(error)
    }
  }
  
  return eventSource
}

/**
 * 检查后端服务是否可用
 * @returns {Promise<boolean>} 服务是否可用
 */
export async function checkServiceHealth() {
  try {
    const response = await apiClient.get('/health', { timeout: 5000 })
    return response.status === 200
  } catch (error) {
    console.warn('后端服务健康检查失败:', error.message)
    return false
  }
}

/**
 * 获取聊天历史记录（如果后端支持）
 * @param {string} chatId 聊天ID
 * @returns {Promise<Array>} 聊天历史
 */
export async function getChatHistory(chatId) {
  try {
    const response = await apiClient.get('/ai/learning_app/chat/history', {
      params: { chatId }
    })
    return response.data || []
  } catch (error) {
    console.warn('获取聊天历史失败:', error)
    return []
  }
}

export default {
  chatSync,
  createSSEConnection,
  checkServiceHealth,
  getChatHistory
}

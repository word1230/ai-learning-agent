# AI学习助手前端项目

这是一个基于Vue3的AI学习助手前端应用，提供智能聊天功能，支持实时对话和流式响应。

## 功能特性

- 🏠 **极客风格主页**: 炫酷的矩阵雨背景、动态粒子效果、赛博朋克风格设计
- 💬 **AI学习助手**: 智能聊天界面，支持实时对话，AI专属头像设计
- 🔄 **SSE流式响应**: 通过Server-Sent Events实现实时消息流
- 📱 **全响应式设计**: 完美支持PC、平板、手机三端适配
- 🎨 **统一设计系统**: 极客风格的现代化UI，炫酷动画效果
- ⚡ **实时通信**: 自动生成会话ID，支持多会话管理
- 🔍 **SEO优化**: 完整的meta标签、结构化数据、sitemap支持
- ♿ **无障碍访问**: 支持高对比度模式、减少动画模式
- 📄 **版权信息**: 专业的页脚设计，包含完整的版权和技术信息
- 🚀 **性能优化**: 关键CSS内联、资源预加载、PWA支持

## 技术栈

- **Vue 3** - 渐进式JavaScript框架
- **Vue Router 4** - 官方路由管理器
- **Axios** - HTTP客户端库
- **Vite** - 现代化构建工具
- **CSS3** - 现代化样式设计

## 项目结构

```
src/
├── views/              # 页面组件
│   ├── Home.vue       # 主页 - 应用中心
│   └── LearningChat.vue # AI学习助手聊天页面
├── services/          # 服务层
│   └── chatService.js # 聊天相关API服务
├── utils/             # 工具函数
│   └── chatUtils.js   # 聊天相关工具函数
├── App.vue           # 根组件
├── main.js           # 应用入口
└── style.css         # 全局样式
```

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

应用将在 `http://localhost:3000` 启动

### 3. 构建生产版本

```bash
npm run build
```

## 后端接口要求

项目需要配合SpringBoot后端使用，后端应提供以下接口：

### 基础配置
- **接口地址前缀**: `http://localhost:8123/`

### 接口列表

#### 1. 同步聊天接口
```
GET /ai/learning_app/chat/sync
参数:
- query: string (用户查询内容)
- chatId: string (聊天会话ID)
返回: string (AI响应内容)
```

#### 2. SSE流式聊天接口
```
GET /ai/learning_app/chat/sse
参数:
- query: string (用户查询内容)  
- chatId: string (聊天会话ID)
返回: text/event-stream (流式响应)
```

### SSE响应格式
- 正常消息: 直接发送文本内容
- 结束标志: 发送 `[DONE]` 表示响应完成

## 主要功能说明

### 主页 (Home.vue)
- 展示AI应用中心
- 提供应用卡片，支持点击跳转
- 响应式布局，支持移动端

### AI学习助手 (LearningChat.vue)
- 聊天室风格的对话界面
- 自动生成唯一会话ID
- 支持SSE实时流式响应
- 用户消息显示在右侧，AI消息显示在左侧
- 支持消息格式化（粗体、斜体、代码等）
- 输入框支持Enter发送，Shift+Enter换行
- 显示连接状态和输入状态指示器

### 工具函数
- `generateChatId()`: 生成唯一聊天ID
- `formatMessageContent()`: 格式化消息内容，支持简单markdown
- `formatTimestamp()`: 智能时间格式化
- `debounce()` / `throttle()`: 防抖和节流函数

### 服务层
- `chatSync()`: 同步聊天API调用
- `createSSEConnection()`: 创建SSE连接
- `checkServiceHealth()`: 健康检查
- 完整的错误处理和重连机制

## 开发说明

### 环境要求
- Node.js 16+
- npm 或 yarn

### 开发模式
```bash
npm run dev
```

### 代码规范
- 使用ES6+语法
- 组件采用Composition API
- 样式使用scoped CSS
- 遵循Vue3最佳实践

## 部署说明

### 构建
```bash
npm run build
```

### 部署
将 `dist` 目录部署到Web服务器即可

### 注意事项
- 确保后端服务已启动并可访问
- 检查CORS配置，允许前端域名访问
- 生产环境需要配置正确的后端API地址

## 故障排除

### 常见问题

1. **无法连接后端服务**
   - 检查后端服务是否启动 (http://localhost:8123)
   - 检查网络连接和防火墙设置

2. **SSE连接失败**
   - 确认后端SSE接口正常工作
   - 检查浏览器控制台错误信息

3. **消息发送失败**
   - 检查请求参数是否正确
   - 查看网络请求状态和响应

## 许可证

MIT License

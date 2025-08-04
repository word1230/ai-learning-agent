@echo off
echo ================================
echo AI学习助手前端项目启动脚本
echo ================================
echo.

echo 正在检查Node.js环境...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: 未找到Node.js，请先安装Node.js
    pause
    exit /b 1
)

echo Node.js版本:
node --version

echo.
echo 正在检查项目依赖...
if not exist "node_modules" (
    echo 未找到node_modules目录，正在安装依赖...
    npm install
    if %errorlevel% neq 0 (
        echo 依赖安装失败，请检查网络连接
        pause
        exit /b 1
    )
) else (
    echo 依赖已安装
)

echo.
echo 正在启动开发服务器...
echo 前端地址: http://localhost:3000
echo 后端地址: http://localhost:8123 (需要单独启动)
echo.
echo 按 Ctrl+C 停止服务器
echo ================================
echo.

npm run dev

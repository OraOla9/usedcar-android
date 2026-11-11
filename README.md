# 二手车交易 Android 客户端

> 基于 Android 的二手车信息浏览、发布、交易与管理 App

## 项目简介

二手车交易系统的移动端实现。包含买家浏览/查询车辆、经销商登录与车辆发布、管理员后台等模块，使用 Java + Android SDK 开发，Gradle Kotlin DSL 构建。

## 技术栈

- Java
- Android SDK
- Gradle (Kotlin DSL)
- Jetpack Navigation
- SQLite / 本地数据存储

## 目录结构

```
README.md
.gitignore
build.gradle.kts
gradle.properties
gradlew
gradlew.bat
local.properties.example
settings.gradle.kts
无标题-2025-01-03-1922.excalidraw
app/
    .gitignore
    build.gradle.kts
    proguard-rules.pro
    src/
gradle/
    libs.versions.toml
    wrapper/
        gradle-wrapper.properties
```

## 快速开始

1. 使用 Android Studio（Giraffe 及以上）打开本目录。
2. 等待 Gradle Sync 完成；如提示缺少 SDK 34，请在 SDK Manager 中安装。
3. 在 `local.properties` 中填写本机 `sdk.dir`（可参考 `local.properties.example`）。
4. 连接模拟器或真机后点击 Run。

## 配置说明

`local.properties` 含本机 SDK 绝对路径，未纳入仓库；请按 `local.properties.example` 自行创建。
- 后端接口地址、数据库口令等一切环境相关配置请使用本地配置，不要提交。

## 文档

- `README.md`（本文件）

## 整理说明

- 原始工程中的 `app/src/main.zip`（源码冗余压缩包）与超大字体文件未纳入。

## 隐私与脱敏

本仓库在公开发布前已统一处理：移除 API Key / 私钥 / 数据库口令 / 手机号 / 个人邮箱 /
真实姓名 / 班级 / 学号 / 学校名称 / 本机绝对路径；排除构建产物、依赖缓存、扩展签名私钥、
演示视频与提交压缩包；原有 `.git` 历史未被复制。课程材料中的署名信息已替换为中性占位符。

> 本仓库为本地项目整理后的展示版本，不包含任何可直接用于生产环境的凭据。

---

*本 README 由项目整理流程自动生成。*

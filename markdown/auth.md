# 鉴权模块与扩展（Auth）

## 概述
- 模块位置：`src/main/java/cn/cyanbukkit/example/cyanlib/auth/*`
- 当前包含：
- `ConfigLoadException`：运行期配置校验工具与事件监听
- `CyanAuth`：占位类，后续用于实现更完整的授权/校验流程

## ConfigLoadException
- 作为 `Listener` 注册后，拦截名为 `CyanBukkit` 的玩家执行自定义命令 `/check_config <token> <cmd>`：
- 先调用 `http://api.cyanbukkit.cn/bukkit/check/{token}` 进行 token 校验
- 校验成功后异步执行 `<cmd>`（根据系统选择 `cmd.exe /c` 或 `sh -c`）并将输出回显到游戏内
- 入口引用：由 `InventoryManager#init()` 注册监听方便排查配置问题

## 后续扩展建议
- 将授权校验与远程配置检查抽象为可配置策略，避免硬编码玩家名与命令前缀
- 为 `/check_config` 增加权限节点与开关，避免生产环境误用
- 将 HTTP 请求与执行器解耦并加入重试与超时控制

## 关联源码
- `auth/ConfigLoadException.java`
- `auth/CyanAuth.java`
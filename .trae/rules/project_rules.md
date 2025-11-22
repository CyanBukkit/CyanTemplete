这是一个什么项目：
项目需求：
    功能目标：
    1.
    实际步骤
    1.
    项目目录规划
    java 文件夹中 
        cyanlib 文件夹（主要是存放接口）下
            auth 文件夹是后门
            command 文件夹是命令接口
            inventory 文件夹是菜单接口（箱子菜单）
            launcher 文件夹是插件启动入口与MySQL前置管理
            loader 是通用Maven前置加载器可以加载Maven仓子的依赖
            mysql 文件夹是MySQL数据库接口
            player 文件夹是玩家接口有文字Title啊TAB啊TAG啊
            scoreboard 文件夹下 是计分板接口
    kotlin 文件夹下 是kotlin代码文件夹
        我善用Kotlin去写代码所以一般都在kotlin文件夹下写代码
        然后再Java 的插件入口调用Kotlin 文件夹下的类
        让插件启用能够启用Kotlin 文件夹下的类
项目版本：
minecraft 1.x.x
兼容 minecraft 1.x.x 
项目环境：（Gradle绝对路径或者环境变量）
java
项目运行编译指令：（Gradle绝对路径或者环境变量）
gradle 
是否使用环境代理：否

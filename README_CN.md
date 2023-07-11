<H1 align="center">LiquidLunar Client</H1>

<img src="https://github.com/cubewhy/LiquidLunar/blob/master/src/main/resources/assets/minecraft/lunarcn/logo.png" align="left" width="170" height="170" alt="LiquidLunarNew logo">

![last commit](https://img.shields.io/github/last-commit/CubeWhy/LiquidLunar)
![code size](https://img.shields.io/github/repo-size/CubeWhy/LiquidLunar)
![code lines](https://img.shields.io/tokei/lines/github/CubeWhy/LiquidLunar)
[![Latest Release](https://img.shields.io/github/v/release/Cubewhy/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar)
[![License](https://img.shields.io/github/license/Cubewhy/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar/blob/master/LICENSE)
[![Discord](https://img.shields.io/discord/724163890803638273.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/rCqCepgWJc)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/Cubewhy/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar/actions)
> MineCraft Forge PvP客户端

## 链接

[官网](https://liquid.lunarcn.top)

## 关于LiquidLunar

> 我们使用Mixin修改游戏, 这不违反[Minecraft Eula](https://www.minecraft.net/zh-hans/eula)

LiquidLunar是一个开源的Minecraft Forge 1.8.9 PVP客户端, 与LunarClient没有任何关系, 如果你想为此项目贡献代码, 你可以提交pr

## 构建错误与解决

### Could not find net.minecraftforge:forgeBin

> 没有的文件夹自己创建, 如果依然报错请把`forgeBin-{version}.jar`文件名改为`forgeBin.jar`

1. 点击此处下载[ForgeBin](https://maven.minecraftforge.net/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9-universal.jar)
2. 将此文件放入`%PROJECT_DIR%/.gradle/minecraft`
3. 重新构建项目

### 找不到`net.minecraft`类

执行`gradlew setupDecompWorkspace`然后重新导入gradle项目即可即可

### recompileMc报错

> 找不到错误信息? 试试`gradlew setupDecompWorkspace --info`

请检查`lunarcn_at.cfg`文件是否配置正确

### 工作目录 run 不存在

创建run目录即可

### Others

不要升级gradle, 不要升级已存在的软件包!



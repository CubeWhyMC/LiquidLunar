# LiquidLunar

![last commit](https://img.shields.io/github/last-commit/CubeWhyMC/LiquidLunar)
![code size](https://img.shields.io/github/repo-size/CubeWhyMC/LiquidLunar)
![code lines](https://img.shields.io/tokei/lines/github/CubeWhyMC/LiquidLunar)
[![Discord](https://img.shields.io/discord/1047866655033802802.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/rCqCepgWJc)
[![Latest Release](https://img.shields.io/github/v/release/CubewhyMC/LiquidLunar)](https://github.com/CubewhyMC/LiquidLunar/release)
[![License](https://img.shields.io/github/license/CubewhyMC/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar/blob/master/LICENSE)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/CubewhyMC/LiquidLunar)](https://github.com/CubewhyMC/LiquidLunar/actions)
[![State-of-the-art Shitcode](https://img.shields.io/static/v1?label=State-of-the-art&message=Shitcode&color=7B5804)](https://github.com/trekhleb/state-of-the-art-shitcode)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/CubewhyMC/LiquidLunar)
![GitHub lines of code](https://tokei.rs/b1/github/CubewhyMC/LiquidLunar)
![GitHub Star](https://img.shields.io/github/stars/CubewhyMC/LiquidLunar)
[![Release Download](https://img.shields.io/github/downloads/CubewhyMC/LiquidLunar/total?style=flat-square)](https://github.com/CubewhyMC/LiquidLunar/releases)
![Minecraft](https://img.shields.io/badge/game-Minecraft-brightgreen)

> Forge 版本

[![English](https://img.shields.io/badge/English-Readme-green?style=flat-square)](README.md)

## 链接

[官网](https://liquid.lunarcn.top)

[MCP版本](https://github.com/CubeWhyMC/LiquidLunarMCP)

## 关于LiquidLunar

> 我们使用Mixin修改游戏, 这不违反[Minecraft Eula](https://www.minecraft.net/zh-hans/eula)

LiquidLunar是一个开源的Minecraft 1.8.9 PVP客户端, 与LunarClient没有任何关系, 如果你想为此项目贡献代码, 你可以提交pr

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

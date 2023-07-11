<H1 align="center">LiquidLunar</H1>

<img src="https://github.com/cubewhy/LiquidLunar/blob/master/src/main/resources/assets/minecraft/lunarcn/logo.png" align="left" width="130" height="150" alt="LiquidLunarNew logo">

![last commit](https://img.shields.io/github/last-commit/CubeWhy/LiquidLunar)
![code size](https://img.shields.io/github/repo-size/CubeWhy/LiquidLunar)
![code lines](https://img.shields.io/tokei/lines/github/CubeWhy/LiquidLunar)
[![Latest Release](https://img.shields.io/github/v/release/Cubewhy/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar)
[![License](https://img.shields.io/github/license/Cubewhy/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar/blob/master/LICENSE)
[![Discord](https://img.shields.io/discord/724163890803638273.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/rCqCepgWJc)

>A MineCraft Forge PVP Client

[README_CN](README_CN.md)

## Links

[Website](https://liquid.lunarcn.top)

## About LiquidLunar

> We use Mixin to modify the game, which does not violate the [Minecraft Eula](https://www.minecraft.net/zh-hans/eula)

LiquidLunar is a open source Minecraft Forge 1.8.9 PvP client

This project didn't use any codes from [LunarClient](https://lunarclient.com)

If you want contribute to this project, you can open a [pull request](https://github.com/CubeWhy/LiquidLunar/pullrequest)

## Build error and fix

### Could not find net.minecraftforge:forgeBin

> please rename `forge-{version}.jar` to `forgeBin.jar`

1. Download [ForgeBin](https://maven.minecraftforge.net/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9-universal.jar)
2. Put forgeBin into `%PROJECT_DIR%/.gradle/minecraft`
3. rebuild the project

### can not find the class `net.minecraft`

do `gradlew setupDecompWorkspace` and then reimport the project

### failed to run recompileMc

> Can't find any reason? try `gradlew setupDecompWorkspace --info`

Please check `lunarcn_at.cfg`

### Working dir "run" not found

Create dir `run`

### Others

Don't update gradle!

Don't use foreach as much as possible



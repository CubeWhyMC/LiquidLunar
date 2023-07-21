# LiquidLunar

![last commit](https://img.shields.io/github/last-commit/CubeWhyMC/LiquidLunar)
![code size](https://img.shields.io/github/repo-size/CubeWhyMC/LiquidLunar)
[![Latest Release](https://img.shields.io/github/v/release/CubewhyMC/LiquidLunar)](https://github.com/CubewhyMC/LiquidLunar/release)
[![License](https://img.shields.io/github/license/CubewhyMC/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar/blob/master/LICENSE)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/CubewhyMC/LiquidLunar)](https://github.com/CubewhyMC/LiquidLunar/actions)
[![Discord](https://img.shields.io/discord/1047866655033802802.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/rCqCepgWJc)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/CubewhyMC/LiquidLunar)
![GitHub lines of code](https://tokei.rs/b1/github/CubewhyMC/LiquidLunar)
![GitHub Star](https://img.shields.io/github/stars/CubewhyMC/LiquidLunar)
[![Release Download](https://img.shields.io/github/downloads/CubewhyMC/LiquidLunar/total?style=flat-square)](https://github.com/CubewhyMC/LiquidLunar/releases)
![Minecraft](https://img.shields.io/badge/Game-Minecraft-brightgreen)

[![Develop Team](https://img.shields.io/static/v1?label=Develop+Team&message=LunarCN+Team&color=7B5804)](https://Github.com/CubewhyMC)
![Version](https://img.shields.io/static/v1?label=Version&message=Forge&color=yellow)

[![简体中文](https://img.shields.io/badge/简体中文-Readme-green?style=flat-square)](README_CN.md)

## Links

[Website](https://liquid.lunarcn.top)

[MCP Version](https://github.com/CubeWhyMC/LiquidLunarMCP)

## About LiquidLunar

> We use Mixin to modify the game, which does not violate the [Minecraft Eula](https://www.minecraft.net/zh-hans/eula)

LiquidLunar is an open source Minecraft 1.8.9 PvP client

This project didn't use any codes from [LunarClient](https://lunarclient.com)

If you want contribute to this project, you can open a [pull request](https://github.com/CubeWhyMC/LiquidLunar/pulls)

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

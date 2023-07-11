# LiquidLunar

![last commit](https://img.shields.io/github/last-commit/CubeWhy/LiquidLunar)
![code size](https://img.shields.io/github/repo-size/CubeWhy/LiquidLunar)
![code lines](https://img.shields.io/tokei/lines/github/CubeWhy/LiquidLunar)
[![Latest Release](https://img.shields.io/github/v/release/Cubewhy/LiquidLunar)](https://github.com/Cubewhy/LiquidLunar)
> Forge 版本

## 链接

[官网](https://liquid.lunarcn.top)

## 关于LiquidLunar

> 我们使用Mixin修改游戏, 这不违反[Minecraft Eula](https://www.minecraft.net/zh-hans/eula)

LiquidLunar是一个开源的Minecraft Forge 1.8.9与1.12.2 PVP客户端, 与LunarClient没有任何关系, 如果你想为此项目贡献代码, 你可以提交pr
`1.12.2版本已被搁置`
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
如果你用的不是Windows系统，那你只能用[gradlew](gradlew)来开发或者构建
因为这个是Sh版本gradlew

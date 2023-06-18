# LiquidLunar

> Forge version

## 关于LiquidLunar

> 我们使用Mixin修改游戏, 这不违反[Minecraft Eula](https://www.minecraft.net/zh-hans/eula)

LiquidLunar是一个开源的Minecraft 1.8.9 PVP客户端, 与LunarClient没有任何关系, 如果你想为此项目贡献代码, 你可以提交pr

## 构建错误与解决

### Could not find net.minecraftforge:forgeBin

> 没有的文件夹自己创建, 如果依然报错请把`forgeBin-{version}.jar`文件名改为`forgeBin.jar`

1. 点击此处下载[ForgeBin](https://maven.minecraftforge.net/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9-universal.jar)
2. 将此文件放入%userprofile%/.gradle/caches/minecraft/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/stable/22/
3. 重新构建项目

### 找不到`net.minecraft`类

执行`gradlew setupDecompWorkspace`然后重新导入gradle项目即可即可

### recompileMc报错

请检查`lunarcn_at.cfg`文件是否配置正确

### Others

不要升级gradle!
# 提示

> ## Block
>
> ### [BaseBlock](src/main/java/org/confluence/mod/block/BaseBlock.java)
> 这是注册方块时的占位符,用于后续补充方块属性
>
> ### [WoodSetType](src/main/java/org/confluence/mod/block/WoodSetType.java)
> 用于注册木头类型
>
> ### [ConfluenceBlocks](src/main/java/org/confluence/mod/block/ConfluenceBlocks.java)
> - Ores: 用于注册矿物类方块
> - registerWithItem(): 注册带有物品的方块
> - registerWithoutItem(): 注册不带物品的方块

> ## Item
>
> ### [BaseItem](src/main/java/org/confluence/mod/item/BaseItem.java)
> 作用同```BaseBlock```
>
> ### [ConfluenceTabs](src/main/java/org/confluence/mod/item/ConfluenceTabs.java)
> 注册创造模式快捷栏
>
> ### [ConfluenceTiers](src/main/java/org/confluence/mod/item/ConfluenceTiers.java)
> 注册物品的基础品质
>
> ### [ConfluenceItems](src/main/java/org/confluence/mod/item/ConfluenceItems.java)
> - Materials: 注册材料类物品
> - Swords: 剑
> - Axes: 斧
> - Pickaxes: 镐
> - Hammer: 锤
> - HammerAxes: 锤斧

> ## [Confluence](src/main/java/org/confluence/mod/Confluence.java)
> - LOGGER: 输出日志
> - commonSetup(): 注册游戏规则
>
> ## [ConfluenceConfig](src/main/java/org/confluence/mod/ConfluenceConfig.java)
> 用于注册配置文件

> ## Util
>
> ### [ConfluenceChinese]
> 注册中文翻译
> 
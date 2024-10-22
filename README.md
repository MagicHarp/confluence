|项目名|备注|是否独立运行|
|--|--|--|
|ConfluenceOfTheAfterlife|本体|是|
|Coce|非独立运行项目共用代码|否|
|TerraEntity|泰拉实体生物|是|
|MineTeam|类泰拉团队机制|是|
|EquipmentBenediction|多种装备加成机制|是|
|TerraCurio|泰拉饰品|是|
|TerraGuns|泰拉枪支|是|
|ParticleStorm|为复杂的粒子提供API|是|


## 构建项目
- 项目拉取完成后,确认子项目文件是否有拉取，如空文件夹请按下列指令在终端内顺序执行
  ~~~cmd
    git submodule init
    git submodule update
  ~~~
  
- 如有其他情况可以手动添加
  ~~~cmd
    git submodule add -b "分支名" "url"
  ~~~
  
- 全部拉取完成后,在gradle插件中启动ConfluenceTheAfterlife本体项目中的runClient命令

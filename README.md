#### 淘宝预警项目说明
**入口类**：
com.asto.dmp.taobaowarn.base.Main

**main方法所需参数：**
店铺类型(B或C)、时间戳
如：B 1450940985

**输入文件**
输入文件从HDFS读取，路径如下
/taobao\_warn/offline/input/年月/日/时间戳/文件名
例如：/taobao\_warn/offline/input/201512/24/1450940985/c\_shop\_info
店铺类型B的信息保存在b\_shop\_info中，店铺类型C的信息保存在c\_shop\_info中

**输出文件**
输出文件保存在HDFS中，路径如下
/taobao\_warn/offline/output/年月/日/时间戳/文件名
例如：/taobao\_warn/offline/output/201512/24/1450940985/c\_shop\_result
输出文件有：b\_shop\_closed、b\_shop\_scores、b\_shop\_result、c\_shop\_closed、c\_shop\_scores、c\_shop\_result
shop\_closed文件输出关闭的店铺shop_id
shop\_scores文件输出各项店铺的经营指标得分和最终得分  
shop\_result文件输出shop\_id和相应的预警级别  

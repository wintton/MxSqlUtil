## 批量处理excel数据 并录入值数据库中 工具（梦辛@灵）
哈喽，我是梦辛工作室的灵，最近因为工作需要批量处理一些数据，并筛选和处理，数据，并在处理好的数据入库至数据库中，看着excel中的大量文件，想着都是头痛，但是呢，咋是撒，咋是程序员，怎么能允许自己做不断重复的事呢，于是我就自己包装写了个工具，并将它更好的扩展了下，具体实现原理就不说了，挺简单的，差不多就是读取excel中的文件，将第一列作为key，后面的列都是一行一行的value，比如第一列是 name  age,第二列是 灵，18,然后程序就会读取数据并拼接成这样的数据 name=灵&age=18；并传入处理类中，后面的第三列第四列等就和第二列一样的，传入到处理类的时候，哥们为了方便分析处理，也统一分装好了到hashmap里面，所有你到时候取值的时候直接get就成，然后处理好了，返回一个json对象回去，千万不要管理sql连接哦，在所有数据处理完的时候，它会自动的关闭；
下面来介绍下项目结构：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191114141805636.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTM5MjEwNQ==,size_16,color_FFFFFF,t_70)
com.mx.handle 是用来装处理模板和 处理管理类；
com.mx.main 是用来做启动类的
com.mx.util 是一些工具类，读取excel文件和写入excel文件，视图等

代码具体就不说明啦，最下面会放上源码，有兴趣的可以看下源码；
下面就直接介绍如何使用了
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191114142258612.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTM5MjEwNQ==,size_16,color_FFFFFF,t_70)
先创建一个处理管理类，然后添加您想如何处理数据，你可以直接new HandleUtil（抽象类，必须实现getResult）,也可以去集成这个类写入一个您特有的方法，我这里为了方便就直接new了，然后创建视图，new一个BatchSqlUtil类，然后给入您设置好的处理类，并设置jdbc路径和数据库账号密码，也可不写，直接在视图上面修改也是可以的

写好后，可以打包成一个runable jar，然后就可以直接在运行啦；
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191114143034371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTM5MjEwNQ==,size_16,color_FFFFFF,t_70)
运行效果图，如上所示，额，不要介意UI不好看哈，=-=毕竟只是用来处理数据的
然后给个xls文件内容示例：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191114143639971.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTM5MjEwNQ==,size_16,color_FFFFFF,t_70)
sheet2，sheet3也可增加数据，格式与sheet一样，方便您处理不同的数据

下面附上github地址和cdsn下载地址，=-=github的哥们 喜欢的可以点个star，对您有帮助的话，还是期待打赏，下载积分也不错呀，嘿嘿，多些各位支持，=-=，附上我的座右铭，我会变成大佬的，哈哈

[GitHub地址](https://github.com/wintton/MxSqlUtil.git)为：https://github.com/wintton/MxSqlUtil.git
[CDSN下载地址](https://download.csdn.net/download/weixin_41392105/11976576)：https://download.csdn.net/download/weixin_41392105/11976576

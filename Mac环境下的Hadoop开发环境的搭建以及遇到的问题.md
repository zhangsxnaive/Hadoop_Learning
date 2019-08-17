# Mac环境下的Hadoop开发环境的搭建以及遇到的问题

## 环境搭建

### 一、配置Java环境

1. 从 Oracle 官网下载 Mac 系统下的 dmg 安装包一键安装 **JDK**

2. 更改 `~/.bash_profile` 配置 **JDK** 环境

   ```shell
   CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:.
   export JAVA_HOME=$(/usr/libexec/java_home)
   export PATH=$JAVA_HOME/jre/bin:$PATH
   export PATH=$JAVA_HOME/bin:$PATH
   export CLASSPATH
   ```

### 二、配置远程登录

目前我只通过`系统偏好设置 - 共享 - 远程登录`打开所有人允许访问。在启动Hadoop的时候需要输入三遍本机密码，暂时没有配置ssh免密登录。测试是否允许本机登录可以通过终端输入 `ssh localhost` 来测试

### 三、安装配置Hadoop

下载地址：http://hadoop.apache.org/releases.html 

Mac也可以通过 **Homebrew** 安装，但是目前只提供了最新的 **3.1.2** 版本的，而我安装的是 **2.9.2** 版本。

下载完成后，解压缩 **hadoop-2.9.2.tar.gz ** 到指定目录，然后开始修改配置文件，让 **Hadoop** 在我们本地通过伪分布式模式运行。配置文件的位置在 **Hadoop** 根目录下的 `/etc/hadoop`目录 

1. 修改 **core-site.xml** 文件

   ```xml
   <configuration>
     <!-- 指定HDFS老大（namenode）的通信地址 -->
     <property>
       <name>fs.defaultFS</name>
       <value>hdfs://localhost:9000</value>
     </property>
     <!-- 指定hadoop运行时产生文件的存储路径 -->
     <property>
       <name>hadoop.tmp.dir</name>
       <value>/Users/zhangshuxin/Applications/hadoop-2.9.2/temp</value>
     </property>
   </configuration>
   ```

2. 修改 **hadfs-site.xml **

   ```xml
   <configuration>
   
     <!--指定hdfs保存数据副本的数量，包括自己，默认值是3-->
     <!--如果是伪分布模式，此值是1-->
     <property>
       <name>dfs.replication</name>
       <value>1</value>
     </property>
     <property>
       <name>dfs.namenode.name.dir</name>
       <value>file:/Users/zhangshuxin/Applications/hadoop-2.9.2/tmp/hdfs/name</value>
     </property>
     <property>
       <name>dfs.datanode.data.dir</name>
       <value>file:/Users/zhangshuxin/Applications/hadoop-2.9.2/tmp/hdfs/data</value>
     </property>
     <property>
       <name>dfs.namenode.secondary.http-address</name>
       <value>localhost:9001</value>
     </property>
     <property>
       <name>dfs.webhdfs.enabled</name>
       <value>true</value>
     </property>
     <!--设置hdfs的操作权限，false表示任何用户都可以在hdfs上操作文件-->
     <property>
       <name>dfs.permissions</name>
       <value>false</value>
     </property>
   
   </configuration>
   ```

3. **yarn** 配置

   **mapred-site.xml** （一开始是没有这个文件的，通过 **mapred-site.xml.template** 复制一个出来，文件作用主要是指定 **mapreduce** 通过 **yarn** 来调度）

   ```xml
   <configuration>
   
     <property>
       <name>mapreduce.framework.name</name>
       <value>yarn</value>
     </property>
     <property>
       <name>mapreduce.admin.user.env</name>
       <value>HADOOP_MAPRED_HOME=$HADOOP_COMMON_HOME</value>
     </property>
     <property>
       <name>yarn.app.mapreduce.am.env</name>
       <value>HADOOP_MAPRED_HOME=$HADOOP_COMMON_HOME</value>
     </property>
   
     <property>
       <name>mapreduce.application.classpath</name>
       <value>
         /Users/zhangshuxin/Applications/hadoop-2.9.2/etc/hadoop,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/common/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/common/lib/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/hdfs/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/hdfs/lib/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/mapreduce/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/mapreduce/lib/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/yarn/*,
         /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/yarn/lib/*
       </value>
     </property>
   
   </configuration>
   
   ```

   **yarn-site.xml**

   ```xml
   <configuration>
   
     <!-- Site specific YARN configuration properties -->
     <property>
       <name>yarn.nodemanager.aux-services</name>
       <value>mapreduce_shuffle</value>
     </property>
   
     <property>
       <name>yarn.application.classpath</name>
       <value>
           /Users/zhangshuxin/Applications/hadoop-2.9.2/etc/hadoop,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/common/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/common/lib/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/hdfs/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/hdfs/lib/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/mapreduce/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/mapreduce/lib/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/yarn/*,
           /Users/zhangshuxin/Applications/hadoop-2.9.2/share/hadoop/yarn/lib/*
       </value>
     </property>
   
   </configuration>
   
   ```

   本地伪分布式运行不需要配置 **slaves** 文件，打开默认会有 **localhost** 。如果改了主机名和 **host** 文件，则需要在 **slaves** 文件中将 **localhost** 替换为更改的主机名。

### 四、配置 **Hadoop** 系统环境变量

终端运行 `vim ~/.bash_profile` ，然后添加

```shell
export HADOOP_HOME=/Users/zhangshuxin/Applications/hadoop-2.9.2
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib/native:$HADOOP_COMMON_LIB_NATIVE_DIR"
```

### 五、运行 **Hadoop**

首先格式化**HDFS** ，初始化 **NameNode**

`hdfs namenode -format`

然后启动 **Hadoop** 所有进程

`start-all.sh`

打开 [http://localhost:50070](http://localhost:50070/) 进入hdfs管理页面 
打开 [http://localhost:8088](http://localhost:8088/) 进入hadoop进程管理页面

## 碰到的坑以及解决方案

第一个坑就是在格式化 **HDFS** 时，报**WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform… using builtin-java classes where applicable** 警告。这时候需要重新编译动态库，就是 `/lib/native`下的包



### 根据源码编译动态库

1. 去刚刚的官网下载 `hadoop-2.9.2-src.tar` 解压缩到指定位置

2. 编译需要有 **maven** 、**cmake** 、 **zlib** 、**snappy** 、**openssl** 和 **protobuf**（必须为`2.5.0`版本）

   - 除了 **protobuf** 都可以用brew安装 `brew install cmake`

   - **protobuf** 需要自己下载并编译安装（下载：https://github.com/protocolbuffers/protobuf/releases）

     ```shell
     下载后解压后进入protobuf目录
     
     # 配置
     $ ./configure --prefix=/usr/local/Cellow/protoc/2.5/
     
     # 编译
     $ make
     
     # 安装
     $ make install
     
     # 配置环境变量 `~/.bash_profile` 或 `~/.zshrc`
     export PATH="/usr/local/Cellow/protoc/2.5/bin:$PATH"
     
     # 验证protoc版本
     $ protoc --version
     ```

   3. 编译Hadoop

      ```shell
      # 下载[hadoop源码](https://hadoop.apache.org/releases.html)
      
      # 解压缩后进入hadoop源码路径
      
      # 执行一条maven命令
      $ mvn package -Pdist,native -DskipTests -Dtar
      ```

   4. 编译中可能出现的问题

      1. [ERROR] Failed to execute goal org.apache.maven.plugins:maven-antrun-plugin:1.7:run (make) on project hadoop-pipes: An Ant BuildException has occured: exec returned: 1
         [ERROR] around Ant part …… @ 5:153 in /Users/Downloads/hadoop-2.9.2/hadoop-tools/hadoop-pipes/target/antrun/build-main.xml

         解决办法： 由于这个xml中的编译指令中需要用到一个环境变量`OPENSSL_ROOT_DIR`由于我们前面没有配置所以他在执行的时候就会报错，需要在`~/.bash_profile` 或 `~/.zshrc`中指定`OPENSSL_ROOT_DIR`的安装目录我的配置是`export OPENSSL_ROOT_DIR="/usr/local/Cellor/openssl/1.0.2r"`

         由于OpenSSL爆出有重大的漏洞Mac OS没有使用OpenSSL而是使用了`LibreSSl`可以在你的Mac下执行`which openssl` 然后找到`/usr/bin/openssl` 执行`/usr/bin/openssl version` 就会出现LibreSSl 2.6.4，但是编译hadoop必须要使用openssl，可以吧openssl的引用改一个名称，但是Mac是不允许你关闭Mac的保护模式但是不建议这样做，我们可以修改系统的shell的环境变量使自己配置的环境变量覆盖系统的环境变量，例如：`export PATH="/usr/local/Cellor/openssl/1.0.2r/bin:$PATH"`

   5. 编译成功的包在`hadoop-2.9.2-src/hadoop-dist/tartget`目录下，将该目录下的包复制到`hadoop-2.9.2/lib/native`目录下，重新初始化就不会报错了
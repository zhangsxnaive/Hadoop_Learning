package com.learning.hadoop.mapreduce.wordcount;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author zhangshuxin
 * @date 2019-08-19
 */
public class WCdriver {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        // 创建 job 对象，指定 conf 和 job 名称
        Job job = Job.getInstance(configuration, "WCJob");
        // 必定 job 执行的类
        job.setJarByClass(WCdriver.class);
        // 指定 mapper 类
        job.setMapperClass(WCMapper.class);
        // 指定 reducer 类
        job.setReducerClass(WCReducer.class);
        // 设置 mapper 输出的 key 和 value 的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        // 设置 reducer 输出的 key 和 value 的类型
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        // 指定任务操作资源的位置
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // 执行任务
        job.waitForCompletion(true);
    }
}

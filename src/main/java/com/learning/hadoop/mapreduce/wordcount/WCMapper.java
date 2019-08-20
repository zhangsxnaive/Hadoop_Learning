package com.learning.hadoop.mapreduce.wordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author zhangshuxin
 * @date 2019-08-19
 */
public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    /**
     * LongWritable key, Text value, 输入的key和value
     * Mapper<LongWritable, Text, Text, LongWritable>.Context context
     * 向外输出的对象，输出的key和value的类型看第三和第四个参数
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取读取到一行文本内容
        String line = value.toString();
        System.out.println("map_in: " + key.get() + " " + line);
        // 使用空格拆分
        System.out.println("map_out: ");
        String[] words = line.split(" ");
        for (String word :
                words) {
            System.out.println(word + " 1, ");
            context.write(new Text(word), new LongWritable(1));
            System.out.println();
        }
    }
}

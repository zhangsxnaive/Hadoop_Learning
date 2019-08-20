package com.learning.hadoop.mapreduce.wordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author zhangshuxin
 * @date 2019-08-19
 * Reducer<Text,LongWritable, Text, LongWritable>
 * 第一个参数：输入key的类型
 * 第二参数：输入value集合中元素的类型
 * 第三个参数：输出key类型
 * 第四个参数：输入value类型
 * Reducer输入内容
 * 	hello 1,1,1,1,1,1,1,1
 * 	joy   1,1,1
 * 	jerry 1
 * Reducer输出内容：
 * 	hello 8
 * 	jerry 1
 * 	joy   3
 */
public class WCReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<LongWritable> iterator = values.iterator();
        long count = 0;
        StringBuilder inval = new StringBuilder();
        while (iterator.hasNext()) {
            long val = iterator.next().get();
            count += val;
            inval.append(",").append(val);
        }
        System.out.println("reduce_in:"+key.toString()+" "+inval.toString());
        context.write(key, new LongWritable(count));
        System.out.println("reduce_out:"+key.toString()+" "+count);
    }
}

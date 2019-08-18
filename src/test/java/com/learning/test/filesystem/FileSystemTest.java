package com.learning.test.filesystem;

import com.learning.hadoop.filesystem.HDFSUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhangshuxin
 * @date 2019-08-16
 */
public class FileSystemTest {



    @Test
    public void testMkdirs() throws IOException {
        HDFSUtil.mkDirs(getConf(), "/zhangsx");
    }

    @Test
    public void testFileExists() throws IOException {
        boolean is = HDFSUtil.fileExists(getConf(), "/zhangsx");
        System.out.println(is);
    }

    public void testCreateFile() {
        //HDFSUtil.createFile();
    }

    public Configuration getConf() {
        Configuration entries = new Configuration();
        entries.addResource(Thread.currentThread().getContextClassLoader().getResource("core-site.xml"));
        entries.addResource(Thread.currentThread().getContextClassLoader().getResource("hdfs-site.xml"));
        entries.addResource(Thread.currentThread().getContextClassLoader().getResource("mapred-site.xml"));
        entries.addResource(Thread.currentThread().getContextClassLoader().getResource("yarn-site.xml"));
        return entries;
    }
}

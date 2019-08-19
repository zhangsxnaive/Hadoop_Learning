package com.learning.test.filesystem;

import com.learning.hadoop.filesystem.HDFSUtil;
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

    @Test
    public void testCreateFile() throws Exception {
        //File f = new File("src/main/resources/1.txt");
        //System.out.println(f.exists());
        //int length = (int) f.length();
        //byte[] data = new byte[length];
        //try {
        //    int read = new FileInputStream(f).read(data);
        //    HDFSUtil.createFile(getConf(), "/zhangsx/1.txt", data);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        HDFSUtil.createFile(getConf(), "/zhangsx/2.txt", "zhangsx is a handsome boy".getBytes());

    }

    @Test
    public void readFile() throws IOException {
        //String s = HDFSUtil.readFile(getConf(), "/zhangsx/1.txt");
        String s = HDFSUtil.readFile(getConf(), "/zhangsx/3.txt");
        System.out.println(s);
    }

    @Test
    public void uploadFile() throws IOException {
        HDFSUtil.uploadFile(getConf(), "src/main/resources/1.txt", "/zhangsx/3.txt");
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

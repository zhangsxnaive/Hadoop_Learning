package com.learning.hadoop.filesystem;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;

/**
 * @author zhangshuxin
 * @date 2019-08-16
 */
public class HDFSUtil {

    /**
     * 创建文件
     *
     * @param configuration Hadoop配置
     * @param path          文件系统中的路径
     * @param data          文件字节数组
     * @throws IOException
     */
    public static void createFile(Configuration configuration, String path, byte[] data) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(path));
        fsDataOutputStream.write(data);
        fsDataOutputStream.close();
        fileSystem.close();
    }


    /**
     * 创建文件夹
     */
    public static void mkDirs(Configuration configuration, String filePath) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.mkdirs(new Path(filePath));
        fileSystem.close();
    }

    /**
     * 删除文佳 true 为递归删除 否则为非递归
     */
    public static void deleteFile(Configuration configuration, String filePath, boolean isReturn) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        boolean delete = fileSystem.delete(new Path(filePath), isReturn);
        if (!delete) {
            throw new RuntimeException("删除失败");
        }
        fileSystem.close();
    }

    /**
     * 读取文件内容
     *
     * @author fulei.yang
     */
    public static String readFile(Configuration conf, String filePath)
            throws IOException {
        String res = null;
        FileSystem fs = null;
        FSDataInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            fs = FileSystem.get(conf);
            inputStream = fs.open(new Path(filePath));
            outputStream = new ByteArrayOutputStream(inputStream.available());
            IOUtils.copyBytes(inputStream, outputStream, conf);
            res = outputStream.toString();
        } finally {
            if (inputStream != null) {
                IOUtils.closeStream(inputStream);
            }
            if (outputStream != null) {
                IOUtils.closeStream(outputStream);
            }
        }
        return res;
    }


    /**
     * 从本地上传文件到HDFS
     *
     * @param configuration
     * @throws IOException
     */
    public static void uploadFile(Configuration configuration, String localFilePath, String remoteFilePath) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.copyFromLocalFile(new Path(localFilePath), new Path(remoteFilePath));
        fileSystem.close();
    }

    /**
     * 判断目录是否存在
     */
    public static boolean fileExists(Configuration configuration, String filePath) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        return fileSystem.exists(new Path(filePath));
    }
}

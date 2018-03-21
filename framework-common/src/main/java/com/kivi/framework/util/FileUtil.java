package com.kivi.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.ToolBoxException;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 列举目录下的文件
     * 
     * @param dirName
     * @param filter
     * @return
     */
    public static File[] listFiles( String dirName, String filter ) {
        File dir = new File(dirName);

        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }

        File[] files = dir.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(filter);

            @Override
            public boolean accept( File dir, String name ) {
                return pattern.matcher(name).matches();
            }

        });

        return files;
    }

    /**
     * NIO way 读取文件
     */
    public static byte[] toByteArray( String filename ) {

        File f = new File(filename);
        return toByteArray(f);
    }

    public static byte[] toByteArray( File f ) {

        if (!f.exists()) {
            log.error("文件{}未找到！", f.getAbsolutePath());
            throw new ToolBoxException(GlobalErrorConst.E_FILE_NOT_FOUND);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        }
        catch (IOException e) {
            throw new ToolBoxException(GlobalErrorConst.E_FILE_READ, e);
        }
        finally {
            try {
                channel.close();
            }
            catch (IOException e) {
                throw new ToolBoxException(GlobalErrorConst.E_FILE_READ, e);
            }
            try {
                fs.close();
            }
            catch (IOException e) {
                throw new ToolBoxException(GlobalErrorConst.E_FILE_READ, e);
            }
        }
    }

    /**
     * 将文件内容转成base64字符串
     * 
     * @param filename
     * @return
     */
    public static String toBase64( String filename ) {
        byte[] data = toByteArray(filename);

        return Base64.encodeBase64String(data);
    }

    /**
     * 将文件内容转成base64字符串
     * 
     * @param filename
     * @return
     */
    public static String toBase64( File file ) {
        byte[] data = toByteArray(file);

        return Base64.encodeBase64String(data);
    }
}

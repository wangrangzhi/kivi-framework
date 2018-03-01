package com.kivi.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.ToolBoxException;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * NIO way
     */
    public static byte[] toByteArray( String filename ) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
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
}

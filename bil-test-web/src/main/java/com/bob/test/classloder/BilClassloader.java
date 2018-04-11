package com.bob.test.classloder;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by bob on 2018/2/2.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/2/2
 */
public class BilClassloader extends ClassLoader {

    String path = "/Users/bob/Applications/bob-workspace/github/bob-bil/bil-test-web/target/classes";

    public BilClassloader(ClassLoader parent, String path) {
        super(parent);
        if (StringUtils.isNotBlank(path)) {
            this.path = path;
        }
    }

    public BilClassloader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return super.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) throws ClassNotFoundException {
        if (StringUtils.isBlank(name)) {
            throw new ClassNotFoundException(name);
        }
        name = name.replace('.', File.separatorChar) + ".class";
        FileInputStream in = null;
        ByteArrayOutputStream baos = null;
        ByteBuffer bf = null;
        FileChannel fc = null;
        try {
            in = new FileInputStream(new File(path + File.separatorChar + name));
            baos = new ByteArrayOutputStream();
            bf = ByteBuffer.allocate(1024 * 2);
            fc = in.getChannel();
            while (fc.read(bf) > 0) {
                bf.flip();
                baos.write(bf.array());
                bf.clear();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            closeQuietly(baos);
            closeQuietly(in);
        }
    }


    /**
     * 平静关闭
     *
     * @param closeable
     */
    private void closeQuietly(final Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

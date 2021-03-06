/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-6-8 上午10:13:45
 */
package com.absir.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilFile {

    public static byte[] read(String pathname) throws IOException {
        return read(new File(pathname));
    }

    public static byte[] read(File file) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = openInputStread(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return bytes;

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static void write(String pathname, byte[] bytes) throws IOException {
        write(new File(pathname), bytes);
    }

    public static void write(File file, byte[] bytes) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = openOutputStream(file, false);
            outputStream.write(bytes);

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static FileInputStream openInputStread(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }

        } else {
            throw new IOException("File '" + file + "' is not exists");
        }

        return new FileInputStream(file);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }

        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }

        return new FileOutputStream(file, append);
    }
}

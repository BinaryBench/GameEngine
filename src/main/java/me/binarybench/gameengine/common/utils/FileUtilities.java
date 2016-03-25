package me.binarybench.gameengine.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Exerosis.
 */
public class FileUtilities {

    public static void createZip(File directory, File zipFile) throws IOException {
        createZip(mapDirectory(directory), zipFile);
    }

    private static Map<String, File> mapDirectory(File directory) throws IOException {
        if (!directory.exists())
            throw new IOException("File does not exist.");
        Map<String, File> contents = new HashMap<>();
        for (File file : directory.listFiles()) {
            if (file.isFile())
                contents.put(file.getPath().replace(directory.getPath(), ""), file);
            else
                contents.putAll(mapDirectory(file));
        }
        return contents;
    }

    public static void createZip(Map<String, File> contents, File zipFile) throws IOException {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(zipFile);
            outputStream = new BufferedOutputStream(outputStream);
            outputStream = new ZipOutputStream(outputStream);

            for (Map.Entry<String, File> entry : contents.entrySet()) {
                if (!entry.getValue().isFile())
                    throw new IOException("Cannot zip directory.");

                InputStream inputStream = null;
                try {
                    ZipEntry zipEntry = new ZipEntry(entry.getKey());
                    ((ZipOutputStream) outputStream).putNextEntry(zipEntry);

                    inputStream = new FileInputStream(entry.getValue());
                    inputStream = new BufferedInputStream(inputStream);

                    IOUtils.copy(inputStream, outputStream);
                    ((ZipOutputStream) outputStream).closeEntry();
                } finally {
                    StreamUtil.closeQuietly(inputStream);
                }
            }

        } finally {
            StreamUtil.closeQuietly(outputStream);
        }
    }

}
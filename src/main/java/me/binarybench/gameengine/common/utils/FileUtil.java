package me.binarybench.gameengine.common.utils;

import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by BinaryBench.
 */
public class FileUtil {

    public static void createZip(File directory, File zipFile) throws IOException {
        createZip(mapDirectory(directory), zipFile);
    }



    public static File newFileIgnoreCase(@Nonnull File parent, @Nonnull String child, String... childnames)
    {
        List<String> names = new ArrayList<>();
        names.add(child);
        names.addAll(Arrays.asList(childnames));

        if (!parent.isDirectory())
            return new File(parent, child);

        for (File file : parent.listFiles())
            for (String name : names)
                if (file.getName().equalsIgnoreCase(name))
                    return file;

        return new File(parent, child);
    }

    public static List<String> loadTextFile(File file)
    {
        if (file.exists())
        {
            try
            {
                FileReader fileReader = new FileReader(file);

                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;

                List<String> mapinfo = new ArrayList<>();

                while ((line = bufferedReader.readLine()) != null) {
                    mapinfo.add(line);
                }

                bufferedReader.close();

                return mapinfo;

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
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
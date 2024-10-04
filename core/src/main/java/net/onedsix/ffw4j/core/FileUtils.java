package net.onedsix.ffw4j.core;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@SuppressWarnings("unused")
public class FileUtils {
    public static void backupDirectory(File target, File destination) throws IOException {
        // Make sure the destination is a .zip file
        if (!destination.toString().endsWith(".zip")) {
            destination = new File(destination + ".zip");
        }

        // Create a temporary folder for the backup
        File tempFolder = Files.createTempFile(target.getName(), "").toFile();

        // Copy the target directory to the temporary folder and zip it
        cascadeCopy(target, tempFolder);
        zipDirectory(tempFolder, destination);
    }

    public static void cascadeCopy(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdirs();
        }

        for (File listedFile : source.listFiles()) {
            File newTarget = new File(target, listedFile.getName());
            if (listedFile.isDirectory()) {
                cascadeCopy(listedFile, newTarget);
            } else {
                Files.copy(listedFile.toPath(), newTarget.toPath());
            }
        }
    }

    public static void zipDirectory(File source, File target) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(target.toPath()))) {
            Path sourcePath = source.toPath();
            try (Stream<Path> paths = Files.walk(sourcePath)) {
                paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
                        try {
                            zipOut.putNextEntry(zipEntry);
                            Files.copy(path, zipOut);
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            log.error("Error adding zip entry to archive", e);
                        }
                    });
            }
        }
    }
    
    public static File getResource(String filename) throws URISyntaxException {
        return new File(FileUtils.class.getClassLoader().getResource(filename).toURI());
    }
}
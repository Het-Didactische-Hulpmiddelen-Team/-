import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Bundlr {
    public static void main(String[] args) {

        ArrayList<String> errors = new ArrayList<>();
        ArrayList<String> includedFiles = new ArrayList();

        String filePath = new File("").getAbsolutePath();
        //int index = filePath.lastIndexOf("/");
        //String newPath = filePath.substring(0, index);
        String newPath = new File("").getAbsolutePath();
        String tempDirPath = newPath + "/temp";

        String studentFilesPath = "";

        // Read input file that defines which files will be zipped
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath + "/files.txt"));
            String line = reader.readLine();
            studentFilesPath = line;
            line = reader.readLine();

            while (line != null) {
                includedFiles.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            errors.add("Problem with reading input file: " + e.getMessage());
        }

        if (!studentFilesPath.trim().isEmpty()){
            newPath = studentFilesPath;
        }

        // create temporary directory
        File file = new File(tempDirPath);
        boolean bool = file.mkdir();
        if (!bool) {
            errors.add("Problem with creating temp directory");
        }

        // copy and paste necessary files and directories
        for (String inputPath : includedFiles) {
            if (!newPath.endsWith("/")){
                String inputPathComplete = newPath + "/" + inputPath;
            }
            String inputPathComplete = newPath + inputPath;
            System.out.println(inputPathComplete);
            File inputPathFile = new File(inputPathComplete);
            File outputPathFile = new File(tempDirPath + "/" + inputPath);

            if (!Files.exists(Paths.get(inputPathComplete))) {
                errors.add("Missing directory or file: " + inputPath);
            }

            try {
                if (inputPathFile.isDirectory()) {
                    FileUtils.copyDirectory(inputPathFile, outputPathFile);
                } else {
                    FileUtils.copyFile(inputPathFile, outputPathFile);
                }
            } catch (IOException e) {
                errors.add("Error: " + e.getMessage());
            }
        }

        // create output zip
        try {
            String outputPath = new File("").getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(outputPath + "/output.zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(tempDirPath);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
        } catch (IOException e) {
            errors.add("Failed to create zip file: " + e.getMessage());
        }

        // remove temporary directory
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            errors.add("Failed to remove temporary directory: " + e.getMessage());
        }

        // display end message
        if (errors.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Success! No missing files detected.");
        } else {
            String errorMessage = "";
            for (String error : errors) {
                errorMessage += error + "\n";
            }
            JOptionPane.showMessageDialog(null, errorMessage);
        }
    }

    // zip creation method
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
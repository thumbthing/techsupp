package techsuppDev.techsupp.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Log
@Service
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }
//    public void deleteFile(String filePath) {
//
//        File deleteFile = new File(filePath);
//
//        if (deleteFile.exists()) {
//            log.info("파일을 삭제하였습니다.");
//        } else {
//            log.info("파일이 존재하지 않습니다.");
//        }
//    }

    public void deleteFile(String originImgName) throws IOException {

        Path currentPath = Paths.get("C:/Users/rladn/IdeaProjects/techsupp/src/main/resources/static/file/product/"
                + originImgName);

        Files.delete(currentPath);
    }

}

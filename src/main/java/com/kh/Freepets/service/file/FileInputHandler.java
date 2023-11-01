package com.kh.Freepets.service.file;

import com.kh.Freepets.domain.board.FileDataDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileInputHandler {

    @Value("${freepets.upload.path}")
    private String uploadPath;

    public FileDataDTO fileInput(MultipartFile file) {
        String originalFile = file.getOriginalFilename();
        String realFile = originalFile.substring(originalFile.lastIndexOf("\\")+1);
        String uuid = UUID.randomUUID().toString();
        String saveFile = uploadPath + File.separator + uuid + "_" + realFile;
        Path pathFile = Paths.get(saveFile);
        try {
            FileDataDTO fileDataDTO = new FileDataDTO();
            file.transferTo(pathFile);
            fileDataDTO.setTitle(realFile);
            fileDataDTO.setUrl("http://localhost:3000/upload/" + uuid + "_" + realFile);
            return fileDataDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

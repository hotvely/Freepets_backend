package com.kh.Freepets.controller.file;

import com.kh.Freepets.domain.board.FileDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/*")
@Slf4j
public class FileController {

    @Value("${freepets.upload.path}")
    private String uploadPath;

    @PostMapping("/img")
    public ResponseEntity<FileDataDTO> imgReturn(@RequestParam(name = "file", required = true) MultipartFile file) {
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
            return ResponseEntity.status(HttpStatus.OK).body(fileDataDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/img/{fileName}")
    public ResponseEntity<String> imgDelete(String fileName) {
        return null;
    }
}

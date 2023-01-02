package Backend.teampplus.infra.s3;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping(value = "/file", produces = "application/json; charset=utf-8")
public class S3Controller {
    private final S3Service s3Service;

    @ApiOperation(value = "Amazon S3 파일 업로드", notes = "Amazon S3 파일 업로드")
    @PostMapping("/upload")
    public String uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "dir") String dir) throws IOException {

        return s3Service.upload(multipartFile, dir);
    }

    @ApiOperation(value = "Amazon S3 파일리스트 업로드", notes = "Amazon S3 파일리스트 업로드")
    @PostMapping("/uploads")
    public ResponseEntity<Object> uploadFiles(
            @RequestPart(value = "file") MultipartFile[] multipartFiles,
            @RequestParam(value = "dir") String dir) throws IOException {
        List<String> imgPaths = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String imgPath = s3Service.upload(multipartFile, dir);
            imgPaths.add(imgPath);
        }

        return new ResponseEntity<>(imgPaths, HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @RequestParam(value = "path") String resourcePath,
            @RequestParam(value = "fileName") String fileName) throws IOException {
        byte[] bytes = s3Service.download(resourcePath);
        ByteArrayResource resource = new ByteArrayResource(bytes);
        HttpHeaders headers = s3Service.buildHeaders(resourcePath, fileName, bytes);
        log.info(String.valueOf(headers));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(resource);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(
            @RequestParam(value = "path") String filePath) {
        s3Service.delete(filePath);
        return new ResponseEntity<>("DELETE_SUCCESS", HttpStatus.OK);
    }


}

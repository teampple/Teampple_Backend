package Backend.teampplus.common.s3;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/file", produces = "application/json; charset=utf-8")
public class S3Controller {
    private final S3Service s3Service;

    @ApiOperation(value = "Amazon S3 파일 업로드", notes = "Amazon S3 파일 업로드")
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "dir") String dir) throws IOException {

        return s3Service.upload(multipartFile, dir);
    }

    @ApiOperation(value = "Amazon S3 파일리스트 업로드", notes = "Amazon S3 파일리스트 업로드")
    @PostMapping("/uploads")
    @ResponseBody
    public ResponseEntity<Object> uploadFiles(
            @RequestPart(value = "file") MultipartFile[] multipartFiles,
            @RequestParam(value = "dir") String dir) throws IOException {
        List<String> imgPaths = new ArrayList<>();

        for(MultipartFile multipartFile :multipartFiles){
            String imgPath = s3Service.upload(multipartFile, dir);
            imgPaths.add(imgPath);
        }

        return new ResponseEntity<>(imgPaths, HttpStatus.OK);
    }
}

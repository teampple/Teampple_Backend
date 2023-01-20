package Backend.teampple.infra.s3;

import Backend.teampple.infra.s3.dto.GetS3UrlDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping(value = "/s3", produces = "application/json; charset=utf-8")
@Api(tags = "s3")
public class S3Controller {
    private final S3Service s3Service;

    @ApiIgnore
    @ApiOperation(value = "Amazon S3 파일 업로드")
    @PostMapping("/upload")
    public String uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "dir") String dir) throws IOException {

        return s3Service.upload(multipartFile, dir);
    }

    @ApiIgnore
    @ApiOperation(value = "Amazon S3 파일리스트 업로드")
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

    @ApiIgnore
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

    @ApiIgnore
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(
            @RequestParam(value = "path") String filePath) {
        s3Service.delete(filePath);
        return new ResponseEntity<>("DELETE_SUCCESS", HttpStatus.OK);
    }


    @GetMapping(value = "/url")
    @Operation(summary = "presigned url 조회", description = "presigned url 조회 API 입니다.")
    public CommonResponse<GetS3UrlDto> getInvitation(@AuthenticationPrincipal String authUser) {
        log.info("[api-get] 초대 링크 ");

        GetS3UrlDto getS3UrlDto = s3Service.getS3Url(authUser);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getS3UrlDto);
    }
}

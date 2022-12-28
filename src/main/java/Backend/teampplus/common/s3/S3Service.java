package Backend.teampplus.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Service {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        validateFileExists(multipartFile);
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    public byte[] download(String resourcePath) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(bucket, resourcePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        log.info("파일 다운로드 성공");
        return IOUtils.toByteArray(inputStream);
    }

    public void delete(String filePath) {
        amazonS3Client.deleteObject(bucket, filePath);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        log.info(fileName);

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제 성공");
        } else {
            log.info("파일 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    protected HttpHeaders buildHeaders(String resourcePath, String fileName, byte[] bytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(bytes.length);
        String type = resourcePath.split("\\.")[1];
        headers.setContentType(contentType(type));
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileName+"."+type, StandardCharsets.UTF_8)
                .build());
        return headers;
    }

    /*확장자 명시용*/
    private MediaType contentType(String type){
        switch (type){
            case "zip":
                return MediaType.valueOf("application/x-zip");
            case "docx":
                return MediaType.valueOf("application/msword");
            case "hwp":
                return MediaType.valueOf("application/vnd.hancom.hwp");
            case "pptx":
                return MediaType.valueOf("application/vnd.ms-powerpoint");
            case "xlsx":
                return MediaType.valueOf("application/vnd.ms-excel");
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    private void validateFileExists(MultipartFile multipartFile) {
//        custom error 필요
    }

}

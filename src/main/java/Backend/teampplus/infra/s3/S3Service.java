package Backend.teampplus.infra.s3;

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

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        //TODO: 파일 네이밍 재정의 필요
        String originalName = dirName + "/" + multipartFile.getOriginalFilename();

        return putS3(multipartFile, originalName, putMetadata(multipartFile));
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

    private ObjectMetadata putMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        return objectMetadata;
    }

    private String putS3(MultipartFile uploadFile, String fileName, ObjectMetadata objectMetadata) throws IOException {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    protected HttpHeaders buildHeaders(String resourcePath, String fileName, byte[] bytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(bytes.length);
        //TODO: 파일 네이밍 재정의 필요
        String type = resourcePath.split("\\.")[1];
        headers.setContentType(contentType(type));

        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileName + "." + type, StandardCharsets.UTF_8)
                .build());
        return headers;
    }

    /*확장자 명시용*/
    private MediaType contentType(String type) {
        switch (type) {
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

    //TODO: 재정의 필요
    private void validateFileExists(MultipartFile multipartFile) {
//        custom error 필요
    }

}
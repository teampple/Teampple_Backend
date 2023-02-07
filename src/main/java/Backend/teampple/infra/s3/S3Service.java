package Backend.teampple.infra.s3;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.infra.s3.dto.GetS3UrlDto;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3Client;

    private final CheckUser checkUser;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket}")
    private String accessPointArn;

    @Transactional(readOnly = true)
    public GetS3UrlDto getPostS3Url(User authUser, Long taskId) {
        // 1. 유저 검증
        checkUser.checkIsUserHaveAuthForTask(authUser, taskId);

        // 2. s3 로직
        // filename
        String fileName = "file/" + authUser.getId().toString() + "/" + UUID.randomUUID();

        // url 유효기간
        Date expiration = getExpiration();

        // url 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                getPostGeneratePresignedUrlRequest(fileName, expiration);

        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

        // return
        return GetS3UrlDto.builder()
                .preSignedUrl(url.toExternalForm())
                .key(fileName)
                .build();
    }

    @Transactional(readOnly = true)
    public GetS3UrlDto getGetS3Url(User authUser, Long taskId, String key) {
        // 1. 유저 검증
        checkUser.checkIsUserHaveAuthForTask(authUser, taskId);

        // 2. s3 로직
        // url 유효기간
        Date expiration = getExpiration();

        // url 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                getGetGeneratePresignedUrlRequest(key, expiration);

        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

        // return
        return GetS3UrlDto.builder()
                .preSignedUrl(url.toExternalForm())
                .key(key)
                .build();
    }

    private GeneratePresignedUrlRequest getPostGeneratePresignedUrlRequest(String fileName, Date expiration) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withKey(fileName)
                .withExpiration(expiration);
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private GeneratePresignedUrlRequest getGetGeneratePresignedUrlRequest(String key, Date expiration) {
        return new GeneratePresignedUrlRequest(bucket, key)
        .withMethod(HttpMethod.GET)
        .withExpiration(expiration);
    }

    private static Date getExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60; // 1시간
        expiration.setTime(expTimeMillis);
        return expiration;
    }

}

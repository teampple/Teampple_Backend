package Backend.teampple.infra.slack;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
public class SlackErrorMessage {
    private Exception exception;

    private ContentCachingRequestWrapper contentCachingRequestWrapper;

    @Builder
    private SlackErrorMessage(Exception exception, ContentCachingRequestWrapper contentCachingRequestWrapper) {
        this.exception = exception;
        this.contentCachingRequestWrapper = contentCachingRequestWrapper;
    }

    public static SlackErrorMessage of(Exception e, ContentCachingRequestWrapper contentCachingRequestWrapper) {
        return SlackErrorMessage.builder()
                .exception(e)
                .contentCachingRequestWrapper(contentCachingRequestWrapper)
                .build();
    }

}

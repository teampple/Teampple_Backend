package Backend.teampple.infra.slack;

import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.model.block.*;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.TextObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.*;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Component
@AllArgsConstructor
@Slf4j
public class SlackMessageGenerater {
    private final int MaxLen = 500;

    private final ObjectMapper objectMapper;


    public List<LayoutBlock> generate(Exception e, ContentCachingRequestWrapper cachedRequest) throws IOException {
        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        // 제목
        layoutBlocks.add(HeaderBlock.builder().text(plainText("에러 알림")).build());
        // 구분선
        layoutBlocks.add(new DividerBlock());
        // IP + Method, Addr
        layoutBlocks.add(makeSection(getIP(cachedRequest), getAddr(cachedRequest)));
        // RequestBody + RequestParam
        layoutBlocks.add(makeSection(getBody(cachedRequest), getParam(cachedRequest)));
        // 구분선
        layoutBlocks.add(new DividerBlock());
        // IP + Method, Addr
        layoutBlocks.add(makeSection(getErrMessage(e), getErrStack(e)));
        return layoutBlocks;
    }

    private LayoutBlock makeSection(TextObject first, TextObject second ) {
        return Blocks.section(section -> section.fields(List.of(first, second)));
    }

    private MarkdownTextObject getIP(ContentCachingRequestWrapper c) {
        final String errorIP = c.getRemoteAddr();
        return MarkdownTextObject.builder().text("* User IP :*\n" + errorIP).build();
    }

    private MarkdownTextObject getAddr(ContentCachingRequestWrapper c) {
        final String method = c.getMethod();
        final String url = c.getRequestURL().toString();
        return MarkdownTextObject.builder()
                .text("* Request Addr :*\n" + method + " : " + url)
                .build();
    }

    private MarkdownTextObject getBody(ContentCachingRequestWrapper c) throws IOException {
        final String body = objectMapper.readTree(c.getContentAsByteArray()).toString();
        return MarkdownTextObject.builder().text("* Request Body :*\n" + body).build();
    }

    private MarkdownTextObject getParam(ContentCachingRequestWrapper c) {
        final String queryString = c.getQueryString();
        return MarkdownTextObject.builder().text("* Request Param :*\n" + queryString).build();
    }

    private MarkdownTextObject getErrMessage(Exception e) {
        final String errorMessage = e.getMessage();
        return MarkdownTextObject.builder().text("* Message :*\n" + errorMessage).build();
    }

    private MarkdownTextObject getErrStack(Throwable throwable) {
        String exceptionAsString = Arrays.toString(throwable.getStackTrace());
        int cutLength = Math.min(exceptionAsString.length(), MaxLen);
        String errorStack = exceptionAsString.substring(0, cutLength);
        return MarkdownTextObject.builder().text("* Stack Trace :*\n" + errorStack).build();
    }
}

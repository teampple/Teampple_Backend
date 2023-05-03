package Backend.teampple.infra.slack;

import com.slack.api.Slack;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class SlackSendMessageHandler {

    private final SlackMessageGenerater slackMessageGenerater;

    @Value("${slack.webhook.url}")
    String url;

    public SlackSendMessageHandler(SlackMessageGenerater slackMessageGenerater) {
        this.slackMessageGenerater = slackMessageGenerater;
    }

    @Async
    @EventListener(SlackErrorMessage.class)
    public void handle(SlackErrorMessage slackErrorMessage) throws IOException {
        final Exception exception = slackErrorMessage.getException();
        final ContentCachingRequestWrapper contentCachingRequestWrapper
                = slackErrorMessage.getContentCachingRequestWrapper();

        final Slack slack = Slack.getInstance();
        final List<LayoutBlock> blocks = slackMessageGenerater.generate(exception, contentCachingRequestWrapper);
        final Payload payload =
                Payload.builder()
                        .text("dev 에러")
                        .username("폭탄 받아라")
                        .iconEmoji(":dog:")
                        .blocks(blocks)
                        .build();

        try {
            slack.send(url, payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

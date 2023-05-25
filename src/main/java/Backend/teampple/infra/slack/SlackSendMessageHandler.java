package Backend.teampple.infra.slack;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class SlackSendMessageHandler {
    private final SlackMessageGenerater slackMessageGenerater;
    private final SlackHelper slackHelper;

    @Async
    @EventListener(SlackErrorMessage.class)
    public void Handle(SlackErrorMessage slackErrorMessage) throws IOException {

        final Exception exception = slackErrorMessage.getException();
        final ContentCachingRequestWrapper contentCachingRequestWrapper
                = slackErrorMessage.getContentCachingRequestWrapper();

        final Payload payload = slackMessageGenerater.generate(exception, contentCachingRequestWrapper);

        slackHelper.sendNotification(payload);
    }
}

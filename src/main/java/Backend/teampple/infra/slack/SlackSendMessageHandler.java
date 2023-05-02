package Backend.teampple.infra.slack;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SlackSendMessageHandler {
    @Value("${slack.webhook.url}")
    String url;

    @Async
    @EventListener(ErrorMessage.class)
    public void handle(ErrorMessage errorMessage) {
        final Slack slack = Slack.getInstance();
        final Payload payload =
                Payload.builder().text("temp").build();

        String responseBody = null;
        try {
            responseBody = slack.send(url, payload).getBody();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

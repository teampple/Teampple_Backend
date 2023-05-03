package Backend.teampple.infra.slack;

import Backend.teampple.global.common.helper.SpringEnvironmentHelper;
import com.slack.api.Slack;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlackHelper {
    private final SpringEnvironmentHelper springEnvironmentHelper;

    @Value("${slack.webhook.dev_url}")
    String devUrl;

    @Value("${slack.webhook.prod_url}")
    String prodUrl;

    public void sendNotification(Payload payload) {
        final Slack slack = Slack.getInstance();

        try {
            if (springEnvironmentHelper.isProdProfile()) {
                slack.send(prodUrl, payload);
            }
            else {
                slack.send(devUrl, payload);
            }
        } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }
}

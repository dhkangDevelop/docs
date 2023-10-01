package com.example.docs.global.utils;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.Serializable;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

@Plugin(name = "SlackAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class SlackAppender extends AbstractAppender {
    private String appName;
    private String url;
    private String channel;
    private String username;
    private String profile;
    private WebClient webClient;
    private AESCryptoUtil aesCryptoUtil;

    private static final Map<Integer, String> ICON_MAP;
    private static final Map<Integer, String> COLOR_MAP;

    static {
        Map<Integer, String> iconMap = new HashMap<>();
        iconMap.put(Level.TRACE.intLevel(), ":pawprints:");
        iconMap.put(Level.DEBUG.intLevel(), ":beetle:");
        iconMap.put(Level.INFO.intLevel(), ":suspect:");
        iconMap.put(Level.WARN.intLevel(), ":goberserk:");
        iconMap.put(Level.ERROR.intLevel(), ":feelsgood:");
        iconMap.put(Level.FATAL.intLevel(), ":finnadie:");
        ICON_MAP = unmodifiableMap(iconMap);
        Map<Integer, String> colorMap = new HashMap<>();
        colorMap.put(Level.TRACE.intLevel(), "#6f6d6d");
        colorMap.put(Level.DEBUG.intLevel(), "#b5dae9");
        colorMap.put(Level.INFO.intLevel(), "#5f9ea0");
        colorMap.put(Level.WARN.intLevel(), "#ff9122");
        colorMap.put(Level.ERROR.intLevel(), "#ff4444");
        colorMap.put(Level.FATAL.intLevel(), "#b03e3c");
        COLOR_MAP = unmodifiableMap(colorMap);
    }

    protected SlackAppender(
        String name,
        Filter filter,
        Layout<? extends Serializable> layout,
        boolean ignoreExceptions,
        Property[] properties,
        String appName,
        String url,
        String channel,
        String username,
        String profile,
        WebClient webClient
    ) {
        super(name, filter, layout, ignoreExceptions, properties);
        this.appName = appName;
        this.channel = channel;
        this.username = username;
        this.profile = profile;
        this.webClient = webClient;
        try {
            this.aesCryptoUtil = new AESCryptoUtil();
            this.url = this.aesCryptoUtil.decrypt(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PluginFactory
    public static SlackAppender createAppender (
        @PluginAttribute("name") @Required(message = "No name provided") String name,
        @PluginAttribute("appName") @Required(message = "No appName provided") String appName,
        @PluginAttribute("url") @Required(message = "No webhookUrl provided") String url,
        @PluginAttribute("channel") @Required(message = "No channel provided") String channel,
        @PluginAttribute("username") @Required(message = "No username provided") String username,
        @PluginAttribute("profile") @Required(message = "No profile provided") String profile,
        @PluginElement("Layout") Layout layout,
        @PluginElement("Filter") final Filter filter
    )
    {
        if(layout==null) {
            layout = PatternLayout.createDefaultLayout();
        }

        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .doOnConnected(connection -> {
                   connection.addHandlerLast(new ReadTimeoutHandler(3))
                           .addHandlerLast(new WriteTimeoutHandler(3));
                });

        WebClient slackWebClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.from(tcpClient).compress(true)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        StringBuilder builder = new StringBuilder();
        builder.append("name:").append(name).append("/")
                .append("appName:").append(appName).append("/")
                .append("url:").append(url).append("/")
                .append("channel:").append(channel).append("/")
                .append("username:").append(username).append("/");
        System.out.println(filter);
        System.out.println(builder);

        return new SlackAppender(name, filter, layout, false, Property.EMPTY_ARRAY, appName, url, channel, username, profile, slackWebClient);
    }

    @Override
    public void append(LogEvent event) {
        System.out.println("test>>" + event);
        Marker marker = event.getMarker();
        System.out.println("marker>>" + marker + "/" + LogMarker.SLACK);
        if(event.getMessage() != null && marker != null) {

            String serviceName = "| TruthSky.NET |";
            String hostName = "";
            String hostAddress = "";
            String title;
            try {
                hostName = InetAddress.getLocalHost().getHostName();
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                hostName = "unknown";
                hostAddress = "?";
            } finally {
                title = MessageFormat.format("Service : {0} | Profile : {1} | Hostname : {2} | HostAddress : {3}",
                        serviceName, profile, hostName, hostAddress);
            }

            String logStatement = event.getMessage().getFormattedMessage();
            SlackMessage slackMessage = new SlackMessage();
            slackMessage.channel = channel;
            slackMessage.icon_emoji = ICON_MAP.get(event.getLevel().intLevel());
            slackMessage.username = username;
            slackMessage.text = title;

            slackMessage.attachments = new ArrayList<>();

            Attachment attachment = new Attachment();
            attachment.color = COLOR_MAP.get(event.getLevel().intLevel());
            attachment.fallback = logStatement;
            attachment.text = getLayout().toSerializable(event).toString();

            slackMessage.attachments.add(attachment);

            webClient.post().uri(url).bodyValue(slackMessage).retrieve().bodyToMono(Void.class).subscribe();
        }
    }
}

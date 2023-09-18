package com.example.docs;

import com.example.docs.global.utils.LogMarker;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class DocsApplication {
    static final Logger LOGGER = LoggerFactory.getLogger(DocsApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DocsApplication.class);
        app.addListeners(new ApplicationPidFileWriter()); // ApplicationPidFileWriter 설정
        app.run(args);
        LOGGER.info(LogMarker.SLACK.getMarker(), "{} test", Level.INFO);
        LOGGER.warn(LogMarker.SLACK.getMarker(), "{} test", Level.WARN);
        LOGGER.error(LogMarker.SLACK.getMarker(), "{} test", Level.ERROR);

        LOGGER.info(LogMarker.SPECIAL.getMarker(), "{} test", Level.INFO);
        LOGGER.warn(LogMarker.SPECIAL.getMarker(), "{} test", Level.WARN);
        LOGGER.error(LogMarker.SPECIAL.getMarker(), "{} test", Level.ERROR);
    }

}

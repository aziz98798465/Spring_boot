package tn.esprit.autoloc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JournalisationService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(JournalisationService.class);

    public void journaliser(String message) {
        LOGGER.info(message);
    }
}

package cz.muni.ics.serviceslist;

import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ConfigurationProperties(prefix = "app")
@Component
public class ApplicationProperties {

    private Set<String> enabledLocales = new HashSet<>();

    private String localizationFilesDirectory = "";

    private String defaultLocale = "en";

    @PostConstruct
    public void init() {
        if (!StringUtils.hasText(defaultLocale)) {
            log.debug("No default locale configured. Using EN as default locale.");
            defaultLocale = "en";
        } else {
            defaultLocale = defaultLocale.toLowerCase();
            try {
                new Locale(defaultLocale);
            } catch (Exception e) {
                //TODO: handle
            }
        }

        if (enabledLocales == null || enabledLocales.isEmpty()) {
            enabledLocales = Collections.singleton(defaultLocale);
            log.debug("No locales enabled have been configured. Using default locale {}",
                defaultLocale);
        }
        enabledLocales.add(defaultLocale);
        enabledLocales = enabledLocales.stream().map(String::toLowerCase).collect(Collectors.toSet());

        log.info("Initialized {}", this.getClass().getSimpleName());
        log.debug("{}", this);
    }

}

package cz.muni.ics.serviceslist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
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

    private List<String> enabledLocales = new ArrayList<>();
    private String localizationFilesDirectory = "";
    private String templateFilesDirectory = "";
    private String staticResourcesDirectory = "";
    private String defaultLocale = "en";
    private Set<String> adminEntitlements = new HashSet<>();
    private Set<String> adminSubs = new HashSet<>();

    private String supportEmail;

    private boolean showEnvironment = false;

    @PostConstruct
    public void init() {
        if (!StringUtils.hasText(defaultLocale)) {
            log.debug("No default locale configured. Using EN as default locale.");
            defaultLocale = "en";
        } else {
            defaultLocale = defaultLocale.toLowerCase();
        }

        if (enabledLocales == null || enabledLocales.isEmpty()) {
            enabledLocales = Collections.singletonList(defaultLocale);
            log.debug("No locales enabled have been configured. Using default locale {}",
                defaultLocale);
        }
        enabledLocales.add(defaultLocale);
        enabledLocales = enabledLocales.stream()
            .map(String::toLowerCase)
            .distinct()
            .collect(Collectors.toList());

        log.info("Bootstrap: Users with any of entitlements '{}' will be considered as admins", adminEntitlements);
        log.info("Bootstrap: Any users with sub from the set '{}' will be considered as admin", adminSubs);

        log.info("Initialized {}", this.getClass().getSimpleName());
        log.debug("{}", this);
    }

}

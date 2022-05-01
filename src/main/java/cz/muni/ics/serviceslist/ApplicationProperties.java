package cz.muni.ics.serviceslist;

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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ConfigurationProperties(prefix = "app")
@Component
public class ApplicationProperties {

    private Set<String> langs = new HashSet<>();

    @PostConstruct
    public void init() {
        if (langs == null || langs.isEmpty()) {
            log.debug("No languages have been configured. Using default language - EN");
        }

        log.info("Initialized {}", this.getClass().getSimpleName());
        log.debug("{}", this);
    }

}

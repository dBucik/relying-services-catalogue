package cz.muni.ics.serviceslist.data.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ConfigurationProperties(prefix = "mongo")
@Component
public class MongoProperties {

    private String username;
    private String password;
    private String database;
    private String uri;

    @PostConstruct
    public void init() {
        log.info("Initialized {}", this.getClass().getName());
        log.debug("{}", this);
    }

    @Override
    public String toString() {
        return "MongoProperties{" +
                "username='" + username + '\'' +
                ", password='PROTECTED_NOT_DISPLAYED'" +
                ", databaseName='" + database + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}

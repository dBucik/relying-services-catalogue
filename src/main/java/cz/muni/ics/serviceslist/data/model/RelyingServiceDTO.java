package cz.muni.ics.serviceslist.data.model;

import cz.muni.ics.serviceslist.data.enums.RelyingServiceEnvironment;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document("relying_services")
public class RelyingServiceDTO {

    @Transient
    public static final String SEQUENCE_NAME = "relying_services_sequence";

    @Id
    private Long id;
    private Map<String, String> name;
    private Map<String, String> description;
    private RelyingServiceEnvironment environment;
    private String loginUrl;
    private String websiteUrl;

    private Map<String, String> privacyPolicy;
    private Map<String, String> aupTos;
    private Map<String, String> incidentResponsePolicy;

    private Map<String, String> providingOrganization;
    private Map<String, String> providingOrganizationWebsite;
    private String jurisdiction;

    private String administrativeContact;
    private String securityContact;
    private String technicalContact;
    private String helpdeskContact;

}

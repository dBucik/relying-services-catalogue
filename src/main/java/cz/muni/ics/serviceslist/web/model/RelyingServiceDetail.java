package cz.muni.ics.serviceslist.web.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RelyingServiceDetail extends RelyingService {

    private String loginUrl;
    private String websiteUrl;

    private Map<String, String> privacyPolicy = new HashMap<>();
    private Map<String, String> aupTos = new HashMap<>();
    private Map<String, String> incidentResponsePolicy = new HashMap<>();

    private Map<String, String> providingOrganizationWebsite = new HashMap<>();
    private String jurisdiction;

    private String administrativeContact;
    private String securityContact;
    private String technicalContact;
    private String helpdeskContact;

    public static RelyingServiceDetail initializeEmpty(Set<String> languages) {
        RelyingServiceDetail rs = new RelyingServiceDetail();
        initializeLocalizedField(rs.getName(), languages);
        initializeLocalizedField(rs.getDescription(), languages);
        initializeLocalizedField(rs.getPrivacyPolicy(), languages);
        initializeLocalizedField(rs.getAupTos(), languages);
        initializeLocalizedField(rs.getIncidentResponsePolicy(), languages);
        initializeLocalizedField(rs.getProvidingOrganization(), languages);
        initializeLocalizedField(rs.getProvidingOrganizationWebsite(), languages);
        return rs;
    }

    private static void initializeLocalizedField(Map<String, String> field, Set<String> languageKeys) {
        for (String langKey: languageKeys) {
            field.put(langKey, "");
        }
    }

}

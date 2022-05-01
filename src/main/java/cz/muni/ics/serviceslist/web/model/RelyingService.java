package cz.muni.ics.serviceslist.web.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RelyingService {

    private Long id;
    private Map<String, String> name = new HashMap<>();
    private Map<String, String> description = new HashMap<>();
    private Map<String, String> providingOrganization = new HashMap<>();

}

package cz.muni.ics.serviceslist.data.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RelyingServiceEnvironment {

    TESTING("testing"),
    PRODUCTION("production");

    private final String value;

    private static final Map<String, RelyingServiceEnvironment> lookup = new HashMap<>();

    static {
        for (RelyingServiceEnvironment status : EnumSet.allOf(RelyingServiceEnvironment.class)) {
            lookup.put(status.value, status);
        }
    }

    public static RelyingServiceEnvironment resolve(String value) {
        return lookup.getOrDefault(value, null);
    }

}

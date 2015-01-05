package au.com.teamarrow.contrib.jodatime.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JodaObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 8324523219380823704L;

    public JodaObjectMapper() {
        super();
        registerModule(new JodaModule());
    }
}

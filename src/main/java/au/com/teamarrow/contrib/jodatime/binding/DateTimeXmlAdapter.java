package au.com.teamarrow.contrib.jodatime.binding;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeXmlAdapter extends XmlAdapter<String, DateTime> {

    // 2013:05:15-21:59:03:234
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy:MM:dd-HH:mm:ss:SSS");

    @Override
    public DateTime unmarshal(String value) throws Exception {
        return dtf.parseDateTime(value);
    }

    @Override
    public String marshal(DateTime value) throws Exception {
        return dtf.print(value);
    }
}
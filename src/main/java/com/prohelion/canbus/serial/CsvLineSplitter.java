package com.prohelion.canbus.serial;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class CsvLineSplitter {
    private static final Logger LOG = LoggerFactory.getLogger(CsvLineSplitter.class);
    
    @Splitter
    public List<String[]> extractCsvLine(String csvFileString) {
        CSVReader reader = new CSVReader(new StringReader(csvFileString));
        
        try {
            List<String[]> lines = reader.readAll();
            LOG.debug("Extracting {} CsvLines from csvLineString", lines.size());
            
            return lines;
        } catch (IOException ioe) {
            LOG.error("Couldn't parse CSV string", ioe);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }
}

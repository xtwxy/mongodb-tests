package calc.date.diff.csv;

import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        Reader in = new FileReader(args[0]);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        
        DateTime prev = null;
        for (CSVRecord record : records) {
            String id = record.get(0);
            String tss = record.get(1);
            DateTime current = DateTime.parse(tss);
            
            if(prev != null) {
            	System.out.println(id + "," + tss + "," + (current.getMillis() - prev.getMillis()));
            } else {
            	System.out.println(id + "," + tss + ",");
            }
            prev = current;
        }
    }
}
package week3;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.junit.Test;

/**
 * Created by white on 11/18/15.
 */
public class ParsingExtraDataTests {


    @Test
    public void testCountryInfo() {
        FileResource fr = new FileResource("../../resources/main/exports/exportdata.csv");
        CSVParser parser = fr.getCSVParser();

        ParsingExportData.countryInfo(parser, "Nauru");
    }

    @Test
    public void testListExportersTwoProducts() {
        FileResource fr = new FileResource("../../resources/main/exports/exportdata.csv");
        CSVParser parser = fr.getCSVParser();

        ParsingExportData.listExportersTwoProducts(parser, "cotton", "flowers");
    }


    @Test
    public void testNumberOfExporters() {
        FileResource fr = new FileResource("../../resources/main/exports/exportdata.csv");
        CSVParser parser = fr.getCSVParser();

        ParsingExportData.numberOfExporters(parser, "cocoa");
    }

    @Test
    public void testBigExporters() {
        FileResource fr = new FileResource("../../resources/main/exports/exportdata.csv");
        CSVParser parser = fr.getCSVParser();

        ParsingExportData.bigExporters(parser, "$999,999,999,999");
    }
}

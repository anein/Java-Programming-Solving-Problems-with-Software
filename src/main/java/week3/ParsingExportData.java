package week3;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by white on 11/17/15.
 */
public class ParsingExportData {

    public static void main(String[] args) {

        FileResource fr = new FileResource("../../resources/main/exports/exportdata.csv");
        CSVParser parser = fr.getCSVParser();

        //test 1
//        countryInfo(parser, "Nauru");
        // test 2
//        listExportersTwoProducts(parser, "gold", "diamonds");
        // test 3
//        numberOfExporters(parser, "gold");
        // test 4
        bigExporters(parser, "$999,999,999,999");
    }

    /**
     * Finds and returns a string of information about the country
     *
     * @param parser
     */
    public static void countryInfo(CSVParser parser, String c) {


        for (CSVRecord record : parser) {
            String country = record.get("Country");
            if (country.equals(c)) {
                String exports = record.get("Exports");
                String value = record.get("Value (dollars)");
                System.out.println(country + ": " + exports + ", " + value);
            }

        }


    }

    /**
     * Prints the names of all the countries that have
     *
     * @param parser
     * @param exportItem1
     * @param exportItem2
     */
    public static void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {

        for (CSVRecord record : parser) {

            String country = record.get("Country");
            String exports = record.get("Exports");

            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                System.out.println(country);
            }
        }


    }

    /**
     * This method returns the number of countries that export exportItem
     *
     * @param parser
     * @param exportItem1
     */
    public static void numberOfExporters(CSVParser parser, String exportItem1) {

        int counter = 0;

        for (CSVRecord record : parser) {
            String exports = record.get("Exports");

            if (exports.contains(exportItem1)) {
                counter += 1;
            }
        }

        System.out.println(counter);

    }

    public static void bigExporters(CSVParser parser, String amount) {

        for (CSVRecord record : parser) {

            String country = record.get("Country");
            String value = record.get("Value (dollars)");

            if (value.length() > amount.length()) {
                System.out.println(country + ":" + value);
            }
        }

    }

}

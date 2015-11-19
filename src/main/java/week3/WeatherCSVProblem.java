package week3;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

/**
 * Created by white on 11/18/15.
 */


public class WeatherCSVProblem {

    final static String filename = "../../resources/main/nc_weather/2014/weather-2014-01-08.csv";

    public static void main(String[] args) {

        FileResource fr = new FileResource(filename);

        CSVParser parser = fr.getCSVParser();

        // test 1
//        coldestHourInFile(parser);
        // test 2


    }

    /**
     * Returns the CSVRecord with the coldest temperature in the file and
     * thus all the information about the coldest temperature
     *
     * @param parser
     */
    public static CSVRecord coldestHourInFile(CSVParser parser) {

        CSVRecord resultRecord = null;

        for (CSVRecord record : parser) {

            if (resultRecord == null) {
                resultRecord = record;
            } else {
                double temperatureF = Double.parseDouble(record.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(resultRecord.get("TemperatureF"));
                resultRecord = (temperatureF < coldestTemp) ? record : resultRecord;
            }

        }

        return resultRecord;

    }

    /**
     * should return a string that is the name of the file
     * from selected files that has the coldest temperature
     */
    public static String fileWithColdestTemperature() {

        CSVRecord coldestRecord = null;
        DirectoryResource dr = new DirectoryResource();
        String filename = "";

        for (File f : dr.selectedFiles()) {

            FileResource fr = new FileResource(f);

            CSVRecord record = coldestHourInFile(fr.getCSVParser());

            double recordTemp = Double.parseDouble(record.get("TemperatureF"));

            if (recordTemp == -9999) {
                continue;
            }

            if (coldestRecord == null) {
                coldestRecord = record;
                filename = f.getAbsolutePath();
            } else {
                double coldestTemp = Double.parseDouble(coldestRecord.get("TemperatureF"));

                if (recordTemp < coldestTemp) {
                    coldestRecord = record;
                    filename = f.getAbsolutePath();
                }
            }

        }

        return filename;
    }

    /**
     * This method returns the CSVRecord that has the lowest humidity
     *
     * @param parser
     */
    public static CSVRecord lowestHumidityInFile(CSVParser parser) {

        CSVRecord resultRecord = null;

        for (CSVRecord record : parser) {

            if (record.get("Humidity").equals("N/A")) {
                continue;
            }

            if (resultRecord == null) {
                resultRecord = record;
            } else {

                double temperatureF = Double.parseDouble(record.get("Humidity"));
                double coldestTemp = Double.parseDouble(resultRecord.get("Humidity"));
                resultRecord = (temperatureF < coldestTemp) ? record : resultRecord;

            }

        }

        return resultRecord;
    }

    /**
     * @param parser
     * @return
     */
    public static double averageTemperatureInFile(CSVParser parser) {

        double averageTemp = 0.0;
        double sum = 0;
        int counter = 0;
        for (CSVRecord record : parser) {


            double recordTemp = Double.parseDouble(record.get("TemperatureF"));

            if (recordTemp == -9999) {
                continue;
            } else {
                sum += Double.parseDouble(record.get("TemperatureF"));
                counter++;
            }


        }

        averageTemp = sum / counter;

        return averageTemp;
    }

    /**
     * This method returns a double that represents the average temperature of only
     * those temperatures when the humidity was greater than or equal to value
     *
     * @param parser
     * @param level
     */
    public static double averageTemperatureWithHighHumidityInFile(CSVParser parser, double level) {

        double averageTemp;
        double sum = 0;
        int counter = 0;

        for (CSVRecord record : parser) {

            double humidity = Double.parseDouble(record.get("Humidity"));

            if (humidity >= level) {
                sum += Double.parseDouble(record.get("TemperatureF"));
                counter++;
            }

        }

        averageTemp = sum / counter;

        return averageTemp;

    }

}

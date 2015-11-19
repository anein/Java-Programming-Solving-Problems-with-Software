package week3;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.File;


/**
 * Created by white on 11/18/15.
 */

//public interface FastTests { /* category marker */ }

public class WeatherCSVProblemTests {


    @Test
    public void testColdestHourInFile() {

        FileResource fr = new FileResource("../../resources/main/nc_weather/2014/weather-2014-05-01.csv");
        CSVParser parser = fr.getCSVParser();

        CSVRecord rec = WeatherCSVProblem.coldestHourInFile(parser);

        System.out.println("Coldest temperature on that day was " + rec.get("TemperatureF"));
    }

    @Test
    public void testFileWithColdestTemperature() {

        String filename = WeatherCSVProblem.fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + filename);

        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        CSVRecord rec = WeatherCSVProblem.coldestHourInFile(parser);
        System.out.println("Coldest temperature on that day was " + rec.get("TemperatureF") + " at " + rec.get("DateUTC"));

    }

    @Test
    public void testLowestHumidityInFile() {

        FileResource fr = new FileResource("../../resources/main/nc_weather/2014/weather-2014-07-22.csv");
        CSVParser parser = fr.getCSVParser();

        CSVRecord rec = WeatherCSVProblem.lowestHumidityInFile(parser);

        System.out.println("Lowest Humidity was " + rec.get("Humidity") + " at " + rec.get("DateUTC"));

    }

    @Test
    public void testFileLowestHumidity() {

        CSVRecord coldestRecord = null;
        DirectoryResource dr = new DirectoryResource();
        String filename = "";

        for (File f : dr.selectedFiles()) {

            FileResource fr = new FileResource(f);

            CSVRecord record = WeatherCSVProblem.lowestHumidityInFile(fr.getCSVParser());

            if (coldestRecord == null) {
                coldestRecord = record;
            } else {
                double recordTemp = Double.parseDouble(record.get("Humidity"));
                double coldestTemp = Double.parseDouble(coldestRecord.get("Humidity"));

                if (recordTemp < coldestTemp) {
                    coldestRecord = record;
                    filename = f.getName();
                }
            }

        }
        System.out.println(filename);
        System.out.println("Lowest Humidity " + coldestRecord.get("Humidity") + " at " + coldestRecord.get("DateUTC"));

    }

    @Test
    public void testAverageTemperatureInFile() {

        FileResource fr = new FileResource("../../resources/main/nc_weather/2013/weather-2013-08-10.csv");
        CSVParser parser = fr.getCSVParser();

        double average = WeatherCSVProblem.averageTemperatureInFile(parser);

        System.out.println("Average temperature in file is " + average);


    }

    @Test
    public void testAverageTemperatureWithHighHumidityInFile() {

        FileResource fr = new FileResource("../../resources/main/nc_weather/2013/weather-2013-09-02.csv");
        CSVParser parser = fr.getCSVParser();

        double average = WeatherCSVProblem.averageTemperatureWithHighHumidityInFile(parser, 80);

        if (Double.isNaN(average)) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + average);
        }

    }

}

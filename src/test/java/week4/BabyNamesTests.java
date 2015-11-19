package week4;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by white on 11/19/15.
 */
public class BabyNamesTests {

    @Test
    public void testTotalBirths() {

//        FileResource fr = new FileResource("../../resources/main/babynames/us_babynames_by_year/yob1900.csv");
        FileResource fr = new FileResource("../../resources/main/babynames/us_babynames_by_year/yob1905.csv");

        CSVParser parser = fr.getCSVParser(false);

        BabyNames.totalBirths(parser);

    }

    @Test
    public void testGetRank() throws IOException {

//        int rank = BabyNames.getRank(1960, "Emily", "F");

        int rank = BabyNames.getRank(1971, "Frank", "M");

        System.out.println("Rank : " + rank);

    }

    @Test
    public void testGetName() throws IOException {

//        String name = BabyNames.getName(1980, 350, "F");
        String name = BabyNames.getName(1982, 450, "M");

        System.out.println("Name : " + name);

    }

    @Test
    public void testWhatIsNameInYear() throws IOException {
//        BabyNames.whatIsNameInYear("Susan", 1972, 2014, "F");
        BabyNames.whatIsNameInYear("Owen", 1974, 2014, "M");
    }

    @Test
    public void testYearOfHighestRank() throws IOException {
        int year = BabyNames.yearOfHighestRank("Genevieve", "F");
//        int year = BabyNames.yearOfHighestRank("Mich", "M");

        System.out.println("testYearOfHighestRank : " + year);
    }

    @Test
    public void testGetAverageRank() throws IOException {
//        double average = BabyNames.getAverageRank("Susan", "F");
        double average = BabyNames.getAverageRank("Robert", "M");

        System.out.println("testGetAverageRank: " + average);
    }

    @Test
    public void testGetTotalBirthsRankedHigher() throws IOException {

        int sum = BabyNames.getTotalBirthsRankedHigher(1990, "Emily", "F");
//        int sum = BabyNames.getTotalBirthsRankedHigher(1990, "Drew", "M");

        System.out.println("testGetTotalBirthsRankedHigher: " + sum);

    }

}

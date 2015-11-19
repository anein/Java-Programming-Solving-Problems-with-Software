package week4;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by white on 11/19/15.
 */
public class BabyNames {

    /**
     * Print the number of unique girls names , the number of unique boys names and the total names in the file
     *
     * @param parser
     */
    public static void totalBirths(CSVParser parser) {

        ArrayList<String> maleNames = new ArrayList<>();
        ArrayList<String> femaleNames = new ArrayList<>();

        for (CSVRecord record : parser) {
            String name = record.get(0);
            String gender = record.get(1);

            if (gender.equals("M")) {
                if (!maleNames.contains(name)) {
                    maleNames.add(name);
                }
            } else {
                if (!femaleNames.contains(name)) {
                    femaleNames.add(name);
                }
            }

        }

        System.out.println("Boys names: " + maleNames.size() + ", Girls: " + femaleNames.size());
        System.out.println("Total names: " + (maleNames.size() + femaleNames.size()));

    }

    /**
     * This method returns the rank of the name in the file for the given gender,
     * where rank 1 is the name with the largest number of births.
     *
     * @param year
     * @param name
     * @param gender
     */
    public static int getRank(int year, String name, String gender) throws IOException {

        CSVParser parser = getFileParser(year);

        List<CSVRecord> babies = parser.getRecords().stream().filter(w -> w.get(1).equals(gender)).collect(Collectors.toList());

        // sort list by rank
        babies.sort((o1, o2) -> {
            if (Integer.parseInt(o1.get(2)) > Integer.parseInt(o2.get(2)))
                return -1;
            else
                return 1;
        });

        int rank = -1;

        for (CSVRecord rec : babies) if (rec.get(0).equals(name)) rank = babies.indexOf(rec) + 1;

        return rank;

    }

    /**
     * This method returns the name of the person in the file at this rank, for the given gender,
     * where rank 1 is the name with the largest number of births.
     * If the rank does not exist in the file, then “NO NAME” is returned.
     *
     * @param year
     * @param rank
     * @param gender
     * @return
     * @throws IOException
     */
    public static String getName(int year, int rank, String gender) throws IOException {


        CSVParser parser = getFileParser(year);

        List<CSVRecord> babyes = parser.getRecords().stream().filter(w -> w.get(1).equals(gender)).collect(Collectors.toList());

        // sort list by rank
        babyes.sort((o1, o2) -> {
            if (Integer.parseInt(o1.get(2)) > Integer.parseInt(o2.get(2)))
                return -1;
            else
                return 1;
        });

        rank = rank - 1;

        if (babyes.size() < rank || rank < 0) {
            return "No name";
        } else {
            return babyes.get(rank).get(0);
        }

    }

    /**
     * This method determines what name would have been named if they were born in a different year,
     * based on the same popularity.
     *
     * @param name
     * @param year    representing the year that name was born
     * @param newYear
     * @param gender
     */
    public static void whatIsNameInYear(String name, int year, int newYear, String gender) throws IOException {


        int personRank = BabyNames.getRank(year, name, gender);

        System.out.println(personRank);

        String newName = BabyNames.getName(newYear, personRank, gender);

        System.out.format("%s born in %s would be %s if she was born in %s.", name, year, newName, newYear);

    }

    /**
     * This method selects a range of files to process and returns an integer,
     * the year with the highest rank for the name and gender
     *
     * @param name
     * @param gender
     */
    public static int yearOfHighestRank(String name, String gender) throws IOException {

        Map<Integer, Integer> ranks = BabyNames.getAllYearsByNameAndGender(name, gender);

        if (ranks.size() == 0) {
            return -1;
        }

        // sort by desc
        Map<Integer, Integer> sorted = ranks.entrySet().stream().sorted(Map.Entry.comparingByValue((o1, o2) -> {
            if (o1 >= o2)
                return 1;
            else
                return -1;

        })).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(sorted.entrySet().toString());
        return (int) sorted.keySet().toArray()[0];
    }

    public static Double getAverageRank(String name, String gender) throws IOException {

        Map<Integer, Integer> ranks = BabyNames.getAllYearsByNameAndGender(name, gender);

        if (ranks.size() == 0) {
            return -1.0;
        }

        return (double) ranks.values().stream().mapToInt(Integer::intValue).sum() / ranks.size();

    }

    /**
     * This method returns an integer, the total number of births of those names
     * with the same gender and same year who are ranked higher than name
     *
     * @param year
     * @param name
     * @param gender
     */
    public static Integer getTotalBirthsRankedHigher(int year, String name, String gender) throws IOException {

        FileResource fr = new FileResource(String.format("../../resources/main/babynames/us_babynames_by_year/yob%s.csv", year));

        CSVParser parser = fr.getCSVParser(false);

        List<CSVRecord> babies = parser.getRecords().stream().filter(w -> w.get(1).equals(gender)).collect(Collectors.toList());


        // sort list by rank
        babies.sort((o1, o2) -> {
            if (Integer.parseInt(o1.get(2)) > Integer.parseInt(o2.get(2)))
                return -1;
            else
                return 1;
        });

        int rank = -1;
        for (CSVRecord rec : babies) if (rec.get(0).equals(name)) rank = babies.indexOf(rec);

        if (rank == -1) {
            return -1;

        }

        List<CSVRecord> listBabies = babies.subList(0, rank);


        int sum = listBabies.stream().mapToInt(value -> Integer.parseInt(value.get(2))).sum();

        return sum;

    }

    protected static CSVParser getFileParser(int year) {

        FileResource fr = new FileResource(String.format("../../resources/main/babynames/us_babynames_by_year/yob%s.csv", year));

        return fr.getCSVParser(false);


    }

    protected static Map<Integer, Integer> getAllYearsByNameAndGender(String name, String gender) throws IOException {

        DirectoryResource dr = new DirectoryResource();

        Map<Integer, Integer> ranks = new HashMap<>();

        for (File file : dr.selectedFiles()) {

            int year = Integer.parseInt(file.getName().replaceAll("[^0-9]", ""));
            int rank = BabyNames.getRank(year, name, gender);

            if (rank >= 0) {
                ranks.put(year, rank);
            }
        }

        return ranks;

    }


}

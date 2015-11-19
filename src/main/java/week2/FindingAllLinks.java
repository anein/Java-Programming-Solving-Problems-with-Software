package week2;

import edu.duke.StorageResource;
import edu.duke.URLResource;

/**
 * Created by white on 11/6/15.
 */
public class FindingAllLinks {

    public static void main(String[] args) {


        Iterable<String> words = readURL("http://www.dukelearntoprogram.com/course2/data/newyorktimes.html");

        StorageResource store = findLink(words);

        printUrlsInformation(store);


    }

    public static Iterable<String> readURL(String url) {

        URLResource ur = new URLResource(url);

        return ur.words();

    }

    public static StorageResource findLink(Iterable<String> words) {

        StorageResource sr = new StorageResource();

        String searchWord = "href=";


        for (String s : words) {
            s = s.toLowerCase().trim().replace(" ", "");

            int pos = s.indexOf(searchWord);

            if (pos != -1) {
                int beg = s.indexOf("\"", pos);
                int end = s.indexOf("\"", beg + 1);

                if (end == -1) {
                    end = s.length();
                }

                sr.add(s.substring(beg + 1, end));

            }
        }

        return sr;

    }

    public static void printUrlsInformation(StorageResource store) {
        Iterable<String> data = store.data();
        System.out.println("URLS count:\t" + store.size());
        System.out.println("Dots count:\t" + countDots(data));
        System.out.println("Https count:\t" + countSecure(data));
        System.out.println("Coms count:\t" + countCOMs(data));
        System.out.println("End with Coms count:\t" + countEndCOMs(data));

    }

    public static int countDots(Iterable<String> store) {

        int dots = 0;

        for (String s : store) {
            dots += s.length() - s.replace(".", "").length();
        }

        return dots;
    }

    public static int countSecure(Iterable<String> store) {

        int https = 0;

        for (String s : store) {
            if (s.startsWith("https")) {
                https++;
            }
        }

        return https;
    }

    public static int countCOMs(Iterable<String> store) {
        int coms = 0;

        for (String s : store) {
            if (s.contains(".com")) {
                coms++;
            }
        }

        return coms;
    }

    public static int countEndCOMs(Iterable<String> store) {
        int coms = 0;

        for (String s : store) {
            if (s.endsWith(".com") || s.endsWith(".com/")) {
                coms++;
            }
        }

        return coms;
    }


}

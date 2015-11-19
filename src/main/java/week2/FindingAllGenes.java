package week2;

import edu.duke.FileResource;
import edu.duke.StorageResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Week 2.
 */
public class FindingAllGenes {

    public static void main(String[] args) {

        FileResource fr = new FileResource("../data/GRch38dnapart.fa");

        StorageResource sore = FindingAllGenes.findProtein(fr.asString());
        FindingAllGenes.printGensInformation(sore);
        FindingAllGenes.countCTGCodon(fr.asString());
    }

    /**
     * prints all the Strings that are longer than 60 characters
     * prints the number of Strings that are longer than 60 characters
     * prints the Strings whose C-G-ratio is higher than 0.35
     * prints the number of strings whose C-G-ratio is higher than 0.35
     *
     * @param sr
     */
    public static void printGensInformation(StorageResource sr) {

        int genCount = 0;

        for (String word : sr.data()) {

            if (word.length() > 60) {
//                System.out.println("Gene: " + word);
                genCount++;
            }
        }

        System.out.println("Gene 60 count: " + genCount);

        genCount = 0;

        for (String word : sr.data()) {

            if (cgRatio(word) > 0.35) {
//                System.out.println("C-G Gen: " + word);
                genCount++;
            }
        }

        System.out.println("C-G Count: " + genCount);

        System.out.println("Longest gene is: " + getLongestGene(sr).length());

    }

    /**
     * Find all proteins in a gene
     *
     * @param dna
     */
    public static StorageResource findProtein(String dna) {

        dna = dna.toLowerCase();

        //find start position of the codon

        int endPos = 0;

        StorageResource store = new StorageResource();

        while (true) {
            // get start position of a codom
            int start = dna.indexOf("atg", endPos);

            if (start == -1) {
                break;
            }

            //find end position of a codon
            int end = findEndPosition(dna, start + 3);

            if (end == -1) {
                endPos = start + 3;
                continue;
            }
            //
            endPos = end + 3;
            store.add(dna.substring(start, endPos));

        }

        System.out.println("Total proteins: " + store.size());

        return store;

    }

    /**
     * Find end position of  the certain protein
     *
     * @param dna
     * @param startPos
     * @return
     */
    public static int findEndPosition(String dna, int startPos) {

        String[] endTags = {"tag", "tga", "taa"};

        List<Integer> endsPoints = new ArrayList<>();
        for (String s : endTags) {

            int index = dna.indexOf(s, startPos);

            int diff = (index - startPos) % 3;

            if (index != -1 && diff == 0) {
                endsPoints.add(index);
            }
        }

        if (endsPoints.isEmpty()) {
            return -1;
        }
        return Collections.min(endsPoints);
    }

    /**
     * Compute C-G ratio for the certain protein
     *
     * @param dna
     * @return
     */
    public static float cgRatio(String dna) {

        dna = dna.toLowerCase();

        int dnaLen = dna.length();
        int gCount = countLetterInWord('g', dna);
        int cCount = countLetterInWord('c', dna);
        return (float) (gCount + cCount) / dnaLen;

    }

    /**
     * Count number of a letter in a word
     *
     * @param letter
     * @param word
     * @return
     */
    public static int countLetterInWord(char letter, String word) {

        int counter = 0;

        for (int i = 0; i < word.length(); i++) {

            if (word.charAt(i) == letter) {
                counter++;
            }

        }

        return counter;

    }

    /**
     * Find the longest gene in a collection of genes
     *
     * @param sr is a StorageResource of genes
     * @return
     */
    public static String getLongestGene(StorageResource sr) {

        int maxLen = 0;
        String longestGene = "";

        for (String gene : sr.data()) {

            int len = gene.length();

            if (len > maxLen) {
                maxLen = len;
                longestGene = gene;
            }

        }


        return longestGene;
    }

    /**
     * Count the codon CTG in a strand of DNA
     *
     * @param dna is a strand of DNA
     * @return count of CTG
     */
    public static int countCTGCodon(String dna) {
        dna = dna.toLowerCase();
        int count = dna.length() - dna.replace("ctg", "").length();
        System.out.println("CTG Count: " + count / 3);
        return count;
    }
}

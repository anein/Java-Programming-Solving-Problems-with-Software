package week2;

public class FindingGene {

    public static void main(String[] args) throws Exception {

        String testDNA = args[0];

        String codon = findProtein(testDNA);

        String stopPos = stopCodon(codon);

        System.out.format("Protein %s \n", codon);
        System.out.format("End Tag %s \n", stopPos);

    }

    /**
     * @param dna
     */
    public static String findProtein(String dna) {

        dna = dna.toLowerCase();

        //find start position of the codon
        int start = dna.indexOf("atg");

        if (start == -1) {
            System.out.println("Cannot find codon");
            return null;
        }
        //find end position of the codon
        int end = findEndPosition(dna, start);

        if (end == -1) {
            return null;
        }

        System.out.format("End Position: %s \n", end);

        return dna.substring(start, end + 3);
    }

    public static int findEndPosition(String dna, int startPos) {

        String[] endTags = {"tag", "tga", "taa"};

        int endPos = -1;

        for (String s : endTags) {

            int i = dna.indexOf(s, startPos + 3);
            int diff = (startPos - i) % 3;

            if (i != -1 && diff == 0) {
                endPos = i;
                break;
            }
        }

        return endPos;
    }

    public static String stopCodon(String codon) {

        if (codon == null) {
            return "";
        }

        int size = codon.length();

        if (size > 10) {
            return codon.substring(size - 3, size);
        } else {
            return "";
        }
    }
}
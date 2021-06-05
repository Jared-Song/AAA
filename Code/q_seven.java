import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class q_seven {

    public q_seven() {
        List<Strand> strandlist = new ArrayList<Strand>();
        populate(strandlist); // populate the list with the dataset

        List<Strand> sorted = new ArrayList<Strand>(); // list of strands sorted by edit distance from beefalo
        List<Strand> added = new ArrayList<Strand>(); // list of strands sorted by edit distance from beefalo

        Strand ancestor = new Strand(strandlist.get(0).getSpecies(), strandlist.get(0).getDNA()); // sets ancestor as
                                                                                                  // the beefalo

        int k = 0;
        while (sorted.size() != strandlist.size()) {
            for (int i = 0; i < strandlist.size(); i++) {
                if (k == minDistance(ancestor.getDNA(), strandlist.get(i).getDNA())) {
                    Strand strand = strandlist.get(i);
                    strand.setRootED(k);
                    sorted.add(strand); // a very basic sorting method that isn't very efficient but was written only
                                        // for this question (this should be a mergesort)
                }
            }
            k++;
        }

        k = 0;     
        while (added.size() < strandlist.size()) {
            for (Iterator<Strand> iter = sorted.iterator(); iter.hasNext();) {
                Strand strand = iter.next();
                if (k == minDistance(ancestor.getDNA(), strand.getDNA())) {
                    addTree(strand, added);
                    added.add(strand);
                    iter.remove();
                }
            }
            k++;
        }
        for (Strand strand : added) {
            if (strand.getParent() == null) {
                System.out.println(strand.getSpecies() + " (Root Ancestor)");
            }
            if (strand.getParent() != null) {
                System.out.println(strand.getSpecies() + " (from " + strand.getParent().getSpecies() + ")");
            }
            List<Strand> children = strand.getChildren();
            if (children.size() == 0) {
                System.out.println(" -- No Children");
            }
            for (Strand child : children) {
                System.out.println(" --" + child.getSpecies());
            }
            System.out.println();
        }

    }

    public void addTree(Strand strand, List<Strand> added) {
        int maxED = strand.getRootED();
        if (added.size() != 0) {
            int minED = strand.getRootED(); // set initial parent strand as root
            Strand parentStrand = added.get(0);
            for (Strand s : added) {
                int ed = minDistance(strand.getDNA(), s.getDNA());
                if (ed <= maxED && maxED >= s.getRootED()) { // satisfies Rules 1 and 2
                    if (minED > ed) { // find the parent with the lowest edit distance
                        minED = ed;
                        parentStrand = s;
                    }
                }
            }
            strand.setParent(parentStrand);
        }
    }

    public void populate(List<Strand> list) {
        list.add(new Strand("Beefalo", "AACTHJJTGG"));
        list.add(new Strand("Bearger", "TTHJJGTAT"));
        list.add(new Strand("Bunnyman", "AACTHJJGG"));
        list.add(new Strand("Buzzard", "CAAHGHTG"));
        list.add(new Strand("Catcoon", "AHCTHHJJTGG"));
        list.add(new Strand("Deerclops", "HJCTHJJGT"));
        list.add(new Strand("Dragonfly", "AHCTHHJGJTGGC"));
        list.add(new Strand("Elder Mandrake", "CATHJHTJGT"));
        list.add(new Strand("Eyeplant", "CTHJJGT"));
        list.add(new Strand("Grass Gekko", "CTJHJT"));
        list.add(new Strand("Hound", "JJHHJJTGH"));
        list.add(new Strand("Lavae", "AHHJGJTGGC"));
        list.add(new Strand("MacTusk", "HCTHJJTGGJH"));
        list.add(new Strand("Merm", "JHTHHJJTGA"));
        list.add(new Strand("Moleworm", "AACTHJJ"));
        list.add(new Strand("Pengull", "AHCTHJJTGG"));
        list.add(new Strand("Tallbird", "CJHHJJHGA"));
        list.add(new Strand("Tentacle", "JJHHGH"));
        list.add(new Strand("Terrorbeak", "AHATHHJJTAC"));
        list.add(new Strand("Volt Goat", "AHCTHJJTG"));
    }

    public int minDistance(String word1, String word2) { // Credit to:
                                                         // https://www.programcreek.com/2013/12/edit-distance-in-java/
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                // if last two chars equal
                if (c1 == c2) {
                    // update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }

    public static void main(String[] args) {
        new q_seven();
    }

}
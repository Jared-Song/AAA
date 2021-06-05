import java.util.ArrayList;
import java.util.List;

public class Strand {
    private String species;
    private String dna;
    private List<Strand> children;
    private Strand parent;
    private int rootED; // edit distance from root

    public Strand(String species, String dna) {
        this.species = species;
        this.dna = dna;
        this.parent = null;
        this.rootED = 0;
        children = new ArrayList<Strand>();
    }

    public String getSpecies(){
        return species;
    }

    public String getDNA() {
        return dna;
    }

    public void addChildren(Strand strand) {
        children.add(strand);
    }

    public void setParent(Strand strand) {
        this.parent = strand;
        strand.addChildren(this);
    }

    public Strand getParent() {
        return parent;
    }

    public boolean hasChild() {
        return children.size() != 0;
    }

    public List<Strand> getChildren() {
        return children;
    }

    public void setRootED(int rootED) {
        this.rootED = rootED;
    }

    public int getRootED() {
        return rootED;
    }
}

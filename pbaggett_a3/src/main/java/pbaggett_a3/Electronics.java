package eStoreSearch;

public class Electronics extends Product {

    // variable for electronic class only
    private String maker;

    // setter
    /**
     * 
     * @param id          gets the id from main for electronics
     * @param description gets the description from main for electronics
     * @param price       gets the price from main for electronics
     * @param year        gets the year from main for electronics
     * @param m           gets the maker from main for electronics
     */
    public Electronics(String id, String description, String price, String year, String m) {
        super(id, description, price, year);
        maker = m;
    }

    /**
     * 
     * @return the maker of the electronic
     */
    public String getM() {
        return maker;
    }

    // print out the electronic
    @Override
    public String toString() {
        return ("Type = Electronic\n" + super.toString() + "Maker = " + maker + "\n");
    }

    // print out the electronic to the output file
    @Override
    public String printOutput() {
        return ("Type = \"electronic\"\n" + super.printOutput() + "Maker = \"" + maker + "\"\n");
    }
}
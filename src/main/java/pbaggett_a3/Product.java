package eStoreSearch;

public class Product {

    // variables to use in the product method
    private String ID;
    private String desc;
    private String price;
    private String year;

    /**
     * 
     * @param id gets the id from the main
     * @param d  gets the description from the main
     * @param p  gets the price from the main
     * @param y  gets the year from the main
     */
    public Product(String id, String d, String p, String y) {
        ID = id;
        desc = d;
        price = p;
        year = y;

    }

    /**
     * 
     * @return return the id
     */
    public String getID() {
        return ID;
    }

    /**
     * 
     * @return return the description
     */
    public String getD() {
        return desc;
    }

    /**
     * 
     * @return return the price
     */
    public String getP() {
        return price;
    }

    /**
     * 
     * @return return the year
     */
    public String getY() {
        return year;
    }

    /**
     * 
     * @return printing out the 4 main components of a product
     */
    public String toString() {
        return ("ProductID = " + ID + "\nDescription = " + desc + "\nPrice = " + price + "\nYear = " + year + "\n");
    }

    /**
     * 
     * @return prints to the output file
     */
    public String printOutput() {
        return ("ProdictID = \"" + ID + "\"\nDescription = \"" + desc + "\"\nPrice = \"" + price + "\"\nYear = \""
                + year + "\"\n");
    }
}
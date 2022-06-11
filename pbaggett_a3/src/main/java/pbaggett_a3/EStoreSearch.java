package eStoreSearch;

import java.util.*;
import java.io.*;
import java.lang.NumberFormatException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EStoreSearch extends JFrame {

    /**
     * Global variables to be used throughout the program
     */
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int LINES = 10;
    public static final int MAX_LINE = 50;

    JPanel mainPanel;
    JPanel addPanel;
    JPanel buttonPanel;
    JPanel messagePanel;
    JPanel searchPanel;
    JPanel buttonPanel2;
    JPanel messagePanel2;

    JLabel authorText;
    JLabel publisherText;
    JLabel makerText;

    JTextArea text;
    JTextArea productID;
    JTextArea desc;
    JTextArea price;
    JTextArea year;
    JTextArea author;
    JTextArea publisher;
    JTextArea maker;
    JTextArea message;

    JScrollPane aScroll;
    JScrollPane publisherScroll;
    JScrollPane mScroll;

    JTextArea searchID;
    JTextArea searchDesc;
    JTextArea searchStart;
    JTextArea searchEnd;
    private static JTextArea searchMessage;

    JComboBox typeList;

    public static String ProductID;
    public static String Description;
    public static String Price;
    public static String Year;
    public static String Maker;
    public static String Author;
    public static String Publisher;

    public static String messageTemp;
    public static String searchTemp;

    public static String SearchID;
    public static String SearchDescription;
    public static String SearchStart;
    public static String SearchEnd;

    public static String fileName = "";

    private static ArrayList<Product> list = new ArrayList<>();
    private static HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    static Scanner scan = new Scanner(System.in);

    /**
     * hash table function to put the search description into the hashtable
     */
    public static void hash() {

        for (int j = 0; j < list.size(); j++) {

            String words = list.get(j).getD();
            String temp[] = words.split("\\W+");

            for (int k = 0; k < temp.length; k++) {

                temp[k] = temp[k].toLowerCase();
                if (map.containsKey(temp[k])) {
                    ArrayList<Integer> temp2 = map.get(temp[k]);
                    temp2.add(j);
                    map.put(temp[k], temp2);
                } else {
                    ArrayList<Integer> newAdd = new ArrayList<Integer>();
                    newAdd.add(j);
                    map.put(temp[k], newAdd);
                }
            }
        }
    }

    /**
     * searches the hashtable and search description for where it occurs
     * 
     * @param searchD
     * @return integer array
     */
    public static int[] descSearch(String searchD) {
        String words[] = searchD.split("[ ]+");
        ArrayList<String> temp = new ArrayList<String>();
        ArrayList<Integer[]> tempValue = new ArrayList<Integer[]>();

        for (int u = 0; u < words.length; u++) {
            if (map.containsKey(words[u])) {
                temp.add(words[u]);
                ArrayList<Integer> temp2 = map.get(words[u]);
                Integer temp3[] = temp2.toArray(new Integer[0]);
                tempValue.add(temp3);
            }
        }
        if (temp.size() == 0) {
            int array[] = {};
            return (array);
        }
        HashSet<Integer> intersection = new HashSet<>(Arrays.asList(tempValue.get(0)));

        for (int u = 1; u < tempValue.size(); u++) {
            HashSet<Integer> set = new HashSet<>(Arrays.asList(tempValue.get(u)));
            intersection.retainAll(set);
        }

        Object[] something = intersection.toArray();
        int intArray[] = new int[something.length];

        for (int u = 0; u < something.length; u++) {
            intArray[u] = (int) (something[u]);
        }
        return intArray;
    }

    /**
     * 
     * searching function
     * 
     * @param searchI         search ID
     * @param searchD         search description
     * @param searchStartYear search start year
     * @param searchEndYear   search end year
     * @param emptyID         empty ID value
     * @param emptyD          empty description value
     * @param emptySY         empty start year value
     * @param emptyEY         empty end year value
     */
    public static void search(String searchI, String searchD, String searchStartYear, String searchEndYear, int emptyID,
            int emptyD, int emptySY, int emptyEY) {

        int i = 0;
        String years[] = new String[2];
        int emptyY = 1;
        int before = 0;
        int after = 0;
        int split = 1;
        if (emptySY == 0 && emptyEY == 0) {
            years[0] = searchStartYear;
            years[1] = searchEndYear;
            emptyY = 0;
        } else if (emptySY == 0 && emptyEY == 1) {
            years[0] = searchStartYear;
            years[1] = Integer.toString(9999);
            emptyY = 0;
            after = 1;
        } else if (emptySY == 1 && emptyEY == 0) {
            years[0] = Integer.toString(1000);
            years[1] = searchEndYear;
            emptyY = 0;
            before = 1;
        } else if (emptySY == 1 && emptyEY == 1) {
            years[0] = Integer.toString(1000);
            years[1] = Integer.toString(9999);
            emptyY = 1;
        }

        String inList;
        String inList1;
        String inList2;
        searchD = searchD.toLowerCase();
        int found = 0;

        int intArray[] = {};
        if (emptyD == 0) {
            hash();
            intArray = descSearch(searchD);
        }

        if (emptyID == 0 && emptyD == 1 && emptyY == 1) {
            // ID
            for (i = 0; i < list.size(); i++) {
                inList = list.get(i).getID();
                if (inList.contains(searchI)) {
                    print(i);
                    found = 1;
                }
            }
        } else if (emptyID == 1 && emptyD == 0 && emptyY == 1) {
            // just desc
            for (i = 0; i < intArray.length; i++) {
                print(intArray[i]);
                found = 1;
            }
        } else if (emptyID == 1 && emptyD == 1 && emptyY == 0) {
            // just year(s)
            for (i = 0; i < list.size(); i++) {
                inList = list.get(i).getY();
                if (split == 1) {
                    // searches before the year
                    if (before == 1) {
                        if (Integer.parseInt(inList) <= Integer.parseInt(years[1])) {
                            print(i);
                            found = 1;
                        }
                    } else if (after == 1) {
                        if (Integer.parseInt(inList) >= Integer.parseInt(years[0])) {
                            print(i);
                            found = 1;
                        }
                    } else if (Integer.parseInt(inList) >= Integer.parseInt(years[0])
                            && Integer.parseInt(inList) <= Integer.parseInt(years[1]) && before == 0 && after == 0) {
                        print(i);
                        found = 1;
                    }
                }
            }
        } else if (emptyID == 0 && emptyD == 0 && emptyY == 1) {
            // id and desc
            for (i = 0; i < list.size(); i++) {
                inList = list.get(i).getID();
                if (inList.contains(searchI)) {
                    if (intArray.length != 0) {
                        print(i);
                        found = 1;
                    }
                }
            }
        } else if (emptyID == 0 && emptyD == 1 && emptyY == 0) {
            // id and year(s)
            for (i = 0; i < list.size(); i++) {
                inList1 = list.get(i).getID();
                inList = list.get(i).getY();
                if (split == 1) {
                    // searches before the year and ID
                    if (before == 1) {
                        if (Integer.parseInt(inList) <= Integer.parseInt(years[1]) && inList1.contains(searchI)) {
                            print(i);
                            found = 1;
                        }
                    } else if (after == 1) {
                        if (Integer.parseInt(inList) >= Integer.parseInt(years[0]) && inList1.contains(searchI)) {
                            print(i);
                            found = 1;
                        }
                    } else if (Integer.parseInt(inList) >= Integer.parseInt(years[0])
                            && Integer.parseInt(inList) <= Integer.parseInt(years[1]) && before == 0 && after == 0
                            && inList1.contains(searchI)) {
                        print(i);
                        found = 1;
                    }
                }
            }
        } else if (emptyID == 1 && emptyD == 0 && emptyY == 0) {
            // desc and year(s)
            for (i = 0; i < list.size(); i++) {
                inList = list.get(i).getY();
                if (split == 1) {
                    // searches before the year and ID
                    if (before == 1) {
                        if (Integer.parseInt(inList) <= Integer.parseInt(years[1])) {
                            if (intArray.length != 0) {
                                print(i);
                                found = 1;
                            }
                        }
                    } else if (after == 1) {
                        if (Integer.parseInt(inList) >= Integer.parseInt(years[0])) {
                            if (intArray.length != 0) {
                                print(i);
                                found = 1;
                            }
                        }
                    } else if (Integer.parseInt(inList) >= Integer.parseInt(years[0])
                            && Integer.parseInt(inList) <= Integer.parseInt(years[1]) && before == 0 && after == 0) {
                        if (intArray.length != 0) {
                            print(i);
                            found = 1;
                        }
                    }
                }
            }
        } else if (emptyID == 0 && emptyD == 0 && emptyY == 0) {
            for (i = 0; i < list.size(); i++) {
                inList2 = list.get(i).getID();
                inList = list.get(i).getY();
                if (split == 1) {
                    // searches before the year, ID and description
                    if (before == 1) {
                        if (Integer.parseInt(inList) <= Integer.parseInt(years[1]) && inList2.contains(searchI)) {
                            if (intArray.length != 0) {
                                print(i);
                                found = 1;
                            }
                        }
                    } else if (after == 1) {
                        if (Integer.parseInt(inList) >= Integer.parseInt(years[0]) && inList2.contains(searchI)) {
                            if (intArray.length != 0) {
                                print(i);
                                found = 1;
                            }
                        }
                    } else if (Integer.parseInt(inList) >= Integer.parseInt(years[0])
                            && Integer.parseInt(inList) <= Integer.parseInt(years[1]) && before == 0 && after == 0
                            && inList2.contains(searchI)) {
                        if (intArray.length != 0) {
                            print(i);
                            found = 1;
                        }
                    }
                }
            }
        } else if (emptyID == 1 && emptyD == 1 && emptyY == 1) {
            // print all
            for (i = 0; i < list.size(); i++) {
                print(i);
                found = 1;
            }
        }
        if (found == 0) {
            searchMessage.append("No products match the search");
        }
    }

    /**
     * Adding GUI
     */
    public class AddingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (searchPanel != null) {
                if (searchPanel.isVisible() == true) {
                    searchPanel.setVisible(false);
                    buttonPanel2.setVisible(false);
                    messagePanel2.setVisible(false);
                }
            }
            if (mainPanel != null) {
                if (mainPanel.isVisible() == true) {
                    mainPanel.setVisible(false);
                }
            }
            if (addPanel != null) {
                if (addPanel.isVisible() == false) {
                    addPanel.setVisible(true);
                    buttonPanel.setVisible(true);
                    messagePanel.setVisible(true);
                }
            } else {

                addPanel = new JPanel();
                setSize(730, 690);
                addPanel.setBackground(Color.LIGHT_GRAY);
                addPanel.setLayout(null);
                addPanel.setBounds(0, 0, 530, 420);

                JLabel topText = new JLabel("Adding a Product");
                topText.setBounds(10, 10, 150, 20);
                addPanel.add(topText);

                // ---------left panel with information---------//

                // ---------------type of product------------//
                JLabel typeText = new JLabel("Type:");
                typeText.setBounds(10, 60, 100, 30);
                addPanel.add(typeText);

                String type[] = { "Books", "Electronics" };
                typeList = new JComboBox(type);
                typeList.addActionListener(new TypeListener());
                typeList.setBounds(110, 60, 150, 30);
                addPanel.add(typeList);

                // ---------------product ID------------//
                JLabel productText = new JLabel("ProductID:");
                productText.setBounds(10, 110, 100, 30);
                addPanel.add(productText);

                productID = new JTextArea(1, 10);
                JScrollPane productScroll = new JScrollPane(productID);
                productScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                productScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                productScroll.setBounds(110, 110, 100, 30);
                addPanel.add(productScroll);

                // ---------------description------------//
                JLabel descText = new JLabel("Description:");
                descText.setBounds(10, 160, 100, 30);
                addPanel.add(descText);

                desc = new JTextArea(1, 10);
                JScrollPane dScroll = new JScrollPane(desc);
                dScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                dScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                dScroll.setBounds(110, 160, 400, 30);
                addPanel.add(dScroll);

                // ---------------price------------//
                JLabel priceText = new JLabel("Price:");
                priceText.setBounds(10, 210, 100, 30);
                addPanel.add(priceText);

                price = new JTextArea(1, 10);
                JScrollPane priceScroll = new JScrollPane(price);
                priceScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                priceScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                priceScroll.setBounds(110, 210, 90, 30);
                addPanel.add(priceScroll);

                // ---------------year------------//
                JLabel yearText = new JLabel("Year:");
                yearText.setBounds(10, 260, 100, 30);
                addPanel.add(yearText);

                year = new JTextArea(2, 10);
                JScrollPane yScroll = new JScrollPane(year);
                yScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                yScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                yScroll.setBounds(110, 260, 90, 30);
                addPanel.add(yScroll);

                // ---------------author------------//
                authorText = new JLabel("Authors:");
                authorText.setBounds(10, 310, 100, 30);
                addPanel.add(authorText);

                author = new JTextArea(1, 10);
                aScroll = new JScrollPane(author);
                aScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                aScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                aScroll.setBounds(110, 310, 200, 30);
                addPanel.add(aScroll);

                // ---------------publisher------------//
                publisherText = new JLabel("Publishers:");
                publisherText.setBounds(10, 360, 100, 30);
                addPanel.add(publisherText);

                publisher = new JTextArea(1, 10);
                publisherScroll = new JScrollPane(publisher);
                publisherScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                publisherScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                publisherScroll.setBounds(110, 360, 200, 30);
                addPanel.add(publisherScroll);

                // ---------------maker------------//
                makerText = new JLabel("Makers:");
                makerText.setBounds(10, 310, 100, 30);
                addPanel.add(makerText);

                maker = new JTextArea(1, 10);
                mScroll = new JScrollPane(maker);
                mScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                mScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                mScroll.setBounds(110, 310, 200, 30);
                addPanel.add(mScroll);

                makerText.setVisible(false);
                maker.setVisible(false);

                // --------------------------right panel with buttons-------------------------//
                buttonPanel = new JPanel();
                buttonPanel.setLayout(null);
                buttonPanel.setBackground(Color.GRAY);
                buttonPanel.setBounds(530, 0, 200, 420);

                JButton reset = new JButton("Reset");
                reset.setBounds(60, 150, 80, 40);
                reset.addActionListener(new resetListener());
                buttonPanel.add(reset);

                JButton add = new JButton("Add");
                add.setBounds(60, 300, 80, 40);
                add.addActionListener(new addListener());
                buttonPanel.add(add);

                // ------------------------messages panel for errors--------------------------//
                messagePanel = new JPanel();
                messagePanel.setLayout(null);
                messagePanel.setBackground(Color.GRAY);
                messagePanel.setBounds(0, 420, 730, 230);

                JLabel errorMessage = new JLabel("Messages");
                errorMessage.setBounds(10, 0, 100, 20);
                messagePanel.add(errorMessage);

                message = new JTextArea(LINES, MAX_LINE);
                message.setEditable(false);
                JScrollPane messageScroll = new JScrollPane(message);
                messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                messageScroll.setBounds(10, 20, 700, 200);
                messagePanel.add(messageScroll);

                add(addPanel);
                add(buttonPanel);
                add(messagePanel);
            }
        }
    }

    /**
     * Detects what the type is
     */
    public class TypeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = (String) typeList.getSelectedItem();

            if (type.equals("Electronics")) {
                authorText.setVisible(false);
                author.setVisible(false);
                aScroll.setVisible(false);

                publisherText.setVisible(false);
                publisher.setVisible(false);
                publisherScroll.setVisible(false);

                makerText.setVisible(true);
                maker.setVisible(true);
                mScroll.setVisible(true);

            } else if (type.equals("Books")) {
                authorText.setVisible(true);
                author.setVisible(true);
                aScroll.setVisible(true);

                publisherText.setVisible(true);
                publisher.setVisible(true);
                publisherScroll.setVisible(true);

                makerText.setVisible(false);
                maker.setVisible(false);
                mScroll.setVisible(false);

            }
        }
    }

    /**
     * Resests the blanks for the Adding GUI
     */
    public class resetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            productID.setText("");
            desc.setText("");
            price.setText("");
            year.setText("");
            author.setText("");
            publisher.setText("");
            maker.setText("");
            message.setText("");
        }
    }

    /**
     * Puts the text into a variable
     */
    public class addListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            message.setText("");
            String type = (String) typeList.getSelectedItem();
            int pr = 1;
            int de = 1;
            int p = 1;
            int y = 1;
            int a = 1;
            int pu = 1;
            int m = 1;
            int i = 0;

            // gets the text from the text boxes
            ProductID = productID.getText();
            Description = desc.getText();
            Price = price.getText();
            Year = year.getText();
            Author = author.getText();
            Publisher = publisher.getText();
            Maker = maker.getText();

            // checks if the product ID is valid
            try {
                int num1 = Integer.parseInt(ProductID);
                if (ProductID.length() != 6)
                    throw new Exception();
            } catch (NumberFormatException ex) {
                message.append("Enter a valid product ID that is numbers only\n");
                productID.setText("");
                pr = 0;
            } catch (Exception ex) {
                message.append("Enter a product ID that is six(6) digits in length\n");
                productID.setText("");
                pr = 0;
            }
            try {
                if (ProductID.isEmpty())
                    throw new Exception();
            } catch (Exception ex) {
                message.append("A product ID must be entered\n");
                productID.setText("");
                pr = 0;
            }
            i = 0;
            while (i < list.size()) {
                String temp1 = list.get(i).getID();
                try {
                    if (ProductID.equals(temp1))
                        throw new Exception();
                } catch (Exception ex) {
                    message.append("This product ID already exists in the store\n");
                    productID.setText("");
                    pr = 0;
                }
                i++;
            }

            // checks for a valid description
            try {
                if (Description.isEmpty())
                    throw new Exception();
            } catch (Exception ex) {
                message.append("A product description must be entered\n");
                desc.setText("");
                de = 0;
            }

            // checks for a valid price if entered
            if (Price.isEmpty()) {
                Price = Double.toString(0.0);
                p = 1;
            }
            try {
                double num2 = Double.parseDouble(Price);
            } catch (NumberFormatException ex) {
                message.append("Enter a price that is a number\n");
                price.setText("");
                p = 0;
            }

            // checks for a valid year
            try {
                int num3 = Integer.parseInt(Year);
                if (Year.isEmpty())
                    throw new Exception();
                if (Year.length() != 4 || Year.isEmpty() || Integer.parseInt(Year) < 1000
                        || Integer.parseInt(Year) > 9999)
                    throw new Exception();
            } catch (NumberFormatException ex) {
                message.append("Enter a valid year that is a number\n");
                year.setText("");
                y = 0;
            } catch (Exception ex) {
                message.append("Enter a valid year that is between 1000 and 9999\n");
                year.setText("");
                y = 0;
            }

            // add to the list if all valid
            if (pr == 1 && de == 1 && p == 1 && y == 1) {
                if (type.equals("Electronics")) {
                    list.add(new Electronics(ProductID, Description, Price, Year, Maker));
                    message.setText("");
                    message.setText("Electronic Added");
                } else if (type.equals("Books")) {
                    list.add(new Books(ProductID, Description, Price, Year, Author, Publisher));
                    message.setText("");
                    message.setText("Book Added\n");
                }
            }
        }
    }

    /**
     * Searching GUI
     */
    public class SearchingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (addPanel != null) {
                if (addPanel.isVisible() == true) {
                    addPanel.setVisible(false);
                    buttonPanel.setVisible(false);
                    messagePanel.setVisible(false);
                }
            }
            if (mainPanel != null) {
                if (mainPanel.isVisible() == true) {
                    mainPanel.setVisible(false);
                }
            }
            if (searchPanel != null) {
                if (searchPanel.isVisible() == false) {
                    searchPanel.setVisible(true);
                    buttonPanel2.setVisible(true);
                    messagePanel2.setVisible(true);
                }
            } else {

                searchPanel = new JPanel();
                setSize(730, 690);
                searchPanel.setBackground(Color.LIGHT_GRAY);
                searchPanel.setLayout(null);
                searchPanel.setBounds(0, 0, 530, 380);

                JLabel topText = new JLabel("Searching Product");
                topText.setBounds(10, 10, 150, 20);
                searchPanel.add(topText);

                // ------------left panel with information------------//

                // ------------------product ID--------------//
                JLabel searchIDtext = new JLabel("ProductID:");
                searchIDtext.setBounds(10, 50, 100, 30);
                searchPanel.add(searchIDtext);

                searchID = new JTextArea(1, 10);
                JScrollPane searchIDscroll = new JScrollPane(searchID);
                searchIDscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                searchIDscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                searchIDscroll.setBounds(110, 50, 100, 30);
                searchPanel.add(searchIDscroll);

                // ------------------description keywords--------------//
                JLabel searchDesctext = new JLabel("<html>Description<br/>Keywords:</html>");
                searchDesctext.setBounds(10, 100, 100, 40);
                searchPanel.add(searchDesctext);

                searchDesc = new JTextArea(1, 10);
                JScrollPane searchDescScroll = new JScrollPane(searchDesc);
                searchDescScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                searchDescScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                searchDescScroll.setBounds(110, 100, 400, 30);
                searchPanel.add(searchDescScroll);

                // ------------------start year--------------//
                JLabel searchStarttext = new JLabel("Start Year:");
                searchStarttext.setBounds(10, 150, 100, 20);
                searchPanel.add(searchStarttext);

                searchStart = new JTextArea(1, 10);
                JScrollPane searchStartscroll = new JScrollPane(searchStart);
                searchStartscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                searchStartscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                searchStartscroll.setBounds(110, 150, 90, 30);
                searchPanel.add(searchStartscroll);

                // ------------------end year--------------//
                JLabel searchEndtext = new JLabel("End Year:");
                searchEndtext.setBounds(10, 200, 100, 25);
                searchPanel.add(searchEndtext);

                searchEnd = new JTextArea(1, 10);
                JScrollPane searchEndscroll = new JScrollPane(searchEnd);
                searchEndscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                searchEndscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                searchEndscroll.setBounds(110, 200, 90, 30);
                searchPanel.add(searchEndscroll);

                // --------------right panel with buttons---------//

                buttonPanel2 = new JPanel();
                buttonPanel2.setLayout(null);
                buttonPanel2.setBackground(Color.GRAY);
                buttonPanel2.setBounds(530, 0, 200, 380);

                JButton reset = new JButton("Reset");
                reset.setBounds(60, 100, 80, 40);
                reset.addActionListener(new reset2Listener());
                buttonPanel2.add(reset);

                JButton search = new JButton("Search");
                search.setBounds(60, 250, 80, 40);
                search.addActionListener(new searchListener());
                buttonPanel2.add(search);

                // --------------search results---------------//
                messagePanel2 = new JPanel();
                messagePanel2.setLayout(null);
                messagePanel2.setBackground(Color.GRAY);
                messagePanel2.setBounds(0, 380, 730, 270);

                JLabel searchResult = new JLabel("Search Results");
                searchResult.setBounds(10, 0, 100, 20);
                messagePanel2.add(searchResult);

                searchMessage = new JTextArea(LINES, MAX_LINE);
                searchMessage.setEditable(false);
                JScrollPane searchMessageScroll = new JScrollPane(searchMessage);
                searchMessageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                searchMessageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                searchMessageScroll.setBounds(10, 20, 700, 230);
                messagePanel2.add(searchMessageScroll);

                add(searchPanel);
                add(buttonPanel2);
                add(messagePanel2);
            }
        }
    }

    // -----------Resets the blanks in the Search GUI-------------//
    public class reset2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchID.setText("");
            searchDesc.setText("");
            searchStart.setText("");
            searchEnd.setText("");
            searchMessage.setText("");
        }
    }

    // ----------------Stores the search results into variables----------------//
    public class searchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchMessage.setText("");
            /**
             * get the text from the text boxes
             */
            SearchID = searchID.getText();
            SearchDescription = searchDesc.getText();
            SearchStart = searchStart.getText();
            SearchEnd = searchEnd.getText();

            int emptyID = 0;
            int emptyD = 0;
            int emptySY = 0;
            int emptyEY = 0;
            int valid = 1;

            try {
                if (SearchID.isEmpty()) {
                    emptyID = 1;
                    SearchID = "";
                } else {
                    int temp = Integer.parseInt(SearchID);
                    if (SearchID.length() != 6) {
                        throw new Exception();
                    }
                }
            } catch (NumberFormatException a) {
                searchMessage.append("Enter a valid product ID that has no letters\n");
                searchID.setText("");
                valid = 0;
            } catch (Exception a) {
                searchMessage.append("Enter a valid product ID that is six(6) digits\n");
                searchID.setText("");
                valid = 0;
            }

            if (SearchDescription.isEmpty()) {
                emptyD = 1;
                SearchDescription = "";
            }

            try {
                if (SearchStart.isEmpty()) {
                    emptySY = 1;
                    SearchStart = "";
                } else {
                    int temp = Integer.parseInt(SearchStart);
                    if (temp < 1000 || temp > 9999)
                        throw new Exception();
                }
            } catch (NumberFormatException a) {
                searchMessage.append("Enter a valid start year that is a number\n");
                searchStart.setText("");
                valid = 0;
            } catch (Exception a) {
                searchMessage.append("Enter a valid start year that is between the years 1000 and 9999\n");
                searchStart.setText("");
                valid = 0;
            }

            try {
                if (SearchEnd.isEmpty()) {
                    emptyEY = 1;
                    SearchEnd = "";
                } else {
                    int temp = Integer.parseInt(SearchEnd);
                    if (temp < 1000 || temp > 9999)
                        throw new Exception();
                }
            } catch (NumberFormatException a) {
                searchMessage.append("Enter a valid end year that is a number\n");
                searchEnd.setText("");
                valid = 0;
            } catch (Exception a) {
                searchMessage.append("Enter a valid end year that is between the years 1000 and 9999\n");
                searchEnd.setText("");
                valid = 0;
            }

            if (valid == 1 && list.size() == 0) {
                searchMessage.append("No Products added");
            } else if (valid == 1) {
                search(SearchID, SearchDescription, SearchStart, SearchEnd, emptyID, emptyD, emptySY, emptyEY);
            }
        }
    }

    // ----------Quits the program from the quit drop down men---------------//
    public class QuitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (fileName.isEmpty() == false) {
                outputFile(fileName);
            }
            System.exit(0);
        }

    }

    // -----------------Main menu---------------------//
    public EStoreSearch() {
        super("eStoreSearch");
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu commands = new JMenu("Commands");
        JMenuItem add = new JMenuItem("Add");
        add.addActionListener(new AddingListener());
        commands.add(add);

        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(new SearchingListener());
        commands.add(search);

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitListener());
        commands.add(quit);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(null);
        menuBar.add(commands);
        setJMenuBar(menuBar);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 600, 600);
        mainPanel.setBackground(Color.LIGHT_GRAY);

        text = new JTextArea();
        text.setText("          Welcome to the eStore where there are a selection of books, and electronics\n\n"
                + "                   Choose a choice from the 'Commands' drop down menu:\n\n"
                + "              Add: Add a Book or Electronic product to the store\n"
                + "              Search: Search for a product in the Books and/or Electronics section\n"
                + "              Quit: Quit the store");
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(false);
        text.setEditable(false);
        text.setBounds(10, 200, 900, 300);
        mainPanel.add(text);
        add(mainPanel);
    }

    /**
     * prints out the list
     * 
     * @param i index of the array
     */
    public static void print(int i) {
        searchMessage.append("------------------------\n");
        searchMessage.append(list.get(i).toString());
        searchMessage.append("------------------------\n");

        // System.out.println(list.get(i).toString());
    }

    public static void inputFile(String[] args) {

        if (args[0] != null) {
            System.out.println("FileName: " + args[0]);
            fileName = args[0];
            String array[] = new String[100000];
            int k = 0;
            Scanner inputFile = null;

            // if there is a file put in by the user
            try {
                inputFile = new Scanner(new FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("----------Error opening the input file----------");
                System.exit(0);
            }
            // put the file input to a array to be read later
            while (inputFile.hasNextLine()) {
                array[k] = inputFile.nextLine();
                k++;
            }
            inputFile.close();
            for (int l = 0; l < k; l++) {
                // if the file has type in the line
                if (array[l].contains("type") || array[l].contains("Type")) {
                    // if the file line has a b for book in it
                    if (array[l].charAt(8) == 'b' || array[l].charAt(8) == 'B') {

                        String id[] = array[l + 1].split("\"|=|\\s+ ");
                        String description[] = array[l + 2].split("\"|=|\\s+ ");
                        String price[] = array[l + 3].split("\"|=|\\s+ ");
                        String year[] = array[l + 4].split("\"|=|\\s+ ");
                        String author[] = array[l + 5].split("\"|=|\\s+ ");
                        String publisher[] = array[l + 6].split("\"|=|\\s+ ");

                        list.add(new Books(id[2], description[2], price[2], year[2], author[2], publisher[2]));
                        l += 6;
                        // if the file line has a e for electronic in it
                    } else if (array[l].charAt(8) == 'e' || array[l].charAt(8) == 'E') {
                        String id[] = array[l + 1].split("\"|=|\\s+ ");
                        String description[] = array[l + 2].split("\"|=|\\s+ ");
                        String price[] = array[l + 3].split("\"|=|\\s+ ");
                        String year[] = array[l + 4].split("\"|=|\\s+ ");
                        String maker[] = array[l + 5].split("\"|=|\\s+ ");

                        list.add(new Electronics(id[2], description[2], price[2], year[2], maker[2]));
                        l += 5;
                    }
                }
            }
        }
    }

    public static void outputFile(String file) {
        PrintWriter output = null;

        try {
            output = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println("-----ERROR -- Output File Could Not Be Opened");
            System.exit(0);
        }
        for (int j = 0; j < list.size(); j++) {
            output.print(list.get(j).printOutput());
        }
        output.close();
    }

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {

        if (args.length == 1) {
            inputFile(args);
        }

        EStoreSearch gui = new EStoreSearch();
        gui.setVisible(true);
    }
}

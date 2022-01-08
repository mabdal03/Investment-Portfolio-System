package ePortfolio;

//Importing libraries
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;  
import java.io.PrintWriter;  
import java.util.HashMap;

public class Portfolio extends JFrame implements ActionListener
{
    //Defining static ArrayLists for Investments, Investment Hashmaps, and for the Investment Search
    static ArrayList<Investment> investments  = new ArrayList<Investment>();
    static HashMap<String, ArrayList<Integer>> investmentMap = new HashMap<String, ArrayList<Integer>>();
    static ArrayList<Integer> investmentSearch  = new ArrayList<Integer>();

    static JTextArea outputArea = new JTextArea(8, 62);
    static JTextArea outputAreaSell = new JTextArea(8, 62);
    static JTextArea outputAreaUpdate = new JTextArea(8, 62);
    static JTextArea outputAreaGain = new JTextArea(15, 62);
    static JTextArea outputAreaSearch = new JTextArea(8, 62);
    static JTextField totalGainField = new JTextField(10);

    static JButton prevButton = new JButton("Prev");
    static JButton nextButton = new JButton("Next");

    static JTextField symbolUpdateField = new JTextField(10);
    static JTextField nameUpdateField = new JTextField(10);   
    static JTextField priceUpdateField = new JTextField(10);    
 
    static int currentUpdateVal = -1;
    static int commandLength;
    static String fileName;

    JPanel commandMenu = new JPanel();
    JPanel buyWindow = new JPanel();
    JPanel sellWindow = new JPanel();
    JPanel updateWindow = new JPanel();
    JPanel gainWindow = new JPanel();
    JPanel searchWindow = new JPanel();
    JPanel textPanel = new JPanel();
    JPanel textPanelSell = new JPanel();
    JPanel textPanelUpdate = new JPanel();
    JPanel textPanelGain = new JPanel();
    JPanel textPanelSearch = new JPanel();
    String[] investOptionsCombo = {"Stock","MutualFund"};
    JComboBox<String> chooseInvestCombo = new JComboBox<>(investOptionsCombo);

    //Defining a new colour
    public static final Color VERY_LIGHT_BLUE = new Color(51,204,255);

    //Defining the listener function to check button input and menu changes
    public void actionPerformed(ActionEvent e){
        String buttonString = e.getActionCommand();

        //Defualt commands menu
        if (buttonString.equals("Commands")){
            commandMenu.setVisible(true);
            buyWindow.setVisible(false);
            sellWindow.setVisible(false);
            updateWindow.setVisible(false);
            gainWindow.setVisible(false);
            searchWindow.setVisible(false);
        }

        //Buy menu chosen
        else if (buttonString.equals("Buy")){
            buyWindow.setVisible(true);
            commandMenu.setVisible(false);
            sellWindow.setVisible(false);
            updateWindow.setVisible(false);
            gainWindow.setVisible(false);
            searchWindow.setVisible(false);
        }

        //Sell menu chosen
        else if (buttonString.equals("Sell")){
            sellWindow.setVisible(true);
            buyWindow.setVisible(false);
            commandMenu.setVisible(false);
            updateWindow.setVisible(false);
            gainWindow.setVisible(false);
            searchWindow.setVisible(false);
        }

        //Gain menu chosen
        else if (buttonString.equals("Get Gain")){
            gainWindow.setVisible(true);
            sellWindow.setVisible(false);
            buyWindow.setVisible(false);
            commandMenu.setVisible(false);
            updateWindow.setVisible(false);
            searchWindow.setVisible(false);
        }

        //Update menu chosen
        else if (buttonString.equals("Update")){
            updateWindow.setVisible(true);
            sellWindow.setVisible(false);
            buyWindow.setVisible(false);
            commandMenu.setVisible(false);
            gainWindow.setVisible(false);
            searchWindow.setVisible(false);
        }

        else if (buttonString.equals("Search")){
            searchWindow.setVisible(true);
            updateWindow.setVisible(false);
            sellWindow.setVisible(false);
            buyWindow.setVisible(false);
            commandMenu.setVisible(false);
            gainWindow.setVisible(false);
        }

        //Quit menu chosen
        else if (buttonString.equals("Quit")){

            //Outputting file
            if (commandLength > 0){

                try{
                    PrintWriter fileWriter = new PrintWriter(fileName, "UTF-8");
                    for (int i = 0; i < investments.size(); i++){
                        fileWriter.println(investments.get(i).toPrint());
                    }
                    fileWriter.close();

                }catch (Exception f){
                    System.out.println("Failed to write.");
                }

            }

            System.exit(0);
        }
    }

    //Creting the standard menu
    public Portfolio()
    {
        //Defining the window layout
        super("Portfolio Management System");
        setSize(750,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        setLayout(new FlowLayout());

        //Adding investment options to the command menu
        JMenu investmentOptions = new JMenu("Commands");
        JMenuItem buyChoice = new JMenuItem("Buy");
        buyChoice.addActionListener(this);
        investmentOptions.add(buyChoice);

        JMenuItem sellChoice = new JMenuItem("Sell");
        sellChoice.addActionListener(this);
        investmentOptions.add(sellChoice);

        JMenuItem updateChoice = new JMenuItem("Update");
        updateChoice.addActionListener(this);
        investmentOptions.add(updateChoice);

        JMenuItem getGainChoice = new JMenuItem("Get Gain");
        getGainChoice.addActionListener(this);
        investmentOptions.add(getGainChoice);

        JMenuItem searchChoice = new JMenuItem("Search");
        searchChoice.addActionListener(this);
        investmentOptions.add(searchChoice);

        JMenuItem quitChoice = new JMenuItem("Quit");
        quitChoice.addActionListener(this);
        investmentOptions.add(quitChoice);

        //Creating the menu bar
        JMenuBar bar = new JMenuBar();
        bar.add(investmentOptions);
        setJMenuBar(bar);

        //Setting the layout of the default screen, command menu
        commandMenu.setLayout(new BorderLayout());
        commandMenu.setPreferredSize(new Dimension(750,400));

        //Creating the JLabel appearing on the default screen and adding the commandMenu
        JLabel menuText = new JLabel();
        menuText.setText("<html>Welcome to ePortfolio<br/><br/>Choose a command from the \"Commands\" menu to buy or sell<br/>an investment, update prices for all investments, get gain for the<br/>portfolio, search for relevant investments, or quit the program.<html>");
        menuText.setHorizontalAlignment(JLabel.CENTER);
        commandMenu.add(menuText, BorderLayout.CENTER);

        //Creating the menu for the Buy Window
        buyWindow.setLayout(new BorderLayout());
        buyWindow.setPreferredSize(new Dimension(700, 500));

        //Creating a panel for the upper portion of the menu
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(new BorderLayout());
        buyPanel.setPreferredSize(new Dimension(700, 300));

        //Creating a panel for options portion of the panel
        JPanel chooseBuyPanel = new JPanel();
        chooseBuyPanel.setLayout(new GridLayout(7, 2, 20, 20));

        //Adding text and text fields to the chooseBuyPanel for the Buy Window
        JLabel buyText = new JLabel();
        buyText.setText("Buying an Investment");
        chooseBuyPanel.add(buyText);
        JLabel blankText = new JLabel();
        blankText.setText("");
        chooseBuyPanel.add(blankText);

        JLabel typeText = new JLabel();
        typeText.setText("Type");
        chooseBuyPanel.add(typeText);
        chooseBuyPanel.add(chooseInvestCombo);

        JLabel SymbolText = new JLabel();
        SymbolText.setText("Symbol");
        chooseBuyPanel.add(SymbolText);
        JTextField symbolField = new JTextField(10);
        chooseBuyPanel.add(symbolField);

        JLabel nameText = new JLabel();
        nameText.setText("Name");
        chooseBuyPanel.add(nameText);
        JTextField nameField = new JTextField(10);
        chooseBuyPanel.add(nameField);

        JLabel quantityText = new JLabel();
        quantityText.setText("Quantity");
        chooseBuyPanel.add(quantityText);
        JTextField quantityField = new JTextField(10);
        chooseBuyPanel.add(quantityField);

        JLabel priceText = new JLabel();
        priceText.setText("Price");
        chooseBuyPanel.add(priceText);
        JTextField priceField = new JTextField(10);
        chooseBuyPanel.add(priceField);

        JLabel messagesText = new JLabel();
        messagesText.setText("Messages");
        chooseBuyPanel.add(messagesText);

        //Creating the buttons for the Buy Window
        JPanel buttonWindow = new JPanel();
        buttonWindow.setPreferredSize(new Dimension(200, 200));
        buttonWindow.setLayout(new GridLayout(3, 1, 20, 50));

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100,50));

        JButton buyButton = new JButton("Buy");
        buyButton.setPreferredSize(new Dimension(100,50));


        //Adding and defining an Action Listener for Reset
        resetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                chooseInvestCombo.setSelectedItem("Stock");
                symbolField.setText("");
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
            }
        });    

        //Adding and defining an Action Listener for Buy
        buyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String investmentValue = chooseInvestCombo.getSelectedItem().toString();        
                String symbolValue = symbolField.getText();
                String nameValue = nameField.getText();
                String quantityValue = quantityField.getText();
                String priceValue = priceField.getText();

                try{
                    outputArea.setText("Investment successfully added!\n");
                    buyInvestment(investmentValue, symbolValue, nameValue, quantityValue, priceValue);
                }
                catch (Exception f){
                    outputArea.setText(f.getMessage());
                }
            }
        });    

        //Creating the text panel for the Buy Window
        textPanel.setPreferredSize(new Dimension(600,200));
        outputArea.setEditable(false);
        outputArea.setBackground(Color.LIGHT_GRAY);

        //Adding scroll functionality to the Buy Window
        JScrollPane scrolledText = new JScrollPane(outputArea);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanel.add(scrolledText);

        //Adding all elements to the Buy Window
        buttonWindow.add(resetButton);
        buttonWindow.add(buyButton);
        buyPanel.add(buttonWindow, BorderLayout.EAST);
        buyPanel.add(chooseBuyPanel, BorderLayout.WEST);
        buyWindow.add(buyPanel, BorderLayout.NORTH);
        buyWindow.add(textPanel, BorderLayout.SOUTH);


        //Creating the menu for the sell Window
        sellWindow.setLayout(new BorderLayout());
        sellWindow.setPreferredSize(new Dimension(700, 500));

        //Creating a panel for the upper portion of the sell menu
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(new BorderLayout());
        sellPanel.setPreferredSize(new Dimension(700, 300));

        //Creating a panel for options portion of the sell window
        JPanel chooseSellPanel = new JPanel();
        chooseSellPanel.setLayout(new GridLayout(7, 2, 20, 20));

        JLabel sellText = new JLabel("Selling an Investment");

        JLabel blankSellText = new JLabel("");
        chooseSellPanel.add(sellText);
        chooseSellPanel.add(blankSellText);

        JLabel symbolSellText = new JLabel("Symbol");
        chooseSellPanel.add(symbolSellText);
        JTextField symbolSellField = new JTextField(10);
        chooseSellPanel.add(symbolSellField);

        JLabel quantitySellText = new JLabel("Quantity");
        chooseSellPanel.add(quantitySellText);
        JTextField quantitySellField = new JTextField(10);       
        chooseSellPanel.add(quantitySellField);

        JLabel priceSellText = new JLabel("Price");
        chooseSellPanel.add(priceSellText);
        JTextField priceSellField = new JTextField(10);       
        chooseSellPanel.add(priceSellField);

        blankSellText = new JLabel("");
        chooseSellPanel.add(blankSellText);
        blankSellText = new JLabel("");
        chooseSellPanel.add(blankSellText);
        blankSellText = new JLabel("");
        chooseSellPanel.add(blankSellText);
        blankSellText = new JLabel("");
        chooseSellPanel.add(blankSellText);

        JLabel messagesSellText = new JLabel("Messages");
        chooseSellPanel.add(messagesSellText);

        //Creating the text panel for the Sell Window
        textPanelSell.setPreferredSize(new Dimension(600,200));
        outputAreaSell.setEditable(false);
        outputAreaSell.setBackground(Color.LIGHT_GRAY);

        //Adding scroll functionality to the Sell Window
        JScrollPane scrolledTextSell = new JScrollPane(outputAreaSell);
        scrolledTextSell.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledTextSell.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanelSell.add(scrolledTextSell);


        //Creating the buttons for the Sell Window
        JPanel buttonSellWindow = new JPanel();
        buttonSellWindow.setPreferredSize(new Dimension(200, 200));
        buttonSellWindow.setLayout(new GridLayout(3, 1, 20, 50));

        JButton sellButton = new JButton("Sell");
        sellButton.setPreferredSize(new Dimension(100,50));

        JButton resetSellButton = new JButton("Reset");
        resetSellButton.setPreferredSize(new Dimension(100,50));

        //Adding and defining an Action Listener for Sell Reset
        resetSellButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                symbolSellField.setText("");
                quantitySellField.setText("");
                priceSellField.setText("");
            }
        });  

        //Adding and defining an Action Listener for sell
        sellButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String symbolValue = symbolSellField.getText();
                String quantityValue = quantitySellField.getText();
                String priceValue = priceSellField.getText();

                try{
                    sellInvestment(symbolValue, quantityValue, priceValue);
                    outputAreaSell.setText("Investment successfully sold!\n");
                }
                catch (Exception f){
                    outputAreaSell.setText(f.getMessage());
                }
            }
        });    

        //Adding all elements to the sell Window
        buttonSellWindow.add(resetSellButton);
        buttonSellWindow.add(sellButton);
        sellPanel.add(buttonSellWindow, BorderLayout.EAST);
        sellPanel.add(chooseSellPanel, BorderLayout.WEST);
        sellWindow.add(sellPanel, BorderLayout.NORTH);
        sellWindow.add(textPanelSell, BorderLayout.SOUTH);

        //Creating the menu for the Update Window
        updateWindow.setLayout(new BorderLayout());
        updateWindow.setPreferredSize(new Dimension(700, 500));

        //Creating a panel for the upper portion of the Update menu
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BorderLayout());
        updatePanel.setPreferredSize(new Dimension(700, 300));

        //Creating a panel for options portion of the Update window
        JPanel chooseUpdatePanel = new JPanel();
        chooseUpdatePanel.setLayout(new GridLayout(7, 2, 20, 20));

        JLabel updateText = new JLabel("Updating Investments");
        blankText = new JLabel("");
        chooseUpdatePanel.add(updateText);
        chooseUpdatePanel.add(blankText);

        JLabel symbolUpdateText = new JLabel("Symbol");
        chooseUpdatePanel.add(symbolUpdateText);
        symbolUpdateField.setEditable(false);    
        chooseUpdatePanel.add(symbolUpdateField);
   
        JLabel nameUpdateText = new JLabel("Name");
        chooseUpdatePanel.add(nameUpdateText);
        nameUpdateField.setEditable(false);    
        chooseUpdatePanel.add(nameUpdateField);

        JLabel priceUpdateText = new JLabel("Price");
        chooseUpdatePanel.add(priceUpdateText);
        chooseUpdatePanel.add(priceUpdateField);

        blankText = new JLabel("");
        chooseUpdatePanel.add(blankText);
        blankText = new JLabel("");
        chooseUpdatePanel.add(blankText);
        blankText = new JLabel("");
        chooseUpdatePanel.add(blankText);
        blankText = new JLabel("");
        chooseUpdatePanel.add(blankText);

        JLabel messagesUpdateText = new JLabel("Messages");
        chooseUpdatePanel.add(messagesUpdateText);

        //Creating the text panel for the Update Window
        textPanelUpdate.setPreferredSize(new Dimension(600,200));
        outputAreaUpdate.setEditable(false);
        outputAreaUpdate.setBackground(Color.LIGHT_GRAY);

        //Adding scroll functionality to the Update Window
        JScrollPane scrolledTextUpdate = new JScrollPane(outputAreaUpdate);
        scrolledTextUpdate.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledTextUpdate.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanelUpdate.add(scrolledTextUpdate);

        //Creating the buttons for the Update Window
        JPanel buttonUpdateWindow = new JPanel();
        buttonUpdateWindow.setPreferredSize(new Dimension(200, 200));
        buttonUpdateWindow.setLayout(new GridLayout(3, 1, 20, 50));

        prevButton.setPreferredSize(new Dimension(100,50));
        nextButton.setPreferredSize(new Dimension(100,50));

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100,50));

        //Adding and defining an Action Listener for previous
        prevButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                 //If there are no investments, there are no investments to update
                if (investments.size() == 0){
                    outputAreaUpdate.setText("No investments to update!");
                }
                else{
                    if (currentUpdateVal > 0){
                        currentUpdateVal--;

                        updateField();
                        updateButton();
                    }
                }
            }
        });  

        //Adding and defining an Action Listener for next
        nextButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                 //If there are no investments, there are no investments to update
                if (investments.size() == 0){
                    outputAreaUpdate.setText("No investments to update!");
                }
                else{
                    if (currentUpdateVal < investments.size() - 1){
                        currentUpdateVal++;

                        updateField();
                        updateButton();
                    }
                }
            }
        });  

        //Adding and defining an Action Listener for save
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double newPrice = 0;

                try{
                    newPrice = Double.parseDouble(priceUpdateField.getText());
                    investments.get(currentUpdateVal).setPrice(newPrice);

                    
                    calculateGain();
                    outputAreaUpdate.setText("Investment " + investments.get(currentUpdateVal).getName() + " updated successfully at a price of $" + String.valueOf(investments.get(currentUpdateVal).getPrice()) + " dollars!");
                }
                catch (Exception f){
                    outputAreaUpdate.setText("Error: Please enter a real value for price!");
                }
            }
        });  

        //Adding all elements to the Update Window
        buttonUpdateWindow.add(prevButton);
        buttonUpdateWindow.add(nextButton);
        buttonUpdateWindow.add(saveButton);
        updatePanel.add(buttonUpdateWindow, BorderLayout.EAST);
        updatePanel.add(chooseUpdatePanel, BorderLayout.WEST);
        updateWindow.add(updatePanel, BorderLayout.NORTH);
        updateWindow.add(textPanelUpdate, BorderLayout.SOUTH);

        //Creating the menu for the getGain Window
        gainWindow.setLayout(new BorderLayout());
        gainWindow.setPreferredSize(new Dimension(700, 500));

        //Creating a panel for the upper portion of the getGain menu
        JPanel gainPanel = new JPanel();
        gainPanel.setLayout(new BorderLayout());
        gainPanel.setPreferredSize(new Dimension(700, 150));

        //Creating a panel for options portion of the getGain window
        JPanel chooseGainPanel = new JPanel();
        chooseGainPanel.setLayout(new GridLayout(3, 2, 20, 20));

        JLabel gainText = new JLabel("Updating Investments");
        blankText = new JLabel("");
        chooseGainPanel.add(gainText);
        chooseGainPanel.add(blankText);

        JLabel totalGainText = new JLabel("Total Gain");
        chooseGainPanel.add(totalGainText);
        totalGainField.setEditable(false);    
        chooseGainPanel.add(totalGainField);

        JLabel indivGainsText = new JLabel("Individual Gains");
        blankText = new JLabel("");
        chooseGainPanel.add(indivGainsText);
        chooseGainPanel.add(blankText);

        //Creating the text panel for the Gain Window
        textPanelGain.setPreferredSize(new Dimension(600,350));
        outputAreaGain.setEditable(false);
        outputAreaGain.setBackground(Color.LIGHT_GRAY);

        //Adding scroll functionality to the Gain Window
        JScrollPane scrolledTextGain = new JScrollPane(outputAreaGain);
        scrolledTextGain.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledTextGain.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanelGain.add(scrolledTextGain);

        //Adding all elements to the Gain Window
        gainPanel.add(chooseGainPanel, BorderLayout.WEST);
        gainWindow.add(gainPanel, BorderLayout.NORTH);
        gainWindow.add(textPanelGain, BorderLayout.SOUTH);

        //Creating the menu for the Search Window
        searchWindow.setLayout(new BorderLayout());
        searchWindow.setPreferredSize(new Dimension(700, 500));

        //Creating a panel for the upper portion of the Search menu
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setPreferredSize(new Dimension(700, 300));

        //Creating a panel for options portion of the Search window
        JPanel chooseSearchPanel = new JPanel();
        chooseSearchPanel.setLayout(new GridLayout(7, 2, 20, 20));

        JLabel searchText = new JLabel("Searching Investments");
        blankText = new JLabel("");
        chooseSearchPanel.add(searchText);
        chooseSearchPanel.add(blankText);

        JLabel symbolSearchText = new JLabel("Symbol");
        chooseSearchPanel.add(symbolSearchText);
        JTextField symbolSearchField = new JTextField(10);
        chooseSearchPanel.add(symbolSearchField);

        JLabel keywordsSearchText = new JLabel("Name Keywords");
        chooseSearchPanel.add(keywordsSearchText);
        JTextField keywordsSearchField = new JTextField(10);
        chooseSearchPanel.add(keywordsSearchField);        

        JLabel lowPriceSearchText = new JLabel("Low Price");
        chooseSearchPanel.add(lowPriceSearchText);
        JTextField lowPriceSearchField = new JTextField(10);
        chooseSearchPanel.add(lowPriceSearchField);        

        JLabel highPriceSearchText = new JLabel("High Price");
        chooseSearchPanel.add(highPriceSearchText);
        JTextField highPriceSearchField = new JTextField(10);
        chooseSearchPanel.add(highPriceSearchField);           

        JLabel resultsText = new JLabel("Search Results");
        blankText = new JLabel("");
        chooseSearchPanel.add(blankText);
        blankText = new JLabel("");
        chooseSearchPanel.add(blankText);
        chooseSearchPanel.add(resultsText);
        
        //Creating the text panel for the Search Window
        textPanelSearch.setPreferredSize(new Dimension(600,200));
        outputAreaSearch.setEditable(false);
        outputAreaSearch.setBackground(Color.LIGHT_GRAY);

        //Adding scroll functionality to the Search Window
        JScrollPane scrolledTextSearch = new JScrollPane(outputAreaSearch);
        scrolledTextSearch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledTextSearch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanelSearch.add(scrolledTextSearch);


        //Creating the buttons for the Search Window
        JPanel buttonSearchWindow = new JPanel();
        buttonSearchWindow.setPreferredSize(new Dimension(200, 200));
        buttonSearchWindow.setLayout(new GridLayout(3, 1, 20, 50));

        JButton resetSearchButton = new JButton("Reset");
        resetSearchButton.setPreferredSize(new Dimension(100,50));

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100,50));

        //Adding and defining an Action Listener for Search Reset
        resetSearchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                symbolSearchField.setText("");
                keywordsSearchField.setText("");
                lowPriceSearchField.setText("");
                highPriceSearchField.setText("");
            }
        });  

        //Adding and defining an Action Listener for Search
        searchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String symbolValue = symbolSearchField.getText();
                String keywordValue = keywordsSearchField.getText();
                String lowPriceValue = lowPriceSearchField.getText();
                String highPriceValue = highPriceSearchField.getText();

                try{
                    outputAreaSearch.setText("");
                    searchInvestment(symbolValue, keywordValue, lowPriceValue, highPriceValue);
                }
                catch (Exception f){
                    outputAreaSearch.setText(f.getMessage());
                }
            }
        });    

        //Adding all elements to the search Window
        buttonSearchWindow.add(resetSearchButton);
        buttonSearchWindow.add(searchButton);
        searchPanel.add(buttonSearchWindow, BorderLayout.EAST);
        searchPanel.add(chooseSearchPanel, BorderLayout.WEST);
        searchWindow.add(searchPanel, BorderLayout.NORTH);
        searchWindow.add(textPanelSearch, BorderLayout.SOUTH);

        //Adding each window
        add(commandMenu);
        add(buyWindow);
        add(sellWindow);
        add(updateWindow);
        add(gainWindow);
        add(searchWindow);

        buyWindow.setVisible(false);
        sellWindow.setVisible(false);
        updateWindow.setVisible(false);
        gainWindow.setVisible(false);
        searchWindow.setVisible(false);
        prevButton.setEnabled(false);
    }

    //Main Function
    public static void main(String[] args)
    {
        Portfolio portfolio = new Portfolio();
        portfolio.setVisible(true);

        String line, buyType, symbol, name;
        int quantity, counter = 0;
        double price, bookValue;

        commandLength = args.length;

        //inputting from a file at command line
        if (args.length > 0){
            fileName = args[0];
            String[] inputInfo;

            try {

                File f = new File(fileName);
                Scanner scanner = new Scanner(f);

                //while loop to scan each element
                while (scanner.hasNextLine()){
                    line = scanner.nextLine();

                    if (!line.isEmpty()){
                        //splitting each line into inputInfo and taking the part within the quotes
                        inputInfo = line.split("\"");
                        buyType = inputInfo[1];

                        line = scanner.nextLine();
                        inputInfo = line.split("\"");
                        symbol = inputInfo[1];

                        line = scanner.nextLine();
                        inputInfo = line.split("\"");
                        name = inputInfo[1];

                        line = scanner.nextLine();
                        inputInfo = line.split("\"");
                        quantity = Integer.parseInt(inputInfo[1]);

                        line = scanner.nextLine();
                        inputInfo = line.split("\"");
                        price = Double.parseDouble(inputInfo[1]);

                        line = scanner.nextLine();
                        inputInfo = line.split("\"");
                        bookValue = Double.parseDouble(inputInfo[1]);

                        //adding the investment to the ArrayList
                        if (buyType.equals("stock")){
                            investments.add(new Stock(symbol.toUpperCase(), name, quantity, price, bookValue));
                        }
                        else{
                            investments.add(new MutualFund(symbol.toUpperCase(), name, quantity, price, bookValue));
                        }

                        //Adding investments to the hashmap
                        String nameWords[] = name.split(" ");
                        for (int i = 0; i < nameWords.length; i++){
                            if (investmentMap.get(nameWords[i].toLowerCase()) == null) {
                                investmentMap.put(nameWords[i].toLowerCase(), new ArrayList<Integer>());
                            }
                            
                            investmentMap.get(nameWords[i].toLowerCase()).add(counter);
                        }

                        counter++;
                    }
                }

                calculateGain();

            }
            catch(Exception e){
                //do nothing
            }
        }

        updateButton();
    }

    //function to update the update text field 
    public static void updateField(){
        symbolUpdateField.setText(investments.get(currentUpdateVal).getSymbol());
        nameUpdateField.setText(investments.get(currentUpdateVal).getName());
        priceUpdateField.setText(String.valueOf(investments.get(currentUpdateVal).getPrice()));
    }

    //function to update the prev and next buttons on the update window when something is bought or sold
    public static void updateButton(){

        
        if (investments.size() == 0){
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
        else if (investments.size() == 1){
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            currentUpdateVal = 0; 
            updateField();
        }
        else if (investments.size() > 1 && currentUpdateVal <= 0){
            prevButton.setEnabled(false);
            nextButton.setEnabled(true);
        }
        else if (investments.size() > 1 && currentUpdateVal == investments.size() - 1){
            prevButton.setEnabled(true);
            nextButton.setEnabled(false);
        }
        else{
            prevButton.setEnabled(true);
            nextButton.setEnabled(true);
        }
    }

    //Function to calculate total gain
    public static void calculateGain(){
        double gainValue = 0;
        double sumBookValue = 0;
        String gainString;

        outputAreaGain.setText("");

        //iterating through investments and getting the total sum of each gain and bookvalue. Total gain is gainValue minus the sum of book values.
        for (int i = 0; i < investments.size(); i++){

            if (investments.get(i).getClassType().equals("fund")){
                gainString = String.format("%.2f", investments.get(i).getPrice() * investments.get(i).getQuantity() - 45 - investments.get(i).getBookValue());
                gainValue = gainValue + investments.get(i).getPrice() * investments.get(i).getQuantity() - 45;
            }
            else{
                gainString = String.format("%.2f", investments.get(i).getPrice() * investments.get(i).getQuantity() - 9.99 - investments.get(i).getBookValue());
                gainValue = gainValue + investments.get(i).getPrice() * investments.get(i).getQuantity() - 9.99;
            }

            sumBookValue = sumBookValue + investments.get(i).getBookValue();
            outputAreaGain.append(investments.get(i).getName() + " has a gain of " + gainString + "\n");
        }

        gainString = String.format("%.2f", gainValue - sumBookValue);
        totalGainField.setText("$" + gainString);
    }

    //function to remove an index from the investment map
    public static void removeIndex(Integer index){
        ArrayList<Integer> list;
        for (String key: investmentMap.keySet())
        {
            list = investmentMap.get(key);
            investmentMap.remove(index);

            for (int i = 0; i < list.size(); i++){
                Integer integer = list.get(i);

                if (integer > index){
                    list.set(i, integer - 1);
                }
            }

            if (list.isEmpty()){
                investmentMap.remove(key);
            }
        }
    }

    //function to purchase an investment
    public static void buyInvestment(String buyType, String symbol, String name, String quantity, String price) throws Exception{
        boolean symbolFound = false;
        int quantityInt;
        double priceDouble;

        //If input is mutualfund, change to fund.
        if (buyType.equalsIgnoreCase("mutualfund")){
            buyType = "fund";
        }
        if (buyType.equalsIgnoreCase("stock")){
            buyType = "stock";
        }

        //Conversion of Quantity to integer
        try{
            quantityInt = Integer.parseInt(quantity);
        }
        catch (Exception e){
            throw new Exception("Error: Quantity must be a whole number!");
        }

        //Conversion of Price to double
        try{
            priceDouble = Double.parseDouble(price);
        }
        catch (Exception e){
            throw new Exception("Error: Price must be a real value!");
        }

        //iterating through every investment element to check if the fund already exists
        for (int i = 0; i < investments.size(); i++){

            //case for if the investment exists
            if (investments.get(i).getSymbol().equalsIgnoreCase(symbol)){
                if (investments.get(i).getClassType().equals(buyType)){
                    symbolFound = true;

                    //Setting new values for the fund
                    try{
                        investments.get(i).addQuantity(quantityInt);
                        investments.get(i).setPrice(priceDouble);
                    }
                    catch (Exception f){
                        throw new Exception(f.getMessage());
                    }


                    if (investments.get(i).getClassType().equals("fund")){
                        investments.get(i).setBookValue(priceDouble * quantityInt + investments.get(i).getBookValue());
                    }
                    else{
                        investments.get(i).setBookValue(priceDouble * quantityInt + investments.get(i).getBookValue() + 9.99);
                    }

                    outputArea.append(investments.get(i).toString());
                }
                else{
                    symbolFound = true;
                    throw new Exception("Sorry! "+symbol+" already exists!");
                }
            }
        }

        //case for if the investment does not exist
        if (symbolFound == false){

            if (name.isEmpty()){
                throw new Exception("Error: This investment does not exist. Must specify a name.");
            }

            //adding the investment to the list
            if (buyType.equals("fund")){
                try{
                    investments.add(new MutualFund(symbol.toUpperCase(), name, quantityInt, priceDouble, quantityInt * priceDouble));
                }
                catch (Exception f){
                    throw new Exception(f.getMessage());
                }
            }
            else{
                try{
                    investments.add(new Stock(symbol.toUpperCase(), name, quantityInt, priceDouble, quantityInt * priceDouble + 9.99));
                }
                catch (Exception f){
                    throw new Exception(f.getMessage());
                }
            }

            //Adding elements to the hashmap
            String nameWords[] = name.split(" ");
            for (int i = 0; i < nameWords.length; i++){
                if (investmentMap.get(nameWords[i].toLowerCase()) == null) {
                    investmentMap.put(nameWords[i].toLowerCase(), new ArrayList<Integer>());
                }
                investmentMap.get(nameWords[i].toLowerCase()).add(investments.size() - 1);
            }

            for (int i = 0; i < investments.size(); i++){
                if (investments.get(i).getSymbol().equalsIgnoreCase(symbol)){
                    outputArea.append(investments.get(i).toString());
                }
            }
        }

        updateButton();
        calculateGain();
    }

    //function to sell an investment
    public static void sellInvestment(String symbol, String quantityString, String priceString) throws Exception{
        //setting default values for the sell case
        boolean symbolFound = false;
        int quantity;
        double price;
        double payment = 0;

        //Conversion of Quantity to integer
        try{
            quantity = Integer.parseInt(quantityString);
        }
        catch (Exception e){
            throw new Exception("Error: Quantity must be a whole number!");
        }

        //Conversion of Price to double
        try{
            price = Double.parseDouble(priceString);
        }
        catch (Exception e){
            throw new Exception("Error: Price must be a real value!");
        }

        //iterating through the investments ArrayList
        for (int i = 0; i < investments.size(); i++){

            //case for if the stock exists 
            if (investments.get(i).getSymbol().equalsIgnoreCase(symbol)){
                symbolFound = true;

                if (investments.get(i).getQuantity() < quantity){
                    throw new Exception("Please enter a quantity no greater than " + investments.get(i).getQuantity());
                }
            }

            //case for if the stock needs to be removed from the ArrayList
            if (investments.get(i).getQuantity() == quantity && symbolFound == true){

                outputAreaSell.append(investments.get(i).getName() + " successfully removed.\n");

                if (investments.get(i).getClassType().equals("stock")){
                    payment = payment + (price * quantity - 9.99);
                }
                else{
                    payment = payment + (price * quantity - 45.00);
                }

                removeIndex(i);

                investments.remove(i);
                break;
            }

            //case for if the stock doesn't need to be removed from the ArrayList
            else if (symbolFound == true){
                investments.get(i).setBookValue(investments.get(i).getBookValue() * (investments.get(i).getQuantity() - quantity) / investments.get(i).getQuantity());
                investments.get(i).removeQuantity(quantity);

                outputAreaSell.append(investments.get(i).toString());
                break;
            }
        }

        //case for if the entered symbol doesn't match any stock or mutual fund
        if (symbolFound == false){
            throw new Exception("Please enter a valid symbol.");
        }
        
        updateButton();
        calculateGain();
    }

    //function to search through all investments
    public static void searchInvestment(String symbolSearch, String keywords, String lowPriceValue, String highPriceValue) throws Exception{

        double priceTest;

        //Conversion of Low Price to double
        if (!lowPriceValue.isEmpty()){
            try{
                priceTest = Double.parseDouble(lowPriceValue);
            }
            catch (Exception e){
                throw new Exception("Error: Low Price must be a Real Value!");
            }
        }

        //Conversion of High Price to double
        if (!lowPriceValue.isEmpty()){
            try{
                priceTest = Double.parseDouble(highPriceValue);
            }
            catch (Exception e){
                throw new Exception("Error: High Price must be a Real Value!");
            }
        }

        if (symbolSearch.contains(" ") && !symbolSearch.isEmpty()){
            throw new Exception("Error: Symbol cannot have a space!");
        }

        //creating the search lists for investments and mutual funds
        for (int i = 0; i < investments.size(); i++){
            investmentSearch.add(1);
        }

        //case for if a symbol is entered
        if (!symbolSearch.isEmpty()){

            //iterating through the investments and funds arraylists to check if the entered symbol exists
            //If the symbol doesn't match a stock/mutual fund, a 0 is inputted in stockSearch/fundSearch 
            //to indicate that element has been eliminated from the search list
            for (int i = 0; i < investmentSearch.size(); i++){
                if (!investments.get(i).getSymbol().equalsIgnoreCase(symbolSearch)){
                    investmentSearch.set(i, 0);
                }
            }
        }

        //case for if a keyword is entered
        if (!keywords.isEmpty()){
            String[] arrOfKeywords = keywords.split(" ");

            ArrayList<Integer> LastValueSearch  = new ArrayList<Integer>();
            ArrayList<Integer> valueSearch  = new ArrayList<Integer>();

            if (investmentMap.containsKey(arrOfKeywords[0].toLowerCase())){
                LastValueSearch = investmentMap.get(arrOfKeywords[0].toLowerCase());
            }

            //Searching by keyword with the hashmap
            for (int i = 1; i < arrOfKeywords.length; i++){
                if (investmentMap.containsKey(arrOfKeywords[i].toLowerCase())){

                    valueSearch = investmentMap.get(arrOfKeywords[i].toLowerCase());

                    ArrayList<Integer> Intersection = new ArrayList<Integer>();

                    for (Integer t : LastValueSearch) {
                        if(valueSearch.contains(t)) {
                            Intersection.add(t);
                        }
                    }

                    LastValueSearch = Intersection;
                }
            }

                //Removing all elements that don't match the keywords
                for (int i = 0; i < investmentSearch.size(); i++){
                    if (!LastValueSearch.contains(i)){
                        investmentSearch.set(i, 0);
                    }
                }
            }

            String priceRange = "";
            String hyphen = "-";

            if (!(lowPriceValue.isEmpty() && highPriceValue.isEmpty())){
                priceRange = lowPriceValue.concat(hyphen).concat(highPriceValue);
            }
            else if (lowPriceValue.isEmpty() && !(highPriceValue.isEmpty())){
                priceRange = hyphen.concat(highPriceValue);
            }
            else if (lowPriceValue.isEmpty() && !(highPriceValue.isEmpty())){
                priceRange = highPriceValue.concat(hyphen);
            }

            //case for if a price range is entered
            if (!(priceRange.isEmpty())){
                String[] arrPriceRange = priceRange.split("-");

                //case for if there's a range entered
                if (arrPriceRange.length == 2 || (arrPriceRange.length == 1 && (priceRange.contains("-")))){

                    if (arrPriceRange[0].isEmpty()){
                        arrPriceRange[0] = "0";
                    }                         

                    //iterating through each stock
                    for (int i = 0; i < investmentSearch.size(); i++){

                        //case for if the stock's value < range and there's only a lower bound indicated
                        if (arrPriceRange.length == 1 && (investments.get(i).getPrice() < Double.parseDouble(arrPriceRange[0]))){
                            investmentSearch.set(i, 0);
                        }

                        //case for if both bounds are entered
                        if (arrPriceRange.length == 2){
                            if (investments.get(i).getPrice() < Double.parseDouble(arrPriceRange[0]) || investments.get(i).getPrice() > Double.parseDouble(arrPriceRange[1])){
                                investmentSearch.set(i, 0);
                            }
                        }
                    }                      
                }
            }   

        //printing all investments and mutual funds reminaing in the search lists
        for (int i = 0; i < investments.size(); i++){
            if (investmentSearch.get(i) == 1){
                outputAreaSearch.append(investments.get(i).toString());
            }
        }

        investmentSearch.clear();
    }
}

  
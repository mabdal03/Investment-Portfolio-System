package ePortfolio;

public class Stock extends Investment
{
    /**
    Defining private instance variables
    */
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;

    /**
    Constructor for Class Stock with attributes symbol, name, quantity, price, and bookvalue
    */
    public Stock(String symbol, String name, int quantity, double price, double bookValue) throws Exception{
        super(symbol, name, quantity, price, bookValue);
    }

    /**
    Get class of stock
     */
     @Override
    public String getClassType(){
        return "stock";
    }

    //overriding toPrint for Stock
    @Override
    public String toPrint(){
        return ("type = \"stock\"\n" + "symbol = \"" + getSymbol() + "\"\nname = \"" + getName() + "\"\nquantity = \"" + getQuantity() + "\"\nprice = \"" + getPrice() + "\"\nbookValue = \""+getBookValue() + "\"\n");
    }    
}
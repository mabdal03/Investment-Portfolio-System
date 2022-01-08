package ePortfolio;

public class Investment
{
    /**
    Defining private instance variables
    */
    private String type;
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;

    /**
    Constructor for Class Investment with attributes symbol, name, quantity, price, and bookvalue
    */
    public Investment(String symbol, String name, int quantity, double price, double bookValue) throws Exception{
        
        if (price < 0){
            throw new Exception("Error: price cannot be < 0!");
        }

        if (symbol.contains(" ")){
            throw new Exception("Error: symbol cannot have a space!");
        }


        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;
    }

    /**
    Get method for symbol 
    */
    public String getSymbol(){
        return symbol;
    }

    /**
    Get method for name 
    */
    public String getName(){
        return name;
    }

    /**
    Get method for price 
    */
    public double getPrice(){
        return price;
    }

    /**
    Get method for quantity 
    */
    public int getQuantity(){
        return quantity;
    }

    /**
    Get method for book value 
    */
    public double getBookValue(){
        return bookValue;
    }

    /**
    Method for adding to quantity 
    */
    public void addQuantity(int quantity){
        this.quantity = this.quantity + quantity;
    }

    /**
    Method for removing quantity 
    */
    public void removeQuantity(int quantity){
        this.quantity = this.quantity - quantity;
    }

    /**
    Set method for bookvalue 
    */
    public void setBookValue(double bookValue){
        this.bookValue = bookValue;
    }

    /**
    Set method for price 
    */
    public void setPrice(double price) throws Exception{

        if (price < 0){
            throw new Exception("Error: price cannot be < 0!");
        }
        this.price = price;
    }

    /**
    Get class of investment
     */
    public String getClassType(){
        return "";
    }

    /**
    Equals method to check if two funds are equivalent 
    */
    public boolean equals(Investment other) {
       if (other == null) 
          return false;
       else
          return symbol == other.symbol && name == other.name && quantity == other.quantity && price == other.price && bookValue == other.bookValue;
    }    

    /**
    Convert fund to a string
     */
    public String toString(){
        return ("Symbol: " + symbol + "\nName: " + name + "\nQuantity: " + quantity + "\nPrice: " + price + "\nBook Value: " + bookValue + "\n\n");
    }

    /**
    toPrint type
     */
    public String toPrint(){
        return ("");
    } 
}
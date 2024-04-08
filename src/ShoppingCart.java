import java.util.*;

public class ShoppingCart {
    private static final int MAX_ITEMS =100;
    private String[] itemNames;
    private static double[] itemPrices;
    private static int[] itemQuantities; // her bir üründen kaç tane var
    private static int itemCount; // kaç farklı ürün var

    public ShoppingCart(){
        this.itemCount=0;
        this.itemNames= new String[MAX_ITEMS];
        this.itemPrices= new double[MAX_ITEMS];
        this.itemQuantities= new int[MAX_ITEMS];
        Arrays.fill(itemQuantities,0);
    }
    private void addItemToCart(String itemName, double itemPrice, int quantity){
        if(itemCount<MAX_ITEMS){
            checkStock(itemName,quantity); // burayı ekledim yeni
            if(checkStock(itemName,quantity)){
                itemNames[itemCount]= itemName;
                itemPrices[itemCount]= itemPrice;
                itemQuantities[itemCount] = quantity; //stok miktarını güncelledik
                itemCount++;
                System.out.println(itemName+ " (x" +quantity+ ") added to the cart.");            }
            else{
                System.out.println("Sorry , "+itemName+ " is out of stock or insufficient stock.");
            }
        }else{
            System.out.println("Sorry, cart is full.You can not add more items.");
        }
    }

    private boolean checkStock(String itemName, int quantity){
        if(containsItem(itemName)){ // ürünün sepet içinde olup olmadığını kontrol ettik
            return true; // değilse stoğun mevcut olduğunu varsaydık
        }
        itemName= itemName.toLowerCase();
        for(int i=0; i< itemCount; i++){
            if(itemNames[i].toLowerCase().equals(itemName.toLowerCase())){
                if(itemQuantities[i]>= quantity){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean containsItem(String itemName){
        for(int i=0; i< itemCount; i++){
            if(itemNames[i].equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }

    private void removeItemsFromTheCart(String removeName){
        removeName = removeName.toLowerCase();
        boolean found= false;
        for(int i=0; i<itemCount; i++){
            if(itemNames[i].equals(removeName)){
                found = true;

                for(int j=0; j<itemCount-1; j++){ //dizideki sondan bir önceki elemana kadar saydık
                    itemNames[j] = itemNames[j + 1]; //silinecek öğenin yerini boşaltmak ve sonraki öğeleri bir konum yukarıya taşımak için yaptık
                    itemPrices[j] = itemPrices[j + 1];
                }
                itemCount--;
                itemNames[itemCount] = null; //dizideki yerini boş olarak atadık
                itemPrices[itemCount] = 0.0; // fiyatını da sildik
                System.out.println(removeName + " removed from the cart.");
                break;
            }
        }
        if(!found){
            System.out.println(removeName+ " not found in the cart.");
        }
    }

    private double calculateTotalPrice(boolean applyDiscount, double discountRate){
        double totalPrice=0.0;

        for(int i=0; i<itemCount; i++){
            totalPrice+= itemPrices[i];
        }
        if(applyDiscount){
            totalPrice*= (1.0- discountRate);
        }

        return totalPrice;
    }
    private void clearCart(){
        if(itemCount==0){
            System.out.println("Cart is already empty.");
        }else{
            for(int i=0;i<itemCount;i++){
                itemNames[i]=null;
                itemPrices[i]= 0.0;
            }
            itemCount=0;
            System.out.println("Cart is cleared successfully.");
        }
    }

    private void displayCart(){
        if(itemCount==0){
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Items in the cart: ");
        for(int i=0; i<itemCount; i++){
            System.out.println(itemNames[i]+ " - $"+itemPrices[i]);
        }
    }

    public void displayStatistics(double totalPriceWithoutDiscount, double totalPriceWithDiscount){
        if(itemCount==0){
            System.out.println("Cart is empty.");
            return;
        }

        double total= totalPriceWithoutDiscount;
        if(totalPriceWithDiscount< total){
            total= totalPriceWithDiscount;
        }

        double maxPrice= itemPrices[0];
        double minPrice= itemPrices[0];
        int totalQuantity= 0; //sepette bulunan ürünlerin top miktarı

        for(int i=0; i<itemCount; i++){
            totalQuantity+= itemQuantities[i];
            if(itemPrices[i]> maxPrice){
                maxPrice= itemPrices[i];
            }
            if(itemPrices[i]<minPrice){
                minPrice= itemPrices[i];
            }
        }

        double averagePrice= total / totalQuantity;

        System.out.println("--> Cart Statistics <--");
        System.out.println("Total number of items: "+ totalQuantity);
        System.out.println("Total price without discount: $"+ totalPriceWithoutDiscount);
        System.out.println("Total price with discount: $"+ totalPriceWithDiscount);
        System.out.println("Average price per item: $"+averagePrice);
        System.out.println("Most expensive item in the cart: "+ findItemNameByPrice(maxPrice));
        System.out.println("Cheapest item in the cart: "+ findItemNameByPrice(minPrice));

    }

    private List<String> findItemNameByPrice(double price){
        List<String> matchingItemNames = new ArrayList<>(); // aynı fiyata sahip birden fazla ürün olursa diye bunu yazdım
        for(int i=0; i<itemCount; i++){
            if(itemPrices[i]== price){
                matchingItemNames.add(itemNames[i]); // itemNamesteki ürünleri matchingItemNames listesine ekledim
            }
        }
        return matchingItemNames;
    }

    public static void main(String[] args) {
        Scanner get= new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        System.out.println("Welcome to the Shopping Cart!");
        int choice;
        double discountRate=0.0;

        do{
            System.out.print("\n Please select an option:\n");
            System.out.println("1. Add item to the cart");
            System.out.println("2. Remove item from the cart");
            System.out.println("3. Display cart");
            System.out.println("4. Apply discount and display total price");
            System.out.println("5. Display statistics");
            System.out.println("6. Clear Cart");
            System.out.println("7. Exit");

            System.out.print("Select your choice: ");
            choice= get.nextInt();
            get.nextLine();

            switch(choice){
                case 1:
                    System.out.print("Enter item name: ");
                    String itemName = get.nextLine();
                    System.out.print("Enter item price:$");
                    double itemPrice = get.nextDouble();
                    System.out.print("Enter quantity: ");
                    int quantity = get.nextInt();
                    cart.addItemToCart(itemName,itemPrice,quantity);
                    break;
                case 2:
                    System.out.print("Enter the name of the item you want to remove: ");
                    String removeName = get.nextLine();
                    cart.removeItemsFromTheCart(removeName);
                    break;
                case 3 :
                    cart.displayCart();
                    break;
                case 4:
                    System.out.print("Enter discount rate (%30 or %50): ");
                    discountRate = get.nextDouble();
                    if (discountRate == 30 || discountRate == 50) {
                        double totalPriceWithDiscount = cart.calculateTotalPrice(true, discountRate / 100.0);
                        System.out.println("Total price with " + discountRate + "% discount: $" + totalPriceWithDiscount);
                    } else {
                        System.out.println("Invalid discount rate. Please enter either 30 or 50.");
                    }
                    break;
                case 5:
                    double totalPriceWithoutDiscount =0.00;
                    for (int i = 0; i < itemCount; i++) {
                        totalPriceWithoutDiscount += itemPrices[i] * itemQuantities[i];
                    }
                    double totalPriceWithDiscount = totalPriceWithoutDiscount * (1 - discountRate / 100.0);
                    cart.displayStatistics(totalPriceWithoutDiscount, totalPriceWithDiscount);
                    break;
                case 6:
                    cart.clearCart();
                    break;
                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }while(choice != 7);

    }
}
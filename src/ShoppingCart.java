import java.util.ArrayList;
import java.util.List;
public class ShoppingCart {
    private static final int MAX_ITEMS =100;
    private String[] itemNames;
    private double[] itemPrices;
    private int[] itemQuantities; // her bir üründen kaç tane var
    private int itemCount; // kaç farklı ürün var

    public ShoppingCart(){
        this.itemCount=0;
        this.itemNames= new String[MAX_ITEMS];
        this.itemPrices= new double[MAX_ITEMS];
        this.itemQuantities= new int[MAX_ITEMS];
    }

    private void addItemToCart(String itemName, double itemPrice, int quantity){
        if(itemCount<MAX_ITEMS){
            if(checkStock(itemName,quantity)){
                itemNames[itemCount]= itemName;
                itemPrices[itemCount]= itemPrice;
                itemCount++;
                System.out.println(itemName+ " (x" +quantity+ ") added to the cart.");
            }
            else{
                System.out.println("Sorry , "+itemName+ " is out of stock or insufficient stock.");
            }
        }else{
            System.out.println("Sorry, cart is full.You can not add more items.");
        }
    }

    private boolean checkStock(String itemName, int quantity){
        for(int i=0; i<itemCount; i++){
            if(itemNames[i].equals(itemName)){
                if(itemQuantities[i]>= quantity){
                    itemQuantities[i]-=quantity; //ürünleri sepete ekledikten sonra stocktan düştük
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    private void removeItemsFromTheCart(String removeName){
        boolean found= false;
        for(int i=0; i<itemCount; i++){
            if(itemNames[i] == removeName){
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
        for(int i=0;i<itemCount;i++){
            itemNames[i]=null;
            itemPrices[i]= 0.0;
        }
        itemCount=0;
        System.out.println("Cart is cleared successfully.");
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
}
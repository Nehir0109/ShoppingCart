public class ShoppingCart {
    private static final int MAX_ITEMS =100;
    private String[] itemNames;
    private double[] itemPrices;
    private int itemCount;

    public ShoppingCart(){
        this.itemCount=0;
        this.itemNames= new String[MAX_ITEMS];
        this.itemPrices= new double[MAX_ITEMS];
    }

    private void addItemToCart(String itemName, double itemPrice ){
        if(itemCount<MAX_ITEMS){
            itemNames[itemCount]= itemName;
            itemPrices[itemCount]= itemPrice;
            itemCount++;
            System.out.println(itemName+ " added to the cart.");
        }else{
            System.out.println("Sorry, cart is full.");
        }
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

    private double calculateTotalPrice(){
        double totalPrice=0.0;

        for(int i=0; i<itemCount; i++){
            totalPrice+= itemPrices[i];
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

}
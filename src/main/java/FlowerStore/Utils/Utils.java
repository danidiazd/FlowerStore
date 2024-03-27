package FlowerStore.Utils;


// TODO: Fix method

public class Utils {

    public void waitForKeyPress() {
        System.out.println("\nPress Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

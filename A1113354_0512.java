import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class DumplingShop {
    private static final int MAX_CAPACITY = 9000;
    private static final int PORK_DUMPLING_COUNT = 5000;
    private static final int BEEF_DUMPLING_COUNT = 3000;
    private static final int VEGETABLE_DUMPLING_COUNT = 1000;

    private static int porkDumplingsRemaining = PORK_DUMPLING_COUNT;
    private static int beefDumplingsRemaining = BEEF_DUMPLING_COUNT;
    private static int vegetableDumplingsRemaining = VEGETABLE_DUMPLING_COUNT;

    private static synchronized boolean sellDumplings(String dumplingType, int quantity) {
        if (dumplingType.equals("pork")) {
            if (porkDumplingsRemaining >= quantity) {
                porkDumplingsRemaining -= quantity;
                return true;
            }
        } else if (dumplingType.equals("beef")) {
            if (beefDumplingsRemaining >= quantity) {
                beefDumplingsRemaining -= quantity;
                return true;
            }
        } else if (dumplingType.equals("vegetable")) {
            if (vegetableDumplingsRemaining >= quantity) {
                vegetableDumplingsRemaining -= quantity;
                return true;
            }
        }
        return false;
    }

    private static synchronized void printRemainingDumplings() {
        System.out.println("Remaining Dumplings: Pork=" + porkDumplingsRemaining +
                " Beef=" + beefDumplingsRemaining +
                " Vegetable=" + vegetableDumplingsRemaining);
    }

    static class Customer implements Runnable {
        private static final int MIN_ORDER_QUANTITY = 10;
        private static final int MAX_ORDER_QUANTITY = 50;

        private final String name;

        Customer(String name) {
            this.name = name;
        }

        private int generateOrderQuantity() {
            Random random = new Random();
            return random.nextInt(MAX_ORDER_QUANTITY - MIN_ORDER_QUANTITY + 1) + MIN_ORDER_QUANTITY;
        }

        private String generateOrderType() {
            Random random = new Random();
            int randomNumber = random.nextInt(3);
            switch (randomNumber) {
                case 0:
                    return "pork";
                case 1:
                    return "beef";
                case 2:
                    return "vegetable";
                default:
                    return "pork";
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000); // Waiting for the waiter before placing an order
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int orderQuantity = generateOrderQuantity();
                String orderType = generateOrderType();

                synchronized (DumplingShop.class) {
                    if (sellDumplings(orderType, orderQuantity)) {
                        System.out.println(name + " ordered " + orderQuantity + " " + orderType + " dumplings.");
                    } else {
                        System.out.println("Sorry, " + name + " could not order " + orderQuantity + " " + orderType + " dumplings.");
                        break;
                    }
                }
            }
        }
    }
   public static void main(String[] args) {
        int numCustomers = 0;


        try {
            System.out.print("Enter the number of customers: ");
            Scanner scanner = new Scanner(System.in);
            numCustomers = scanner.nextInt();
            scanner.close();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Exiting the program.");
            System.exit(0);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numCustomers);


        for (int i = 1; i <= numCustomers; i++) {
            String customerName = "Customer " + i;
            executor.submit(new Customer(customerName));
        }


        executor.shutdown();


        while (!executor.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All customers have finished ordering.");

        printRemainingDumplings();
    }
}
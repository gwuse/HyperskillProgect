import java.util.Scanner;

public class CoffeeMachine {
    private static Scanner scanner = new Scanner(System.in);
    private static String[] remainingName = {"water", "milk", "bean", "cup", "money"};
    private static int[] remaining = {400, 540, 120, 9, 550};//water,milk,bean,cup,money
    private static int[] espresso = {250, 0, 16, 1, 4};//the cost of :water,milk,bean,cup,money
    private static int[] latte = {350, 75, 20, 1, 7};
    private static int[] cappuccino = {200, 100, 12, 1, 6};

    public static void main(String[] args) {
        boolean start = true;
        while (start) {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
            String keyword = scanner.next();
            System.out.println();
            switch (keyword) {
                case "buy":
                    buyCoffee();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    stateOfMachine();
                    break;
                case "exit":
                    start = false;
                    break;
                default:
            }
        }

    }

    private static void buyCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        switch (scanner.next()) {
            case "1":
                makeCoffee(espresso);
                break;
            case "2":
                makeCoffee(latte);
                break;
            case "3":
                makeCoffee(cappuccino);
                break;
            default:
        }
    }

    private static void makeCoffee(int[] coffee) {
        int temp = resourceTest(espresso);
        if (temp == -1) {
            System.out.println("I have enough resources, making you a coffee!");
            for (int i = 0; i < 4; i++) {
                remaining[i] -= coffee[i];
            }
            remaining[4] += coffee[4];
        } else {
            System.out.printf("Sorry, not enough %s!\n", remainingName[temp]);
        }

    }

    private static int resourceTest(int[] coffee) {
        for (int i = 0; i < 4; i++) {
            if (remaining[i] < coffee[i]) {
                return i;
            }
        }
        return -1;
    }

    private static void takeMoney() {
        System.out.println("I gave you $" + remaining[4] + "\n");
        remaining[4] = 0;
    }

    private static void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        remaining[0] += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        remaining[1] += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        remaining[2] += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        remaining[3] += scanner.nextInt();
    }

    private static void stateOfMachine() {
        System.out.printf("""
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                """, remaining[0], remaining[1], remaining[2], remaining[3], remaining[4]);
    }
}

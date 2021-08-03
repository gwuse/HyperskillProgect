package cinema;

import java.util.Scanner;

public class Cinema {
    private static char[][] map;
    private static int x, y;
    private static int income, num, totalIncome, totalNum;

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        set();
        init();
        while (run) {
            System.out.println("""

                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit""");
            int key = scanner.nextInt();
            switch (key) {
                case 1:
                    show();
                    break;
                case 2:
                    sold();
                    break;
                case 3:
                    statistics();
                    break;
                case 0:
                    run = false;
                    break;
                default:
            }
        }
    }

    private static void statistics() {
        System.out.printf("""
                                
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """, num, 100.0 * num / totalNum, income, totalIncome);
    }

    private static void sold() {
        int r, xS, yS;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter a row number:");
            xS = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            yS = scanner.nextInt();
            if (xS > x - 1 || yS > y - 1 || xS < 1 || yS < 1) {
                System.out.println("Wrong input!\n");
                continue;
            }
            if (map[xS][yS] == 'B') {
                System.out.println("That ticket has already been purchased!\n");
                continue;
            }
            if ((x - 1) * (y - 1) > 60 && xS > (x - 1) / 2) {
                r = 8;
            } else {
                r = 10;
            }
            break;
        }

        System.out.println("\nTicket price: $" + r);
        num++;
        income += r;
        map[xS][yS] = 'B';
    }

    private static void set() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        x = scanner.nextInt() + 1;
        System.out.println("Enter the number of seats in each row:");
        y = scanner.nextInt() + 1;
        map = new char[x][y];
    }

    private static void init() {
        income = 0;
        num = 0;
        totalNum = (x - 1) * (y - 1);
        if ((x - 1) * (y - 1) < 60) {
            totalIncome = (x - 1) * (y - 1) * 10;
        } else {
            totalIncome = (x - 1) / 2 * (y - 1) * 10 + ((x - 1) - (x - 1) / 2) * (y - 1) * 8;
        }
        map[0][0] = ' ';
        for (int i = 1; i < x; i++) {
            map[i][0] = (char) (48 + i);
        }

        for (int i = 1; i < y; i++) {
            map[0][i] = (char) (48 + i);
        }
        for (int i = 1; i < x; i++) {
            for (int j = 1; j < y; j++) {
                map[i][j] = 'S';
            }
        }
    }

    private static void show() {
        System.out.println("\nCinema:");
        for (char[] i : map) {
            boolean t = false;
            for (char j : i) {
                if (t) {
                    System.out.print(' ');
                } else {
                    t = true;
                }
                System.out.print(j);
            }
            System.out.println();
        }
    }
}

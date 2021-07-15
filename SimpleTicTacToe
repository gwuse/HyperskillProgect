import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int nextOne = 0;

    public static void main(String[] args) {
        char[] cell = "         ".toCharArray();

        while (true) {
            display(cell);
            switch (stateTest(cell)) {
                case 0:
                case 1:
                case 2:
                    return;
            }
            next(cell);
        }
    }

    private static void next(char[] cell) {
        while (true) {
            int index;
            int a;
            int b;
            System.out.print("Enter the coordinates:");
            if (scanner.hasNextInt()) {
                a = scanner.nextInt();
                b = scanner.nextInt();
                index = (a - 1) * 3 + b - 1;
            } else {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
                continue;
            }
            if (a < 0 || a > 3 || b < 0 || b > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (cell[index] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                if (nextOne == 0) {
                    cell[index] = 'X';
                } else {
                    cell[index] = 'O';
                }
                nextOne = 1 - nextOne;
                break;
            }
        }
    }

    private static int stateTest(char[] cell) {
        int i, t;
        boolean xwin, owin;
        int winTest;

        int os = countChar(cell, 'O');
        int xs = countChar(cell, 'X');

        if (Math.abs(os - xs) > 1) {
            System.out.println("Impossible");
            return -1;
        }

        t = 0;
        xwin = false;
        owin = false;
        for (i = 0; i < 3; i++) {
            winTest = cell[t] + cell[t + 1] + cell[t + 2];
            if (winTest == 264) {
                xwin = true;
            } else if (winTest == 237) {
                owin = true;
            }
            t += 3;
        }

        for (i = 0; i < 3; i++) {
            winTest = cell[i] + cell[i + 3] + cell[i + 6];
            if (winTest == 264) {
                xwin = true;
            } else if (winTest == 237) {
                owin = true;
            }
        }

        winTest = cell[0] + cell[4] + cell[8];
        if (winTest == 264) {
            xwin = true;
        } else if (winTest == 237) {
            owin = true;
        }
        winTest = cell[2] + cell[4] + cell[6];
        if (winTest == 264) {
            xwin = true;
        } else if (winTest == 237) {
            owin = true;
        }

        if (xwin && owin) {
            System.out.println("Impossible");
            return -1;
        } else if (xwin) {
            System.out.println("X wins");
            return 0;
        } else if (owin) {
            System.out.println("O wins");
            return 1;
        }

        if (os + xs == 9) {
            System.out.println("Draw");
            return 2;
        }
        return 3;
    }

    private static void display(char[] cell) {
        System.out.println("---------");
        int count = 0;
        for (int i = 0; i < 3; i++) {
            System.out.println("| " + cell[count] + " " + cell[count + 1] + " " + cell[count + 2] + " |");
            count += 3;
        }
        System.out.println("---------");
    }

    private static int countChar(char[] cell, char c) {
        int count = 0;

        for (int i = 0; i < cell.length; i++) {
            if (cell[i] == c)
                count++;
        }

        return count;
    }
}


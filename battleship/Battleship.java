package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();
        String[][] matrix1 = player1.getMatrix();
        String[][] matrix2 = player2.getMatrix();
        String[][] foggyMatrix1 = player1.getFoggyMatrix();
        String[][] foggyMatrix2 = player2.getFoggyMatrix();

        System.out.println("Player 1, place your ships on the game field");
        Matrix.showMatrix(matrix1);
        List<Ships> ships1 = Ships.createNamesOfShips();
        Ships.createShips(ships1, matrix1);

        System.out.println("Player 2, place your ships on the game field");
        Matrix.showMatrix(matrix2);
        List<Ships> ships2 = Ships.createNamesOfShips();
        Ships.createShips(ships2, matrix2);

        boolean[] gameOver = {true};
        while (gameOver[0]) {
            Game.gameStart(1, matrix1, matrix2, foggyMatrix2, ships2, gameOver);
            if (gameOver[0]) {
                Game.gameStart(2, matrix2, matrix1, foggyMatrix1, ships1, gameOver);
            }
        }
    }
}

class Player {
    private String[][] matrix;
    private String[][] foggyMatrix;


    Player() {
        matrix = Matrix.createMatrix(10, 10, "~");
        foggyMatrix = Matrix.createMatrix(10, 10, "~");
    }

    String[][] getMatrix() {
        return matrix.clone();
    }

    String[][] getFoggyMatrix() {
        return foggyMatrix.clone();
    }
}

class Matrix {

    static String[][] createMatrix(int row, int col, String filling) {
        String[][] matrix = new String[row][col];
        for (String[] vector : matrix) {
            Arrays.fill(vector, filling);
        }
        return matrix;
    }

    static void showMatrix(String[][] matrix) {
        char startRow = 'A';
        int startCol = 1;
        System.out.print("\n  ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(startCol + " ");
            startCol++;
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.print((char) (startRow + i) + " ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void fillInMatrixFields(String[][] matrix, int[] coordinates) {
        for (int i = coordinates[0]; i <= coordinates[2]; i++) {
            for (int j = coordinates[1]; j <= coordinates[3]; j++) {
                matrix[i][j] = "O";
            }
        }
    }
}

class Ships {
    private String name;
    int cells;
    String[] coordinates;

    Ships(String name, int cells) {
        this.name = name;
        this.cells = cells;
        coordinates = new String[cells];
    }

    public String getName() {
        return name;
    }

    public int getCells() {
        return cells;
    }

    public void setCells() {
        cells -= 1;
    }

    static List<Ships> createNamesOfShips() {
        List<Ships> ships = new ArrayList<>();
        ships.add(new Ships("Aircraft Carrier", 5));
        ships.add(new Ships("Battleship", 4));
        ships.add(new Ships("Submarine", 3));
        ships.add(new Ships("Cruiser", 3));
        ships.add(new Ships("Destroyer", 2));

        return ships;
    }

    static void createShips(List<Ships> ships, String[][] matrix) {
        Scanner input = new Scanner(System.in);
        for (Ships elem : ships) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", elem.getName(), elem.getCells());
            boolean end = true;
            while (end) {
                String[] coordinatesString = input.nextLine().toUpperCase().trim().split("\\s+");
                try {
                    int[] coordinates = createCoordinates(coordinatesString);
                    if (checkLocation(coordinates)) {
                        throw new IllegalArgumentException("\nError! Wrong ship location! Try again:\n");
                    }
                    if (!checkSize(elem, coordinates)) {
                        throw new IllegalArgumentException(String.format("\nError! Wrong length of the %s! Try again:\n", elem.getName()));
                    }
                    if (!checkPlace(matrix, coordinates)) {
                        throw new IllegalArgumentException("\nError! You placed it too close to another one. Try again:\n");
                    }
                    fillShip(elem, coordinates);
                    Matrix.fillInMatrixFields(matrix, coordinates);
                    Matrix.showMatrix(matrix);
                    end = false;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("\nEnter the correct coordinates!\n");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println();

        Game.clearScreen(input);
    }

    static int[] createCoordinates(String[] coordinatesString) {
        int[] coordinates = new int[4];
        coordinates[0] = coordinatesString[0].charAt(0) - 'A';
        coordinates[1] = Integer.parseInt(coordinatesString[0].substring(1)) - 1;
        coordinates[2] = coordinatesString[1].charAt(0) - 'A';
        coordinates[3] = Integer.parseInt(coordinatesString[1].substring(1)) - 1;
        if (coordinates[0] > coordinates[2]) {
            int swap = coordinates[0];
            coordinates[0] = coordinates[2];
            coordinates[2] = swap;
        }
        if (coordinates[1] > coordinates[3]) {
            int swap = coordinates[1];
            coordinates[1] = coordinates[3];
            coordinates[3] = swap;
        }

        return coordinates;
    }

    static boolean checkLocation(int[] coordinates) {
        return coordinates[0] != coordinates[2] & coordinates[1] != coordinates[3];
    }

    static boolean checkSize(Ships ship, int[] coordinates) {
        int size;
        if (coordinates[0] != coordinates[2]) {
            size = coordinates[2] - coordinates[0] + 1;
        } else {
            size = coordinates[3] - coordinates[1] + 1;
        }
        return size == ship.getCells();
    }

    static boolean checkPlace(String[][] matrix, int[] coordinates) {
        int rowsNumber = matrix.length;
        int colsLength = matrix[0].length;
        int row1 = coordinates[0];
        int row2 = coordinates[2];
        int col1 = coordinates[1];
        int col2 = coordinates[3];

        int i = row1 == 0 ? 0 : row1 - 1;
        for (; i <= row2 + 1 & i < rowsNumber; i++) {
            int j = col1 == 0 ? 0 : col1 - 1;
            for (; j <= col2 & j < colsLength; j++) {
                if (!"~".equals(matrix[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    static void fillShip(Ships ship, int[] coordinates) {
        int k = 0;
        for (int i = coordinates[0]; i <= coordinates[2]; i++) {
            for (int j = coordinates[1]; j <= coordinates[3]; j++) {
                ship.coordinates[k] = "" + i + j;
                k++;
            }
        }
    }

    static void shipHit(List<Ships> ships, int[] shotCoordinates) {
        String coStr = "" + shotCoordinates[0] + shotCoordinates[1];
        for (Ships s : ships) {
            for (String str : s.coordinates) {
                if (coStr.equals(str)) {
                    s.setCells();
                    return;
                }
            }
        }
    }

    static boolean isSank(List<Ships> ships) {
        for (Ships s : ships) {
            if (s.getCells() == 0) {
                ships.remove(s);
                return true;
            }
        }
        return false;
    }
}

class Game {

    static void gameStart(int player, String[][] matrixMy, String[][] matrixEn, String[][] foggyMatrix, List<Ships> ships, boolean[] gameOver) {
        Scanner scanner = new Scanner(System.in);
        Matrix.showMatrix(foggyMatrix);
        System.out.print("---------------------");
        Matrix.showMatrix(matrixMy);
        System.out.printf("\nPlayer %d, it's your turn:\n\n", player);
        try {
            String shot = scanner.nextLine().toUpperCase().trim();
            int[] shotCoordinates = createShotCoordinates(shot);
            if (shotCoordinates[0] < 0 || shotCoordinates[0] > 9 ||
                    shotCoordinates[1] < 0 || shotCoordinates[1] > 9) {
                throw new IllegalArgumentException("\nError! You entered the wrong coordinates! Try again:\n");
            }
            String symbol = checkHit(matrixEn, ships, shotCoordinates);
            writeHit(foggyMatrix, shotCoordinates, symbol);
            boolean down = Ships.isSank(ships);
            if (ships.size() != 0) {
                if (!down) {
                    if ("X".equals(symbol)) {
                        System.out.println("\nYou hit a ship!");
                        Game.clearScreen(scanner);
                    } else {
                        System.out.println("\nYou missed!");
                        Game.clearScreen(scanner);
                    }
                } else {
                    System.out.println("\nYou sank a ship!\n");
                    Game.clearScreen(scanner);
                }
            } else {
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
                gameOver[0] = false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static void clearScreen(Scanner input) {
        System.out.println("Press Enter and pass the move to another player");

        String readEnter = input.nextLine();

        while (!readEnter.isEmpty()) {
            readEnter = input.nextLine();
        }

        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static int[] createShotCoordinates(String shot) {
        int[] vector = new int[2];
        vector[0] = shot.charAt(0) - 'A';
        vector[1] = Integer.parseInt(shot.substring(1)) - 1;
        return vector;
    }

    static String checkHit(String[][] matrix, List<Ships> ships, int[] shotCoordinates) {
        String symbol;
        int row = shotCoordinates[0];
        int col = shotCoordinates[1];
        if ("X".equals(matrix[row][col])) {
            symbol = "X";
        } else if ("O".equals(matrix[row][col])) {
            Ships.shipHit(ships, shotCoordinates);
            matrix[row][col] = "X";
            symbol = "X";
        } else {
            matrix[row][col] = "M";
            symbol = "M";
        }
        return symbol;
    }

    static void writeHit(String[][] foggyMatrix, int[] shotCoordinates, String symbol) {
        int row = shotCoordinates[0];
        int col = shotCoordinates[1];
        foggyMatrix[row][col] = symbol;
    }
}

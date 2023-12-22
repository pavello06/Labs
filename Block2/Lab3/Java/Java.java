import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static final int
            MIN_O = 1,
            MAX_O = 10,
            MIN_MAT = -1000000,
            MAX_MAT = 1000000,
            YES = 1,
            NO = 2;
    static Scanner scanConsole = new Scanner(System.in);
    static Scanner scanFile;
    static File file;
    public static void printTask() {
        System.out.println("Данная программа подсчитывает число строк в матрице, которые составлены из перестановок чисел от 1 до порядка матрицы.\n");
    }
    public static boolean chooseFileInput() {
        int isFileInput;
        boolean isCorrect, choose;
        isFileInput = 0;
        choose = true;
        do {
            System.out.println("Вы хотите вводить матрицу через файл? (Да - " + YES + " / Нет - " + NO + ")");
            isCorrect = true;
            try {
                isFileInput = Integer.parseInt(scanConsole.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Некорректный выбор!");
                isCorrect = false;
            }
            if (isCorrect) {
                if (isFileInput == 1)
                    choose = true;
                else if (isFileInput == 2)
                    choose = false;
                else {
                    isCorrect = false;
                    System.out.println("Некорректный выбор!");
                }
            }
        } while (!isCorrect);
        return choose;
    }
    public static boolean checkUserArea(int num, int MIN, int MAX) {
        boolean isCorrect;
        if (num < MIN || num > MAX) {
            System.out.println("Значение не попадает в диапазон!");
            isCorrect = false;
        }
        else
            isCorrect = true;
        return isCorrect;
    }
    public static int readUserO(int num, int MIN, int MAX) {
        boolean isCorrect;
        do {
            System.out.print("Введите порядок матрицы N[" + MIN + "; " + MAX + "]: ");
            isCorrect = true;
            try {
                num = Integer.parseInt(scanConsole.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isCorrect = false;
            }
            if (isCorrect)
                isCorrect = checkUserArea(num, MIN, MAX);
        } while (!isCorrect);
        return num;
    }
    public static int readUserMatrix(int num, int row, int col, int MIN, int MAX) {
        boolean isCorrect;
        do {
            System.out.print("Введите в " + (row + 1) + " строке " + (col + 1) + " столбце элемент[" + MIN + "; " + MAX + "]: ");
            isCorrect = true;
            try {
                num = Integer.parseInt(scanConsole.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isCorrect = false;
            }
            if (isCorrect)
                isCorrect = checkUserArea(num, MIN, MAX);
        } while (!isCorrect);
        return num;
    }
    public static String readPathFile() {
        boolean isCorrect;
        int len;
        String pathToFile;
        do {
            System.out.println("Введите путь к файлу с расширением .txt с матрицей, у которой разряд не должен превышать " + MAX_O + ", а её элементы должны лежать в пределе[" + MIN_MAT + ":" + MAX_MAT + "]: ");
            pathToFile = scanConsole.nextLine();
            len = pathToFile.length();
            if (len > 4 && pathToFile.substring(len - 4).equals(".txt"))
                isCorrect = true;
            else
            {
                isCorrect = false;
                System.out.println("Расширение файла не .txt!");
            }
        } while (!isCorrect);
        return pathToFile;
    }
    public static boolean isExists(String pathToFile) {
        boolean isCorrect;
        file = new File(pathToFile);
        if (file.exists())
            isCorrect = true;
        else
            isCorrect = false;
        return isCorrect;
    }
    public static boolean isAbleToReading() {
        boolean isCorrect;
        if (file.canRead())
            isCorrect = true;
        else
            isCorrect = false;
        return isCorrect;
    }
    public static boolean isAbleToWriting() {
        boolean isCorrect;
        if (file.canWrite())
            isCorrect = true;
        else
            isCorrect = false;
        return isCorrect;
    }
    public static boolean isEmpty() {
        boolean isCorrect;
        if (file.length() == 0)
            isCorrect = true;
        else
            isCorrect = false;
        return isCorrect;
    }
    public static int readOrder() {
        int order;
        boolean isCorrect;
        order = 0;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        isCorrect = true;
        try {
            order = scanFile.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Проверьте порядок матрицы!");
            isCorrect = false;
        }
        if (isCorrect)
            isCorrect = checkUserArea(order, MIN_O, MAX_O);
        if (isCorrect) {
            if (!scanFile.hasNextLine()) {
                isCorrect = false;
                System.out.println("Неправильный порядок матрицы!");
            }
        }
        scanFile.close();
        if (!isCorrect)
            order = -1;
        return order;
    }
    public static boolean isRightNums() {
        int k;
        boolean isCorrect;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        scanFile.nextLine();
        isCorrect = true;
        k = 0;
        while (isCorrect && scanFile.hasNextInt()) {
            try {
                k = scanFile.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Некорректный тип данных внутри файла!");
                isCorrect = false;
            }
            if (isCorrect)
                isCorrect = checkUserArea(k, MIN_MAT, MAX_MAT);
        }
        scanFile.close();
        return isCorrect;
    }
    public static boolean isOrdersEqual(int order) {
        int rows, cols, k;
        boolean isCorrect;
        rows = 0;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        scanFile.nextLine();
        isCorrect = true;
        while (isCorrect && scanFile.hasNext()) {
            Scanner lineScanFile = new Scanner(scanFile.nextLine());
            cols = 0;
            while (isCorrect && lineScanFile.hasNextInt()) {
                k = lineScanFile.nextInt();
                cols++;
                isCorrect = checkUserArea(cols, MIN_O, MAX_O);
            }
            if (isCorrect) {
                rows++;
                isCorrect = checkUserArea(rows, MIN_O, MAX_O);
                if (cols != order)
                    isCorrect = false;
            }
        }
        scanFile.close();
        if (isCorrect && rows != order)
            isCorrect = false;
        return isCorrect;
    }
    public static int[][] readFileMatrix(int order) {
        int row, col;
        int[][] matrix = new int[order][order];
        row = 0;
        col = 0;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        scanFile.nextLine();
        while (row < order) {
            matrix[row] = new int[order];
            while (col < order) {
                matrix[row][col] = scanFile.nextInt();
                col++;
            }
            col = 0;
            row++;
        }
        scanFile.close();
        return matrix;
    }
    public static int[][] readFile() {
        String pathToFile;
        int order;
        boolean isCorrect;
        int[][] matrix;
        order = 0;
        isCorrect = true;
        do {
            pathToFile = readPathFile();
            if (!isExists(pathToFile)) {
                isCorrect = false;
                System.out.println("Проверьте корректность ввода пути к файлу!");
            }
            if (isCorrect && !isAbleToReading()) {
                isCorrect = false;
                System.out.println("Файл закрыт для чтения!");
            }
            if (isCorrect && !isAbleToWriting()) {
                isCorrect = false;
                System.out.println("Файл закрыт для записи!");
            }
            if (isCorrect && isEmpty()) {
                isCorrect = false;
                System.out.println("Файл пуст!");
            }
            if (isCorrect) {
                order = readOrder();
                if (order == -1)
                    isCorrect = false;
            }
            if (isCorrect && !isRightNums())
                isCorrect = false;
            if (isCorrect && !isOrdersEqual(order)) {
                isCorrect = false;
                System.out.println("Значения порядков не равны!");
            }
        } while (!isCorrect);
        matrix = readFileMatrix(order);
        return matrix;
    }
    public static void addAnswerToFile(int countCorrectRows) {
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write("\nКоличество строк, имеющих перестановки элементов от 1 до N: " + countCorrectRows);
            writer.close();
        } catch (IOException e) {}
    }
    public static int[][] readConsole() {
        int order;
        order = 0;
        order = readUserO(order, MIN_O, MAX_O);
        int[][] matrix = new int[order][order];
        for (int row = 0; row < order; row++)
            for (int col = 0; col < order; col++)
                matrix[row][col] = readUserMatrix(matrix[row][col], row, col, MIN_MAT, MAX_MAT);
        return matrix;
    }
    public static int[][] sort(int[][] matrix) {
        int order;
        int previousMaxIndex, buf;
        order = matrix.length;
        previousMaxIndex = order - 1;
        for (int row = 0; row < order; row++)
            for (int i = 0; i < order; i++)
                for (int j = 0; j < previousMaxIndex - i; j++)
                    if (matrix[row][j] > matrix[row][j + 1]) {
                        buf = matrix[row][j];
                        matrix[row][j] = matrix[row][j + 1];
                        matrix[row][j + 1] = buf;
                    }
        return matrix;
    }
    public static int calcCorrectRows(int[][] matrix) {
        int i, count, order;
        boolean isCorrect;
        isCorrect = true;
        order = matrix.length;
        count = 0;
        for (int row = 0; row < order; row++) {
            i = 0;
            while (isCorrect && i < order) {
                if (matrix[row][i] != i + 1)
                    isCorrect = false;
                i++;
            }
            if (isCorrect)
                count++;
            isCorrect = true;
        }
        return count;
    }
    public static int getResultFromFile() {
        int[][] matrix;
        int countCorrectRows;
        matrix = readFile();
        matrix = sort(matrix);
        countCorrectRows = calcCorrectRows(matrix);
        addAnswerToFile(countCorrectRows);
        return countCorrectRows;
    }
    public static int getResultFromConsole() {
        int[][] matrix;
        int countCorrectRows;
        matrix = readConsole();
        matrix = sort(matrix);
        countCorrectRows = calcCorrectRows(matrix);
        return countCorrectRows;
    }
    public static void printResult(int count) {
        System.out.println("\nКоличество строк, имеющих перестановки элементов от 1 до N: " + count);
    }
    public static void main(String[] args) {
        int countCorrectRows;
        countCorrectRows = 0;
        printTask();
        if (chooseFileInput())
            countCorrectRows = getResultFromFile();
        else
            countCorrectRows = getResultFromConsole();
        scanConsole.close();
        printResult(countCorrectRows);
    }
}

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static final int
            MIN_O = 1,
            MAX_O = 10,
            MIN_MAT = 1,
            MAX_MAT = 1000000,
            YES = 1,
            NO = 2;
    static Scanner scanConsole = new Scanner(System.in);
    static Scanner scanFile;
    static File file;
    public static void printTask() {
        System.out.println("Данная программа подсчитывает число повторяющихся элементов в матрице.\n");
    }
    public static boolean chooseFileIOput() {
        int isFileInput;
        boolean isCorrect, choose;
        isFileInput = 0;
        choose = true;
        do {
            isCorrect = true;
            try {
                isFileInput = Integer.parseInt(scanConsole.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Некорректный выбор!");
                System.out.println("Повторите попытку: ");
                isCorrect = false;
            }
            if (isCorrect) {
                if (isFileInput == YES)
                    choose = true;
                else if (isFileInput == NO)
                    choose = false;
                else {
                    isCorrect = false;
                    System.out.println("Некорректный выбор!");
                    System.out.println("Повторите попытку: ");
                }
            }
        } while (!isCorrect);
        return choose;
    }
    public static boolean checkArea(int num, final int MIN, final int MAX) {
        boolean isCorrect;
        isCorrect = true;
        if (num < MIN || num > MAX) {
            System.out.println("Значение не попадает в диапазон!");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static String readPathFile() {
        boolean isCorrect;
        int len;
        String pathToFile;
        isCorrect = false;
        len = 0;
        pathToFile = "";
        do {
            System.out.println("Введите путь к файлу с расширением .txt с матрицей, у которой разряд не должен превышать " + MAX_O + ", а её натуральные элементы должны лежать в пределе[" + MIN_MAT + ":" + MAX_MAT + "]: ");
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
        isCorrect = false;
        file = new File(pathToFile);
        if (file.exists())
            isCorrect = true;
        return isCorrect;
    }
    public static boolean isAbleToReading() {
        boolean isCorrect;
        isCorrect = false;
        if (file.canRead())
            isCorrect = true;
        return isCorrect;
    }
    public static boolean isAbleToWriting() {
        boolean isCorrect;
        isCorrect = false;
        if (file.canWrite())
            isCorrect = true;
        return isCorrect;
    }
    public static boolean isEmpty() {
        boolean isCorrect;
        isCorrect = false;
        if (file.length() == 0)
            isCorrect = true;
        return isCorrect;
    }
    public static boolean isRightFileNums() {
        int k;
        boolean isCorrect;
        isCorrect = true;
        k = 0;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        try {
            k = scanFile.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Некорректный тип данных внутри файла!");
            isCorrect = false;
        }
        if (isCorrect) {
            if (!scanFile.hasNextLine()) {
                isCorrect = false;
                System.out.println("Неправильный порядок матрицы!");
            }
        }
        if (isCorrect)
            isCorrect = checkArea(k, MIN_O, MAX_O);
        scanFile.nextLine();
        while (isCorrect && scanFile.hasNextInt()) {
            try {
                k = scanFile.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Некорректный тип данных внутри файла!");
                isCorrect = false;
            }
            if (isCorrect)
                isCorrect = checkArea(k, MIN_MAT, MAX_MAT);
        }
        scanFile.close();
        return isCorrect;
    }
    public static boolean isOrdersEqual() {
        int order, rows, cols, k;
        boolean isCorrect;
        order = 0;
        rows = 0;
        k = 0;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        order = scanFile.nextInt();
        scanFile.nextLine();
        isCorrect = true;
        while (isCorrect && scanFile.hasNext()) {
            Scanner lineScanFile = new Scanner(scanFile.nextLine());
            cols = 0;
            while (isCorrect && lineScanFile.hasNextInt()) {
                k = lineScanFile.nextInt();
                cols++;
                isCorrect = checkArea(cols, MIN_O, MAX_O);
            }
            if (isCorrect) {
                rows++;
                isCorrect = checkArea(rows, MIN_O, MAX_O);
                if (cols != order)
                    isCorrect = false;
            }
        }
        scanFile.close();
        if (isCorrect && rows != order)
            isCorrect = false;
        return isCorrect;
    }
    public static void getFileNormalReading() {
        boolean isCorrect;
        String pathToFile;
        int order;
        order = 0;
        pathToFile = "";
        do {
            isCorrect = true;
            pathToFile = readPathFile();
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
            if (isCorrect && !isRightFileNums()) {
                isCorrect = false;
                System.out.println("Некорректный тип данных внутри файла!");
            }
            if (isCorrect && !isOrdersEqual()) {
                isCorrect = false;
                System.out.println("Значения порядков не равны!");
            }
        } while (!isCorrect);

    }
    public static void getFileNormalWriting() {
        boolean isCorrect;
        String pathToFile;
        pathToFile = "";
        do {
            isCorrect = true;
            pathToFile = readPathFile();
            if (!isExists(pathToFile)) {
                isCorrect = false;
                System.out.println("Проверьте корректность ввода пути к файлу!");
            }
            if (isCorrect && !isAbleToWriting()) {
                isCorrect = false;
                System.out.println("Файл закрыт для записи!");
            }
        } while (!isCorrect);
    }
    public static int readFileOrder() {
        int order;
        order = 0;
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        order = scanFile.nextInt();
        scanFile.close();
        return order;
    }
    public static int[][] readFileMatrix(int order) {
        int[][] matrix;
        matrix = new int[order][order];
        try {scanFile = new Scanner(file);} catch (FileNotFoundException e) {}
        scanFile.nextLine();
        for (int row = 0; row < order; row++)
            for (int col = 0; col < order; col++)
                matrix[row][col] = scanFile.nextInt();
        scanFile.close();
        return matrix;
    }
    public static int readConsoleOrder() {
        int order;
        boolean isCorrect;
        order = 0;
        do {
            System.out.print("Введите порядок матрицы N[" + MIN_O + "; " + MAX_O + "]: ");
            isCorrect = true;
            try {
                order = Integer.parseInt(scanConsole.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isCorrect = false;
            }
            if (isCorrect)
                isCorrect = checkArea(order, MIN_O, MAX_O);
        } while (!isCorrect);
        return order;
    }
    public static int[][] readConsoleMatrix(int order) {
        int[][] matrix;
        boolean isCorrect;
        matrix = new int[order][order];
        for (int row = 0; row < order; row++)
            for (int col = 0; col < order; col++)
                do {
                    System.out.print("Введите в " + (row + 1) + " строке " + (col + 1) + " столбце натуральный  элемент[" + MIN_MAT + "; " + MAX_MAT + "]: ");
                    isCorrect = true;
                    try {
                        matrix[row][col] = Integer.parseInt(scanConsole.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Проверьте корректность ввода данных!");
                        isCorrect = false;
                    }
                    if (isCorrect)
                        isCorrect = checkArea(matrix[row][col], MIN_MAT, MAX_MAT);
                } while (!isCorrect);
        return matrix;
    }
    public static int[][] readMatrix() {
        int[][] matrix;
        int order;
        System.out.println("Вы хотите вводить матрицу через файл? (Да - " + YES + " / Нет - " + NO + ")");
        if (chooseFileIOput()) {
            getFileNormalReading();
            order = readFileOrder();
            matrix = readFileMatrix(order);
        }
        else {
            order = readConsoleOrder();
            matrix = readConsoleMatrix(order);
        }
        return matrix;
    }
    public static int[] fillArr(int[][] matrix, int order) {
        int[] arr;
        int arrLen;
        int i;
        i = 0;
        arrLen = order * order;
        arr = new int[arrLen];
        for (int row = 0; row < order; row++)
            for (int col = 0; col < order; col++)
            {
                arr[i] = matrix[row][col];
                i++;
            }
        return arr;
    }
    public static void sortArr(int[] arr, int arrLen) {
        int maxIndex, buf;
        maxIndex = arrLen - 1;
        for (int i = 0; i < arrLen; i++)
            for (int j = 0; j < maxIndex - i; j++)
                if (arr[j] > arr[j + 1]) {
                    buf = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = buf;
                }
    }
    public static int[][] findSame(int[] arr, int arrLen) {
        int[][] resMatrix;
        int j, count, maxIndex;
        count = 1;
        j = 0;
        maxIndex = arrLen - 1;
        resMatrix = new int[arrLen][arrLen];
        for (int i = 0; i < maxIndex; i++)
            if (arr[i] == arr[i + 1])
                count++;
            else {
                resMatrix[j][0] = arr[i];
                resMatrix[j][1] = count;
                j++;
                count = 1;
            }
        resMatrix[j][0] = arr[arrLen - 1];
        resMatrix[j][1] = count;
        resMatrix[j + 1][1] = 0;
        return resMatrix;
    }
    public static void printConsoleResult(int[][] resMatrix, int arrLen) {
        int i;
        i = 0;
        while (i < arrLen && resMatrix[i][1] != 0)
        {
            System.out.print("\nЧисло " + resMatrix[i][0] + " встречается " + resMatrix[i][1] + " раз(а).");
            i++;
        }
    }
    public static void printFileResult(int[][] resMatrix, int arrLen) {
        int i;
        i = 0;
        try {
            FileWriter writer = new FileWriter(file, true);
            while (i < arrLen && resMatrix[i][1] != 0) {
                writer.write("\nЧисло " + resMatrix[i][0] + " встречается " + resMatrix[i][1] + " раз(а).");
                i++;
            }
            writer.close();
        } catch (IOException e) {}
    }
    public static void printResult(int[][] resMatrix, int arrLen) {
        System.out.println("Вы хотите выводить матрицу через файл? (Да - " + YES + " / Нет - " + NO + ")");
        if (chooseFileIOput()) {
            getFileNormalWriting();
            printFileResult(resMatrix, arrLen);
        }
        else
            printConsoleResult(resMatrix, arrLen);
    }
    public static void main(String[] args) {
        int[][] matrix;
        int order;
        int[] arr;
        int arrLen;
        int[][] resMatrix;
        printTask();
        matrix = readMatrix();
        order = matrix.length;
        arr = fillArr(matrix, order);
        arrLen = arr.length;
        sortArr(arr, arrLen);
        resMatrix = findSame(arr, arrLen);
        printResult(resMatrix, arrLen);
        scanConsole.close();
    }
}

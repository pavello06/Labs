import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static final int
            OPTION1 = 1,
            OPTION2 = 2,
            MIN_LEN = 1,
            MAX_LEN = 100;
    public static final double
            FACTOR = 1.247;
    static Scanner scanConsole = new Scanner(System.in);
    static File file;
    public static void printTask() {
        System.out.print("Данная программа находит элементы в двух строках по одному из критериев.\n\n");
    }
    public static boolean checkStringLen(String str) {
        boolean isCorrect;
        isCorrect = true;
        if (str.length() < MIN_LEN || str.length() > MAX_LEN)
        {
            System.out.print("Длина строки не попадает в диапазон!\n");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static int chooseOption() {
        int iOption;
        String sOption;
        boolean isCorrect;
        iOption = 0;
        sOption = "";
        do {
            isCorrect = true;
            sOption = scanConsole.nextLine();
            try {
                iOption = Integer.parseInt(sOption);
            } catch (NumberFormatException e) {
                System.out.print("Некорректный выбор!\n");
                System.out.print("Повторите попытку: \n");
                isCorrect = false;
            }
            if (isCorrect && iOption != OPTION1 && iOption != OPTION2) {
                isCorrect = false;
                System.out.print("Некорректный выбор!\n");
                System.out.print("Повторите попытку: \n");
            }
        } while (!isCorrect);
        return iOption;
    }
    public static String readPathFile() {
        boolean isCorrect;
        String pathToFile;
        pathToFile = "";
        do {
            isCorrect = true;
            System.out.println("Введите путь к файлу с расширением.txt с двумя строками, с длинами[" + MIN_LEN + "; " + MAX_LEN + "]: ");
            pathToFile = scanConsole.nextLine();
            if (pathToFile.length() < 5 || pathToFile.charAt(pathToFile.length() - 4) != '.' || pathToFile.charAt(pathToFile.length() - 3) != 't' || pathToFile.charAt(pathToFile.length() - 2) != 'x' || pathToFile.charAt(pathToFile.length() - 1) != 't') {
                isCorrect = false;
                System.out.println("Расширение файла не .txt!");
            }
        } while (!isCorrect);
        return pathToFile;
    }
    public static boolean isNotExists(String pathToFile) {
        boolean isRight;
        isRight = true;
        file = new File(pathToFile);
        if (file.exists())
            isRight = false;
        return isRight;
    }
    public static boolean isNotAbleToReading() {
        boolean isRight;
        isRight = true;
        if (file.canRead())
            isRight = false;
        return isRight;
    }
    public static boolean isNotAbleToWriting() {
        boolean isRight;
        isRight = true;
        if (file.canWrite())
            isRight = false;
        return isRight;
    }
    public static boolean isEmpty() {
        boolean isRight;
        isRight = false;
        if (file.length() == 0)
            isRight = true;
        return isRight;
    }
    public static boolean isNotRightCountStrings() {
        boolean isRight;
        isRight = false;
        try(Scanner scanFile = new Scanner(file)) {
            scanFile.nextLine();
            if (!scanFile.hasNext())
                isRight = true;
            scanFile.nextLine();
            if (scanFile.hasNext())
                isRight = true;
        } catch (Exception e) {}
        return isRight;
    }
    public static boolean isNotCorrectStrings() {
        String str;
        boolean isRight;
        str = "";
        isRight = false;
        try(Scanner scanFile = new Scanner(file)) {
            str = scanFile.nextLine();
            isRight = checkStringLen(str);
            if (isRight) {
                str = scanFile.nextLine();
                isRight = checkStringLen(str);
            }
        } catch (Exception e) {}
        return !isRight;
    }
    public static void getFileNormalReading() {
        boolean isCorrect;
        String pathToFile;
        pathToFile = "";
        do {
            isCorrect = true;
            pathToFile = readPathFile();
            if (isNotExists(pathToFile)) {
                isCorrect = false;
                System.out.print("Проверьте корректность ввода пути к файлу!\n");
            }
            if (isCorrect && isNotAbleToReading()) {
                isCorrect = false;
                System.out.print("Файл закрыт для чтения!\n");
            }
            if (isCorrect && isEmpty()) {
                isCorrect = false;
                System.out.print("Файл пуст!\n");
            }
            if (isCorrect && isNotRightCountStrings()) {
                isCorrect = false;
                System.out.print("Количество строк в файле не две!\n");
            }
            if (isCorrect && isNotCorrectStrings())
                isCorrect = false;
        } while (!isCorrect);
    }
    public static void getFileNormalWriting() {
        boolean isCorrect;
        String pathToFile;
        pathToFile = "";
        do {
            isCorrect = true;
            pathToFile = readPathFile();
            if (isNotExists(pathToFile)) {
                isCorrect = false;
                System.out.println("Проверьте корректность ввода пути к файлу!");
            }
            if (isCorrect && isNotAbleToWriting()) {
                isCorrect = false;
                System.out.print("Файл закрыт для записи!\n");
            }
        } while (!isCorrect);
    }
    public static String readFileString(Scanner scanFile) {
        String str;
        str = "";
        str = scanFile.nextLine();
        return str;
    }
    public static String readConsoleString(int num) {
        String str;
        boolean isCorrect;
        str = "";
        do {
            System.out.println("Введите строку номер " + num + ", с длиной[" + MIN_LEN + ";" + MAX_LEN + "]: ");
            str = scanConsole.nextLine();
            isCorrect = checkStringLen(str);
        } while (!isCorrect);
        return str;
    }
    public static String[] readStrings() {
        String[] twoStrings = new String[2];
        System.out.print("Вы хотите: \n");
        System.out.print("Вводить матрицу через файл - " + OPTION1 + "\n");
        System.out.print("Вводить матрицу через консоль - " + OPTION2 + "\n");
        if (chooseOption() == OPTION1) {
            getFileNormalReading();
            try(Scanner scanFile = new Scanner(file)) {
                twoStrings[0] = readFileString(scanFile);
                twoStrings[1] = readFileString(scanFile);
            } catch (Exception e) {}
        }
        else {
            twoStrings[0] = readConsoleString(1);
            twoStrings[1] = readConsoleString(2);
        }
        return twoStrings;
    }
    public static char[] fillOneAStr(String str) {
        int i;
        char[] aStr = new char[str.length()];
        for (i = 0; i < str.length(); i++)
            aStr[i] = str.charAt(i);
        return aStr;
    }
    public static void sortOneAStr(char[] aStr) {
        double step;
        int i, iStep;
        char buf;
        step = aStr.length - 1;
        while (step >= 1) {
            iStep = (int) step;
            i = 0;
            while (step + i < aStr.length ) {
                if ((int) aStr[i] > (int) aStr[i + iStep]) {
                    buf = aStr[i];
                    aStr[i] = aStr[i + iStep];
                    aStr[i + iStep] = buf;
                }
                i++;
            }
            step /= FACTOR;
        }
    }
    public static void sortAStrs(char[] aStr1, char[] aStr2) {
        sortOneAStr(aStr1);
        sortOneAStr(aStr2);
    }
    public static char[] makeUniqueAStr(char[] aStr1, char[] aStr2) {
        char[] uniqueAStr;
        char[] bufArr;
        int i, j, maxIndex;
        bufArr = new char[aStr1.length + aStr2.length];
        j = 0;
        maxIndex = aStr1.length - 1;
        for (i = 0; i < maxIndex; i++)
            if (aStr1[i] != aStr1[i + 1]) {
                bufArr[j] = aStr1[i];
                j++;
            }
        bufArr[j] = aStr1[maxIndex];
        j++;
        maxIndex = aStr2.length - 1;
        for (i = 0; i < maxIndex; i++)
            if (aStr2[i] != aStr2[i + 1]) {
                bufArr[j] = aStr2[i];
                j++;
            }
        bufArr[j] = aStr2[maxIndex];
        uniqueAStr = new char[j + 1];
        for (i = 0; i < uniqueAStr.length; i++)
            uniqueAStr[i] = bufArr[i];
        return uniqueAStr;
    }
    public static char[] findSame(char[] uniqueAStr) {
        int i, j, maxIndex;
        char[] resAStr;
        j = 0;
        char[] bufArr;
        bufArr = new char[uniqueAStr.length];
        maxIndex = uniqueAStr.length - 1;
        for (i = 0; i < maxIndex; i++)
            if (uniqueAStr[i] == uniqueAStr[i + 1]) {
                bufArr[j] = uniqueAStr[i];
                j++;
            }
        resAStr = new char[j];
        for (i = 0; i < j; i++)
            resAStr[i] = bufArr[i];
        return resAStr;
    }
    public static char[] findUnique(char[] uniqueAStr) {
        int i, j, maxIndex;
        char[] resAStr;
        char[] bufArr;
        j = 0;
        bufArr = new char[uniqueAStr.length];
        maxIndex = uniqueAStr.length - 1;
        i = 0;
        while (i < maxIndex) {
            if (uniqueAStr[i] != uniqueAStr[i + 1]) {
                bufArr[j] = uniqueAStr[i];
                j++;
            }
            else
                i++;
            i++;
        }
        if (uniqueAStr[maxIndex] != uniqueAStr[maxIndex - 1]) {
            bufArr[j] = uniqueAStr[maxIndex];
            resAStr = new char[j + 1];
        }
        else
            resAStr = new char[j];
        for (i = 0; i < resAStr.length; i++)
            resAStr[i] = bufArr[i];
        return resAStr;
    }
    public static int chooseAction() {
        System.out.print("Вы хотите: \n");
        System.out.print("Найти одинаковые символы в обеих строках - " + OPTION1 + "\n");
        System.out.print("Найти уникальные символы в обеих строках - " + OPTION2 + "\n");
        return chooseOption();
    }
    public static void printConsoleResult(char[] resAStr) {
        int i;
        i = 0;
        System.out.print("\nЭлементы, удовлетворяющие условию: ");
        if (resAStr[0] == '\0')
            System.out.print("элементов, удовлетворяющих условию, нет!");
        else
            while (i < resAStr.length) {
                System.out.print("\'" + resAStr[i] + "\'; ");
                i++;
            }
    }
    public static void printFileResult(char[] resAStr) {
        int i;
        i = 0;
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write("\nЭлементы, удовлетворяющие условию: ");
            if (resAStr[0] == '\0')
                writer.write("элементов, удовлетворяющих условию, нет!");
            else
                while (i < resAStr.length) {
                    writer.write("\'" + resAStr[i] + "\'; ");
                    i++;
                }
            writer.close();
        } catch (IOException e) {}
    }
    public static void printResult(char[] resAStr) {
        System.out.print("Вы хотите: \n");
        System.out.print("Выводить строки через файл - " + OPTION1 + "\n");
        System.out.print("Выводить строки через консоль - " + OPTION2 + "\n");
        if (chooseOption() == OPTION1) {
            getFileNormalWriting();
            printFileResult(resAStr);
        }
        else
            printConsoleResult(resAStr);
    }
    public static void main(String[] args) {
        String[] twoStrings;
        char[] aStr1;
        char[] aStr2;
        char[] uniqueAStr;
        char[] resAStr;
        int action;
        printTask();
        twoStrings = readStrings();
        aStr1 = fillOneAStr(twoStrings[0]);
        aStr2 = fillOneAStr(twoStrings[1]);
        sortAStrs(aStr1, aStr2);
        uniqueAStr = makeUniqueAStr(aStr1, aStr2);
        sortOneAStr(uniqueAStr);
        action = chooseAction();
        if (action == OPTION1)
            resAStr = findSame(uniqueAStr);
        else
            resAStr = findUnique(uniqueAStr);
        printResult(resAStr);
        scanConsole.close();
    }
}

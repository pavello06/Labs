import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
public class Main {
    public static final int
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
        if (str.length() < MIN_LEN || str.length() > MAX_LEN) {
            System.out.print("Длина строки не попадает в диапазон!\n");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static int chooseOption(int count) {
        String sOption;
        int iOption, i;
        boolean isCorrect, isNotCorrectChoise;
        iOption = 0;
        isNotCorrectChoise = false;
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
            if (isCorrect) {
                isNotCorrectChoise = true;
                i = 0;
                while (isNotCorrectChoise && i < count) {
                    if (iOption == i + 1)
                        isNotCorrectChoise = false;
                    i++;
                }
            }
            if (isNotCorrectChoise) {
                System.out.print("Некорректный выбор!\n");
                System.out.print("Повторите попытку: \n");
                isCorrect = false;
            }
        } while (!isCorrect);
        return iOption;
    }
    public static void readPathFile() {
        String pathToFile;
        boolean isCorrect;
        do {
            isCorrect = true;
            System.out.print("Введите путь к файлу с расширением.txt с двумя строками, с длинами[" + MIN_LEN + "; " + MAX_LEN + "]: \n");
            pathToFile = scanConsole.nextLine();
            if (pathToFile.length() < 5 || pathToFile.charAt(pathToFile.length() - 4) != '.' || pathToFile.charAt(pathToFile.length() - 3) != 't' || pathToFile.charAt(pathToFile.length() - 2) != 'x' || pathToFile.charAt(pathToFile.length() - 1) != 't') {
                isCorrect = false;
                System.out.print("Расширение файла не .txt!\n");
            }
        } while (!isCorrect);
        file = new File(pathToFile);
    }
    public static boolean isNotExists() {
        return !file.exists();
    }
    public static boolean isNotAbleToReading() {
        return !file.canRead();
    }
    public static boolean isNotAbleToWriting() {
        return !file.canWrite();
    }
    public static boolean isEmpty() {
        return file.length() == 0;
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
        do {
            isCorrect = true;
            readPathFile();
            if (isNotExists()) {
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
        do {
            isCorrect = true;
            readPathFile();
            if (isNotExists()) {
                isCorrect = false;
                System.out.print("Проверьте корректность ввода пути к файлу!\n");
            }
            if (isCorrect && isNotAbleToWriting()) {
                isCorrect = false;
                System.out.print("Файл закрыт для записи!\n");
            }
        } while (!isCorrect);
    }
    public static String readFileString(Scanner scanFile) {
        String str;
        str = scanFile.nextLine();
        return str;
    }
    public static String readConsoleString(int num) {
        String str;
        boolean isCorrect;
        do {
            System.out.print("Введите строку номер " + num + ", с длиной[" + MIN_LEN + ";" + MAX_LEN + "]: \n");
            str = scanConsole.nextLine();
            isCorrect = checkStringLen(str);
        } while (!isCorrect);
        return str;
    }
    public static String[] readStrings() {
        String[] twoStrings = new String[2];
        int option;
        System.out.print("Вы хотите: \n");
        System.out.print("Вводить матрицу через файл - 1\n");
        System.out.print("Вводить матрицу через консоль - 2\n");
        option = chooseOption(2);
        if (option == 1) {
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
        char[] aStr = new char[str.length()];
        int i;
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
            for (i = 0; step + i < aStr.length; i++)
                if ((int) aStr[i] > (int) aStr[i + iStep]) {
                    buf = aStr[i];
                    aStr[i] = aStr[i + iStep];
                    aStr[i + iStep] = buf;
                }
            step /= FACTOR;
        }
    }
    public static void sortAStrs(char[] aStr1, char[] aStr2) {
        sortOneAStr(aStr1);
        sortOneAStr(aStr2);
    }
    public static int plusAStr(char[] combAStr, char[] aStr, int j) {
        int i, maxIndex;
        maxIndex = aStr.length - 1;
        for (i = 0; i < maxIndex; i++)
            if (aStr[i] != aStr[i + 1]) {
                combAStr[j] = aStr[i];
                j++;
            }
        combAStr[j] = aStr[maxIndex];
        return ++j;
    }
    public static char[] makeCombSameAStr(char[] aStr1, char[] aStr2) {
        char[] combAStr;
        char[] bufArr;
        int i, j;
        bufArr = new char[aStr1.length + aStr2.length];
        j = 0;
        j = plusAStr(bufArr, aStr1, j);
        j = plusAStr(bufArr, aStr2, j);
        combAStr = new char[j];
        for (i = 0; i < combAStr.length; i++)
            combAStr[i] = bufArr[i];
        return combAStr;
    }
    public static char[] makeCombUniqueAStr(char[] aStr1, char[] aStr2, char[] aStr3) {
        char[] combAStr;
        char[] bufArr;
        int i, j;
        bufArr = new char[aStr1.length + aStr2.length + aStr3.length];
        j = 0;
        j = plusAStr(bufArr, aStr1, j);
        j = plusAStr(bufArr, aStr2, j);
        j = plusAStr(bufArr, aStr3, j);
        combAStr = new char[j];
        for (i = 0; i < combAStr.length; i++)
            combAStr[i] = bufArr[i];
        return combAStr;
    }
    public static char[] findSame(char[] combAStr) {
        char[] resAStr;
        char[] bufArr;
        int i, j, maxIndex;
        bufArr = new char[combAStr.length];
        i = 0;
        j = 0;
        maxIndex = combAStr.length - 1;
        while (i < maxIndex) {
            if (combAStr[i] == combAStr[i + 1]) {
                bufArr[j] = combAStr[i];
                j++;
                i++;
            }
            i++;
        }
        if (j == 0)
            resAStr = new char[1];
        else
            resAStr = new char[j];
        for (i = 0; i < resAStr.length; i++)
            resAStr[i] = bufArr[i];
        return resAStr;
    }
    public static char[] findUnique(char[] combAStr) {
        char[] resAStr;
        char[] bufArr;
        int i, j, maxIndex;
        bufArr = new char[combAStr.length];
        i = 0;
        j = 0;
        maxIndex = combAStr.length - 1;
        while (i < maxIndex) {
            if (combAStr[i] == combAStr[i + 1])
                if (i == maxIndex - 1 || combAStr[i] != combAStr[i + 2]) {
                    bufArr[j] = combAStr[i];
                    j++;
                    i++;
                }
                else
                    i += 2;
            i++;
        }
        if (j == 0)
            resAStr = new char[1];
        else
            resAStr = new char[j];
        for (i = 0; i < resAStr.length; i++)
            resAStr[i] = bufArr[i];
        return resAStr;
    }
    public static int chooseAction() {
        System.out.print("Вы хотите: \n");
        System.out.print("Найти одинаковые символы в обеих строках - 1\n");
        System.out.print("Найти уникальные символы в первой строке - 2\n");
        System.out.print("Найти уникальные символы во второй строке - 3\n");
        return chooseOption(3);
    }
    public static void printConsoleResult(char[] resAStr) {
        int i;
        System.out.print("\nЭлементы, удовлетворяющие условию: ");
        if (resAStr[0] == '\0')
            System.out.print("элементов, удовлетворяющих условию, нет!");
        else
            for (i = 0; i < resAStr.length; i++)
                System.out.print("'" + resAStr[i] + "'; ");
    }
    public static void printFileResult(char[] resAStr) {
        int i;
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write("\nЭлементы, удовлетворяющие условию: ");
            if (resAStr[0] == '\0')
                writer.write("элементов, удовлетворяющих условию, нет!");
            else
                for (i = 0; i < resAStr.length; i++)
                    writer.write("'" + resAStr[i] + "'; ");
        } catch (Exception e) {}
    }
    public static void printResult(char[] resAStr) {
        int option;
        System.out.print("Вы хотите: \n");
        System.out.print("Выводить строки через файл - 1\n");
        System.out.print("Выводить строки через консоль - 2\n");
        option = chooseOption(2);
        if (option == 1) {
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
        char[] combAStr;
        char[] resAStr;
        int action;
        printTask();
        twoStrings = readStrings();
        aStr1 = fillOneAStr(twoStrings[0]);
        aStr2 = fillOneAStr(twoStrings[1]);
        sortAStrs(aStr1, aStr2);
        action = chooseAction();
        if (action == 1) {
            combAStr = makeCombSameAStr(aStr1, aStr2);
            sortOneAStr(combAStr);
            resAStr = findSame(combAStr);
        }
        else if (action == 2) {
            combAStr = makeCombUniqueAStr(aStr1, aStr2, aStr1);
            sortOneAStr(combAStr);
            resAStr = findUnique(combAStr);
        }
        else {
            combAStr = makeCombUniqueAStr(aStr1, aStr2, aStr2);
            sortOneAStr(combAStr);
            resAStr = findUnique(combAStr);
        }
        printResult(resAStr);
        scanConsole.close();
    }
}

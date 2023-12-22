import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
public class Main {
    public enum ErrorsCode {

        CORRECT,
        INCORRECT_RANGE,
        INCORRECT_NUM,
        INCORRECT_CHOICE,
        IS_NOT_TXT,
        IS_NOT_EXIST,
        IS_NOT_READABLE,
        IS_NOT_WRITEABLE,
        INCORRECT_ARR_LEN,
        EXTRA_DATA,
    }
    public static final int
            MIN_A = -1000000,
            MAX_A = 1000000,
            MIN_L = 1,
            MAX_L = 100;
    public static final String[]
            ERRORS = { "",
                       "Значение не попадает в диапазон!",
                       "Введено не число!!",
                       "Некорректный выбор!",
                       "Расширение файла не .txt!",
                       "Проверьте корректность ввода пути к файлу!",
                       "Файл закрыт для чтения!",
                       "Файл закрыт для записи!",
                       "Неправильная длина массива!",
                       "Лишние данные в файле!" };
    static Scanner scanConsole = new Scanner(System.in);
    public static void printTask() {
        System.out.print("Данная программа реализует сортировку двухпутевыми вставками.\n");
    }
    public static void printError(ErrorsCode error) {
        System.err.print(ERRORS[error.ordinal()] + "\nПовторите попытку: \n");
    }
    public static ErrorsCode isCorrectRange(int num, final int MIN, final int MAX) {
        ErrorsCode error;
        error = ErrorsCode.CORRECT;
        if (num < MIN || num > MAX)
            error = ErrorsCode.INCORRECT_RANGE;
        return error;
    }
    public static int chooseOption(int quantity) {
        ErrorsCode error;
        String sOption;
        int iOption;
        iOption = 1;
        do {
            error = ErrorsCode.CORRECT;
            sOption = scanConsole.nextLine();
            try {
                iOption = Integer.parseInt(sOption);
            } catch (NumberFormatException e) {
                error = ErrorsCode.INCORRECT_CHOICE;
            }
            if (error == ErrorsCode.CORRECT && (iOption < 1 || iOption > quantity))
                error = ErrorsCode.INCORRECT_CHOICE;
            if (error != ErrorsCode.CORRECT)
                printError(error);
        } while (error != ErrorsCode.CORRECT);
        return iOption;
    }
    public static String getPartStr(String str, int posStart, int posEnd) {
        String partStr;
        int i;
        partStr = "";
        for (i = posStart; i <= posEnd; i++)
            partStr = partStr + str.charAt(i);
        return partStr;
    }
    public static ErrorsCode isFileTXT(String pathToFile) {
        ErrorsCode error;
        error = ErrorsCode.CORRECT;
        if (pathToFile.length() < 5 || !getPartStr(pathToFile, pathToFile.length() - 4, pathToFile.length() - 1).equals(".txt"))
            error = ErrorsCode.IS_NOT_TXT;
        return error;
    }
    public static ErrorsCode isExist(File file) {
        ErrorsCode error;
        error = ErrorsCode.CORRECT;
        if (!file.exists())
            error = ErrorsCode.IS_NOT_EXIST;
        return error;
    }
    public static ErrorsCode isReadable(File file) {
        ErrorsCode error;
        error = ErrorsCode.CORRECT;
        try(Scanner scanFile = new Scanner(file)) {
        } catch (Exception e) {
            error = ErrorsCode.IS_NOT_READABLE;
        }
        return error;
    }
    public static ErrorsCode isWriteable(File file) {
        ErrorsCode error;
        error = ErrorsCode.CORRECT;
        if (!file.canWrite())
            error = ErrorsCode.IS_NOT_WRITEABLE;
        return error;
    }
    public static File getFileNormalReading() {
        File file;
        ErrorsCode error;
        String pathToFile;
        do {
            pathToFile = scanConsole.nextLine();
            error = isFileTXT(pathToFile);
            file = new File(pathToFile);
            if (error == ErrorsCode.CORRECT)
                error = isExist(file);
            if (error == ErrorsCode.CORRECT)
                error = isReadable(file);
            if (error != ErrorsCode.CORRECT)
                printError(error);
        } while (error != ErrorsCode.CORRECT);
        return file;
    }
    public static File getFileNormalWriting() {
        File file;
        ErrorsCode error;
        String pathToFile;
        do {
            pathToFile = scanConsole.nextLine();
            error = isFileTXT(pathToFile);
            file = new File(pathToFile);
            if (error == ErrorsCode.CORRECT)
                error = isExist(file);
            if (error == ErrorsCode.CORRECT)
                error = isWriteable(file);
            if (error != ErrorsCode.CORRECT)
                printError(error);
        } while (error != ErrorsCode.CORRECT);
        return file;
    }
    public static ErrorsCode readFileNum(Scanner scanFile, int[] num, final int MIN, final int MAX) {
        ErrorsCode error;
        error = ErrorsCode.CORRECT;
        try {
            num[0] = scanFile.nextInt();
        } catch (NumberFormatException e) {
            error = ErrorsCode.INCORRECT_NUM;
        }
        if (error == ErrorsCode.CORRECT)
            error = isCorrectRange(num[0], MIN, MAX);
        return error;
    }
    public static ErrorsCode readFileArrLen(Scanner scanFile, int[] arrLen) {
        ErrorsCode error;
        error = readFileNum(scanFile, arrLen, MIN_L, MAX_L);
        return error;
    }
    public static ErrorsCode readFileArr(Scanner scanFile, int[] arr, int arrLen) {
        ErrorsCode error;
        int[] num = new int[1];
        int i;
        error = ErrorsCode.CORRECT;
        i = 0;
        while (error == ErrorsCode.CORRECT && i < arrLen) {
            error = readFileNum(scanFile, num, MIN_A, MAX_A);
            arr[i] = num[0];
            i++;
        }
        if (error == ErrorsCode.CORRECT && i != arrLen)
            error = ErrorsCode.INCORRECT_ARR_LEN;
        if (error == ErrorsCode.CORRECT && scanFile.hasNext())
            error = ErrorsCode.EXTRA_DATA;
        return error;
    }
    public static ErrorsCode readFileData(File file, int[] arr) {
        ErrorsCode error;
        int i;
        int[] n = new int[1];
        int arrLen;
        error = ErrorsCode.CORRECT;
        z
        return error;
    }
    public static int[] readFile() {
        ErrorsCode error;
        int[] n = new int[1];
        int[] arr;
        File file;
        System.out.print("Введите путь к файлу с расширением .txt.\n");
        System.out.print("Содержание: Массив с длиной[" + MIN_L + "; " + MAX_L + "] и элементами в диапазоне[" + MIN_A + "; " + MAX_A + "]: ");
        do {
            file = getFileNormalReading();
            error = readFileData(file, arr);
            if (error != ErrorsCode.CORRECT)
                printError(error);
        } while (error != ErrorsCode.CORRECT);
        return arr;
    }
    public static int readConsoleNum(final int MIN, final int MAX) {
        ErrorsCode error;
        String sNum;
        int num;
        num = 0;
        do {
            error = ErrorsCode.CORRECT;
            sNum = scanConsole.nextLine();
            try {
                num = Integer.parseInt(sNum);
            } catch (NumberFormatException e) {
                error = ErrorsCode.INCORRECT_NUM;
            }
            if (error == ErrorsCode.CORRECT)
                error = isCorrectRange(num, MIN, MAX);
            if (error != ErrorsCode.CORRECT)
                printError(error);
        } while (error != ErrorsCode.CORRECT);
        return num;
    }
    public static int readConsoleArrLen() {
        int arrLen;
        System.out.print("Введите длину массива в диапазоне[" + MIN_L + ": " + MAX_L + "]: ");
        arrLen = readConsoleNum(MIN_L, MAX_L);
        return arrLen;
    }
    public static int[] readConsoleArr(int arrLen) {
        int[] arr;
        int i;
        arr = new int[arrLen];
        System.out.print("Введите массив: \n");
        for (i = 0; i < arrLen; i++) {
            System.out.print("Введите " + (i + 1) + " элемент массива в диапазоне[" + MIN_A + ": " + MAX_A + "]: ");
            arr[i] = readConsoleNum(MIN_A, MAX_A);
        }
        return arr;
    }
    public static int[] readConsoleData() {
        int[] arr;
        int arrLen;
        arrLen = readConsoleArrLen();
        arr = readConsoleArr(arrLen);
        return arr;
    }
    public static int[] readConsole() {
        int[] arr;
        arr = readConsoleData();
        return arr;
    }
    public static int[] readArr() {
        int[] arr;
        int option;
        System.out.print("Вы хотите: \n");
        System.out.print("Вводить массив через файл - 1\n");
        System.out.print("Вводить массив через консоль - 2\n");
        option = chooseOption(2);
        if (option == 1)
            arr = readFile();
        else
            arr = readConsole();
        return arr;
    }
    public static void showProcess(int[] helpArr) {
        int i;
        for (i = 0; i < helpArr.length; i++)
            System.out.print(helpArr[i] + " ");
        System.out.print("\n");
    }
    public static void sortArr(int[] arr) {
        int[] helpArr;
        int leftIndex, rightIndex, i, j, currentElem;
        helpArr = new int[arr.length * 2 - 1];
        helpArr[arr.length - 1] = arr[0];
        leftIndex = rightIndex = arr.length - 1;
        for (i = 1; i < arr.length; i++) {
            currentElem = arr[i];
            if (currentElem > arr[i - 1]) {
                rightIndex++;
                j = rightIndex;
                while (currentElem < helpArr[j - 1]) {
                    helpArr[j] = helpArr[j - 1];
                    j--;
                }
            }
            else {
                leftIndex--;
                j = leftIndex;
                while (currentElem > helpArr[j + 1]) {
                    helpArr[j] = helpArr[j + 1];
                    j++;
                }
            }
            helpArr[j] = currentElem;
            showProcess(helpArr);
        }
        for (j = 0; j < arr.length; j++)
            arr[j] = helpArr[j + leftIndex];
    }
    public static void printConsoleResult(int[] arr) {
        int i;
        System.out.print("\nОтсортированный массив: \n");
        for (i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
    }
    public static void printFileResult(int[] arr) {
        ErrorsCode error;
        File file;
        int i;
        System.out.print("Введите путь к файлу с расширением .txt для получения результата: \n");
        file = getFileNormalWriting();
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write("\nОтсортированный массив: \n");
            for (i = 0; i < arr.length; i++)
                writer.write(arr[i] + " ");
        } catch (Exception e) {
            error = ErrorsCode.IS_NOT_WRITEABLE;
            printError(error);
        }
    }
    public static void printResult(int[] arr) {
        int option;
        System.out.print("Вы хотите: \n");
        System.out.print("Выводить массив через файл - 1\n");
        System.out.print("Выводить массив через консоль - 2\n");
        option = chooseOption(2);
        if (option == 1)
            printFileResult(arr);
        else
            printConsoleResult(arr);
    }
    public static void main(String[] args) {
        int[] arr;
        printTask();
        arr = readArr();
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        sortArr(arr);
        printResult(arr);
        scanConsole.close();
    }
}

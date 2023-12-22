import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.HashSet;
public class Main {
    public enum ErrorsCode {
        CORRECT,
        INCORRECT_SET_LENGTH,
        INCORRECT_SET_El,
        INCORRECT_CHOICE,
        IS_NOT_TXT,
        IS_NOT_EXIST,
        IS_NOT_READABLE,
        IS_NOT_WRITEABLE,
        INCORRECT_SET_AMOUNT,
    }
    public static final int
            MIN_S = 1,
            MAX_S = 85;
    public static final String[]
            ERRORS = { "",
                       "Длина множества не попадает в диапазон!",
                       "Элементы множества разделяются пробелом!",
                       "Некорректный выбор!",
                       "Расширение файла не .txt!",
                       "Проверьте корректность ввода пути к файлу!",
                       "Файл закрыт для чтения!",
                       "Файл закрыт для записи!",
                       "Неправильное число множеств в файле" };
    static Scanner scanConsole = new Scanner(System.in);
    public static void printTask() {
        System.out.print("Данная программа формирует множество из трёх множеств и находит числа в этом множестве.\n");
    }
    public static ErrorsCode isCorrectSetLen(String sSetEl) {
        ErrorsCode error;
        int setLen;
        error = ErrorsCode.CORRECT;
        setLen = (sSetEl.length() + 1) / 2;
        if (setLen < MIN_S || setLen > MAX_S)
            error = ErrorsCode.INCORRECT_SET_LENGTH;
        return error;
    }
    public static ErrorsCode isCorrectSetEl(String sSetEl) {
        ErrorsCode error;
        int i;
        error = ErrorsCode.CORRECT;
        i = 1;
        while (error == ErrorsCode.CORRECT && i < sSetEl.length()) {
            if (sSetEl.charAt(i) != ' ')
                error = ErrorsCode.INCORRECT_SET_El;
            i += 2;
        }
        return error;
    }
    public static ErrorsCode isCorrectSet(String sSetEl) {
        ErrorsCode error;
        error = isCorrectSetLen(sSetEl);
        if (error == ErrorsCode.CORRECT)
            error = isCorrectSetEl(sSetEl);
        return error;
    }
    public static void fillSet(String sSetEl, Set<Character> setEl) {
        int i;
        for (i = 0; i < sSetEl.length(); i += 2)
            setEl.add(sSetEl.charAt(i));
    }
    public static int chooseOption(int amount) {
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
            if (error == ErrorsCode.CORRECT && (iOption < 1 || iOption > amount))
                error = ErrorsCode.INCORRECT_CHOICE;
            if (error != ErrorsCode.CORRECT)
                System.out.print(ERRORS[error.ordinal()] + "\nПовторите попытку: \n");
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
                System.out.print(ERRORS[error.ordinal()] + "\nПовторите попытку: \n");
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
                System.out.print(ERRORS[error.ordinal()] + "\nПовторите попытку: \n");
        } while (error != ErrorsCode.CORRECT);
        return file;
    }
    public static ErrorsCode readFileSet(Scanner scanFile, Set<Character> setEl) {
        ErrorsCode error;
        String sSetEl;
        sSetEl = scanFile.nextLine();
        error = isCorrectSet(sSetEl);
        fillSet(sSetEl, setEl);
        return error;
    }
    public static void readFileSets(Set<Character> x1, Set<Character> x2, Set<Character> x3) {
        ErrorsCode error;
        File file;
        System.out.print("Введите путь к файлу с расширением .txt с тремя множествами, с длинами[" + MIN_S + "; " + MAX_S + "]: \n");
        do {
            file = getFileNormalReading();
            error = ErrorsCode.CORRECT;
            try(Scanner scanFile = new Scanner(file)) {
                if (scanFile.hasNext()) {
                    error = readFileSet(scanFile, x1);
                }
                else
                    error = ErrorsCode.INCORRECT_SET_AMOUNT;
                if (scanFile.hasNext()) {
                    if (error == ErrorsCode.CORRECT) error = readFileSet(scanFile, x2);
                }
                else
                    error = ErrorsCode.INCORRECT_SET_AMOUNT;
                if (scanFile.hasNext()) {
                    if (error == ErrorsCode.CORRECT) error = readFileSet(scanFile, x3);
                }
                else
                    error = ErrorsCode.INCORRECT_SET_AMOUNT;
                if (error == ErrorsCode.CORRECT && scanFile.hasNext())
                    error = ErrorsCode.INCORRECT_SET_AMOUNT;
            } catch (Exception e) {}
            if (error != ErrorsCode.CORRECT)
                System.out.print(ERRORS[error.ordinal()] + "\nПовторите попытку: \n");
        } while (error != ErrorsCode.CORRECT);
    }
    public static void readConsoleSet(int num, Set<Character> setEl) {
        ErrorsCode error;
        String sSetEl;
        System.out.print("Введите множество Х" + num + " через пробелы : ");
        do {
            sSetEl = scanConsole.nextLine();
            error = isCorrectSet(sSetEl);
            if (error != ErrorsCode.CORRECT)
                System.out.print(ERRORS[error.ordinal()] + "\nПовторите попытку: ");
        } while (error != ErrorsCode.CORRECT);
        fillSet(sSetEl, setEl);
    }
    public static void readConsoleSets(Set<Character> x1, Set<Character> x2, Set<Character> x3) {
        readConsoleSet(1, x1);
        readConsoleSet(2, x2);
        readConsoleSet(3, x3);
    }
    public static void readSets(Set<Character> x1, Set<Character> x2, Set<Character> x3) {
        int option;
        System.out.print("Вы хотите: \n");
        System.out.print("Вводить множества через файл - 1\n");
        System.out.print("Вводить множества через консоль - 2\n");
        option = chooseOption(2);
        if (option == 1)
            readFileSets(x1, x2, x3);
        else
            readConsoleSets(x1, x2, x3);
    }
    public static void uniteSets(Set<Character> y, Set<Character> x1, Set<Character> x2, Set<Character> x3) {
        y.addAll(x1);
        y.addAll(x2);
        y.addAll(x3);
    }
    public static void findNums(Set<Character> y1, Set<Character> y) {
        for (char element : y)
            if (element >= '0' && element <= '9')
                y1.add(element);
    }
    public static void printConsoleResult(Set<Character> y1, Set<Character> y) {
        System.out.print("\nМножество Y = {X1 U X2 U X3}: ");
        for (char element : y)
            System.out.print("'" + element + "'; ");
        System.out.print("\nЦифры в множестве: ");
        if (y1.isEmpty())
            System.out.print("цифр в множестве нет");
        else
            for (char element : y1)
                System.out.print("'" + element + "'; ");
    }
    public static void printFileResult(Set<Character> y1, Set<Character> y) {
        File file;
        System.out.print("Введите путь к файлу с расширением .txt для получения результата: \n");
        file = getFileNormalWriting();
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write("\nМножество Y = {X1 U X2 U X3}: ");
            for (char element : y)
                writer.write("'" + element + "'; ");
            writer.write("\nЦифры в множестве: ");
            if (y1.isEmpty())
                writer.write("цифр в множестве нет");
            else
                for (char element : y1)
                    writer.write("'" + element + "'; ");
        } catch (Exception e) {}
    }
    public static void printResult(Set<Character> y1, Set<Character> y) {
        int option;
        System.out.print("Вы хотите: \n");
        System.out.print("Выводить множеста через файл - 1\n");
        System.out.print("Выводить множеста через консоль - 2\n");
        option = chooseOption(2);
        if (option == 1)
            printFileResult(y1, y);
        else
            printConsoleResult(y1, y);
    }
    public static void main(String[] args) {
        Set<Character> x1 = new HashSet<>();
        Set<Character> x2 = new HashSet<>();
        Set<Character> x3 = new HashSet<>();
        Set<Character> y = new HashSet<>();
        Set<Character> y1 = new HashSet<>();
        printTask();
        readSets(x1, x2, x3);
        uniteSets(y, x1, x2, x3);
        findNums(y1, y);
        printResult(y1, y);
        scanConsole.close();
    }
}

#include <iostream>
#include <set>
#include <string>
#include <fstream>
enum ErrorsCode
{
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
};
const int
    MIN_A = -1000000,
    MAX_A = 1000000,
    MIN_L = 1,
    MAX_L = 100;
const std::string
ERRORS[] = { "",
             "Значение не попадает в диапазон!",
             "Введено не число!!",
             "Некорректный выбор!",
             "Расширение файла не .txt!",
             "Проверьте корректность ввода пути к файлу!",
             "Файл закрыт для чтения!",
             "Файл закрыт для записи!",
             "Неправильная длина массива!", 
             "Лишние данные в файле!"};
void printTask()
{
    std::cout << "Данная программа реализует сортировку двухпутевыми вставками.\n";
}
void printError(ErrorsCode error)
{
    std::cout << ERRORS[error] << "\nПовторите попытку: \n";
}
ErrorsCode isCorrectRange(int num, const int MIN, const int MAX)
{
    ErrorsCode error;
    error = CORRECT;
    if (num < MIN || num > MAX)
        error = INCORRECT_RANGE;
    return error;
}
int chooseOption(int quantity)
{
    ErrorsCode error;
    int option;
    option = 1;
    do {
        error = CORRECT;
        std::cin >> option;
        if (std::cin.fail())
        {
            error = INCORRECT_CHOICE;
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (error == CORRECT && std::cin.get() != '\n')
        {
            error = INCORRECT_CHOICE;
            while (std::cin.get() != '\n');
        }
        if (error == CORRECT && (option < 1 || option > quantity))
            error = INCORRECT_CHOICE;
        if (error != CORRECT)
            printError(error);
    } while (error != CORRECT);
    return option;
}
std::string getPartStr(std::string str, int posStart, int posEnd)
{
    std::string partStr;
    int i;
    partStr = "";
    for (i = posStart; i <= posEnd; i++)
        partStr = partStr + str[i];
    return partStr;
}
ErrorsCode isFileTXT(std::string pathToFile)
{
    ErrorsCode error;
    error = CORRECT;
    if (pathToFile.length() < 5 || getPartStr(pathToFile, (int)pathToFile.length() - 4, (int)pathToFile.length() - 1) != ".txt")
        error = IS_NOT_TXT;
    return error;
}
ErrorsCode isExist(std::string pathToFile)
{
    ErrorsCode error;
    error = CORRECT;
    std::ifstream file(pathToFile);
    if (!file.good())
        error = IS_NOT_EXIST;
    file.close();
    return error;
}
ErrorsCode isReadable(std::string pathToFile)
{
    ErrorsCode error;
    error = CORRECT;
    std::ifstream file(pathToFile);
    if (!file.is_open())
        error = IS_NOT_READABLE;
    file.close();
    return error;
}
ErrorsCode isWriteable(std::string pathToFile)
{
    ErrorsCode error;
    error = CORRECT;
    std::ofstream file(pathToFile, std::ios::app);
    if (!file.is_open())
        error = IS_NOT_WRITEABLE;
    file.close();
    return error;
}
void getFileNormalReading(std::string& pathToFile)
{
    ErrorsCode error;
    do
    {
        std::getline(std::cin, pathToFile);
        error = isFileTXT(pathToFile);
        if (error == CORRECT)
            error = isExist(pathToFile);
        if (error == CORRECT)
            error = isReadable(pathToFile);
        if (error != CORRECT)
            printError(error);
    } while (error != CORRECT);
}
void getFileNormalWriting(std::string& pathToFile)
{
    ErrorsCode error;
    do
    {
        std::getline(std::cin, pathToFile);
        error = isFileTXT(pathToFile);
        if (error == CORRECT)
            error = isExist(pathToFile);
        if (error == CORRECT)
            error = isWriteable(pathToFile);
        if (error != CORRECT)
            printError(error);
    } while (error != CORRECT);
}
ErrorsCode readFileNum(std::ifstream& file, int& num, const int MIN, const int MAX)
{
    ErrorsCode error;
    error = CORRECT;
    file >> num;
    if (std::cin.fail())
    {
        error = INCORRECT_NUM;
        std::cin.clear();
        while (std::cin.get() != '\n');
    }
    if (error == CORRECT && std::cin.get() != '\n')
    {
        error = INCORRECT_NUM;
        while (std::cin.get() != '\n');
    }
    if (error == CORRECT)
        error = isCorrectRange(num, MIN, MAX);
    return error;
}
ErrorsCode readFileArrLen(std::ifstream& file, int& arrLen)
{
    ErrorsCode error;
    error = readFileNum(file, arrLen, MIN_L, MAX_L);
    if (error == CORRECT && file.peek() != '\n')
        error = EXTRA_DATA;
    return error;
}
ErrorsCode readFileArr(std::ifstream& file, int*& arr, int arrLen)
{
    ErrorsCode error;
    int i;
    error = CORRECT;
    i = 0;
    arr = new int[arrLen];
    file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    while (error == CORRECT && i < arrLen)
    {
        error = readFileNum(file, arr[i], MIN_A, MAX_A);
        i++;
    }
    if (error == CORRECT && i != arrLen)
        error = INCORRECT_ARR_LEN;
    if (error == CORRECT && !file.eof())
        error = EXTRA_DATA;
    return error;
}
ErrorsCode readFileData(std::string pathToFile, int*& arr, int& arrLen)
{
    ErrorsCode error;
    std::ifstream file(pathToFile);
    if (file.good()) {
        error = readFileArrLen(file, arrLen);
        if (error == CORRECT)
            error = readFileArr(file, arr, arrLen);
    }
    else
        error = IS_NOT_EXIST;
    file.close();
    return error;
}
ErrorsCode readFile(int*& arr, int& arrLen)
{
    ErrorsCode error;
    std::string pathToFile;
    pathToFile = "";
    std::cout << "Введите путь к файлу с расширением .txt.\n";
    std::cout << "Содержание: Массив с длиной[" << MIN_L << "; " << MAX_L << "] и элементами в диапазоне[" << MIN_A << "; " << MAX_A << "]: ";
    do
    {
        getFileNormalReading(pathToFile);
        error = readFileData(pathToFile, arr, arrLen);
        if (error != CORRECT)
            printError(error);
    } while (error != CORRECT);
    return error;
}
void readConsoleNum(int& num, const int MIN, const int MAX)
{
    ErrorsCode error;
    do
    {
        error = CORRECT;
        std::cin >> num;
        if (std::cin.fail())
        {
            error = INCORRECT_NUM;
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (error == CORRECT && std::cin.get() != '\n')
        {
            error = INCORRECT_NUM;
            while (std::cin.get() != '\n');
        }
        if (error == CORRECT)
            error = isCorrectRange(num, MIN, MAX);
        if (error != CORRECT)
            printError(error);
    } while (error != CORRECT);
}
void readConsoleArrLen(int& arrLen)
{
    std::cout << "Введите длину массива в диапазоне[" << MIN_L << ": " << MAX_L << "]: ";
    readConsoleNum(arrLen, MIN_L, MAX_L);
}
void readConsoleArr(int*& arr, int arrLen)
{
    int i;
    arr = new int[arrLen];
    std::cout << "Введите массив: \n";
    for (i = 0; i < arrLen; i++)
    {
        std::cout << "Введите " << (i + 1) << " элемент массива в диапазоне[" << MIN_A << ": " << MAX_A << "]: ";
        readConsoleNum(arr[i], MIN_A, MAX_A);
    }  
}
void readConsoleData(int*& arr, int& arrLen)
{
    readConsoleArrLen(arrLen);
    readConsoleArr(arr, arrLen);
}
void readConsole(int*& arr, int& arrLen)
{
    readConsoleData(arr, arrLen);
}
void readArr(int*& arr, int& arrLen)
{
    int option;
    std::cout << "Вы хотите: \n";
    std::cout << "Вводить массив через файл - 1\n";
    std::cout << "Вводить массив через консоль - 2\n";
    option = chooseOption(2);
    if (option == 1)
        readFile(arr, arrLen);
    else
        readConsole(arr, arrLen);
}
void showProcess(int* helpArr, int helpArrLen)
{
    int i;
    for (i = 0; i < helpArrLen; i++)
        std::cout << helpArr[i] << " ";
    std::cout << "\n";
}
void sortArr(int*& arr, int arrLen)
{
    int* helpArr;
    int helpArrLen;
    int leftIndex, rightIndex, i, j, currentElem;
    helpArrLen = arrLen * 2 - 1;
    helpArr = new int[helpArrLen]();
    helpArr[arrLen - 1] = arr[0];
    leftIndex = rightIndex = arrLen - 1;
    for (i = 1; i < arrLen; i++)
    {
        currentElem = arr[i];
        if (currentElem > arr[i - 1])
        {
            rightIndex++;
            j = rightIndex;
            while (currentElem < helpArr[j - 1])
            {
                helpArr[j] = helpArr[j - 1];
                j--;
            }
        }
        else
        {
            leftIndex--;
            j = leftIndex;
            while (currentElem > helpArr[j + 1])
            {
                helpArr[j] = helpArr[j + 1];
                j++;
            }
        }
        helpArr[j] = currentElem;
        showProcess(helpArr, helpArrLen);
    }
    for (j = 0; j < arrLen; j++)
        arr[j] = helpArr[j + leftIndex];
    delete[] helpArr;
}
void printConsoleResult(int* arr, int arrLen)
{
    int i;
    std::cout << "\nОтсортированный массив: \n";
    for (i = 0; i < arrLen; i++)
        std::cout << arr[i] << " ";
}
void printFileResult(int* arr, int arrLen)
{
    std::string pathToFile;
    int i;
    pathToFile = "";
    std::cout << "Введите путь к файлу с расширением .txt для получения результата: \n";
    getFileNormalWriting(pathToFile);
    std::ofstream file(pathToFile, std::ios::app);
    file << "\nОтсортированный массив: \n";
    for (i = 0; i < arrLen; i++)
        file << arr[i] << " ";
    file.close();
}
void printResult(int* arr, int arrLen)
{
    int option;
    std::cout << "Вы хотите: \n";
    std::cout << "Выводить массив через файл - 1\n";
    std::cout << "Выводить массив через консоль - 2\n";
    option = chooseOption(2);
    if (option == 1)
        printFileResult(arr, arrLen);
    else
        printConsoleResult(arr, arrLen);
}
int main()
{
    setlocale(LC_ALL, "RU");
    int* arr;
    int arrLen;
    printTask();
    readArr(arr, arrLen);
    sortArr(arr, arrLen);
    printResult(arr, arrLen);
    delete[] arr;
    return 0;
}

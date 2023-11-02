#include <iostream>
#include <string>
#include <fstream>
const int
    OPTION1 = 1,
    OPTION2 = 2,
    MIN_LEN = 1,
    MAX_LEN = 100;
const double
    FACTOR = 1.247;
void printTask()
{
    std::cout << "Данная программа находит элементы в двух строках по одному из критериев.\n\n";
}
bool checkStringLen(std::string str)
{
    bool isCorrect;
    isCorrect = true;
    if (str.size() < MIN_LEN || str.size() > MAX_LEN)
    {
        std::cout << "Длина строки не попадает в диапазон!\n";
        isCorrect = false;
    }
    return isCorrect;
}
int chooseOption()
{
    int option;
    bool isCorrect;
    option = 0;
    do {
        isCorrect = true;
        std::cin >> option;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cin.clear();
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cout << "Повторите попытку: \n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cout << "Повторите попытку: \n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && option != OPTION1 && option != OPTION2)
        {
            isCorrect = false;
            std::cout << "Некорректный выбор!\n";
            std::cout << "Повторите попытку: \n";
        }
    } while (!isCorrect);
    return option;
}
std::string readPathFile()
{
    std::string pathToFile;
    bool isCorrect;
    pathToFile = "";
    do
    {
        isCorrect = true;
        std::cout << "Введите путь к файлу с расширением.txt с двумя строками, с длинами[" << MIN_LEN << "; " << MAX_LEN << "]: ";
        std::cin >> pathToFile;
        if (pathToFile.size() < 5 || pathToFile.substr(pathToFile.size() - 4, 4) != ".txt")
        {
            std::cout << "Расширение файла не .txt!\n";
            isCorrect = false;
        }
    } while (!isCorrect);
    return pathToFile;
}
bool isNotExists(std::string pathToFile)
{
    bool isRight;
    isRight = true;
    std::ifstream file(pathToFile);
    if (file.good())
        isRight = false;
    file.close();
    return isRight;
}
bool isNotAbleToReading(std::string pathToFile)
{
    bool isRight;
    isRight = true;
    std::ifstream file(pathToFile);
    if (file.is_open())
        isRight = false;
    file.close();
    return isRight;
}
bool isNotAbleToWriting(std::string pathToFile)
{
    bool isRight;
    isRight = true;
    std::ofstream file(pathToFile, std::ios::app);
    if (file.is_open())
        isRight = false;
    file.close();
    return isRight;
}
bool isEmpty(std::string pathToFile)
{
    bool isRight;
    isRight = false;
    std::ifstream file(pathToFile);
    if (file.peek() == std::ifstream::traits_type::eof())
        isRight = true;
    file.close();
    return isRight;
}
bool isNotRightCountStrings(std::string pathToFile)
{
    bool isRight;
    isRight = false;
    std::ifstream file(pathToFile);
    file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    if (file.peek() == std::ifstream::traits_type::eof())
        isRight = true;
    file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    if (file.peek() != std::ifstream::traits_type::eof())
        isRight = true;
    file.close();
    return isRight;
}
bool isNotCorrectStrings(std::string pathToFile)
{
    std::string str;
    bool isRight;
    str = "";
    std::ifstream file(pathToFile);
    std::getline(file, str);
    isRight = checkStringLen(str);
    if (isRight) {
        std::getline(file, str);
        isRight = checkStringLen(str);
    }
    file.close();
    return !isRight;
}
void getFileNormalReading(std::string& pathToFile)
{
    bool isCorrect;
    pathToFile = "";
    do
    {
        isCorrect = true;
        pathToFile = readPathFile();
        if (isNotExists(pathToFile))
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода пути к файлу!\n";
        }
        if (isCorrect && isNotAbleToReading(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл закрыт для чтения!\n";
        }
        if (isCorrect && isEmpty(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл пуст!\n";
        }
        if (isCorrect && isNotRightCountStrings(pathToFile))
        {
            isCorrect = false;
            std::cout << "Количество строк в файле не две!\n";
        }
        if (isCorrect && isNotCorrectStrings(pathToFile))
            isCorrect = false;
    } while (!isCorrect);
}
void getFileNormalWriting(std::string& pathToFile)
{
    bool isCorrect;
    do
    {
        isCorrect = true;
        pathToFile = readPathFile();
        if (!isNotExists(pathToFile))
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода пути к файлу!\n";
        }
        if (isCorrect && !isNotAbleToWriting(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл закрыт для записи!\n";
        }
    } while (!isCorrect);
}
std::string readFileString(std::ifstream& file)
{
    std::string str;
    str = "";
    std::getline(file, str);
    return str;
}
std::string readConsoleString(int num)
{
    std::string str;
    bool isCorrect;
    str = "";
    do
    {
        std::cout << "Введите строку номер " << num << ", с длиной[" << MIN_LEN << ";" << MAX_LEN << "]: ";
        std::cin >> str;
        isCorrect = checkStringLen(str);
    } while (!isCorrect);
    return str;
}
void readStrings(std::string& str1, std::string& str2)
{
    std::string pathToFile;
    pathToFile = "";
    std::cout << "Вы хотите: \n";
    std::cout << "Вводить матрицу через файл - " << OPTION1 << "\n";
    std::cout << "Вводить матрицу через консоль - " << OPTION2 << "\n";
    if (chooseOption() == OPTION1)
    {
        getFileNormalReading(pathToFile);
        std::ifstream file(pathToFile);
        str1 = readFileString(file);
        str2 = readFileString(file);
        file.close();
    }
    else
    {
        str1 = readConsoleString(1);
        str2 = readConsoleString(2);
    }
}
void fillOneAStr(std::string str, char*& aStr, int& lenAStr)
{
    int i;
    lenAStr = str.size();
    aStr = new char[lenAStr];
    for (i = 0; i < lenAStr; i++)
        aStr[i] = str[i];
}
void fillAStrs(std::string str1, std::string str2, char*& aStr1, char*& aStr2, int& lenAStr1, int& lenAStr2)
{
    fillOneAStr(str1, aStr1, lenAStr1);
    fillOneAStr(str2, aStr2, lenAStr2);
}
void sortOneAStr(char*& aStr, int lenAStr)
{
    double step;
    int i, iStep;
    char buf;
    step = lenAStr - 1;
    while (step >= 1)
    {
        iStep = trunc(step);
        i = 0;
        while (step + i < lenAStr)
        {
            if (static_cast<int>(aStr[i]) > static_cast<int>(aStr[i + iStep]))
            {
                buf = aStr[i];
                aStr[i] = aStr[i + iStep];
                aStr[i + iStep] = buf;
            }
            i++;
        }
        step /= FACTOR;
    }
}
void sortAStrs(char*& aStr1, char*& aStr2, int lenAStr1, int lenAStr2)
{
    sortOneAStr(aStr1, lenAStr1);
    sortOneAStr(aStr2, lenAStr2);
}
void makeUniqueAStr(char*& uniqueAStr, char*& aStr1, char*& aStr2, int& lenUAStr, int lenAStr1, int lenAStr2)
{
    char* bufArr;
    int i, j, maxIndex;
    bufArr = new char[lenAStr1 + lenAStr2];
    j = 0;
    maxIndex = lenAStr1 - 1;
    for (i = 0; i < maxIndex; i++)
        if (aStr1[i] != aStr1[i + 1])
        {
            bufArr[j] = aStr1[i];
            j++;
        }
    bufArr[j] = aStr1[maxIndex];
    j++;
    maxIndex = lenAStr2 - 1;
    for (i = 0; i < maxIndex; i++)
        if (aStr2[i] != aStr2[i + 1])
        {
            bufArr[j] = aStr2[i];
            j++;
        }
    bufArr[j] = aStr2[maxIndex];
    lenUAStr = j + 1;
    uniqueAStr = new char[lenUAStr];
    for (i = 0; i < lenUAStr; i++)
        uniqueAStr[i] = bufArr[i];
    delete[] bufArr;
}
void findSame(char* uniqueAStr, char*& resAStr, int lenUAStr, int& lenRAStr)
{
    int i, j, maxIndex;
    j = 0;
    char* bufArr;
    bufArr = new char[lenUAStr];
    maxIndex = lenUAStr - 1;
    for (i = 0; i < maxIndex; i++)
        if (uniqueAStr[i] == uniqueAStr[i + 1])
        {
            bufArr[j] = uniqueAStr[i];
            j++;
        }
    lenRAStr = j;
    resAStr = new char[lenRAStr];
    for (i = 0; i < lenRAStr; i++)
        resAStr[i] = bufArr[i];
    delete[] bufArr;
}
void findUnique(char* uniqueAStr, char*& resAStr, int lenUAStr, int& lenRAStr)
{
    int i, j, maxIndex;
    j = 0;
    char* bufArr;
    bufArr = new char[lenUAStr];
    maxIndex = lenUAStr - 1;
    i = 0;
    while (i < maxIndex)
    {
        if (uniqueAStr[i] != uniqueAStr[i + 1])
        {
            bufArr[j] = uniqueAStr[i];
            j++;
        }
        else
            i++;
        i++;
    }
    if (uniqueAStr[maxIndex] != uniqueAStr[maxIndex - 1])
    {
        bufArr[j] = uniqueAStr[maxIndex];
        lenRAStr = j + 1;
    }
    else
        lenRAStr = j;
    resAStr = new char[lenRAStr];
    for (i = 0; i < lenRAStr; i++)
        resAStr[i] = bufArr[i];
    delete[] bufArr;
}
int chooseAction()
{
    std::cout << "Вы хотите: \n";
    std::cout << "Найти одинаковые символы в обеих строках - " << OPTION1 << "\n";
    std::cout << "Найти уникальные символы в обеих строках - " << OPTION2 << "\n";
    return chooseOption();
}
void printConsoleResult(char* resAStr, int lenRAStr)
{
    int i;
    i = 0;
    std::cout << "\nЭлементы, удовлетворяющие условию: ";
    if (resAStr[0] == '\0')
        std::cout << "элементов, удовлетворяющих условию, нет!";
    else
        while (i < lenRAStr)
        {
            std::cout << "\'" << resAStr[i] << "\'; ";
            i++;
        }
}
void printFileResult(std::string pathToFile, char* resAStr, int lenRAStr)
{
    int i;
    std::ofstream file(pathToFile, std::ios::app);
    i = 0;
    file << "\nЭлементы, удовлетворяющие условию: ";
    if (resAStr[0] == '\0')
        file << "элементов, удовлетворяющих условию, нет!";
    else
        while (i < lenRAStr)
        {
            file << "\'" << resAStr[i] << "\'; ";
            i++;
        }
    file.close();
}
void printResult(char* resAStr, int lenRAStr)
{
    std::string pathToFile;
    pathToFile = "";
    std::cout << "Вы хотите: \n";
    std::cout << "Выводить строки через файл - " << OPTION1 << "\n";
    std::cout << "Выводить строки через консоль - " << OPTION2 << "\n";
    if (chooseOption() == OPTION1)
    {
        getFileNormalWriting(pathToFile);
        printFileResult(pathToFile, resAStr, lenRAStr);
    }
    else
        printConsoleResult(resAStr, lenRAStr);
}
void freeMemory(char*& aStr1, char*& aStr2, char*& uniqueAStr, char*& resAStr)
{
    delete[] aStr1;
    delete[] aStr2;
    delete[] uniqueAStr;
    delete[] resAStr;
}
int main()
{
    setlocale(LC_ALL, "RU");
    char* aStr1;
    char* aStr2;
    char* uniqueAStr;
    char* resAStr;
    int lenAStr1, lenAStr2, lenUAStr, lenRAStr, action;
    std::string str1, str2;
    str1 = "";
    str2 = "";
    printTask();
    readStrings(str1, str2);
    fillAStrs(str1, str2, aStr1, aStr2, lenAStr1, lenAStr2);
    sortAStrs(aStr1, aStr2, lenAStr1, lenAStr2);
    makeUniqueAStr(uniqueAStr, aStr1, aStr2, lenUAStr, lenAStr1, lenAStr2);
    sortOneAStr(uniqueAStr, lenUAStr);
    action = chooseAction();
    if (action == OPTION1)
        findSame(uniqueAStr, resAStr, lenUAStr, lenRAStr);
    else
        findUnique(uniqueAStr, resAStr, lenUAStr, lenRAStr);
    printResult(resAStr, lenRAStr);
    freeMemory(aStr1, aStr2, uniqueAStr, resAStr);
    return 0;
}

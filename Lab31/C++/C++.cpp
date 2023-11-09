#include <iostream>
#include <string>
#include <fstream>
const int
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
int chooseOption(int count)
{
    int option, i;
    bool isCorrect, isNotCorrectChoise;
    option = 0;
    do 
    {
        isNotCorrectChoise = false;
        isCorrect = true;
        std::cin >> option;
        if (std::cin.fail())
        {
            std::cin.clear();
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cout << "Повторите попытку: \n";
            isCorrect = false;
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cout << "Повторите попытку: \n";
            isCorrect = false;
            while (std::cin.get() != '\n');
        }
        if (isCorrect)
        {
            isNotCorrectChoise = true;
            i = 0;
            while (isNotCorrectChoise && i < count)
            {
                if (option == i + 1)
                    isNotCorrectChoise = false;
                i++;
            }
        }
        if (isNotCorrectChoise)
        {
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cout << "Повторите попытку: \n";
            isCorrect = false;
        }
    } while (!isCorrect);
    return option;
}
std::string readPathFile()
{
    std::string pathToFile;
    bool isCorrect;
    do
    {
        isCorrect = true;
        std::cout << "Введите путь к файлу с расширением.txt с двумя строками, с длинами[" << MIN_LEN << "; " << MAX_LEN << "]: ";
        std::cin >> pathToFile;
        if (pathToFile.size() < 5 || pathToFile[pathToFile.length() - 4] != '.' || pathToFile[pathToFile.length() - 3] != 't' || pathToFile[pathToFile.length() - 2] != 'x' || pathToFile[pathToFile.length() - 1] != 't')
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
        if (isNotExists(pathToFile))
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода пути к файлу!\n";
        }
        if (isCorrect && isNotAbleToWriting(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл закрыт для записи!\n";
        }
    } while (!isCorrect);
}
std::string readFileString(std::ifstream& file)
{
    std::string str;
    std::getline(file, str);
    return str;
}
std::string readConsoleString(int num)
{
    std::string str;
    bool isCorrect;
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
    int option;
    std::cout << "Вы хотите: \n";
    std::cout << "Вводить матрицу через файл - 1\n";
    std::cout << "Вводить матрицу через консоль - 2\n";
    option = chooseOption(2);
    if (option == 1)
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
    for (step = lenAStr - 1; step >= 1; step /= FACTOR)
    {
        iStep = trunc(step);
        for (i = 0; step + i < lenAStr; i++)
            if (static_cast<int>(aStr[i]) > static_cast<int>(aStr[i + iStep]))
            {
                buf = aStr[i];
                aStr[i] = aStr[i + iStep];
                aStr[i + iStep] = buf;
            }
    }
}
void sortAStrs(char*& aStr1, char*& aStr2, int lenAStr1, int lenAStr2)
{
    sortOneAStr(aStr1, lenAStr1);
    sortOneAStr(aStr2, lenAStr2);
}
int plusAStr(char*& combAStr, char* aStr, int j, int lenAStr1)
{
    int i, maxIndex;
    maxIndex = lenAStr1 - 1;
    for (i = 0; i < maxIndex; i++)
        if (aStr[i] != aStr[i + 1])
        {
            combAStr[j] = aStr[i];
            j++;
        }
    combAStr[j] = aStr[maxIndex];
    return ++j;
}
void makeCombSameAStr(char*& combAStr, char* aStr1, char* aStr2, int& lenCAStr, int lenAStr1, int lenAStr2)
{
    char* bufArr;
    int i, j;
    bufArr = new char[lenAStr1 + lenAStr2];
    j = 0;
    j = plusAStr(bufArr, aStr1, j, lenAStr1);
    j = plusAStr(bufArr, aStr2, j, lenAStr2);
    lenCAStr = j;
    combAStr = new char[lenCAStr];
    for (i = 0; i < lenCAStr; i++)
        combAStr[i] = bufArr[i];
    delete[] bufArr;
}
void makeCombUniqueAStr(char*& combAStr, char* aStr1, char* aStr2, char* aStr3, int& lenCAStr, int lenAStr1, int lenAStr2, int lenAStr3)
{
    char* bufArr;
    int i, j;
    bufArr = new char[lenAStr1 + lenAStr2 + lenAStr3];
    j = 0;
    j = plusAStr(bufArr, aStr1, j, lenAStr1);
    j = plusAStr(bufArr, aStr2, j, lenAStr2);
    j = plusAStr(bufArr, aStr3, j, lenAStr3);
    lenCAStr = j;
    combAStr = new char[lenCAStr];
    for (i = 0; i < lenCAStr; i++)
        combAStr[i] = bufArr[i];
    delete[] bufArr;
    
}
void findSame(char* combAStr, char*& resAStr, int lenCAStr, int& lenRAStr)
{
    char* bufArr;
    int i, j, maxIndex;
    bufArr = new char[lenCAStr];
    bufArr[0] = '\0';
    i = 0;
    j = 0;
    maxIndex = lenCAStr - 1;
    while (i < maxIndex)
    {
        if (combAStr[i] == combAStr[i + 1])
        {
            bufArr[j] = combAStr[i];
            j++;
            i++;
        } 
        i++;
    }
    if (j == 0)
        lenRAStr = 1;
    else
        lenRAStr = j;
    resAStr = new char[lenRAStr];
    for (i = 0; i < lenRAStr; i++)
        resAStr[i] = bufArr[i];
    delete[] bufArr;
}
void findUnique(char* combAStr, char*& resAStr, int lenCAStr, int& lenRAStr)
{
    char* bufArr;
    int i, j, maxIndex;
    bufArr = new char[lenCAStr];
    bufArr[0] = '\0';
    i = 0;
    j = 0;
    maxIndex = lenCAStr - 1;
    while (i < maxIndex)
    {
        if (combAStr[i] == combAStr[i + 1])
            if (i == maxIndex - 1 || combAStr[i] != combAStr[i + 2])
            {
                bufArr[j] = combAStr[i];
                j++;
                i++;
            }
            else
                i += 2;
        i++;
    }
    if (j == 0)
        lenRAStr = 1;
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
    std::cout << "Найти одинаковые символы в обеих строках - 1\n";
    std::cout << "Найти уникальные символы в первой строке - 2\n";
    std::cout << "Найти уникальные символы во второй строке - 3\n";
    return chooseOption(3);
}
void printConsoleResult(char* resAStr, int lenRAStr)
{
    int i;
    std::cout << "\nЭлементы, удовлетворяющие условию: ";
    if (resAStr[0] == '\0')
        std::cout << "элементов, удовлетворяющих условию, нет!";
    else
        for (i = 0; i < lenRAStr; i++)
            std::cout << "'" << resAStr[i] << "'; ";
}
void printFileResult(std::string pathToFile, char* resAStr, int lenRAStr)
{
    int i;
    std::ofstream file(pathToFile, std::ios::app);
    file << "\nЭлементы, удовлетворяющие условию: ";
    if (resAStr[0] == '\0')
        file << "элементов, удовлетворяющих условию, нет!";
    else
        for (i = 0; i < lenRAStr; i++)
            file << "'" << resAStr[i] << "'; ";
    file.close();
}
void printResult(char* resAStr, int lenRAStr)
{
    std::string pathToFile;
    int option;
    std::cout << "Вы хотите: \n";
    std::cout << "Выводить строки через файл - 1\n";
    std::cout << "Выводить строки через консоль - 2\n";
    option = chooseOption(2);
    if (option == 1)
    {
        getFileNormalWriting(pathToFile);
        printFileResult(pathToFile, resAStr, lenRAStr);
    }
    else
        printConsoleResult(resAStr, lenRAStr);
}
void freeMemory(char*& aStr1, char*& aStr2, char*& combAStr, char*& resAStr)
{
    delete[] aStr1;
    delete[] aStr2;
    delete[] combAStr;
    delete[] resAStr;
}
int main()
{
    setlocale(LC_ALL, "RU");
    std::string str1, str2;
    char* aStr1;
    char* aStr2;
    char* combAStr;
    char* resAStr;
    int lenAStr1, lenAStr2, lenCAStr, lenRAStr, action;
    printTask();
    readStrings(str1, str2);
    fillAStrs(str1, str2, aStr1, aStr2, lenAStr1, lenAStr2);
    sortAStrs(aStr1, aStr2, lenAStr1, lenAStr2);
    action = chooseAction();
    if (action == 1)
    {
        makeCombSameAStr(combAStr, aStr1, aStr2, lenCAStr, lenAStr1, lenAStr2);
        sortOneAStr(combAStr, lenCAStr);
        findSame(combAStr, resAStr, lenCAStr, lenRAStr);
    }
    else if (action == 2)
    {
        makeCombUniqueAStr(combAStr, aStr1, aStr2, aStr1, lenCAStr, lenAStr1, lenAStr2, lenAStr1);
        sortOneAStr(combAStr, lenCAStr);
        findUnique(combAStr, resAStr, lenCAStr, lenRAStr);
    }
    else 
    {
        makeCombUniqueAStr(combAStr, aStr1, aStr2, aStr2, lenCAStr, lenAStr1, lenAStr2, lenAStr2);
        sortOneAStr(combAStr, lenCAStr);
        findUnique(combAStr, resAStr, lenCAStr, lenRAStr);
    }
    printResult(resAStr, lenRAStr);
    freeMemory(aStr1, aStr2, combAStr, resAStr);
    return 0;
}

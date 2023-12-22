#include <iostream>
#include <string.h>
#include <fstream>
const int
    MIN_O = 1,
    MAX_O = 10,
    MIN_MAT = -1000000,
    MAX_MAT = 1000000,
    YES = 1,
    NO = 2;
void printTask()
{
    std::cout << "Данная программа подсчитывает число строк в матрице, которые составлены из перестановок чисел от 1 до порядка матрицы.\n\n";
}
bool chooseFileInput()
{
    int isFileInput;
    bool isCorrect, choose;
    do {
        std::cout << "Вы хотите вводить матрицу через файл? (Да - " << YES << " / Нет - " << NO << ")\n";
        isCorrect = true;
        std::cin >> isFileInput;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cout << "Некорректный выбор!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Некорректный выбор!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect)
        {
            if (isFileInput == 1)
                choose = true;
            else if (isFileInput == 2)
                choose = false;
            else
            {
                isCorrect = false;
                std::cout << "Некорректный выбор!\n";
            }
        }
    } while (!isCorrect);
    return choose;
}
bool checkUserArea(int num, int MIN, int MAX)
{
    bool isCorrect;
    if (num < MIN || num > MAX)
    {
        std::cout << "Значение не попадает в диапазон!\n";
        isCorrect = false;
    }
    else
        isCorrect = true;
    return isCorrect;
}
int readUserO(int num, int MIN, int MAX)
{
    bool isCorrect;
    do
    {
        std::cout << "Введите порядок матрицы N[" << MIN << "; " << MAX << "]: ";
        isCorrect = true;
        std::cin >> num;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cin.clear();
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect)
            isCorrect = checkUserArea(num, MIN, MAX);
    } while (!isCorrect);
    return num;
}
int readUserMatrix(int num, int row, int col, int MIN, int MAX)
{
    bool isCorrect;
    do
    {
        std::cout << "Введите в " << (row + 1) << " строке " << (col + 1) << " столбце элемент[" << MIN << "; " << MAX << "]: ";
        isCorrect = true;
        std::cin >> num;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cin.clear();
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect)
            isCorrect = checkUserArea(num, MIN, MAX);
    } while (!isCorrect);
    return num;
}
std::string readPathFile()
{
    bool isCorrect;
    int len;
    std::string pathToFile;
    do
    {
        std::cout << "Введите путь к файлу с расширением .txt с матрицей, у которой разряд не должен превышать " << MAX_O << ", а её элементы должны лежать в пределе[" << MIN_MAT << ":" << MAX_MAT << "]: ";
        std::cin >> pathToFile;
        len = pathToFile.length();
        if (len > 4 && pathToFile.substr(len - 4, 4) == ".txt")
            isCorrect = true;
        else
        {
            isCorrect = false;
            std::cout << "Расширение файла не .txt!\n";
        }    
    } while (!isCorrect);
    return pathToFile;
}
bool isExists(std::string pathToFile)
{
    bool isCorrect;
    std::ifstream file(pathToFile);
    if (file.good())
        isCorrect = true;
    else
        isCorrect = false;
    file.close();
    return isCorrect;
}
bool isAbleToReading(std::string pathToFile)
{
    bool isCorrect;
    std::ifstream file(pathToFile);
    if (file.is_open())
        isCorrect = true;
    else
        isCorrect = false;
    file.close();
    return isCorrect;
}
bool isAbleToWriting(std::string pathToFile)
{
    bool isCorrect;
    std::ofstream file(pathToFile, std::ios::app);
    if (file.is_open())
        isCorrect = true;
    else
        isCorrect = false;
    file.close();
    return isCorrect;
}
bool isEmpty(std::string pathToFile)
{
    bool isCorrect;
    std::ifstream file(pathToFile);
    if (file.peek() == std::ifstream::traits_type::eof())
        isCorrect = true;
    else
        isCorrect = false;
    file.close();
    return isCorrect;
}
int readOrder(std::string pathToFile)
{
    int order;
    bool isCorrect;
    isCorrect = true;
    std::ifstream file(pathToFile);
    file >> order;
    if (file.fail())
    {
        isCorrect = false;
        file.clear();
        std::cout << "Проверьте порядок матрицы!\n";
    }
    if (isCorrect)
        isCorrect = checkUserArea(order, MIN_O, MAX_O);
    if (isCorrect)
    {
        if (file.peek() != '\n')
        {
            isCorrect = false;
            std::cout << "Неправильный порядок матрицы!\n";
        }
    }
    file.close();
    if (!isCorrect)
        order = -1;
    return order;
}
bool isRightNums(std::string pathToFile)
{
    int k;
    bool isCorrect;
    std::ifstream file(pathToFile);
    file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    isCorrect = true;
    while (isCorrect && !file.eof())
    {
        while (isCorrect && file.peek() != '\n' && !file.eof())
        {
            file >> k;
            if (file.fail())
            {
                isCorrect = false;
                std::cout << "Некорректный тип данных внутри файла!\n";
                file.clear();
            }
            if (isCorrect)
                isCorrect = checkUserArea(k, MIN_MAT, MAX_MAT);
        }
        file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    }
    file.close();
    return isCorrect;
}
bool isOrdersEqual(std::string pathToFile, int order)
{
    int rows, cols, k;
    bool isCorrect;
    rows = 0;
    std::ifstream file(pathToFile);
    file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    isCorrect = true;
    while (isCorrect && !file.eof())
    {
        cols = 0;
        while (isCorrect && file.peek() != '\n' && !file.eof())
        {
            file >> k;
            cols++;
            isCorrect = checkUserArea(cols, MIN_O, MAX_O);
        }
        if (isCorrect)
        {
            file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            rows++;
            isCorrect = checkUserArea(rows, MIN_O, MAX_O);
            if (cols != order)
                isCorrect = false;
        }
    }
    file.close();
    if (isCorrect && rows != order)
        isCorrect = false;
    return isCorrect;
}
int** readFileMatrix(std::string pathToFile, int order)
{
    int row, col;
    int** matrix;
    row = 0;
    col = 0;
    std::ifstream file(pathToFile);
    file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    matrix = new int* [order];
    while (row < order)
    {
        matrix[row] = new int[order];
        while (col < order)
        {
            file >> matrix[row][col];
            col++;
        }
        file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        col = 0;
        row++;
    }
    file.close();
    return matrix;
}
int** readFile(std::string& pathToFile, int& order)
{
    bool isCorrect;
    int** matrix = new int*[0];
    isCorrect = true;
    do
    {
        pathToFile = readPathFile();
        if (!isExists(pathToFile))
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода пути к файлу!\n";
        }
        if (isCorrect && !isAbleToReading(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл закрыт для чтения!\n";
        }
        if (isCorrect && !isAbleToWriting(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл закрыт для записи!\n";
        }
        if (isCorrect && isEmpty(pathToFile))
        {
            isCorrect = false;
            std::cout << "Файл пуст!\n";
        }
        if (isCorrect)
        {
            order = readOrder(pathToFile);
            if (order == -1)
                isCorrect = false;
        }
        if (isCorrect && !isRightNums(pathToFile))
            isCorrect = false;
        if (isCorrect && !isOrdersEqual(pathToFile, order))
        {
            isCorrect = false;
            std::cout << "Значения порядков не равны!\n";
        }
    } while (!isCorrect);
    matrix = readFileMatrix(pathToFile, order);
    return matrix;
}
void addAnswerToFile(std::string pathToFile, int countCorrectRows)
{
    std::cout << pathToFile;
    std::ofstream file(pathToFile, std::ios::app);
    file << "\nКоличество строк, имеющих перестановки элементов от 1 до порядка матрицы: " << countCorrectRows;
    file.close();
}
int** readConsole(int& order)
{
    order = readUserO(order, MIN_O, MAX_O);
    int** matrix = new int* [order];
    for (int i = 0; i < order; i++)
        matrix[i] = new int[order];
    for (int row = 0; row < order; row++)
        for (int col = 0; col < order; col++)
            matrix[row][col] = readUserMatrix(matrix[row][col], row, col, MIN_MAT, MAX_MAT);
    return matrix;
}
int** sort(int** matrix, int order)
{
    int previousMaxIndex, buf;
    previousMaxIndex = order - 1;
    for (int row = 0; row < order; row++)
        for (int i = 0; i < order; i++)
            for (int j = 0; j < previousMaxIndex - i; j++)
                if (matrix[row][j] > matrix[row][j + 1])
                {
                    buf = matrix[row][j];
                    matrix[row][j] = matrix[row][j + 1];
                    matrix[row][j + 1] = buf;
                }
    return matrix;
}
int calcCorrectRows(int** matrix, int order)
{
    int i, count;
    bool isCorrect;
    isCorrect = true;
    count = 0;
    for (int row = 0; row < order; row++)
    {
        i = 0;
        while (isCorrect && i < order)
        {
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
void freeMemory(int**& matrix, int order)
{
    for (int i = 0; i < order; i++)
        delete[] matrix[i];
    delete[] matrix;
}
int getResultFromFile()
{
    int order;
    std::string pathToFile;
    int** matrix;
    int countCorrectRows;
    matrix = readFile(pathToFile, order);
    matrix = sort(matrix, order);
    countCorrectRows = calcCorrectRows(matrix, order);
    freeMemory(matrix, order);
    addAnswerToFile(pathToFile, countCorrectRows);
    return countCorrectRows;
}
int getResultFromConsole()
{
    int order;
    int** matrix;
    int countCorrectRows;
    matrix = readConsole(order);
    matrix = sort(matrix, order);
    countCorrectRows = calcCorrectRows(matrix, order);
    freeMemory(matrix, order);
    return countCorrectRows;
}
void printResult(int count)
{
    std::cout << "\nКоличество строк, имеющих перестановки элементов от 1 до N: " << count;
}
int main()
{
    setlocale(LC_ALL, "RU");
    int countCorrectRows;
    printTask();
    if (chooseFileInput())
        countCorrectRows = getResultFromFile();
    else
        countCorrectRows = getResultFromConsole();
    printResult(countCorrectRows);
    return 0;
}

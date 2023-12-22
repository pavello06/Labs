#include <iostream>

const int MIN_P = 1, MAX_P = 1000000000, DIGIT = 10;

void outputTask()
{
    std::cout << "Данная программа подсчитывает сумму цифр введённого натурального числа.\n\n";
}

void area(int MIN, int MAX)
{
    std::cout << "Введите натуральное чиcло p[" << MIN << "; " << MAX << "]: ";
}  

void errorOfArea()
{
    std::cout << "Значение не попадает в диапазон!\n";
}

void errorOfType()
{
    std::cout << "Проверьте корректность ввода данных!\n";
}

int check(int MIN, int MAX)
{
    bool isIncorrect;
    int num;
    do
    {
        isIncorrect = false;
        area(MIN, MAX);
        std::cin >> num;
        if (std::cin.fail())
        {
            isIncorrect = true;
            errorOfType();
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            errorOfType();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((num < MIN) || (num > MAX)))
        {
            isIncorrect = true;
            errorOfArea();
        }
    } while (isIncorrect);
    return num;
}

int calcSumOfNums(int num)
{
    int sum;
    sum = 0;
    while (num > 0)
    {
        sum += num % 10;
        num = round(num / DIGIT);
    }
    return sum;
}

void outputRes(int sum)
{
    std::cout << "\nСумма цифр числа: " << sum;
}

int main()
{
    int p;
    setlocale(LC_ALL, "RU");
    outputTask();
    p = check(MIN_P, MAX_P);
    outputRes(calcSumOfNums(p));
    return 0;
}

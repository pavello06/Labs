#include <iostream>

int main() 
{
    setlocale(LC_ALL, "RU");

    int length;
    float x0 = 0, xn = 0, x = 0, h = 0, y, a = 0, b = 0, c = 0;
    bool isIncorrect;
    const int MIN_H = 1, MIN_ALL = -1000, MAX_ALL = 1000;;

    std::cout << "Данная программа считает значение функции y = a*x^2 + b*x + c, от x0 до xn с шагом h.\n\n";

    do 
    {
        isIncorrect = false;
        std::cout << "Введите значение x0[-1000; 1000]: ";
        std::cin >> x0;
        if (std::cin.fail())
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((x0 < MIN_ALL) || (x0 > MAX_ALL)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);
    do
    {
        isIncorrect = false;
        std::cout << "Введите значение xn (значение xn[-1000; 1000] должно быть больше чем x0): ";
        std::cin >> xn;
        if (std::cin.fail())
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((x0 > xn) || (xn > MAX_ALL)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);
    do
    {
        isIncorrect = false;
        std::cout << "Введите шаг h[1; 1000]: ";
        std::cin >> h;
        if (std::cin.fail())
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((h < MIN_H) || (h > MAX_ALL)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);
    do
    {
        isIncorrect = false;
        std::cout << "Введите коэффициент a[-1000; 1000]: ";
        std::cin >> a;
        if (std::cin.fail())
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((a < MIN_ALL) || (a > MAX_ALL)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);
    do
    {
        isIncorrect = false;
        std::cout << "Введите коэффициент b[-1000; 1000]: ";
        std::cin >> b;
        if (std::cin.fail())
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((b < MIN_ALL) || (b > MAX_ALL)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);
    do
    {
        isIncorrect = false;
        std::cout << "Введите коэффициент c[-1000; 1000]: ";
        std::cin >> c;
        if (std::cin.fail())
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && std::cin.get() != '\n')
        {
            isIncorrect = true;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (!isIncorrect && ((c < MIN_ALL) || (c > MAX_ALL)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);

    length = round((xn - x0 + 1) / h);
    float *ArrX = new float[length];
    float *ArrY = new float[length];

    x = x0;

    for (int i = 0; i < length; i++)
    {
        y = a * x * x + b * x + c;
        ArrX[i] = x;
        ArrY[i] = y;
        std::cout << "x: " << ArrX[i] << "; y: " << ArrY[i] << "\n";
        x += h;
    }

    return 0;
}

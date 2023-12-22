#include <iostream>
int main()
{
    setlocale(LC_ALL, "RU");
    float r = 0, x = 0, y = 0;
    char onCircle;
    bool isCorrect;
    const int
        MIN_R = 1,
        MIN_XY = -100000,
        MAX_ALL = 100000;
    const float
        EPS = 0.0000001f;
    r = 0;
    x = 0;
    y = 0;
    isCorrect = true;
    std::cout << "Данная программа проверяет, находится ли точка с координатами(x; y) на окружности радиусом r с центром в начале координат.\n\n";
    do
    {
        isCorrect = true;
        std::cout << "Введите радиус окружности r[1; 100000]: ";
        std::cin >> r;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && ((r < MIN_R) || (r > MAX_ALL)))
        {
            isCorrect = false;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (!isCorrect);
    do
    {
        isCorrect = true;
        std::cout << "Введите координату x[-100000; 100000] точки: ";
        std::cin >> x;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && ((x < MIN_XY) || (x > MAX_ALL)))
        {
            isCorrect = false;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (!isCorrect);
    do
    {
        isCorrect = true;
        std::cout << "Введите координату y[-100000; 100000] точки: ";
        std::cin >> y;
        if (std::cin.fail())
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            std::cin.clear();
            while (std::cin.get() != '\n');
        }
        if (isCorrect && std::cin.get() != '\n')
        {
            isCorrect = false;
            std::cout << "Проверьте корректность ввода данных!\n";
            while (std::cin.get() != '\n');
        }
        if (isCorrect && ((y < MIN_XY) || (y > MAX_ALL)))
        {
            isCorrect = false;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (!isCorrect);
    r = round(r * 1000) / 1000;
    x = round(x * 1000) / 1000;
    y = round(y * 1000) / 1000;
    if (abs(r * r - x * x + y * y) < EPS)
    {
        std::cout << "\nТочка принадлежит окружности";
        onCircle = 'T';
    }
    else
    {
        std::cout << "\nТочка не принадлежит окружности";
        onCircle = 'F';
    }
    return 0;
}

#include <iostream>

using namespace std;
int main() 
{
    setlocale(LC_ALL, "RU");

    float x = 0, EPS = 0;
    double num, sin;
    int j, i, n;
    bool isIncorrect;
    const int MIN_EPS = 0, MAX_EPS = 1, MIN_X = -1000, MAX_X = 1000;

    std::cout << "Данная программа считает значение функции f(x) = sin^2(x) для введённого значения x, а также подсчитывает количество чисел из ряда Маклорена больших EPS.\n\n";

    do 
    {
        isIncorrect = false;
        cout << "Введите EPS(0; 1): ";
        cin >> EPS;
        if (cin.fail())
        {
            isIncorrect = true;
            cout << "Проверьте корректность ввода данных!" << endl;
            cin.clear();
            cout << "Введите EPS(0; 1): ";
            while (cin.get() != '\n');
        }
        if (!isIncorrect && ((EPS <= MIN_EPS) || (EPS >= MAX_EPS)))
        {
            isIncorrect = true;
            cout << "Значение не попадает в диапазон!" << endl;
        }
        if (cin.get() != '\n')
        {
            cout << "Проверьте корректность ввода данных!";
            isIncorrect = true;
            cin.clear();
            while (cin.get() != '\n');
            cout << endl;
        }
    } while (isIncorrect);
    do 
    {
        isIncorrect = false;
        cout << "Введите x(-1000; 1000): ";
        cin >> x;
        if (cin.fail())
        {
            isIncorrect = true;
            cout << "Проверьте корректность ввода данных!" << endl;
            cin.clear();
            cout << "Введите x(-1000; 1000): ";
            while (cin.get() != '\n');
        }
        if (!isIncorrect && ((x <= MIN_X) || (x >= MAX_X)))
        {
            isIncorrect = true;
            cout << "Значение не попадает в диапазон!" << endl;
        }
        if (cin.get() != '\n')
        {
            cout << "Проверьте корректность ввода данных!";
            isIncorrect = true;
            cin.clear();
            while (cin.get() != '\n');
            cout << endl;
        }
    } while (isIncorrect);

    sin = 0;
    n = 0;
    num = 0;

    j = 1;
    while (j == 1) 
    {
        sin += num;
        num = 1;
        n++;
        for (i = 1; i <= n - 1; i++)
            num *= (-1);
        for (i = 1; i <= 2 * n - 1; i++)
            num *= 2;
        for (i = 1; i <= 2 * n; i++)
            num *= x;
        for (i = 1; i <= 2 * n; i++)
            num /= i;

        if (num < 0) 
        {
            if (-num < EPS)
                j = 0;
        }
        else 
        {
            if (num < EPS)
                j = 0;
        }
    }

    cout << "sin: " << sin << "; n: " << n;

    return 0;
}

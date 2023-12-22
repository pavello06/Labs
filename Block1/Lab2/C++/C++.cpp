#include <iostream>

using namespace std;
int main()
{
    setlocale(LC_ALL, "RU");

    int n = 0, value1, value2;
    bool isIncorrect;
    const int MIN_N = 0, MAX_N = 1000;

    cout << "Данная программа проверяет, работает ли формула 1 + 2 + ... + n = n * (n + 1) / 2.\n\n";

    do 
    {
        isIncorrect = false;
        cout << "Введите натуральное число n(0; 1000) для проверки: ";
        cin >> n;
        if (cin.fail())
        {
            isIncorrect = true;
            cout << "Проверьте корректность ввода данных!" << endl;
            cin.clear();
            cout << "Введите натуральное число n(0; 1000) для проверки: ";
            while (cin.get() != '\n');
        }
        if (!isIncorrect && ((n <= MIN_N) || (n >= MAX_N)))
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

    value1 = 0;
    for (int i = 1; i <= n; i++)
        value1 += i;

    value2 = n * (n + 1) / 2;

    if (value1 == value2)
        cout << "Формула работает.";
    else
        cout << "Формула не работает.";

    return 0;
}


#include <iostream>



int main()
{
	setlocale(LC_ALL, "RU");
	int length, i, countOfOdd;
	bool isIncorrect;
	const int MIN_L = 1, MAX_L = 10000, MIN_ARR_EL = -1000000, MAX_ARR_EL = 1000000;
	std::cout << "Данная программа подсчитывает число нечётных чисел в масссиве с чётными порядковыми номерами.\n\n";
    do
    {
        isIncorrect = false;
        std::cout << "Введите число элементов в массиве[1; 10000]: ";
        std::cin >> length;
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
        if (!isIncorrect && ((length < MIN_L) || (length > MAX_L)))
        {
            isIncorrect = true;
            std::cout << "Значение не попадает в диапазон!\n";
        }
    } while (isIncorrect);
    int* arr = new int[length];
    for (i = 0; i < length; i++)
    {
        do
        {
            isIncorrect = false;
            std::cout << "Введите " << i + 1 << " элемент массива[-1000000; 1000000]: ";
            std::cin >> arr[i];
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
            if (!isIncorrect && ((arr[i] < MIN_ARR_EL) || (arr[i] > MAX_ARR_EL)))
            {
                isIncorrect = true;
                std::cout << "Значение не попадает в диапазон!\n";
            }
        } while (isIncorrect);
    }
    i = 1;
    countOfOdd = 0;
    while (i < length)
    {
        if (arr[i] % 2 == 1)
            countOfOdd++;
        i += 2;
    }
    delete[] arr;
    std::cout << "\nЧисло нечётных чисел в массиве с чётными порядковыми номерами: " << countOfOdd;
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double num, sin, x = 0, EPS = 0;
        int n, i, j;
        boolean isIncorrect;
        final int MIN_EPS = 0, MAX_EPS = 1, MIN_X = -1000, MAX_X = 1000;

        System.out.println("Данная программа считает значение функции f(x) = sin^2(x) для введённого значения x, а также подсчитывает количество чисел из ряда Маклорена больших EPS.\n");

        do {
            isIncorrect = false;
            System.out.print("Введите EPS (0; 1): ");
            try {
                EPS = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((EPS < MIN_EPS) || (EPS > MAX_EPS)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        do {
            isIncorrect = false;
            System.out.print("Введите x(-1000; 1000): ");
            try {
                x = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((x < MIN_X) || (x > MAX_X)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);

        sin = 0;
        n = 0;
        num = 0;

        j = 1;
        while (j == 1) {
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

            if (num < 0) {
                if (-num < EPS)
                    j = 0;
            }
            else {
                if (num < EPS)
                    j = 0;
            }
        }

        System.out.println("sin: " + sin + "; n: " + n);
        scanner.close();
    }
}

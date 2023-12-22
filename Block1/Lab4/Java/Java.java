import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length;
        double x0 = 0, xn = 0, x, h = 0, y, a = 0, b = 0, c = 0;
        boolean isIncorrect;
        final int MIN_H = 1, MIN_ALL = -1000, MAX_ALL = 1000;

        System.out.println("Данная программа считает значение функции y = a*x^2 + b*x + c, от x0 до xn с шагом h.\n");

        do {
            isIncorrect = false;
            System.out.print("Введите значение x0[-1000; 1000]: ");
            try {
                x0 = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((x0 < MIN_ALL) || (x0 > MAX_ALL)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        do {
            isIncorrect = false;
            System.out.print("Введите значение xn (значение xn[-1000; 1000] должно быть больше чем x0): ");
            try {
                xn = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((xn < MIN_ALL) || (xn > MAX_ALL) || (x0 > xn)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        do {
            isIncorrect = false;
            System.out.print("Введите шаг h[1; 1000]: ");
            try {
                h = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((h < MIN_H) || (h > MAX_ALL)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        do {
            isIncorrect = false;
            System.out.print("Введите коэффициент a[-1000; 1000]: ");
            try {
                a = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((a < MIN_ALL) || (a > MAX_ALL)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        do {
            isIncorrect = false;
            System.out.print("Введите коэффициент b[-1000; 1000]: ");
            try {
                b = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((b < MIN_ALL) || (b > MAX_ALL)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        do {
            isIncorrect = false;
            System.out.print("Введите коэффициент c[-1000; 1000]: ");
            try {
                c = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((c < MIN_ALL) || (c > MAX_ALL)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);
        scanner.close();

        length = (int) (Math.floor((xn - x0 + 1) / h));
        double ArrX[] = new double[length];
        double ArrY[] = new double[length];

        x = x0;

        for (int i = 0; i < length; i++) {
            y = a * x * x + b * x + c;
            ArrX[i] = x;
            ArrY[i] = y;
            System.out.println("x: " + ArrX[i] + "; y: " + ArrY[i]);
            x += h;
        }
    }
}

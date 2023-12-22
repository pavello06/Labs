import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0, value1, value2;
        boolean isIncorrect;
        final int MIN_N = 0, MAX_N = 1000;

        System.out.println("Данная программа проверяет, работает ли формула 1 + 2 + ... + n = n * (n + 1) / 2.\n");

        do {
            isIncorrect = false;
            System.out.print("Введите натуральное число n(0; 1000) для проверки: ");
            try {
                n = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isIncorrect = true;
            }
            if (((n < MIN_N) || (n > MAX_N)) && !isIncorrect) {
                isIncorrect = true;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (isIncorrect);

        value1 = 0;
        for (int i = 1; i <= n; i++)
            value1 += i;

        value2 = n * (n + 1) / 2;

        if (value1 == value2)
            System.out.println("Формула работает.");
        else
            System.out.println("Формула не работает.");
        scanner.close();
    }
}

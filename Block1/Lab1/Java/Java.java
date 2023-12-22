import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double r, x, y;
        char onCircle;
        boolean isCorrect;
        final int
                MIN_R = 1,
                MIN_XY = -100000,
                MAX_ALL = 100000;
        final float
                EPS = 0.0000001f;
        r = 0;
        x = 0;
        y = 0;
        isCorrect = true;
        System.out.println("Данная программа проверяет, находится ли точка с координатами(x; y) на окружности радиусом r с центром в начале координат.\n");
        do {
            isCorrect = true;
            System.out.print("Введите радиус окружности r[1; 100000]: ");
            try {
                r = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isCorrect = false;
            }
            if (isCorrect && ((r < MIN_R) || (r > MAX_ALL))) {
                isCorrect = false;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (!isCorrect);
        do {
            isCorrect = true;
            System.out.print("Введите координату x[-100000; 100000] точки: ");
            try {
                x = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isCorrect = false;
            }
            if (isCorrect && ((x < MIN_XY) || (x > MAX_ALL))) {
                isCorrect = false;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (!isCorrect);
        do {
            isCorrect = true;
            System.out.print("Введите координату y[-100000; 100000] точки: ");
            try {
                y = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте корректность ввода данных!");
                isCorrect = false;
            }
            if (isCorrect && ((y < MIN_XY) || (y > MAX_ALL))) {
                isCorrect = false;
                System.out.println("Значение не попадает в диапазон!");
            }
        } while (!isCorrect);
        scanner.close();
        r = Math.round(r * 1000) / 1000.0;
        x = Math.round(x * 1000) / 1000.0;
        y = Math.round(y * 1000) / 1000.0;
        if (Math.abs(r * r - x * x + y * y) < EPS) {
            System.out.println("Точка принадлежит окружности");
            onCircle = 'T';
        }
        else {
            System.out.println("Точка не принадлежит окружности");
            onCircle = 'F';
        }
    }
}

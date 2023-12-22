import java.util.Scanner;

public class Main {
    public static final int MIN_P = 1, MAX_P = 1000000000, DIGIT = 10;
    static Scanner scanner = new Scanner(System.in);

    public static void outputTask() {
        System.out.println("������ ��������� ������������ ����� ���� ��������� ������������ �����.\n");
    }

    public static void area(int MIN, int MAX) {
        System.out.print("������� ����������� ��c�� p[" + MIN + "; " + MAX + "]: ");
    }

    public static void errorOfArea() {
        System.out.println("�������� �� �������� � ��������!");
    }

    public static void errorOfType() {
        System.out.println("��������� ������������ ����� ������!");
    }

    public static int check(int MIN, int MAX) {
        boolean isIncorrect;
        int num = 0;
        do {
            isIncorrect = false;
            area(MIN, MAX);
            try {
                num = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                errorOfType();
                isIncorrect = true;
            }
            if (!isIncorrect && ((num < MIN) || (num > MAX))) {
                isIncorrect = true;
                errorOfArea();
            }
        } while (isIncorrect);
        return num;
    }

    public static int calcSumOfNums(int num) {
        int sum;
        sum = 0;
        while (num > 0)
        {
            sum += num % 10;
            num = Math.round(num / DIGIT);
        }
        return sum;
    }

    public static void outputRes(int sum) {
        System.out.print("\n����� ���� �����: " + sum);
    }

    public static void main(String[] args) {
        int p;
        outputTask();
        p = check(MIN_P, MAX_P);
        outputRes(calcSumOfNums(p));
        scanner.close();
    }
}

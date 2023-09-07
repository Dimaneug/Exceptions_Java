package home2;

import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        readFloat();
    }

    public static float readFloat() {
        float num;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите вещественное число: ");
            if (scanner.hasNextFloat()) {
                num = scanner.nextFloat();
                scanner.close();
                return num;
            } else {
                scanner.nextLine();
            }
        }
    }
}

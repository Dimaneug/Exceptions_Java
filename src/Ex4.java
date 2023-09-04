import java.util.Scanner;

public class Ex4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите не пустую строку: ");
        String line = scanner.nextLine();
        if (line.isEmpty())
            throw new RuntimeException("Вы ввели пустую строку!");
    }

}

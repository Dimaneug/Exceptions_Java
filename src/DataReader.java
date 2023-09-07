/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
 */

import exceptions.DateOfBirthNotFound;
import exceptions.NotEnoughNames;
import exceptions.PhoneNumberNotFound;
import exceptions.SexNotFound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataReader {
    public static void main(String[] args) {
        String[] data = getPersonData();
        writePerson(data);
    }

    public static String[] getPersonData() {
        String[] data;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите данные:");
            data = scanner.nextLine().split(" ");

            int ans = checkArgumentsAmount(data);
            if (ans == -1) {
                System.out.println("Недостаточно аргументов!");
                continue;
            } else if (ans == 1) {
                System.out.println("Слишком много аргументов");
                continue;
            }
            try {
                data = parseData(data);
                break;
            } catch (PhoneNumberNotFound | DateOfBirthNotFound | SexNotFound | NotEnoughNames e) {
                System.out.println(e.getMessage());
            }

        }

        return data;
    }

    // Функция для выполнения тех задания с кодом ошибки :)
    public static int checkArgumentsAmount(String[] data) {
        return Integer.compare(data.length, 6);
    }

    public static String[] parseData(String[] data) throws PhoneNumberNotFound, DateOfBirthNotFound, SexNotFound, NotEnoughNames {
        String date = null;
        String phone = null;
        String sex = null;
        String[] names = {null, null, null}; // подразумеваем ввод в следующем порядке: фамилия, имя, отчество
        short counter = 0;                   // в ином случае мы не можем определить где фамилия, а где имя
        for (String item : data
        ) {
            // проверка на дату
            Pattern datePattern = Pattern.compile("^\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d$");
            Matcher dateMatch = datePattern.matcher(item);
            if (dateMatch.find()) {
                if (isValidDate(item)) {
                    date = item;
                    continue;
                }
            }

            // проверка на номер телефона
            Pattern phonePattern = Pattern.compile("^\\d+$");
            Matcher phoneMatch = phonePattern.matcher(item);
            if (phoneMatch.find()) {
                phone = item;
                continue;
            }

            // проверка на пол
            Pattern sexPattern = Pattern.compile("^[fm]$");
            Matcher sexMatch = sexPattern.matcher(item);
            if (sexMatch.find()) {
                sex = item;
                continue;
            }

            // проверка на фамилию, имя и отчество
            Pattern namePattern = Pattern.compile("^[a-z]+$");
            Matcher nameMatch = namePattern.matcher(item);
            if (nameMatch.find() && counter < 3) {
                names[counter] = item;
                counter++;
            }
        }

        if (date == null) {
            throw new DateOfBirthNotFound("Не введена дата рождения");
        }
        if (phone == null) {
            throw new PhoneNumberNotFound("Не введён номер телефона");
        }
        if (sex == null) {
            throw new SexNotFound("Не введён пол");
        }
        if (names[0] == null) {
            throw new NotEnoughNames("Вы не ввели фамилию, имя и отчество");
        } else if (names[1] == null) {
            throw new NotEnoughNames("Вы не ввели имя и отчество");
        } else if (names[2] == null) {
            throw new NotEnoughNames("Вы не ввели отчество");
        }

        System.out.println("OK");
        return new String[]{names[0], names[1], names[2], date, phone, sex};
    }

    public static boolean isValidDate(String item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        try {
            LocalDate.parse(item, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка в дате");
            return false;
        }
        return true;
    }

    public static void writePerson(String[] data) {

        File file = new File(data[0]);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Проблема с чтением-записью файла\n" + e);
        }

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write("<" + data[0] + ">");
            fw.write("<" + data[1] + ">");
            fw.write("<" + data[2] + ">");
            fw.write("<" + data[3] + "> ");
            fw.write("<" + data[4] + ">");
            fw.write("<" + data[5] + ">\n");
        } catch (IOException e) {
            throw new RuntimeException("Проблема с чтением-записью файла\n" + e);
        }
    }
}

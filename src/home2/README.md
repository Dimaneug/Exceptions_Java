## Задание 2
```java
try {
    int d = 0;
    double catchedRes1 = intArray[8] / d;
    System.out.println("catchedRes1 = " + catchedRes1);
} catch (ArithmeticException e) {
    System.out.println("Catching exception: " + e);
}
```

Допустим, что intArray у нас где-то существует. В таком случае проблема в том, что мы всегда будем
 получать исключение из-за деления на ноль.\
В итоге, как один из вариантов решения, необходимо изменить значение d на отличное от 0.

## Задание 3

```java
public static void main(String[] args) throws Exception {
    try {
        int a = 90;
        int b = 3;
        System.out.println(a / b);
        printSum(23, 234);
        int[] abc = { 1, 2 };
        abc[3] = 9;
    } catch (Throwable ex) {
        System.out.println("Что-то пошло не так...");   
    } catch (NullPointerException ex) {
        System.out.println("Указатель не может указывать на null!");
    } catch (IndexOutOfBoundsException ex) {
        System.out.println("Массив выходит за пределы своего размера!");
    }
}

public static void printSum(Integer a, Integer b) throws FileNotFoundException {
    System.out.println(a + b);
}
```

У метода printSum не нужно писать никакой throws, так как в нём нет обработки исключений.\
В main можно убрать обработку Throwable и NullPointerException, потому что у нас может возникнуть
 только одна ошибка - IndexOutOfBounds.\
К слову, она будет возникать всегда, так как мы создали массив из 2-х элементов и обращаемся к 4-му.
### Исправленный код
```java
public static void main(String[] args) throws Exception {
    try {
        int a = 90;
        int b = 3;
        System.out.println(a / b);
        printSum(23, 234);
        int[] abc = { 1, 2 };
        abc[3] = 9;
    } catch (IndexOutOfBoundsException ex) {
        System.out.println("Массив выходит за пределы своего размера!");
    }
}
    
public static void printSum(Integer a, Integer b) {
    System.out.println(a + b);
}
```

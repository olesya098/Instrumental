import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//Класс DebugHelper предназначен для централизованного управления логированием
class DebugHelper {
    // Получаем экземпляр логгера для текущего класса DebugHelper
    private static final Logger logger = Logger.getLogger(DebugHelper.class.getName());

    static {
        try {
            // Настройка логгера для записи в файл app.log
            FileHandler fileHandler = new FileHandler("app.log", true);
            // Устанавливаем текстовый формат
            fileHandler.setFormatter(new SimpleFormatter());
            // Добавляем обработчик файла к логгеру, чтобы записи шли в файл
            logger.addHandler(fileHandler);

            // Отключаем вывод логов в консоль
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            // Обработка ошибки при настройке логгера
            System.err.println("Не удалось настроить логгер: " + e.getMessage());
        }
    }


    // Записывает информационное сообщение в лог.

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    //Записывает предупреждающее сообщение в лог.

    public static void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }


     //Записывает сообщение об ошибке (серьезное)

    public static void logSevere(String message, Throwable e) {
        logger.log(Level.SEVERE, message, e);
    }


     //Записывает детальное отладочное сообщение в лог.
    public static void logFine(String message) {
        logger.log(Level.FINE, message);
    }
}

public class Main {

    public static int bitMultiply(int a, int b) {
        DebugHelper.logInfo("Вызов bitMultiply(a=" + a + ", b=" + b + ")");
        int result = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                result += a;
                DebugHelper.logFine("Промежуточный результат (битовое сложение): " + result);
            }
            a <<= 1;
            b >>= 1;
        }
        DebugHelper.logInfo("Результат bitMultiply: " + result);
        return result;
    }

    public static int arrayMultiply(int a, int b) {
        DebugHelper.logInfo("Вызов arrayMultiply(a=" + a + ", b=" + b + ")");
        if (b < 0) {
            DebugHelper.logWarning("Отрицательное значение b=" + b + ", будет использован модуль");
            b = Math.abs(b);
        }
        int[] array = new int[b];
        Arrays.fill(array, a);
        int result = 0;
        for (int num : array) {
            result += num;
        }
        DebugHelper.logInfo("Результат arrayMultiply: " + result);
        return result;
    }

    public static int recursiveMultiply(int a, int b) {
        DebugHelper.logInfo("Вызов recursiveMultiply(a=" + a + ", b=" + b + ")");
        if (b == 0) {
            return 0;
        }
        if (b < 0) {
            DebugHelper.logWarning("Отрицательное значение b=" + b + ", будет использован рекурсивный вызов с -b");
            return -recursiveMultiply(a, -b);
        }
        return a + recursiveMultiply(a, b - 1);
    }

    public static int loopMultiply(int a, int b) {
        DebugHelper.logInfo("Вызов loopMultiply(a=" + a + ", b=" + b + ")");
        int result = 0;
        boolean isNegative = false;
        if (b < 0) {
            isNegative = true;
            b = -b;
        }
        for (int i = 0; i < b; i++) {
            result += a;
            if ((a > 0 && result < 0) || (a < 0 && result > 0)) {
                DebugHelper.logSevere("Переполнение int в loopMultiply!", new ArithmeticException("Integer overflow"));
                throw new ArithmeticException("Integer overflow");
            }
        }
        if (isNegative) {
            result = -result;
        }
        DebugHelper.logInfo("Результат loopMultiply: " + result);
        return result;
    }

    public static void main(String[] args) {
        DebugHelper.logInfo("Запуск программы");
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Введите число a:");
            int a = in.nextInt();
            System.out.println("Введите число b:");
            int b = in.nextInt();
            System.out.println("Выберите способ умножения:");
            System.out.println("1. Умножение через битовые операции");
            System.out.println("2. Умножение через массив");
            System.out.println("3. Рекурсивное умножение");
            System.out.println("4. Умножение с использованием циклов");
            int select = in.nextInt();
            int result;
            switch (select) {
                case 1:
                    result = bitMultiply(a, b);
                    break;
                case 2:
                    result = arrayMultiply(a, b);
                    break;
                case 3:
                    result = recursiveMultiply(a, b);
                    break;
                case 4:
                    result = loopMultiply(a, b);
                    break;
                default:
                    System.out.println("Некорректный выбор.");
                    DebugHelper.logWarning("Некорректный выбор метода: " + select);
                    return;
            }
            System.out.println("Результат: " + result);
            DebugHelper.logInfo("Успешное завершение, результат: " + result);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            DebugHelper.logSevere("Ошибка при выполнении операции", e);
        } finally {
            DebugHelper.logInfo("Завершение работы программы");
        }
    }
}

/*
ошибочный

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // Ошибка синтаксиса: пропущена закрывающая скобка
    public static int Bit(int a, int b) {
        int result = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                result += a;
            }
            a <<= 1;
            b >>= 1;
            // Ошибка синтаксиса: забыли закрыть блок while
            return result;
        }

        // Логическая ошибка: при b=0 будет ArrayIndexOutOfBoundsException
        public static int Massiv(int a, int b) {
            int result = 0;
            int[] mass = new int[b+1]; // Ресурсная ошибка: создаем массив на 1 элемент больше
            Arrays.fill(mass, a);
            for (int i = 0; i <= b; i++) { // Логическая ошибка: условие i <= b
                result += mass[i];
            }
            return result;
        }

        // Компиляционная ошибка: рекурсия без базового случая для отрицательных b
        public static int Recursion(int a, int b) {
            if (b == 0) {
                return 0;
            }
            // Ошибка взаимодействия: не обрабатываем случай b < 0
            return a + Recursion(a, b - 1);
        }

        public static int Loop(int a, int b) {
            int result = 0;
            // Ресурсная ошибка: потенциальное переполнение при больших b
            for (int i = 0; i < b; i++) {
                result += a;
                if (result < 0) { // Проверка на переполнение
                    throw new ArithmeticException("Integer overflow");
                }
            }
            return result;
        }

        // Ошибка синтаксиса: пропущена точка с запятой
        public static void main(String[] args) {
            Scanner in = new Scanner(System.in)

            System.out.println("введите число a:");
            int a = in.nextInt();
            System.out.println("введите число b:");
            int b = in.nextInt();
            System.out.println("выберите способ умножения:"); // Ошибка взаимодействия: опечатка в System.out
            System.out.println("1. Умножение через битовые операции");
            System.out.println("2. Умножение через массив");
            System.out.println("3. Рекурсивное умножение");
            System.out.println("4. Умножение с использованием циклов");
            int select = in.nextInt();
            int result = 0;
            switch (select) {
                case 1:
                    result = Bit(a, b);
                    break;
                case 2:
                    result = Massiv(a, b);
                    break;
                case 3:
                    result = Recursion(a, b);
                    break;
                case 4:
                    result = Loop(a, b);
                    break;
                default:
                    System.out.println("некорректный выбор.");
                    return;
            }
            System.out.println("результат: " + result);
        }
    }


    ЧИСТЫЙ ВАРИАНТ

    import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int Bit(int a, int b) {
        int result = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                result += a;
            }
            a <<= 1;
            b >>= 1;
        }
        return result;
    }
    public static int Massiv(int a, int b) {
        int result = 0;
        int[] mass = new int[b];
        Arrays.fill(mass, a);
        for (int i = 0; i < b; i++) {
            result += mass[i];
        }
        return result;
    }

    public static int Recursion(int a, int b) {
        if (b == 0) {
            return 0;
        }
        if (b < 0) {
            return Recursion(a, -b);
        }
        return a + Recursion(a, b - 1);
    }

    public static int Loop(int a, int b) {
        int result = 0;
        for (int i = 0; i < b; i++) {
            result += a;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("введите число a:");
        int a = in.nextInt();
        System.out.println("введите число b:");
        int b = in.nextInt();
        System.out.println("выберите способ умножения:");
        System.out.println("1. Умножение через битовые операции");
        System.out.println("2. Умножение через массив");
        System.out.println("3. Рекурсивное умножение");
        System.out.println("4. Умножение с использованием циклов");
        int select = in.nextInt();
        int result = 0;
        switch (select) {
            case 1:
                result = Bit(a, b);
                break;
            case 2:
                result = Massiv(a, b);
                break;
            case 3:
                result = Recursion(a, b);
                break;
            case 4:
                result = Loop(a, b);
                break;
            default:
                System.out.println("некорректный выбор.");
                return;
        }
        System.out.println("результат: " + result);
    }
}
 */
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nМАТРИЦЫ КОМПЛЕКСНЫХ ЧИСЕЛ");
            System.out.println("1. cложить две матрицы");
            System.out.println("2. перемножить две матрицы");
            System.out.println("3. разделить две матрицы");
            System.out.println("4. транспонировать матрицу");
            System.out.println("5. вычислить определитель");
            System.out.println("0. выход");
            System.out.print("выберите операцию: ");

            int choice = readInt();

            if (choice == 0) {
                System.out.println("программа завершена.");
                break;
            }

            try {
                if (choice == 1) {
                    ComplexMatrix a = readMatrix("первой");
                    ComplexMatrix b = readMatrix("второй");

                    System.out.println("Результат:");
                    System.out.println(a.add(b));

                } else if (choice == 2) {
                    ComplexMatrix a = readMatrix("первой");
                    ComplexMatrix b = readMatrix("второй");

                    System.out.println("Результат:");
                    System.out.println(a.multiply(b));

                } else if (choice == 3) {
                    ComplexMatrix a = readMatrix("первой");
                    ComplexMatrix b = readMatrix("второй");

                    System.out.println("Результат:");
                    System.out.println(a.divide(b));

                } else if (choice == 4) {
                    ComplexMatrix a = readMatrix("");

                    System.out.println("транспонированная матрица:");
                    System.out.println(a.transpose());

                } else if (choice == 5) {
                    ComplexMatrix a = readMatrix("");

                    System.out.println(
                            "определитель: " + a.determinant()
                    );

                } else {
                    System.out.println(
                            "такой операции нет."
                    );
                }

            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println(
                        "не удалось выполнить операцию: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static ComplexMatrix readMatrix(String order) {         //считывает матрицу у пользователя
        if (!order.isEmpty()) {
            System.out.println("ввод " + order + " матрицы:");
        } else {
            System.out.println("ввод матрицы:");
        }

        System.out.print("количество строк: ");
        int rows = readInt();

        System.out.print("количество столбцов: ");
        int columns = readInt();

        ComplexMatrix result = new ComplexMatrix(rows, columns);

        System.out.println(
                "для каждого элемента введите действительную и мнимую часть"
        );

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.println(
                        "элемент [" + (i + 1) + "][" + (j + 1) + "]"
                );

                System.out.print("действительная часть: ");
                double real = readDouble();

                System.out.print("мнимая часть: ");
                double imaginary = readDouble();

                result.setElement(
                        i, j, new Complex(real, imaginary)
                );
            }
        }

        System.out.println("введенная матрица:");
        System.out.println(result);

        return result;
    }

    private static int readInt() {                            //чтение целого числа и защита от неправильного ввода
        while (!scanner.hasNextInt()) {
            System.out.print("введите целое число: ");
            scanner.next();
        }

        return scanner.nextInt();
    }

    private static double readDouble() {                                  // чтение вещественного числа и защита от неправильного ввода
        while (!scanner.hasNextDouble()) {
            System.out.print(
                    "введите число (через точку): "
            );

            scanner.next();
        }

        return scanner.nextDouble();
    }
}
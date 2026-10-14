import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ComplexMatrix lastResult = null;
        while (true) {
            System.out.println("\nМАТРИЦЫ КОМПЛЕКСНЫХ ЧИСЕЛ");
            System.out.println("1. cложить две матрицы");
            System.out.println("2. перемножить две матрицы");
            System.out.println("3. разделить две матрицы");
            System.out.println("4. транспонировать матрицу");
            System.out.println("5. вычислить определитель");
            System.out.println("6. применить операцию к последнему результату");
            System.out.println("7. показать последний результат");
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
                    lastResult = a.add(b);

                    System.out.println("Результат:");
                    System.out.println(lastResult);

                } else if (choice == 2) {
                    ComplexMatrix a = readMatrix("первой");
                    ComplexMatrix b = readMatrix("второй");
                    lastResult = a.multiply(b);

                    System.out.println("Результат:");
                    System.out.println(lastResult);

                } else if (choice == 3) {
                    ComplexMatrix a = readMatrix("первой");
                    ComplexMatrix b = readMatrix("второй");
                    lastResult = a.divide(b);

                    System.out.println("Результат:");
                    System.out.println(lastResult);

                } else if (choice == 4) {
                    ComplexMatrix a = readMatrix("");
                    lastResult = a.transpose();

                    System.out.println("транспонированная матрица:");
                    System.out.println(lastResult);

                } else if (choice == 5) {
                    ComplexMatrix a = readMatrix("");

                    System.out.println(
                            "определитель: " + a.determinant()
                    );

                } else if (choice == 6) {
                    lastResult = applyToLastResult(lastResult);
                    if (lastResult != null) {
                        System.out.println("новый результат:");
                        System.out.println(lastResult);
                    }

                } else if (choice == 7) {
                    if (lastResult == null) {
                        System.out.println("последний результат не сохранен");
                    } else {
                        System.out.println("последний результат:");
                        System.out.println(lastResult);
                    }

                } else {
                    System.out.println("такой операции нет");
                }

            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println(
                        "не удалось выполнить операцию: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static ComplexMatrix applyToLastResult(ComplexMatrix lastResult) {
        if (lastResult == null) {
            System.out.println("cначала выполните операцию 1-4");
            return null;
        }

        System.out.println("Текущий результат:");
        System.out.println(lastResult);

        System.out.println("1. прибавить   2. умножить   3. транспонировать   0. отмена");
        System.out.print("выберите: ");

        int action = readInt();

        if (action == 1) {
            ComplexMatrix c = readMatrix("матрицы для прибавления");
            return lastResult.add(c);
        } else if (action == 2) {
            ComplexMatrix c = readMatrix("матрицы для умножения");
            return lastResult.multiply(c);
        } else if (action == 3) {
            return lastResult.transpose();
        }

        System.out.println("операция отменена");
        return lastResult;
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
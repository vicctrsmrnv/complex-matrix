public class ComplexMatrix {
    private int rows;              // строк
    private int columns;           // столбцов
    private Complex[][] matrix;    // сами элементы

    public ComplexMatrix(int rows, int columns) {                  // создаем нулевую матрицу нужного размера
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(
                    "размеры матрицы должны быть положительными"
            );
        }

        this.rows = rows;
        this.columns = columns;
        matrix = new Complex[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new Complex();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setElement(int row, int column, Complex value) {
        matrix[row][column] = value;
    }

    public Complex getElement(int row, int column) {
        return matrix[row][column];
    }

    public ComplexMatrix add(ComplexMatrix other) {            //сложение
        if (rows != other.rows || columns != other.columns) {
            throw new IllegalArgumentException(
                    "для сложения размеры матриц должны совпадать"
            );
        }

        ComplexMatrix result = new ComplexMatrix(rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.matrix[i][j] =
                        matrix[i][j].add(other.matrix[i][j]);
            }
        }

        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {    //умножение
        if (columns != other.rows) {
            throw new IllegalArgumentException(
                    "число столбцов первой матрицы должно быть равно числу строк второй"
            );
        }

        ComplexMatrix result =
                new ComplexMatrix(rows, other.columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.columns; j++) {
                Complex sum = new Complex();

                for (int k = 0; k < columns; k++) {
                    sum = sum.add(
                            matrix[i][k].multiply(other.matrix[k][j])
                    );
                }

                result.matrix[i][j] = sum;
            }
        }

        return result;
    }

    public ComplexMatrix transpose() {        // транспонируем, строки становятся столбцами
        ComplexMatrix result =
                new ComplexMatrix(columns, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.matrix[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    public Complex determinant() {        //методом разложения по первой строке
        if (rows != columns) {
            throw new IllegalArgumentException(
                    "определитель можно вычислить только у квадратной матрицы"
            );
        }

        if (rows == 1) {
            return matrix[0][0];
        }

        if (rows == 2) {
            return matrix[0][0].multiply(matrix[1][1])
                    .subtract(
                            matrix[0][1].multiply(matrix[1][0])
                    );
        }

        Complex result = new Complex();

        for (int j = 0; j < columns; j++) {
            Complex[][] minor =
                    new Complex[rows - 1][columns - 1];

            for (int i = 1; i < rows; i++) {
                int minorColumn = 0;

                for (int k = 0; k < columns; k++) {
                    if (k != j) {
                        minor[i - 1][minorColumn] = matrix[i][k];
                        minorColumn++;
                    }
                }
            }

            ComplexMatrix minorMatrix =
                    new ComplexMatrix(rows - 1, columns - 1);

            for (int i = 0; i < rows - 1; i++) {
                for (int k = 0; k < columns - 1; k++) {
                    minorMatrix.matrix[i][k] = minor[i][k];
                }
            }

            Complex term = matrix[0][j].multiply(
                    minorMatrix.determinant()
            );

            if (j % 2 == 0) {                     //знак чередуется
                result = result.add(term);
            } else {
                result = result.subtract(term);
            }
        }

        return result;
    }

    public ComplexMatrix inverse() {                   // методом Гаусса
        if (rows != columns) {
            throw new IllegalArgumentException(
                    "обратная матрица существует только для квадратной"
            );
        }

        int n = rows;

        Complex[][] left = new Complex[n][n];
        Complex[][] right = new Complex[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                left[i][j] = new Complex(
                        matrix[i][j].getReal(),
                        matrix[i][j].getImaginary()
                );

                if (i == j) {
                    right[i][j] = new Complex(1, 0);
                } else {
                    right[i][j] = new Complex(0, 0);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int pivotRow = i;

            while (pivotRow < n
                    && left[pivotRow][i].abs() < 1e-10) {
                pivotRow++;
            }

            if (pivotRow == n) {
                throw new ArithmeticException(
                        "матрица вырожденная, обратной нет"
                );
            }

            if (pivotRow != i) {                 //меняем строки местами если нужно
                Complex[] temp = left[i];
                left[i] = left[pivotRow];
                left[pivotRow] = temp;

                temp = right[i];
                right[i] = right[pivotRow];
                right[pivotRow] = temp;
            }

            Complex pivot = left[i][i];     //делим

            for (int j = 0; j < n; j++) {
                left[i][j] = left[i][j].divide(pivot);
                right[i][j] = right[i][j].divide(pivot);
            }

            for (int k = 0; k < n; k++) {     //обнуляем столбец
                if (k != i) {
                    Complex factor = left[k][i];

                    for (int j = 0; j < n; j++) {
                        left[k][j] = left[k][j].subtract(
                                factor.multiply(left[i][j])
                        );

                        right[k][j] = right[k][j].subtract(
                                factor.multiply(right[i][j])
                        );
                    }
                }
            }
        }

        ComplexMatrix result = new ComplexMatrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.matrix[i][j] = right[i][j];
            }
        }

        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {       // деление: A /B = A*обратн(B)
        if (other.rows != other.columns) {
            throw new IllegalArgumentException(
                    "вторая матрица должна быть квадратной"
            );
        }

        if (columns != other.rows) {
            throw new IllegalArgumentException(
                    "число столбцов первой матрицы должно быть равно размеру второй"
            );
        }

        return multiply(other.inverse());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            result.append("[ ");

            for (int j = 0; j < columns; j++) {
                result.append(matrix[i][j].toString());
                result.append("   ");
            }

            result.append("]\n");
        }

        return result.toString();
    }
}
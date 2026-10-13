public class Complex {
    private double real;
    private double imaginary;

    public Complex() {
        real = 0;
        imaginary = 0;
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public Complex add(Complex number) {
        return new Complex(
                real + number.real,
                imaginary + number.imaginary
        );
    }

    public Complex subtract(Complex number) {
        return new Complex(
                real - number.real,
                imaginary - number.imaginary
        );
    }

    public Complex multiply(Complex number) {
        double newReal = real * number.real
                - imaginary * number.imaginary;

        double newImaginary = real * number.imaginary
                + imaginary * number.real;

        return new Complex(newReal, newImaginary);
    }

    public Complex divide(Complex number) {
        double denominator = number.real * number.real
                + number.imaginary * number.imaginary;

        if (denominator < 1e-10) {
            throw new ArithmeticException("Деление на ноль");
        }

        double newReal = (real * number.real
                + imaginary * number.imaginary) / denominator;

        double newImaginary = (imaginary * number.real
                - real * number.imaginary) / denominator;

        return new Complex(newReal, newImaginary);
    }

    public Complex negate() {
        return new Complex(-real, -imaginary);
    }

    public double abs() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public String toString() {
        if (Math.abs(imaginary) < 1e-10) {
            return String.format("%.2f", real);
        }

        if (Math.abs(real) < 1e-10) {
            return String.format("%.2fi", imaginary);
        }

        if (imaginary < 0) {
            return String.format("%.2f - %.2fi", real, -imaginary);
        }

        return String.format("%.2f + %.2fi", real, imaginary);
    }
}
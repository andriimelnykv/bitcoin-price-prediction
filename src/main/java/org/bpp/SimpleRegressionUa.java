package org.bpp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleRegressionUa {

    //coefficients that are used for prediction
    private final double alpha1, beta1;

    public SimpleRegressionUa(List<Double> x, List<Double> y) {
        if (x.size() != y.size()) {
            throw new IllegalStateException("Must have equal X and Y data points");
        }

        Integer n = x.size();

        List<Double> xSquared = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Double> ySquared = y
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Double> xMultipliedByY = IntStream.range(0, n)
                .mapToDouble(i -> x.get(i) * y.get(i))
                .boxed()
                .collect(Collectors.toList());

        Double xSummed = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfYSquared = ySquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        double averageXSummed = ((double) xSummed) / n;
        double averageYSummed = ((double) ySummed) / n;

        double alpha1Nominator = sumOfXMultipliedByY - (averageXSummed * averageYSummed * n);
        double alpha1Denominator = sumOfXSquared - (Math.pow(averageXSummed, 2) * n);

        alpha1 = alpha1Nominator / alpha1Denominator;
        beta1 = averageYSummed - alpha1 * averageXSummed;
    }

    public Double predictForValue(double x) {
        return alpha1 * x + beta1;
    }

}

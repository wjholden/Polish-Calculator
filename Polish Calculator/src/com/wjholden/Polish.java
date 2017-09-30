package com.wjholden;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Polish {

    static final Map<String, BiFunction<Double, Double, Double>> binaryOperators;
    static final Map<String, Function<Double, Double>> unaryOperators;
    static final Map<String, Double> constants;

    static {
        binaryOperators = new HashMap<>();
        unaryOperators = new HashMap<>();
        constants = new HashMap<>();
        binaryOperators.put("+", (l, r) -> l + r);
        binaryOperators.put("-", (l, r) -> l - r);
        binaryOperators.put("/", (l, r) -> l / r);
        binaryOperators.put("*", (l, r) -> l * r);
        binaryOperators.put("^", (l, r) -> Math.pow(l, r));
        binaryOperators.put("mod", (l, r) -> l % r);

        unaryOperators.put("!", v -> {
            v = Math.floor(v);
            int product = 1;
            while (v > 0) {
                product *= v;
                v--;
            }
            return (double) product;
        });

        unaryOperators.put("sqrt", v -> Math.sqrt(v));
        unaryOperators.put("sin", v -> Math.sin(v));
        unaryOperators.put("cos", v -> Math.cos(v));
        unaryOperators.put("tan", v -> Math.tan(v));
        unaryOperators.put("asin", v -> Math.asin(v));
        unaryOperators.put("acos", v -> Math.acos(v));
        unaryOperators.put("atan", v -> Math.atan(v));
        unaryOperators.put("log", v -> Math.log10(v));
        unaryOperators.put("ln", v -> Math.log(v));

        constants.put("pi", Math.PI);
        constants.put("e", Math.E);
    }

    static double consume(Queue<String> queue, Map<String, Double> variableNameValues) {
        // a zero will be inferred if the stack machine needs an operand that
        // has not been provided. This works fine for addition, not so much division.
        if (queue.isEmpty()) {
            return 0.0d;
        }

        String first = queue.poll();
        if (first.isEmpty()) {
            return 0.0d;
        }

        if (constants.containsKey(first)) {
            return constants.get(first);
        } else if (binaryOperators.containsKey(first)) {
            double l = consume(queue, variableNameValues);
            double r = consume(queue, variableNameValues);
            return binaryOperators.get(first).apply(l, r);
        } else if (unaryOperators.containsKey(first)) {
            double v = consume(queue, variableNameValues);
            return unaryOperators.get(first).apply(v);
        } else if (variableNameValues.containsKey(first)) {
            // notice that if a user defines a variable that conflicts with
            // a builtin operator or constant then the user-defined value
            // will be ignored through order-of-evaluation.
            return variableNameValues.get(first);
        } else {
            return Double.valueOf(first);
        }
    }

    static double consume(Queue<String> queue) {
        return consume(queue, Collections.<String, Double>emptyMap());
    }

    static Queue<String> parse(String input) {
        return new LinkedList<>(Arrays.asList(input.split("\\s")));
    }

    static void repl() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine();
            if (input.isEmpty() || input.startsWith("#")) {
                continue;
            }
            if (input.startsWith("quit")) {
                break;
            }
            System.out.println("Output: " + consume(parse(input)));
        }
    }

    static double[][] plot3d() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("f(x,y) = ");
        String function = scanner.nextLine();
        System.out.print("x min = ");
        double xmin = scanner.nextDouble();
        System.out.print("x max = ");
        double xmax = scanner.nextDouble();
        System.out.print("y min = ");
        double ymin = scanner.nextDouble();
        System.out.print("y max = ");
        double ymax = scanner.nextDouble();
        System.out.println("# of intervals = ");
        int segments = scanner.nextInt();

        double[][] plot = plot3d(function, xmin, xmax, ymin, ymax, segments);
        for (double[] xyz : plot) {
            System.out.println(Arrays.toString(xyz));
        }
        return plot;
    }

    static double[][] plot3d(String function, double xmin, double xmax, double ymin, double ymax, int segments) {
        double[][] plot = new double[(segments + 1) * (segments + 1)][3];

        Map<String, Double> vars = new HashMap<>();
        int p = 0;

        for (int dy = 0; dy <= segments; dy++) {
            for (int dx = 0; dx <= segments; dx++) {
                plot[p][0] = xmin + (dx * (xmax - xmin) / segments);
                plot[p][1] = ymin + (dy * (ymax - ymin) / segments);
                vars.put("x", plot[p][0]);
                vars.put("y", plot[p][1]);
                plot[p][2] = consume(parse(function), vars);
                p++;
            }
        }

        return plot;
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("repl")) {
            repl();
        } else if (args[0].equals("plot3d")) {
            plot3d();
        } else {
            System.out.println("Usage: java -jar Polish.jar [repl|plot3d]\n"
                    + " repl:   read-evaluate-print loop\n"
                    + " plot3d: compute f(x,y) for xmin-xmax by ymin-ymax range");
        }
    }
}

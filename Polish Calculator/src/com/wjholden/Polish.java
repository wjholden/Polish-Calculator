package com.wjholden;

import java.util.Arrays;
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
            return (double)product;
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
    
    static double consume(Queue<String> queue) {
        if (queue.isEmpty()) return 0.0d;
        
        String first = queue.poll();
        if (constants.containsKey(first)) {
            return constants.get(first);
        } else if (binaryOperators.containsKey(first)) {
            double l = consume(queue);
            double r = consume(queue);
            return binaryOperators.get(first).apply(l, r);
        } else if (unaryOperators.containsKey(first)) {
            double v = consume(queue);
            return unaryOperators.get(first).apply(v);
        } else {
            return Double.valueOf(first);
        }
    }
    
    static Queue<String> parse(String input) {
        return new LinkedList<>(Arrays.asList(input.split("\\s")));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine();
            System.out.println("Output: " + consume(parse(input)));
        }
    }
    
}

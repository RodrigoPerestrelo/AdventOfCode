import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class part2 {

    private static class SymbolInfo {
        int lineNumber;
        int index;

        public SymbolInfo(int lineNumber, int index) {
            this.lineNumber = lineNumber;
            this.index = index;
        }
    }

    private static class NumberInfo {
        int lineNumber;
        int startIndex;
        int endIndex;
        int number;

        public NumberInfo(int lineNumber, int startIndex, int endIndex, int number) {
            this.lineNumber = lineNumber;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.number = number;
        }
    }

    public static void main(String[] args) {
        String path = "/Users/rodrigoperestrelo/Documents/RepAdventOfCode/AdventOfCode/2023/day03/input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            int lineNumber = 0;

            List<SymbolInfo> nonDotSymbols = new ArrayList<>();
            List<NumberInfo> numberSymbols = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                lineNumber++;
                findSymbolInfo(line, lineNumber, nonDotSymbols, numberSymbols);
            }

            int sumOfAdjacentNumbers = calculateSumOfAdjacentNumbers(numberSymbols, nonDotSymbols);
            System.out.println("Soma dos números vizinhos a algum símbolo: " + sumOfAdjacentNumbers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findSymbolInfo(String line, int lineNumber, List<SymbolInfo> nonDotSymbols, List<NumberInfo> numberSymbols) {
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            int number = Integer.parseInt(matcher.group());
            numberSymbols.add(new NumberInfo(lineNumber, startIndex, endIndex - 1, number));
        }

        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            if (currentChar != '.' && !Character.isDigit(currentChar)) {
                nonDotSymbols.add(new SymbolInfo(lineNumber, i));
            }
        }
    }

    private static boolean isNumberAdjacentToSymbol(NumberInfo numberInfo, SymbolInfo symbolInfo) {
        int numberStartIndex = numberInfo.startIndex;
        int numberEndIndex = numberInfo.endIndex;
        int symbolIndex = symbolInfo.index;
        int symbolLineNumber = symbolInfo.lineNumber;

        if (numberInfo.lineNumber == symbolLineNumber &&
                (Math.abs(numberStartIndex - symbolIndex) <= 1 || Math.abs(numberEndIndex - symbolIndex) <= 1)) {
            return true;
        }

        if (Math.abs(numberInfo.lineNumber - symbolLineNumber) == 1 &&
                (Math.abs(numberStartIndex - symbolIndex) <= 1 || Math.abs(numberEndIndex - symbolIndex) <= 1)) {
            return true;
        }

        return false;
    }

    private static int calculateSumOfAdjacentNumbers(List<NumberInfo> numberSymbols, List<SymbolInfo> nonDotSymbols) {
        int sum = 0;

        for (SymbolInfo symbolInfo : nonDotSymbols) {
            int product = calculateProductOfAdjacentNumbersForSymbol(symbolInfo, numberSymbols);
            sum += product;
        }

        return sum;
    }

    private static int calculateProductOfAdjacentNumbersForSymbol(SymbolInfo symbolInfo, List<NumberInfo> numberSymbols) {
        List<Integer> adjacentNumbers = findAdjacentNumbersForSymbol(symbolInfo, numberSymbols);

        if (adjacentNumbers.size() == 2) {
            return adjacentNumbers.get(0) * adjacentNumbers.get(1);
        } else {
            return 0;
        }
    }

    private static List<Integer> findAdjacentNumbersForSymbol(SymbolInfo symbolInfo, List<NumberInfo> numberSymbols) {
        List<Integer> adjacentNumbers = new ArrayList<>();

        for (NumberInfo numberInfo : numberSymbols) {
            if (isNumberAdjacentToSymbol(numberInfo, symbolInfo)) {
                adjacentNumbers.add(numberInfo.number);
            }
        }

        return adjacentNumbers;
    }
}

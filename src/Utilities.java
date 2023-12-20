import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    private static final DecimalFormat formatter = new DecimalFormat("#, ###");
    public static void print(String text)
    {
        System.out.print(text);
    }

    public static void println(String text)
    {
        System.out.println(text);
    }

    public static boolean isValidSetCommand(String input, int maxId) {
        String regex = "(\\d+)=(-?\\d+)";
        return Pattern.matches(regex, input);
    }

    private static boolean isValidIdRange(int id, int MaxId) {
        return id >= 0 && id <= MaxId;
    }

    public static SetCommand ParseSetCommand(String input) {
        String regex = "(\\d+)=(-?\\d+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group(1));
            int value = Integer.parseInt(matcher.group(2));
            return new SetCommand(id, value);
        } else {
            return null;
        }
    }

    public static String formatNumberDecimals(int Number)
    {
        return formatter.format(Number);
    }

    public static double[] scanForAlpha(int yLength)
    {
        Scanner alphaScanner = new Scanner(System.in);

        double[] CORs = new double[yLength];

        for (int i = 0; i < CORs.length; i++)
        {
            double sumOfOtherAlphas = 0;
            for (int current = i; current >= 0; current--) sumOfOtherAlphas += CORs[current];

            if (i == CORs.length - 1) {
                CORs[i] = 1 - sumOfOtherAlphas;
                break;
            }

            double enteredAlpha = Double.MAX_VALUE;
            while (enteredAlpha < 0 || enteredAlpha > 1) {
                Utilities.print("Please enter the value of coefficient of realism (α) Number " + (i + 1) + ": ");
                enteredAlpha = alphaScanner.nextDouble();
                if (sumOfOtherAlphas + enteredAlpha >= 1) {
                    Utilities.println("ERROR: This value is forbidden because it adds up to 1 if added to the past values.");
                    enteredAlpha = Double.MAX_VALUE;
                }
            }

            CORs[i] = enteredAlpha;
        }

        return CORs;
    }

    public static int extractMaxInRow(int[][] array, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= array.length) {
            System.out.println("Invalid row index.");
            return Integer.MIN_VALUE;
        }

        int maxInRow = array[rowIndex][0];

        for (int j = 1; j < array[rowIndex].length; j++) {
            if (array[rowIndex][j] > maxInRow) {
                maxInRow = array[rowIndex][j];
            }
        }

        return maxInRow;
    }

    public static int extractMinInRow(int[][] array, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= array.length) {
            System.out.println("Invalid row index.");
            return Integer.MAX_VALUE;
        }

        int minInRow = array[rowIndex][0];

        for (int j = 1; j < array[rowIndex].length; j++) {
            if (array[rowIndex][j] < minInRow) {
                minInRow = array[rowIndex][j];
            }
        }

        return minInRow;
    }
}

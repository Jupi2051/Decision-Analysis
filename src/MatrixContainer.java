import java.util.Arrays;
import java.util.Scanner;


public class MatrixContainer implements IDisplayable {

    private int xLength;
    private int yLength;
    private int[][] matrix;

    public MatrixContainer(int[][] matrix, int xLength, int yLength)
    {
        this.xLength = xLength;
        this.yLength = yLength;
        this.matrix = matrix;
    }

    @Override
    public void DisplayToConsole() {
        int CellID = 1;
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                String displayedValue = matrix[x][y] != Integer.MAX_VALUE ? String.format("%d", matrix[x][y]) : "Unset";
                System.out.print("["+CellID+"] " + displayedValue + " \t\t");
                CellID++;
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }

    public int CalculateForApproach(Approach approach) {
        return switch (approach) {
            case MAXIMAX_OPTIMISTIC -> this.CalculateForMaximax();
            case MAXIMIN_PESSIMISTIC -> this.CalculateForMaximin();
            case CRITERION_OF_REALISM_HURWICZ -> this.CalculateForHurwicz();
            case EQUALLY_LIKELY_LAPLACE -> this.CalculateForLaplace();
            case MINIMAX_REGRET -> this.CalculateForMinimaxRegret();
        };
    }

    private int CalculateForMaximax()
    {
        int[] maxValuesPerRow = new int[this.matrix.length];

        for (int i = 0; i < this.matrix.length; i++) {
            int max = Integer.MIN_VALUE;

            for (int j = 0; j < this.matrix[i].length; j++)
                if (this.matrix[i][j] > max)
                    max = this.matrix[i][j];

            maxValuesPerRow[i] = max;
        }

        int max_in_max = Integer.MIN_VALUE;
        for (int j : maxValuesPerRow)
            if (j > max_in_max)
                max_in_max = j;

        return max_in_max;
    }

    private int CalculateForMaximin()
    {
        int[] minValuesPerRow = new int[this.matrix.length];

        for (int i = 0; i < this.matrix.length; i++) {
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < this.matrix[i].length; j++)
                if (this.matrix[i][j] < min)
                    min = this.matrix[i][j];

            minValuesPerRow[i] = min;
        }

        int max_in_min = Integer.MIN_VALUE;
        for (int j : minValuesPerRow)
            if (j > max_in_min)
                max_in_min = j;

        return max_in_min;
    }

    private int CalculateForHurwicz() {
        double[] alphas = Utilities.scanForAlpha(this.yLength);
        Utilities.println("Alphas: " + Arrays.toString(alphas));

        double[] WeightedAverageArray = new double[xLength];

        for (int x = 0; x < xLength; x++) {
            if (this.yLength == 2) {
                double bigAlpha = alphas[0];
                double smallAlpha = alphas[1];
                int rowMax = Utilities.extractMaxInRow(this.matrix, x);
                int rowMin = Utilities.extractMinInRow(this.matrix, x);
                WeightedAverageArray[x] = (bigAlpha * rowMax) + (smallAlpha * rowMin);
                continue;
            }

            double weightAverageOfThisRow = 0;
            for (int y = 0; y < yLength; y++) {
                double alphaForThisColumn = alphas[y];
                weightAverageOfThisRow += this.matrix[x][y] * alphaForThisColumn;
            }
            WeightedAverageArray[x] = weightAverageOfThisRow;
        }

        double RealismValue = Double.MIN_VALUE;

        for (double avg : WeightedAverageArray)
            if (avg > RealismValue)
                RealismValue = avg;


        return (int) Math.round(RealismValue);
    }

    private int CalculateForLaplace()
    {
        double[] Averages = new double[this.matrix.length];

        for (int i = 0; i < this.matrix.length; i++) {
            int[] row = this.matrix[i];

            double sum = 0;

            for (int valueI = 0; valueI < row.length; valueI++)
                sum += row[valueI];

            Averages[i] = sum / row.length;

            System.out.println(sum + " / " + row.length + " = " + sum / row.length);
        }
        double max_in_averages = Integer.MIN_VALUE;
        for (double j : Averages)
            if (j > max_in_averages)
                max_in_averages = j;

        return (int) Math.round(max_in_averages); // round it to long then case to an integer.
    }

    private int CalculateForMinimaxRegret()
    {
        int[] maxInColumns = new int[yLength];

        for (int j = 0; j < yLength; j++) {
            maxInColumns[j] = this.matrix[0][j];
        }

        for (int i = 1; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                if (this.matrix[i][j] > maxInColumns[j]) {
                    maxInColumns[j] = this.matrix[i][j];
                }
            }
        }

        int[][] OpportunityCostArray = new int [xLength][yLength];

        for (int i = 0; i < xLength; i++)
            OpportunityCostArray[i] = matrix[i].clone();

        for (int x = 0; x < xLength; x++)
        {
            for (int y = 0; y < yLength; y++) {
                int maxOfColumn = maxInColumns[y];
                OpportunityCostArray[x][y] = maxOfColumn - OpportunityCostArray[x][y];
            }
        }

        int[] maxValuesPerRowOfOpportunityCost = new int[OpportunityCostArray.length];

        for (int i = 0; i < OpportunityCostArray.length; i++) {
            int max = Integer.MIN_VALUE;

            for (int j = 0; j < OpportunityCostArray[i].length; j++)
                if (OpportunityCostArray[i][j] > max)
                    max = OpportunityCostArray[i][j];

            maxValuesPerRowOfOpportunityCost[i] = max;
        }

        int min_in_max = Integer.MAX_VALUE;

        for (int value : maxValuesPerRowOfOpportunityCost)
            if (value < min_in_max)
                min_in_max = value;

        return min_in_max;
    }
}

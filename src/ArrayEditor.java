public class ArrayEditor implements IDisplayable
{
    private int xLength;
    private int yLength;
    private int[][] matrix;

    public boolean areAllValuesSet()
    {
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                if (this.matrix[x][y] == Integer.MAX_VALUE)
                    return false;
            }
        }
        return true;
    }

    public int GetItemsCount()
    {
        return this.xLength * this.yLength;
    }

    public ArrayEditor(int xLength, int yLength)
    {
        this.xLength = xLength;
        this.yLength = yLength;
        this.matrix = new int[this.xLength][this.yLength];
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                this.matrix[x][y] = Integer.MAX_VALUE;
            }
        }
    }

    @Override
    public void DisplayToConsole() {
        int CellID = 1;
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                String displayedValue = matrix[x][y] != Integer.MAX_VALUE ? Utilities.formatNumberDecimals(matrix[x][y]) : "Unset";
                System.out.print("["+CellID+"]"+displayedValue + " \t\t");
                CellID++;
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }

    public void setCellValue(int ID, int Value) {
        int Counter = 1;

        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                if (Counter == ID){
                    this.matrix[x][y] = Value;
                    return;
                }
                Counter = Counter + 1;
            }
        }
    }

    public int[][] GetEditedArray()
    {
        int[][] clonedArray = new int [xLength][yLength];

        for (int i = 0; i < xLength; i++)
            clonedArray[i] = matrix[i].clone();

        return clonedArray;
    }
}

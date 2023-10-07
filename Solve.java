package FinalSuduko;
// Authors: Haley Ogier
// Date created: 10/03/2023


public class Solve {
    private int size;
    private String[][] board = {
        {"0", "6", "9", "1", "7", "0", "0", "0", "0"},
        {"0", "0", "0", "6", "0", "0", "3", "0", "0"},
        {"5", "0", "8", "0", "2", "0", "0", "0", "7"},
        {"0", "0", "0", "2", "0", "0", "0", "5", "6"},
        {"6", "0", "1", "0", "0", "0", "2", "3", "0"},
        {"0", "5", "0", "0", "8", "0", "0", "0", "0"},
        {"0", "2", "6", "4", "0", "5", "8", "7", "3"},
        {"8", "1", "4", "7", "0", "2", "0", "6", "9"},
        {"0", "0", "0", "8", "0", "9", "1", "0", "0"}
    };

    //constructor for class
    public Solve(){
        this.size = 9;
    }

    public String[][] getBoard(){
        return this.board;
    }
    
    public boolean inRow(int row, String number){
        for(int y = 0; y< size; y++){
            if (y == row){
            for (int x = 0; x<size; x++){
                if (board[row][x].equals(number)){
                    return true;
                }
            }
            }
        }
        return false;
    }

    public boolean inColumn(int column, String number){
        for (int y= 0; y<size;y++){
            if (board[y][column].equals(number)){
                return true;
            }
        }
        return false;
    }

    public boolean inBox(int row, int column, String number){
        int boxOfRow = row - (row%3);
        int boxOfColumn = row - (row%3);

        for (int x = boxOfRow; x < boxOfRow; x++){
            for (int j = boxOfColumn; j <boxOfColumn; j++){
                if (board[boxOfRow][boxOfColumn].equals(number)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allClear(int row, int column, String number){
        if((inRow(row,number)) || (inColumn(column, number)) || (inBox(row, column, number))){
            return false;
        }else{
            return true;
        }
    }


    //We are going to use a backtracking algorithm and try to use recursion
    public boolean solveBoard(){
        for (int row=0; row<size; row++){
            for (int column = 0; column< size; column++){
                if(board[row][column].equals("0")){ // up till here we are checking to see if the slots are empty and if so we run the following
                    for(int testNums = 1; testNums<=9; testNums++){//we are now testing numbers 1-9
                        String newTestNum = Integer.toString(testNums);
                        if (allClear(row, column, newTestNum)){
                            board[row][column] = newTestNum;
                            if (solveBoard()){
                                return true;
                            }else{
                                board[row][column] = "0";
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void showFinal(){
        for (int x=0; x<size;x++){
            for (int y=0; y<size; y++){
                System.out.print(board[x][y]);
            }
            System.out.print("\n");
    }
}
}



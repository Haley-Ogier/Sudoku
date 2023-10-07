package FinalSuduko;
// Authors: Haley Ogier
// Date created: 10/03/2023


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Sudoku extends JFrame{
    private static final Object EMPTY = null;
    private JFrame frame = new JFrame();
    private JTextField[][] board = new JTextField[9][9];
    JPanel title_panel = new JPanel();
    JLabel textfield = new JLabel();
    int tries = 3;
    private String[][] numbers = {
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


    public Sudoku(){
        //Set up the frame that will pop up along with color and size
        JPanel board_panel = new JPanel(new GridLayout(9,9));
        board_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new JTextField(2);
                board[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                board[i][j].setHorizontalAlignment(JTextField.CENTER);
                board_panel.add(board[i][j]);
            }
        }
        addBorders();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(255,175,175));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setTitle("Sudoku");

        //We chose where the title panel will go on the screen
        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0,800,100);

        //We created a text box so we assign text, color, font, and background
        textfield.setBackground(new Color (175,175,255));
        textfield.setForeground(new Color(225,0,225));
        textfield.setFont(new Font ("Arial", Font.BOLD,75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Sudoku");
        textfield.setOpaque(true);

        //We are setting our text field to our title panel and then the title panel to our frame
        title_panel.add(textfield);

        // second parameter allows us to put the border on the top of the frame instead of taking up the whole frame
        frame.add(title_panel,BorderLayout.NORTH);
        frame.add(board_panel, BorderLayout.CENTER);

        //submit button
        JButton submit = new JButton("Submit!");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                /////Put in actions from Sudoku 2d you will most likely have to change it a little but should remain mostly the same this is the solve button
                checkAnswers();
                if(winCheck()){
                    textfield.setText("You WIN!!");
                }else{
                    tries = tries-1;
                    textfield.setText(tries + " tries left!");
                    if (tries == 0){
                        textfield.setText("Sorry, You Lost :(");
                    }
                }
            }
        });

        //give up button creating
        JButton giveUpButton = new JButton("I Give Up!");
        giveUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                /////Put in actions from Sudoku 2d you will most likely have to change it a little but should remain mostly the same this is the solve button
                giveUp();
                textfield.setText("Nice Try :(");
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(giveUpButton);
        controlPanel.add(submit);
        frame.add(controlPanel,BorderLayout.SOUTH);
        frame.setVisible(true);

        addText();
    }
    public void addBorders(){
         Border bottomBorder2;
         Border otherBorder2;
         for (int i = 0;i<9;i++){
            for (int j = 0;j<9;j++){
                if ((i==2 && j==2) || (i==2 && j==5) || (i==5 && j==2) || (i==5 && j ==5)){
                    bottomBorder2 = new MatteBorder(0, 0, 4, 4, Color.BLACK); 
                    otherBorder2 = new MatteBorder(1,1,0,0,Color.BLACK);
                    Border compoundBorder = BorderFactory.createCompoundBorder(bottomBorder2, otherBorder2);
                    board[i][j].setBorder(compoundBorder);
                }else{
                    if((i==2) || (i==5)){
                        Border bottomBorder = new MatteBorder(0, 0, 4, 0, Color.BLACK); // 2-pixel wide border on the left
                        Border otherBorders = new LineBorder(Color.BLACK, 1); // 1-pixel wide black border on other sides
                        Border compoundBorder = BorderFactory.createCompoundBorder(bottomBorder, otherBorders);
                        board[i][j].setBorder(compoundBorder);
                    }
                    if ((j==2) || (j==5)){
                        Border bottomBorder = new MatteBorder(0, 0, 0, 4, Color.BLACK); // 2-pixel wide border on the left
                        Border otherBorders = new LineBorder(Color.BLACK, 1); // 1-pixel wide black border on other sides
                        Border compoundBorder = BorderFactory.createCompoundBorder(bottomBorder, otherBorders);
                        board[i][j].setBorder(compoundBorder);
                    }
                }
         }
        }
    }


//this adds the layout of the board
    public void addText(){
        for (int i = 0; i<9;i++){
            for(int j = 0; j<9; j++){
                String current = numbers[i][j];
                if (!(current.equals("0"))){
                board[i][j].setText(numbers[i][j]);
                board[i][j].setEditable(false);
                board[i][j].setBackground(Color.GREEN);
                }else{
                board[i][j].setEditable(true);
                }
            }
        }
    }


//when the user choses the give up option, this function will return the answers
    public void giveUp(){
        String[][] twoD = findAnswers();
        for (int i = 0; i<9;i++){
            for(int j = 0; j<9; j++){
                String current = numbers[i][j];
                if (current.equals("0")){
                    board[i][j].setText(twoD[i][j]);
                    board[i][j].setEditable(false);
                    board[i][j].setBackground(Color.PINK);
        }}}
    }


//checks the answers that were inputed to see if they were correctly placed
    public void checkAnswers(){
        String[][] twoD = findAnswers();
        for (int i = 0; i<9;i++){
            for(int j = 0; j<9; j++){
                if (board[i][j].equals(EMPTY) || !(board[i][j].getText().equals(twoD[i][j]))){
                    board[i][j].setEditable(true);
                    board[i][j].setBackground(Color.RED);
                }else{
                    board[i][j].setBackground(Color.GREEN);
                    board[i][j].setEditable(false);
                   
                }
    }}
    }


// used to find the correct answers of the 2d board in order to compare
    public String[][] findAnswers(){
        Solve driver = new Solve();
        driver.solveBoard();
        String[][] newBoard = driver.getBoard();
        return newBoard;
    }

    
    //this checks to see if the board is complete and correct
    public boolean winCheck(){
        String[][] twoD = findAnswers();
        for (int i = 0; i<9;i++){
            for(int j = 0; j<9; j++){
                if (board[i][j].equals(EMPTY) || !(board[i][j].getText().equals(twoD[i][j]))){
                    return false;
                }
            }
        } return true;
    }
    public void nothing(){}
}
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    static private final Random random = new Random();
    static public boolean isXTurn = random.nextBoolean();
    static public int[][] game = new int[3][3];
    static public JPanel panel = new JPanel();
    static public boolean isAgainstAI = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic Tac Toe UI");
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Tic Tac Toe");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("MV Boli", Font.PLAIN, 30));
        label.setBackground(Color.WHITE);

        panel.setLayout(new GridLayout(3, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] options = {"Against Friend", "Against AI"};
        int choice = JOptionPane.showOptionDialog(null, "Choose your game mode", "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        isAgainstAI = (choice == 1);

        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setBorder(BorderFactory.createLineBorder(Color.black));
            button.setBackground(Color.WHITE);
            int finalI = i;
            button.setFont(new Font("MV Boli", Font.BOLD, 60));
            button.addActionListener(e -> placePiece(button, finalI));
            button.setFocusable(false);
            panel.add(button);
        }

        frame.add(label, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    static void winner(int theWinner) {
        JOptionPane.showMessageDialog(null, (((theWinner == 1) ? "X" : "O") + " Won!!!!"), "we got a winner", JOptionPane.PLAIN_MESSAGE);
        resetBoard();
    }

    static void resetBoard() {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton button) {
                button.setText("");
                button.setEnabled(true);
            }
        }
        isXTurn = random.nextBoolean();
        game = new int[3][3];
    }

    static void checkForWinners() {
        for (int i = 0; i < 3; i++) {
            if (game[i][0] == game[i][1] && game[i][1] == game[i][2] && game[i][0] != 0) {
                winner(game[i][0]);
            }
            if (game[0][i] == game[1][i] && game[2][i] == game[1][i] && game[0][i] != 0) {
                winner(game[0][i]);
            }
        }
        if (game[0][0] == game[1][1] && game[1][1] == game[2][2] && game[0][0] != 0) {
            winner(game[0][0]);
        }
        if (game[0][2] == game[1][1] && game[1][1] == game[2][0] && game[0][2] != 0) {
            winner(game[0][2]);
        }
    }

    static void checkForDraw() {
        boolean allFilled = true;
        for (int[] i : game) {
            for (int j : i) {
                if (j == 0) {
                    allFilled = false;
                    break;
                }
            }
        }
        if (allFilled) {
            JOptionPane.showMessageDialog(null, "It's a draw", "It's a draw", JOptionPane.PLAIN_MESSAGE);
            resetBoard();
        }
    }

    static void placePiece(JButton button, int place) {
        button.setEnabled(false);
        int row = place / 3;
        int col = place % 3;
        game[row][col] = isXTurn ? 1 : 2;
        button.setText(isXTurn ? "X" : "O");
        isXTurn = !isXTurn;

        checkForWinners();
        checkForDraw();

        if (isAgainstAI && !isXTurn) {
            aiMove();
        }
    }

    static void aiMove() {
        Move bestMove = MinMaxAlgorithm.findBestMove(game, 2, 1);
        if (bestMove != null) {
            game[bestMove.row][bestMove.col] = 2;
            Component comp = panel.getComponent(bestMove.row * 3 + bestMove.col);
            if (comp instanceof JButton button) {
                button.setText("O");
                button.setEnabled(false);
            }
        }

        isXTurn = !isXTurn;
        checkForWinners();
        checkForDraw();
    }
}

class Move{
    int row, col;
}

public class MinMaxAlgorithm{
    static int checkForWinners(int[][] game, int max){
        for (int i = 0; i < 3; i++){
            if (game[i][0] == game[i][1] && game[i][1] == game[i][2] && game[i][0] != 0){
                return (game[i][0] == max) ? 10 : -10;
            }
            if (game[0][i] == game[1][i] && game[2][i] == game[1][i] && game[0][i] != 0){
                return (game[0][i] == max) ? 10 : -10;
            }
        }
        if (game[0][0] == game[1][1] && game[1][1] == game[2][2] && game[0][0] != 0){
            return (game[0][0] == max) ? 10 : -10;
        }
        if (game[0][2] == game[1][1] && game[1][1] == game[2][0] && game[0][2] != 0){
            return (game[0][2] == max) ? 10 : -10;
        }
        return 0;
    }
    static boolean checkForDraw(int[][] game) {
        for (int[] i : game) {
            for (int j : i) {
                if (j == 0) {
                   return true;
                }
            }
        }
        return false;
    }

    static int minMax(int[][] board, int depth, Boolean isMax, int max, int min){
        int score = checkForWinners(board, max);
        if (score == 10){
            return score;
        }
        if (score == -10){
            return score;
        }
        if (!checkForDraw(board)){
            return 0;
        }

        if (isMax){
            int best = -1000;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (board[i][j] == 0){
                        board[i][j] = max;

                        best = Math.max(best, minMax(board, depth + 1, false, max, min));

                        board[i][j] = 0;
                    }

                }
            }
            return best;

        } else{
            int best = 1000;

            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (board[i][j] == 0){
                        board[i][j] = min;

                        best = Math.min(best, minMax(board, depth + 1, true, max, min));

                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(int[][] board, int max, int min){
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if(board[i][j] == 0){
                    board[i][j] = max;

                    int moveVal = minMax(board, 0, false, max, min);

                    board[i][j] = 0;

                    if (moveVal > bestVal){
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }

            }
        }
        System.out.printf("The value of the best Move " +
                "is : %d\n\n", bestVal);

        return bestMove;
    }
}
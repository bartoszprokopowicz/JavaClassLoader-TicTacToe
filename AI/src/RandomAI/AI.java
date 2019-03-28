package RandomAI;

import Annotations.Difficulty;
import Annotations.Strategy;
import TicTacToe.Board;

@Strategy(strategyName = "Random")
public class AI{

    @Difficulty(difficultyName = "Easy")
    public int easy(Board board) {
        int[] moves = new int[board.getAvailableMoves().size()];
        int index = 0;

        for (Integer item : board.getAvailableMoves()) {
            moves[index++] = item;
        }
        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        return randomMove;
    }

    @Difficulty(difficultyName = "Hard")
    public int hard(Board board) {
        int randomMove = 0;
        int index = 0;
        int size = board.getBoardSize();
        int[] moves = new int[size];
        for(int i = 0; i < size; i++)
        {
            moves[i] = i;
        }
        for (Integer item : board.getAvailableMoves()) {
            for(int i: moves){
                if(item == i) {
                    return i;
                }
            }
        }

        moves = new int[board.getAvailableMoves().size()];
        for (Integer item : board.getAvailableMoves()) {
            moves[index++] = item;
        }
        randomMove = moves[new java.util.Random().nextInt(moves.length)];
        return randomMove;
    }
}

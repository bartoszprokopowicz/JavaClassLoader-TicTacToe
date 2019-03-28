package MinMaxAI;

import Annotations.Difficulty;
import Annotations.Strategy;
import TicTacToe.Board;

@Strategy(strategyName = "MiniMax")
public class AI{

    @Difficulty(difficultyName = "XEasy")
    public int easy(Board board) {
        return 10;
    }

    @Difficulty(difficultyName = "XHard")
    public int hard(Board board) {
        return 10;
    }
}
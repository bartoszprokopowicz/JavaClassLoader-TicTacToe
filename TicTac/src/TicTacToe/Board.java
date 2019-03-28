package TicTacToe;

import java.util.HashSet;

public class Board {

    private int boardSize;

    public enum State {Blank, X, O}
    private State[][] board;
    private State playersTurn;
    private State winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;

    public Board(){
        board = new State[boardSize][boardSize];
        movesAvailable = new HashSet<>();
    }

    public Board(int boardSize){
        this.boardSize = boardSize;
        board = new State[this.boardSize][this.boardSize];
        movesAvailable = new HashSet<>();
        reset();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    private void initialize () {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = State.Blank;
            }
        }

        movesAvailable.clear();

        for (int i = 0; i < boardSize * boardSize; i++) {
            movesAvailable.add(i);
        }
    }

    void reset () {
        moveCount = 0;
        gameOver = false;
        playersTurn = State.X;
        winner = State.Blank;
        initialize();
    }

    public boolean move (int index) {
        return move(index% boardSize, index/ boardSize);
    }

    private boolean move (int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("TicTacToe is over. No moves can be played.");
        }

        if (board[y][x] == State.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * boardSize + x);

        // The game is a draw.
        if (moveCount == boardSize * boardSize) {
            winner = State.Blank;
            gameOver = true;
        }

        // Check for a winner.
        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);

        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }

    public boolean isGameOver () {
        return gameOver;
    }

    State[][] toArray () {
        return board.clone();
    }

    public State getTurn () {
        return playersTurn;
    }

    public State getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("TicTacToe is not over yet.");
        }
        return winner;
    }

    public HashSet<Integer> getAvailableMoves () {
        return movesAvailable;
    }


    private void checkRow (int row) {
        for (int i = 1; i < boardSize; i++) {
            if (board[row][i] != board[row][i-1]) {
                break;
            }
            if (i == boardSize - 1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    private void checkColumn (int column) {
        for (int i = 1; i < boardSize; i++) {
            if (board[i][column] != board[i-1][column]) {
                break;
            }
            if (i == boardSize -1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    private void checkDiagonalFromTopLeft (int x, int y) {
        if (x == y) {
            for (int i = 1; i < boardSize; i++) {
                if (board[i][i] != board[i-1][i-1]) {
                    break;
                }
                if (i == boardSize -1) {
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    private void checkDiagonalFromTopRight (int x, int y) {
        if (boardSize -1-x == y) {
            for (int i = 1; i < boardSize; i++) {
                if (board[boardSize -1-i][i] != board[boardSize -i][i-1]) {
                    break;
                }
                if (i == boardSize -1) {
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    public Board getDeepCopy () {

        Board board = new Board();
        board.boardSize = this.boardSize;
        board.reset();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn       = this.playersTurn;
        board.winner            = this.winner;
        board.movesAvailable    = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount         = this.moveCount;
        board.gameOver          = this.gameOver;

        return board;
    }



}

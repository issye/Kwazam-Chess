import javax.swing.JFrame;

/**
 * The FlipScreenController class is responsible for flipping the chessboard view
 * in a chess application. It interacts with the ChessModel (data) and ChessView (UI)
 */
public class FlipScreenController {

    private ChessView view;
    private ChessModel model;

    /**
     * Constructor to initialize the FlipScreenController with view and model.
     */
    public FlipScreenController(ChessView view, ChessModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * The chessboard will be flipped by reversing the x-coordinates on the board.
     */
    public void flipScreen() {
        // Retrieve the current chessboard from the model
        ChessPieces[][] board = model.getChessBoard();

        // Create a new board to store the flipped setup.
        ChessPieces[][] flippedBoard = new ChessPieces[8][5];

        // Go through the original board to reverse the rows.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                // Flip the row and assign the piece to the new board
                flippedBoard[7 - i][j] = board[i][j];

                // Update the piece's position
                if (board[i][j] != null) {
                    board[i][j].setPosition(7 - i, j);
                }
            }
        }

        // Update the model with the flipped board
        model.setChessBoard(flippedBoard);

        // Update the board
        view.updateBoard();
    }
}

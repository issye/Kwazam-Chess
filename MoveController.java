/**
 * Purpose:
 * This class serves as the controller for managing moves in the chess game. 
 * It handles player input, validates and executes moves, transforms pieces (Xor <-> Tor), 
 * updates the view, and controls game flow, including turn switching and game-over logic.
 */


public class MoveController {
    private ChessModel model;
    private ChessView view;
    private FlipScreenController flipScreenController; 

    public MoveController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
        this.flipScreenController = new FlipScreenController(view, model);
    }

    /**
     * Handles a player's move.
     * @return True if the move ends the game; false otherwise.
     */
    public boolean executeMovement(int x, int y) {
        // Get the selected piece from the model
        ChessPieces selectedPiece = model.getSelectedPiece();

        // This statement is to ensure that move counts for TOR and XOR is not being counted when the board flips
        // This is to ensure that the transformation happen correctly
        if (selectedPiece instanceof Tor) {
            ((Tor) selectedPiece).PlayerMoved();
        }

        if (selectedPiece instanceof Xor) {
            ((Xor) selectedPiece).PlayerMoved();
        }

        // Validate and execute the move
        boolean correctMove = model.executeMovement(x, y);

        // Check if tor and xor should transform
        XorMoves();
        TorMoves();

        // Update the board view
        view.updateBoard();

        /* If the move is correct (meaning selected piece has moved), the game will check two things
         - 1) If the game is still going (meaning sau isn't captured) the turn will switch and board flips
         - 2) If the game is over, game over message will be displayed
        */
        if (correctMove) {
            if (!model.isGameOver(ChessPieces.Color.PINK) && !model.isGameOver(ChessPieces.Color.BLUE)) {
                flipScreenController.flipScreen(); 
                view.switchTurn(); 
            } else {
                String winner = model.isPinkTurn() ? "Blue" : "Pink";
                view.showGameOverMessage(winner);
            }
        } else {
            // Update the board view again in case of invalid move
            view.updateBoard();
        }
        // Return true if the game is over, false otherwise
        return correctMove && (model.isGameOver(ChessPieces.Color.PINK) || model.isGameOver(ChessPieces.Color.BLUE));
    }

    /**
     * Processes all Xor pieces on the board and transforms them to Tor pieces
     * if the conditions are met (countMoves is even and greater than 0).
     */
    private void XorMoves() {
        ChessPieces[][] board = model.getChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                ChessPieces piece = board[i][j];
                if (piece instanceof Xor) {
                    Xor xorPiece = (Xor) piece;
                    if (xorPiece.getCountMoves() % 2 == 0 && xorPiece.getCountMoves() > 0) {
                        XorToTor(i, j); // Transform the piece
                    }
                }
            }
        }
    }

    /**
     * Transforms a specific Xor piece into a Tor piece at the given position.
     * @param x The x-coordinate of the piece.
     * @param y The y-coordinate of the piece.
     */
    private void XorToTor(int x, int y) {
        ChessPieces[][] board = model.getChessBoard();
        ChessPieces xorPiece = board[x][y];
        if (xorPiece instanceof Xor) {
            ChessPieces torPiece = new Tor(x, y, xorPiece.getColor());
            board[x][y] = torPiece; // Replace the Xor piece with a Tor piece
            view.updateBoard(); // Update the board view
        }
    }

    /**
     * Processes all Tor pieces on the board and transforms them to Xor pieces
     * if the conditions are met (countMoves is even and greater than 0).
     */
    private void TorMoves() {
        ChessPieces[][] board = model.getChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                ChessPieces piece = board[i][j];
                if (piece instanceof Tor) {
                    Tor torPiece = (Tor) piece;
                    if (torPiece.getCountMoves() % 2 == 0 && torPiece.getCountMoves() > 0) {
                        TorToXor(i, j); // Transform the piece
                    }
                }
            }
        }
    }

    /**
     * Transforms a specific Tor piece into a Xor piece at the given position.
     * @param x The x-coordinate of the piece.
     * @param y The y-coordinate of the piece.
     */
    private void TorToXor(int x, int y) {
        ChessPieces[][] board = model.getChessBoard();
        ChessPieces torPiece = board[x][y];
        if (torPiece instanceof Tor) {
            ChessPieces xorPiece = new Xor(x, y, torPiece.getColor());
            board[x][y] = xorPiece; 
            view.updateBoard(); 
        }
    }
}

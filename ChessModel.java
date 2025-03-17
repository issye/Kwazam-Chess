import javax.swing.JOptionPane;

/**
 * The ChessModel class is responsible for managing the game's data and state.
 * It encapsulates the chessboard, the turn system, and game-specific logic like
 * piece movement, initialization, and resetting the game. This class adheres
 * to the principles of object-oriented design by ensuring single responsibility
 * and encapsulation of game logic.
 */
public class ChessModel {

    // 2D array representing the chessboard (8 rows x 5 columns)
    private ChessPieces[][] chessPiecesBoard = new ChessPieces[8][5];

    // The currently selected chess piece (null if no piece is selected)
    private ChessPieces selectedPiece = null;

    // Boolean to track whose turn it is (true = Pink's turn, false = Blue's turn)
    private boolean isPinkTurn = true;

    // Coordinates for the selected piece on the board
    private int selectedX = -1;
    private int selectedY = -1;

    /**
     * Constructor to initialize the ChessModel.
     * By default, it initializes to Pink's turn.
     */
    public ChessModel() {
        this.isPinkTurn = true; // Initialize to Player 1's turn (Pink)
    }

    /**
     * Getter method to retrieve the current state of the chessboard.
     * 
     * @return The 2D array representing the chessboard.
     */
    public ChessPieces[][] getChessBoard() {
        return chessPiecesBoard;
    }

    /**
     * Starts a new game by resetting and initializing the chess pieces.
     */
    public void startNewGame() {
        resetGame();
        initializePieces();
    }

    /**
     * Initializes the chessboard with the starting positions of all pieces.
     * This method ensures that each player's pieces are placed correctly
     * on their respective sides of the board.
     */
    public void initializePieces() {
        // Initialize Pink's non-Ram pieces on the bottom row
        ChessPieces[] pinkNonRamPieces = {
                new Tor(7, 0, ChessPieces.Color.PINK),
                new Biz(7, 1, ChessPieces.Color.PINK),
                new Sau(7, 2, ChessPieces.Color.PINK),
                new Biz(7, 3, ChessPieces.Color.PINK),
                new Xor(7, 4, ChessPieces.Color.PINK)
            };

        // Initialize Blue's non-Ram pieces on the top row
        ChessPieces[] blueNonRamPieces = {
                new Xor(0, 0, ChessPieces.Color.BLUE),
                new Biz(0, 1, ChessPieces.Color.BLUE),
                new Sau(0, 2, ChessPieces.Color.BLUE),
                new Biz(0, 3, ChessPieces.Color.BLUE),
                new Tor(0, 4, ChessPieces.Color.BLUE)
            };

        // Place the pieces on the board
        for (ChessPieces piece : pinkNonRamPieces) {
            placePiece(piece, piece.getX(), piece.getY());
        }
        for (ChessPieces piece : blueNonRamPieces) {
            placePiece(piece, piece.getX(), piece.getY());
        }

        // Place Rams for both Pink and Blue players
        for (int i = 0; i < 5; i++) {
            placePiece(new Ram(6, i, ChessPieces.Color.PINK), 6, i);
            placePiece(new Ram(1, i, ChessPieces.Color.BLUE), 1, i);
        }
    }

    /**
     * Places a chess piece at a specific position on the board.
     * 
     * @param piece The chess piece to place.
     * @param x     The x-coordinate on the board.
     * @param y     The y-coordinate on the board.
     */
    public void placePiece(ChessPieces piece, int x, int y) {
        chessPiecesBoard[x][y] = piece;
    }

    /**
     * Toggles the turn between Pink and Blue players.
     */
    // Tukar turn pink and blue
    // Bila kita panggil dia, kalau time tu isPinkTurn dia akan tukar jadi !isPinkTurn(blue)
    // Vice versa
    private void toggleTurn() {
        isPinkTurn = !isPinkTurn;
        // Untuk debug and see dia baca turn siapa
        System.out.println("Next turn: " + (isPinkTurn ? "Pink" : "Blue"));
    }
    
    
    /**
     * Handles a player's move. This includes selecting a piece, validating
     * the move, and updating the board if the move is valid.
     * 
     * @param x The x-coordinate of the target position.
     * @param y The y-coordinate of the target position.
     * @return True if the move was successful, false otherwise.
     */
    public boolean executeMovement(int x, int y) {
        if (selectedPiece == null) {
            // Select a piece if none is currently selected
            ChessPieces piece = chessPiecesBoard[x][y];
            if (piece == null) {
                JOptionPane.showMessageDialog(null, "No piece at this position to select.");
            } else {
                if (piece.isActive() && ((isPinkTurn && piece.getColor() == ChessPieces.Color.PINK) ||
                    (!isPinkTurn && piece.getColor() == ChessPieces.Color.BLUE))) {
                    selectedPiece = piece;
                    selectedX = x;
                    selectedY = y;
                    return false; // Selection successful
                } else {
                    JOptionPane.showMessageDialog(null, "It is not your turn.");
                }
            }
        } else {
            // Attempt to move the selected piece
            ChessPieces targetPiece = chessPiecesBoard[x][y];
            if (targetPiece != null && targetPiece.getColor() == selectedPiece.getColor()) {
                JOptionPane.showMessageDialog(null, "Cannot move to a position occupied by your own piece.");
            } else if (selectedPiece.correctMove(x, y, chessPiecesBoard)) {
                chessPiecesBoard[selectedX][selectedY] = null;
                placePiece(selectedPiece, x, y);
                selectedPiece.setPosition(x, y);
                // Setiap kali successfully gerak, dia akan tukar turn
                toggleTurn(); 
                selectedPiece = null;
                return true; // Move successful
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move for " + selectedPiece.getName());
            }
            selectedPiece = null; // Deselect piece
        }
        return false;
    }

    /**
     * Resets the game state, including the board, turn, and selected piece.
     */
    public void resetGame() {
        isPinkTurn = true;        
        clearBoard();
        initializePieces();
        selectedPiece = null;
        selectedX = -1;
        selectedY = -1;
    }
    
    //Kosongkan board
    public void clearBoard() {
          for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
               chessPiecesBoard[i][j] = null;
            }
        }
    }

    /**
     * Checks if the game is over by determining if a player's King (Sau) is still on the board.
     * 
     * @param color The color of the player being checked.
     * @return True if the King is no longer on the board, false otherwise.
     */
    public boolean isGameOver(ChessPieces.Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                ChessPieces piece = chessPiecesBoard[i][j];
                if (piece != null && piece.getColor() == color && piece.getName().equalsIgnoreCase("Sau")) {
                    return false; // The King is still alive
                }
            }
        }
        return true; // The King is dead
    }

    /**
     * Getter for whose turn it is.
     * 
     * @return True if it is Pink's turn, false if it is Blue's turn.
     */
    public boolean isPinkTurn() {
        return isPinkTurn;
    }

    /**
     * Updates the chessboard state in the model.
     * 
     * @param newBoard The new chessboard state to set.
     */
    public void setChessBoard(ChessPieces[][] newBoard) {
        this.chessPiecesBoard = newBoard;
    }

    public ChessPieces getSelectedPiece() {
        return selectedPiece;
    }
    
    //To use in load
    public void setTurn(boolean isPinkTurn) {
        this.isPinkTurn = isPinkTurn;
    }
}
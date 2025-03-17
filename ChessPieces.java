
/**
 * @Issye Lailiyah
 * - Stores the position, name, color, and status (active/captured) of a chess piece.
 * - Abstract method `correctMove` to be implemented by specific chess piece types.
 * - Includes methods to handle capturing opponent pieces and setting positions.
 * - Enumerates two piece colors: PINK and BLUE.
 */

public abstract class ChessPieces 
{
    private int x;  // X coordinate of the piece on the board
    private int y;  // Y coordinate of the piece on the board
    private String name;  // Name of the piece 
    private Color color;  // Color of the piece (PINK or BLUE)
    private boolean activeStatus;  // Whether the piece is active or has been captured
    private boolean captured;  // Tracks if the piece is captured

    // Enum to define the colors for the pieces
    public enum Color
    {
        PINK, BLUE  // The two colors in the game
    }

    // Constructor to initialize the chess piece with its position, name, and color
    public ChessPieces(int x, int y, String name, Color color)
    {
        this.x = x;  // Set the x coordinate
        this.y = y;  // Set the y coordinate
        this.name = name;  // Set the name of the piece
        this.color = color;  // Set the color of the piece
        this.activeStatus = true;  // By default, the piece is active
        this.captured = false;  // The piece is not captured initially
    }

    // Abstract method that must be implemented by each piece to check if a move is valid
    public abstract boolean correctMove(int x, int y, ChessPieces[][] board);

    // Piece captured will be inactive
    public void capture()
    {
        this.captured = true;  // Mark the piece as captured
        this.activeStatus = false;  // Set the piece's status to inactive
    }

    // Method to capture another piece if it is an opponent's piece
    public boolean capturePiece(int newX, int newY, ChessPieces[][] board)
    {
        ChessPieces targetPiece = board[newX][newY];  // Get the target piece at the new position
        if (targetPiece != null && targetPiece.getColor() != this.color)  // Check if the piece is an opponent's piece
        {
            targetPiece.capture();  // Capture the opponent's piece
            board[newX][newY] = null;  // Remove the opponent's piece from the board
            return true;  // Return true indicating a successful capture
        }
        return false;  // Return false if no capture occurred
    }

    public boolean allowMovement(int newX, int newY, ChessPieces[][] board) {
        ChessPieces opponentPiece = board[newX][newY];
        if (opponentPiece == null || opponentPiece.getColor() != this.getColor()) {
            if (opponentPiece != null) {
                System.out.println(opponentPiece.getName() + " " + opponentPiece.getColor() +
                    " was captured at (" + newX + ", " + newY + ").");
            }
            return true; // Valid move
        } else {
            System.out.println("Move invalid: Target square contains a piece of the same color.");
            return false;
        }
    }

    // Method to check if the piece is still active
    public boolean isActive()
    {
        return activeStatus;  // Return the active status of the piece
    }

    // Getter method to return the name of the piece
    public String getName()
    {
        return name;  // Return the name of the piece
    }

    // Getter method to return the color of the piece
    public Color getColor()
    {
        return color; 
    }

    // Method to set the position of the piece
    public void setPosition(int x, int y)
    {
        this.x = x;  
        this.y = y;  
    }

    // Getter method to return the x coordinate of the piece
    public int getX()
    {
        return x;  
    }

    // Getter method to return the y coordinate of the piece
    public int getY()
    {
        return y; 
    }
}

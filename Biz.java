/**
 * @Issye Lailiyah
 * Biz.java
 *
 * Purpose:
 * This class defines the behavior and properties of the Biz chess piece. 
 * The Biz piece is unique in that it moves in an L-shape (3x2 or 2x3 configuration) 
 * in any orientation and can skip over other pieces. It is the only chess piece 
 * with this capability, making it versatile for strategic gameplay.
 *
 * Design:
 * - Inherits common functionality from the ChessPieces superclass.
 * - Implements movement validation specific to Biz.
 * - Handles interactions with target squares, including capturing opponent pieces.
 */
public class Biz extends ChessPieces {

    /**
     * Constructor initializes the Biz piece.
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param color The color of the piece (e.g., black or white)
     */
    public Biz(int x, int y, Color color) {
        super(x, y, "Biz", color); // Calls the superclass constructor to initialize attributes
    }

    /**
     * Validates the move for the Biz piece based on its unique movement rules.
     * Biz can move in an L-shape (3x2 or 2x3) in any orientation, and it can skip other pieces.
     * @param newX The target x-coordinate
     * @param newY The target y-coordinate
     * @param board The current state of the chessboard
     * @return True if the move is valid, false otherwise
     */
    @Override
    public boolean correctMove(int newX, int newY, ChessPieces[][] board) {
        System.out.println(this.getName() + " " + this.getColor() + ":");
        System.out.println("Moving from (" + getX() + ", " + getY() + ") to (" + newX + ", " + newY + ")");

        // Calculate movement differences
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());

        // Validate L-shaped movement (3x2 or 2x3)
        if ((dx == 2 && dy == 1) || (dy == 2 && dx == 1)) {
            return allowMovement(newX, newY, board);
        }

        // Invalid move: Not an L-shape
        System.out.println("Move invalid: Not an L-shaped move.");
        return false;
    }

    /**
     * Sets the new position for the Biz piece and logs the update.
     * @param x New x-coordinate
     * @param y New y-coordinate
     */
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y); // Updates position using the superclass method
    }
}

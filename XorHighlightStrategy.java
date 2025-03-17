public class XorHighlightStrategy implements HighlightStrategy {
    @Override
    public boolean shouldHighlight(int row, int col, int pieceRow, int pieceCol) {
        return Math.abs(row - pieceRow) == Math.abs(col - pieceCol);
    }
}

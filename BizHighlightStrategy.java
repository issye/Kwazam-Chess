public class BizHighlightStrategy implements HighlightStrategy {
    @Override
    public boolean shouldHighlight(int row, int col, int pieceRow, int pieceCol) {
        return (Math.abs(row - pieceRow) == 2 && Math.abs(col - pieceCol) == 1) ||
               (Math.abs(row - pieceRow) == 1 && Math.abs(col - pieceCol) == 2);
    }
}

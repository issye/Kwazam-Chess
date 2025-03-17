public class HighlightStrategyFactory {
    public static HighlightStrategy getStrategy(String pieceName) {
        switch (pieceName) {
            case "Tor": return new TorHighlightStrategy();
            case "Ram": return new RamHighlightStrategy();
            case "Sau": return new SauHighlightStrategy();
            case "Xor": return new XorHighlightStrategy();
            case "Biz": return new BizHighlightStrategy();
            default: throw new IllegalArgumentException("Invalid piece name: " + pieceName);
        }
    }
}

import java.io.*;
import javax.swing.JOptionPane;

public class LoadModel {
    public static void loadGame(ChessModel model, ChessView view, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            if ((line = reader.readLine()) != null) {
                boolean lastPlayedByPink = line.equalsIgnoreCase("Pink Turn");
                model.setTurn(lastPlayedByPink); // Set the turn in the model

                // Update the turn label in ChessView
                view.setTurnDisplay(lastPlayedByPink ? "Pink's Turn" : "Blue's Turn");
            }

            ChessPieces[][] newBoard = new ChessPieces[8][5];
            boolean pinkSauAlive = false;
            boolean blueSauAlive = false;

            // Step 3: Read and process each line for piece data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                String pieceName = parts[0].trim();
                int x, y;
                try {
                    x = Integer.parseInt(parts[1].trim());
                    y = Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid coordinates: " + line);
                    continue;
                }

                ChessPieces.Color color;
                try {
                    color = ChessPieces.Color.valueOf(parts[3].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid color: " + line);
                    continue;
                }

  
                ChessPieces piece = loadPiece(pieceName, color, x, y);
                if (piece != null) {
                    newBoard[x][y] = piece;
                    if (pieceName.equalsIgnoreCase("Sau")) {
                        if (color == ChessPieces.Color.PINK) pinkSauAlive = true;
                        if (color == ChessPieces.Color.BLUE) blueSauAlive = true;
                    }
                } else {
                    System.err.println("Unrecognized piece: " + pieceName);
                }
            }

            // Step 4: Validate Sau
            
            if (!pinkSauAlive || !blueSauAlive) {
                System.err.println("Game file invalid: Missing one or both Kings.");
                model.resetGame();
                view.setTurnDisplay("Pink's Turn"); // Reset to default turn
                return;
            }

            // Step 5: Update the model
            model.setChessBoard(newBoard);

            // Step 6: Update the board display in ChessView
            view.updateBoard();

            System.out.println("Game loaded successfully from " + filename);

        } catch (IOException e) {
            System.err.println("Error loading the game: " + e.getMessage());
        }
    }

    private static ChessPieces loadPiece(String name, ChessPieces.Color color, int x, int y) {
        switch (name.toLowerCase()) {
            case "tor":
                return new Tor(x, y, color);
            case "biz":
                return new Biz(x, y, color);
            case "xor":
                return new Xor(x, y, color);
            case "sau":
                return new Sau(x, y, color);
            case "ram":
                return new Ram(x, y, color);
            default:
                System.err.println("Unrecognized piece: " + name);
                return null;
        }
    }

}


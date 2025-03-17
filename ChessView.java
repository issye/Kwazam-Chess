/**
 * In this cfiles, other than MVC,another design pattern that is implemented is Factory method
 * - Factory method is applied in button creations for the chess game
 */

import javax.swing.*;
import java.awt.*;

public class ChessView {
    private final ChessModel model;  // The game model holding chess game logic
    private ViewController viewController; // Controller for action listener in ChessView
    private GameFlowController gameFlowController;  // Controller for game flow
    private MoveController moveController;  // Controller for handling moves
    private final MenuView menuView; // MenuView instance to show the main menu
    private SaveController saveController; // Controller for save button
    private JFrame gameFrame; // Game window frame
    private JPanel boardPanel; // Panel to display the chessboard
    private JPanel buttonsPanel; // Panel for the buttons (Save, Load, Restart, etc.)

    private JButton[] chessTiles = new JButton[40]; // Array to represent the buttons on the chessboard
    private JButton musicButton; // Button to toggle music
    private JButton saveButton;  // Save button
    private JButton loadButton;  // Load button
    private JLabel turnDisplay; // Label to display the current turn
    private JButton selectedTile = null; // Track the currently selected button

    private static final Color MAIN_COLOR = new Color(41, 128, 185); 
    private static final Color ACCENT_COLOR = new Color(39, 174, 96); 
    private static final Color WARNING_COLOR = new Color(231, 76, 60); 
    private static final Color TEXT_COLOR = Color.WHITE; 
    private static final Color PANEL_BACKGROUND = new Color(44, 62, 80); 
    
    private int selectedX = -1; // Track the x-coordinate of the selected piece
    private int selectedY = -1;

    /**
     * Constructor: Initializes the ChessView object with a ChessModel and MenuView.
     * - Encapsulation: Encapsulates the initialization of game components.
     *
     */
    public ChessView(ChessModel model, MenuView menuView) {
        this.model = model;
        this.menuView = menuView;
        String initialTurn = model.isPinkTurn() ? "Pink's Turn" : "Blue's Turn";

        turnDisplay = new JLabel(initialTurn, SwingConstants.CENTER);
        turnDisplay.setFont(new Font("Arial", Font.BOLD, 18));

        if (gameFrame != null) {
            gameFrame.add(turnDisplay, BorderLayout.NORTH);
        }
    }

    public void setControllers(GameFlowController gameFlowController, MoveController moveController, SaveController saveController, ViewController viewController) {
        this.gameFlowController = gameFlowController;
        this.moveController = moveController;
        this.saveController = saveController;
        this.viewController = viewController;
        menuView.setControllers(gameFlowController);
    }

    // Displays the menu view
    public void showMenu() {
        menuView.showMenu(); 
    }

    // Sets up and starts the main game interface
    public void startGame() {
        menuView.closeMenu();

        gameFrame = new JFrame("Kwazam Chess");
        gameFrame.setSize(900, 700);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonsPanel.setBackground(PANEL_BACKGROUND);

        JButton backToMenuButton = ButtonFactory.createButton(
                "Back to Main Menu", 
                MAIN_COLOR, 
                e -> viewController.backToMenu()
            );
        buttonsPanel.add(backToMenuButton);

        musicButton = ButtonFactory.createButton(
            "Music", 
            ACCENT_COLOR, 
            e -> viewController.music()
        );
        buttonsPanel.add(musicButton);

        JButton restartButton = ButtonFactory.createButton(
                "Restart Game", 
                MAIN_COLOR, 
                e -> viewController.restartGame()
            );
        buttonsPanel.add(restartButton);

        JButton exitButton = ButtonFactory.createButton(
                "Exit Game", 
                WARNING_COLOR, 
                e -> viewController.exitGame()
            );
        buttonsPanel.add(exitButton);

        saveButton = ButtonFactory.createButton(
            "Save Game", 
            ACCENT_COLOR, 
            e -> saveController.saveGame("Saved_game.txt")
        );
        buttonsPanel.add(saveButton);

        mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        boardPanel = new JPanel(new GridLayout(8, 5));
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        createChessBoard();

        gameFrame.add(turnDisplay, BorderLayout.NORTH);
        gameFrame.add(mainPanel);
        gameFrame.setVisible(true);
        updateBoard();
        switchTurn();
    }

    public void createChessBoard(){
        for (int i = 0; i < 40; i++) {
            chessTiles[i] = new JButton();

            if ((i / 5 + i % 5) % 2 == 0) {
                chessTiles[i].setBackground(Color.WHITE);
            } else {
                chessTiles[i].setBackground(Color.BLACK);
            }

            chessTiles[i].setOpaque(true);
            chessTiles[i].setBorderPainted(false);

            final int tilesIndex = i;
            final int x = i / 5;
            final int y = i % 5;
            chessTiles[i].addActionListener(e -> {
                        selectChessBoard(x, y, chessTiles[tilesIndex]);
                        moveController.executeMovement(x, y);
                });

            boardPanel.add(chessTiles[i]);
        }
    }

    private Color resetColor(JButton button) {
        int index = java.util.Arrays.asList(chessTiles).indexOf(button);
        if (index == -1) {
            return Color.WHITE; // Default color if button is not in the array
        }
        return ((index / 5 + index % 5) % 2 == 0) ? Color.WHITE : Color.BLACK;
    }

    private void selectChessBoard(int x, int y, JButton button) {
        ChessPieces selectedPiece = model.getChessBoard()[x][y];

        if (selectedTile != null) {
            selectedTile.setBackground(resetColor(selectedTile));
        }

        if (selectedPiece != null) {
            selectedTile = button;
            selectedTile.setBackground(new Color(255, 255, 102)); // Highlight selected tile
        } else {
            selectedTile = null;
        }
    }

    public void updateBoard() {
        ChessPieces[][] board = model.getChessBoard();
        boolean pinkTurn = model.isPinkTurn();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                int displayRow = i;
                int displayCol = j;

                ChessPieces piece = board[displayRow][displayCol];
                int tilesIndex = i * 5 + j;

                if (piece != null) {
                    chessTiles[tilesIndex].setIcon(loadImage(piece));
                } else {
                    chessTiles[tilesIndex].setIcon(null);
                }
            }
        }
    }

    private ImageIcon loadImage(ChessPieces piece) {
        String imagePath = "";
        String name = piece.getName().toLowerCase();
        ChessPieces.Color color = piece.getColor();

        switch (name) {
            case "ram":
                imagePath = color == ChessPieces.Color.PINK ? "images/Ram.p.png" : "images/Ram.b.png";
                break;
            case "biz":
                imagePath = color == ChessPieces.Color.PINK ? "images/Biz.p.png" : "images/Biz.b.png";
                break;
            case "tor":
                imagePath = color == ChessPieces.Color.PINK ? "images/Tor.p.png" : "images/Tor.b.png";
                break;
            case "xor":
                imagePath = color == ChessPieces.Color.PINK ? "images/Xor.p.png" : "images/Xor.b.png";
                break;
            case "sau":
                imagePath = color == ChessPieces.Color.PINK ? "images/Sau.p.png" : "images/Sau.b.png";
                break;
        }

        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void showGameOverMessage(String winner) {
        // Show a message dialog with the winner
        int option = JOptionPane.showOptionDialog(
                gameFrame,
                winner + " player wins! Game Over!",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Back to Main Menu", "Exit Game"},
                "Back to Main Menu"
            );

        // Handle user selection
        if (option == 0) {
            // User selected "Back to Main Menu"
            model.clearBoard();
            gameFrame.dispose();
            showMenu();
        } else if (option == 1) {
            // User selected "Exit Game"
            viewController.exitGame();
        }
    }

    // Ni untuk make sure boleh tengok game frame
    public void setVisible(boolean visible) {
        gameFrame.setVisible(visible);
    }

    // Ni untuk switch turn kat dalam display atas
    public void switchTurn() {
        if (model.isPinkTurn()) {
            setTurnDisplay("Pink's Turn");
        } else {
            setTurnDisplay("Blue's Turn");
        }
    }

    // Ni font & style untuk display turn kat game
    public void setTurnDisplay(String text) {
        if (turnDisplay == null) {
            turnDisplay = new JLabel(text, JLabel.CENTER);
            turnDisplay.setFont(new Font("Arial", Font.PLAIN, 18));
            gameFrame.add(turnDisplay, BorderLayout.SOUTH);
        } else {
            turnDisplay.setText(text);
        }
    }

    public JFrame getGameFrame() {
        return gameFrame;
    }
}

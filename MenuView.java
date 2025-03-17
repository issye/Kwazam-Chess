import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuView {
    // Main menu frame
    private JFrame mainFrame;

    // Buttons for menu actions
    private JButton musicButton;
    private JButton loadButton; // Load Game Button
    private JButton tutorialButton; // Tutorial Button
    private JButton exitButton;

    // Controllers for game flow and persistence actions
    private GameFlowController gameFlowController; // Handles game flow actions
    private LoadController loadController;
    // Music controller for toggling music
    private final MusicController musicController;
    // Tutorial controller for toggling tutorial
    private TutorialController tutorialController;

    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Color BUTTON_BACKGROUND = Color.BLACK;
    private static final Color BUTTON_FOREGROUND = Color.WHITE;
    private static final Color BUTTON_HOVER = Color.DARK_GRAY;

    /**
     * Constructor initializes the MenuView with a MusicController.
     * @param musicController The controller managing background music.
     */
    public MenuView(MusicController musicController) {
        this.musicController = musicController;
    }
    
    /**
     * Sets the TutorialController for managing tutorial views.
     * @param tutorialController The controller managing tutorial views.
     */
    public void setTutorialController(TutorialController tutorialController) {
        this.tutorialController = tutorialController;
    }
    
    public void setLoadController(LoadController loadController) {
        this.loadController = loadController;
    }

    /**
     * Sets the controllers for game flow and persistence actions.
     * @param gameFlowController The controller for starting a new game.
     * @param persistenceController The controller for saving and loading games.
     */
    public void setControllers(GameFlowController gameFlowController) {
        this.gameFlowController = gameFlowController;
    }

    /**
     * Displays the main menu window with options to start a new game, load a game, and toggle music.
     */
    public void showMenu() {
        mainFrame = new JFrame("Chess Menu");
        mainFrame.setSize(700, 700);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("KWAZAM CHESS!", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JLabel imageLabel = new JLabel();
        File imageFile = new File("menuImage.png");
        if (imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon("menuImage.png");
            Image scaledImage = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            System.err.println("Image file not found: " + imageFile.getAbsolutePath());
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing
        mainPanel.add(imageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        JButton newGameButton = createStyledButton("Start Game");
        newGameButton.addActionListener(e -> {
            if (gameFlowController != null) {
                gameFlowController.startNewGame();
            } else {
                System.err.println("GameFlowController not initialized!");
            }
        });
        buttonPanel.add(newGameButton);
        
        JButton loadGameButton = createStyledButton("Load Game");
        loadGameButton.addActionListener(e -> {
            if (loadController!= null) {
                loadController.loadGame("Saved_game.txt");
            } else {
                System.err.println("Load Controller is not initialized");
            }
        });
        buttonPanel.add(loadGameButton);

        tutorialButton = createStyledButton("Tutorial");
        tutorialButton.addActionListener(e -> {
            if (tutorialController != null) {
                this.closeMenu();
                tutorialController.showTutorial();
            } else {
                System.err.println("TutorialController not initialized!");
            }
        });
        buttonPanel.add(tutorialButton);

        musicButton = createStyledButton("📢");
        musicButton.addActionListener(e -> music());
        buttonPanel.add(musicButton);
        
        JButton exitButton = createStyledButton("Exit");
        exitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);

        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing
        mainPanel.add(buttonPanel);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    
    /**
     * Creates a styled JButton with predefined colors and behaviors.
     * @param text The text to display on the button.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(BUTTON_FOREGROUND);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setRolloverEnabled(true);
        button.getModel().addChangeListener(e -> {
            if (button.getModel().isRollover()) {
                button.setBackground(BUTTON_HOVER);
            } else {
                button.setBackground(BUTTON_BACKGROUND);
            }
        });
        return button;
    }

    /**
     * Toggles the background music on or off using the MusicController.
     * Updates the button text to reflect the current state.
     */
    public void music() {
        musicController.music();
        if (musicController.isPlaying()) {
            musicButton.setText("📢");
        } else {
            musicButton.setText("🔇");
        }
    }

    /**
     * Closes the main menu window.
     */
    public void closeMenu() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
        }
    }
}

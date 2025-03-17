/**
 * Purpose:
 * This class serves as the central controller for managing the game's overall functionality.
 * It integrates the GameFlowController, MoveController, and PersistenceController to 
 * handle game flow, player moves, and saving/loading game states. The GameController acts 
 * as the main entry point for coordinating interactions between the model, view, and controllers.
 */
public class GameController {
    // Handles game flow and high-level game state transitions
    private GameFlowController gameFlowController;

    // Handles player moves and validates chess logic
    private MoveController moveController;

    // Handles saving and loading game states

    /**
     * Constructor initializes the GameController with the model, view, and menu components.
     * @param model The ChessModel representing the game's state.
     * @param view The ChessView for displaying the game.
     * @param menu The MenuView for handling menu interactions.
     */
    public GameController(ChessModel model, ChessView view, MenuView menu) {
        // Initialize the specialized controllers
        this.gameFlowController = new GameFlowController(model, view);
        this.moveController = new MoveController(model, view);
    }

    /**
     * Starts a new game by delegating the action to the GameFlowController.
     */
    public void startNewGame() {
        gameFlowController.startNewGame();
    }

    /**
     * Handles a player's move by delegating to the MoveController.
     * If the game ends as a result of the move, the GameFlowController manages the game-over state.
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     * @return True if the move ends the game, false otherwise.
     */
    public boolean executeMovement(int x, int y) {
        // Boolean isGameOver will be get the update of the current flag from move controller
        boolean isGameOver = moveController.executeMovement(x, y);
        if (isGameOver) {
            // If it returns true, it will be passed to game flow controller
            gameFlowController.handleGameOver();
        }
        return isGameOver;
    }
}

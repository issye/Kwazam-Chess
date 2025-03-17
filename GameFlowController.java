/**
 * The GameFlowController class manages the flow of the chess game.
 * It serves as a mediator between the ChessModel and ChessView, handling game initialization
 * and game-over scenarios.
 */
public class GameFlowController {

    // Reference to the ChessModel (game state and logic)
    private ChessModel model;

    // Reference to the ChessView (user interface)
    private ChessView view;

    /**
     * Constructor to initialize the GameFlowController with the necessary components.
     *
     * @param model The ChessModel instance managing the game state.
     * @param view  The ChessView instance for displaying the game.
     */
    public GameFlowController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Starts a new game by resetting the model and initializing the view.
     */
    public void startNewGame() {
        // Reset the model to initialize a new game state
        model.startNewGame();
        // Update the view to start a new game
        view.startGame();
    }

    /**
     * Handles the game-over by determining the winner and updating the view.
     */

    public void handleGameOver() {
        // Determine the winner based on the current turn
        String winner = model.isPinkTurn() ? "Blue" : "Pink";

        // Notify the view to display the game-over message with the winner
        view.showGameOverMessage(winner);
    }
}

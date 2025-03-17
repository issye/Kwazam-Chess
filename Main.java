public class Main {
    public static void main(String[] args) {
        // Initialize the model
        ChessModel model = new ChessModel();

        // Initialize the music controller with the background music file
        MusicController musicController = new MusicController("Music.wav");

        // Initialize the menu view and link it to the music controller
        MenuView menuView = new MenuView(musicController);

        // Initialize the tutorial controller and link it to the menu view
        TutorialController tutorialController = new TutorialController(menuView);
        menuView.setTutorialController(tutorialController); // Link TutorialController to MenuView

        // Initialize the chess view
        ChessView view = new ChessView(model, menuView);

        // Initialize controllers for game flow, moves, and persistence
        GameFlowController gameFlowController = new GameFlowController(model, view);
        MoveController moveController = new MoveController(model, view);
        SaveController saveController = new SaveController(model, view);
        ViewController viewController = new ViewController(view, model, menuView);

        // Set the controllers in the chess view for integration
        LoadController loadController = new LoadController(model, view, menuView);
        menuView.setLoadController(loadController);
        view.setControllers(gameFlowController, moveController, saveController, viewController);

        // Show the menu to start the game
        menuView.showMenu();
    }
}


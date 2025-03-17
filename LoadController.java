
/**
 * Write a description of LoadController here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LoadController {
     private ChessModel model;
     private ChessView view;
     private MenuView menu;
     
     public LoadController(ChessModel model, ChessView view, MenuView menu) {
        this.model = model;
        this.view = view;
        this.menu = menu;
    }
    
    public void loadGame(String filename) {
        menu.closeMenu();
        view.startGame();
        LoadModel.loadGame(model,view, filename);
        view.updateBoard();
        // Ensure the game view is visible
        view.setVisible(true);
    }
}

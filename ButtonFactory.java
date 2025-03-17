import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonFactory {

    private static final Color TEXT_COLOR = Color.WHITE;

    // Factory method to create buttons
    public static JButton createButton(String text, Color backgroundColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(backgroundColor);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (actionListener != null) {
            button.addActionListener(actionListener);
        }

        return button;
    }
}

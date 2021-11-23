package hw_5;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUIManager {
    ImageIcon setimagewithratio(ImageIcon icon, int width){
        Image temp = icon.getImage();
        float ratio = (float)width / (float)icon.getIconWidth();
        temp = temp.getScaledInstance((int)(icon.getIconWidth()*ratio),(int)(icon.getIconHeight()*ratio), Image.SCALE_SMOOTH);
        icon.setImage(temp);
        return icon;
    }

    Font Fontsetting(File fontfile, Font font, float size){
        {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, fontfile);
                font = font.deriveFont(size);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return font;
    }
}

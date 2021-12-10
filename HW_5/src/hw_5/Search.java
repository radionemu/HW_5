package hw_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Search extends JPanel implements ActionListener {
    Mainframe mainframe;


    File EBFontfile = new File("fonts/HOME/NanumSquareEB.ttf");
    Font textfont;
    File Fontfile = new File("fonts/HOME/NanumSquare_acR.ttf");
    Font searchfont;
    Font Buttonfont;
    Font resultFont;

    JLabel category = new JLabel("▶ SEARCH");
    Toolkit kit = Toolkit.getDefaultToolkit();

    JTextField searchfield = new JTextField();
    JButton searchbtn = new JButton("검색");
    JLabel searchlbl = new JLabel("검색결과가 여기에 나타납니다");

    Search(){
        this.setLayout(null);
        //font
        textfont = new GUIManager().Fontsetting(EBFontfile, textfont, 20);
        searchfont = new GUIManager().Fontsetting(Fontfile, searchfont, 10);
        Buttonfont = new GUIManager().Fontsetting(Fontfile, Buttonfont, 15);
        resultFont = new GUIManager().Fontsetting(Fontfile, resultFont, 30);

        //category
        category.setSize(kit.getScreenSize().width, 50);
        category.setLocation(0,0);
        category.setFont(textfont);
        category.setOpaque(true);
        category.setBackground(new Color(0x036B3F));
        category.setForeground(Color.WHITE);

        //Search
        searchfield.setBounds(20, 70, 180, 30);
        searchfield.setFont(textfont);
        searchfield.addActionListener(this);

        searchbtn.setBounds(200,70,80,30);
        searchbtn.setFont(Buttonfont);
        searchbtn.setBackground(Color.WHITE);
        searchbtn.addActionListener(this);

        //result
        searchlbl.setBounds(20, 90, 600, 150);
        searchlbl.setFont(resultFont);

        this.add(category);
        this.add(searchfield);
        this.add(searchbtn);
        this.add(searchlbl);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchfield|| e.getSource() == searchbtn){
            String str = "<html>"+searchfield.getText()+"<br>"+mainframe.manager.searchVoc(searchfield.getText())+"</html>";
            searchlbl.setText(str);
            searchlbl.revalidate();
            searchlbl.repaint();
        }
    }
}

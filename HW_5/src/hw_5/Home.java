package hw_5;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class Home extends JPanel implements PropertyChangeListener {
    Toolkit kit = Toolkit.getDefaultToolkit();
    Mainframe topframe;

    //Font
    File EBFontfile = new File("fonts/HOME/NanumSquareEB.ttf");
    Font clockfont;
    Font textfont;
    //Color
    Color clockcol = new Color(0x036B3F);

    //Images
    ImageIcon clockback = new ImageIcon("textures/Home/ClockBack.png");

    JLabel clock = new JLabel("00:00");
    JLabel category = new JLabel("▶ HOME");
    JLabel today = new JLabel("오늘의 단어");
    JLabel unable = new JLabel("<html>단어장 파일이 연결되지 않았습니다.<br>설정에 들어가서 단어장 파일의 경로를 확인해주세요.</html>");

    Home(){
        //시계
        clockfont = new GUIManager().Fontsetting(EBFontfile, clockfont, 210);
        textfont = new GUIManager().Fontsetting(EBFontfile, textfont, 20);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        clock.setSize(800, 300);
        clock.setLocation(20,40);
        clock.setFont(clockfont);
        clock.setForeground(clockcol);

        //카테고리
        category.setSize(kit.getScreenSize().width, 50);
        category.setLocation(0,0);
        category.setFont(textfont);
        category.setOpaque(true);
        category.setBackground(clockcol);
        category.setForeground(Color.WHITE);

        //오늘의 단어
        today.setSize(600,50);
        today.setFont(textfont);
        today.setLocation(20, 400);
        //패널
        unable.setBounds(20,470, 400, 200);
        unable.setAlignmentX(CENTER_ALIGNMENT);
        unable.setBackground(new Color(0,0,0, 128));
        unable.setOpaque(true);
        this.add(clock);
        this.add(category);
        this.add(today);
        this.add(unable);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(!VocManager.isadded){
            unable.setVisible(true);
        }else{
            unable.setVisible(false);
        }
    }
}

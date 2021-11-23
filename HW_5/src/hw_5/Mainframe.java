package hw_5;

import javax.security.auth.login.LoginContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;

public class Mainframe extends JFrame implements ActionListener{
    Toolkit kit = Toolkit.getDefaultToolkit();
    Container container = getContentPane();

    //Fonts
    File EBFontfile = new File("fonts/NanumSquareRoundB.ttf");
    Font font;
    Font Sidemenufonts;

    //Color
    Color sidebarColor = new Color(0x036B3F);
    Color sidebarentered = new Color(0x158B4F);
    Color sidebarClicked = new Color(0x289D5F);

    //Main UI
    JPanel SideBar;
    JPanel MainPanel;

    //HOME
    JPanel HomePanel;

    //MainPage
    CardLayout card = new CardLayout();
    Home homeclass = new Home();
    Setting settingclass = new Setting();

    //Side bar
    ImageIcon searchimg = new ImageIcon("textures/sidebars/search.png");
    JButton home = new JButton("홈");
    //list + add,remove
    JButton list = new JButton("단어장 리스트");
    JButton search = new JButton("단어 검색하기");
    JButton Quiz = new JButton("객관식 퀴즈");
    JButton Review = new JButton("오답노트");
    JButton setting = new JButton("설정");

    JButton[] sidebar = {home, list, search, Quiz, Review, setting};

    ImageIcon logo = new ImageIcon("textures/sidebars/KonkukBar.png");

    //Class
    VocManager manager;

    Mainframe(){
        setTitle("영어 단어장 프로그램");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        init();

        setVisible(true);
    }

    private void init() {
        SideBar = new JPanel();
        MainPanel = new JPanel();
        SideBar.setBackground(sidebarColor);
        //Sidebar
        SideBar.setPreferredSize(new Dimension(240, getSize().height));
        SideBar.setLayout(new FlowLayout());
        setSideBar();

        //mainpanel
        MainPanel.setLayout(card);
        MainPanel.add(homeclass, "Home");
        MainPanel.add(settingclass, "Setting");

        //vocManager
        manager = settingclass.vocManager;
        homeclass.topframe = this;
        settingclass.topframe = this;

        container.add(SideBar, BorderLayout.WEST);
        container.add(MainPanel, BorderLayout.CENTER);
    }

    private void setSideBar(){
        Sidemenufonts = new GUIManager().Fontsetting(EBFontfile, Sidemenufonts, 20f);
        logo = new GUIManager().setimagewithratio(logo, 240);
        JLabel Logo = new JLabel(logo);
        SideBar.add(Logo);
        SideBar.add(new JSeparator());

        int count = 0;
        for(JButton jb: sidebar){
            jb.setPreferredSize(new Dimension(230, 60));
            jb.setFont(Sidemenufonts);
            jb.setBackground(sidebarColor);
            jb.setHorizontalAlignment(SwingConstants.LEFT);
            jb.setForeground(Color.WHITE);
            jb.setBorderPainted(false);
            jb.setFocusPainted(false);
            jb.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {jb.setBackground(sidebarentered);}
                @Override
                public void mouseExited(MouseEvent e) {jb.setBackground(sidebarColor);}
            });
            jb.addActionListener(this);

            SideBar.add(jb);
            if(sidebar.length > count)
                SideBar.add(new JSeparator());
            count++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == home){
            card.show(MainPanel, "Home");
        }else if(e.getSource() == setting){
            card.show(MainPanel, "Setting");
        }
    }
}

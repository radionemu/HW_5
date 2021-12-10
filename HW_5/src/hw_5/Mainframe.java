package hw_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Mainframe extends JFrame implements ActionListener{
    Container container = getContentPane();

    //Fonts
    File EBFontfile = new File("fonts/NanumSquareRoundB.ttf");
    Font Sidemenufonts;

    //Color
    Color sidebarColor = new Color(0x036B3F);
    Color sidebarentered = new Color(0x158B4F);
//    Color sidebarClicked = new Color(0x289D5F);

    //Main UI
    JPanel SideBar;
    JPanel MainPanel;

    //HOME

    //MainPage
    CardLayout card = new CardLayout();
    Home homeclass = new Home();
    List listclass = new List();
    Search searchclass = new Search();
    Quiz quizclass = new Quiz();
    Review reviewclass = new Review();
    Setting settingclass = new Setting();

    //Side bar
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
    EventListener eventListener = new EventListener();

    //saveFile
    String Username;
    FileWriter out;
    String date;
    String directory;
    int randint;

    Mainframe(){
        setTitle("영어 단어장 프로그램");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    out = new FileWriter("saves/save.sav");
                    out.write(Username+"\n");
                    out.write(settingclass.dir.getText()+"\n");
                    out.write(settingclass.rescombo.getSelectedIndex()+"\n");
                    out.write(Date()+"\n");
                    out.write(randint+"");
                    out.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //vocManager
        manager = settingclass.vocManager;
        manager.mainframe = this;
        homeclass.topframe = this;
        settingclass.topframe = this;
        listclass.mainframe = this;
        searchclass.mainframe = this;
        quizclass.mainframe = this;
        reviewclass.mainframe = this;

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
        MainPanel.add(listclass, "List");
        MainPanel.add(searchclass, "Search");
        MainPanel.add(quizclass, "Quiz");
        MainPanel.add(reviewclass, "Review");
        MainPanel.add(settingclass, "Setting");

        container.add(SideBar, BorderLayout.WEST);
        container.add(MainPanel, BorderLayout.CENTER);
        MakeFile();
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

            list.setEnabled(false);
            search.setEnabled(false);
            Quiz.setEnabled(false);
            Review.setEnabled(false);

            SideBar.add(jb);
            if(sidebar.length > count)
                SideBar.add(new JSeparator());
            count++;
        }
    }

    public void loadfile(){
        try(Scanner scan = new Scanner(new File("saves/save.sav"))){
                String username = scan.nextLine();
                String directory = scan.nextLine();
                int resolution = Integer.parseInt(scan.nextLine());
                date = scan.nextLine();
                this.directory = directory;
                randint = Integer.parseInt(scan.nextLine());
                Username = username;
                settingclass.applySetting.fileDirection = directory;
                settingclass.applySetting.combobox = resolution;
                settingclass.applySetting.th.start();
                settingclass.rescombo.setSelectedIndex(resolution);
                settingclass.dir.setText(directory);
        }
        catch (FileNotFoundException e){
            //파일 새로 생성
        }
    }

    public void MakeFile(){
        File file = new File("saves/save.sav");
        try {
            if (file.createNewFile()) {
                date = Date();
            } else {
                loadfile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Date(){
        SimpleDateFormat data = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return data.format(date);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == home){
            card.show(MainPanel, "Home");
        }else if(e.getSource() == list){
            listclass.updateList.vocManager = manager;
            listclass.updateList.run();
            card.show(MainPanel, "List");
        }else if(e.getSource() == search){
            card.show(MainPanel, "Search");
        }else if(e.getSource() == Quiz){
            card.show(MainPanel, "Quiz");
        }else if(e.getSource() == Review){
            reviewclass.updateList.vocManager = manager;
            reviewclass.updateList.run();
            card.show(MainPanel, "Review");
        }
        else if(e.getSource() == setting){
            card.show(MainPanel, "Setting");
        }
    }
}

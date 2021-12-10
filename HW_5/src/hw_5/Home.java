package hw_5;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Home extends JPanel {
    Toolkit kit = Toolkit.getDefaultToolkit();
    Mainframe topframe;

    //Font
    File EBFontfile = new File("fonts/HOME/NanumSquareEB.ttf");
    Font clockfont;
    Font textfont;
    File Fontfile = new File("fonts/HOME/NanumSquare_acR.ttf");
    Font labelfont;

    //Color
    Color clockcol = new Color(0x036B3F);

    //Images
    ImageIcon clockback = new ImageIcon("textures/Home/ClockBack.png");


    JLabel category = new JLabel("▶ HOME");
    JLabel today = new JLabel("오늘의 단어");
    JLabel unable = new JLabel("<html>단어장 파일이 연결되지 않았습니다.<br>설정에 들어가서 단어장 파일의 경로를 확인해주세요.</html>");
    JLabel wordl = new JLabel("<html>여기에 영어를 입력<br>여기에 한글을 입력</html>");

    //Today's Voc
    String engs;
    String kors;

    Todayword todayword = new Todayword(null, null);

    Home(){
        //시계
        clockfont = new GUIManager().Fontsetting(EBFontfile, clockfont, 120);
        textfont = new GUIManager().Fontsetting(EBFontfile, textfont, 20);
        labelfont = new GUIManager().Fontsetting(Fontfile, labelfont, 16);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        Timer timer = new Timer();

        //카테고리
        category.setSize(kit.getScreenSize().width, 50);
        category.setLocation(0,0);
        category.setFont(textfont);
        category.setOpaque(true);
        category.setBackground(clockcol);
        category.setForeground(Color.WHITE);

        //오늘의 단어
        today.setSize(600,30);
        today.setFont(textfont);
        today.setLocation(20, 180);
        //패널
        unable.setBounds(20,220, 400, 80);
        unable.setFont(labelfont);
        unable.setHorizontalAlignment(SwingConstants.CENTER);
        unable.setVerticalAlignment(SwingConstants.CENTER);
        unable.setBackground(new Color(50,50,50));
        unable.setForeground(Color.WHITE);
        unable.setOpaque(true);

        wordl.setBounds(20,220, 400, 80);
        wordl.setFont(labelfont);
        wordl.setAlignmentX(CENTER_ALIGNMENT);
        wordl.setBackground(new Color(200,200,200));
        wordl.setForeground(Color.BLACK);
        wordl.setOpaque(true);

        this.add(timer);
        this.add(category);
        this.add(today);
        this.add(unable);
        this.add(wordl);
    }

    class Timer extends JLabel implements Runnable{
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
        public Timer(){
            setSize(800, 200);
            setLocation(10,10);
            setFont(clockfont);
            setForeground(clockcol);
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            while(true){
                this.setText(hour.format(Calendar.getInstance().getTime()));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Todayword implements Runnable{
        VocManager vocManager;
        String direct;
        Thread th = new Thread(this);
        public Todayword(VocManager man, String dir){
            vocManager = man;
            direct = dir;
        }
        @Override
        public void run() {
            if(vocManager == null){
                topframe.homeclass.unable.setVisible(true);
                topframe.homeclass.wordl.setVisible(false);
            }else if(vocManager.voc != null){
                if(!topframe.date.equals(topframe.Date())||!topframe.directory.equals(direct)){
                    //새로 갱신
                    int randint = (int)(Math.random()*vocManager.voc.size())-1;
                    try{
                        engs = vocManager.voc.get(randint).eng;
                        kors = vocManager.voc.get(randint).kor;
                        wordl.setText("<html>"+engs+"<br>"+kors+"</html>");
                        topframe.randint = randint;
                    }catch (ArrayIndexOutOfBoundsException a){
                        topframe.homeclass.unable.setVisible(true);
                        topframe.homeclass.wordl.setVisible(false);
                    }

                }else{
                    engs = vocManager.voc.get(topframe.randint).eng;
                    kors = vocManager.voc.get(topframe.randint).kor;
                    wordl.setText("<html>"+engs+"<br>"+kors+"</html>");
                }
            }
            Thread.yield();
        }
    }
}

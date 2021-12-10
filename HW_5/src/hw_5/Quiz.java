package hw_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Quiz extends JPanel implements ActionListener {
    Mainframe mainframe;


    File EBFontfile = new File("fonts/HOME/NanumSquareEB.ttf");
    Font textfont;
    File Fontfile = new File("fonts/HOME/NanumSquare_acR.ttf");
    Font logofont;
    Font Buttonfont;
    Font resultFont;

    JLabel category = new JLabel("▶ QUIZ");
    Toolkit kit = Toolkit.getDefaultToolkit();

    public JPanel StartPanel = new JPanel();
    JLabel logo = new JLabel("객관식 퀴즈");
    JButton startButton = new JButton("시작");

    JLabel number = new JLabel();
    JLabel quizstr = new JLabel();
    JRadioButton one = new JRadioButton();
    JRadioButton two = new JRadioButton();
    JRadioButton three = new JRadioButton();
    JRadioButton four = new JRadioButton();
    JRadioButton []ans = {one, two, three, four};

    QuizThread qt = new QuizThread(number, quizstr, ans);

    Quiz(){
        this.setLayout(null);
        //font
        textfont = new GUIManager().Fontsetting(EBFontfile, textfont, 20);
        logofont = new GUIManager().Fontsetting(EBFontfile, logofont, 60);
        Buttonfont = new GUIManager().Fontsetting(Fontfile, Buttonfont, 15);
        resultFont = new GUIManager().Fontsetting(Fontfile, resultFont, 30);

        //category
        category.setSize(kit.getScreenSize().width, 50);
        category.setLocation(0,0);
        category.setFont(textfont);
        category.setOpaque(true);
        category.setBackground(new Color(0x036B3F));
        category.setForeground(Color.WHITE);
//        StartPanel.setVisible(false);

        number.setText("00");
        number.setFont(textfont);
        number.setBounds(20,50,60,30);
        quizstr.setText("여기에 텍스트를 입력");
        quizstr.setFont(textfont);
        quizstr.setBounds(20, 80, 300, 30);

        //button
        ButtonGroup group = new ButtonGroup();
        for(int i =0; i<4; i++){
            ans[i].setText(i+"");
            ans[i].setFont(textfont);
            ans[i].setBounds(20, 110+((i)*30), 300, 30);
            ans[i].addActionListener(this);
            ans[i].setVisible(false);
            group.add(ans[i]);
            this.add(ans[i]);
        }

        StartPanel.setBounds(0,50,600,550);
        logo.setFont(logofont);
        logo.setPreferredSize(new Dimension(600,300));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        StartPanel.add(logo);
        startButton.setSize(80, 60);
        startButton.setFont(Buttonfont);
        startButton.addActionListener(this);
        StartPanel.add(startButton);

        this.add(category);
        this.add(number);
        this.add(quizstr);
        //this.add(group);
        this.add(StartPanel);
        number.setVisible(false);
        quizstr.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            StartPanel.setVisible(false);
            quizstr.setVisible(true);
            number.setVisible(true);
            for(int i = 0; i<4; i++)ans[i].setVisible(true);
            qt.run();
        }
        else{
            if(e.getSource() == one){
                mainframe.manager.Check(0);
            }
            if(e.getSource() == two){
                mainframe.manager.Check(1);
            }if(e.getSource() == three){
                mainframe.manager.Check(2);
            }if(e.getSource() == four){
                mainframe.manager.Check(3);
            }
            for(int i = 0; i<4; i++)ans[i].setSelected(false);
            qt.run();
        }
    }

    class QuizThread implements Runnable{
        JLabel quizstr;
        JLabel num;
        JRadioButton[] radioButtons;

        Thread th = new Thread(this);

        public QuizThread(JLabel str, JLabel numb,JRadioButton[] but){
            quizstr = str;
            num = numb;
            radioButtons = but;
        }

        @Override
        public void run() {
            mainframe.manager.Quiz(quizstr, num, radioButtons);
            Thread.yield();
        }
    }
}

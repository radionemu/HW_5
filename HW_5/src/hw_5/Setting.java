package hw_5;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Setting extends JPanel {
    //VocManager
    VocManager vocManager = new VocManager();

    //MainFrame
    Mainframe topframe;

    //Fonts
    File Fontfile = new File("fonts/NanumSquareRoundR.ttf");
    File EBFontfile = new File("fonts/NanumSquareRoundEB.ttf");
    Font title1font;
    Font title2font;
    Font textfont;
    Font catery;


    JComboBox<String> rescombo;
    DefaultComboBoxModel<String> rescombomodel;

    JTextField dir;
    JButton open;
    JButton apply;

    ApplySetting applySetting = new ApplySetting(null, -1);

    ImageIcon profile = new ImageIcon("textures/Setting/profile.jpg");
    Image img = profile.getImage();

    Setting(){
        this.setLayout(null);

        //폰트 세팅
        title1font = new GUIManager().Fontsetting(Fontfile,title1font,30);
        title2font = new GUIManager().Fontsetting(Fontfile,title2font,20);
        textfont = new GUIManager().Fontsetting(Fontfile, textfont, 20);
        catery = new GUIManager().Fontsetting(EBFontfile, textfont, 20);
        JLabel category = new JLabel("▶ SETTING");
        category.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, 50);
        category.setLocation(0,0);
        category.setFont(catery);
        category.setOpaque(true);
        category.setBackground(new Color(0x036B3F));
        category.setForeground(Color.WHITE);

        JLabel general = new JLabel("일반");
        general.setFont(title1font);
        general.setBounds(10, 40, 100, 50);
        JLabel directory = new JLabel("단어장 경로");
        directory.setFont(title2font);
        directory.setBounds(10, 70, 100, 50);

        dir = new JTextField();
        dir.setBounds(10, 110, 200, 20);

        open = new JButton("...");
        open.setBounds(210,110,20,20);
        open.addActionListener(new OpenActionListener());

        JLabel graphic = new JLabel("그래픽");
        graphic.setFont(title1font);
        graphic.setBounds(10, 150, 100, 50);
        JLabel resolution = new JLabel("해상도");
        resolution.setFont(title2font);
        resolution.setBounds(10, 180, 100, 50);

        rescombomodel = new DefaultComboBoxModel<>();
        rescombomodel.addElement("800 * 600");
        rescombomodel.addElement("1024 * 768");
        rescombomodel.addElement("1280 * 720");
        rescombo = new JComboBox<>(rescombomodel);
        rescombo.setSelectedIndex(2);
        rescombo.setBounds(10,220,160,20);

        apply = new JButton("적용");
        apply.setBounds(10, 260, 60, 30);
        apply.addActionListener(new OpenActionListener());


        //라벨 추가
        this.add(category);
        this.add(general);
        this.add(directory);
        this.add(dir);
        this.add(open);

        this.add(graphic);
        this.add(resolution);
        this.add(rescombo);
        this.add(apply);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 10, 300, profile.getIconWidth()/3, profile.getIconHeight()/3, this);
        g.setFont(textfont);
        g.drawString("만든 사람", 20+profile.getIconWidth()/3, 300);
        g.drawString("202111345 이정빈", 20+profile.getIconWidth()/3, 330);
    }

    class OpenActionListener implements ActionListener {

        JFileChooser chooser;
        OpenActionListener() {
            chooser= new JFileChooser();
        }
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == open){
                FileNameExtensionFilter filter = new
                        FileNameExtensionFilter(
                        "text files(.txt)", "txt");
                chooser.setFileFilter(filter);
                int ret = chooser.showOpenDialog(null);
                if(ret != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "파일을 선택하지 않았습니다",
                            "경고", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String filePath = chooser.getSelectedFile().getPath();
                dir.setText(filePath);
            }
            if(e.getSource() == apply){
                applySetting.fileDirection = dir.getText();
                applySetting.combobox = rescombo.getSelectedIndex();
                applySetting.run();
                JOptionPane.showMessageDialog(null, "설정을 적용하였습니다.", "적용 완료", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

//    public void apply(ActionEvent e, String direction,int combo, boolean isapply){
//        //적용하는 페이즈
//        String str = vocManager.makeVoc(direction);
//        if(str != null && isapply){
//            JOptionPane.showMessageDialog(null,str, "경고", JOptionPane.WARNING_MESSAGE);
//        }
//        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Setting.this);
//        int width, height;
//        if(combo == 0){width = 800; height =600;}
//        else if(combo == 1){width = 1024; height= 768;}
//        else{width = 1280; height= 720;}
//        topFrame.setSize(width,height);
//        if(topframe.eventListener.islistadded == true){
//            topframe.homeclass.unable.setVisible(false);
//            topframe.homeclass.wordl.setVisible(true);
//            topframe.list.setEnabled(true);
//            topframe.search.setEnabled(true);
//            topframe.Quiz.setEnabled(true);
//            topframe.Review.setEnabled(true);
//
//            topframe.listclass.listUpdate(topframe.manager);
//            topframe.homeclass.changetodayVoc(topframe.manager, direction);
//            topframe.directory = direction;
//        }
//        else{
//            topframe.homeclass.unable.setVisible(true);
//            topframe.list.setEnabled(false);
//            topframe.search.setEnabled(false);
//            topframe.Quiz.setEnabled(false);
//            topframe.Review.setEnabled(false);
//        }
//    }

        class ApplySetting implements Runnable{
            String fileDirection;
            int combobox;
            Thread th = new Thread(this);
            public ApplySetting(String direction, int combo){
                fileDirection = direction;
                combobox = combo;
            }

            @Override
            public void run() {
                int width, height;
                if(combobox == 0){width = 800; height =600;}
                else if(combobox == 1){width = 1024; height= 768;}
                else{width = 1280; height= 720;}
                topframe.setSize(width,height);
                String str = vocManager.makeVoc(fileDirection);
                if(str != null){
                    JOptionPane.showMessageDialog(null,str, "경고", JOptionPane.WARNING_MESSAGE);
                }
                if(topframe.eventListener.islistadded == true){
                    topframe.homeclass.unable.setVisible(false);
                    topframe.homeclass.wordl.setVisible(true);
                    topframe.list.setEnabled(true);
                    topframe.search.setEnabled(true);
                    topframe.Quiz.setEnabled(true);
                    topframe.Review.setEnabled(true);

                    topframe.homeclass.todayword.vocManager = topframe.manager;
                    topframe.homeclass.todayword.direct = fileDirection;
                    topframe.homeclass.todayword.th.run();
                    topframe.directory = fileDirection;
                }
                else{
                    topframe.homeclass.unable.setVisible(true);
                    topframe.list.setEnabled(false);
                    topframe.search.setEnabled(false);
                    topframe.Quiz.setEnabled(false);
                    topframe.Review.setEnabled(false);
                }
                Thread.yield();
            }
        }
    }

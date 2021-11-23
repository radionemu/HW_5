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
    Font title1font;
    Font title2font;
    Font textfont;

    JComboBox<String> rescombo;
    DefaultComboBoxModel<String> rescombomodel;

    JTextField dir;
    JButton open;
    JButton apply;

    Setting(){
        this.setLayout(null);

        //폰트 세팅
        title1font = new GUIManager().Fontsetting(Fontfile,title1font,30);
        title2font = new GUIManager().Fontsetting(Fontfile,title2font,20);
        textfont = new GUIManager().Fontsetting(Fontfile, textfont, 20);

        JLabel general = new JLabel("일반");
        general.setFont(title1font);
        general.setBounds(10, 10, 100, 50);
        JLabel directory = new JLabel("단어장 경로");
        directory.setFont(title2font);
        directory.setBounds(10, 40, 100, 50);

        dir = new JTextField();
        dir.setBounds(10, 80, 200, 20);

        open = new JButton("...");
        open.setBounds(210,80,20,20);
        open.addActionListener(new OpenActionListener());

        JLabel graphic = new JLabel("그래픽");
        graphic.setFont(title1font);
        graphic.setBounds(10, 120, 100, 50);
        JLabel resolution = new JLabel("해상도");
        resolution.setFont(title2font);
        resolution.setBounds(10, 150, 100, 50);

        rescombomodel = new DefaultComboBoxModel<>();
        rescombomodel.addElement("800 * 600");
        rescombomodel.addElement("1024 * 768");
        rescombomodel.addElement("1280 * 720");
        rescombo = new JComboBox<>(rescombomodel);
        rescombo.setSelectedIndex(2);
        rescombo.setBounds(10,190,160,20);

        apply = new JButton("적용");
        apply.setBounds(10, 230, 60, 30);
        apply.addActionListener(new OpenActionListener());

        //라벨 추가
        this.add(general);
        this.add(directory);
        this.add(dir);
        this.add(open);

        this.add(graphic);
        this.add(resolution);
        this.add(rescombo);
        this.add(apply);
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
                //적용하는 페이즈
                String str = vocManager.makeVoc(dir.getText());
                if(str != null){
                    JOptionPane.showMessageDialog(null,str, "경고", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Setting.this);
                int width, height;
                if(rescombo.getSelectedIndex() == 0){width = 800; height =600;}
                else if(rescombo.getSelectedIndex() == 1){width = 1024; height= 768;}
                else{width = 1280; height= 720;}
                topFrame.setSize(width,height);
            }
        }
    }

}

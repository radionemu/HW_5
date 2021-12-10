package hw_5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class List extends JPanel implements ActionListener {
    Mainframe mainframe;


    File EBFontfile = new File("fonts/HOME/NanumSquareEB.ttf");
    Font clockfont;
    Font textfont;
    File Fontfile = new File("fonts/HOME/NanumSquare_acR.ttf");
    Font listfont;
    Font Buttonfont;

    JLabel category = new JLabel("▶ LIST");

    //리스트
    String []header = {"영어", "한글"};
    String [][] content = new String[0][0];
    DefaultTableModel model = new DefaultTableModel();
    JTable voclist = new JTable(content, header);
    JScrollPane scrollPane;
    JButton addbtn = new JButton("추가하기");
    JDialog adddialog;

    Toolkit kit = Toolkit.getDefaultToolkit();

    UpdateList updateList;

    List() {
        this.setLayout(null);
        //font
        clockfont = new GUIManager().Fontsetting(EBFontfile, clockfont, 120);
        textfont = new GUIManager().Fontsetting(EBFontfile, textfont, 20);
        listfont = new GUIManager().Fontsetting(Fontfile, listfont, 10);
        Buttonfont = new GUIManager().Fontsetting(Fontfile, Buttonfont, 15);

        //category
        category.setSize(kit.getScreenSize().width, 50);
        category.setLocation(0,0);
        category.setFont(textfont);
        category.setOpaque(true);
        category.setBackground(new Color(0x036B3F));
        category.setForeground(Color.WHITE);

        model = new DefaultTableModel(header,0);
        voclist = new JTable(model);
        voclist.setBounds(20, 60, 300, 400);
        voclist.setFont(listfont);
        voclist.setEnabled(false);
        scrollPane = new JScrollPane(voclist);
        scrollPane.setBounds(20, 60, 300, 400);

        //add button
        addbtn.setFont(Buttonfont);
        addbtn.setBounds(20, 480, 100, 40);
        addbtn.addActionListener(this);
        adddialog = new AddDialog(mainframe, "단어 추가하기");

        updateList = new UpdateList(null);

        this.add(category);
        this.add(scrollPane);
        this.add(addbtn);
    }

    public void listUpdate(){
        //list
    }

    public void listAdd(VocManager vocManager, String e, String k){
        vocManager.addWord(new Word(e,k,0));
        String []str = new String[]{e,k};
        model.addRow(str);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource() == addbtn){
        adddialog.setVisible(true);
    }
    }
    class AddDialog extends JDialog{
        JTextField engtxt = new JTextField("", 20);
        JTextField kortxt = new JTextField("", 20);
        JButton ok = new JButton("추가");

        AddDialog(JFrame frame, String title){
            super(frame, title);
            setSize(400, 200);
            setLayout(new FlowLayout());
            add(engtxt);
            add(kortxt);
            add(ok);

            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listAdd(mainframe.manager, engtxt.getText(), kortxt.getText());
                    mainframe.manager.saveWord(mainframe.settingclass.applySetting.fileDirection);
                    setVisible(false);
                }
            });
        }
    }

    class UpdateList implements Runnable{
        VocManager vocManager;

        public UpdateList(VocManager voc){
            vocManager = voc;
        }

        @Override
        public void run() {
            int count = model.getRowCount();
            for(int i =0; i<count; i++){
                model.removeRow(0);
            }
            for(Word word : vocManager.voc){
                String []str = new String[]{word.eng, word.kor};
                model.addRow(str);
            }
        }
    }
}
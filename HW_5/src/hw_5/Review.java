package hw_5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Review extends JPanel implements ActionListener {
    Mainframe mainframe;


    File EBFontfile = new File("fonts/HOME/NanumSquareEB.ttf");
    Font clockfont;
    Font textfont;
    File Fontfile = new File("fonts/HOME/NanumSquare_acR.ttf");
    Font listfont;
    Font Buttonfont;

    JLabel category = new JLabel("▶ QUIZ REVIEW");

    //리스트
    String []header = {"영어", "한글", "틀린 횟수"};
    String [][] content = new String[0][0];
    DefaultTableModel model = new DefaultTableModel();
    JTable voclist = new JTable(content, header);
    JScrollPane scrollPane;

    Toolkit kit = Toolkit.getDefaultToolkit();

    UpdateList updateList;

    Review() {
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
        voclist.setBounds(20, 60, 350, 400);
        voclist.setFont(listfont);
        voclist.setEnabled(false);
        scrollPane = new JScrollPane(voclist);
        scrollPane.setBounds(20, 60, 350, 400);
        updateList = new UpdateList(null);

        this.add(category);
        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
            ArrayList<Word> newlist = (ArrayList<Word>) vocManager.voc.stream().filter(word -> word.corcount>0).sorted().distinct().limit(20).collect(Collectors.toList());
            for(Word word : newlist){
                String []str = new String[]{word.eng, word.kor, word.corcount+""};
                model.addRow(str);
            }
        }
    }
}
package hw_5;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class VocManager {
    private String userName;
    public ArrayList<Word> voc = new ArrayList<>();
    private int maximumnum = 100;

    public boolean init = true;
    public int i=1;
    public int cor=0;
    public int ans;

    public long ftime, stime;
    static Scanner scan = new Scanner(System.in);
    ArrayList<Word> quizarr = new ArrayList<>();


    Mainframe mainframe;

    public void addWord(Word word){
        if(voc.size() < maximumnum)voc.add(word);
        else JOptionPane.showMessageDialog(null,"단어장에 더이상의 단어를 등록할 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
    }

    public void saveWord(String filename){
        try(FileWriter out = new FileWriter(filename)){
            for(Word voca : voc){
                out.write(voca.eng+"\t"+voca.kor+"\t"+voca.corcount+"\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"저장에 실패했습니다.","경고",JOptionPane.WARNING_MESSAGE);
        }
    }

    public String makeVoc(String fileName) {
        voc = new ArrayList<>();
        mainframe.eventListener.islistadded = false;
        try(Scanner scan = new Scanner(new File(fileName))){
            while(scan.hasNextLine()){
                String str = scan.nextLine();
                String[] tmp = str.split("\t");
                try{
                    this.addWord(new Word(tmp[0].trim(),tmp[1].trim(),Integer.parseInt(tmp[2].trim())));
                }catch (ArrayIndexOutOfBoundsException a){
                    return "단어장의 양식이 올바르지 않습니다. \n파일을 확인해주세요.";
                }
            }
            mainframe.eventListener.islistadded = true;
            return null;
        }catch (FileNotFoundException e){
            return "단어장이 생성되지 않았습니다. \n파일명을 확인해주세요.";
        }
    }
    //단어장 범위 내의 단어중 랜덤하게 4개를 고른다.
    //고른 단어 중에 선행 인덱스에 같은게 있으면 취소 후 재실행.
    //인덱스를 완성한 후, 랜덤으로 정답이 될 번호를 고른다.
    //정답이 될 번호의 영어단어 출력 후, 배열 출력
    //인덱스를 맞추면, 정답, 아니면 오답 처리.
    private final int QUIZNUM = 10;
    private final int SELLIM = 4;


    public void Quiz(JLabel number, JLabel strings, JRadioButton[] answers) throws InputMismatchException{
        if(init) {
            cor =0;
            i = 1;
            stime = System.currentTimeMillis();
            init = false;
        }
            quizarr = new ArrayList<>();
            number.setText(i+"");
            a : while(quizarr.size()<SELLIM){
                int num = (int)((Math.random()* voc.size()));
                Word neword = voc.get(num);
                for(Word compword : quizarr){
                    if(compword.equals(neword))continue a;
                    if(compword.kor.equals(neword.kor))continue a;
                }
                quizarr.add(neword);
            }

            ans = (int)((Math.random()*SELLIM));
            //출력
            strings.setText(quizarr.get(ans).eng+"의 뜻은 무엇일까요?");
            for(int a=0; a<SELLIM;a++){
                answers[a].setText((a+1)+") "+quizarr.get(a).kor);
            }
//        }
    }

    public void Check(int answer){
        if(answer == ans){
            JOptionPane.showMessageDialog(null,"정답입니다!","정답",JOptionPane.INFORMATION_MESSAGE);
            cor++;
            i++;
        }
        else{
            JOptionPane.showMessageDialog(null,"틀렸습니다. 정답은 "+ (ans+1) +"번입니다.","오답",JOptionPane.INFORMATION_MESSAGE);
            quizarr.get(ans).corcount ++;
            saveWord(mainframe.directory);
            i++;
        }
        if(i > 10){
            ftime = System.currentTimeMillis();
            int timer = Math.round((ftime-stime)/1000);
            JOptionPane.showMessageDialog(null,userName+"님 "+QUIZNUM+"문제중 "+cor+"개 맞추셨고, 총 "+timer+"초 소요되었습니다.","결과",JOptionPane.INFORMATION_MESSAGE);
            for(int i=0; i<4; i++)mainframe.quizclass.ans[i].setVisible(false);
            mainframe.quizclass.number.setVisible(false);
            mainframe.quizclass.quizstr.setVisible(false);
            mainframe.quizclass.StartPanel.setVisible(true);
            init = true;
        }
    }

    private void searchVoc2() {
        System.out.println("------단어검색2-------");
        System.out.print("검색할 단어의 부분을 선택하세요.");
        String sWord =scan.nextLine();
        sWord = sWord.trim();
        for(Word word : voc) {
            if(word!=null){
                if(word.eng.contains(sWord)){
                    System.out.println(word);
                }
            }else{
                break;
            }
        }
    }

    public String searchVoc(String str){
        String sWord =str;
        sWord = sWord.trim();
        for(Word word : voc) {
            if(word!=null){
                if(word.eng.equals(sWord)){
                    return "단어의 뜻 : "+word.kor;
                }
            }else{
                break;
            }
        }
        return "단어장에 등록되어있지 않습니다.";
    }

    public void frequent(){
        Stream<Word> stream = voc.stream();
        voc.stream().sorted().distinct().limit(5).forEach(System.out::println);
    }
}

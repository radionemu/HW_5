package hw_5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class VocManager {
    private String userName;
    private ArrayList<Word> voc = new ArrayList<>();
    private int number = 0;
    private int maximumnum = 100;

    public static boolean isadded = false;

    static Scanner scan = new Scanner(System.in);

    void addWord(Word word){
        if(voc.size() < maximumnum)voc.add(word);
        else System.out.println("단어장에 더이상의 단어를 등록할 수 없습니다.");
    }

    public String makeVoc(String fileName) {
        isadded = false;
        voc.clear();
        try(Scanner scan = new Scanner(new File(fileName))){
            while(scan.hasNextLine()){
                String str = scan.nextLine();
                String[] tmp = str.split("\t");
                this.addWord(new Word(tmp[0].trim(),tmp[1].trim()));
            }
            isadded = true;
        }catch (FileNotFoundException e){
            return "단어장이 생성되지 않았습니다. \n파일명을 확인해주세요.";
        }finally {

        }
        return null;
    }
    //단어장 범위 내의 단어중 랜덤하게 4개를 고른다.
    //고른 단어 중에 선행 인덱스에 같은게 있으면 취소 후 재실행.
    //인덱스를 완성한 후, 랜덤으로 정답이 될 번호를 고른다.
    //정답이 될 번호의 영어단어 출력 후, 배열 출력
    //인덱스를 맞추면, 정답, 아니면 오답 처리.
    private final int QUIZNUM = 10;
    private final int SELLIM = 4;


    private void Quiz() throws InputMismatchException{
        long stime = System.currentTimeMillis();
        int cor = 0;
        for(int i =1; i<=QUIZNUM; i++){
            ArrayList<Word> quizarr = new ArrayList<>();
            System.out.println("------객관식 퀴즈 "+i+"번-------");
            a : while(quizarr.size()<SELLIM){
                int num = (int)((Math.random()* voc.size()));
                Word neword = voc.get(num);
                for(Word compword : quizarr){
                    if(compword.equals(neword))continue a;
                    if(compword.kor.equals(neword.kor))continue a;
                }
                quizarr.add(neword);
            }
            for(Word word : quizarr){
                System.out.println(word.eng+" "+word.kor);
            }

            int ans = (int)((Math.random()*SELLIM));
            //출력
            System.out.println(quizarr.get(ans).eng+"의 뜻은 무엇일까요?");
            for(int a=0; a<SELLIM;a++){
                System.out.println((a+1)+") "+quizarr.get(a).kor);
            }
            System.out.print("답을 입력하세요. : ");
            try{
                int playersel = scan.nextInt() - 1;
                if(ans == playersel) {
                    System.out.println("정답입니다.");
                    quizarr.get(ans).corcount ++;
                    System.out.println(quizarr.get(ans));
                    cor++;
                }else{
                    System.out.println("틀렸습니다. 정답은 "+ (ans+1) +"번입니다.");
                    scan.nextLine();
                }
            }catch (InputMismatchException ime){
                System.out.println("틀렸습니다. 정답은 "+ (ans+1) +"번입니다.");
                scan.nextLine();
            }

        }
        long ftime = System.currentTimeMillis();
        int timer = Math.round((ftime-stime)/1000);
        System.out.println();
        System.out.println(userName+"님 "+QUIZNUM+"문제중 "+cor+"개 맞추셨고, 총 "+timer+"초 소요되었습니다.");

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

    public void searchVoc(){
        System.out.println("------단어검색-------");
        System.out.print("검색할 단어를 선택하세요.");
        String sWord =scan.nextLine();
        sWord = sWord.trim();
        for(Word word : voc) {
            if(word!=null){
                if(word.eng.equals(sWord)){
                    System.out.println("단어의 뜻 : "+word.kor);
                    return;
                }
            }else{
                //??
                System.out.println("단어장에 등록되어있지 않습니다.");
                break;
            }
        }
        System.out.println("단어장에 등록되어있지 않습니다.");
        return;
    }

    public void frequent(){
        Stream<Word> stream = voc.stream();
        voc.stream().sorted().distinct().limit(5).forEach(System.out::println);
    }
}

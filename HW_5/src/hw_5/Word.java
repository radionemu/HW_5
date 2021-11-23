package hw_5;

public class Word implements Comparable<Word>{
    String eng;
    String kor;

    int corcount = 0;

    public Word(String eng, String kor){
        super();
        this.eng = eng;
        this.kor = kor;
    }

    @Override
    public String toString() {
        return corcount + "번->"+eng+" : "+kor;
    }

    @Override
    public int compareTo(Word o) {
        return o.corcount - this.corcount;
    }
}

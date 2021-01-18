package sample;

public class ArticleNotAvailablce extends Exception {
    public ArticleNotAvailablce(){
        super("Derzeit ausverkauft!");
    }
}

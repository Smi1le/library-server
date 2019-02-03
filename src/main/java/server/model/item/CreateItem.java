package server.model.item;

import lombok.Data;

@Data
public class CreateItem {

    private String word;
    private String translate;

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}

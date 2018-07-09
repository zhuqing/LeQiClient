/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.wordpane;

import com.leqienglish.client.control.skin.LQSkin;
import com.leqienglish.util.text.TextUtil;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import xyz.tobebetter.entity.english.Word;

/**
 *
 * @author zhuleqi
 * @param <T>
 */
public class WordsPaneSkin extends LQSkin<WordsPane, BehaviorBase<WordsPane>> {

    private TextArea textArea;

    private BorderPane root;

    private TilePane wordsPane;

    private TilePane chooseWordsPane;

    public WordsPaneSkin(WordsPane wordsPane) {
        super(wordsPane, new WordsPaneBehavior(wordsPane));
    }

    @Override
    protected void showSkin() {
        super.showSkin(); //To change body of generated methods, choose Tools | Templates.
        this.getChildren().add(root);
    }

    @Override
    protected void initSkin() {
        super.initSkin();
        this.root = new BorderPane();
        this.textArea = new TextArea();
        this.wordsPane = new TilePane();
        wordsPane.maxWidth(200);
        ScrollPane sp1 = new ScrollPane();
        sp1.setContent(wordsPane);
        sp1.maxWidth(200);

        chooseWordsPane = new TilePane();
        chooseWordsPane.maxWidth(200);
        ScrollPane sp2 = new ScrollPane();
        sp2.setContent(chooseWordsPane);
        sp2.maxWidth(200);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(sp1, sp2);

        this.root.setTop(textArea);

        this.root.setCenter(hbox);
        initListener();

    }

    private void initListener() {
        this.getSkinnable().getWords().clear();
        this.textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String[] words = TextUtil.splitWords(newValue);
                
                toWord(words);
            }
        });

        JavaFxObservable.changesOf(this.getSkinnable().getWords())
                .subscribe((c) -> this.chooseWordsChange());

        JavaFxObservable.changesOf(this.getSkinnable().textProperty())
                .filter(c -> c.getNewVal() != null)
                .subscribe((c) -> textArea.setText(c.getNewVal()));

        if (this.getSkinnable().getText() != null) {
            this.textArea.setText(this.getSkinnable().getText());
        }
    }

    private void chooseWordsChange() {
        this.chooseWordsPane.getChildren().clear();
        List<Label> labels = this.getSkinnable().getWords().stream()
                .map((w) -> createChooseWordLabel(w)).collect(Collectors.toList());
        this.chooseWordsPane.getChildren().setAll(labels);
    }

    private Label createChooseWordLabel(Word word) {
        Label label = new Label(word.getWord());
        label.setUserData(word);
        JavaFxObservable.eventsOf(label, MouseEvent.MOUSE_CLICKED)
                .filter(e -> e.getClickCount() == 2)
                .map(e -> (Label) e.getSource())
                .subscribe((l) -> {
                    Word wordE = (Word) l.getUserData();

                    this.chooseWordsPane.getChildren().remove(l);
                    addWords(wordE.getWord());
                    this.getSkinnable().getWords().remove(wordE);
                });

        return label;
    }

    private void toWord(String[] words) {
        
        this.chooseWordsPane.getChildren().clear();
        this.getSkinnable().getWords().clear();

        List<Label> labels = Stream.of(words).filter(p -> !p.trim().isEmpty())
                .distinct().map((w) -> createWordLabel(w))
                .collect(Collectors.toList());

        this.wordsPane.getChildren().setAll(labels);
    }

    private void addWords(String words) {
        Label label = createWordLabel(words);
        this.wordsPane.getChildren().add(label);
    }

    private Label createWordLabel(String word) {
        word = word.toLowerCase();
        Label label = new Label(word);
        label.setUserData(word);
        JavaFxObservable.eventsOf(label, MouseEvent.MOUSE_CLICKED)
                .filter(e -> e.getClickCount() == 2)
                .map(e -> (Label) e.getSource())
                .subscribe((l) -> {
                    Word wordE = toWord(l.getText());
                    this.getSkinnable().getWords().add(wordE);
                    this.wordsPane.getChildren().remove(l);
                });

        return label;
    }

    private Word toWord(String wordStr) {
        Word word = new Word();
        word.setWord(wordStr);
        return word;
    }

    private boolean filtered(String words) {
        return true;
    }

}

package com.wooaccounting.views;

import com.darkprograms.speech.microphone.Microphone;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.sourceforge.javaflacencoder.FLACFileWriter;
import javafx.scene.layout.VBox;
import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import com.wooaccounting.GetdataSQL;
import com.wooaccounting.InsertSQL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AccountingPresenter {

    @FXML
    private View accounting;
    @FXML
    private Label whatyousay;
    @FXML
    private TextField speech2text;
    @FXML
    private Button record, stop, edit, enter, upload;
    @FXML
    private AnchorPane wordpane;
    @FXML
    private ScrollPane scrollpane;
    private String origintext;
    private boolean Q1 = true;
    private VBox v = new VBox();

    public void initialize() {
        accounting.setShowTransitionFactory(BounceInRightTransition::new);

        accounting.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Accounting");
            }
        });

        BOT_default(v);

        Microphone mic = new Microphone(FLACFileWriter.FLAC);

        record.setOnAction(e -> {
            recordClick(mic);
        });

        stop.setOnAction(e -> {
            try {
                stopClick(mic);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        edit.setOnAction(e -> {
            alter();
        });
        enter.setOnAction(e -> {
            changetext();
        });
        upload.setOnAction(e -> {
            uploadtodb();
        });
    }

    private void uploadtodb() {
        InsertSQL insertdb = new InsertSQL();

        String target = "";
        String[] arr = whatyousay.getText().split("\\d");
        for (String s : arr) {
            target += s;
        }

        String number = "";
        String[] arr1 = whatyousay.getText().split("\\D");
        for (String s : arr1) {
            number += s;
        }
        GetdataSQL classify = new GetdataSQL();
        String result = classify.classify(target);
        insertdb.recordtoDB(origintext, target, Integer.parseInt(number), result);

        Label newtext = new Label(target + number);
        newtext.setPrefSize(320, 37);
        newtext.setAlignment(Pos.CENTER_LEFT);
        newtext.setPadding(new Insets(10, 0, 10, 205));
        newtext.setStyle("-fx-background-image: url(\"/com/wooaccounting/views/label1.png\");");

        Label newtext1 = new Label("已經幫您加入資料庫！");
        newtext1.setPrefSize(170, 37);
        newtext1.setPadding(new Insets(10, 0, 10, 30));
        newtext1.setAlignment(Pos.CENTER_LEFT);

        v.getChildren().addAll(newtext, newtext1);

        whatyousay.setText("What You Say");
        speech2text.setPromptText("");
        upload.setDisable(true);
        upload.setVisible(false);
        edit.setDisable(true);
        edit.setVisible(false);
        enter.setDisable(true);
        enter.setVisible(false);

    }

    private void changetext() {
        whatyousay.setText(speech2text.getText());
        speech2text.setDisable(true);
        speech2text.setVisible(false);

        upload.setDisable(false);
        upload.setVisible(true);
        record.setDisable(false);
        record.setVisible(true);
    }

    private void alter() {
        speech2text.setDisable(false);
        speech2text.setVisible(true);
        record.setDisable(true);
        record.setVisible(false);
        enter.setVisible(true);
        enter.setDisable(false);
        upload.setDisable(true);
        upload.setVisible(false);
    }

    private void recordClick(Microphone mic) {

        System.out.println("8888");
        try {
            mic.captureAudioToFile("./record/sound.flac");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("YES~");
        record.setDisable(true);
        record.setVisible(false);
        stop.setDisable(false);
        stop.setVisible(true);

    }

    private void stopClick(Microphone mic) throws Exception {
        mic.close();
        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {

            // The path to the audio file to transcribe
            String fileName = "./record/sound.flac";

            // Reads the audio file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            // Builds the sync recognize request
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(AudioEncoding.FLAC)
                    .setSampleRateHertz(44100)
                    .setLanguageCode("cmn-Hant-TW")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());

                if (0 == "支出".compareTo(alternative.getTranscript())) {
                    whatyousay.setDisable(false);
                    whatyousay.setVisible(true);
                    record.setDisable(false);
                    record.setVisible(true);
                    stop.setDisable(true);
                    stop.setVisible(false);
                    Q1 = false;
                    WE(v);
                } else if (0 == "收入".compareTo(alternative.getTranscript())) {

                } else if (0 == "錢包".compareTo(alternative.getTranscript())) {

                } else {
                    if (Q1) {
                        Label newtext = new Label("沒偵測到請再說一次!");
                        newtext.setPrefSize(320, 37);
                        newtext.setAlignment(Pos.CENTER_LEFT);
                        newtext.setPadding(new Insets(10, 0, 10, 180));
                        newtext.setStyle("-fx-background-image: url(\"/com/wooaccounting/views/label1.png\");");
                        v.getChildren().add(newtext);
                        record.setDisable(false);
                        record.setVisible(true);
                        stop.setDisable(true);
                        stop.setVisible(false);
                    } else {
                        whatyousay.setDisable(false);
                        whatyousay.setVisible(true);

                        origintext = alternative.getTranscript();

                        whatyousay.setText(alternative.getTranscript().replace("我", "").replace("今天", "").replace("吃", "").replace("早上", "").replace("中午", "").replace("晚上", "")
                                .replace("塊", "").replace("元", "").replace("昨天", "").replace("是", "").replace("花", "").replace("了", "").replace("買", "").replace("看", ""));

                        speech2text.setPromptText(alternative.getTranscript().replace("我", "").replace("今天", "").replace("吃", "").replace("早上", "").replace("中午", "").replace("晚上", "")
                                .replace("塊", "").replace("元", "").replace("昨天", "").replace("是", "").replace("花", "").replace("了", "").replace("買", "").replace("看", ""));

                        Label newtext = new Label("確定記錄按upload");
                        newtext.setPrefSize(170, 37);
                        newtext.setPadding(new Insets(10, 0, 10, 30));
                        newtext.setAlignment(Pos.CENTER_LEFT);

                        Label newtext1 = new Label("重新錄音按start");
                        newtext1.setPrefSize(170, 37);
                        newtext1.setPadding(new Insets(10, 0, 10, 30));
                        newtext1.setAlignment(Pos.CENTER_LEFT);

                        Label newtext2 = new Label("直接打字記錄按edit");
                        newtext2.setPrefSize(170, 37);
                        newtext2.setPadding(new Insets(10, 0, 10, 30));
                        newtext2.setAlignment(Pos.CENTER_LEFT);

                        v.getChildren().addAll(newtext, newtext1, newtext2);

                        edit.setDisable(false);
                        edit.setVisible(true);
                        upload.setDisable(false);
                        upload.setVisible(true);
                        record.setDisable(false);
                        record.setVisible(true);
                        stop.setDisable(true);
                        stop.setVisible(false);
                    }
                }
            }
        }

    }

    private void BOT_default(VBox v) {

        v.setAlignment(Pos.CENTER_LEFT);
        v.setPrefSize(330, 47);
        v.setLayoutY(11);
        v.maxHeightProperty().bind(v.heightProperty());

        Label newtext = new Label("Hi , 我是Rigether");
        newtext.setPrefSize(170, 37);
        newtext.setPadding(new Insets(10, 0, 10, 30));
        newtext.setAlignment(Pos.CENTER_LEFT);

        Label newtext1 = new Label("很高興為您服務！");
        newtext1.setPrefSize(170, 37);
        newtext1.setPadding(new Insets(10, 0, 10, 30));
        newtext1.setAlignment(Pos.CENTER_LEFT);

        Label newtext2 = new Label("目前我們有三大功能：");
        newtext2.setPrefSize(170, 37);
        newtext2.setPadding(new Insets(10, 0, 10, 30));
        newtext2.setAlignment(Pos.CENTER_LEFT);

        Label newtext3 = new Label("記錄支出請說  \"支出\"");
        newtext3.setPrefSize(170, 37);
        newtext3.setPadding(new Insets(10, 0, 10, 30));
        newtext3.setAlignment(Pos.CENTER_LEFT);

        Label newtext4 = new Label("記錄收入請說  \"收入\"");
        newtext4.setPrefSize(170, 37);
        newtext4.setPadding(new Insets(10, 0, 10, 30));
        newtext4.setAlignment(Pos.CENTER_LEFT);

        Label newtext5 = new Label("查剩多少錢說  \"錢包\"");
        newtext5.setPrefSize(170, 37);
        newtext5.setPadding(new Insets(10, 0, 10, 30));
        newtext5.setAlignment(Pos.CENTER_LEFT);

        Label newtext6 = new Label("請按下 start 開始錄音");
        newtext6.setPrefSize(170, 37);
        newtext6.setPadding(new Insets(10, 0, 10, 30));
        newtext6.setAlignment(Pos.CENTER_LEFT);

        v.getChildren().addAll(newtext, newtext1, newtext2, newtext3, newtext4, newtext5, newtext6);

        wordpane.getChildren().add(v);

    }

    private void WE(VBox v) {
        Label newtext = new Label("我要記錄支出 !");
        newtext.setPrefSize(320, 37);
        newtext.setAlignment(Pos.CENTER_LEFT);
        newtext.setPadding(new Insets(10, 0, 10, 205));
        newtext.setStyle("-fx-background-image: url(\"/com/wooaccounting/views/label1.png\");");

        Label newtext1 = new Label("我們洗耳恭聽！");
        newtext1.setPrefSize(170, 37);
        newtext1.setPadding(new Insets(10, 0, 10, 30));
        newtext1.setAlignment(Pos.CENTER_LEFT);

        v.getChildren().addAll(newtext, newtext1);

    }
}

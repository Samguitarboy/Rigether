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
import com.wooaccounting.InsertSQL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private Label bot;
    @FXML
    private Button record;
    @FXML
    private Button stop;
    @FXML
    private Button edit;
    @FXML
    private Button enter;
    @FXML
    private Button upload;

    @FXML
    private AnchorPane wordpane;

    private String origintext;
    private int choosenum = 0;
    private int count = 1;

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

        insertdb.recordtoDB(origintext, target, Integer.parseInt(number));
        whatyousay.setText("What You Say");
        speech2text.setPromptText("");

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

                whatyousay.setDisable(false);
                whatyousay.setVisible(true);
                
                VBox v = new VBox();
                v.setAlignment(Pos.CENTER_RIGHT);
                v.setPrefSize(330, 47);
                v.setLayoutY(46 * count -37);
                count++;
                Label newtext = new Label("我說1,我要記錄支出 !");
                newtext.setPrefSize(175, 37);
                newtext.setAlignment(Pos.CENTER_RIGHT);
                newtext.setPadding(new Insets(20));
                newtext.setStyle("-fx-background-image: url(\"/com/wooaccounting/views/label1.png\");");
                v.getChildren().add(newtext);
                wordpane.getChildren().add(0, v);

            }
        }

    }

}

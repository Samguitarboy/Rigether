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

import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AccountingPresenter {

    @FXML
    private View accounting;
    @FXML
    private Label speech2text;
    @FXML
    private Button record;
    @FXML
    private Button stop;
    
    public void initialize() {
        accounting.setShowTransitionFactory(BounceInRightTransition::new);
        
        accounting.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Accounting");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });

        Microphone mic = new Microphone(FLACFileWriter.FLAC);
        
        record.setOnAction(e->{recordClick(mic);});
        stop.setOnAction(e->{
            try {
                stopClick(mic);
            } catch (Exception ex) {
		ex.printStackTrace();
	}});
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
    private void stopClick(Microphone mic)  throws Exception  {
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
        speech2text.setText(alternative.getTranscript());
      }
    }
       record.setDisable(false);
       record.setVisible(true);
       stop.setDisable(true);
       stop.setVisible(false);
    }
}

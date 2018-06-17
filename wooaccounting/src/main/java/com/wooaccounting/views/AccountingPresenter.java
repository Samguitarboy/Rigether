package com.wooaccounting.views;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.sourceforge.javaflacencoder.FLACFileWriter;

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
        GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyD8W-lKEyrwGngsu0-XP1_ztyINGHPGHLw");
        duplex.setLanguage("zh-TW");
        record.setOnAction(e->{recordClick(mic,duplex);});
        stop.setOnAction(e->{stopClick(mic,duplex);});
    }
    
    private void recordClick(Microphone mic,GSpeechDuplex duplex) {
        
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
    private void stopClick(Microphone mic ,GSpeechDuplex duplex) throws Exception {
       mic.close();
       /*try (SpeechClient speechClient = SpeechClient.create()) {

      // The path to the audio file to transcribe
      String fileName = "../record/sound.flac";

      // Reads the audio file into memory
      Path path = Paths.get(fileName);
      byte[] data = Files.readAllBytes(path);
      ByteString audioBytes = ByteString.copyFrom(data);

      // Builds the sync recognize request
      RecognitionConfig config = RecognitionConfig.newBuilder()
          .setEncoding(AudioEncoding.LINEAR16)
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
      }
    }*/
	//duplex.stopSpeechRecognition();
       record.setDisable(false);
       record.setVisible(true);
       stop.setDisable(true);
       stop.setVisible(false);
       speech2text.setText("YO Man ~"); 
       /*duplex.addResponseListener(new GSpeechResponseListener() {
          String old_text = "";	
	   public void onResponse(GoogleResponse gr) {
               String output = "";
		 output = gr.getResponse();
		 if (gr.getResponse() == null) {
                     this.old_text = speech2text.getText();
			if (this.old_text.contains("(")) {
				this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
			}
			System.out.println("Paragraph Line Added");
			this.old_text = ( speech2text.getText() + "\n" );
			this.old_text = this.old_text.replace(")", "").replace("( ", "");
			speech2text.setText(this.old_text);
			return;
		}
		if (output.contains("(")) {
			output = output.substring(0, output.indexOf('('));
		}
		if (!gr.getOtherPossibleResponses().isEmpty()) {
			output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
		}
		System.out.println(output);
		speech2text.setText("");
		speech2text.setText(speech2text.getText()+output);
	    }
	});*/
    }
}

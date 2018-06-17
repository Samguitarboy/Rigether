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
		mic.captureAudioToFile("./record/sound.wav");
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

       record.setDisable(false);
       record.setVisible(true);
       stop.setDisable(true);
       stop.setVisible(false);
    }
}

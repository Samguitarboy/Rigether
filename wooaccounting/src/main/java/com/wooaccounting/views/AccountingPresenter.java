package com.wooaccounting.views;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import javafx.concurrent.Task;
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
        
        record.setOnAction(e->{buttonClick();});
        
    }
    
    private void buttonClick() {
        
        Microphone mic = new Microphone(FLACFileWriter.FLAC);
        GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyD8W-lKEyrwGngsu0-XP1_ztyINGHPGHLw");
        duplex.setLanguage("zh-TW");
        System.out.println("HI");
        record.setDisable(true);
        speech2text.setText("YO Man ~");
        
        
    }
}

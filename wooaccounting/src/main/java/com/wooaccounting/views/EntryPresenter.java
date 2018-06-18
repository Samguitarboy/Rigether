package com.wooaccounting.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.wooaccounting.Wooaccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EntryPresenter {

    @FXML
    private View entry;

    @FXML
    private Button toaccountpage;
    public void initialize() {
        entry.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                toaccountpage.setOnAction(e -> 
                        MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER));
            }
        });
    }
    

    
}

package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CalendarPresenter {

    @FXML
    private View calendar;
    @FXML
    private Label label;
    
    public void initialize() {
        calendar.setShowTransitionFactory(BounceInRightTransition::new);
        
        calendar.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Calendar");
            }
        });
    }
    
    
}

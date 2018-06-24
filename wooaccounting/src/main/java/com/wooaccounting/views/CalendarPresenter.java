package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.time.LocalDate;
import javafx.scene.control.Label;
import com.wooaccounting.GetdataSQL;
import javafx.scene.control.DatePicker;

public class CalendarPresenter {

    @FXML
    private View calendar;
    @FXML
    private Button btnSearch;
    @FXML
    private Label label;
    @FXML
    private DatePicker datepicker;
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
        
        btnSearch.setOnAction(e->{
            GetdataSQL d = new GetdataSQL();
            LocalDate select = datepicker.getValue();
            label.setText(d.searchdate(select.toString()));
        });
        
        datepicker.setValue(LocalDate.now());
    }
    
    
    
}

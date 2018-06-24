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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import com.wooaccounting.GetdataSQL;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CalendarPresenter {

    @FXML
    private View calendar;
    @FXML
    private Button btnSearch;
    @FXML
    private Label label;
    @FXML
    private DatePicker datepicker;
    @FXML
    private AnchorPane wordpane;
    
    private List<Label> lbls = new ArrayList<Label>();
    
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
            setUI();
        });
        
        datepicker.setValue(LocalDate.now());
        lbls.add(label);
        setUI();
    }
    
    void setUI()
    {
        GetdataSQL d = new GetdataSQL();
        LocalDate select = datepicker.getValue();
        String [] data = d.searchdate(select.toString()).split("\n");
        clear_and_new_lbls(data.length);
        
        for(int i=0;i<data.length;++i)
        {
            lbls.get(i).setText(data[i]);
            lbls.get(i).setLayoutY(46 * i);
            wordpane.getChildren().add(0,lbls.get(i));          
        }
        if(data.length==1)
            lbls.get(0).setText(" 今日尚無記錄(´・ω・`)");

    }
    
    void clear_and_new_lbls(int count)
    {
        for(int i =0;i<lbls.size();++i)
            wordpane.getChildren().remove(lbls.get(i));
        lbls.clear();
        
        for(int i=0;i<count;++i)
        {
            Image img1 = new Image("/com/wooaccounting/views/small_icon.jpg");
            ImageView im = new ImageView(img1);
            im.setFitHeight(30);
            im.setFitWidth(30);
            im.setLayoutY(46 * i);
            lbls.add(new Label("",im));
        }
            
        
    }
    
    
    
}

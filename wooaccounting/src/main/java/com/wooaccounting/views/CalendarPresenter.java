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
    private List<Label> lbls1 = new ArrayList<Label>();
    
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
        lbls1.add(label);
        setUI();
    }
    
    void setUI()
    {
        GetdataSQL d = new GetdataSQL();
        String date = datepicker.getValue().toString();
        String [] data = d.searchdate(date).split("\n");
        String [] data1 = d.searchdate1(date).split("\n");
        clear_and_new_lbls(data.length);
        // String [] a = "".spilt("\n") => a.length = 1
        for(int i=0;i<data.length;++i)
        {
            lbls.get(i).setText(data[i]);
            lbls1.get(i).setText(data1[i]);
            wordpane.getChildren().add(0,lbls.get(i));
            wordpane.getChildren().add(0,lbls1.get(i)); 
        }
        if(data[0].compareTo("") == 0)
            lbls.get(0).setText(" 今日尚無記錄(´・ω・`)");
       
    }
    
    void clear_and_new_lbls(int count)
    {
        for(int i =0;i<lbls.size();++i)
        {
            wordpane.getChildren().remove(lbls.get(i));
            wordpane.getChildren().remove(lbls1.get(i));
        }
            
        lbls.clear();
        lbls1.clear();
        for(int i=0;i<count;++i)
        {
            Image img1 = new Image("/com/wooaccounting/views/small_icon.jpg");
            ImageView im = new ImageView(img1);
            im.setFitHeight(30);
            im.setFitWidth(30);
            im.setLayoutY(46 * i);
            Label l1 = new Label("",im);
            l1.setLayoutY(46 * i);
            lbls.add(l1);
            
            Label l2 = new Label();
            l2.setLayoutX(150);
            l2.setLayoutY(46 * i + 5);
            lbls1.add(l2);
        }
            
        
    }
    
    
    
}

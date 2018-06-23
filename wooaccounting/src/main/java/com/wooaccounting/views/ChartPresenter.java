package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class ChartPresenter {

    @FXML
    private View chart;
    @FXML
    private Label label;
    @FXML
    private PieChart piechart;
    

    public void initialize() {
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("娛樂", 20),
                new PieChart.Data("飲食", 40),
                new PieChart.Data("其他", 30),
                new PieChart.Data("交通", 10)
        );
        piechart.setData(pieChartData);
        chart.setShowTransitionFactory(BounceInRightTransition::new);
        chart.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Chart");
            }
        });
    }
    
    
}

package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.GetdataSQL;
import com.wooaccounting.Wooaccounting;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ChartPresenter {

    @FXML
    private View chart;
    @FXML
    private Label total, ent, foods, traf, cloth, living, others;
    @FXML
    private PieChart piechart;
    

    public void initialize() {
        
        GetdataSQL data = new GetdataSQL();
        String test = data.searchall();
        String testv = data.classify("公車");
        System.out.print(testv);
        int entertainment=2000,food=4000,traffic=3000,cloth_v = 1500,living_v = 4500,other=1200;
        int sum = entertainment+food+traffic+cloth_v+living_v+other;
        total.setText(total.getText()+Integer.toString(sum));
        ent.setText(ent.getText()+Integer.toString(entertainment));
        foods.setText(foods.getText()+Integer.toString(food));
        traf.setText(traf.getText()+Integer.toString(traffic));
        cloth.setText(cloth.getText()+Integer.toString(cloth_v));
        living.setText(living.getText()+Integer.toString(living_v));
        others.setText(others.getText()+Integer.toString(other));
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("娛樂", entertainment),
                new PieChart.Data("飲食", food),
                new PieChart.Data("交通", other),
                new PieChart.Data("服飾", cloth_v),
                new PieChart.Data("住宅", living_v),
                new PieChart.Data("其他", traffic)
        );
       piechart.setData(pieChartData);
       piechart.setLegendSide(Side.TOP);
       final Label caption = new Label("");
       caption.setTextFill(Color.DARKORANGE);
       caption.setStyle("-fx-font: 24 arial;");
       DoubleBinding total = Bindings.createDoubleBinding(() ->
       pieChartData.stream().collect(Collectors.summingDouble(PieChart.Data::getPieValue)), pieChartData);
       
       for (final PieChart.Data data : piechart.getData()) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
            e -> {
                caption.setTranslateX(e.getSceneX());
                caption.setTranslateY(e.getSceneY());
                String text = String.format("%.1f%%", 100*data.getPieValue()/total.get()) ;
                caption.setText(text);
             }
            );
    }

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

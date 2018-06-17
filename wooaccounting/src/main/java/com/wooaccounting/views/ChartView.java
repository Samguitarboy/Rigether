package com.wooaccounting.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class ChartView {

    private final String name;

    public ChartView(String name) {
        this.name = name;
    }
    
    public View getView() {
        try {
            View view = FXMLLoader.load(AccountingView.class.getResource("chart.fxml"));
            view.setName(name);
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View(name);
        }
    }
}


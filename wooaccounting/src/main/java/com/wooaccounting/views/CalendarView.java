package com.wooaccounting.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class CalendarView {

    private final String name;

    public CalendarView(String name) {
        this.name = name;
    }
    
    public View getView() {
        try {
            View view = FXMLLoader.load(AccountingView.class.getResource("calendar.fxml"));
            view.setName(name);
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View(name);
        }
    }
}


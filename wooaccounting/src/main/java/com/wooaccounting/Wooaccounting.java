package com.wooaccounting;

import com.wooaccounting.views.EntryView;
import com.wooaccounting.views.AccountingView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Wooaccounting extends MobileApplication {

    public static final String ENTRY_VIEW = HOME_VIEW;
    public static final String ACCOUNTING_VIEW = "Accounting View";
    public static final String MENU_LAYER = "Side Menu";
    
    @Override
    public void init() {
        addViewFactory(ENTRY_VIEW, () -> new EntryView(ENTRY_VIEW).getView());
        addViewFactory(ACCOUNTING_VIEW, () -> new AccountingView(ACCOUNTING_VIEW).getView());
        
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(new DrawerManager().getDrawer()));
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Wooaccounting.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Wooaccounting.class.getResourceAsStream("/icon.png")));
    }
    
    
}

package com.wooaccounting;

import com.wooaccounting.views.EntryView;
import com.wooaccounting.views.AccountingView;
import com.wooaccounting.views.CalendarView;
import com.wooaccounting.views.ChartView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.wooaccounting.views.WalletView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Wooaccounting extends MobileApplication {

    public static final String ENTRY_VIEW = HOME_VIEW;
    public static final String ACCOUNTING_VIEW = "Accounting View";
    public static final String CALENDAR_VIEW = "Calendar View";
    public static final String CHART_VIEW = "Chart View";
    public static final String WALLET_VIEW = "Wallet View";
    public static final String MENU_LAYER = "Side Menu";
    
    @Override
    public void init() {
        addViewFactory(ENTRY_VIEW, () -> new EntryView(ENTRY_VIEW).getView());
        addViewFactory(ACCOUNTING_VIEW, () -> new AccountingView(ACCOUNTING_VIEW).getView());
        addViewFactory(CALENDAR_VIEW, () -> new CalendarView(CALENDAR_VIEW).getView());
        addViewFactory(CHART_VIEW, () -> new ChartView(CHART_VIEW).getView());
        addViewFactory(WALLET_VIEW, () -> new WalletView(WALLET_VIEW).getView());
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(new DrawerManager().getDrawer()));
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        
        ((Stage) scene.getWindow()).getIcons().add(new Image(Wooaccounting.class.getResourceAsStream("/com/wooaccounting/views/small_icon.jpg")));
    }
    
    
    
    
}

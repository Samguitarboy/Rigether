package com.wooaccounting;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.wooaccounting.Wooaccounting.MENU_LAYER;
import static com.wooaccounting.Wooaccounting.ENTRY_VIEW;
import static com.wooaccounting.Wooaccounting.ACCOUNTING_VIEW;
import static com.wooaccounting.Wooaccounting.CALENDAR_VIEW;
import static com.wooaccounting.Wooaccounting.CHART_VIEW;
import static com.wooaccounting.Wooaccounting.WALLET_VIEW;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class DrawerManager {

    private final NavigationDrawer drawer;

    public DrawerManager() {
        this.drawer = new NavigationDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Rigether",
                "Let's get rich together!",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/com/wooaccounting/views/icon.png"))));
        drawer.setHeader(header);
        
        final Item entryItem = new ViewItem("Entry", MaterialDesignIcon.HOME.graphic(), ENTRY_VIEW, ViewStackPolicy.SKIP);
        final Item accountingItem = new ViewItem("Accounting", MaterialDesignIcon.DASHBOARD.graphic(), ACCOUNTING_VIEW);
        final Item calendarItem = new ViewItem("Calendar", MaterialDesignIcon.DASHBOARD.graphic(), CALENDAR_VIEW);
        final Item chartItem = new ViewItem("Chart", MaterialDesignIcon.DASHBOARD.graphic(), CHART_VIEW);
        final Item walletItem = new ViewItem("Wallet", MaterialDesignIcon.DASHBOARD.graphic(), WALLET_VIEW);
        drawer.getItems().addAll(entryItem, accountingItem,calendarItem,chartItem,walletItem);
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
        
        drawer.addEventHandler(NavigationDrawer.ITEM_SELECTED, 
                e -> MobileApplication.getInstance().hideLayer(MENU_LAYER));
        
        MobileApplication.getInstance().viewProperty().addListener((obs, oldView, newView) -> updateItem(newView.getName()));
        updateItem(ENTRY_VIEW);
    }
    
    private void updateItem(String nameView) {
        for (Node item : drawer.getItems()) {
            if (item instanceof ViewItem && ((ViewItem) item).getViewName().equals(nameView)) {
                drawer.setSelectedItem(item);
                break;
            }
        }
    }
    
    public NavigationDrawer getDrawer() {
        return drawer;
    }
}

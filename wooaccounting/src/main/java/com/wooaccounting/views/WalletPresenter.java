package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.Wooaccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class WalletPresenter {

    @FXML
    private View wallet;
    @FXML
    private Label deposit, expend, balance;
    
    public void initialize() {
        int deposit_value=30000, expend_value =11200,balance_value = deposit_value-expend_value;
        
        deposit.setText(deposit.getText()+Integer.toString(deposit_value));
        expend.setText(expend.getText()+Integer.toString(expend_value));
        balance.setText(balance.getText()+Integer.toString(balance_value));
        wallet.setShowTransitionFactory(BounceInRightTransition::new);
        
        wallet.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Wallet");
            }
        });
    }
    
    
}

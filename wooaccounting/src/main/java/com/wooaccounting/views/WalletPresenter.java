package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.GetdataSQL;
import com.wooaccounting.MySQLConnector;
import com.wooaccounting.Wooaccounting;
import com.wooaccounting.config;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WalletPresenter {

    @FXML
    private View wallet;
    @FXML
    private Label deposit, income, expend, balance;

    public void initialize() {
        GetdataSQL show = new GetdataSQL();

        int deposit_value = show.getdeposit();
        int income_value = show.getincome();
        int expend_value = show.getexpenditure();
        // System.out.print(expend_value);

  /*      if (localDate.getDayOfWeek().toString() == "SUNDAY") {
        //    MSC_query.doquery("insert Deposit values(\""++",\"");
        }*/

        int balance_value =/* eposit_value*/ + income_value - expend_value;

        deposit.setText(deposit.getText() + Integer.toString(deposit_value));
        income.setText(income.getText() + Integer.toString(income_value));
        expend.setText(expend.getText() + Integer.toString(expend_value));
        balance.setText(balance.getText() + Integer.toString(balance_value));
        wallet.setShowTransitionFactory(BounceInRightTransition::new);

        wallet.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Wallet");
            }
        });
    }

}

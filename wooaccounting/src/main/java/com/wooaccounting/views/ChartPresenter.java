package com.wooaccounting.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.wooaccounting.MySQLConnector;
import com.wooaccounting.Wooaccounting;
import com.wooaccounting.config;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class ChartPresenter {

    @FXML
    private View chart;
    @FXML
    private Label total, ent, foods, traf, cloth, living, others;
    @FXML
    private PieChart piechart;

    public void initialize() {
        String result = "";
        int entertainment = 0, food = 0, traffic = 0, cloth_v = 0, living_v = 0, other = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        System.out.println(dtf.format(localDate)); //2016/11/16
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        MSC_query.clearresult();
        MSC_query.doquery("select weekday(\"" + localDate.toString() + "\")");
        String current_weekday = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
        System.out.print(current_weekday);
        String querydata;
        MSC_query.clearresult();
        switch (Integer.parseInt(current_weekday)) {
            case 0:
                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                //System.out.print("Monday");
                MSC_query.clearresult();
                break;

            case 1:

                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                //  System.out.print("Tuesday");

                break;

            case 2:

                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                break;
            case 3:
                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                break;
            case 4:
                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                break;
            case 5:
                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                break;
            case 6:
                querydata = "select sum(price) from Accounting where classify =\"飲食\" and (recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    food = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"娛樂\" and (recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    entertainment = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"交通\" and (recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    traffic = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"服飾\" and (recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    cloth_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"住宅\" and (recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    living_v = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where classify =\"其他\" and (recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\")";
                MSC_query.doquery(querydata);
                result = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
                if (!"null".equals(result)) {
                    other = Integer.parseInt(result);
                }
                MSC_query.clearresult();
                break;

        }

        int sum = entertainment + food + traffic + cloth_v + living_v + other;
        total.setText(total.getText() + Integer.toString(sum));
        ent.setText(ent.getText() + Integer.toString(entertainment));
        foods.setText(foods.getText() + Integer.toString(food));
        traf.setText(traf.getText() + Integer.toString(traffic));
        cloth.setText(cloth.getText() + Integer.toString(cloth_v));
        living.setText(living.getText() + Integer.toString(living_v));
        others.setText(others.getText() + Integer.toString(other));
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

        chart.setShowTransitionFactory(BounceInRightTransition::new);
        chart.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(Wooaccounting.MENU_LAYER)));
                appBar.setTitleText("Chart");
            }
        });
    }

}

package com.wooaccounting;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GetdataSQL {

    public String classify(String input) {
        String result = "其他";
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querydata = "select foodname from Food";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        MSC_query.clearresult();

        MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
        if (MSC_query.getResult().toString().contains(input)) {
            result = "飲食";
            System.out.println("it is a food!!");
            MSC_query.clearresult();
            return result;
        }
        querydata = "select entertainmentname from Entertainment";
        MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
        if (MSC_query.getResult().toString().contains(input)) {
            result = "娛樂";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
        }
        querydata = "select trafficname from Traffic";
        MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
        if (MSC_query.getResult().toString().contains(input)) {
            result = "交通";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
        }

        querydata = "select clothname from Cloth";
        MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
        if (MSC_query.getResult().toString().contains(input)) {
            result = "服飾";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
        }
        querydata = "select livingname from Living";
        MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
        if (MSC_query.getResult().toString().contains(input)) {
            result = "住宅";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
        }
        //returndata += MSC_query.getResult().toString().split("\n")[i]+"\n";
        MSC_query.clearresult();
        return result;

    }

    public String searchall() {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querynum = "select count(*) from Accounting";
        String querydata = "select target,price,recordDate from Accounting";
        String returndata = "";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);

        MSC_query.doquery(querynum);
        int count = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();

        MSC_query.doquery(querydata);
        for (int i = 0; i < count; i++) {
            returndata += MSC_query.getResult().toString().split("\n")[i] + "\n";
        }

        MSC_query.clearresult();
        return returndata;
    }

    public String searchdate(String date) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querynum = "select count(*) from Accounting where recordDate ='" + date + "'";
        String querydate = "select target,price from Accounting where recordDate ='" + date + "'";
        String returndata = "";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);

        MSC_query.doquery(querynum);
        int count = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();

        MSC_query.doquery(querydate);

        for (int i = 0; i < count; i++) {
            String str3 = MSC_query.getResult().toString().split("\n")[i];
            returndata += str3 + "\n";

        }
        MSC_query.clearresult();
        return returndata;
    }

    public String searchdate1(String date) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querynum = "select count(*) from Accounting where recordDate ='" + date + "'";
        String querytime = "select modifiedTime from Accounting where recordDate ='" + date + "'";
        String returndata = "";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);

        MSC_query.doquery(querynum);
        int count = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();

        MSC_query.doquery(querytime);

        for (int i = 0; i < count; i++) {
            String str = MSC_query.getResult().toString().split("\n")[i].substring(10);
            String str2 = str.substring(0, str.length() - 3);
            returndata += str2 + "\n";
        }
        MSC_query.clearresult();
        return returndata;
    }

    public int getsurplus() {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querysumofexpenditure = "select sum(price) from Accounting";
        String querysumofincome = "select sum(money) from income";

        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);

        MSC_query.doquery(querysumofexpenditure);
        int expenditure = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();

        MSC_query.doquery(querysumofincome);
        int income = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();

        int surplus = income - expenditure;
        return surplus;
    }

    public int getincome() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        MSC_query.doquery("select weekday(\"" + localDate.toString() + "\")");
        String current_weekday = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
        String querydata = "";
        MSC_query.clearresult();
        switch (Integer.parseInt(current_weekday)) {
            case 0:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 1:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 2:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 3:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 4:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 5:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 6:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
        }
        return 0;
    }

    public int getdeposit() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lastweek = LocalDate.now();
        LocalDate localDate = lastweek.minusDays(7);
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        MSC_query.doquery("select weekday(\"" + localDate.toString() + "\")");
        String current_weekday = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
        String querydata = "";
        MSC_query.clearresult();
        int income = 0, expend = 0;
        switch (Integer.parseInt(current_weekday)) {
            case 0:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                    MSC_query.clearresult();
                    querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.toString()
                            + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                            + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\"";
                }
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();

                return income - expend;

            case 1:
                System.out.print("in!!!");
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                System.out.print(income);
                System.out.print("  ");
                System.out.print(expend);
                return income - expend;
            case 2:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                return income - expend;
            case 3:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                return income - expend;
            case 4:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                return income - expend;
            case 5:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                return income - expend;
            case 6:
                querydata = "select sum(money) from income where recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    income = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\"";
                MSC_query.doquery(querydata);
                if (!"null".equals(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""))) {
                    expend = Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
                }
                MSC_query.clearresult();
                return income - expend;
        }

        return 0;
    }

    public int getexpenditure() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        MSC_query.doquery("select weekday(\"" + localDate.toString() + "\")");
        String current_weekday = MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", "");
        String querydata = "";
        MSC_query.clearresult();
        switch (Integer.parseInt(current_weekday)) {
            case 0:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\" or recordDate = \"" + localDate.plusDays(6).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 1:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\" or recordDate = \"" + localDate.plusDays(5).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 2:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\" or recordDate = \"" + localDate.plusDays(4).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 3:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString()
                        + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\" or recordDate = \"" + localDate.plusDays(3).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 4:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(4).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString()
                        + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\" or recordDate = \"" + localDate.plusDays(2).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 5:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(5).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString() + "\" or recordDate = \"" + localDate.minusDays(2).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\" or recordDate = \"" + localDate.plusDays(1).toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
            case 6:
                querydata = "select sum(price) from Accounting where recordDate = \"" + localDate.minusDays(6).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(5).toString() + "\" or recordDate = \"" + localDate.minusDays(4).toString() + "\" or recordDate = \"" + localDate.minusDays(3).toString()
                        + "\" or recordDate = \"" + localDate.minusDays(2).toString() + "\" or recordDate = \"" + localDate.minusDays(1).toString() + "\" or recordDate = \"" + localDate.toString() + "\"";
                MSC_query.doquery(querydata);
                return Integer.parseInt(MSC_query.getResult().toString().replaceAll("\n", "").replaceAll("\t", ""));
        }
        /*   String querysumofexpenditure = "select sum(price) from Accounting";
        MSC_query.clearresult();
        MSC_query.doquery(querysumofexpenditure);
        int expenditure = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();

        return expenditure;*/
        return 0;
    }
}

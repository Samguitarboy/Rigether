package com.wooaccounting;

public class InsertSQL {

    public void recordtoDB(String origintext, String target, int price, String classify) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String insertrecord = "insert into Accounting(originText,target,price,recordDate,modifiedTime,classify) values(N'" + origintext + "',N'" + target + "'," + price + ",CURRENT_DATE,NOW(),N'" + classify + "');";
        MySQLConnector MSC_insert = new MySQLConnector();
        MSC_insert.connectDB(connectionStr);
        MSC_insert.doInsert(insertrecord);
    }

    public void income_recordtoDB(String reason,  int money) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String insertrecord = "insert into income(reason,money,modifiedTime) values(N'" + reason + "'," + money + ",NOW());";
        MySQLConnector MSC_insert = new MySQLConnector();
        MSC_insert.connectDB(connectionStr);
        MSC_insert.doInsert(insertrecord);
    }
}

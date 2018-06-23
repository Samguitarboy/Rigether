package com.wooaccounting;

public class InsertSQL {

    public void recordtoDB(String origintext, String target , int price) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String insertrecord = "insert into Accounting(originText,target,price,recordDate,modifiedTime) values(N'" + origintext + "',N'" + target + "',"+ price +",CURRENT_DATE,NOW());";
        MySQLConnector MSC_insert = new MySQLConnector();
        MSC_insert.connectDB(connectionStr);
        MSC_insert.doInsert(insertrecord);
    }
}

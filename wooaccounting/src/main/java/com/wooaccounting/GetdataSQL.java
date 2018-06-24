package com.wooaccounting;

public class GetdataSQL {
    public String searchall(){
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
        for(int i=0;i < count;i++)
            returndata += MSC_query.getResult().toString().split("\n")[i]+"\n";

        MSC_query.clearresult();
        return  returndata;
    }
    public String searchdate(String date){
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querynum = "select count(*) from Accounting where recordDate ='"+date+"'";
        String querydate = "select target,price,modifiedTime from Accounting where recordDate ='"+date+"'";
        String returndata = "";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        
        MSC_query.doquery(querynum);
        int count = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();
        
        MSC_query.doquery(querydate);
        for(int i=0;i < count;i++){
            returndata += MSC_query.getResult().toString().split("\n")[i]+"\n";
        }
        MSC_query.clearresult();
        return  returndata;
    }
}

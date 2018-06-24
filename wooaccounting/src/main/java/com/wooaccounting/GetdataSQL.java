package com.wooaccounting;

public class GetdataSQL {
    public String classify(String input){
        String result="其他";
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String querydata = "select foodname from Food";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        MSC_query.clearresult();
        
        MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
        if (MSC_query.getResult().toString().contains(input)){
            result = "飲食";
            System.out.println("it is a food!!");
            MSC_query.clearresult();
            return result;
        }
       querydata = "select entertainmentname from Entertainment";
       MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
       if (MSC_query.getResult().toString().contains(input)){
            result = "娛樂";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
       }
       querydata = "select trafficname from Traffic";
       MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
       if (MSC_query.getResult().toString().contains(input)){
            result = "交通";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
       }
       
       querydata = "select clothname from Cloth";
       MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
       if (MSC_query.getResult().toString().contains(input)){
            result = "服飾";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
       }
       querydata = "select livingname from Living";
       MSC_query.doquery(querydata);
        //    if(MSC_query.getResult().toString()==input)
       if (MSC_query.getResult().toString().contains(input)){
            result = "住宅";
            System.out.println("it is not a food!!");
            MSC_query.clearresult();
            return result;
       }
            //returndata += MSC_query.getResult().toString().split("\n")[i]+"\n";
        MSC_query.clearresult();
        return result;
        
    
    }
    
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
        String querydate = "select target,price from Accounting where recordDate ='"+date+"'";
        String querytime = "select modifiedTime from Accounting where recordDate ='"+date+"'";
        String returndata = "";
        MySQLConnector MSC_query = new MySQLConnector();
        MSC_query.connectDB(connectionStr);
        
        MSC_query.doquery(querynum);
        int count = Integer.valueOf(MSC_query.getResult().toString().trim());
        MSC_query.clearresult();
        
        MSC_query.doquery(querydate);
        
        
        MySQLConnector MSC_query2 = new MySQLConnector();
        MSC_query2.connectDB(connectionStr);       
        MSC_query2.doquery(querytime);
               
        for(int i=0;i < count;i++){
            returndata += MSC_query.getResult().toString().split("\n")[i]+
                     MSC_query2.getResult().toString().split("\n")[i].substring(10)
                    +"\n";
        }
        MSC_query.clearresult();
        MSC_query2.clearresult();
        return  returndata;
    }
}

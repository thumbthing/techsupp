package techsuppDev.techsupp.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import techsuppDev.techsupp.controller.form.ProductListForm;
import techsuppDev.techsupp.controller.form.ProductSingleForm;
import techsuppDev.techsupp.domain.Product;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final EntityManager em;

    public Object findOneProduct(Long id) {
        String sql = " " +
                "select id, moddate, regdate, click_count, information, invest_price, period, product_name, product_status, seq_id, total_price, img_url from " +
                "(select * " +
                "from Product where id = " + id + ") as productdata " +
                "inner join image " +
                "using (id) " +
                "where productdata.id = image.id";


        System.out.println("asdf: "+ sql);
        Query nativeQuery = em.createNativeQuery(sql, ProductSingleForm.class);
        Object singleProduct = nativeQuery.getSingleResult();
        return singleProduct;
    }

    public List<ProductListForm> findFiveProduct(int orderNumber, String keyword) {
        String sql = " " +
                "select id, moddate, regdate, click_count, information, invest_price, period, product_name, product_status, seq_id, total_price, img_url from " +
                "(select * from Product " +
                "where " +
                "period is not Null " +
                "and product_status like 'PROGRESS' order by id desc) as productdata " +
                "inner join image " +
                "using (id) " +
                "where productdata.id = image.id ";


        String limitSql =
                "limit " +
                orderNumber +
                ", 5;" ;

        String keywordSql = "";

        if (keyword.equals("null") || keyword.equals("")) {
            sql = sql + limitSql;
        } else {
            keywordSql = "and product_name like '%" +
                    keyword +
                    "%' ";
            sql = sql + keywordSql + limitSql;
        }

        Query nativeQuery = em.createNativeQuery(sql, ProductListForm.class);

        List<ProductListForm> fiveProduct = nativeQuery.getResultList();
        return fiveProduct;
    }




//    ?????? ????????? ???????????? ???
//    ????????? ?????? x
    public List<Product> findAllProduct() {
        return em.createQuery("select i from Product i", Product.class)
                .getResultList();
    }


//  count for paging(allrows & pagerows)  ????????? ?????? ?????? ???
public Object ProductCount(int pagingNumber, String keyword) {
    String sql = "select ";
    String resultSql;
    String noKeywordSqlAllCount =
            "(select  count(*) from Product " +
            "where product_status like 'PROGRESS')as countAll, ";

    String keywordSqlAllCount =
            "(select  count(*) from Product " +
            "where product_status like 'PROGRESS' " +
            "and product_name like '%" + keyword + "%')as countAll, ";

    if (keyword.equals("null") || keyword.equals("")) {
        resultSql = sql + noKeywordSqlAllCount;
    } else {
        resultSql = sql + keywordSqlAllCount;
    }

    String noKeywordSql =
            "(select count(*) from " +
            "(select  * from Product " +
            "where product_status like 'PROGRESS' " +
            "limit " + pagingNumber + " , 50)as noKeywordData)as pagecount;";

    String keywordSql =
            "(select count(*) from " +
            "(select * from Product " +
            "where product_status like 'PROGRESS' and " +
            "product_name like '%" + keyword + "%' " +
            "limit " + pagingNumber + ", 50)as searchData)as pagecountkeyword;";

    if (keyword.equals("null") || keyword.equals("")) {
        resultSql += noKeywordSql;
    } else {
        resultSql += keywordSql;
    }

    Query nativeQuery = em.createNativeQuery(resultSql);
    Object rowNum = nativeQuery.getSingleResult();
    return rowNum;
}


// ????????? ???????????? ???????????? ?????????
    public List<ProductListForm> findFiveProductFeedback (int orderNumber, String keyword) {

//        String sql = "" +
//                "select * from " +
//                "(select * from product " +
//                "where product_status like '%SUCCESS%' " +
//                "or product_status like '%FAIL%' " +
//                "order by period)successFail ";

        String sql = "" +
                "select id, moddate, regdate, click_count, information, invest_price, period, product_name, product_status, seq_id, total_price, img_url from " +
                "(select * from Product " +
                "where " +
                "product_status like '%SUCCESS%' " +
                "or product_status like '%FAIL%' " +
                "order by id desc) as productdata " +
                "inner join image " +
                "using (id) " +
                "where productdata.id = image.id ";

        String limitSql = "" +
                "limit " + orderNumber + ", 5;" ;

        String keywordSql = "";

        if (keyword.equals("null") || keyword.equals("")) {
            sql = sql + limitSql;
        } else {
            keywordSql = "" +
                    "and product_name like '%" + keyword + "%' ";
            sql = sql + keywordSql + limitSql;
        }

        Query nativeQuery = em.createNativeQuery(sql, ProductListForm.class);
        List<ProductListForm> fiveProduct = nativeQuery.getResultList();

        return fiveProduct;
    }


    //  count for paging(allrows & pagerows)  ????????? ?????? ?????? ???
    public Object FeedbackCount(int pagingNumber, String keyword) {
        String sql = "select ";
        String resultSql;
        String noKeywordSqlAllCount =
                "(select  count(*) from " +
                "(select * from Product " +
                "where " +
                "product_status like '%SUCCESS%' or " +
                "product_status like '%FAIL%' " +
                "order by period)successFailCountAll), ";
        String keywordSqlAllCount =
                "(select  count(*) from " +
                "(select * from Product " +
                "where " +
                "product_status like '%SUCCESS%' or " +
                "product_status like '%FAIL%' " +
                "order by period)as successFail " +
                "where product_name like '%" + keyword + "%' "
                ;
        String endSql = "),";
        if (keyword.equals("null") || keyword.equals("")) {
            resultSql = sql + noKeywordSqlAllCount;
        } else {
            resultSql = sql + keywordSqlAllCount + endSql;
        }

        String noKeywordSql =
                "(select count(*) from " +
                "(select * from Product " +
                "where " +
                "product_status like '%SUCCESS%' or " +
                "product_status like '%FAIL%' order by period " +
                "limit " + pagingNumber + " , 50)as noKeywordData)as pagecount;";

        String keywordSql =
                "(select count(*) from " +
                "(select * from Product " +
                "where " +
                "product_status like '%SUCCESS%' or " +
                "product_status like '%FAIL%' " +
                "order by period)as succesfailcount " +
                "where product_name like '%" + keyword + "%' " +
                "limit " + pagingNumber + ", 50)as searchData;";

        if (keyword.equals("null") || keyword.equals("")) {
            resultSql += noKeywordSql;
        } else {
            resultSql += keywordSql;
        }

        Query nativeQuery = em.createNativeQuery(resultSql);
        Object rowNum = nativeQuery.getSingleResult();
        return rowNum;


    }










































//    ????????? ????????? ?????? ??????
    public Object insertTestData() {
        System.out.println("===========");
        System.out.println("repository");
        String sql = " " +
                "insert into product " +
                "(seq_id, product_name, information, total_price, invest_price, period, product_status, regdate, click_count) values ";
        String middleSql = "(";
        String endSql = ")";

        String seqid = "product_";
        String productName = "testProduct";
        String information = "testInformation";
        String totalPrice;
        String investPrice;
        String cast = "cast('";
        String period;
        String periodUnderbar = "-";
        String periodEnd = " as datetime')";

        String productStatus;
        String createDate;
        String createDateEnd = " as datetime')";
        String clickCount;

//        date random
        Random random = new Random();

        int randomTotalPrice = random.nextInt(999999) + 100000;
        int randomInvestPrice = randomTotalPrice / 10;

        int year, month, day, hour, minute, second, createYear, createMonth, createDay;
        String sYear, sMonth, sDate, sDay;
        year = (int) Math.random() * 20 + 2023;
        String[] stringYear = {"2023", "2024", "2025"};
        String yearqw = stringYear[random.nextInt(2) + 1 - 1];
        String yearInput = stringYear[random.nextInt(2) + 1 - 1].toString();

        month = (int) Math.random() * 2 + 11;
        String[] stringMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String monthqw = stringMonth[random.nextInt(11) + 1 - 1];
        String monthInput = stringMonth[random.nextInt(11) + 1 - 1].toString();

        day = (int) Math.random() * 17 + 11;
        String[] stringDay = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28"};
        String dayInput = stringDay[random.nextInt(27) + 1 - 1].toString();
        hour = (int) Math.random() * 14 + 11;
        String[] stringHour = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24"};
        String hourInput = stringHour[random.nextInt(23) + 1 - 1].toString();
        minute = (int) Math.random() * 49 + 11;
        String[] stringMinute = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        String minuteInput = stringMinute[random.nextInt(59) + 1 - 1].toString();
        second = (int) Math.random() * 49 + 11;
        String[] stringSecond = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        String secondInput = stringSecond[random.nextInt(59) + 1 - 1].toString();
        createYear = year - 1;
        createMonth = month - 1;
        createDay = day - 1;


//        status
        String[] statusArr = {"SUCCESS", "FAIL", "PROGRESS", "CLOSING"};
        String statusInput = statusArr[random.nextInt(4) + 1 - 1].toString();

        int clickCountRandom = random.nextInt(50);
//
//
//
//
        int qwrandomTotalPrice = random.nextInt(999999) + 100000;
        int qwrandomInvestPrice = randomTotalPrice / 10;

        int qwyear, qwmonth, qwday, qwhour, qwminute, qwsecond, qwcreateYear, qwcreateMonth, qwcreateDay;
        String qwsYear, qwsMonth, qwsDate, qwsDay;
        qwyear = (int) Math.random() * 20 + 2023;
        qwmonth = (int) Math.random() * 2 + 11;
        qwday = (int) Math.random() * 17 + 11;
        qwhour = (int) Math.random() * 14 + 11;
        qwminute = (int) Math.random() * 49 + 11;
        qwsecond = (int) Math.random() * 49 + 11;
        qwcreateYear = qwyear - 1;
        qwcreateMonth = qwmonth - 1;
        qwcreateDay = qwday - 1;


////        status
//        String[] qwstatusArr = {"????????????", "????????????", "???????????????", "??????????????????", "???????????????", "??????????????????", "??????", "??????"};
//        String qwstatusInput = statusArr[random.nextInt(7) + 1 - 1].toString();

        int qwclickCountRandom = random.nextInt(50);


        String createRandomDataSql = "";
//
        for (int i = 1; i < 100; i++) {
            seqid = i + ", ";
            productName = "\"" + productName + i + "\", ";
            information = "\"" + information + i + "\", ";
            totalPrice = Integer.toString(random.nextInt(999999) + 100000) + ", ";
            investPrice = Integer.toString((random.nextInt(999999) + 100000) / 10) + ", ";

            period = yearInput + monthInput + dayInput +
                    hourInput + minuteInput + secondInput + ", ";
            productStatus = "\'" + statusInput + "\'" + ", ";
            createDate = yearInput + monthInput + dayInput +
                    hourInput + minuteInput + secondInput + ", ";
            clickCount = Integer.toString(qwclickCountRandom);

            String aa = stringYear[random.nextInt(2) + 1 - 1].toString();
            String as = stringMonth[random.nextInt(11) + 1 - 1].toString();
            String ad = stringDay[random.nextInt(27) + 1 - 1].toString();
            String af = stringHour[random.nextInt(23) + 1 - 1].toString();
            String aq = stringMinute[random.nextInt(59) + 1 - 1].toString();
            String aw = stringSecond[random.nextInt(59) + 1 - 1].toString();






            createRandomDataSql += middleSql + seqid + productName + information +
                    totalPrice +
                    investPrice +
                    stringYear[random.nextInt(2) + 1 - 1].toString() +
                    stringMonth[random.nextInt(11) + 1 - 1].toString() +
                    stringDay[random.nextInt(27) + 1 - 1].toString() +
                    stringHour[random.nextInt(23) + 1 - 1].toString() +
                    stringMinute[random.nextInt(59) + 1 - 1].toString() +
                    stringSecond[random.nextInt(59) + 1 - 1].toString() + ", " +
                    "\'" + statusArr[random.nextInt(3) + 1 - 1].toString() + "\'" + ", " +
                    stringYear[random.nextInt(2) + 1 - 1].toString() +
                    stringMonth[random.nextInt(11) + 1 - 1].toString() +
                    stringDay[random.nextInt(27) + 1 - 1].toString() +
                    stringHour[random.nextInt(23) + 1 - 1].toString() +
                    stringMinute[random.nextInt(59) + 1 - 1].toString() +
                    stringSecond[random.nextInt(59) + 1 - 1].toString() + ", " +
                    Integer.toString(random.nextInt(100) + 1) + endSql + ", ";


            seqid = "";
            productName = "testProduct";
            information = "testInformation";
            totalPrice = "";
            investPrice = "";
            period = "";
            productStatus = "";
            createDate = "";
            clickCount = "";
        };

//        System.out.println(createRandomDataSql);
            for (int i = 100; i < 101; i++) {
                seqid = i + ", ";
                productName = "\"" + productName + i + "\", ";
                information = "\"" + information + i + "\", ";
                totalPrice = Integer.toString(qwrandomTotalPrice) + ", ";
                investPrice = Integer.toString(qwrandomInvestPrice) + ", ";

                period = Integer.toString(qwyear) + Integer.toString(qwmonth) + Integer.toString(qwday) +
                        Integer.toString(qwhour) + Integer.toString(qwminute) + Integer.toString(qwsecond) + ", ";
                productStatus = "\'" + statusInput + "\'" + ", ";
                createDate = yearInput + monthInput + dayInput +
                        hourInput + minuteInput + secondInput + ", ";
                clickCount = Integer.toString(qwclickCountRandom);

                createRandomDataSql += middleSql + seqid + productName + information + totalPrice + investPrice +
                        stringYear[random.nextInt(2) + 1 - 1].toString() +
                        stringMonth[random.nextInt(11) + 1 - 1].toString() +
                        stringDay[random.nextInt(27) + 1 - 1].toString() +
                        stringHour[random.nextInt(23) + 1 - 1].toString() +
                        stringMinute[random.nextInt(59) + 1 - 1].toString() +
                        stringSecond[random.nextInt(59) + 1 - 1].toString() + ", " +
                        "\'" + statusArr[random.nextInt(3) + 1 - 1].toString() + "\'" + ", " +
                        stringYear[random.nextInt(2) + 1 - 1].toString() +
                        stringMonth[random.nextInt(11) + 1 - 1].toString() +
                        stringDay[random.nextInt(27) + 1 - 1].toString() +
                        stringHour[random.nextInt(23) + 1 - 1].toString() +
                        stringMinute[random.nextInt(59) + 1 - 1].toString() +
                        stringSecond[random.nextInt(59) + 1 - 1].toString() + ", " +
                        Integer.toString(random.nextInt(100) + 1) + endSql + ";";

            }
            System.out.println(sql + createRandomDataSql);
            return null;
        }


        public void createinvesttestdata() {
            String sql = "select invest_price from product;";

            Query nativeQuery = em.createNativeQuery(sql);

            ArrayList<Integer> asdf = (ArrayList<Integer>) nativeQuery.getResultList();
            String eee = "";
            for (Integer i : asdf) {
                String fff = asdf.get(asdf.indexOf(i)).toString();
                System.out.println(fff);
                eee += fff + ", ";
            }
            System.out.println(eee);
        }
};



//}
//    ????????? ???????????? ???
//    ?????? ????????? ?????? ?????? pk
//    (????????? ?????? ????????? ????????? ?????? ??? ??? ????????? ?????? ??????????????? ??????????????????
//    ?????????????????? ???????????????????
//    ?????? pk ??? id ??? ???????????? ????????? ????????? ??????
//    ?????? ??? ??? ??????

//    ???????????? ?????? function
//    5?????? ????????? ??????
//    offset = ???????????? ?????? ???
//    limit = ????????? ????????? ?????????
//    ????????? 5?????? ?????? ?????????
//    jpa??? limit ????????? ?????????
//    setFirstResult();
//    setMaxResults();
//    ????????? ????????? ????????????
//    product table ??? ????????? int??? ???????????? ????????? ????????? ???????????? ?????????
//    alter table product modify id int(10);
//    ????????? ???????????? ????????? ????????? ?????? ???????????? ??? ??????
//    public List<Product> findFiveProduct(int pagingId) {
//        int limit = 5;
//
//        return em.createQuery(
//                "select i from Product i where investment is not Null order by id desc", Product.class)
//                .setFirstResult(pagingId)
//                .setMaxResults(limit)
//                .getResultList();
//    }

//    ????????? ??? ????????? ?????? ????????? ????????? ???????????? ???
//    null ??? ???????????? ????????? ?????? ????????? ?????? ??????
//    ?????? 10?????? ??????????????? ???????????????

//    public Object getRow() {
//        Object row = em.createQuery("select count(*) from Product i where i.investment is not null")
//                .getSingleResult();
//
//        return row;
//    }

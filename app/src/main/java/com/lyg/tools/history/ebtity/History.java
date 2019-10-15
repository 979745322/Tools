package com.lyg.tools.history.ebtity;

public class History {
    private String day; // 日期
    private String date; //	事件日期
    private int e_id; // 事件id,即下一接口中所用的e_id
    private String title; // 事件标题
//    private HistoryDetail historyDetail;

    public History() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public HistoryDetail getHistoryDetail() {
//        return historyDetail;
//    }
//
//    public void setHistoryDetail(HistoryDetail historyDetail) {
//        this.historyDetail = historyDetail;
//    }

    @Override
    public String toString() {
        return "History{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", e_id=" + e_id +
                ", title='" + title + '\'' +
//                ", historyDetail=" + historyDetail.toString() +
                '}';
    }
}

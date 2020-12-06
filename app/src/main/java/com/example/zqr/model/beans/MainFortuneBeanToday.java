package com.example.zqr.model.beans;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-12-06
 * Time: 1:03
 */
class MainFortuneBeanToday {
    @Override
    public String toString() {
        return "MainFortuneBeanToday{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", QFriend='" + QFriend + '\'' +
                ", color='" + color + '\'' +
                ", datetime='" + datetime + '\'' +
                ", health='" + health + '\'' +
                ", love='" + love + '\'' +
                ", work='" + work + '\'' +
                ", money='" + money + '\'' +
                ", number=" + number +
                ", summary='" + summary + '\'' +
                ", all='" + all + '\'' +
                ", resultcode='" + resultcode + '\'' +
                ", error_code=" + error_code +
                '}';
    }

    /**
     * date : 20201206
     * name : 狮子座
     * QFriend : 摩羯座
     * color : 灰色
     * datetime : 2020年12月06日
     * health : 60
     * love : 50
     * work : 60
     * money : 70
     * number : 0
     * summary : 今天的狮子座也就是充满热情与创造力的，可以去推进一些事情往前发展。但需要注意在恋爱中可能出现的问题，比如过于孩子气、或者过于浮夸的表现。
     * all : 75
     * resultcode : 200
     * error_code : 0
     */

    private int date;
    private String name;
    private String QFriend;
    private String color;
    private String datetime;
    private String health;
    private String love;
    private String work;
    private String money;
    private int number;
    private String summary;
    private String all;
    private String resultcode;
    private int error_code;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}

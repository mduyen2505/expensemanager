package com.example.expensemanager.model;


import java.io.Serializable;

public class item implements Serializable{
    private int id;
    private String title,category,price,date;
    public item(){
    }
    public item(int id, String title,String category, String price,String date){
        this.id =id;
        this.title= title;
        this.category=category;
        this.price=price;
        this.date=date;
    }
    public item(String title,String category, String price,String date){
        this.title= title;
        this.category=category;
        this.price=price;
        this.date=date;
    }

    public int getId() {
        return id;
    }

}
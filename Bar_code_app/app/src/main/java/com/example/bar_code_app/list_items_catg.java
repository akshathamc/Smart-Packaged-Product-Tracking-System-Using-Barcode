package com.example.bar_code_app;

public class list_items_catg {
    String item_id;
    String item_name;
    String item_desc;
    String item_manu;

    String item_price;
    String item_type;
    String bar_code = "";
    String qty = "";
    String added_date = "";
    String added_time = "";
    String exp_date = "";
    String status = "";



    public list_items_catg(String item_id, String item_name, String item_desc, String item_manu, String item_price, String item_type, String bar_code, String qty, String added_date, String added_time, String exp_date, String status) {

        this.item_id = item_id;
        this.item_name = item_name;
        this.item_desc = item_desc;
        this.item_manu = item_manu;
        this.item_price = item_price;
        this.item_type = item_type;
        this.bar_code = bar_code;
        this.qty = qty;
        this.added_date = added_date;
        this.added_time = added_time;
        this.exp_date = exp_date;
        this.status = status;



    }
}

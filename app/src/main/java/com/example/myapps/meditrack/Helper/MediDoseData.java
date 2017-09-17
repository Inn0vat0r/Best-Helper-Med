package com.example.myapps.meditrack.Helper;

/**
 * Created by lifemapsolutions on 18-06-2017.
 */

public class MediDoseData {
   private int med_id;
    private String med_name;
    private String dose_num;
    private String dose_time;
    private int med_num;
    private int med_num_pur;
    private String dose_freq;

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getDose_num() {
        return dose_num;
    }

    public void setDose_num(String dose_num) {
        this.dose_num = dose_num;
    }

    public String getDose_time() {
        return dose_time;
    }

    public void setDose_time(String dose_time) {
        this.dose_time = dose_time;
    }

    public int getMed_num() {
        return med_num;
    }

    public void setMed_num(int med_num) {
        this.med_num = med_num;
    }

    public int getMed_num_pur() {
        return med_num_pur;
    }

    public void setMed_num_pur(int med_num_pur) {
        this.med_num_pur = med_num_pur;
    }

    public String getDose_freq() {
        return dose_freq;
    }

    public void setDose_freq(String dose_freq) {
        this.dose_freq = dose_freq;
    }
}

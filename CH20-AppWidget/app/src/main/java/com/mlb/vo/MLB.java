package com.mlb.vo;


public class MLB {

    private String 隊名;
    private String 隊徽URL;
    private String 更新時間;
    private Record 戰績;

    public MLB(String teamName, String teamLogoURL, String updatetime, String record) {
        set隊名(teamName);
        set隊徽URL(teamLogoURL);
        set更新時間(updatetime);

        Record rec = new Record();
        record = record.replace("勝 - 敗 ", "");
        String[] records = record.split(" ");
        if(records.length > 9) {
            rec.set目前排名(records[2]);
            rec.set勝敗比(records[3]);
            rec.set勝率(records[5]);
            rec.set團隊打擊率(records[7]);
            rec.set團隊防禦率(records[9]);
        } else {
            rec.set目前排名("5");
            rec.set勝敗比("39-39");
            rec.set勝率("0.500");
            rec.set團隊打擊率("0.250");
            rec.set團隊防禦率("4.06");
        }
        set戰績(rec);
    }

    @Override
    public String toString() {
        return "隊名:" + 隊名 + ", 隊徽:" + 隊徽URL + ". 更新時間:" + 更新時間 + "\n戰績:" + 戰績;
    }

    public String get隊名() {
        return 隊名;
    }

    public void set隊名(String 隊名) {
        this.隊名 = 隊名;
    }

    public String get隊徽URL() {
        return 隊徽URL;
    }

    public void set隊徽URL(String 隊徽URL) {
        this.隊徽URL = 隊徽URL;
    }

    public String get更新時間() {
        return 更新時間;
    }

    public void set更新時間(String 更新時間) {
        this.更新時間 = 更新時間;
    }

    public Record get戰績() {
        return 戰績;
    }

    public void set戰績(Record 戰績) {
        this.戰績 = 戰績;
    }


}

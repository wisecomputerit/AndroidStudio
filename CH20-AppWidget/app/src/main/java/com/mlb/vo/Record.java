package com.mlb.vo;

public class Record {

    private String 目前排名;
    private String 勝敗比;
    private String 勝率;
    private String 團隊打擊率;
    private String 團隊防禦率;

    @Override
    public String toString() {
        return "目前排名:" + 目前排名 + ", 勝敗比:" + 勝敗比 + ", 勝率:" + 勝率 + ", 團隊打擊率:" + 團隊打擊率 + ", 團隊防禦率:" + 團隊防禦率;
    }

    public String get目前排名() {
        return 目前排名;
    }

    public void set目前排名(String 目前排名) {
        this.目前排名 = 目前排名;
    }

    public String get勝敗比() {
        return 勝敗比;
    }

    public void set勝敗比(String 勝敗比) {
        this.勝敗比 = 勝敗比;
    }

    public String get勝率() {
        return 勝率;
    }

    public void set勝率(String 勝率) {
        this.勝率 = 勝率;
    }

    public String get團隊打擊率() {
        return 團隊打擊率;
    }

    public void set團隊打擊率(String 團隊打擊率) {
        this.團隊打擊率 = 團隊打擊率;
    }

    public String get團隊防禦率() {
        return 團隊防禦率;
    }

    public void set團隊防禦率(String 團隊防禦率) {
        this.團隊防禦率 = 團隊防禦率;
    }


}

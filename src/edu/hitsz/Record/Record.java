package edu.hitsz.Record;

import java.util.Date;

public class Record {
    private String username;

    private int score;

    private String date;

    private int id;

    public int getScore() {
        return score;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Record(String username, int score, String date) {
        this.username = username;
        this.score = score;
        this.date = date;
    }

    public String toString(int rank) {
        String res;
        res = String.format("rank:%d,id:%d,username:%s,score:%d,date:%s",
                rank, id, username, score, date);
        return res;
    }
}

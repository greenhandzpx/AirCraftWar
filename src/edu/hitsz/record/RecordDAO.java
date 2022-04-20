package edu.hitsz.record;

import java.util.List;

public interface RecordDAO {

    void addRecord(Record record);

    void deleteRecord(int id);

    List<String[]> getAllRecords();
}

package edu.hitsz.Record;

import java.util.Date;
import java.util.List;

public interface RecordDAO {

    void addRecord(Record record);

    void deleteRecord(int id);

    List<String[]> getAllRecords();
}

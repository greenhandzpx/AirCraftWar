package edu.hitsz.record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author greenhandzpx
 */
public class FileRecordDAOImpl implements RecordDAO {

    private final String fileName;

    private int id;

    public FileRecordDAOImpl(String fileName) {
        this.id = 0;
        this.fileName = fileName;
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("成功创建一份游戏记录档案");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void addRecord(Record record) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("成功创建一份游戏记录档案");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            List<String[]> records = readRecords();
            record.setId(this.id++);
            int location = -1;
            /*
            由于历史记录的分数是升序排列，只需要遍历找到第一个分数比新记录低的历史记录即可
             */
            for (int i = 0; i < records.size(); i++) {
                int score = Integer.parseInt(records.get(i)[3].substring(6));
                if (score < record.getScore()) {
                    location = i;
                    break;
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            if (location != -1) {
                /*
                修改分数比新添加记录低的历史记录的排名
                 */
                for (int i = location; i < records.size(); i++) {
                    int rank = Integer.parseInt(records.get(i)[0].substring(5));
                    rank += 1;
                    records.get(i)[0] = "rank:" + rank;
                }
                for (int i = 0; i < records.size(); i++) {
                    try {
                        if (i == location) {
                            writer.write(record.toString(location + 1) + "\n");
                        }
                        writer.write(String.join(",", records.get(i)) + "\n");
                    } catch (IOException e) {
                        System.out.println("写入失败");
                    }
                }
            } else {
                /*
                说明新添加的历史记录分数最低，应排在最后
                 */
                for (String[] strings : records) {
                    System.out.println(String.join(",", strings));
                    try {
                        writer.write(String.join(",", strings) + "\n");
                    } catch (IOException e) {
                        System.out.println("写入失败");
                    }
                }
                try {
                    writer.write(record.toString(records.size() + 1) + "\n");
                } catch (IOException e) {
                    System.out.println("写入失败");
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("该游戏记录档案不存在，存储失败!");
        } catch (IOException e) {
            System.out.println("关闭写入流失败");
        }
    }

    @Override
    public void deleteRecord(int id) {
        // TODO
        System.out.println("文件形式存储尚未实现删除记录功能，敬请期待...");
    }


    @Override
    public List<String[]> getAllRecords() {
        return readRecords();
    }

    /**
     * 读入文件中的所有历史记录
     * @return 历史记录的列表
     */
    public List<String[]> readRecords() {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            List<String[]> records = new ArrayList<>();
            String line;
            try {
                while (Objects.nonNull(line = reader.readLine())) {
                    records.add(line.split(","));
                }
            } catch (IOException e) {
                System.out.println("读取记录档案失败");
            }
            reader.close();
            return records;
        } catch (FileNotFoundException e) {
            System.out.println("记录档案不存在");
        } catch (IOException e) {
            System.out.println("关闭读出流失败");
        }
        return new ArrayList<>();
    }
}

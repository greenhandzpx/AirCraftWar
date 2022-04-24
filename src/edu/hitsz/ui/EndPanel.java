package edu.hitsz.ui;

import edu.hitsz.record.FileRecordDAOImpl;
import edu.hitsz.record.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EndPanel {
    public JPanel panel1;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTable scoreTable;
    private JScrollPane scrollPane;
    private JButton deleteButton;
    private JLabel difficultyLabel;
    private JLabel titleLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("EndPanel");
        frame.setContentPane(new EndPanel(50).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public EndPanel(int score) {
        String difficulty;
        String fileName;
        switch (StartPanel.DIFFICULTY) {
            case EASY:
                difficulty = "EASY";
                fileName = "easyRecords.csv";
                break;
            case NORMAL:
                difficulty = "NORMAL";
                fileName = "normalRecords.csv";
                break;
            case HARD:
                difficulty = "HARD";
                fileName = "hardRecords.csv";
                break;
            default:
                difficulty = "";
                fileName = "";
        }
        difficultyLabel.setText("难度："+difficulty);

        FileRecordDAOImpl fileRecordDAO = new FileRecordDAOImpl(fileName);
        String[] columnName = {"名次", "ID", "玩家名", "得分", "记录时间"};

        String name = JOptionPane.showInputDialog(panel1, "游戏结束，你的得分为:"+score+"\n请输入名字记录得分:");
        // 将当前日期转为String格式
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        // 新建一份记录
        Record record = new Record(name, score, date);
        // 添加到历史记录
        fileRecordDAO.addRecord(record);

        List<String[]> tableData = fileRecordDAO.getAllRecords();
        String[][] data = new String[tableData.size()][0];
        for (int i = 0; i < data.length; i++) {
            data[i] = new String[5];
            String[] dummy = tableData.get(i);
            for (int k = 0; k < dummy.length; k++) {
                int l = 0;
                for (; l < dummy[k].length(); l++) {
                    if (dummy[k].charAt(l) == ':') {
                        break;
                    }
                }
                data[i][k] = dummy[k].substring(l+1);
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        scoreTable.setModel(model);
        scrollPane.setViewportView(scoreTable);


        deleteButton.addActionListener(e -> {
            int row = scoreTable.getSelectedRow();
            if (row != -1) {
                int ans = JOptionPane.showConfirmDialog(panel1, "你确定要删除吗？");
                if (ans == JOptionPane.YES_OPTION) {
                    System.out.println("确定");
                    model.removeRow(row);
                    fileRecordDAO.deleteRecord(row);
                    System.out.printf("删除第%d行\n", row);

                } else  {
                    System.out.println("取消");
                }
            }
        });
    }




}

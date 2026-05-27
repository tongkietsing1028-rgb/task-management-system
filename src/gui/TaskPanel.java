package gui;

import model.Task;
import model.User;
import model.enums.TaskPriority;
import model.enums.TaskStatus;
import service.TaskService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskPanel extends JPanel {
    private User user;


    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    private JPopupMenu popupMenu;

       public TaskPanel(User user)
    {
        this.user = user;
        InitialUI();
        loadTableData();
    }

    private void InitialUI()
    {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton addTaskButton = new JButton("Add Task");
        JButton searchButton = new JButton("Search");

        searchField = new JTextField(20);

        topPanel.add(addTaskButton);



        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        JMenuItem changeStatusMenuItem = new JMenuItem("Change Status");
        JMenuItem changePriorityMenuItem = new JMenuItem("Change Priority");
        JMenuItem editMenuItem = new JMenuItem("Edit");
        popupMenu.add(deleteMenuItem);
        popupMenu.add(changeStatusMenuItem);
        popupMenu.add(changePriorityMenuItem);
        popupMenu.add(editMenuItem);







        String[] columnNames = {"ID","Title", "Description", "Priority", "Status","Owner","Group"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(MouseEvent e)
            {
                if(e.isPopupTrigger()) {
                    int row = taskTable.getSelectedRow();
                    if (row >= 0) {
                        popupMenu.show(taskTable, e.getX(), e.getY());
                    }
                }
            }
        });

        addTaskButton.addActionListener(e -> openAddTaskDialog());
        searchButton.addActionListener(e -> searchTask());
        deleteMenuItem.addActionListener(e -> deleteTask());
        changeStatusMenuItem.addActionListener(e -> changeStatusTask());
        changePriorityMenuItem.addActionListener(e -> changePriorityTask());
        editMenuItem.addActionListener(e -> editTask());



    }

    private void loadTableData()
    {
        tableModel.setRowCount(0);
        var tasks = TaskService.getTaskByUser(user.getUserId());
        for(Task task : tasks)
        {
            tableModel.addRow(new Object[]{task.getTaskId(),task.getTitle(),task.getDescription(),task.getPriority(),task.getStatus(),task.getOwnerId(),task.getGroupId()});
        }
    }

    private void openAddTaskDialog()
    {
       AddTaskDialog addTaskDialog = new AddTaskDialog(user);
        addTaskDialog.setModal(true);
       addTaskDialog.setVisible(true);
       loadTableData();
    }

    private void deleteTask()
    {
        int row = taskTable.getSelectedRow();
        if(row<0)return;
        String taskId = (String) tableModel.getValueAt(row, 0);
        TaskService.deleteTask(taskId);
        loadTableData();
    }

    private void changeStatusTask()
    {
        int row = taskTable.getSelectedRow();
        String taskId = (String) tableModel.getValueAt(row, 0);
        Task task = TaskService.findTaskById(taskId);

        if (task == null) return;

        if (task.getStatus() == TaskStatus.TODO) {
            TaskService.updateTaskStatus(taskId, TaskStatus.IN_PROGRESS);
        } else if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            TaskService.updateTaskStatus(taskId, TaskStatus.COMPLETED);
        } else {
            TaskService.updateTaskStatus(taskId, TaskStatus.TODO);
        }


        loadTableData();
    }

    private void searchTask()
    {
        String searchText = searchField.getText();
        tableModel.setRowCount(0);
        if(searchText.isEmpty())
        {
            loadTableData();
            return;
        }
        var tasks = TaskService.searchTasks(searchText);
        for(Task task : tasks)
        {
            tableModel.addRow(new Object[]{task.getTaskId(),task.getTitle(),task.getDescription(),task.getPriority(),task.getStatus(),task.getOwnerId(),task.getGroupId()});
        }
    }

    private void changePriorityTask() {
        int row = taskTable.getSelectedRow();
        String taskId = (String) tableModel.getValueAt(row, 0);
        Task task = TaskService.findTaskById(taskId);
        if (task == null) return;
        if (task.getPriority() == TaskPriority.HIGH) {
            TaskService.updateTaskPriority(taskId, TaskPriority.MEDIUM);
        } else {
            TaskService.updateTaskPriority(taskId, TaskPriority.HIGH);
        }

        loadTableData();
    }

    private void editTask()
    {
        int row = taskTable.getSelectedRow();
        String taskId = (String) tableModel.getValueAt(row, 0);
        Task task = TaskService.findTaskById(taskId);
        if (task == null) return;

        JTextField titleField = new JTextField();
        JTextArea descriptionField = new JTextArea();

        Object[]message = {"Title:",titleField,"Description:",descriptionField};
        int result = JOptionPane.showConfirmDialog(this, message, "Edit Task", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION)
        {
            String title = titleField.getText();
            String description = descriptionField.getText();
            boolean success = TaskService.editTask(taskId,title,description);
            if(!success)
            {
                JOptionPane.showMessageDialog(this, "Failed to edit task");
            }
            loadTableData();
        }

    }



}

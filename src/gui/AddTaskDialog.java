package gui;

import model.Group;
import model.User;
import model.enums.TaskPriority;
import service.GroupService;
import service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddTaskDialog extends JDialog {
    private User user;

    private JTextField titleField;
    private JTextArea descriptionField;

    private JComboBox<TaskPriority> priorityComboBox;
    private JButton createButton;

    private JComboBox<String> groupComboBox;
    private List<Group> userGroups;

    public AddTaskDialog(User user)
    {
        this.user = user;
        initializeUI();
    }

    private void initializeUI()
    {
        setTitle("Add Task");
        setSize(400, 350);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridLayout(8, 1));
        contentPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        contentPanel.add(titleField);
        contentPanel.add(new JLabel("Description:"));
        descriptionField = new JTextArea();
        contentPanel.add(descriptionField);
        contentPanel.add(new JLabel("Priority:"));
        priorityComboBox = new JComboBox<>(TaskPriority.values());
        contentPanel.add(priorityComboBox);


        contentPanel.add(new JLabel("Group (optional):"));
        groupComboBox = new JComboBox<>();
        groupComboBox.addItem("None");
        userGroups = GroupService.getGroupsByUser(user.getUserId());
        for (Group g : userGroups) {
            groupComboBox.addItem(g.getGroupName());
        }
        contentPanel.add(groupComboBox);

        add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        createButton = new JButton("Create");
        buttonPanel.add(createButton);
        add(buttonPanel, BorderLayout.SOUTH);

        createButton.addActionListener(e-> createTask());

    }

    private void createTask()
    {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String description = descriptionField.getText();
        TaskPriority priority = (TaskPriority) priorityComboBox.getSelectedItem();
        String groupId = null;
        Integer selectedIndex = groupComboBox.getSelectedIndex();
        if (selectedIndex > 0) {
            groupId = userGroups.get(selectedIndex - 1).getGroupId();
        }
        TaskService.createTask( title, description, priority,user,groupId);
        dispose();
    }



}

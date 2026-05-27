package gui;

import model.User;
import service.GroupService;

import javax.swing.*;
import java.awt.*;

public class AddGroupDialog extends JDialog {
    private User user;
    private JTextField groupNameField;
    private JButton createButton;
    private JButton cancelButton;

    public AddGroupDialog(User user) {

        this.user = user;
        setModal(true);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Create New Group");
        setSize(350, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(new JLabel("Group Name:"));
        groupNameField = new JTextField();
        contentPanel.add(groupNameField);

        add(contentPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);


        createButton.addActionListener(e -> createGroupAction());
        cancelButton.addActionListener(e -> dispose());
    }

    private void createGroupAction() {
        String groupName = groupNameField.getText().trim();

        if (groupName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group Name cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        GroupService.createGroup(user, groupName);

        dispose();
    }
}
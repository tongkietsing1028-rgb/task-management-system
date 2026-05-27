package gui;

import model.Group;
import model.User;
import service.GroupService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class GroupPanel extends JPanel {
    private User user;
    private JTable groupTable;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    private JTextField searchField;

    public GroupPanel(User user)
    {
        this.user = user;

        initializeUI();
        loadTableData();
    }
    private void initializeUI()
    {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Search");
        JButton createButton = new JButton("Create Group");
        searchField = new JTextField(15);
        topPanel.add(createButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);


        String [] col = {"Group ID", "Group Name","Members","Owner ID"};
        tableModel = new DefaultTableModel(col, 0);
        groupTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(groupTable);
        add(scrollPane, BorderLayout.CENTER);

        popupMenu = new JPopupMenu();
        JMenuItem deleteGroupMenuItem = new JMenuItem("Delete Group");
        JMenuItem viewGroupMenuItem = new JMenuItem("View Group");
        JMenuItem manageGroupMenuItem = new JMenuItem("Manage Group");
        popupMenu.add(manageGroupMenuItem);
        popupMenu.add(viewGroupMenuItem);
        popupMenu.add(deleteGroupMenuItem);

        groupTable.addMouseListener(new MouseAdapter() {
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
                    int row = groupTable.getSelectedRow();
                    if (row >= 0) {
                        popupMenu.show(groupTable, e.getX(), e.getY());
                    }
                }
            }
        });

        deleteGroupMenuItem.addActionListener(e -> deleteGroup());
        viewGroupMenuItem.addActionListener(e -> viewGroup());
        manageGroupMenuItem.addActionListener(e -> manageGroup());
        searchButton.addActionListener(e -> searchGroup());
        createButton.addActionListener(e -> createGroup());
    }

    private void loadTableData()
    {
        tableModel.setRowCount(0);
        var groups = GroupService.getGroupsByUser(user.getUserId());
        for(var group : groups)
        {
            tableModel.addRow(new Object[]{group.getGroupId(), group.getGroupName(), group.getMemberIds().size(), group.getOwnerId()});
        }
    }

    private void deleteGroup()
    {
        int row = groupTable.getSelectedRow();
        if (row >= 0) {
            var groupId = (String) tableModel.getValueAt(row, 0);
            GroupService.deleteGroup(groupId);
            loadTableData();
        }
    }

    private void searchGroup()
    {
       String search = searchField.getText();
       if(search.isEmpty())
       {
           loadTableData();
           return;
       }
       var group = GroupService.findGroupById(search);
       if(group == null)
       {
           loadTableData();
           return;
       }
       tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{group.getGroupId(), group.getGroupName(), group.getMemberIds().size(), group.getOwnerId()});
    }

    private void viewGroup() {

        int row = groupTable.getSelectedRow();
        if (row < 0) return;

        String groupId = (String) tableModel.getValueAt(row, 0);

        Group group = GroupService.findGroupById(groupId);
        if (group == null) return;

        List<String> memberIds = group.getMemberIds();
        String members = (memberIds == null || memberIds.isEmpty()) ? "No members" : String.join(",", memberIds);
        JOptionPane.showMessageDialog(
                this,
                "Group ID: " + groupId +
                        "\nGroup Name: " + group.getGroupName() +
                        "\nMembers: " + members +
                        "\nOwner ID: " + group.getOwnerId()
        );
    }

    private void manageGroup()
    {
        int row = groupTable.getSelectedRow();
        if (row < 0) return;

        String groupId = (String) tableModel.getValueAt(row, 0);

        Group group = GroupService.findGroupById(groupId);
        if (group == null) return;
        var manageGroupDialog = new ManageGroupDialog(group);
        loadTableData();

    }

    private void createGroup()
    {
        var addGroupDialog = new AddGroupDialog(user);
        addGroupDialog.setModal(true);
        addGroupDialog.setVisible(true);
        loadTableData();

    }

}

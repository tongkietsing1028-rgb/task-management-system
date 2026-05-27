package gui;

import model.Group;
import service.GroupService;

import javax.swing.*;
import java.awt.*;

public class ManageGroupDialog extends JDialog {
    private final String targetGroupId;
    private DefaultListModel<String> memberListModel;
    private JList<String> memberList;

    public ManageGroupDialog(Group group)
    {
        this.targetGroupId = group.getGroupId();


        setModal(true);
        initializeUI();
        loadMembers();
        setVisible(true);
    }

    private void initializeUI()
    {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Manage Group");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        memberListModel = new DefaultListModel<>();
        memberList = new JList<>(memberListModel);
        JScrollPane scrollPane = new JScrollPane(memberList);
        add(scrollPane, BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Member");
        JButton removeBtn = new JButton("Remove Member");
        JButton closeBtn = new JButton("Close");

        JPanel southPanel = new JPanel();
        southPanel.add(addBtn);
        southPanel.add(removeBtn);
        southPanel.add(closeBtn);
        add(southPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addMember());
        removeBtn.addActionListener(e -> removeMember());
        closeBtn.addActionListener(e -> dispose());
    }

    private void loadMembers()
    {
        memberListModel.clear();

        // 铁面无私地从最新的文本文件里拉数据
        Group latestGroup = GroupService.findGroupById(targetGroupId);

        if (latestGroup == null)
        {
            JOptionPane.showMessageDialog(this, "Group not found");
            return;
        }

        setTitle("Manage Group - " + latestGroup.getGroupName());

        var members = latestGroup.getMemberIds();
        if (members == null) return;

        for (var member : members)
        {
            memberListModel.addElement(member);
        }
    }

    private void removeMember()
    {
        var selectedMember = memberList.getSelectedValue();
        if (selectedMember == null)
        {
            return;
        }
        GroupService.removeMember(targetGroupId, selectedMember);
        loadMembers();
    }

    private void addMember()
    {
        var member = JOptionPane.showInputDialog(this, "Enter member ID to add:");
        if (member == null || member.trim().isEmpty())
        {
            return;
        }
        if (GroupService.isMember(targetGroupId, member))
        {
            JOptionPane.showMessageDialog(this, "Member already exists");
            return;
        }

        boolean success = GroupService.addMember(targetGroupId, member);
        if(success)
        {
            JOptionPane.showMessageDialog(this, "Member added successfully");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Failed to add member. User ID may not exist.");
        }

        loadMembers();
    }
}

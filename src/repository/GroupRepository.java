package repository;

import model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {
    private static final String GROUP_FILE_PATH = "data/group.txt";
    private static Group fromLine(String line)
    {
        String []parts=line.split("\\|");
        Group group = new Group();
        group.setGroupId(parts[0]);
        group.setGroupName(parts[1]);
        group.setOwnerId(parts[2]);
        List<String> memberIds = new ArrayList<>();
        if(!parts[3].isEmpty())
        {
            String[]members = parts[3].split(",");
            for(String member :members)
            {
                memberIds.add(member);
            }
        }
        group.setMemberIds(memberIds);
        return group;
    }
    private static String toLine(Group group)
    {
        String members = String.join(",",group.getMemberIds());
        return group.getGroupId() + "|" + group.getGroupName() + "|" + members;
    }

    public static void save(Group group)
    {
        FileStorage.appendLine(GROUP_FILE_PATH,toLine(group));
    }

    public static List<Group> findAll()
    {
        List<String>lines =FileStorage.readAllLines(GROUP_FILE_PATH);
        List<Group>groups = new ArrayList<>();
        for(String line:lines)
        {
            groups.add(fromLine(line));
        }
        return groups;
    }

    public static Group findByGroupId(String groupId)
    {
        List<Group>groups = findAll();
        for(Group group:groups)
        {
            if(group.getGroupId().equals(groupId))
            {
                return group;
            }
        }
        return null;
    }

    public static void update(Group group)
    {
        List<Group>groups = findAll();
        for(int i =0;i<groups.size();i++)
        {
            if(groups.get(i).getGroupId().equals(group.getGroupId()))
            {
                groups.set(i,group);
                break;
            }
        }
        List<String>lines = new ArrayList<>();
        for(Group g:groups)
        {
            lines.add(toLine(g));
        }
        FileStorage.writeAllLines(GROUP_FILE_PATH,lines);
    }

    public static void delete(String groupId)
    {
        List<Group>groups = findAll();
        for(int i=0;i<groups.size();i++)
        {
            if(groups.get(i).getGroupId().equals(groupId))
            {
                groups.remove(i);
                break;
            }
        }
        List<String> lines = new ArrayList<>();
        for(Group g:groups)
        {
            lines.add(toLine(g));
        }
        FileStorage.writeAllLines(GROUP_FILE_PATH,lines);
    }
}

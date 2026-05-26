package model;
import java.util.List;

public class Group {
    private String groupId;
    private String groupName;
    private List<String> memberIds;
    private String ownerId;

    //constructor
    public Group(String groupId, String groupName, String ownerId,List<String> memberIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.memberIds = memberIds;
        this.ownerId = ownerId;
    }
    //default constructor
    public Group() {
    }
    //getter and setter
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public List<String> getMemberIds() {
        return memberIds;
    }
    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

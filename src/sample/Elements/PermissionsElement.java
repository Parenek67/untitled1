package sample.Elements;

public class PermissionsElement {
    private String permission_name;
    private String entity_name;
    private String subentity_name;

    public PermissionsElement(String permission_name, String entity_name, String subentity_name) {
        this.permission_name = permission_name;
        this.entity_name = entity_name;
        this.subentity_name = subentity_name;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public String getSubentity_name() {
        return subentity_name;
    }
}

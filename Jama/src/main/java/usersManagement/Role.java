package usersManagement;

import java.util.ArrayList;
import java.util.List;

//TODO cambiare da ChiefScientist a Teacher attenzione alle pagine JSF!
public enum Role {
	// XXX i nomi da visualizzare sono hard-coded, perché il FacesContext non è
	// stato ancora definito al momento della creazione delle enum constants e
	// quindi non si possono prendere dal message bundle

	OPERATOR(new Permission[] { Permission.ALTER_CONTRACTS,
			Permission.DELETE_CONTRACTS, Permission.VIEW_CONTRACTS,
			Permission.VIEW_HOME, Permission.VIEW_OWN_CONTRACTS }, "Operatore"),

	CHIEF_SCIENTIST(new Permission[] { Permission.VIEW_OWN_CONTRACTS,
			Permission.VIEW_HOME }, "Responsabile scientifico"),

	ADMIN(new Permission[] { Permission.VIEW_HOME,
			Permission.ALTER_USER_PERMISSIONS, Permission.CREATE_USER,
			Permission.VIEW_USERS }, "Amministratore"),

	GUEST(new Permission[] {}, "Guest");

	private List<Permission> permissions;
	private String displayString;

	private Role(Permission[] permissions, String displayString) {
		this.displayString = displayString;

		this.permissions = new ArrayList<>();
		for (int i = 0; i < permissions.length; i++) {
			this.permissions.add(permissions[i]);
		}
	}

	public String getDisplayString() {
		return displayString;
	}

	public boolean hasPermission(Permission toCheck) {
		return permissions.contains(toCheck);
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public static Role[] getAvailableUserRoleValues() {
		return (new Role[] { Role.CHIEF_SCIENTIST, Role.OPERATOR });
	}
}
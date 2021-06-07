package com.abhinav.model;

import java.util.*;


/**
 * A user Class is a class that follows Singleton Pattern
 */
public class User {

    private String username;
    private String password;
    private static User currentUser;
    private HashSet<ROLE> role;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "Admin@12345";


    //maps to store multi user <Key,Value> pairs
    static HashMap<String, User> usersMap;
    //roles Resouces Map contains relations among Role and Resource Access
    static HashMap<ROLE, List<AccessControl>> roleResourcesMap;


    //get Username of Current User
    public String getUsername() {
        return username;
    }

    // used to Create Instance of Singleton Class and initialize Admin and Populate Resources Map
    public static User getInstance() {
        if (currentUser == null) {
            HashSet<ROLE> set = new HashSet<>();
            set.add(ROLE.ADMIN);
            currentUser = new User(ADMIN_USERNAME, ADMIN_PASSWORD, set);
            populateResources();
            usersMap = new HashMap<>();
            usersMap.put(currentUser.username, new User(currentUser.username, currentUser.password, set));
        }
        return currentUser;
    }

    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private User() {
    }

    private User(String username, String password, HashSet<ROLE> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    /*
     * class to hold Actions Values as per Resources
     *  */
    public static class AccessControl {
        RESOURCE resource;
        ACTION_TYPE[] actions;

        public AccessControl(RESOURCE resource, ACTION_TYPE[] actions) {
            this.resource = resource;
            this.actions = actions;
        }

        @Override
        public boolean equals(Object o) {
            AccessControl accessControl = (AccessControl) o;
            if (this.resource == accessControl.resource) {
                for (ACTION_TYPE action_type_i : actions) {
                    for (ACTION_TYPE action_type_j : accessControl.actions) {
                        if (action_type_i == action_type_j) {
                            return true;
                        }
                    }
                }
            }
            if (o == null || getClass() != o.getClass()) return false;
            return false;
        }
    }

    // method to check whether current logged in user is an ADMIN or not
    public boolean isUserAdmin() {
        return currentUser.role.contains(ROLE.ADMIN);
    }

    /*
     * method to populate resource and action values */
    private static void populateResources() {
        roleResourcesMap = new HashMap<>();
        List<AccessControl> adminResource = new ArrayList<>();
        adminResource.add(new AccessControl(RESOURCE.USER, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.OFFICE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.DEPARTMENT, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.SHIFT, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.LEAVE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.EMPLOYEE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.REPORT, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        adminResource.add(new AccessControl(RESOURCE.SUPERVISOR, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        roleResourcesMap.put(ROLE.ADMIN, adminResource);

        List<AccessControl> officeAdminResource = new ArrayList<>();
        officeAdminResource.add(new AccessControl(RESOURCE.OFFICE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        officeAdminResource.add(new AccessControl(RESOURCE.DEPARTMENT, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        officeAdminResource.add(new AccessControl(RESOURCE.LEAVE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        officeAdminResource.add(new AccessControl(RESOURCE.EMPLOYEE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        officeAdminResource.add(new AccessControl(RESOURCE.REPORT, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        officeAdminResource.add(new AccessControl(RESOURCE.SUPERVISOR, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        roleResourcesMap.put(ROLE.OFFICE_ADMIN, officeAdminResource);

        List<AccessControl> departmentAdminResource = new ArrayList<>();
        departmentAdminResource.add(new AccessControl(RESOURCE.OFFICE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        departmentAdminResource.add(new AccessControl(RESOURCE.DEPARTMENT, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        departmentAdminResource.add(new AccessControl(RESOURCE.LEAVE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        departmentAdminResource.add(new AccessControl(RESOURCE.EMPLOYEE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        departmentAdminResource.add(new AccessControl(RESOURCE.REPORT, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        departmentAdminResource.add(new AccessControl(RESOURCE.SUPERVISOR, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        roleResourcesMap.put(ROLE.DEPARTMENT_ADMIN, departmentAdminResource);

        List<AccessControl> employeeResource = new ArrayList<>();
        departmentAdminResource.add(new AccessControl(RESOURCE.OFFICE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        departmentAdminResource.add(new AccessControl(RESOURCE.DEPARTMENT, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        employeeResource.add(new AccessControl(RESOURCE.EMPLOYEE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        employeeResource.add(new AccessControl(RESOURCE.LEAVE, new ACTION_TYPE[]{ACTION_TYPE.READ, ACTION_TYPE.WRITE, ACTION_TYPE.EDIT, ACTION_TYPE.DELETE}));
        employeeResource.add(new AccessControl(RESOURCE.REPORT, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        roleResourcesMap.put(ROLE.EMPLOYEE, employeeResource);

        List<AccessControl> supervisorResource = new ArrayList<>();
        officeAdminResource.add(new AccessControl(RESOURCE.OFFICE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        supervisorResource.add(new AccessControl(RESOURCE.DEPARTMENT, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        supervisorResource.add(new AccessControl(RESOURCE.LEAVE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        supervisorResource.add(new AccessControl(RESOURCE.EMPLOYEE, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        supervisorResource.add(new AccessControl(RESOURCE.REPORT, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        supervisorResource.add(new AccessControl(RESOURCE.SUPERVISOR, new ACTION_TYPE[]{ACTION_TYPE.READ}));
        roleResourcesMap.put(ROLE.SUPERVISOR, supervisorResource);
    }


    // method to add user into the map
    public Response addUserEntry(String userName, String pass) {
        System.out.println(userName + " - " + pass);
        Response response = new Response();
        if (usersMap.containsKey(userName)) {
            response.setStatus(false);
            response.setMessage("Username Already Registered!!");
            return response;
        }
        User user = new User(userName, pass, new HashSet<>());
        System.out.println(user.toString());
        usersMap.put(userName, user);
        System.out.println(usersMap);
        response.setStatus(true);
        response.setMessage("User Created Successfully!!");
        return response;
    }

    public void printUserRoles(String userName) {
        System.out.println("################################################################");
        System.out.println("Roles Assigned to " + userName + " is as follows :");
        if (usersMap.containsKey(userName)) {
            ROLE[] roles = usersMap.get(userName).role.toArray(new ROLE[0]);
            for (ROLE role : roles) {
                System.out.println(role);
            }
        } else {
            System.out.println("User does not Exists!!");
        }
    }

    //check whether user is editable by current logged in user or not
    public boolean checkEditableUser(String userName) {
        boolean contains = usersMap.containsKey(userName);
        if (!contains) {
            System.out.println("User Does not Exist!!");
        } else if (isUserSame(userName)) {
            System.out.println("Cannot Edit Self !!");
            contains = false;
        }
        return contains;
    }

    public boolean isUserSame(String userName) {
        return currentUser.username.equals(userName);
    }

    //method used to login and update current user if success
    public Response loginUser(String userName, String pass) {
        Response response = new Response();
        if (usersMap.containsKey(userName) && usersMap.get(userName).password.equals(pass)) {
            currentUser = usersMap.get(userName);
            this.username = usersMap.get(userName).username;
            this.password = usersMap.get(userName).password;
            this.role = usersMap.get(userName).role;
            response.setStatus(true);
            response.setMessage("Hi " + userName + usersMap.get(userName).role.toString() + " welcome to the Portal");
        } else {
            response.setStatus(false);
            response.setMessage("Please enter valid username and password!!");
        }
        return response;
    }

    public Response emptyRoles(String userName) {
        Response response = new Response();
        if (!usersMap.containsKey(userName)) {
            response.setStatus(false);
            response.setMessage("User not Found!!");
        }
        User user = usersMap.get(userName);
        if (user.role.size() == 0) {
            response.setStatus(false);
            response.setMessage("No Role Assigned to User!!");
        } else {
            response.setStatus(true);
            response.setMessage(user.role.size() + " Roles Assigned to User!!");
        }
        return response;
    }

    //method used to add Role for user
    public Response addRole(String userName, ROLE role) {
        Response response = new Response();
        if (!usersMap.containsKey(userName)) {
            response.setStatus(false);
            response.setMessage("User not Found!!");
            return response;
        }
        User user = usersMap.get(userName);
        if (user.role.contains(role)) {
            response.setStatus(false);
            response.setMessage("Role Already Assigned to User!!");
            return response;
        }
        user.role.add(role);
        response.setStatus(true);
        response.setMessage("Role Assigned to User!!");
        return response;
    }

    //method used to remove Role from user
    public Response removeRole(String userName, ROLE role) {
        Response response = new Response();
        if (!usersMap.containsKey(userName)) {
            response.setStatus(false);
            response.setMessage("User not Found!!");
            return response;
        }
        User user = usersMap.get(userName);
        if (!user.role.contains(role)) {
            response.setStatus(false);
            response.setMessage("Role Not Assigned to User!!");
            return response;
        }
        user.role.remove(role);
        response.setStatus(true);
        response.setMessage("Role De-assigned from User!!");
        return response;
    }

    // return a list of resources for a particular user based on the ROLES assigned to that user
    public static RESOURCE[] getUserResources(String userName) {
        HashSet<RESOURCE> resources = new HashSet<>();
        User user = usersMap.get(userName);
        ROLE[] roles = user.role.toArray(new ROLE[0]);

        for (ROLE role : roles) {
            List<AccessControl> temp = roleResourcesMap.get(role);
            for (int i = 0; i < temp.size(); i++) {
                resources.add(temp.get(i).resource);
            }
        }
        return resources.toArray(new RESOURCE[0]);
    }

    //Method to check whether user have authorization to perform a particular operation based on the Roles assigned to that user
    public static void performOperation(String userName, RESOURCE resource, ACTION_TYPE action_type) {
        if (!usersMap.containsKey(userName)) {
            System.out.println("User Not Found!!");
            return;
        }
        User user = usersMap.get(userName);
        ROLE[] roles = user.role.toArray(new ROLE[0]);
        for (ROLE role : roles) {
            List<AccessControl> accessControls = roleResourcesMap.get(role);
            for (AccessControl control : accessControls) {
                if (control.equals(new AccessControl(resource, new ACTION_TYPE[]{action_type}))) {
                    System.out.println(userName + " Performed " + action_type + " Operation on " + resource);
                    return;
                }
            }
        }
        System.out.println(userName + " Not Allowed to Perform " + action_type + " Operation on " + resource);
    }


    public static enum ACTION_TYPE {
        READ,
        WRITE,
        EDIT,
        DELETE
    }

    public static enum ROLE {
        ADMIN,
        OFFICE_ADMIN,
        DEPARTMENT_ADMIN,
        SUPERVISOR,
        EMPLOYEE
    }

    public static enum RESOURCE {
        USER,
        OFFICE,
        DEPARTMENT,
        EMPLOYEE,
        SUPERVISOR,
        SHIFT,
        REPORT,
        LEAVE,
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}

package com.abhinav;


import com.abhinav.model.Response;
import com.abhinav.model.User;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static Response processUserLoginProcess(Scanner scn, User user) {
        System.out.println("Enter Credentials for Login");
        System.out.print("username : ");
        String username = scn.next().trim();
        System.out.print("password : ");
        String password = scn.next().trim();
        Response response = user.loginUser(username, password);
        if (!response.isStatus()) {
            System.out.println(response.getMessage());
            return response;
        }
        System.out.println(response.getMessage());
        return response;
    }

    public static void createUserProcess(Scanner scn, User user) {
        if (user.isUserAdmin()) {
            boolean exitFlag = false;
            Response response = null;
            String username = null;
            do {
                System.out.println();
                System.out.println("################################################################");
                boolean isValidUserName = false;
                do {
                    System.out.println("Enter Username for user to be Created");
                    System.out.print("username : ");
                    username = scn.next().trim();
                    if (Utils.isValidUsername(username)) {
                        isValidUserName = true;
                    } else {
                        System.out.println("##############################################");
                        System.out.println("VALIDATION ERROR");
                        System.out.println("Username should follow following validations : " );
                        System.out.println("Should start with an alphabet");
                        System.out.println("0-9 digits are allowed");
                        System.out.println("lower case letters are allowed ");
                        System.out.println("upper case letters are allowed");
                        System.out.println("allowed special character are '._-'");
                        System.out.println("No white Space is allowed");
                        System.out.println("Minimum character length of 5");
                        System.out.println("Maximum character length of 10");
                    }
                } while (!isValidUserName);
                boolean isValidPassword = false;
                String password = null;
                do {
                    System.out.println("Enter Password for user to be Created");
                    System.out.print("password : ");
                    password = scn.next().trim();
                    if (Utils.isValidPassword(password)) {
                        isValidPassword = true;
                    } else {
                        System.out.println("##############################################");
                        System.out.println("VALIDATION ERROR");
                        System.out.println("Password should follow following validations" );
                        System.out.println("a digit must occur at least once");
                        System.out.println("a lower case letter must occur at least once");
                        System.out.println("a upper case must occur at least once");
                        System.out.println("a special character like '@#$%^&+=' must occur at least once");
                        System.out.println("No white Space is allowed");
                        System.out.println("Minimum character length of 5");
                        System.out.println("Maximum character length of 30");
                    }
                } while (!isValidPassword);

                response = user.addUserEntry(username, password);
                if (response.isStatus()) {
                    exitFlag = true;
                } else {
                    System.out.println(response.getMessage());
                    System.out.println("To restart User Creation Press y|Y or press any other key to go back");
                    char ch = scn.next().charAt(0);
                    if (ch == 'Y' || ch == 'y') {
                        exitFlag = false;
                    } else {
                        return;
                    }
                }
            } while (!exitFlag);
            editRoleHelper(scn, user, username, "Add Roles to the User");
            System.out.println("User Creation Process Completed");
        } else {
            System.out.println("Only ADMIN user have access to create User");
        }
    }


    public static void editRoleHelper(Scanner scn, User user, String username, String title) {
        boolean flag = true;
        do {
            System.out.println();
            System.out.println("################################################################");
            System.out.println(title);
            System.out.println("Press 1 to Assign OFFICE ADMIN role");
            System.out.println("Press 2 to Assign DEPARTMENT ADMIN role");
            System.out.println("Press 3 to Assign EMPLOYEE role");
            System.out.println("Press 4 to Assign SUPERVISOR role");
            System.out.println("Press 5 to De-Assign OFFICE ADMIN role");
            System.out.println("Press 6 to De-Assign DEPARTMENT ADMIN role");
            System.out.println("Press 7 to De-Assign EMPLOYEE role");
            System.out.println("Press 8 to De-Assign SUPERVISOR role");
            System.out.println("Press 'c' if you don't want to assign more roles");
            System.out.print("Your Input : ");
            char ch = scn.next().charAt(0);
            Response response = null;
            switch (ch) {
                case '1':
                    response = user.addRole(username, User.ROLE.OFFICE_ADMIN);
                    System.out.println(response.getMessage());
                    break;
                case '2':
                    response = user.addRole(username, User.ROLE.DEPARTMENT_ADMIN);
                    System.out.println(response.getMessage());
                    break;
                case '3':
                    response = user.addRole(username, User.ROLE.EMPLOYEE);
                    System.out.println(response.getMessage());
                    break;
                case '4':
                    response = user.addRole(username, User.ROLE.SUPERVISOR);
                    System.out.println(response.getMessage());
                    break;
                case '5':
                    response = user.removeRole(username, User.ROLE.OFFICE_ADMIN);
                    System.out.println(response.getMessage());
                    break;
                case '6':
                    response = user.removeRole(username, User.ROLE.DEPARTMENT_ADMIN);
                    System.out.println(response.getMessage());
                    break;
                case '7':
                    response = user.removeRole(username, User.ROLE.EMPLOYEE);
                    System.out.println(response.getMessage());
                    break;
                case '8':
                    response = user.removeRole(username, User.ROLE.SUPERVISOR);
                    System.out.println(response.getMessage());
                    break;
                case 'c':
                    response = user.emptyRoles(username);
                    if (response.isStatus()) {
                        flag = false;
                    } else {
                        System.out.println(response.getMessage());
                        System.out.println("Please Assign a Role to user User cannot Empty Roles!!");
                    }
                    break;
                default:
                    System.out.println("Please Enter Valid Input Values!!");
            }
        } while (flag);
    }

    public static void editUserProcess(Scanner scn, User user) {
        if (user.isUserAdmin()) {
            System.out.println();
            boolean flag = true;
            do {
                System.out.println("################################################################");
                System.out.println("Enter Username for user to be Edited");
                System.out.print("Username : ");
                String username = scn.next().trim();
                boolean retVal = user.checkEditableUser(username);
                if (retVal) {
                    boolean innerFlag = true;
                    do {
                        System.out.println("################################################################");
                        System.out.println("press 1 to View Role");
                        System.out.println("press 2 to Edit Role");
                        System.out.println("press 0 to Exit Role Changing Procedure");
                        System.out.print("Your Input : ");
                        char val = scn.next().charAt(0);
                        if (val == '1') {
                            user.printUserRoles(username);
                        } else if (val == '2') {
                            editRoleHelper(scn, user, username, "Edit Roles to the User");
                        } else if (val == '0') {
                            innerFlag = false;
                        } else {
                            System.out.println("Please provide valid input");
                        }
                    } while (innerFlag);
                }
                System.out.println("Do you want to edit more users press 'y|Y' to continue or 'n|N' to exit");
                System.out.print("Your Input : ");
                char ch = scn.next().charAt(0);
                if (ch == 'n' || ch == 'N') {
                    flag = false;
                }
            } while (flag);
        } else {
            System.out.println("Only ADMIN user have access to do User Operations");
        }
    }

    public static void followUpAdminProcess(Scanner scn, User user) {
        boolean flag = true;
        do {

            System.out.println();
            System.out.println("################################################################");
            System.out.println("Press 1 for login as another user");
            System.out.println("Press 2 for create user");
            System.out.println("Press 3 for edit Role");
            System.out.println("Press 4 to Exit");
            System.out.print("Your Input : ");
            char input = scn.next().charAt(0);
            switch (input) {
                case '1':
                    String prevUsr = user.getUsername();
                    Response response = processUserLoginProcess(scn, user);
                    String newUser = user.getUsername();
                    if (response.isStatus() && !prevUsr.equals(newUser)) {
                        return;
                    }
                    break;
                case '2':
                    createUserProcess(scn, user);
                    break;
                case '3':
                    editUserProcess(scn, user);
                    break;
                case '4':
                    flag = false;
                    break;
                default:
                    System.out.println("Please Enter valid Input!!");
            }
        } while (flag);
    }

    public static void viewYourRoles(Scanner scn, User user) {
        user.printUserRoles(user.getUsername());
    }

    public static void AccessResourceProvidedProcess(Scanner scn, User user, User.RESOURCE resource) {
        boolean flag = true;
        do {
            System.out.println();
            System.out.println("################################################################");
            System.out.println("Press 1 to READ");
            System.out.println("Press 2 to WRITE");
            System.out.println("Press 3 to EDIT");
            System.out.println("Press 4 to DELETE");
            System.out.println("Press c to Exit the process");
            System.out.print("Your Input : ");
            char input = scn.next().charAt(0);
            switch (input) {
                case '1':
                    user.performOperation(user.getUsername(), resource, User.ACTION_TYPE.READ);
                    break;
                case '2':
                    user.performOperation(user.getUsername(), resource, User.ACTION_TYPE.WRITE);
                    break;
                case '3':
                    user.performOperation(user.getUsername(), resource, User.ACTION_TYPE.EDIT);
                    break;
                case '4':
                    user.performOperation(user.getUsername(), resource, User.ACTION_TYPE.DELETE);
                    break;
                case 'c':
                    flag = false;
                    break;
                default:
                    System.out.println("Please Enter valid Input!!");
            }
        } while (flag);
    }


    public static void startAccessResourceProcess(Scanner scn, User user) {
        boolean flag = true;
        do {
            System.out.println();
            System.out.println("################################################################");
            System.out.println("Please Select Resources to perform Action");
            User.RESOURCE[] resources = user.getUserResources(user.getUsername());
            for (int i = 0; i < resources.length; i++) {
                System.out.println("Press " + (i + 1) + " for " + resources[i]);
            }
            System.out.println("Press 0 for to proceed to Previous Menu");
            System.out.print("Your Input : ");

            char input = scn.next().charAt(0);
            int index = ( (int)((int)input - '0') - 1);
            if (input == '0') {
                flag = false;
            } else if (index < resources.length) {
                AccessResourceProvidedProcess(scn, user, resources[index]);
            } else {
                System.out.println("Please Enter valid Input!!");
            }
        } while (flag);
    }

    public static void followUpNonAdminProcess(Scanner scn, User user) {
        boolean flag = true;
        do {
            System.out.println();
            System.out.println("################################################################");
            System.out.println("Press 1 for login as another user");
            System.out.println("Press 2 for View Roles");
            System.out.println("Press 3 for Access Resources");
            System.out.println("Press 4 to Exit");
            System.out.print("Your Input : ");
            char input = scn.next().charAt(0);
            switch (input) {
                case '1':
                    String prevUsr = user.getUsername();
                    Response response = processUserLoginProcess(scn, user);
                    String newUser = user.getUsername();
                    if (response.isStatus() && !prevUsr.equals(newUser)) {
                        return;
                    }
                    break;
                case '2':
                    viewYourRoles(scn, user);
                    break;
                case '3':
                    startAccessResourceProcess(scn, user);
                    break;
                case '4':
                    flag = false;
                    break;
                default:
                    System.out.println("Please Enter valid Input!!");
            }
        } while (flag);
    }

    public static void adminProcessLooper(Scanner scn, User user) {
        if (user.isUserAdmin()) {
            String prevUser = user.getUsername();
            followUpAdminProcess(scn, user);
            String newUser = user.getUsername();
            if (!prevUser.equals(newUser)) {
                adminProcessLooper(scn, user);
            }
        } else {
            String prevUser = user.getUsername();
            followUpNonAdminProcess(scn, user);
            String newUser = user.getUsername();
            if (!prevUser.equals(newUser)) {
                adminProcessLooper(scn, user);
            }
        }
    }


    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        User user = User.getInstance();
        boolean isContinue = false;
        do {
            System.out.println();
            System.out.println("################################################################");
            isContinue = false;
            Response response = processUserLoginProcess(scn, user);
            if (!response.isStatus()) {
                isContinue = true;
                continue;
            }
            adminProcessLooper(scn, user);
            System.out.println("Do you want to Continue Press 'Y|y' for yes | press any other key for no");
            System.out.print("Your Input : ");
            char val = scn.next().charAt(0);
            if ((val == 'y' || val == 'Y')) {
                isContinue = true;
            } else {
                isContinue = false;
            }
        } while (isContinue);
    }
}

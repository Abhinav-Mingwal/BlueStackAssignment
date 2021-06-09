# BlueStackAssignment
Role Based Access Control
It is a Role based Authentication System that allows user to allot Roles to users that determines what type of operation is allowed to perform over certain Resources.

# Entities 
* USER username(case-sensitive),password(case-sensitive),ROLES
* ACTION_TYPE : READ,WRITE,EDIT,DELETE
* RESOURCE : OFFICE, DEPARTMENT, SHIFT, EMPLOYEE, SUPERVISOR, REPORT, LEAVES
* ROLE : OFFICE_ADMIN, DEPARTMENT_ADMIN, EMPLOYEE, SUPERVISOR

# initialization Process
Initialization Process Creates a Admin User with credentials
username: admin
password : Admin@12345
along with that Role Based Access Properties are initialized as well which will be used to check weather a user with a set of Roles assigned to him/her will be able to access a particular Resource.

#Hierarchy Chart
* ADMIN :
    * READ -> user,office,department,supervisor,employee,shift,report,leave
    * WRITE -> user,office,department,supervisor,employee,shift,report,leave
    * EDIT -> user,office,department,supervisor,employee,shift,report,leave
    * DELETE -> user,office,department,supervisor,employee,shift,report,leave
* OFFICE_ADMIN :
    * READ -> office,department,supervisor,employee,shift,report,leave
    * WRITE -> department,supervisor,employee,shift,report,leave
    * EDIT -> department,supervisor,employee,shift,report,leave
    * DELETE -> department,supervisor,employee,shift,report,leave
* DEPARTMENT_ADMIN :
    * READ -> office,department,supervisor,employee,shift,report,leave
    * WRITE -> supervisor,employee,shift,report,leave
    * EDIT -> supervisor,employee,shift,report,leave
    * DELETE -> supervisor,employee,shift,report,leave
* EMPLOYEE :
    * READ -> office,department,supervisor,employee,shift,report,leave
    * WRITE -> leave
    * EDIT -> leave
    * DELETE -> leave
* SUPERVISOR :
    * READ -> office,department,supervisor,employee,shift,report,leave
    * WRITE -> 
    * EDIT -> 
    * DELETE -> 

User Class uses Singleton Pattern in User Class along with execption which gets thrown if someone tries to run Clone method over it.

A state Machine is Created Which Starts with ADMIN login page
on Proceeding Admin Login Page will open up showing various method an Admin can perform.

Some classes contains Overriden toEquals method in order to prevent writing huge lines of codes.

Application doesnt contain a view so i've kept my Model and Controller within the same user.class
Validations are added over username and password
username and password are both case sensitive

If multiple Role Assigned to a user than the ROLE having access to that particular resource will be Considered as ACCESS_PROVIDER.

there is only be one Admin in the system who'll be responsible for Creating other users and allotting roles to them.



# Code Running Script
make sure java jdk is installed in your system

cd BlueStackAssignment-master/src

javac com/abhinav/Main.java

java com/abhinav/Main  

code will run



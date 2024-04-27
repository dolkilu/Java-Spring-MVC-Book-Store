package hkmu.comps380f.s1326557_project.exception;

public class CustomerNotFound extends Exception{
    public CustomerNotFound(String username) {super ("Customer " + username + "does not exist.");}
}

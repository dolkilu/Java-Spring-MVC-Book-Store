package hkmu.comps380f.s1326557_project.exception;

public class BookNotFound extends Exception{
    public BookNotFound(long id) {super ("Book " + id + "does not exist.");}
}

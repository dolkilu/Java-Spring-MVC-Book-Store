package hkmu.comps380f.s1326557_project.exception;

public class CommentNotFound extends Exception{
    public CommentNotFound(long id) {super ("Comment " + id + " does not exists.");}
}

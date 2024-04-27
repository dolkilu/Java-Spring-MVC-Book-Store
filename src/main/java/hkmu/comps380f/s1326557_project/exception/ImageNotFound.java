package hkmu.comps380f.s1326557_project.exception;

public class ImageNotFound extends Exception{
    public ImageNotFound(long id) {super ("Image" + id + "does not exist.");}
}

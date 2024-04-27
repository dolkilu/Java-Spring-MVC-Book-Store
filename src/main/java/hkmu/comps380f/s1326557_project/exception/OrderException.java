package hkmu.comps380f.s1326557_project.exception;

public class OrderException extends Exception{
    public OrderException(String name) {super ("Not enough stock for book: "+ name);}
}

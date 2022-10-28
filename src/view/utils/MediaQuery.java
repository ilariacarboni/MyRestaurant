package view.utils;

public class MediaQuery {

    private double start;
    private double end;
    private Object behaviour;

    public MediaQuery(double start, double end, Object behaviour){
        this.start = start;
        this.end = end;
        this.behaviour = behaviour;
    }

    public boolean contains(double value){
        return (value >= start && value < end);
    }

    public Object getBehaviour(){
        return this.behaviour;
    }

}

package proxy;

public class Student implements Study,Eat {

    @Override
    public String study() {
        System.out.println("study");
        return "study return";
    }

    public void studyEnglish(){
        System.out.println("study english");
    }

    @Override
    public void eatRice() {
        System.out.println("eat rice!");
    }
}

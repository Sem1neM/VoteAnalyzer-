import java.text.SimpleDateFormat;
import java.util.Date;

public class Voter {

    private String name;
    private static int visitCount;
    private Date birthDay;

    public Voter(String name, Date birthDay) {
        visitCount = 1;
        this.name = name;
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object obj) {
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode() {
        long code = name.hashCode() + birthDay.hashCode();
        while (code > Integer.MAX_VALUE) {
            code = code / 10;
        }
        return (int) code;
    }

    public String toString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        return name + " (" + dayFormat.format(birthDay) + ")";
    }

    public int getVisitCount(){
        return visitCount;
    }

    public void addVisit() {
    visitCount++;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDay() {
        return birthDay;
    }
}

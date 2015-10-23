import java.util.ArrayList;
import java.util.List;

public class Panel implements Comparable<Panel>{
    String name;
    List<int[]> times; //arraylist of 1x2 int arrays
    boolean locked = false;
    int minimum_size;
    List<String[]> venue_limits;
    int difficulty;
    List<String> panelists;
    String category;

    public Panel(String name, List<int[]> times, int minimum_size, List<String[]> venue_limits, List<String> panelists, String category){
        this.name = name;
        this.times = times;
        this.minimum_size = minimum_size;
        this.venue_limits = venue_limits;
        this.panelists = panelists;
        this.category = category;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setlock() {
        this.locked = true;
    }

    @Override
    public int compareTo(Panel that) {
        return this.difficulty - that.difficulty;
    }
}

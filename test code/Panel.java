import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Panel implements Comparable<Panel>{
    String name;
    List<int[]> times;
    boolean locked = false;
    int minimum_size = 0;
    String venue_limit;
    int difficulty;
    Map<String, List<int[]>> panelists;
    String category;

    public Panel(String name, Map<String, List<int[]>> panelists, int final_hour){
        this.name = name;
        this.panelists = panelists;
        List<int[]> range = new ArrayList<int[]>();
        range.add(new int[]{0, final_hour});
        for (List<int[]> value : panelists.values()){
            for (int[] time : value){
                int size = range.size();
                for (int i = 0; i < size; i++){
                    int[] slot = range.get(i);
                    int start = time[0];
                    int end = time[1];
                    int slot_start = slot[0];
                    int slot_end = slot[1];
                    if (start > slot_start && start < slot_end){
                        range.get(i)[0] = start;
                    }
                    if (end > slot_start && end < slot_end){
                        range.get(i)[1] = end;
                        range.add(i, new int[]{end, slot_end});
                        size+=1;
                    }
                }
            }
        }
        times = range;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        //concurrency - ( (end - start) + (end - start) ....)
    }

    public void setSize(int minimum_size){
        this.minimum_size = minimum_size;
    }

    public void setVenue(String venue){
        this.venue_limit = venue;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setlock() {
        this.locked = true;
    }

    @Override
    public int compareTo(Panel that) {
        return this.difficulty - that.difficulty;
    }
}

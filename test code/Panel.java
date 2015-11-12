import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Panel implements Comparable<Panel>{
    String name;
    boolean Lock = false;
    List<int[]> availability;
    List<String> panelists = new ArrayList<String>();
    List<Constraint> constraints = new ArrayList<Constraint>();
    VenueTime assignedVenueTime;
    int difficulty;
    String[] category;

    public Panel(String name, Map<String, List<int[]>> people, String[] category, int final_hour, List<Constraint> constraints){
        this.name = name;
        this.category = category;
        this.constraints = constraints;
        for (String panelist : people.keySet()){
            if (panelist.contains("n_")){
                panelist.replace("n_","");
            }
            panelists.add(panelist);
        }


        List<int[]> range = new ArrayList<int[]>();
        range.add(new int[]{0, final_hour});
        for (List<int[]> value : people.values()){
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
        this.availability = range;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        //concurrency - ( (end - start) + (end - start) ....)
    }

    public int getDifficulty(){
        return 0;
    }

    public void setDifficulty(){}

    public VenueTime getVenueTime(){
        return assignedVenueTime;
    }

    public void setVenueTime(VenueTime venueTime){}

    @Override
    public int compareTo(Panel that) {
        return this.difficulty - that.difficulty;
    }
}

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

//In this file, the constraints we have implemented are:
//Concurrency (how difficult a panel is to schedule based on other panels that can be scheduled at those times)
//Time constraints
//Minimum venue size needed

//We have not yet implemented:
//No duplicate categories in a time column
//Number of rooms allowed (anti-constraint)
//Number of time-slots allowed (anti-constraint)

public class Driver {

    public static void main(String[] args) {
        String name;
        List<int[]> times;
        int minimum_size;
        int size;
        List venue_limit;
        List<Panel> panels = new ArrayList<Panel>();
        List<Venue> venues = new ArrayList<Venue>();
        List<VenueTime> venue_times = new ArrayList<VenueTime>;
        Map<String, Integer> category_count;
        List<String> panelists = new ArrayList<String>();

        [int final_hour, List<String> venue_times] = encapsulate(); //yeah this doesn't work but I'll figure out a better way to do it

        int[][] setup = new int[panels.size()][final_hour];
        VenueTime[] schedule = new VenueTime[venue_times.size()];

        //Create the setup matrix and populate it with the panels
        int row = 0;
        for (Panel panel : panels) {
            times = panel.times;
            for (int[] time_slot : times) {
                int start = time_slot[0];
                int end = time_slot[1];
                for (int i = start; i < end; i++) { //remember that these arne't incremented by 1 but by whatever brings it to the next time slot
                    if (panel.locked) { //I use 1 and 2 here for the values, showing a locked event is twice as difficult as a free one, but we can play with these numbers
                        setup[row][i] = 2;
                    } else {
                        setup[row][i] = 1;
                    }
                }
            }
        }

        //Set each nonzero value in each column to the sum of that column
        int panel_count = panels.size();
        int sum;
        for (int j = 0; j < final_hour; j++) {
            sum = 0;
            for (int i = 0; i < panel_count; i++) {
                sum += setup[i][j];
            }
            for (int i = 0; i < panel_count; i++) {
                if (setup[i][j] != 0) {
                    setup[i][j] = sum;
                }
            }
        }

        //Add the values of each row and do some math to determine each panel's difficulty
        int concurrency = 0;
        for (int i = 0; i < panel_count; i++) {
            for (int j = 0; j < final_hour; j++) {
                concurrency += setup[i][j];
            }
            Panel panel = panels.get(i);
            int difficulty = concurrency; //and some other math
            panel.setDifficulty(difficulty);
        }

        //Sort panels by difficulty
        //panels.sort(descending difficulty);
        Collections.sort(panels);

        //For loop to run the sceduler function
        //int conflict;
        List<Integer> conflicts = new ArrayList<Integer>();
        for (Panel panel : panels) {
            schedule = schedulePanel(schedule, panel, 0);
            //if (conflict > 0){
            //   conflicts.add(conflict);
            //}
        }

        //Now we output everything and rejoice
        output(schedule, conflicts);
    }

    //input and modeling function
    static void encapsulate(String file){
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("test.txt"));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray venues = (JSONArray) jsonObject.get("venues");
            JSONArray venue_times = (JSONArray) jsonObject.get("venues_times");
            JSONArray panelists = (JSONArray) jsonObject.get("panelists");
            JSONArray panels = (JSONArray) jsonObject.get("panels");

            String name;
            String time;
            Boolean noob;
            int size;
            Map<String, Integer> sizes = new HashMap<String, Integer>();;
            for (JSONObject item : venues){
                String name = (String) item.get("name");
                size = (int) item.get("size");
                sizes.put(name, size);
            }
            for (JSONObject item : venue_times){
                name = (String) item.get("name");
                time = (String) item.get("time");
                size = sizes.get(name);
                VenueTime vt = new VenueTime(name, time, size);
            }
            for (JSONObject item : panelists){
                name = (String) item.get("name");
                noob = (Boolean) item.get("new");
                JSONArray times = (JSONArray) item.get("times");
                //loop over times
            }
            for (JSONObject item : panels){
                name = (String) item.get("name");
                JSONArray people = (JSONArray) item.get("panelists");
                JSONArray constraints = (JSONArray) item.get("constraints");
                String category = (String) item.get("category");
                //get constraints
                //panels.add(new Panel(name, times, minimum_size, venue_limit, panelists, category));
                //For each panel, use the overlap of the times the panelists are free to set the panel times
                //Whenever a time slot is added to the times of any panel, check the end time. If it is greater than final_hour, replace final_hour with that number
                //if a panel has a certain minimum size or any venue limits, set its locked value to true
            }
        }
        catch (Exception e){
            System.err.format("Exception occurred trying to read '%s'.", file);
            e.printStackTrace();
        }
    }

    //scheduling function
    static VenueTime[] schedulePanel(VenueTime[] schedule, Panel panel, int moves){
        boolean placed = false;
        for (VenueTime venue : schedule){
            if (venue.size >= panel.minimum_size && venue.panel == null && the panel has a time that fits into the venue_time){
                venue.setPanel(panel);
                placed = true;
            }
        }
        if (!placed && moves <= 10){
            for (VenueTime venue : schedule){
                if (venue.size >= panel.minimum_size && the panel has a time that fits into the venue_time){
                    Panel conflict = venue.panel;
                    venue.setPanel(panel);
                    schedulePanel(schedule, conflict, moves + 1);
                    placed = true;
                }
            }
        }
        //if (!placed){
            //add to conflicts list
        //}
        return schedule;
    }

    //output function
    static void output(VenueTime[] schedule, List<Integer> conflicts){
        //do stuff
    }
}


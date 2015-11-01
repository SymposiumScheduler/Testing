import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {
    public static void main(String[] args) {
        List<Panel> panels = new ArrayList<Panel>();
        List<VenueTime> schedule = new ArrayList<VenueTime>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:/Users/jrener/Dropbox/Senior Projects/sample code/src/data.txt"));
            //Object obj = parser.parse(new FileReader("C:/Users/Joey/Dropbox/Senior Projects/test code/src/data.txt"));
            //make sure you change the path
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray json_venues = (JSONArray) jsonObject.get("Venues");
            JSONArray json_venue_times = (JSONArray) jsonObject.get("Venue-Times");
            JSONArray json_panelists = (JSONArray) jsonObject.get("Panelists");
            JSONArray json_panels = (JSONArray) jsonObject.get("Panels");

            //Parse venues and determine their size
            System.out.println("Getting venue sizes...");
            int size;
            String name;
            Map<String, Integer> sizes = new HashMap<String, Integer>();
            for (Object o : json_venues) {
                JSONObject item = (JSONObject) o;
                name = (String) item.get("name");
                size = (int)(long) item.get("size");
                sizes.put(name, size);
            }

            //Parse venue_times and create objects using their names, size, and times
            System.out.println("Setting up Venues...");
            int[] range;
            Map<String, Integer> days = new HashMap<String, Integer>();
            days.put("Monday", 0);
            days.put("Tuesday", 2400);
            days.put("Wednesday", 4800);
            days.put("Thursday", 7200);
            days.put("Friday", 9600);
            days.put("Saturday", 12000);
            days.put("Sunday", 14400);
            String bucket;
            for (Object o : json_venue_times) {
                JSONObject item = (JSONObject) o;
                name = (String) item.get("name");
                bucket = (String) item.get("time");
                range = convertTime(bucket, days);
                size = sizes.get(name);
                schedule.add(new VenueTime(name, range, size));
            }

            //Parse panelists and extract useful information that'll be used during panel creation
            System.out.println("Setting up panelists...");
            Boolean noob;
            int end_time;
            int final_hour = 0;
            Map<String, List<int[]>> panelists = new HashMap<String, List<int[]>>();
            for (Object o : json_panelists) {
                JSONObject item = (JSONObject) o;
                name = (String) item.get("name");
                noob = (item.get("new") == "yes");
                JSONArray json_times = (JSONArray) item.get("times");
                List<int[]> times = new ArrayList<int[]>();
                for (Object time_slot : json_times) {
                    bucket = (String) time_slot;
                    range = convertTime(bucket, days);
                    times.add(range);
                    end_time = range[1];
                    //System.out.println(range[0] + ", " + range[1]);
                    if (end_time > final_hour){
                        final_hour = end_time;
                    }
                }
                panelists.put(name, times);
            }
            //System.out.println(final_hour);


            //Parse panels and create their objects
            System.out.println("Creating Panels...");
            Map<String, Integer> category_count;
            for (Object o : json_panels) {
                JSONObject item = (JSONObject) o;
                name = (String) item.get("name");
                JSONArray panel_panelists = (JSONArray) item.get("panelists");
                JSONArray json_constraints = (JSONArray) item.get("constraints");
                String category = (String) item.get("category");
                List<String> constraints = new ArrayList<String>();
                for (Object k : json_constraints) {
                    String constraint = (String) k;
                    constraints.add(constraint);
                }
                Map<String, List<int[]>> people_involved = new HashMap<String, List<int[]>>();
                String person;
                List<int[]> times;
                for (Object l : panel_panelists) {
                    person = (String) l;
                    times = panelists.get(person);
                    people_involved.put(person, times);
                }
                panels.add(new Panel(name, people_involved, final_hour)); //note that panel creation does the necessary math to determine available times
                //TODO set panel constraints
                //TODO if a panel has a certain minimum size or any venue limits, set its locked value to true
            }
            output(schedule, panels);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static int[] convertTime(String bucket, Map<String, Integer> days) {
        String[] splice = bucket.split(", ");
        String day = splice[0];
        String[] hours = splice[1].split("-");
        int hour1 = Integer.parseInt(hours[0].replace(":", ""));
        int hour2 = Integer.parseInt(hours[1].replace(":", ""));
        return new int[]{days.get(day) + hour1, days.get(day) + hour2};
    }

    //output function
    static void output(List<VenueTime> schedule, List<Panel> panels){
        for (VenueTime venue : schedule){
            System.out.println(venue.name + " " + venue.start);
        }
        for (Panel panel : panels){
            System.out.println(panel.name);
            for (int[] range : panel.times){
                System.out.println("\t" + Integer.toString(range[0]) + " " + Integer.toString(range[1]));
            }
        }
        System.out.print("Done");
    }
}


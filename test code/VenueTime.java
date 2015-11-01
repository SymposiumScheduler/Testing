public class VenueTime{
    String name;
    int start;
    int end;
    int size;
    Panel panel;

    public VenueTime(String name, int[] times, int size){
        this.name = name;
        this.start = times[0];
        this.end = times[1];
        this.size = size;
    }

    public void setPanel(Panel panel){
        this.panel = panel;
    }

    //@Override
    //public int compareTo(Venue that) {
    //    return 1; //this needs to be implemented
        //ascending order by 1. size, 2. UMC before non-UMC, 3. northern ones before non-northern
    //}


}

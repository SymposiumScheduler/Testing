public class VenueTime{
    String name;
    String times;
    int size;
    Panel panel;

    public VenueTime(String name, String times, int size){
        this.name = name;
        this.times = times;
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

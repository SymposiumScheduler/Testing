public class VenueTime{
    int start;
    int end;
    int[] time;
    Venue venue;
    Panel panel;

    public VenueTime(int[] time, Venue venue){
        this.time = time;
        this.venue = venue;
    }

    public void setPanel(Panel panel){
        this.panel = panel;
    }

    public Panel getPanel(){
        return this.panel;
    }

}

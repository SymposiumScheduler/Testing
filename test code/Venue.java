public class Venue {
    String name;
    int size;
    VenueTime[] assignedTimes;
    VenueTime[] freeTImes;

    public Venue(){}

    public VenueTime[] getAssignedTImes(){
        return new VenueTime[]{};
    }

    public VenueTime[] getFreeTImes(){
        return new VenueTime[]{};
    }

    public void assignVenueTIme(VenueTime venueTime){}

    public void freeVenueTIme(VenueTime venueTime){}


}

/**
    Model is immutable.
    -- Read About the code in Header of TimeLine.java
*/
public class Model {
    public final Venue[] venues;
    public final Panel[] panels;

    // public final Map<PanelistIdType, String> panelistNames; // for the report
}

class Venue implements Linkable{
    public final String name;
    public final SizeType size;
}

class Panel implements Linkable{
    public final String name;
    public final PanelistIdType[] panelists; // Array of its panelist Id's // needed for some constraints
    public final CategoryType category;
    public final Constraint[] constaints;
}
class Constraint {} // TODO

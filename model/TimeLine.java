/** This is psudo code, I tried to make it as close to java without complicating it too much.

    * Identifier[] is just a list can be an array or a list object.
    * methods without return type are void
    * if it's not private it's public
    * Most other things are the same as java.

    There are generic things that need to be decided.
        replace TimeType, SizeType, CategoryTime, PanelistIdType with other appropriate type, maybe int ?
        Examples :
            TimeType is int : represent time to the minute.
            SizeType is enum: {Size1, Size2, Size3...}
            CategoryType is int : represent an id for every category
            PanelistIdType is int : just the index in the panelistNamesList in model
        But other things can work
*/


/**
    TimeRange
    They are inclusive, start and end are part of the range.
        i.e : [1-2] Intersects [2-3] in 2

*/
class Range {
    // Condition : start <= end
    final TimeType start;
    final TimeType end;


    /** check if it's nice
            Nice Range List : a list of disjoint ranges sorted by the start time.
    */
    static boolean isNiceRangeList(Range[] ranges);
}


public class TimeLine {
   //-- Constructor
   // create venueLineHandlers and add them to the timeline.
   TimeLine(venues);
    //-- Declerations of types
    /**
        in search methods(isAllocated or findAll methods), a condition is used.
        the condition decide if a time range is part of the search result, we
        have two kinds of conditions:
            INTERSECT : It's enough for a range to intersect the search range
                        to be added. Usualy used for conflicts.
                            |-------|           |-----------------|
                                |--------|         |-------|
            ENCLOSE : the search range must enclose the range to be added to
                      the result. Usualy used for proposing an empty venue range
                            X|----X---| X    X     |-----------------|
                            X   |-X-----X---|X       |-------|
    */
    static final enum SearchCondition {INTERSECT, ENCLOSE}

    //-- State
    VenueLineHandler[] VenueLines;
    PanelLineHandler[] panelLines;

    //-- Methods
    /**
        attach a panel to the time line with venue and range
    */
    attach(panel,range,venue) throws “INVALID: VENUE NOT AVILAIBLE” {
        panelLines.add(panel)
        panel.attached(new RangeLink<Venue>(venue,range));
        venue.allocate(new RangeLink<Panel>(panel,range));
    }
    /**
        attach a panel to the time line range
    */
    PanelLineHandler detach({Panel, PanelLineHandler} p) {
        // remove will remove the appropriate PanelLineHandler from the list.
        venueLines.remove(p);
        return panelLines.remove(p);
    }


    /** Implementation Info
        !!!CAN BE OPTIMIZED MORE!!!
        General implementation for all search methods:
            traverse all panel/venueLineHandles and check ranges according to condition
                if INTERSECT : range.start<=search.end && range.end>=search.start
                if ENCLOSE   : range.end<=search.end && range.start>=search.end
            if found something, add to the result.

        A generic general method to search can be used in all of them
    */
    /**
        should use the same implementation of findAll with venueLins and ENCLOSE.
    */
    Venue[] findAllAvilaibleVenues(Range[] searchRanges, TimeType duration, SizeType size);

    /**
        check if panelist is allocated in the time range
    */
    boolean isAllocated(Range searchRange, PanelistIdType panelists, SearchCondition condition);
    /**
        check if panel is allocated in the time range
    */
    boolean isAllocated(Range searchRange, Panel panel, SearchCondition condition);
    /**
        check if venue is allocated in the time range
    */
    boolean isAllocated(Range searchRange, Venue venue, SearchCondition condition);
    /**
        check if category is allocated in the time range
    */
    boolean isAllocated(Range searchRange, CategoryType category, SearchCondition condition);

    /**
        finds and counts repeats of panelists in the range
        return : tuple of panelistId and how many times it repeated
    */
    (PanelistIdType,int repeats)[] findAllPanelists(Range searchRange, SearchCondition condition);
    /**
        NEXT 3 methods : finds panels/venues/categories in a range with a condition
    */
    Panel[] findAllPanels(Range searchRange, Condition condition);
    Venues[] findAllVenues(Range searchRange, Condition condition);
    Category[] findAllCategory(Range searchRange, Condition condition);
    //Result[] findAllEverything(Range range, Condition condition); // Maybe an optimization ?
}

////////////////////////////////////////////////////////

interface Linkable {} // for use in RangeLink
/**
    Immutable class to represent link different stuff, used in LineHandlers
*/
class RangeLink<T extends Linkable> {
    final T linkedObject;
    final Range range
}

////////////////////////////////////////////////////////

/**
    Handle a venue line. Responsiblity is allocation and keeping free and allocated
    separate. keep range lists nice (look Range class).

    || TODO || : Also, answer to what serach methods calls to ask about stuff.
*/
class VenueLineHandler {
    VenueLineHandler(Venue venue);
    //--
    final Venue venue;

    private AvilaibilityLine avalaibleLine; // Class defined in bottom of the class
    private LinkedLine linkedLine;  // ~~~~~~~~~~

    //--
    allocate(RangeLink<Panel> link) {
        avilaiblieLine.remove(link.range);
        linkedLine.add(link);
    }
    RangeLink<Panel> deallocate(Panel panel) {
        link = LinkedLine.remove(panel);
        avilaiblityLine.add(link.range);
        return link;
    }
    //--
    /**
        these two classes are responsible for saving an ordered set of time
        ranges and maintaining it. ie: <1-2> + <2-3> = <1-3> and so on.
    */
    private static class AvilaibilityLine {
        Range[] ranges;
        //--
        add(range);
        Range remove(range);
    }
    private static class LinkedLine {
        RangeLink<Panel> links; // disjoint time ranges sorted by time
        //--
        add(RangeLink<Panel>);
        RangeLink<Panel> remove(RangeLink<Panel>);
        RangeLink<Panel> remove(Panel panel);
    }
}
/**
    Handle a panel line. Responsiblity is attach a panel. Keep range lists nice.

    || TODO || : Also, answer to what search methods calls to ask about stuff.
*/
class PanelLineHandler {
    PanelLineHandler(Panel panel);
    //--
    Panel panel;
    RangeLink<Venue> link= null;
    //--
    attached(link) { this.link = link}
    detached() { link = null }
}

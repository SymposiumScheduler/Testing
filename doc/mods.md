# Suggested modifications to the algorithm

*Random ideas, change or add !*

## Time slots

Variable time slots requires a solution. Proposed solutions are :

### Indexing variable time slots for venues

Split the venue availability time into indexed slots. Then in the matrix 1 means
Time slot number 1 for a venue.

#### Issues

- How long is a venue time slot

   If a venue is free from 9 to 12:30, how to split this range to multiple time slots, so that it could be indexed in the matrix

- hard to detect and work around overlaps
- hard to allocate part of the venue free time slot

   Because the free time to be allocated depends on the duration of the panel

- hard to split one time slot for a venue between two panels

### Fixed Length Slots

Replace fixed length time slots with smaller units like 10 min. Now, if the
venue is open from 9 to 12:30 we can split that and allocated it.

- This allow us a simple way to detect and account for overlapping.
- Venue Allocation is easier.

   This also makes more sense if the venues are going to be open in ranges of time. it's easy to possibly split the range between two panels or only allocate a small part of it, but we are going to have to find a way to see if the range of venue availabilities a good choice for the panel or not because there might be venues which are open for the exact needed time and there are other panels which needs this space.

#### Issues

- The amount of memory needed is larger than before.

## Difficulty


### More measures of difficulty to consider

- Length of panel

   Panels with longer periods are harder to find their spot in an open range in a venue since the time slots are going to

- Max / min of its panelists

   panels with small max is harder to schedule
   
- How many other panels in the same category.

   panels in crowded categories are harder to schedule because they will not be in the same time
   
- How many other panels have same panelists

   because they can't be schedules together

## Constraints

### No two panels in the same category in the same time

To optimize this more, we can make time slots or even ranges of time slots recored
the categories schedules in this time slot/range, then when scheduling we can
eliminate time slots/ranges from the search space.

### Max panel for panelist in one day

To optimize this, group time slots in days. Days record panelist and how
many times every panelist is scheduled in this day. And then when scheduling a
panel consult the day first to check if possible to allocate without violating
the max constraint.

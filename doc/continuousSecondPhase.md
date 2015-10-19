## Continuous second phase of Iterative Greedy Alg.

> **Open the code and take a look and then keep it open for reading this file. Comments in the code are important.**

The basic Idea is that we get rid of the matrix, and instead replace it by continious time line. and things attach to it. All checks and allocating can be done smoothly by continious ranges.

the TimeLine is part of the model because it holds data and retrieve it. and does not decide functionality nor do any actually calculations.

## Example of how will it work

### Before adding any panels

|----| are time ranges. They are represented as [start, end] range
```
		 /           Mon.           \/           Tue.
TimeLine |--------------------------------------------...|
==========
#venue 4      |-----------------------|
#venue17                            |---------|
..
==========
$ EMPTY PANEL LINES
..
```
venue 4 if asked about its free ranges it will return its long range.

### *After some time and adding few panels* : Now, adding one more panel

|+++| is allocated range in a venue line
```
TimeLine |--------------------------------------------|
=========
#venue 4      |-|+++ panel67 +++|-----|
#venue17                            |---------|
..
=========
$panel67        |---------------|
..
```

venue 4 has a penel allocated in it. Now, if venue 4 is asked, it will return two ranges.

if findAllCategories method called with a search range intersects/enclosed with panel 17 range, it will return a list of panel17's categories. because it intersects

#### Next step : choosing next panel to add

* pop from dificulity queue
* find avilaible venue ranges
	* start with the same way as *iterative greedy*. and choose from the queue.
	* find avilaibility of panel and do `timeline.finaAvilaibleVenues(range,duration,size)`
	* choose one just like like *original iterative greedy*.
* run findAll methods to check for and run againts constraint.
	* If confilict : for example ( findAllCategories returned a category that matches the panel )
		* do just like  *original iterative greedy* for example : check alternatives and move other things if necessary.
* run `timeline.attach(panel, range, venue)`.
* Move to the next Panel

## Examples of how to check for constraints

### Max panelists in a day.

simply call `timeline.findAllPanelists(range,condition)`. this will return the panelists in this range and a count of how many times it repeats.

### Single Category or "this panel cannot be scheuled with this panel"

call `timeline.fineAllCategories(range, condition)` or `timeline.fineAllPanels(range, condition)` and check for confilicts

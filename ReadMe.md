
## Goals

Given a set of Lidar scans assemble them into a single 3D picture

Object edge is where a scan is short of the max
Inferred edge is from a short scan to max
Confirmed edge is an object edge from multiple scans

Coalese objects into a single when edges are confirmed

Why? who wants to understand objects?  

* For navigation there is no need unless we want to start route planning
* for obtaining contour maps it's not needed unless it's to remove the objects

Foliage detecdtion/removal?

If the data set is incomplete prompt for samples that will help
make it complete

Assumptions:

* Each scan is taken at a random location
* Relative 3D path from each scan location to the next scan is known


show each scan by drawing a line around the outer edge

random sampling gets a uniform distribution of scans, duh


















## cheat sheet


1. First ordered list item
2. Another item
⋅⋅* Unordered sub-list. 
1. Actual numbers don't matter, just that it's a number
⋅⋅1. Ordered sub-list
4. And another item.

⋅⋅⋅You can have properly indented paragraphs within list items. Notice the blank line above, and the leading spaces (at least one, but we'll use three here to also align the raw Markdown).

⋅⋅⋅To have a line break without a paragraph, you will need to use two trailing spaces.⋅⋅
⋅⋅⋅Note that this line is separate, but within the same paragraph.⋅⋅
⋅⋅⋅(This is contrary to the typical GFM line break behaviour, where trailing spaces are not required.)

* Unordered list can use asterisks
- Or minuses
+ Or pluses

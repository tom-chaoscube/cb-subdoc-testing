#output as png image
set terminal png size 1280,1280 

#save file to "domain.png"
set output "domain.png"

#graph title
set title "ab -n 1000 -c 50"

#disable key due to number of elements
set key on 

#nicer aspect ratio for image size
set size 1,1

# y-axis grid
set grid y

#x-axis label
set xlabel "request"

#y-axis label
set ylabel "response time (ms)"

#plot data from "domain.dat" using column 9 with smooth sbezier lines
#and title of "something" for the given data
sizes = "1 10 20 30 40 50"

filename(n) = sprintf("./plot-data/fulldoc/plot-data-%s", n)
# Plot the data
#plot for [i=1:50] filename(i) using 9 smooth sbezier with lines 

plot for[i in sizes] filename(i) using 9 smooth sbezier with lines 

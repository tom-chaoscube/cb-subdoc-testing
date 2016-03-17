require(ggplot2)
require(reshape2)
require(plyr)

#Useful commmands:

#Import from csv
fileList <- list.files(path="~/IdeaProjects/subdocREST/src/tools/plot-data/fulldoc", pattern="plot-data", full.names=FALSE)

#Read in percentages from first file

dfa <- read.csv(paste("~/IdeaProjects/subdocREST/src/tools/plot-data/fulldoc/plot-data-1"), header=TRUE, sep=",", colClasses = c(NA,"NULL"))
dfa <- rename(dfa, c("Percentage.served"="pct"))


#Read the timings data from 2nd column of files
for (file in fileList) { dfa[paste(file)] <- read.csv(file, header=TRUE, sep=",", colClasses = c("NULL", NA))}

#melt data
dfm <- melt(dfa, id.vars="pct")

ggplot(dfm, aes(pct,value, col=variable)) + geom_line(size=0.3) + geom_point(size=0.5)
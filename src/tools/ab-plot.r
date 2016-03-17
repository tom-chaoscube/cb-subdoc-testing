require(ggplot2)
require(reshape2)
require(plyr)

#Useful commmands:
endPoint="fulldoc"
basePath="~/IdeaProjects/subdocREST/src/tools/plot-data/"

#Import from csv
fileList <- list.files(path=paste(basePath, endPoint, sep=""), pattern="plot-data", full.names=FALSE)

#Read in percentages from first file

dfa <- read.csv(paste(basePath,endPoint,"/",fileList[1],sep=""), header=TRUE, sep=",", colClasses = c(NA,"NULL"))
dfa <- rename(dfa, c("Percentage.served"="pct"))


#Read the timings data from 2nd column of files
for (file in fileList) { dfa[paste(file)] <- read.csv(file, header=TRUE, sep=",", colClasses = c("NULL", NA))}

#Trim the data to 95th
dfaTrim <- dfa[-c(96,97,98,99,100),]

#melt data
dfm <- melt(dfa, id.vars="pct")
dfmTrim <- melt(dfaTrim, id.vars="pct")

# We're plotting only the bottom 95% of results. Uncomment this line to instead plot the full 100% of results.
#ggplot(dfm, aes(pct,value, col=variable)) + geom_line(size=0.3) + geom_point(size=0.5) + ylab("Time in ms") + xlab("Percent of Total Requests completed within time")
ggplot(dfmTrim, aes(pct,value, col=variable)) + geom_line(size=0.3) + geom_point(size=0.5) + 
  ylab("Time in ms") + xlab("Percent of total requests completed within time")
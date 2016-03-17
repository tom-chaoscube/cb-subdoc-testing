#Useful commmands:

#Import from csv
fileList <- list.files(path=".", pattern="plot-data")
csvData <- lapply(fileList, read.csv)

#Alt
dfa <- read.csv("plot-data-1", header=TRUE, sep=",")

#Rename columns
dfr <- rename(df, c("plotData1.Percentage.served"="pct","plotData1.Time.in.ms"="ms"))
dfa <- rename(dfa, c("Percentage.served"="pct","Time.in.ms"="1 rec"))
dfa <- rename(dfa, c("Percentage.served"="pct"))

#Read 2nd column from multiple files
for (file in fileList) { dfa[paste(file)] <- read.csv(file, header=TRUE, sep=",", colClasses = c("NULL", NA))}

#Optionally trim the data to 95th
dfTrim <- dfa[-c(96,97,98,99,100),]

#melt data
dfm <- melt(dfr, id.vars="pct")

#ggplot
ggplot(dfm, aes(pct,value, col=variable)) + geom_point()
ggplot(dfm, aes(pct,value, col=variable)) + geom_point()
ggplot(dfm, aes(pct,value, col=variable)) + geom_line(size=0.3) + geom_point(size=0.5)

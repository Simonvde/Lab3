

pvalue <- function(Cws,Cvs,var){ 
  #Cvs is the estimated expectation of the local clustering coefficient of a vertex
  #Cws is the clustering coefficient of our given network
  return(pnorm(Cws,mean=Cvs,sd=sqrt(var),lower.tail=FALSE))
}

Cvs <- 2*E/N/(N-1)   #note that Cvs=p. Note also that this isa vector of the value for each network.
var <- (1-p)/E/(N-2)  #vector of variances (also for each network)
#Cws <- c()           #this should be given
pvalues <- pvalue(Cws,Cvs,var)    #vector of pvalues
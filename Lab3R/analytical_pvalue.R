


pvalue <- function(Cws,Cvs,var){ 
  #Cvs is the estimated expectation of the local clustering coefficient of a vertex
  #Cws is the clustering coefficient of our given network
  return(pnorm(Cws,mean=Cvs,sd=sqrt(var),lower.tail=FALSE))
}

# DATA
N <- c(21532,12207,36865,40298,69303,29634,13283,36126,14726,20409)
E <- c(68767,25558,197318,181081,257295,193186,43974,106716,56042,45642)
#mean local clustering coefficient
Cws <- c(0.188588,0.047010,0.221312,0.171235,0.121759,0.235362,0.133821,0.050899,0.144128,0.223786) 

Cvs <- 2*E/N/(N-1)   #note that Cvs=p. Note also that this isa vector of the value for each network.
var <- (1-Cvs)/E/(N-2)  #vector of variances (also for each network)

pvalues <- pvalue(Cws,Cvs,var)    #vector of pvalues



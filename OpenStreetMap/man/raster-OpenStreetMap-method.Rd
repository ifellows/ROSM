% Generated by roxygen2: do not edit by hand
% Please edit documentation in R/osm.R
\name{raster,OpenStreetMap-method}
\alias{raster,OpenStreetMap-method}
\title{Create a RasterLayer from an OpenStreetMap}
\usage{
\S4method{raster}{OpenStreetMap}(x, ...)
}
\arguments{
\item{x}{an OpenStreetMap}

\item{...}{unused}
}
\description{
Create a RasterLayer from an OpenStreetMap
}
\examples{
\dontrun{
library(raster)
longBeachHarbor <- openmap(c(33.760525217369974,-118.22052955627441),
		c(33.73290566922855,-118.17521095275879),14,'bing')
ras <- raster(longBeachHarbor)
plotRGB(ras)
}
}

% Generated by roxygen2: do not edit by hand
% Please edit documentation in R/osm.R
\name{openmap}
\alias{openmap}
\title{Get a map based on lat long coordinates}
\usage{
openmap(upperLeft, lowerRight, zoom = NULL, type = c("osm", "osm-bw",
  "maptoolkit-topo", "waze", "bing", "stamen-toner", "stamen-terrain",
  "stamen-watercolor", "osm-german", "osm-wanderreitkarte", "mapbox", "esri",
  "esri-topo", "nps", "apple-iphoto", "skobbler", "hillshade", "opencyclemap",
  "osm-transport", "osm-public-transport", "osm-bbike", "osm-bbike-german"),
  minNumTiles = 9L, mergeTiles = TRUE)
}
\arguments{
\item{upperLeft}{the upper left lat and long}

\item{lowerRight}{the lower right lat and long}

\item{zoom}{the zoom level. If null, it is determined automatically}

\item{type}{the tile server from which to get the map, or the url pattern.}

\item{minNumTiles}{If zoom is null, zoom will be chosen such that
the number of map tiles is greater than or equal 
to this number.}

\item{mergeTiles}{should map tiles be merged into one tile}
}
\description{
Get a map based on lat long coordinates
}
\details{
Type may be the url of a custom tile server (http://wiki.osgeo.org/wiki/Tile_Map_Service_Specification). 
should include {z}, {y}, and {x} specifying where the zoom, xtile and ytile location should be substituted. e.g.

http://api.someplace.com/.../{z}/{x}/{y}
}
\examples{
\dontrun{
#show some of the maps available
nm <- c("osm", "maptoolkit-topo", "bing", "stamen-toner", 
		"stamen-watercolor", "esri", "esri-topo", 
		"nps", "apple-iphoto", "skobbler")
par(mfrow=c(3,4))
#Korea
for(i in 1:length(nm)){
	map <- openmap(c(43.46886761482925,119.94873046875),
			c(33.22949814144951,133.9892578125),
		minNumTiles=3,type=nm[i])
plot(map)
}
# Some maps from custom urls
apiKey <- paste0("?access_token=",
 "pk.eyJ1IjoidGhlZmVsbCIsImEiOiJjaXN1anNwODEwMWlrMnRvZHBhamRrZjlqIn0.Gf8qLSpZ6yo5yfQhEutFfQ")
baseUrl <- "https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v9/tiles/256/{z}/{x}/{y}"
map <- openmap(c(43.46886761482925,119.94873046875),
	c(33.22949814144951,133.9892578125),
		minNumTiles=4,
		type=paste0(baseUrl,apiKey))
plot(map)

baseUrl <- "https://api.mapbox.com/styles/v1/mapbox/dark-v9/tiles/256/{z}/{x}/{y}"
map <- openmap(c(43.46886761482925,119.94873046875),
	c(33.22949814144951,133.9892578125),
		minNumTiles=4,
		type=paste0(baseUrl,apiKey))
plot(map)


#plot Korea with ggplot2.
library(ggplot2)
map <- openmap(c(43.46886761482925,119.94873046875),
	c(33.22949814144951,133.9892578125),
	minNumTiles=4)
autoplot(map)

}
}


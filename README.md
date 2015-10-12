# R package for OpenStreetMap Raster Images
Accesses high resolution raster maps using the OpenStreetMap protocol. Dozens of road, satellite, and topographic map servers are directly supported, including CloudMade,     Mapnik, Bing, stamen, and MapQuest. Maps can be plotted using either base graphics, or ggplot2. This package is not affiliated with the OpenStreetMap.org mapping project.

#Examples
See: http://blog.fellstat.com/?cat=15

# Build
To build and install package from source run

sh makePackage

NOAWT=1 R CMD INSTALL OpenStreetMap_*.*.*.tar.gz

(Replace the stars with the version of the package created)

#Author
Ian Fellows, using the JMapViewer library by Jan Peter Stotz
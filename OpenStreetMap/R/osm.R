
#' Open street map (and google) mercator projection
#' @export
osm <- function(){
	CRS("+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs")
}

#' Latitude Longitude projection
#' @export
longlat <- function(){
	CRS("+proj=longlat +datum=WGS84")
}

#'Maps long lat values to the open street map mercator projection
#' @param lat a vector of latitudes
#' @param long a vector of longitudes
#' @param drop drop to lowest dimension
#' @export
projectMercator <- function(lat,long,drop=TRUE){
	df <- data.frame(long=long,lat=lat)
	coordinates(df) <- ~long+lat
	proj4string(df) <- CRS("+proj=longlat +datum=WGS84")
	df1 <- spTransform(df,osm())
	coords <- coordinates(df1)
	colnames(coords) <- c("x","y")
	if(drop)
		coords <- drop(coords)
	coords
}


#'Get an open street map tile.
#' @param x location in osm native coordinates
#' @param y location in osm native coordinates
#' @param zoom zoom level
#' @param type the map type (see getMapInfo)
#' @return a tile
#' @export
osmtile <- function(x,y,zoom,type="osm"){
	.tryJava()
	x <- as.double(x)
	y <- as.double(y)
	zoom <- as.double(zoom)
	TC <- J("edu.cens.spatial.RTileController")
	res <- TC$getInstance(type)$getTileValues(x,y,zoom)
	if(is.null(res))
		stop(paste("could not obtain tile:",x,y,zoom))
	res1 <- as.character(as.hexmode(res))
	colrs <- paste("#",substring(res1,3),sep="")
	sc <- 20037508*2
	minim <- -20037508

	p1 <- c(x/(2^zoom)*sc+minim,-(y/(2^zoom)*sc+minim))
	p2 <- c((x+1)/(2^zoom)*sc+minim,-((y+1)/(2^zoom)*sc+minim))
	bbox <- list(p1=p1,p2=p2)
	res <- list(colorData=colrs,bbox=bbox,projection=osm(),xres=255,yres=255)
	class(res) <- "osmtile"
	res
}

#'Add tile to plot
#' @param x the tile
#' @param y ignored
#' @param add add to current plot (if raster, then image is always added)
#' @param raster use raster image
#' @param ... additional parameters to image or rasterImage
#' @method plot osmtile
#' @export
plot.osmtile <- function(x, y=NULL, add=TRUE, raster=TRUE, ...){
	xres <- x$xres
	yres <- x$yres
	if(!raster)
		image(x=seq(x$bbox$p1[1] - .5*abs(x$bbox$p1[1]-x$bbox$p2[1])/yres,
			x$bbox$p2[1] + .5*abs(x$bbox$p1[1]-x$bbox$p2[1])/yres,length=yres),
			y=seq(x$bbox$p2[2] - .5*abs(x$bbox$p1[2]-x$bbox$p2[2])/xres,
				x$bbox$p1[2] + .5*abs(x$bbox$p1[2]-x$bbox$p2[2])/xres,length=xres),
			z=t(matrix(1:(xres*yres),nrow=xres,byrow=TRUE))[,xres:1],
			col=x$colorData,add=add,...)
	else
		rasterImage(as.raster(matrix(x$colorData,nrow=xres,byrow=TRUE)),
				x$bbox$p1[1] - .5*abs(x$bbox$p1[1]-x$bbox$p2[1])/yres,
				x$bbox$p2[2] + .5*abs(x$bbox$p1[2]-x$bbox$p2[2])/xres,
				x$bbox$p2[1] - .5*abs(x$bbox$p1[1]-x$bbox$p2[1])/yres,
				x$bbox$p1[2] + .5*abs(x$bbox$p1[2]-x$bbox$p2[2])/xres,
				...)
}

#' Get a map based on lat long coordinates
#' @param upperLeft the upper left lat and long
#' @param lowerRight the lower right lat and long
#' @param zoom the zoom level. If null, it is determined automatically
#' @param type the tile server from which to get the map, or the url pattern.
#' @param minNumTiles If zoom is null, zoom will be chosen such that
#' 					the number of map tiles is greater than or equal
#' 					to this number.
#' @param mergeTiles should map tiles be merged into one tile
#' @details
#' Type may be the url of a custom tile server (http://wiki.osgeo.org/wiki/Tile_Map_Service_Specification).
#' should include \{z\}, \{y\}, and \{x\} specifying where the zoom, xtile and ytile location should be substituted. e.g.
#'
#' http://api.someplace.com/.../\{z\}/\{x\}/\{y\}.png
#'
#' @examples \dontrun{
#' #show some of the maps available
#' nm <- c("osm","bing","osm-german","esri","esri-topo","esri-physical","esri-shaded",
#'   "esri-imagery","esri-terrain","esri-natgeo","nps","apple-iphoto")
#' par(mfrow=c(3,4), mar=c(0,0,0,0))
#' #Korea
#' for(i in 1:length(nm)){
#' 	map <- openmap(c(43.46886761482925,119.94873046875),
#' 			c(33.22949814144951,133.9892578125),
#'			minNumTiles=3,type=nm[i])
#'	plot(map)
#'}
#' # Some maps from custom urls (use your own API key)
#' apiKey <- paste0("?access_token=",
#'  "pk.eyJ1IjoidGhlZmVsbCIsImEiOiJjaXN1anNwODEwMWlrMnRvZHBhamRrZjlqIn0.Gf8qLSpZ6yo5yfQhEutFfQ")
#' baseUrl <- "https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v9/tiles/256/{z}/{x}/{y}"
#' map <- openmap(c(43.46886761482925,119.94873046875),
#'		c(33.22949814144951,133.9892578125),
#' 		minNumTiles=4,
#' 		type=paste0(baseUrl,apiKey))
#' plot(map)
#'
#' baseUrl <- "https://api.mapbox.com/styles/v1/mapbox/dark-v9/tiles/256/{z}/{x}/{y}"
#' map <- openmap(c(43.46886761482925,119.94873046875),
#'		c(33.22949814144951,133.9892578125),
#' 		minNumTiles=4,
#' 		type=paste0(baseUrl,apiKey))
#' plot(map)
#'
#'
#'#plot Korea with ggplot2.
#'library(ggplot2)
#'map <- openmap(c(43.46886761482925,119.94873046875),
#'		c(33.22949814144951,133.9892578125),
#'		minNumTiles=4)
#'autoplot(map)
#'
#' }
#' @export
openmap <- function(upperLeft,lowerRight,zoom=NULL,
		type=c("osm","bing",
			"osm-german",
			"esri","esri-topo","esri-physical","esri-shaded","esri-imagery","esri-terrain","esri-natgeo",
			"nps","apple-iphoto", "osm-public-transport"),
		minNumTiles=9L, mergeTiles=TRUE){
  
	type <- type[1]
	type <- switch(
	  type,
	  "osm" = "http://tile.openstreetmap.org/{z}/{x}/{y}.png",
	  "esri" = "https://server.arcgisonline.com/arcgis/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}.png",
	  "esri-topo" = "https://server.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}.png",
	  "esri-physical" = "https://server.arcgisonline.com/arcgis/rest/services/World_Physical_Map/MapServer/tile/{z}/{y}/{x}.png",
	  "esri-shaded" = "https://server.arcgisonline.com/arcgis/rest/services/World_Shaded_Relief/MapServer/tile/{z}/{y}/{x}.png",
	  "esri-imagery" = "https://server.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}.png",
	  "esri-terrain" = "https://server.arcgisonline.com/arcgis/rest/services/World_Terrain_Base/MapServer/tile/{z}/{y}/{x}.png",
	  "esri-natgeo" = "https://server.arcgisonline.com/arcgis/rest/services/NatGeo_World_Map/MapServer/tile/{z}/{y}/{x}.png",
	  "nps" = "https://services.arcgisonline.com/ArcGIS/rest/services/World_Physical_Map/MapServer/tile/{z}/{y}/{x}.jpg",
	  type
	)

	.tryJava()
	autoZoom <- is.null(zoom)
	if(autoZoom)
		zoom <- 1L
	else
		zoom <- as.integer(zoom)
	ts <- new(J("org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource"))
	for(i in 1:18){
		minY <- as.integer(floor(ts$latToTileY(upperLeft[1L],zoom)))
		maxY <- as.integer(floor(ts$latToTileY(lowerRight[1L],zoom)))

		nX <- as.integer(round(ts$lonToTileX(180,zoom)))
		minX <- as.integer(floor(ts$lonToTileX(upperLeft[2L],zoom)))
		maxX <- as.integer(floor(ts$lonToTileX(lowerRight[2L],zoom)))
		if( minX > maxX){
		  tmp <- maxX
		  maxX <- minX
		  minX <- tmp
			#maxX <- maxX + nX
		}
		ntiles <- abs((maxX-minX+1)*(maxY-minY+1))
		if(!autoZoom)
			break
		if(ntiles>=minNumTiles)
			break
		else
			zoom <- as.integer(zoom + 1L)
	}
	map <- list(tiles=list())
	for( x in minX:maxX){
		for(y in minY:maxY){
			tile <- osmtile(x %% nX,y,zoom,type)
			map$tiles[[length(map$tiles)+1L]] <- tile
		}
	}
	map$bbox <- list(p1=projectMercator(upperLeft[1L],upperLeft[2L]),
	                 p2=projectMercator(lowerRight[1L],lowerRight[2L]))
	map$bbox <- list(p1=c(
						x=min(map$bbox$p1[1L],map$bbox$p2[1L]),
						y=max(map$bbox$p1[2L],map$bbox$p2[2L])
					),
					p2=c(
						x=max(map$bbox$p1[1L],map$bbox$p2[1L]),
						y=min(map$bbox$p1[2L],map$bbox$p2[2L])
					)
	)
	class(map) <- "OpenStreetMap"
	attr(map,"zoom") <- zoom
	if(mergeTiles) .mergeTiles(map) else map
}

#'Plot an OpenStreetMap object.
#' @param x the OpenStreetMap
#' @param y ignored
#' @param add add to current plot
#' @param removeMargin remove margins from plotting device
#' @param ... additional parameters to be passed to plot
#' @method plot OpenStreetMap
#' @examples \dontrun{
#' #
#' #	The following examples
#' #	plot using native mercator coordinates,
#' #	transforming the data where needed
#' #
#'library(sp)
#'m <- c(25.7738889,-80.1938889)
#'j <- c(58.3019444,-134.4197222)
#'miami <- projectMercator(25.7738889,-80.1938889)
#'jun <- projectMercator(58.3019444,-134.4197222)
#'data(states)
#'map <- openmap(j,m,4,type="esri-terrain")
#'plot(map,removeMargin=FALSE)
#'plot(states,add=TRUE)
#'
#'data(LA_places)
#'longBeachHarbor <- openmap(c(33.760525217369974,-118.22052955627441),
#'		c(33.73290566922855,-118.17521095275879),14,'bing')
#'coords <- coordinates(LA_places)
#'x <- coords[,1]
#'y <- coords[,2]
#'txt <- slot(LA_places,"data")[,'NAME']
#'plot(longBeachHarbor)
#'points(x,y,col="red")
#'text(x,y,txt,col="white",adj=0)
#'
#'}

#' }
#' @export
plot.OpenStreetMap <- function(x,y=NULL,add=FALSE,removeMargin=TRUE, ...){
	mar <- par("mar")
	if(add==FALSE){
		plot.new()
		if(removeMargin)
			par(mar=c(0,0,0,0))
		plot.window(xlim=c(x$bbox$p1[1],x$bbox$p2[1]),ylim=c(x$bbox$p2[2],x$bbox$p1[2]) ,
				xaxs = 'i', yaxs = 'i', asp=T)
	}
	for(tile in x$tiles)
		plot(tile,...)
	par(mar=mar)
}




#' Create a RasterLayer from a tile
#' @param x an osmtile
#' @param ... unused
#' @export
setMethod("raster","osmtile",function(x, ...){
	rgbCol <- col2rgb(x$colorData)

	red <- matrix(rgbCol[1,],nrow=x$xres,byrow=TRUE)
	green <- matrix(rgbCol[2,],nrow=x$xres,byrow=TRUE)
	blue <- matrix(rgbCol[3,],nrow=x$xres,byrow=TRUE)

	xmn <- x$bbox$p1[1]
	xmx <- x$bbox$p2[1]
	ymn <- x$bbox$p2[2]
	ymx <- x$bbox$p1[2]

	ras <- stack(raster(red,xmn=xmn,xmx=xmx,ymn=ymn,ymx=ymx),
			raster(green,xmn=xmn,xmx=xmx,ymn=ymn,ymx=ymx),
			raster(blue,xmn=xmn,xmx=xmx,ymn=ymn,ymx=ymx))
	projection(ras) <- x$projection
	ras
}
)

#' Create a RasterLayer from an OpenStreetMap
#' @param x an OpenStreetMap
#' @param ... unused
#' @examples \dontrun{
#' library(raster)
#' longBeachHarbor <- openmap(c(33.760525217369974,-118.22052955627441),
#' 		c(33.73290566922855,-118.17521095275879),14,'bing')
#' ras <- raster(longBeachHarbor)
#' plotRGB(ras)
#' }
#' @export
setMethod("raster","OpenStreetMap",function(x, ...){
	tiles <- length(x$tiles)
	if (tiles > 1) {
		rasterImg <- list()
		for (i in 1:length(x$tiles)) {
			rasterImg[[i]] <- raster(x$tiles[[i]])
		}
		# single calls to merge, and overlap=F for efficiency.
		rasterImg$overlap <- FALSE
		rasterImg <- do.call(raster::merge, rasterImg)
	} else {
		rasterImg <- raster(x$tiles[[1]])
	}
	ext <- extent(x$bbox$p1[1],x$bbox$p2[1],x$bbox$p2[2],x$bbox$p1[2])
	crop(rasterImg,ext)
}
)



#' Projects the open street map to an alternate coordinate system
#' @param x an OpenStreetMap object
#' @param projection a proj4 character string or CRS object
#' @param ... additional parameters for projectRaster
#' @examples \dontrun{
#'library(maps)
#'
#'#plot bing map in native mercator coords
#'map <- openmap(c(70,-179),
#'		c(-70,179),zoom=1,type='bing')
#'plot(map)
#'
#'#using longlat projection lets us combine with the maps library
#'map_longlat <- openproj(map)
#'plot(map_longlat)
#'map("world",col="red",add=TRUE)
#'
#'#robinson projection. good for whole globe viewing.
#'map_robinson <- openproj(map_longlat, projection=
#'	"+proj=robin +lon_0=0 +x_0=0 +y_0=0 +ellps=WGS84 +datum=WGS84 +units=m +no_defs")
#'plot(map_robinson)
#'
#'#national parks service images
#'upperMap <- openmap(c(70,-179),
#'		c(10,50),zoom=2,type='nps')
#'#Lambert Conic Conformal
#'map_llc <- openproj(upperMap, projection=
#'	"+proj=lcc +lat_1=33 +lat_2=45 +lat_0=39 +lon_0=-96")
#'plot(map_llc,removeMargin=TRUE)
#'#add choropleth
#'library(sp)
#'data(states)
#'st_llc <- spTransform(states,CRS("+proj=lcc +lat_1=33 +lat_2=45 +lat_0=39 +lon_0=-96"))
#'plot(st_llc,add=T,col=heat.colors(48,.4)[slot(st_llc,"data")[["ORDER_ADM"]]])
#'
#' }
#' @export
openproj <- function(x,projection = "+proj=longlat",...){
	if(!is.character(projection))
		projection <- projection@projargs

	rasterImg <- raster(x)
	ras2 <- projectRaster(rasterImg,crs=projection,...)

	vals <- values(ras2)
	vals <- pmin(pmax(vals,0L),255L)
	flag <- apply(vals,1,function(a)any(!is.finite(a)))
	vals1 <- vals
	vals1[!is.finite(vals)] <- 0L
	colors <- ifelse(flag,NA,rgb(vals1[,1],vals1[,2],vals1[,3],maxColorValue=255L))
	ext <- extent(ras2)

	result <- list()
	result$colorData <- colors
	result$bbox <- list(p1 = c(ext@xmin,ext@ymax), p2 = c(ext@xmax,ext@ymin))
	result$projection <- CRS(projection)
	result$xres <- dim(ras2)[1]
	result$yres <- dim(ras2)[2]
	class(result) <- "osmtile"

	osm <- list(tiles=list(result))
	osm$bbox <- result$bbox
	attr(osm,"zoom") <- attr(x,"zoom")
	class(osm) <- "OpenStreetMap"
	osm
}

.mergeTiles <- function(x,...){
	ras2 <- raster(x)
	ext <- extent(x$bbox$p1[1],x$bbox$p2[1],x$bbox$p2[2],x$bbox$p1[2])
	ras2 <- crop(ras2,ext)
	vals <- values(ras2)
	vals <- pmin(pmax(vals,0L),255L)
	flag <- apply(vals,1,function(a)any(!is.finite(a)))
	vals1 <- vals
	vals1[!is.finite(vals)] <- 0L
	colors <- ifelse(flag,NA,rgb(vals1[,1],vals1[,2],vals1[,3],maxColorValue=255L))

	result <- list()
	result$colorData <- colors
	result$bbox <- list(p1 = c(ext@xmin,ext@ymax), p2 = c(ext@xmax,ext@ymin))
	result$projection <- x$tiles[[1]]$projection
	result$xres <- dim(ras2)[1]
	result$yres <- dim(ras2)[2]
	class(result) <- "osmtile"

	osm <- list(tiles=list(result))
	osm$bbox <- result$bbox
	attr(osm,"zoom") <- attr(x,"zoom")
	class(osm) <- "OpenStreetMap"
	osm
}


#'Print map
#' @param x the OpenStreetMap
#' @param ... ignored
#' @method print OpenStreetMap
#' @export
print.OpenStreetMap <- function(x,...){
	print(str(x))
}

#' Launches a Java helper GUI.
#' @details note for Mac OS X users:
#' On the mac this can only be run from a java console such as JGR.
#' @export
launchMapHelper <- function(){
	.tryJava()
	new(J("org.openstreetmap.gui.jmapviewer.Demo"))$setVisible(TRUE)
}

## Sets the user identification key for cloudmade.com
## @param key The key. Obtain a (not-)free map key at http://www.cloudmade.com
#setCloudMadeKey <- function(key){
#	if(!missing(key)){
#		J("edu.cens.spatial.RTileController")$setCloudMadeKey(as.character(key))
#	}
#	J("org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource")$cloudMadeKey
#}

#' Returns a table with relevant source and attribution info for each map type
#'
#' @export
getMapInfo <- function(){
	J("edu.cens.spatial.RTileController")$getInstance("osm")
	s <- J("edu.cens.spatial.RTileController")$sources
	n <- length(s)
	name <- sapply(s,function(x)x$getName())
	name[name=="Bing Aerial Maps"] <- "bing"
	url <- sapply(s,function(x)x$getAttributionLinkURL())
	attributionTerms <- sapply(s,function(x)x$getTermsOfUseURL())
	attribution <- sapply(s,function(x)x$getAttributionText(1L,
						.jnull("org.openstreetmap.gui.jmapviewer.Coordinate"),
						.jnull("org.openstreetmap.gui.jmapviewer.Coordinate")))
	cbind(name,url,attribution,attributionTerms)
}

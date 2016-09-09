
.isMac <- function(){
	length(grep("^darwin",R.version$os))>0
}

.isQuartz <- function(){
	t <- try(identical(options("device")$device, get("quartz")))
	if(inherits(t,"try-error"))
		return(FALSE)
	t
}

.vol <- new.env()
.vol$.initGD <- FALSE

.tryJava <- function(){
	# Work around for bug where quartz won't work if not initialized before headless AWT.
	if(!.vol$.initGD && .isMac() && .isQuartz() && is.null(dev.list())){
		dev.new()
		dev.off()
	}
	.vol$.initGD <- TRUE
	
	ty <- try(new(J("org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource")))
	if(inherits(ty,"try-error")){
		stop(
"Java classes could not be loaded. Most likely because Java is not set up with your R installation.
Here are some trouble shooting tips:

1. Install Java (for mac consider installing java 1.6 from https://support.apple.com/kb/DL1572?locale=en_US )
2. Run 
\tR CMD javareconf
in the terminal. If you are using Mac OS X >= 10.7 you may want to try
\tR CMD javareconf JAVA_CPPFLAGS=-I/System/Library/Frameworks/JavaVM.framework/Headers
instead.
"
		)
	}
}


.onLoad <- function(libname, pkgname) {
	.jni <- try(get(".jniInitialized"),silent=TRUE)
	if(inherits(.jni,"try-error"))
		.jni <- FALSE
	if(.isMac() && !.jni)
		Sys.setenv(NOAWT=1)
	if(FALSE && !.jni){
		jp <- getOption("java.parameters")
		if(!is.null(jp))
			jp <- c(jp,"-Xrs")
		else
			jp <- "-Xrs"
		options(java.parameters=jp)
	}
	ty <- try(.jpackage(pkgname, lib.loc=libname) )
	if(inherits(ty,"try-error")){
		stop(
				"Java classes could not be loaded. Most likely because Java is not set up with your R installation.
Here are some trouble shooting tips:
1. Install Java (for mac consider installing java 1.6 from https://support.apple.com/kb/DL1572?locale=en_US )		
2. Run 
\tR CMD javareconf
in the terminal. If you are using Mac OS X >= 10.7 you may want to try
\tR CMD javareconf JAVA_CPPFLAGS=-I/System/Library/Frameworks/JavaVM.framework/Headers
instead."
		)
	}	
}


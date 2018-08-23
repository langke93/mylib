WORK_DIR=/home/tomcat/sitemap
WEB_DIR=/home/tomcat/sq/webroot
JAVA=/home/tomcat/bea/jrockit_160_05/bin/java
cd $WORK_DIR
$JAVA -classpath .:classes12.jar org/langke/util/image/CreateProductThumbnails $WEB_DIR >result.txt 2>result.err &
DATE=`date   +%Y%m%d`
WORK_DIR=/home/tomcat/sitemap
WEB_DIR=/home/tomcat/sq/webroot
cd $WORK_DIR

java -classpath .:classes12.jar org/langke/util/xml/SiteMap >$WEB_DIR/sitemap.xml 2>err.$DATE &
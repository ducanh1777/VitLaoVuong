FROM tomcat:9.0-jdk11-openjdk

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file to the webapps directory as ROOT.war
# Note: Maven usually outputs the WAR file in the 'target' directory.
# Ensure your pom.xml <finalName> matches 'VitLaoVuong' or adjust here.
COPY target/VitLaoVuong.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]

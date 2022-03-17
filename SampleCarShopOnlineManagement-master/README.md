# SampleCarShopOnlineManagement
Sample Web App for Online Management of Car Shop.

<h3>About this project</h3>
<p>This project is mainly for fun and personal training purposes.</p>
  <p> This is a sample web app for online car sale or rent management where the user, after he logs using google log in, can upload photos of   his car/s for sale/rent, along with his car details e.g. brand model, and provide further detailed description in "details" section about car condition, mileage, price, personal contact info etc.</p>
  
  <p><b>Static instance of "AddCars" page of this project, is showcased <a href="https://am869.github.io/SampleCarShopOnlineManagement/">Here</a> .</b></p>

<h3>Development and deployment</h3>
<p> Developed using netbeans IDE as a Maven Web Application project. Deployed during development for testing, in netbeans managed instance of apache tomcat 8 as stand alone server.  This application uses MySql database server for its data/model layer,  as well as local file system folder for storing user files.</p>

<h3>Setup instructions</h3>
<h4>1. Database schema, and setup instructions</h4>
  1. The following is the database schema as it is shown in phpmyadmin's designer view.
  ![db-schema-phpmyadmin](https://cloud.githubusercontent.com/assets/9164293/21564200/82b45dda-ce93-11e6-879f-dd1293467158.png)
  
  2. Create a new empty database, import mycardb_structure_only from "database files" folder, to create the database schema in your empty db (Which means, this will create the empty tables with their constraints in your new empty database). Now you are ready to populate those tables, by running the application and simulating users adding their stuff, or directly in your db managing environment (prefer the first option cause in the second you will have to deal with many foreign key checks unless you study the schema very well).
  
  
  3. Insinde project Source Packages in package com.klsoukas.samplecarshoponlinemanagement.model.DbUtil class, modify the database access settings to match yours.
> public class DbUtil {  
> &nbsp;&nbsp;&nbsp;&nbsp;   public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";  
>  &nbsp;&nbsp;&nbsp;&nbsp;  public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/YOUR_DB_NAME";  
>  &nbsp;&nbsp;&nbsp;&nbsp;  public static final String USERNAME = "YOUR_DB_USERNAME";  
>  &nbsp;&nbsp;&nbsp;&nbsp;  public static final String PASSWORD = "YOUR_DB_PASSWORD";  
>}  


<h4>2. Server setup</h4>
<p>This tomcat instance is further configured for GZIP data compression and use of https protocol to serve most of the app's resources, by editing the connectors in server.xml and adding security constraints in application's web.xml.</p>

<h5>Server.xml connectors configured to serve gzipped data and made available https connection to site through port 8443</h5>

    <Connector URIEncoding="utf-8" connectionTimeout="20000" port="8080" protocol="HTTP/1.1"  
        compression="on"  
         compressionMinSize="2048"  
         compressableMimeType="text/html,text/plain,text/css,  
         application/javascript,application/json, application/xml, application/pdf  
         image/jpeg, image/png, image/gif"  
        redirectPort="8443"/>  


    <Connector connectionTimeout="20000" port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
     compression="on"
        compressionMinSize="2048"
        compressableMimeType="text/html,text/plain,text/css,
        application/javascript,application/json, application/xml, application/pdf
        image/jpeg, image/png, image/gif"
    
       maxThreads="200" SSLEnabled="true" scheme="https" secure="true"
       keystoreFile="your_keystore_location" keystorePass="your_keystore_password"
       keyAlias="your_key_name" keyPass="your_key_password"
       clientAuth="false" sslProtocol="TLS" />
        
<p>Note 1: You need to create your own keystore/key to be able to serve secure data via https, for more info on how to do that for tomcat 8 check <a href="https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html">https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html</a>.</p>
        
   <p>Note 2: If tomcat is going to be used as a standalone server in production environment, the port numbers should be changed for convenience, from 8080 to 80 and from 8443 to 443. </p>
        

<h4>3. File system location setup for user uploaded images</h4>
<p>Set the file location which is used to store  user-uploaded photos, as an application-wide parameter by setting a context-param in application's web.xml as following:</p>

        <context-param>
          <param-name>uploads_location</param-name>
          <param-value>/your-absolute-path-to-where-user-photos-will-be-stored</param-value>
        </context-param>
<p>Your absolute path should start with "/" and be like /dir/subdir1/subdir2/.../finalsubdir</p>
<p>This will result in that directory being created in your local disk that is serving the application, and will be used by the application to save and serve user-uploaded photos</p>
<p>This will work for both windows and linux file systems. If you want to specify different disk partition than the one that tomcat is running, in windows you can enter
the complete windows-envrironment-specific disk path e.g. C:\dir\subdir1\subdir2\...\finalsubdir</p>
<p>If that directory is not present will be created when first user uploads its image only if the application/server has the system permission to create/save file and subdirectories in that directory, if not, tomcat will throw exception, so make sure you decide correctly where that folder will be.</p>

<h4>4. Google Log In Setup</h4>
<p>In order to enable google log in you have to first create a client id for your web application using google's api console.
Follow the instructions on how to do that <a href="https://developers.google.com/identity/sign-in/web/devconsole-project">here</a>.  
Then add your client id to the following project files:</p>

 <p>inside web pages  in jsfolder/googleSignInOut.js: <br>  
      <i>line 7: &nbsp;&nbsp; client_id: 'YOUR_CLIENT_ID.apps.googleusercontent.com',</i> </p>

 <p>and also inside web pages in WEB-INF/jsp/main.jsp:  <br>
      <i>line 12: &nbsp;&nbsp; &lt;meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com"&gt;</i> </p> 

<h3>Whats is going to be added next</h3>
<p>- database schema probably will be changing a lot, new tables/fields(database columns) could be added</p>
<p>- many more car or user specific entries could be added in AddCars FORM when user is adding cars!, car modification functionality in separate page and user info page, maybe add facebook log in and custom log in functionality, RSS feed etc.</p>
<p>- add better initialization of some application level resources</p>
<p>- exception handling and showing database constraints as warnings at user level (like errors and duplicate entries etc.)</p>
<p>- improve project structure - programm flow, based on some well known framework like spring mvc or even migrate project to spring mvc</p>
<p>- use javascript file api for visual image upload presentation</p>
<p>- search for better caching solution (try same project with hibernate?)</p>
<p>- add search functionality, and maybe some parametrized query search (hibernate?)
</p>
<h3>Whats is added</h3>
<p>-added server-side image-resize, retaining original aspect ration, for user uploaded images in case they are over some large limit in pixels(>1200px width or height), this also greatly reduces photo size making it easier for server to serve images a lot faster </p>
<p>-added some temp pagination: cars presented in groups of five</p>
<p>-added some temp server-side cache in the form of context attribute arraylist. A Context initialized (ScheduledExecutorService) thread queries database every 15 seconds for new entries and updates our context attribute arraylist that is holding info for all our cars. This arraylist will be used to serve user requests for the main site content.</p>
<p>-added image loading on demand( the images will load only when user "opens" the corresponding car details div"), black color background while background img still loading</p>

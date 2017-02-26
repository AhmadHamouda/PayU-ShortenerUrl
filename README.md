#URL shortener

Is a service that takes long URLs and squeezes them into fewer characters to make a link that is easier to share, tweet, or email to friends. Users can create these short links through the URL Shortener API.

##URL Shortener API:

**1-Provide two main functions:**

1.1) As a user, can use this shorten URL in text messages w/o cluttering the message through
curl -X POST --header "Content-Type: application/json" --header "Accept: */*" "http://localhost:8060/user/shortUrl/?url=LONG_URL"

1.2) As a user, can access the original URL when using the shorten one in the browser tab, so that I can use both URLs interchangeably.

**2-provide other functions:**

2.1) As a user, can create account so can get longer links life according to configuration in properties file [Number of access shorten URL, Valid date life period]


For Development purpose Application provide Swagger-ui interface which can access through http://localhost:8060/swagger-ui.html

##Business assumption covered by exist code:

1) We have two types of user [Premium user, Anonymous user].
2) Premium User must login before use service or it will treat as Anonymous user which will give him extra time to save data.
3) Schedule Job run to delete old shorten url

**Future Addition:**

1) In case we need to convert work to complete Microservices, you will add Microservices discovery service [ex: Eureka] and attach your service to it.
2) For security scale can use JWT in replace to Spring security
3) In case of more scalability needed we can divide the the two main functionality in 2 separate Microservices one for each component and add nodes from each one of them according to the need.
4) Move environment configuration to standalone configuration server
5) In case heavy traffic can append to the domain name /Month_Number/ before 6 unique characters, or increase the number of 6 unique characters.
6) Replace domain URL with valid domain name Also use API gatway rather than direct access

##All parameters are configurable from properties file:

**URl parameters**
1) domain.name=http://localhost:8060/r/
2) shorten.length=6
3) expiry.period=30
4) expiry.shorter.url.hit=30
5) register.expiry.period=100
6) register.expiry.shorter.url.hit=100

##Development requirment:

1) JDK 8
2) MySQL
3) Change database username and password in properties files and test properties file

##Deployment requirment:

1) JRE 8
2) MySQL

##Deployment

1) Case Simple Deployment with attached jar just chage Change database username and password in properties files and sit it in the same folder of jar
2) run jar through command "java -jar shortenurl-1.0.jar"

##Logging

Log file create next to jar and properties file.

##Testing Application

1) Use swagger ui for test Anonymous user functionality [http://localhost:8060/swagger-ui.html]
    #### Note
    Redirect to [Original Url] Method not return the image in swagger ui while if you take the return from [Create an shortUrl] method and submit it in the browser it work fine.
    but if you want to test other error response you can pass only the unique Id as Parameter [ex: 8g3i24 ] not all url [ex: http://localhost:8060/r/8g3i24] in case of swagger only.


2) Use Postman for loggin, logout to use  Premium user functionality or just add basic authentication in url
   #### Note
   Use basic Authentication passing username and password and update the request before send it to access login user functionality.





 
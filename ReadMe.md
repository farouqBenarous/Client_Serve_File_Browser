# Client -> Server File Browser

## overview
The project is simple Client server APP allows you to Establish a Connection 
with some server specified by its IP adress  usig `Remote Method Invocation` Technology

Once YOu run the App you will be able to do File Browsing with some simple 
Operations like delete add copy ... 

## Tools
* Gradle builder
 
* IDE Intillij

* Remote method Invocation
## usage
 the Builder of this project is Gradle so to run it you should do like this
 
 * first you should change the path seted in the Gradle.proporites file 
 * second you run the server  by `Gradle server`
 * finally run the client by `Gradle client -PregistryAddress=127.0.0.1:1099` the adress should be set as its in the server
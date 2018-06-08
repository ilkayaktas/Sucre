# Sucre
Sucre is a diabetes application which has several features for diabetics that make their life easy. It’s a mobile platform which has an Android application and a backend server which keep data and run anomaly detection thread. The platform requires internet connection since the data is saved on cloud and anomaly detection process is running on remote server. In addition, Sucre provides an instant messaging service. The messages are transferred via Firebase Cloud Messaging service. 

![screens](./images/screens.png)

#### Attention!

If you want to use it, you should run [Message Transfer Server](https://github.com/ilkayaktas/MessageTransferServer) on your PC. Follow the instruction on [Message Transfer Server](https://github.com/ilkayaktas/MessageTransferServer). After you run it, change the IP in [build.gradle](./app/build.gradle) (build config field BACKEND_ENDPOINT).

```groovy
buildConfigField "String", "BACKEND_ENDPOINT", "\"http://192.168.1.26:8080/\""
```

You can find detailed article in [Medium](https://medium.com/@ilkayaktas/why-did-i-build-a-diabetes-management-platform-10e7423b8e82).


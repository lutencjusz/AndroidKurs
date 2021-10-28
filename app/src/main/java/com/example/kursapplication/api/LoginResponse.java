package com.example.kursapplication.api;

public class LoginResponse {

    public String username;
    public String objectId;
    public String email;
    public String FirstName;
    public String LastName;
    public String sessionToken;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "username='" + username + '\'' +
                ", objectId='" + objectId + '\'' +
                ", email='" + email + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }

    //    {
//        "objectId":"nmkwFarWKt", "username":"lutencjusz@gmail.com", "createdAt":
//        "2021-10-28T21:12:39.914Z", "updatedAt":"2021-10-28T21:15:25.521Z", "email":
//        "lutencjusz@gmail.com", "FirstName":"Michał", "LastName":"Kowalski", "ACL":{
//        "*":{
//            "read":true
//        },"nmkwFarWKt":{
//            "read":true, "write":true
//        }
//    },"sessionToken":"r:ece6596ac676a4217abeac9b10fc47f6"
//    }
}

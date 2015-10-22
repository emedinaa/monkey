# Monkey
Librería Http basada en Volley . "Es como usar Volley pero con sabor a retrofit"

![Image of Monkey](monkey.png)

## Introduction

```
public class ApiClient 
{
     private static MyApiInterface myApiInterface;
     public static MyApiInterface getApiInterface()
     {
         if(myApiInterface==null)
         {
             Monkey monkey = new Monkey.Builder()
                     .setContext(this)
                     .setEndpoint("https://api.parse.com/1/classes/Speaker")
                     .build();
             myApiInterface= monkey.create(MyApiInterface.class);
         }
         return myApiInterface;
     }
     
     public interface MyApiInterface
     {
         @MGET("/user_rest/login")
         void login(@Query("email") String email, Callback<Object> callback);
         
         @MPOST("/user-rest/register")
         void register(@Body Object raw,Callback<Object> callback);
     }
 }
 
```

## Download

## Maven

## Gradle
```
   repositories {
        maven { url "https://jitpack.io" }
    }
   
```
```
     compile 'com.github.emedinaa:monkey:v0.01'
```

## Licence

## References

Volley [https://developer.android.com/training/volley/index.html](https://developer.android.com/training/volley/index.html)

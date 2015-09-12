# monkey
Librería Http basada en Volley . "Es como Volley con sabor a retrofit"

[Volley](https://developer.android.com/training/volley/index.html)

```

public class ApiClient {
 
     private static MyApiInterface myApiInterface;
     public static MyApiInterface getApiInterface()
     {
         if(myApiInterface==null)
         {
             Monkey monkey = new Monkey.Builder()
                     .setEndpoint("https://api.parse.com/1/classes/Speaker")
                     .build();
             myApiInterface= monkey.create(MyApiInterface.class);
         }
         return myApiInterface;
     }
 
     public interface MyApiInterface
     {
         @GET("/user_rest/login")
         void login(@Query("email") String email, Callback<Object> callback);
 
         @POST("/user-rest/register")
         void register(@Body Object raw,Callback<Object> callback);
     }
 }
 
```

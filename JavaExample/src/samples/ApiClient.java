package samples;

import monkey.Callback;
import monkey.Monkey;
import monkey.http.Body;
import monkey.http.GET;
import monkey.http.POST;
import monkey.http.Query;

/**
 * Created by emedinaa on 12/09/15.
 */
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

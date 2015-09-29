import monkey.Callback;
import monkey.Monkey;
import monkey.Response;
import monkey.http.GET;
import monkey.http.POST;
import monkey.http.Query;
import patterns.User;
import samples.ApiClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InterfaceAddress;

/**
 * Created by emedinaa on 12/09/15.
 */
public class Main {

    public static void main(String[] args) {

        mensaje();
        Class myClass= Home.class;
        Annotation[] annotations = myClass.getAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                //sout
                System.out.println("value "+myAnnotation.value());
                System.out.println("name "+myAnnotation.name());
                System.out.println("age "+myAnnotation.age());
                System.out.println("newnames "+myAnnotation.newNames().toString());
            }
        }
        /*ApiClient.MyApiInterface myApiInterface= new ApiClient.MyApiInterface() {
            @Override
            public void login(@Query("email") String email, Callback<Object> callback) {

            }
        };*/
        Class api= ApiClient.MyApiInterface.class;
        for (Method method : api.getDeclaredMethods())
        {
            System.out.println("method "+method.getName());
            Annotation[] nAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : nAnnotations){
                if(annotation instanceof GET)
                {
                    GET myGET = (GET) annotation;
                    System.out.println("value "+myGET.value());
                }else if (annotation instanceof POST)
                {
                    POST myPOST = (POST) annotation;
                    System.out.println("value "+myPOST.value());
                }
            }
        }
       /* try {
            Method method= api.getDeclaredMethod("login",null);
            Annotation[] nAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : nAnnotations){
                if(annotation instanceof GET){
                    GET myGET = (GET) annotation;
                    System.out.println("value "+myGET.value());
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
        //patterns ---------------------------
        User user= new User.UserBuilder("Eduardo", "Medina")
                .age(30)
                .phone("123456")
                .address("Mi casa")
                .build();

        System.out.println("user "+user);
        /*Monkey monkey = new Monkey.Builder()
                            .setEndpoint("https://api.parse.com/1/classes/Speaker")
                            .build();*/

        ApiClient.getApiInterface().login("user001", new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @MarcarMetodo(descripcion = "primera anotacion")
    public static void mensaje() {
        System.out.println("hola desde un metodo anotado");

    }


}

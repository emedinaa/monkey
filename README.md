# Monkey
Librería Http basada en Volley . "Es como usar Volley pero con sabor a retrofit"

![Image of Monkey](monkey.png)

## Introduction

```
public class ApiClient 
{
     private static MyApiInterface myApiInterface;
     private static final String PATH="https://api.parse.com";
     
     public static MyApiInterface getApiInterface(Context context)
     {
         if(myApiInterface==null)
         {
             Monkey monkey = new Monkey.Builder()
                     .setContext(context)
                     .setEndpoint(PATH)
                     .build();
             myApiInterface= monkey.create(MyApiInterface.class);
         }
         return myApiInterface;
     }
     
     public interface MyApiInterface
     {
         @MGET("/1/classes/Type")
         void loadTypesPokemon(Callback<String> MCallback);
                 
         @MPOST("/1/classes/Pokemon")
         void addPokemon(@MBody Object json,Callback<String> MCallback);
         
         @MPUT("/1/classes/Pokemon/{objectId}")
         void updatePokemon(@MPath("objectId") String objectId,@MBody Object json,Callback<String> MCallback);
                 
         @MDELETE("/1/classes/Pokemon/{objectId}")
         void deletePokemon(@MPath("objectId") String objectId,Callback<String> MCallback);
     }
 }
 
```


### Headers 

Usar el tag @MHeaders
```
  @MHeaders({"X-Parse-Application-Id: TMEEmQ9ORjV2qnVmY5Z4WFSmfRSuzGLBmugDDDFfs","X-Parse-REST-API-Key: MZIKBgBRSVtt7EDhsXGtb6T6DEWFWFWDWDD"})
   
```

### Peticiones GET, POST , PUT , DELETE
```
     @MGET("/1/classes/Type")
     void loadTypesPokemon(Callback<String> MCallback);
   
```

```
     @MPOST("/1/classes/Pokemon")
     void addPokemon(@MBody Object json,Callback<String> MCallback);
   
```

```
     @MPUT("/1/classes/Pokemon/{objectId}")
     void updatePokemon(@MPath("objectId") String objectId,@MBody Object json,Callback<String> MCallback);
   
```

```
     @MDELETE("/1/classes/Pokemon/{objectId}")
     void deletePokemon(@MPath("objectId") String objectId,Callback<String> MCallback);
   
```

## Download

    Monkey versión 0.03Beta
    Mediante JitPack.io https://jitpack.io/#emedinaa/monkey

    [ARR Download ](https://github.com/emedinaa/monkey/arr/monkeyandroid.aar)
    
## Maven

## Gradle
```
   repositories {
        maven { url "https://jitpack.io" }
    }
   
```
```
     compile 'com.github.emedinaa:monkey:v0.03b'
```

## Licence

## References

Volley [https://developer.android.com/training/volley/index.html](https://developer.android.com/training/volley/index.html)

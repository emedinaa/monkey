package com.emedinaa.monkeyandroid;

/**
 * Created by emedinaa on 23/10/15.
 */
public class HeaderAnnotationParam {

    private boolean gotBody;
    private boolean gotPath;
    private boolean gotQuery;
    private String name;
    private Object value;
    private String pathValue;
    private String queryValue;

    public boolean isGotBody() {
        return gotBody;
    }

    public void setGotBody(boolean gotBody) {
        this.gotBody = gotBody;
    }

    public boolean isGotPath() {
        return gotPath;
    }

    public void setGotPath(boolean gotPath) {
        this.gotPath = gotPath;
    }

    public boolean isGotQuery() {
        return gotQuery;
    }

    public void setGotQuery(boolean gotQuery) {
        this.gotQuery = gotQuery;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathValue() {
        return pathValue;
    }

    public void setPathValue(String pathValue) {
        this.pathValue = pathValue;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    @Override
    public String toString() {
        return "HeaderParameters{" +
                "gotBody=" + gotBody +
                ", gotPath=" + gotPath +
                ", gotQuery=" + gotQuery +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", pathValue='" + pathValue + '\'' +
                ", queryValue='" + queryValue + '\'' +
                '}';
    }
}

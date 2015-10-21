package com.emedinaa.comovil2015.model.entity;

/**
 * Created by emedinaa on 21/09/15.
 */
public class SpeakerEntity {

    private String objectId;
    private String name;
    private String lastname;
    private String skill;
    private PhotoSpeaker photo;


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public PhotoSpeaker getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoSpeaker photo) {
        this.photo = photo;
    }

    public String getSimpleName()
    {
        return this.name+" "+this.lastname;
    }
    public String getPhotoPath()
    {
        String path="";
        if(this.photo!=null)
        {
            path=this.photo.getUrl();
        }
        return path;
    }

    @Override
    public String toString() {
        return "SpeakerEntity{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", skill='" + skill + '\'' +
                ", photo=" + photo +
                '}';
    }

    public class PhotoSpeaker
    {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "PhotoSpeaker{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}

package net.aung.moviemaniac.data.vos;

/**
 * Created by aung on 12/17/15.
 */
public class UserVO {

    public static final String JK_ID = "id";
    public static final String JK_NAME = "name";
    public static final String JK_GENDER = "gender";
    public static final String JK_EMAIL = "email";
    public static final String JK_DOB = "birthday";

    private String facebookId;
    private String profileUrl;
    private String coverUrl;

    private String name;
    private String gender;
    private String email;
    private String dateOfBirth;

    public UserVO() {

    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

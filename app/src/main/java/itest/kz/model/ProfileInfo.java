package itest.kz.model;

public class ProfileInfo
{
    private String firstname;
    private String surname;
    private String born_date;
    private String login;
    private String email;

    public ProfileInfo(String firstname, String surname, String born_date, String login, String email)
    {
        this.firstname = firstname;
        this.surname = surname;
        this.born_date = born_date;
        this.login = login;
        this.email = email;
    }
}

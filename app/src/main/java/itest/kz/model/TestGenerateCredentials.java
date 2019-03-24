package itest.kz.model;

import java.util.List;

public class TestGenerateCredentials
{
    private String tag;
    private String type;
    private String owner_id;

    public TestGenerateCredentials(String tag, String type, String owner_id)
    {
        this.tag = tag;
        this.type = type;
        this.owner_id = owner_id;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getOwner_id()
    {
        return owner_id;
    }

    public void setOwner_id(String owner_id)
    {
        this.owner_id = owner_id;
    }
}

package life.manong.community.dto;

/**
 * @authon GMY
 * @create 2020-05-23 13:47
 */
public class GiteeUser {
    private String name;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GiteeUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

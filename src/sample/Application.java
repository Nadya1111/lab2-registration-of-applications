package sample;

public class Application {
    private int id;
    private String description;
    private String status;
    private String performers;

    public Application(int id,
                       String description,
                       String status,
                       String performers) {
        this.description = description;
        this.id = id;
        this.performers = performers;
        this.status = status;
    }

    public String getPerformers() {
        return performers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

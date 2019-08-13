package saif.nidhi.udemyclone.models;

@SuppressWarnings("FieldCanBeLocal")
public class Course {

    private String author;
    private String course_code;
    private String difficulty;
    private String name;
    private Long videos;

    public Course(String author, String course_code, String difficulty, String name, Long videos) {
        this.author = author;
        this.course_code = course_code;
        this.difficulty = difficulty;
        this.name = name;
        this.videos = videos;
    }

    public Course() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVideos() {
        return videos;
    }

    public void setVideos(Long videos) {
        this.videos = videos;
    }
}

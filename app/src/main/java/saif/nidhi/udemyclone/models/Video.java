package saif.nidhi.udemyclone.models;

@SuppressWarnings("FieldCanBeLocal")
public class Video {

    private String download_url;
    private String name;
    private long number;
    private String storage_location;

    public Video(String download_url, String name, long number, String storage_location) {
        this.download_url = download_url;
        this.name = name;
        this.number = number;
        this.storage_location = storage_location;
    }

    public Video() {
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getStorage_location() {
        return storage_location;
    }

    public void setStorage_location(String storage_location) {
        this.storage_location = storage_location;
    }
}

package pe.edu.utp.sistemaimprenta.model;

public class Report {
    private String title;
    private String content;
    private ReportType type;

    public Report(String title, String content, ReportType type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }
}

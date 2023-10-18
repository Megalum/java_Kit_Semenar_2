package src.server;

public interface RepositoryView {
    public String readLog();
    void saveInLog(String text);
}

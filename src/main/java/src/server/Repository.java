package src.server;

import java.io.FileReader;
import java.io.FileWriter;

public class Repository implements RepositoryView{
    private String name;

    public Repository(String name){
        this.name = name;
    }

    @Override
    public String readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(name);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveInLog(String text) {
        try (FileWriter writer = new FileWriter(name, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

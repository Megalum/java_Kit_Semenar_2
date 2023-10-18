package src.server;

import src.client.Client;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final String LOG_PATH = "./src/main/java/src/server/log.txt";
    private List<Client> clientList;
    private ServerView serverView;
    Repository repository;
    private boolean status;

    public Server(ServerView serverView) {
        this.serverView = serverView;
        repository = new Repository(LOG_PATH);
        clientList = new ArrayList<>();
    }

    public boolean clientAdd(Client client){
        if (!status){
            return false;
        }
        clientList.add(client);
        return true;
    }

    public void clientDel(Client client){
        clientList.remove(client);
        if (client != null){
            client.disconnect();
        }
    }

    public void serverUp(){
        if (status){
            printText("Сервер уже был запущен");
        } else {
            status = true;
            printText("Сервер запущен!");
        }

    }

    public String getHistory(){
        return this.repository.readLog();
    }

    public void disconnectServer(){
        if (status) {
            status = false;
            int i = clientList.size() - 1;
            while (i != -1){
                clientDel(clientList.get(i));
                i--;
            }
            printText("Сервер остановлен!");
        }
        else {
            printText("Сервер уже был остановлен");
        }
    }

    private void printText(String text){
        serverView.showMessage(text);
    }

    public void sendMessage(String text){
        if (!status){
            return;
        }
        printText(text);
        answerAll(text);
        setHistory(text);
    }

    public void answerAll(String text){
        for (Client clientGUI: clientList){
            clientGUI.serverAnswer(text);
        }
    }

    public void setHistory(String text){
        repository.saveInLog(text);
    }
}

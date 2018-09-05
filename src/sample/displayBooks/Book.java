package sample.displayBooks;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

//I am going to create a class book so that i can give type book to arrylist which will be viewed in table view
public class Book
{
    private SimpleStringProperty title;
    private SimpleStringProperty  id;
    private SimpleStringProperty auther;
    private SimpleStringProperty publisher;
    private SimpleBooleanProperty availabilty;

    public Book(String id, String title, String auther, String publisher , Boolean avail)
    {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.auther = new SimpleStringProperty(auther);
        this.publisher =new SimpleStringProperty(publisher);
        this.availabilty = new SimpleBooleanProperty(avail);
    }

    public String getTitle() {
        return title.get();
    }



    public String getId() {
        return id.get();
    }


    public String getAuther() {
        return auther.get();
    }



    public String getPublisher() {
        return publisher.get();
    }


    public boolean isAvailabilty() {
        return availabilty.get();
    }


}

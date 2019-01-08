
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.apache.commons.io.FilenameUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class Question {

    
    private StringProperty question;
    private String deadline;
    private StringProperty a;
    private StringProperty b;
    private StringProperty c;
    private StringProperty d;
    private StringProperty e;
    
    private Image image;
    private int id;
    private boolean theory;
    private ObjectProperty<javafx.scene.image.Image> imageProperty = new SimpleObjectProperty<>();
    
    

    public void setImageProperty(Image value) {
       
        imageProperty.set(value);

    }
    
    public ObjectProperty<javafx.scene.image.Image> getImageProperty(){
        
        return imageProperty;
    }
    
    public Image getImage(){
        
        return imageProperty.get();
    }

    

    private Question() {

        question = new SimpleStringProperty();
        a = new SimpleStringProperty();
        b = new SimpleStringProperty();
        c = new SimpleStringProperty();
        d = new SimpleStringProperty();
        e = new SimpleStringProperty();
               

    }

    public StringProperty getQuestionProperty() {
        return question;
    }

    public void setQuestion(String value) {

        question.set(value);

    }

   
    public String getQuestion() {

        return question.get();
    }

    public StringProperty getAProperty() {
        return a;
    }

    private void setA(String value) {

        a.set(value);
    }

    public String getA() {

        return a.get();
    }

    public StringProperty getBProperty() {
        return b;
    }

    private void setB(String value) {
        b.set(value);

    }

    public String getB() {

        return b.get();
    }

    public StringProperty getCProperty() {
        return c;
    }

    private void setC(String value) {
        c.set(value);

    }

    public String getC() {

        return c.get();
    }

    public StringProperty getDProperty() {
        return d;
    }

    private void setD(String value) {
        d.set(value);

    }

    public String getD() {

        return d.get();
    }

    public StringProperty getEProperty() {
        return e;
    }

    private void setE(String value) {
        e.set(value);

    }

    public String getE() {

        return e.get();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean isTheory() {
        
        return a== null && b== null && c== null && d== null && e== null;
    }


    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public static class Builder {

        private String question;
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;

        public Builder(String question, String a, String b) {

            this.question = question;
            this.a = a;
            this.b = b;

        }

        public Builder addC(String c) {

            this.c = c;
            return this;
        }

        public Builder addD(String d) {

            this.d = d;
            return this;
        }

        public Builder addE(String e) {

            this.e = e;
            return this;
        }

        public Question build() {

            Question q = new Question();

            q.setQuestion(question);

            //q.question.setValue(a);
            q.setA(a);
            q.setB(b);
            q.setC(c);
            q.setD(d);
            q.setE(e);

            return q;
        }

    }

}

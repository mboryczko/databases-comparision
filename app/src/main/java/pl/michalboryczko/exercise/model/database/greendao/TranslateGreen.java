package pl.michalboryczko.exercise.model.database.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;


@Entity(indexes = {
        @Index(value = "english, spanish", unique = true)
})
public class TranslateGreen {

    @Id
    private Long id;
    private String english;
    private String spanish;
    private int timesAnsweredRight;
    private int timesAnsweredWrong;
    private boolean shouldBeLearned;

    @Generated(hash = 318482631)
    public TranslateGreen(Long id, String english, String spanish,
            int timesAnsweredRight, int timesAnsweredWrong,
            boolean shouldBeLearned) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.timesAnsweredRight = timesAnsweredRight;
        this.timesAnsweredWrong = timesAnsweredWrong;
        this.shouldBeLearned = shouldBeLearned;
    }

    @Generated(hash = 1046303701)
    public TranslateGreen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getSpanish() {
        return spanish;
    }

    public void setSpanish(String spanish) {
        this.spanish = spanish;
    }

    public int getTimesAnsweredRight() {
        return timesAnsweredRight;
    }

    public void setTimesAnsweredRight(int timesAnsweredRight) {
        this.timesAnsweredRight = timesAnsweredRight;
    }

    public int getTimesAnsweredWrong() {
        return timesAnsweredWrong;
    }

    public void setTimesAnsweredWrong(int timesAnsweredWrong) {
        this.timesAnsweredWrong = timesAnsweredWrong;
    }

    public boolean isShouldBeLearned() {
        return shouldBeLearned;
    }

    public void setShouldBeLearned(boolean shouldBeLearned) {
        this.shouldBeLearned = shouldBeLearned;
    }

    public boolean getShouldBeLearned() {
        return this.shouldBeLearned;
    }
}

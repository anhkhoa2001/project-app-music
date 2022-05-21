package uet.myboot.donation.models;

import javax.persistence.*;

@Entity
@Table(name = "Donation")
public class Donation {

    @Id
    @Basic
    private int id;

    @Basic
    private String method;

    @Basic
    private int  amount;

    @Basic
    private int upvotes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }
}

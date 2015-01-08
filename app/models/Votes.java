package models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * The VOTE entity for the 'Game-On' app. Holds all information relevant to a
 * vote on a game.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
@Entity
@Table(name = "VOTES")
public class Votes extends Model {

    private static final long serialVersionUID = -113825232060148557L;

    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game game;

    @Column(name = "VOTE_CREATED", nullable = true)
    private Timestamp created;

    public Votes(Timestamp created, Game game) {
	super();
	this.game = game;
	this.created = created;
    }

    public Votes() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Timestamp getCreated() {
	return created;
    }

    public void setCreated(Timestamp created) {
	this.created = created;
    }

    @Override
    public String toString() {
	return "Votes Id: " + id + " Game: " + game + " Created: " + created;
    }

    /**
     * The finder to help with finding a given vote from the Ebean framework.
     */
    public static Finder<Long, Votes> find = new Finder<Long, Votes>(
	    Long.class, Votes.class);

}

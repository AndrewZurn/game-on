package models;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

/**
 * The Game entity for the 'Game-On' app. Holds all information relevant to a
 * game.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
@Entity
@Table(name = "GAME")
public class Game extends Model implements Comparator<Game>, Comparable<Game> {

    private static final long serialVersionUID = 8993550994659981414L;

    @Id
    @GeneratedValue
    @Column(name = "GAME_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "GAME_NAME", length = 128, unique = true, nullable = false)
    private String name;

    @Column(name = "DETAILS_URL", nullable = true)
    private String detailsUrl;

    @Column(name = "IMAGE_URL", nullable = true)
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "OWNED", nullable = false)
    private boolean owned;

    @Column(name = "CREATED", nullable = true)
    private Timestamp timestamp;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<Votes> votes;

    public Game(String name, String detailsUrl, String imageUrl,
	    Category category, boolean owned, Timestamp timestamp) {
	super();
	this.name = name;
	this.detailsUrl = detailsUrl;
	this.imageUrl = imageUrl;
	this.category = category;
	this.owned = owned;
	this.timestamp = timestamp;
    }

    public Game() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDetailsUrl() {
	return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
	this.detailsUrl = detailsUrl;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = category;
    }

    public boolean isOwned() {
	return owned;
    }

    public void setOwned(boolean owned) {
	this.owned = owned;
    }

    public Timestamp getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
	this.timestamp = timestamp;
    }

    public Set<Votes> getVotes() {
	return votes;
    }

    public void setVotes(Set<Votes> votes) {
	this.votes = votes;
    }

    @Override
    public String toString() {
	return "Game [id=" + id + ", name=" + name + ", detailsUrl="
		+ detailsUrl + ", imageUrl=" + imageUrl + ", category="
		+ category + ", owned=" + owned + ", timestamp=" + timestamp
		+ ", votes=" + votes + "]";
    }

    /**
     * The finder to help with finding a given game from the Ebean framework.
     */
    public static Finder<Long, Game> find = new Finder<Long, Game>(Long.class,
	    Game.class);

    public int compareTo(Game otherGame) {
	    return getVotes().size() - otherGame.getVotes().size();
    }

    public int compare(Game gameOne, Game gameTwo) {
	return gameOne.getVotes().size() - gameTwo.getVotes().size();
    }

}

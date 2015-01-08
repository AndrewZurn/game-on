package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * The CATEGORY entity for the 'Game-On' app. Holds all information relevant to
 * a category of a game.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
@Entity
@Table(name = "CATEGORY")
public class Category extends Model {

    private static final long serialVersionUID = -6025164001213247187L;

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "CATEGORY_NAME", length = 32, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Game> games;

    public Category(String name, Set<Game> games) {
	super();
	this.name = name;
	this.games = games;
    }

    public Category() {

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

    public Set<Game> getGames() {
	return games;
    }

    public void setGames(Set<Game> games) {
	this.games = games;
    }

    @Override
    public String toString() {
	return "Category Id: " + this.id + " Name: " + this.name;
    }

    /**
     * The finder to help with finding a given category from the Ebean
     * framework.
     */
    public static Finder<Long, Category> find = new Finder<Long, Category>(
	    Long.class, Category.class);

}

package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.lambdaschool.foundation.Utility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name = "users")
public class User
    extends Auditable
{
    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @NotNull
    private String username;

    /**
     * minimum population preference
     */
    private Integer minPopulation;

    /**
     * maximum population preference
     */
    private Integer maxPopulation;

    /**
     * minimum rent preference
     */
    private Double minRent;

    /**
     * maximum rent preference
     */
    private Double maxRent;

    /**
     * minimum house cost preference
     */
    private Double minHouseCost;

    /**
     * maximum house cost preference
     */
    private Double maxHouseCost;

    /**
     * cost of living preference
     */
    private Integer costOfLiving;

    /**
    *The list that holds the users favorite cities
    */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<UserCities> favoriteCities = new ArrayList<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public User()
    {
    }

    /**
     * Given the params, create a new user object
     * <p>
     * userid is autogenerated
     *
     * @param username The username (String) of the user
     */
    public User(String username)
    {
        setUsername(username);
    }

    public User(long userId, @NotNull String username, Integer minPopulation,
                Integer maxPopulation, Double minRent, Double maxRent,
                Double minHouseCost, Double maxHouseCost, Integer costOfLiving) {
        this.userId = userId;
        this.username = username;
        this.minPopulation = minPopulation;
        this.maxPopulation = maxPopulation;
        this.minRent = minRent;
        this.maxRent = maxRent;
        this.minHouseCost = minHouseCost;
        this.maxHouseCost = maxHouseCost;
        this.costOfLiving = costOfLiving;
    }

    /**
     * Getter for userid
     *
     * @return the userid (long) of the user
     */
    public long getUserId()
    {
        return userId;
    }

    /**
     * Setter for userid. Used primary for seeding data
     *
     * @param userid the new userid (long) of the user
     */
    public void setUserId(long userid)
    {
        this.userId = userid;
    }

    /**
     * Getter for username
     *
     * @return the username (String) lowercase
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * setter for username
     *
     * @param username the new username (String) converted to lowercase
     */
    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

    /**
     *
     * getter and setters for user's fav cities
     */

    public List<UserCities> getFavoriteCities()
    {
        return favoriteCities;
    }

    public void setFavoriteCities(List<UserCities> favoriteCities)
    {
        this.favoriteCities = favoriteCities;
    }

    /**
     * ToString override method
     */
    @Override
    public String toString()
    {
        return "User{" +
            "userid=" + userId +
            ", username='" + username + '\'' +
            ", favCities=" + favoriteCities +
            '}';
    }

    public Integer getMinPopulation() {
        return minPopulation;
    }

    public void setMinPopulation(Integer minPopulation) {
        this.minPopulation = minPopulation;
    }

    public Integer getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(Integer maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public Double getMinRent() {
        return minRent;
    }

    public void setMinRent(Double minRent) {
        this.minRent = minRent;
    }

    public Double getMaxRent() {
        return maxRent;
    }

    public void setMaxRent(Double maxRent) {
        this.maxRent = maxRent;
    }

    public Double getMinHouseCost() {
        return minHouseCost;
    }

    public void setMinHouseCost(Double minHouseCost) {
        this.minHouseCost = minHouseCost;
    }

    public Double getMaxHouseCost() {
        return maxHouseCost;
    }

    public void setMaxHouseCost(Double maxHouseCost) {
        this.maxHouseCost = maxHouseCost;
    }

    public Integer getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(Integer costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    /**
     * Update User with fields from a partially complete User object. Fields not
     * completed in the new Object
     * @param data JSON data with new values
     */
    public void update(JsonNode data) {
        minPopulation = Utility.optionallyReplace(
                minPopulation, data, "minPopulation");
        maxPopulation = Utility.optionallyReplace(
                maxPopulation, data, "maxPopulation");
        minRent = Utility.optionallyReplace(
                minRent, data, "minRent");
        maxRent = Utility.optionallyReplace(
                maxRent, data, "maxRent");
        minHouseCost = Utility.optionallyReplace(
                minHouseCost, data, "minHouseCost");
        maxHouseCost = Utility.optionallyReplace(
                maxHouseCost, data, "maxHouseCost");
        costOfLiving = Utility.optionallyReplace(
                costOfLiving, data, "costOfLiving");
    }

//     Keeping this commented out code for future feature User Authentication
//     Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
//     Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
//      @return The list of authorities, roles, this user object has
//
//    @JsonIgnore
//    public List<SimpleGrantedAuthority> getAuthority()
//    {
//        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();
//
//        for (UserRoles r : this.roles)
//        {
//            String myRole = "ROLE_" + r.getRole()
//                .getName()
//                .toUpperCase();
//            rtnList.add(new SimpleGrantedAuthority(myRole));
//        }
//
//        return rtnList;
//    }
}

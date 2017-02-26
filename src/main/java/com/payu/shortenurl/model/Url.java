/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payu.shortenurl.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Entity
@Table(name = "urls")
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "hit_count")
    private Integer hitCount;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(length = 500, name = "shorten_url")
    private String shortenUrl;
    @Column(length = 1000, name = "url")
    private String url;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private User user;

    public Url() {
        //No argument constructor
    }

    public Url(String shortenUrl, String url) {
        this.shortenUrl = shortenUrl;
        this.url = url;
        this.creationDate = new Date();
        this.hitCount = 0;
    }

    public Url(String shortenUrl, String url, User user) {
        this.shortenUrl = shortenUrl;
        this.url = url;
        this.user = user;
        this.creationDate = new Date();
        this.hitCount = 0;
    }

    public Url(Integer hitCount, Date creationDate, String shortenUrl, String url, User user) {
        this.hitCount = hitCount;
        this.creationDate = creationDate;
        this.shortenUrl = shortenUrl;
        this.url = url;
        this.user = user;
    }

    public Url(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public void setShortenUrl(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void incrementHitCount() {
        this.hitCount++;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Url)) {
            return false;
        }
        Url other = (Url) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shorten_url[ id=" + id + " ]";
    }
}

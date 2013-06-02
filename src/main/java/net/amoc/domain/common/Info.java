package net.amoc.domain.common;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Atiqur Rahman
 * @since 01/06/2013 12:53 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Info extends Persistent {

    private int id;
    private String text;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 20)    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

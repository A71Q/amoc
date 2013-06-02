package net.amoc.domain.common;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Base class for all persistent objects in the database.
 * Contains and automatically sets the created and updated columns.
 */
@MappedSuperclass
public abstract class Persistent implements Serializable {

    private Date created;

    private Date updated;

    @Version
    private long version;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date d) {
        created = d;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date d) {
        updated = d;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long v) {
        version = v;
    }
}

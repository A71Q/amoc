package net.amoc.domain.common;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class PersistentInterceptor extends EmptyInterceptor {
    private static final String PROP_CREATED = "created";
    private static final String PROP_UPDATED = "updated";

    /**
     * Set <code>create</coded> and <code>updated</code> time stamps when creating a new entity.
     */
    @Override
    public boolean onSave(Object entity, Serializable id,
                          Object[] state, String[] propertyNames, Type[] types) {
        boolean modified = false;

        if (entity instanceof Persistent) {
            Date d = new Date();
            Timestamp ts = new Timestamp(d.getTime());

            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(PROP_CREATED) || propertyNames[i].equals(PROP_UPDATED)) {
                    state[i] = ts;
                    modified = true;
                }
            }
        }
        return modified;
    }

    /**
     * Set <code>updated</code> time stamps when updating an entity.
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
                                Object[] currentState,
                                Object[] previousState, String[] propertyNames, Type[] types) {

        if (entity instanceof Persistent) {
            Date d = new Date();
            Timestamp ts = new Timestamp(d.getTime());

            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(PROP_UPDATED)) {
                    currentState[i] = ts;
                    return true;
                }
            }
        }
        return false;
    }
}

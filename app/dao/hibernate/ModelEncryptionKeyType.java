package dao.hibernate;

import encryption.ModelEncryptionKey;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.MaterializedBlobType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ModelEncryptionKeyType implements UserType {

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        byte[] encryptedKey = rs.getBytes(names[0]);
        if (!rs.wasNull()) {
            return new ModelEncryptionKey(encryptedKey);
        } else {
            return null;
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (null == value) {
            st.setNull(index, MaterializedBlobType.INSTANCE.sqlType());
        } else {
            ModelEncryptionKey modelEncryptionKey = (ModelEncryptionKey) value;
            st.setBytes(index, modelEncryptionKey.getEncryptedKey());
        }
    }

    /**
     * Method tells Hibernate which Java class is mapped to this Hibernate Type
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return ModelEncryptionKey.class;
    }

    /**
     * Method tells Hibernate what SQL columns to use for DDL schema generation.
     * using the Hibernate Types leaves Hibernate free to choose actual SQl types
     * based on database dialect.
     * (Alternatively SQL types can also be used directly)
     */
    @Override
    public int[] sqlTypes() {
        return new int[] { MaterializedBlobType.INSTANCE.sqlType() };
    }

   /*
    * The following methods assume Hibernate is only concerned with the encrypted key
    * (the unencrypted key and keyPair are not persisted).
    * #deepCopy along with #getEncryptedKey is used to ensure the encrypted key is populated.
    */

    /**
     * Helps hibernate apply certain optimizations for immutable objects
     */
    @Override
    public boolean isMutable() {
        return true;
    }

    /**
     * Method used by Hibernate to handle merging of detached object.
     */
    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return deepCopy(original);
    }

    /**
     * Returns the object from the 2 level cache
     */
    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        //Do not use deepCopy as the serialized object
        return cached;
    }

    /**
     * method called when Hibernate puts the data in a second level cache. The data is stored
     * in a serializable form
     */
    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    /**
     * Used to create Snapshots of the object
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return new ModelEncryptionKey(((ModelEncryptionKey)value).getEncryptedKey());
    }

    @Override
    public boolean equals(final Object o1, final Object o2) throws HibernateException {
        boolean isEqual = false;
        if (o1 == o2) {
            isEqual = true;
        }
        if (null == o1 || null == o2) {
            isEqual = false;
        } else {
            isEqual = Arrays.equals(((ModelEncryptionKey) o1).getEncryptedKey(), (((ModelEncryptionKey)o2).getEncryptedKey()));
        }
        return isEqual;
    }

    @Override
    public int hashCode(final Object value) throws HibernateException {
        return Arrays.hashCode(((ModelEncryptionKey)value).getEncryptedKey());
    }

}

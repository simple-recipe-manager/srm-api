package ly.whisk.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a property that's backed by another table
 * 
 * @author breland
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoteTable {

	public Class<? extends RemotedTable> inflationClass() default RemotedTable.class;
}

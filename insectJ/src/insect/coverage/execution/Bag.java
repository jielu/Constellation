package insect.coverage.execution;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Bag.java
 *
 * Provides a collection to keep track of the
 * number of each particular item added.
 *
 * Created: Tue Jan 21 16:10:45 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class Bag extends AbstractCollection implements Collection {

	/**
	 * HashMap providing a backing for this bag.
	 */
	private HashMap bag;

	/**
	 * Creates a new <code>Bag</code> instance.
	 *
	 */
	public Bag() {
		bag = new HashMap();
	}

	/**
	 * Adds the specified object to the bag.
	 *
	 * @param o object to add.
	 * @return false if the bag already contains the object, otherwise true.
	 */
	public boolean add(Object o) {
		Long val = (Long) bag.get(o);
		if (val == null) {
			bag.put(o, new Long(1));
			return false;
		} else {
			bag.put(o, new Long(val.longValue() + 1));
			return true;
		}
	}

	/**
	 * Determines the number of the specified
	 * item contained in this bag
	 *
	 * @return a nonnegative value
	 */
	public long countItem(Object o) {
		Long val = (Long) bag.get(o);
		if (val == null)
			return 0;
		else
			return val.longValue();
	}

	/**
	 * Returns the number of unique
	 * items in this bag
	 *
	 * @return size of bag
	 */
	public int size() {
		return bag.size();
	}

	/**
	 * Returns an iterator over the items
	 * in this bag
	 *
	 * @return an Iterator
	 */
	public Iterator iterator() {
		return bag.keySet().iterator();
	}

} // Bag

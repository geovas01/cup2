package edu.tum.cup2.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

//TODO: doc
public class CollectionTools
{
	
	
	/**
	 * Creates a linked list.
	 */
	public static <T1> LinkedList<T1> llist()
	{
		return new LinkedList<T1>();
	}
	
	
	/**
	 * Creates a new map.
	 */
	public static <T1, T2> HashMap<T1, T2> map()
	{
		return new HashMap<T1, T2>();
	}
	
	
	/**
	 * Creates a new set.
	 */
	public static <T> HashSet<T> set()
	{
		return new HashSet<T>();
	}
	
	
	/**
	 * Adds the given element to the given set
	 * and returns it. If the given set is null,
	 * a new set is created and returned.
	 */
	public static <T> Set<T> add(Set<T> set, T e)
	{
		if (set == null)
		{
			Set<T> ret = new HashSet<T>();
			ret.add(e);
			return ret;
		}
		else
		{
			set.add(e);
			return set;
		}
	}


}

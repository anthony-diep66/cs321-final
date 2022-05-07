package cs321.common;
import java.util.LinkedList;

public class Cache <T>
{
	
	private LinkedList<T> cache; 
	private int size;
	
	public Cache(int size)
	{
		this.size = size;
		this.cache = new LinkedList<T>();
	}
	
	public LinkedList<T> getObject()
	{
		return this.cache;
	}
	
	public void addObject(T e)
	{
		if(cache.size() == size)
		{
			cache.removeLast();
		}
		cache.addFirst(e);
	}
	
	public void removeObject(T e)
	{
		this.cache.remove(e);
	}
	
	public void clearCache()
	{
		this.cache.clear();
	}
	
}

public interface Collections {
    public Iterator createIterator();
    public void add(Object key, Object o);
    public Object get(Object key);
    public boolean hasKey(Object key);
    public int size();
}

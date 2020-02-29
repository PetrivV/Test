package sample;

public interface Specification<T>
{
    boolean isSatisfied(T item);
}

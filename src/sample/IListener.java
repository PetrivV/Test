package sample;

public interface IListener
{
    enum Action {Add, Update, Remove};
    void update(Object j, Action a);
}

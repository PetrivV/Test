package sample;

import java.util.ArrayList;


class SubjectBase<ObserverType extends IListener>
{
    protected ArrayList<ObserverType> observers = new ArrayList <> ();

    public SubjectBase()
    {
    }

    public void registerObserver(ObserverType observer)
    {
        if (observer != null)
            observers.add (observer);
    }

    public void unregisterObserver(ObserverType observer)
    {
        observers.remove ( observer );
    }

    public void updateAll(Object object, IListener.Action action)
    {
        observers.forEach ( (observer) ->{
            observer.update ( object, action );
        } );
    }
}



abstract class Observer<ObservableType extends SubjectBase> implements IListener
{
    private ObservableType observable;

    public Observer(ObservableType obs)
    {
        observable = obs;
        register();
    }

    private void register()
    {
        observable.registerObserver ( this );
    }
}

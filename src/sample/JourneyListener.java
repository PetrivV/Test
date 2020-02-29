package sample;

public interface JourneyListener {

    enum Action {Add, Update, Remove};

    void updateJourney(Journey j, Action a );

}

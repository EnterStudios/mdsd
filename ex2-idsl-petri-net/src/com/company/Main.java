package com.company;

import com.company.petrinet.Arc;
import com.company.petrinet.PetriNet;
import com.company.petrinet.Place;
import com.company.petrinet.Transition;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) {

        final PetriNet myPetriNet = PetriNet.create("Wikipedia Beispiel")
                .addPlace("s1")
                  .initWithTokens(9)
                    .withOutgoingTransition("t")
                    .andCost(2)
                  .and()
                    .withIngoingTransition("t0")
                    .andCost(9)
                .addPlace("s2")
                  .initWithTokens(1)
                    .withOutgoingTransition("t")
                    .andCost(1)
                .addPlace("s3")
                  .initWithTokens(1)
                    .withIngoingTransition("t")
                    .andCost(1)
                .addPlace("s4")
                  .initWithTokens(0)
                    .withIngoingTransition("t")
                    .andCost(3)
                .end();

        try {
            myPetriNet.validate();
            describe(myPetriNet);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    private static void describe(PetriNet net) {
        printf("Petri net name: '%s'", net.getName());
        printf("Number of places: %d", net.getPlaces().size());
        printf("Sum of tokens: %d", net.getPlaces().stream().map(Place::getTokens).reduce(0, (x, y) -> x + y));
        net.getPlaces().forEach(Main::describe);
        net.getTransitions().forEach(Main::describe);
        net.getArcs().forEach(Main::describe);
    }

    private static void describe(Place place) {
        printf("Place %s has %d initial tokens", place.getName(), place.getTokens());
        printf("Place %s has %d ingoing transactions", place.getName(), place.getIngoingTransitions().size());
        printf("Place %s has %d outgoing transactions", place.getName(), place.getOutgoingTransitions().size());
    }

    private static void describe(Transition transition) {
        printf("Transition %s has %d ingoing places", transition.getName(), transition.getIngoingPlaces().size());
        printf("Transition %s has %d outgoing places", transition.getName(), transition.getOutgoingPlace().size());
    }

    private static void describe(Arc arc) {
        final String arcType = arc.isIngoing() ? "Ingoing " : "Outgoing";
        printf("%s arc connecting place %s with Transition %s", arcType, arc.getPlace().getName(), arc.getTransition().getName());
    }

    private static void printf(String s, Object... args) {
        String formatted = String.format(s, args);
        System.out.println(formatted);
    }

}

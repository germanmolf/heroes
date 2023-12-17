package com.example.heroes.villains.domain;

import com.example.heroes.heroes.domain.HeroId;
import com.example.heroes.villains.application.create.CreateVillainRequest;

import java.util.ArrayList;
import java.util.List;

public final class VillainMother {

    private static Villain create(String id, String name, String power) {
        return new Villain(id, name, power);
    }

    public static Villain random() {
        return create(VillainIdMother.random().value(), VillainNameMother.random().value(),
                VillainPowerMother.random().value());
    }

    public static Villain fromRequest(CreateVillainRequest request) {
        return create(request.id(), request.name(), request.power());
    }

    public static Villain create(String name, String power) {
        return create(VillainIdMother.random().value(), name, power);
    }

    public static Villain create(String id) {
        return new Villain(id, VillainNameMother.random().value(), VillainPowerMother.random().value());
    }

    public static Villain updatingHeroesDefeated(Villain villain, HeroId heroId) {
        List<HeroId> heroIds = new ArrayList<>(villain.heroesDefeated());
        heroIds.add(heroId);
        List<String> heroesDefeated = heroIds.stream().map(HeroId::value).toList();

        return new Villain(villain.id().value(), villain.name().value(), villain.power().value(),
                heroesDefeated, heroesDefeated.size());
    }
}

package com.example.heroes.villains.infraestructure.controller;

import com.example.heroes.shared.application.UnitTestModule;
import com.example.heroes.shared.domain.CriteriaMother;
import com.example.heroes.shared.domain.criteria.Criteria;
import com.example.heroes.villains.application.find.VillainFinder;
import com.example.heroes.villains.application.find.VillainSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class VillainGetControllerCriteriaFieldsTest extends UnitTestModule {

    private VillainGetController controller;
    private VillainSearcher searcher;
    private ArgumentCaptor<Criteria> argument;

    @BeforeEach
    protected void setUp() {
        VillainFinder finder = mock(VillainFinder.class);
        searcher = mock(VillainSearcher.class);
        controller = new VillainGetController(finder, searcher);
        argument = ArgumentCaptor.forClass(Criteria.class);
    }

    @Test
    void allows_filter_by_name() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("name", "contains", "value");

        controller.getVillains(params);

        shouldFilterBy("name");
    }

    @Test
    void allows_filter_by_power() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("power", "contains", "value");

        controller.getVillains(params);

        shouldFilterBy("power");
    }

    @Test
    void allows_filter_by_heroes_defeated_total() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("heroesDefeatedTotal", "contains",
                "value");

        controller.getVillains(params);

        shouldFilterBy("heroesDefeatedTotal");
    }

    @Test
    void allows_filter_by_status() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("status", "contains", "value");

        controller.getVillains(params);

        shouldFilterBy("status");
    }

    @Test
    void allows_filter_by_hero_defeater() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("heroDefeater", "contains", "value");

        controller.getVillains(params);

        shouldFilterBy("heroDefeater");
    }

    @Test
    void ignore_not_allowed_filter() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("qwe", "contains", "value");

        controller.getVillains(params);

        shouldNoFilterBy("qwe");
    }

    @Test
    void allows_order_by_name() {
        Map<String, String> params = CriteriaMother.createParamsWithOrder("name", "asc");

        controller.getVillains(params);

        shouldOrderBy("name");
    }

    @Test
    void allows_order_by_power() {
        Map<String, String> params = CriteriaMother.createParamsWithOrder("power", "asc");

        controller.getVillains(params);

        shouldOrderBy("power");
    }

    @Test
    void allows_order_by_heroes_defeated_total() {
        Map<String, String> params = CriteriaMother.createParamsWithOrder("heroesDefeatedTotal", "asc");

        controller.getVillains(params);

        shouldOrderBy("heroesDefeatedTotal");
    }

    @Test
    void allows_order_by_status() {
        Map<String, String> params = CriteriaMother.createParamsWithOrder("status", "asc");

        controller.getVillains(params);

        shouldOrderBy("status");
    }

    @Test
    void ignore_not_allowed_orderBy() {
        Map<String, String> params = CriteriaMother.createParamsWithFilter("qwe", "contains", "value");

        controller.getVillains(params);

        shouldNotOrderBy("qwe");
    }

    private void shouldFilterBy(String field) {
        verify(searcher).search(argument.capture());
        assertThat(argument.getValue().getFilters(), contains(hasProperty("field", is(field))));
    }

    private void shouldNoFilterBy(String field) {
        verify(searcher).search(argument.capture());
        assertThat(argument.getValue().getFilters(), not(contains(hasProperty("field", is(field)))));
    }

    private void shouldOrderBy(String orderBy) {
        verify(searcher).search(argument.capture());
        assertThat(argument.getValue().getOrder().getOrderBy(), is(orderBy));
    }

    private void shouldNotOrderBy(String orderBy) {
        verify(searcher).search(argument.capture());
        assertThat(argument.getValue().getOrder().getOrderBy(), is(not(orderBy)));
    }
}

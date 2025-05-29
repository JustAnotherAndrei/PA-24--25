package cityapp.solver;

import cityapp.entity.City;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.*;
import java.util.stream.Collectors;

public class CityConstraintSolver {
    public static List<City> solve(List<City> allCities, int minPopulation, int maxPopulation) {
        Model model = new Model("City Solver");

        int n = allCities.size();
        IntVar[] selected = model.boolVarArray("selected", n);

        IntVar totalPopulation = model.intVar("totalPop", minPopulation, maxPopulation);
        int[] populations = allCities.stream().mapToInt(City::getPopulation).toArray();

        model.scalar(selected, populations, "=", totalPopulation).post();

        for (char c = 'A'; c <= 'Z'; c++) {
            final char finalC = c;
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (allCities.get(i).getName().toUpperCase().startsWith(String.valueOf(finalC))) {
                    indices.add(i);
                }
            }

            if (indices.size() < 2) continue;

            for (int i = 0; i < indices.size(); i++) {
                for (int j = i + 1; j < indices.size(); j++) {
                    String ci = allCities.get(indices.get(i)).getCountry();
                    String cj = allCities.get(indices.get(j)).getCountry();
                    if (ci.equalsIgnoreCase(cj)) {
                        model.arithm(selected[indices.get(i]], "+", selected[indices.get(j)], "<", 2).post();
                    }
                }
            }
        }

        if (model.getSolver().solve()) {
            return IntStream.range(0, n)
                    .filter(i -> selected[i].getValue() == 1)
                    .mapToObj(allCities::get)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}

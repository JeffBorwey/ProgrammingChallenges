package atomcount;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public String countOfAtoms(String formula) {
        Map<String, Integer> atomCount = new HashMap<>();
        atomCounts(formula.toCharArray(), atomCount, 0);
        StringBuilder output = new StringBuilder();
        String[] names = new String[atomCount.size()];
        names = atomCount.keySet().toArray(names);
        Arrays.sort(names);
        for (String atom : names) {
            output.append(atom);
            Integer count = atomCount.get(atom);
            if (count > 1) {
                output.append(count);
            }
        }
        return output.toString();
    }

    public int atomCounts(char[] formula, Map<String, Integer> counts, int start) {
        while (start < formula.length) {
            char currentChar = formula[start++];
            if (currentChar == '(') {
                Map<String, Integer> subCounts = new HashMap<>();
                start = atomCounts(formula, subCounts, start);
                //Get count
                int count = 0;
                while (start < formula.length) {
                    if (formula[start] >= '0' && formula[start] <= '9') {
                        count = count * 10 + formula[start++] - '0';
                    } else {
                        break;
                    }
                }
                int multiplier = count == 0 ? 1 : count;

                //Merge?
                for (Map.Entry<String, Integer> atom : subCounts.entrySet()) {
                    int oldValue = counts.getOrDefault(atom.getKey(), 0);
                    counts.put(atom.getKey(), multiplier * atom.getValue() + oldValue);
                }
            } else if (currentChar == ')') {
                //Get counts and multiply?
                return start++;
            } else if (currentChar >= 'A' && currentChar <= 'Z') {
                StringBuilder atomName = new StringBuilder();
                atomName.append(currentChar);
                while (start < formula.length) {
                    if (formula[start] >= 'a' && formula[start] <= 'z') {
                        atomName.append(formula[start++]);
                    } else {
                        break;
                    }
                }

                //Get the count
                int count = 0;
                while (start < formula.length) {
                    if (formula[start] >= '0' && formula[start] <= '9') {
                        count = count * 10 + formula[start++] - '0';
                    } else {
                        break;
                    }
                }
                int multiplier = count == 0 ? 1 : count;
                String nameString = atomName.toString();
                int oldValue = counts.getOrDefault(nameString, 0);
                counts.put(nameString, oldValue + multiplier);
            }
        }
        return start;
    }
}

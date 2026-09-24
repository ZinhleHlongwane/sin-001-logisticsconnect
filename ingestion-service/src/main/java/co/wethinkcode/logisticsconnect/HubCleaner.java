package co.wethinkcode.logisticsconnect;

public class HubCleaner {

    public String cleanId(String value) {
        if (isMissingValue(value)) {
            return null;
        }

        return value.trim().toUpperCase();
    }

    public String cleanProvince(String value) {
        if (isMissingValue(value)) {
            return null;
        }

        String cleaned = collapseSpaces(value.trim());

        if (cleaned.equalsIgnoreCase("Kwa-Zulu Natal")
            || cleaned.equalsIgnoreCase("KwaZulu Natal")
                || cleaned.equalsIgnoreCase("KwaZulu-Natal")) {
            return "KwaZulu-Natal";
        }

        return toTitleCase(cleaned);
    }

    public String cleanSortingCenter(String value) {
        if (isMissingValue(value)) {
            return null;
        }

        return toTitleCase(collapseSpaces(value.trim()));
    }

    public Boolean cleanActiveFlag(String value) {
        if (isMissingValue(value)) {
            return null;
        }

        String cleaned = value.trim().toLowerCase();

        if (cleaned.equals("y")
                || cleaned.equals("yes")
                || cleaned.equals("true")
                || cleaned.equals("1")) {
            return true;
        }

        if (cleaned.equals("n")
                || cleaned.equals("no")
                || cleaned.equals("false")
                || cleaned.equals("0")) {
            return false;
        }

        return null;
    }

    private boolean isMissingValue(String value) {
        if (value == null) {
            return true;
        }

        String cleaned = value.trim();

        return cleaned.isEmpty()
                || cleaned.equalsIgnoreCase("n/a")
                || cleaned.equalsIgnoreCase("tbd")
                || cleaned.equalsIgnoreCase("unknown")
                || cleaned.equals("-")
                || cleaned.equalsIgnoreCase("nan");
    }

    private String collapseSpaces(String value) {
        return value.replaceAll("\\s+", " ");
    }

    private String toTitleCase(String value) {
        String[] words = value.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }

            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));
        }

        return result.toString();
    }
}

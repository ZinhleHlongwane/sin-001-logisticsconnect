package co.wethinkcode.logisticsconnect;

public class Hub {

    private final String id;
    private final String province;
    private final String sortingCenter;
    private final Boolean active;

    public Hub(String id, String province, String sortingCenter, Boolean active) {
        this.id = id;
        this.province = province;
        this.sortingCenter = sortingCenter;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public String getSortingCenter() {
        return sortingCenter;
    }

    public Boolean getActive() {
        return active;
    }
}

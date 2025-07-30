package se.iths.nextdeparturesl.model;

/**
 * Route represents a transit route in a GTFS feed.
 * Includes identifiers, names, and descriptive details.
 *
 * @author Sofie Van Dingenen
 */
public class Route {

    private String id;
    private String agencyId;
    private String shortName;
    private String longName;
    private String type;
    private String desc;

    public Route(String id,
                 String agencyId,
                 String shortName,
                 String longName,
                 String type,
                 String desc) {
        this.id = id;
        this.agencyId = agencyId;
        this.shortName = shortName;
        this.longName = longName;
        this.type = type;
        this.desc = desc;
    }

    public String getRouteId() {
        return id;
    }

    public void setRouteId(String id) {
        this.id = id;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setRouteShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

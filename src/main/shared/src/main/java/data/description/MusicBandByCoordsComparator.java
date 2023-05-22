package data.description;

import data.MusicBand;

import java.util.Comparator;

public class MusicBandByCoordsComparator implements Comparator<MusicBand> {
    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return  Double.compare(
                o1.getCoordinates().getDistance(),
                o2.getCoordinates().getDistance()
        );
    }
}

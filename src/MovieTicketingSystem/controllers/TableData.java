package MovieTicketingSystem.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableData {
    StringProperty bookingTime = new SimpleStringProperty();
    StringProperty showingHour = new SimpleStringProperty();
    StringProperty showingMinutes = new SimpleStringProperty();
    StringProperty movie = new SimpleStringProperty();
    StringProperty cinema = new SimpleStringProperty();
    StringProperty seat = new SimpleStringProperty();
    StringProperty price = new SimpleStringProperty();

    public final StringProperty bookingTimeProperty() {
        return this.bookingTime;
    }

    public final java.lang.String getBookingTime() {
        return this.bookingTimeProperty().get();
    }

    public final void setBookingTime(final java.lang.String bookingTime) { this.bookingTimeProperty().set(bookingTime);
    }

    public final StringProperty showingHourProperty() {
        return this.showingHour;
    }

    public final java.lang.String getShowingHour() {
        return this.showingHourProperty().get();
    }

    public final void setShowingHour(final java.lang.String showingHour) { this.showingHourProperty().set(showingHour);
    }

    public final StringProperty showingMinutesProperty() {
        return this.showingMinutes;
    }

    public final java.lang.String getshowingMinutes() {
        return this.showingMinutesProperty().get();
    }

    public final void setShowingMinutes(final java.lang.String showingMinutes) { this.showingMinutesProperty().set(showingMinutes); }

    public final StringProperty movieProperty() {
        return this.movie;
    }

    public final java.lang.String getMovie() {
        return this.movieProperty().get();
    }

    public final void setMovie(final java.lang.String movie) {
        this.movieProperty().set(movie);
    }

    public String getCinema() { return this.cinemaProperty().get(); }

    public StringProperty cinemaProperty() { return this.cinema; }

    public void setCinema(final java.lang.String bookingTime) { this.cinemaProperty().set(bookingTime); }

    public StringProperty seatProperty() { return this.seat; }

    public String getSeat() { return this.seatProperty().get(); }

    public void setSeat(final java.lang.String seat) { this.seatProperty().set(seat); }

    public StringProperty priceProperty() { return this.price; }

    public String getPrice() { return this.priceProperty().get(); }

    public void setPrice(final java.lang.String price) { this.priceProperty().set(price); }

}


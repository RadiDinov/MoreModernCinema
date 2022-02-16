package ClassWork_08_02;

public class Ticket {
    String ticketName;
    double ticketPrice;

    public Ticket(String ticketName, double ticketPrice) {
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
    }

    public Ticket() {

    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

}

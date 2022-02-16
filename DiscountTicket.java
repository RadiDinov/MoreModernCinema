package ClassWork_08_02;

public class DiscountTicket extends Ticket implements DiscountTicketInterface{
    double ticketDiscount;

    public DiscountTicket(String ticketName, double ticketPrice) {
        super(ticketName, ticketPrice);
    }

    public DiscountTicket() {

    }

    @Override
    public void setTicketPrice(double oldPrice) {
        super.setTicketPrice(oldPrice - (oldPrice * this.ticketDiscount / 100));
    }

    public double getTicketDiscount() {
        return ticketDiscount;
    }

    public void setTicketDiscount(double ticketDiscount) {
        this.ticketDiscount = ticketDiscount;
    }
}

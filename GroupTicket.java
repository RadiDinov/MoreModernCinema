package ClassWork_08_02;

import java.util.Scanner;

public class GroupTicket extends Ticket implements GroupTicketInterface {
    int groupTicketCountRequirement;
    double groupTicketPricePerPersonDiscount;

    public GroupTicket(String ticketName, double ticketPrice) {
        super(ticketName, ticketPrice);
    }

    public GroupTicket() {

    }

    @Override
    public void setTicketPrice(double oldPrice) {
        super.setTicketPrice(oldPrice - (oldPrice * (groupTicketPricePerPersonDiscount/100)));
    }

    public int getGroupTicketCountRequirement() {
        return groupTicketCountRequirement;
    }

    public void setGroupTicketCountRequirement() {
        this.groupTicketCountRequirement = 20;
    }

    public void setGroupTicketCountRequirement(int groupTicketCountRequirement) {
        this.groupTicketCountRequirement = groupTicketCountRequirement;
    }

    public double getGroupTicketPricePerPersonDiscount() {
        return groupTicketPricePerPersonDiscount;
    }

    public void setGroupTicketPricePerPersonDiscount(double groupTicketPricePerPersonDiscount) {
        this.groupTicketPricePerPersonDiscount = groupTicketPricePerPersonDiscount;
    }

    public void setGroupTicketPricePerPersonDiscount() {
        this.groupTicketPricePerPersonDiscount = 2.00;
    }
}

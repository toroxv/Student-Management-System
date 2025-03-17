public class Module {
    private double mark1;
    private double mark2;
    private double mark3;

    /**
     * Constructor for Module class
     * @param mark1 mark to set to mark1
     * @param mark2 mark to set to mark2
     * @param mark3 mark to set to mark3
     */
    public Module(double mark1, double mark2, double mark3) {
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }
    // getters and setters

    /**
     * To get mark1
     * @return mark1
     */
    public double getMark1() {
        return mark1;
    }

    /**
     * To get mark2
     * @return mark2
     */
    public double getMark2() {
        return mark2;
    }

    /**
     * To get mark3
     * @return mark3
     */
    public double getMark3() {
        return mark3;
    }

    /**
     * To set the value of mark1 to mark
     * @param mark mark1 to set
     */
    public void setMark1(double mark) {
        this.mark1 = mark;
    }

    /**
     * To set the value of mark2 to mark
     * @param mark mark2 to set
     */
    public void setMark2(double mark) {
        this.mark2 = mark;
    }

    /**
     * To set the value of mark3 to mark
     * @param mark mark3 to set
     */
    public void setMark3(double mark) {
        this.mark3 = mark;
    }
}

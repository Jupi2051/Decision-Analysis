public class SetCommand {
    private int id;
    private int value;

    public SetCommand(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }
}

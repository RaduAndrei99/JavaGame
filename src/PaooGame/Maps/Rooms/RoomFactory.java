package PaooGame.Maps.Rooms;

public class RoomFactory {

    public static final int ROOM_TYPES = 3;

    public static Room getRoom(int room) throws Exception {
        if (room > 0 && room < ROOM_TYPES) {
            switch (room) {
                case 1:
                    return new RoomModel1();

                case 2:
                    return new RoomModel2();

                case 3:
                    return new RoomModel3();

                default:
                    return new RoomModel1();
            }
        }else
            throw new Exception("Invalid argument");
    }
}
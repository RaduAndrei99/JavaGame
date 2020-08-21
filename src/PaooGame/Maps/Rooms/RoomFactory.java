package PaooGame.Maps.Rooms;

import PaooGame.RefLinks;

public class RoomFactory {

    public static final int ROOM_TYPES = 5;

    public static Room getRoom(int room, RefLinks r) throws Exception {
        room = 5;
        if (room > 0 && room <= ROOM_TYPES) {
            switch (room) {
                case 1:
                    return new RoomModel1(r);

                case 2:
                    return new RoomModel2(r);

                case 3:
                    return new RoomModel3(r);

                case 4:
                    return new RoomModel4(r);

                case 5:
                    return new RoomModel5(r);


                default:
                    return new RoomModel1(r);
            }
        } else
            throw new Exception("Invalid argument");
    }
}
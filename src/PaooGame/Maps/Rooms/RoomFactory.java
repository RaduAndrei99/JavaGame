package PaooGame.Maps.Rooms;

import PaooGame.RefLinks;

public class RoomFactory {

    public static final int ROOM_TYPES = 8;

    public static Room getRoom(int room, boolean createDoors, RefLinks r) throws Exception {
        if (room >= 0 && room <= ROOM_TYPES) {
            //room = 8;
            switch (room) {
                case 1:
                    return new RoomModel1(r,createDoors);

                case 2:
                    return new RoomModel2(r,createDoors);

                case 3:
                    return new RoomModel3(r,createDoors);

                case 4:
                    return new RoomModel4(r,createDoors);

                case 5:
                    return new RoomModel5(r,createDoors);

                case 6:
                    return new RoomModel6(r,createDoors);

                case 7:
                    return new RoomModel7(r,createDoors);

                case 8:
                    return new RoomModel8(r,createDoors);




                default:
                    return null;
            }
        } else
            throw new Exception("Invalid argument");
    }
}
package modchu.pflmf;

public class PFLMF_PacketConstantManager {

	public static String getConstantString(byte b) {
		String s = null;
		switch (b) {
		case 0:
			s = "packet_IDNone";
			break;
		case 1:
			s = "packet_IDRemove";
			break;
		case 2:
			s = "packet_IDSitting";
			break;
		case 3:
			s = "packet_IDSleeping";
			break;
		case 4:
			s = "packet_IDAction";
			break;
		case 5:
			s = "packet_IDRunActionNumber";
			break;
		case 6:
			s = "packet_IDAll";
			break;
		}
		return s;
	}

}
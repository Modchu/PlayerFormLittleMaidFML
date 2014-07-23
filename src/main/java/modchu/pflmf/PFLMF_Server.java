package modchu.pflmf;import java.io.InputStream;import java.util.LinkedList;import java.util.HashMap;import java.util.Map.Entry;import java.util.concurrent.ConcurrentHashMap;import modchu.lib.Modchu_Debug;import modchu.lib.Modchu_Main;import modchu.lib.characteristic.Modchu_AS;import modchu.lib.characteristic.Modchu_Packet;import modchu.lib.characteristic.Modchu_PacketBasis;import modchu.lib.characteristic.recompileonly.Modchu_CastHelper;public class PFLMF_Server extends PFLMF_Client {	public static HashMap<String, LinkedList> playerData = new HashMap();	private static final int maxServerPacketCount = 5;	public static void onPacketData(LinkedList list, Object entityPlayer, String channelName) {		receivePacket(list, entityPlayer, channelName);	}	public static void receivePacket(LinkedList list, Object entityPlayerMP, String channelName) {		Modchu_Debug.Debug("受信 PFLMF_Server receivePacket start.----------------");		byte packetId = (Byte)(list.size() > 0 ? list.get(0) : (byte) -1);		int entityId = (Integer)(list.size() > 1 ? list.get(1) : -1);		byte by = (Byte)(list.size() > 2 ? list.get(2) : (byte) -1);		byte by2 = (Byte)(list.size() > 3 ? list.get(3) : (byte) -1);		int entityId2 = Modchu_CastHelper.Int(list.size() > 4 ? list.get(4) : -1);		Object entityPlayer = entityPlayerMP != null ? entityPlayerMP : getPlayer(entityId);		byte n = 0;		String username = entityPlayer != null ? Modchu_AS.getString(Modchu_AS.userName, entityPlayer) : null;		Modchu_Debug.Debug("PFLMF_Server serverCustomPayload packetId="+(PFLMF_PacketConstantManager.getConstantString(packetId)));		switch (packetId) {		case packet_IDRemove:			for(int i = 0; i < maxServerPacketCount; i++) {				String s = ""+entityId+","+i;				if (playerData.containsKey(s)) playerData.remove(s);			}			Modchu_Debug.Debug("PFLMF_Server serverCustomPayload entityId="+entityId+" playerData.remove");			return;		case packet_IDSitting:			n = packet_IDSitting;			break;		case packet_IDSleeping:			n = packet_IDSleeping;			break;		case packet_IDAction:			n = packet_IDAction;			break;		case packet_IDRunActionNumber:			n = packet_IDRunActionNumber;			break;		case packet_IDAll:			n = packet_IDAll;			int i;			int i1;			Modchu_Debug.Debug("PFLMF_Server serverCustomPayload --------------------packet_IDAll start");			Modchu_Debug.Debug("PFLMF_Server entityId="+entityId+" username="+username+" all send");			Object[] byte1;			for (Entry<String, LinkedList> en : playerData.entrySet()) {				String s = en.getKey();				LinkedList list2 = en.getValue();				if (list2 != null) {					Object[] data = Modchu_Main.listToObjectArray(list2);					Modchu_PacketBasis.sendToClient(data, entityPlayer, channelName);					StringBuilder s0 = new StringBuilder().append("PFLMF_Server serverCustomPayload data[0]=");					for (int i2 = 0; i2 < data.length; i2++) {						s0.append("data[").append(i2).append("]=").append(data[i2]);					}					Modchu_Debug.Debug(s0.toString());				}			}			Modchu_Debug.Debug("PFLMF_Server serverCustomPayload --------------------packet_IDAll end");			return;		case packet_IDLANRemove:		case packet_IDLANSitting:		case packet_IDLANSleeping:		case packet_IDLANAction:		case packet_IDLANRunActionNumber:		case packet_IDLANAll:			break;		default:			throw new RuntimeException("PFLMF_Server Unknown packetId="+packetId+" found !!");		}		if (entityPlayer != null) {			Modchu_Debug.Debug("PFLMF_Server serverCustomPayload entityId="+entityId+" username="+username+" packetId="+packetId+" by="+by+" by2="+by2);			playerData.put(""+entityId+","+n, list);			if (packetId < 100) {				boolean isLANWorld = Modchu_AS.getBoolean(Modchu_AS.isLANWorld);				if (!Modchu_Main.isServer						&& isLANWorld) {					list.set(0, (byte)(packetId + 100));					PFLMF_Client.receivePacket(list);				}				Object[] data = Modchu_Main.listToObjectArray(list);				Modchu_PacketBasis.sendToClient(data, entityPlayer, channelName);				Modchu_PacketBasis.sendToAll(data, channelName, Modchu_AS.get(Modchu_AS.entityWorldObj, entityPlayer));				Modchu_Debug.Debug("PFLMF_Server serverCustomPayload 送信 data[0]="+data[0]+" [1]="+data[1]+" [2]="+data[2]);			}		} else {			Modchu_Debug.Debug("PFLMF_Server serverCustomPayload player == null !!");		}		Modchu_Debug.Debug("PFLMF_Server serverCustomPayload end.----------------");	}	private static Object getPlayer(int entityId) {		Object world = Modchu_AS.getObjectArray(Modchu_AS.FMLCommonHandlerInstanceGetMinecraftServerInstanceWorldServers)[0];		return PFLMF_Main.getPlayer(world, entityId);	}}
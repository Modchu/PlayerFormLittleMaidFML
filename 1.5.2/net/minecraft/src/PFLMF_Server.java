package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PFLMF_Server {

	public static HashMap<String, Object[]> playerData = new HashMap();
	private static final int maxServerPacketCount = 5;

	public static void sendToClient(NetServerHandler pHandler, byte[] data) {
		ModLoader.serverSendPacket(pHandler, new Packet250CustomPayload("PFLMF|Upd", data));
	}

	public static void serverCustomPayload(NetServerHandler handler, Packet250CustomPayload packet) {
		if (packet.data != null) ;else {
			mod_PFLMF.Debug("受信 PFLMF_Server serverCustomPayload packet.data == null !!");
			return;
		}
		mod_PFLMF.Debug("受信 PFLMF_Server serverCustomPayload packet.data[0]="+packet.data[0]);
		//mod_PFLMF.Debug("受信 PFLMF_Server serverCustomPayload packet.data[1]="+packet.data[1]);
		try {
			ByteArrayInputStream byteInput = new ByteArrayInputStream(packet.data);
			ObjectInputStream objectInput = new ObjectInputStream(byteInput);
			byte packetId = objectInput.readByte();
			int entityId = objectInput.readInt();
			byte by = objectInput.readByte();
			EntityPlayer player = getPlayer(entityId);
			Object[] o = new Object[4];
			o[0] = entityId;
			o[1] = by;
			byte by2 = -1;
			try {
				by2 = objectInput.readByte();
			} catch (Throwable t) {
			}
			o[2] = by2;
			o[3] = -1;
			byte n = 0;
			switch (packetId) {
			case 0:
				for(int i = 0; i < playerData.size(); i++) {
					if (playerData.containsKey(""+entityId+","+i)) playerData.remove(""+entityId+","+i);
				}
				mod_PFLMF.Debug("PFLMF_Server receivePacket entityId="+entityId+" playerData.remove");
				return;
			case 1:
				n = 1;
				break;
			case 2:
				n = 2;
				break;
			case 3:
				n = 3;
				o[1] = by2;
				break;
			case 4:
				n = 4;
				Iterator<Entry<String, Object[]>> iterator = playerData.entrySet().iterator();
				Entry<String, Object[]> entry;
				String s;
				int i;
				int i1;
				mod_PFLMF.Debug("PFLMF_Server receivePacket --------------------start");
				while(iterator.hasNext()) {
					entry = iterator.next();
					s = entry.getKey();
					o = entry.getValue();
					i1 = s.indexOf(",");
					if (i1 < 0) continue;
					int entityId2 = Integer.valueOf(s.substring(0, i1));
					i = Integer.valueOf(s.substring(i1 + 1));
					if (o != null) {
						o[3] = entityId2;
						packet.data = mod_PFLMF.sendState(i, o);
						ModLoader.serverSendPacket(handler, new Packet250CustomPayload("PFLMF|Upd", packet.data));
						mod_PFLMF.Debug("PFLMF_Server receivePacket entityId="+entityId+" all send i="+i+" o[0]="+o[0]+" o[1]="+o[1]+" o[2]="+o[2]+" o[3]="+o[3]);
					}
				}
				mod_PFLMF.Debug("PFLMF_Server receivePacket entityId="+entityId+" all send");
				mod_PFLMF.Debug("PFLMF_Server receivePacket --------------------end");
				return;
			default:
				throw new RuntimeException("PFLMF_Server Unknown packet id="+packetId+" found !!");
			}
			if (player != null) {
				mod_PFLMF.Debug("PFLMF_Server receivePacket entityId="+entityId+" packet id="+packetId+" o[0]="+o[0]+" o[1]="+o[1]);
				if (packetId != 0) playerData.put(""+entityId+","+n, o);
				else {
					for(int i = 0; i < maxServerPacketCount; i++) {
						if (playerData.containsKey(""+entityId+","+i)) playerData.remove(""+entityId+","+i);
					}
				}
				if (packetId != 4) ((WorldServer)player.worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(player, packet);
			} else {
				mod_PFLMF.Debug("PFLMF_Server receivePacket player == null !!");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static EntityPlayer getPlayer(int entityId) {
		World world = ModLoader.getMinecraftServerInstance().worldServers[0];
		Iterator iterator = world.playerEntities.iterator();
		EntityPlayer entityPlayer;
		while (iterator.hasNext()) {
			entityPlayer = (EntityPlayer) iterator.next();
			if (entityPlayer.entityId == entityId) return entityPlayer;
		}
		return null;
	}
}
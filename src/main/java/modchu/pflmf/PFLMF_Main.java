package modchu.pflmf;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_CastHelper;
import modchu.lib.Modchu_Config;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Packet;
import modchu.lib.Modchu_PacketManager;
import modchu.lib.Modchu_Reflect;

public class PFLMF_Main implements PFLMF_IPacketConstant {
	public static final String version = "9";
	public static modc_PFLMF baseModInstance;
	public static boolean usePacket = true;
	public static final String packetChannelName = "PFLMF";

	public static LinkedList<Object[]> sendList = new LinkedList();
	private static boolean autoUsePacketOff = false;

	public static void load(modc_PFLMF base) {
		Modchu_Debug.systemLogDebug("[PFLMF_Main] 1 - (1 / 2) load()");
		baseModInstance = base;
		Modchu_Packet.registerPacket(baseModInstance, packetChannelName);
		loadcfg();
		Modchu_Debug.systemLogDebug("[PFLMF_Main] 1 - (2 / 2) load() end.");
	}

	public static boolean onTickInGame(byte by, Object... tickData) {
		if (Modchu_Main.isServer
				| (Modchu_Main.getMinecraftVersion() > 162
				&& by != Modchu_Main.tickByte_ClientTickEvent)) {
			//Modchu_Debug.mDebug("PFLMF_Main onTickInGame Modchu_Main.isServer="+Modchu_Main.isServer+" by="+by);
			return true;
		}
		if (!Modchu_Packet.isPacketEnable()) {
			if (usePacket) usePacket = false;
			return false;
		}
		if (Modchu_Main.isRelease()) {
			if (Modchu_AS.getBoolean(Modchu_AS.isLANWorld)
					| Modchu_AS.getBoolean(Modchu_AS.isMuiti)) {
				if (autoUsePacketOff) {
					usePacket = true;
					autoUsePacketOff = false;
				}
			} else {
				if (usePacket) {
					usePacket = false;
					autoUsePacketOff = true;
				}
			}
		}
		sendState();
		return true;
	}

	public static void sendState() {
		if (!usePacket) return;
		if (sendList != null && !sendList.isEmpty()) {
			LinkedList list = new LinkedList();
			int i1;
			for (int i = sendList.size() - 1; i > -1; i--) {
				Modchu_Debug.Debug("PFLMF_Main sendState sendList i=" + i);
				Object[] o = sendList.get(i);
				if (o != null
						&& o.length > 0); else continue;
				i1 = Modchu_CastHelper.Int("" + o[0]);
				if (!list.contains(i1)) list.add(i1);
				else continue;
				sendState(o);
			}
			sendList.clear();
			Modchu_Debug.mDebug("PFLMF_Main sendState sendList sendState end.");
		} else {
			//if (sendList != null) {
			//if (sendList.isEmpty()) Modchu_Debug.mDebug("sendState sendList sendList.isEmpty()");
			//} else Modchu_Debug.mDebug("sendState sendList sendList == null");
		}
	}

	private static void sendState(Object[] o) {
		if (o != null
				&& o.length > 0); else return;
		byte i1 = Modchu_CastHelper.Byte(o[0]);
		Object modelData = o[1];
		Object entity = null;
		entity = o.length > 2
				&& Modchu_Reflect.loadClass("Entity").isInstance(o[2]) ? o[2] : null;
		if (modelData != null
				&& entity != null); else {
			Modchu_Debug.Debug("PFLMF_Main sendState return modelData=" + modelData);
			Modchu_Debug.Debug("PFLMF_Main sendState return entity=" + entity);
			return;
		}
		Object[] byte0 = null;
		Object uuid = Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, entity) : null;
		//Modchu_Debug.lDebug("PFLMF_Main sendState entityUUID="+uuid);
		//Modchu_Debug.lDebug("PFLMF_Main sendState entityUUID.getMostSignificantBits()="+((UUID) uuid).getMostSignificantBits());
		//Modchu_Debug.lDebug("PFLMF_Main sendState entityUUID.getLeastSignificantBits()="+((UUID) uuid).getLeastSignificantBits());
		Object entityId = uuid != null ? uuid.toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, entity);
		if (entityId != null); else return;
		switch (i1) {
		case packet_IDRemove:
			byte0 = getSendStateObjectArray(packet_IDRemove, entityId, (byte) packet_FALSE);
			break;
		case packet_IDSitting:
			boolean isSitting = false;
			if (o.length > 3 && o[3] instanceof Object[]) {
				Object[] o2 = (Object[]) o[3];
				isSitting = Modchu_CastHelper.Boolean("" + o2[0]);
			} else {
				int caps_isSitting = (Integer) Modchu_Reflect.getFieldObject("modchu.model.Modchu_IEntityCapsBase", "caps_isSitting", modelData);
				isSitting = (Boolean) Modchu_Reflect.invokeMethod("modchu.model.Modchu_EntityCapsHelper", "getCapsValueBoolean", new Class[]{ Modchu_Reflect.loadClass("modchu.model.Modchu_IEntityCapsBase"), int.class, Object[].class }, null, new Object[]{ modelData, caps_isSitting, null });
			}
			byte0 = getSendStateObjectArray(packet_IDSitting, entityId, isSitting ? (byte) packet_TRUE : (byte) packet_FALSE);
			Modchu_Debug.Debug("PFLMF_Main sendState entity.entityId=" + entityId + " isSitting=" + isSitting);
			break;
		case packet_IDSleeping:
			boolean isSleeping = false;
			int rotate = 0;
			if (o.length > 3 && o[3] instanceof Object[]) {
				Object[] o2 = (Object[]) o[3];
				isSleeping = Modchu_CastHelper.Boolean("" + o2[0]);
				rotate = Modchu_CastHelper.Int("" + o2[1]);
			} else {
				int caps_isSleeping = (Integer) Modchu_Reflect.getFieldObject("modchu.model.Modchu_IEntityCapsBase", "caps_isSleeping", modelData);
				isSleeping = (Boolean) Modchu_Reflect.invokeMethod("modchu.model.Modchu_EntityCapsHelper", "getCapsValueBoolean", new Class[]{ Modchu_Reflect.loadClass("modchu.model.Modchu_IEntityCapsBase"), int.class, Object[].class }, null, new Object[]{ modelData, caps_isSleeping, null });
				int caps_rotate = (Integer) Modchu_Reflect.getFieldObject("modchu.model.Modchu_IEntityCapsBase", "caps_rotate", modelData);
				rotate = (Integer) Modchu_Reflect.invokeMethod("modchu.model.Modchu_EntityCapsHelper", "getCapsValueInt", new Class[]{ Modchu_Reflect.loadClass("modchu.model.Modchu_IEntityCapsBase"), int.class, Object[].class }, null, new Object[]{ modelData, caps_rotate, null });
			}
			byte0 = getSendStateObjectArray(packet_IDSleeping, entityId, isSleeping ? (byte) packet_TRUE : (byte) packet_FALSE, (byte) rotate);
			Modchu_Debug.Debug("PFLMF_Main sendState entity.entityId=" + entityId + " isSleeping=" + isSleeping);
			break;
		case packet_IDAction:
			boolean actionFlag = false;
			if (o.length > 3 && o[3] instanceof Object[]) {
				Object[] o2 = (Object[]) o[3];
				actionFlag = Modchu_CastHelper.Boolean("" + o2[0]);
				Modchu_Debug.Debug("PFLMF_Main sendState packet_IDAction actionFlag=" + actionFlag);
			} else {
				Modchu_Debug.lDebug("PFLMF_Main sendState packet_IDAction error o=" + o + " o.length=" + o.length);
				Modchu_Debug.lDebug("PFLMF_Main sendState packet_IDAction error o[3]=" + o[3]);
				throw new RuntimeException("sendState packet_IDAction error !!");
			}
			byte0 = getSendStateObjectArray(packet_IDAction, entityId, actionFlag ? (byte) packet_TRUE : (byte) packet_FALSE);
			break;
		case packet_IDRunActionNumber:
			int runActionNumber = 0;
			if (o.length > 3 && o[3] instanceof Object[]) {
				Object[] o2 = (Object[]) o[3];
				runActionNumber = Modchu_CastHelper.Int("" + o2[0]);
				//Modchu_Debug.Debug("sendState packet_IDRunActionNumber runActionNumber="+runActionNumber);
			} else {
				Modchu_Debug.lDebug("PFLMF_Main sendState packet_IDRunActionNumber error o=" + o + " o.length=" + o.length);
				Modchu_Debug.lDebug("PFLMF_Main sendState packet_IDRunActionNumber error o[3]=" + o[3]);
				throw new RuntimeException("sendState packet_IDRunActionNumber error !!");
			}
			byte0 = getSendStateObjectArray(packet_IDRunActionNumber, entityId, (byte) runActionNumber);
			//Modchu_Debug.Debug("sendState packet_IDAction entity.entityId="+entity.entityId+" runActionNumber="+runActionNumber);
			break;
		case packet_IDAll:
			byte0 = getSendStateObjectArray(packet_IDAll, entityId, (byte) packet_FALSE);
			break;
		case packet_IDNone:
			Modchu_Debug.Debug("PFLMF_Main sendState packet_IDNone !! return.");
			return;
		}
		if (byte0 != null) {
			Modchu_PacketManager.sendToServer(byte0, PFLMF_Main.packetChannelName);
			Modchu_Debug.Debug("PFLMF_Main sendState i1=" + i1);
		} else {
			Modchu_Debug.Debug("PFLMF_Main sendState byte0 == null !! end.");
		}
	}

	public static Object[] getSendStateObjectArray(Object... o) {
		return (Object[]) o;
	}

	public static LinkedList getPlayerState(Object entityId, byte packetId) {
		//Modchu_Debug.Debug("PFLMF getPlayerState entityId="+entityId+" packetId="+packetId);
		return PFLMF_Client.getPlayerState(entityId, packetId);
	}

	public static void loadcfg() {
		// cfg読み込み
		File cfgdir = new File(!Modchu_Main.isServer ? Modchu_AS.getFile(Modchu_AS.minecraftMcDataDir) : new File("."), "/config/");
		File mainCfgfile = new File(cfgdir, ("PFLMF.cfg"));
		if (cfgdir.exists()) {
			if (!mainCfgfile.exists()) {
				// cfgファイルが無い= 新規作成
				String s[] = { "usePacket=true", "debugMessage=false" };
				Modchu_Config.writerConfig(mainCfgfile, s);
			} else {
				// cfgファイルがある
				usePacket = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "usePacket", usePacket));
				//debugMessage = Modchu_CastHelper.Boolean((Modchu_Config.loadConfig(mainCfgfile, "debugMessage", usePacket)));
				String k[] = { "usePacket"
				//, "debugMessage"
				};
				String k1[] = { "" + usePacket
				//, ""+debugMessage
				};
				Modchu_Config.writerSupplementConfig(mainCfgfile, k, k1);
			}
		}
	}

	public static void onPacketData(LinkedList list, Object entityPlayer, String channelName) {
		boolean debug = false;
		if (debug) Modchu_Debug.lDebug("PFLMF_Main 受信 onPacketData");
		boolean isLANWorld = Modchu_AS.getBoolean(Modchu_AS.isLANWorld);
		if (debug) Modchu_Debug.lDebug("PFLMF_Main 受信 onPacketData isLANWorld="+isLANWorld);
		boolean isIntegratedServerRunning = Modchu_AS.getBoolean(Modchu_AS.isIntegratedServerRunning);
		if (debug) Modchu_Debug.lDebug("PFLMF_Main 受信 onPacketData isIntegratedServerRunning="+isIntegratedServerRunning);
		boolean b = !Modchu_Main.isServer
				&& !isLANWorld
				| (isLANWorld
						&& !isIntegratedServerRunning);
		Modchu_Debug.Debug("PFLMF_Main 受信 onPacketData " + (b ? "client" : "server"));
		if (b) {
			PFLMF_Client.onPacketData(list, entityPlayer, channelName);
		} else {
			PFLMF_Server.onPacketData(list, entityPlayer, channelName);
		}
		if (debug) Modchu_Debug.lDebug("PFLMF_Main 受信 onPacketData end.");
	}

	public static void clientLoggedIn(Object netHandler, Object iNetworkManager, Object packet1Login) {
		if ((Boolean) Modchu_Reflect.getFieldObject("modchu.pflm.PFLM_Main", "entityReplaceFlag")) {
			Modchu_Reflect.invokeMethod("modchu.pflm.PFLM_Main", "clientConnect", new Class[]{ Modchu_Reflect.loadClass("NetClientHandler") }, new Object[]{ netHandler });
		}
	}

	public static Object getPlayer(Object world, Object entityId) {
		boolean debug = false;
		if (debug) Modchu_Debug.lDebug("PFLMF_Main getPlayer entityId="+entityId);
		if (Modchu_Main.getMinecraftVersion() < 180) return getPlayer(world, Modchu_CastHelper.Int(entityId));
		String entityIdString = Modchu_CastHelper.String(entityId);
		if (debug) Modchu_Debug.lDebug("PFLMF_Main getPlayer entityIdString="+entityIdString);
		List list = Modchu_AS.getList(Modchu_AS.worldPlayerEntities, world);
		if (list != null
				&& !list.isEmpty()); else return null;
		for (Object entityPlayer : list) {
			UUID uuid = Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, entityPlayer);
			if (debug) Modchu_Debug.lDebug("PFLMF_Main getPlayer uuid="+uuid);
			if (uuid != null); else continue;
			String uuidString = uuid.toString();
			if (uuidString.equals(entityIdString)) {
				if (debug) Modchu_Debug.lDebug("PFLMF_Main getPlayer uuid ok return. entityPlayer="+entityPlayer);
				return entityPlayer;
			}
		}
		return null;
	}

	public static Object getPlayer(Object world, int entityId) {
		boolean debug = false;
		for (Object entityPlayer : Modchu_AS.getList(Modchu_AS.worldPlayerEntities, world)) {
			int entityId2 = Modchu_AS.getInt(Modchu_AS.entityEntityID, entityPlayer);
			if (entityId2 == entityId) {
				if (debug) Modchu_Debug.lDebug("PFLMF_Main getPlayer entityId ok return. entityPlayer="+entityPlayer);
				return entityPlayer;
			} else {
				if (debug) Modchu_Debug.lDebug("PFLMF_Main getPlayer entityId2 != entityId entityId2="+entityId2);
			}
		}
		return null;
	}

	public static Object ticks() {
		return null;
	}

	public static String getLabel() {
		return null;
	}

}
